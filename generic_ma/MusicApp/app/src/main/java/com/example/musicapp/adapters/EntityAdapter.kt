package com.example.musicapp.adapters

import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.musicapp.EditActivity
import com.example.musicapp.EntityDetailActivity
import com.example.musicapp.R
import com.example.musicapp.local_db.DbManager
import com.example.musicapp.models.Song
import com.example.musicapp.networking.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.item_view_clerk.view.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException

class EntityAdapter(val context: Context) :
    RecyclerView.Adapter<EntityAdapter.ElementViewAdapter>() {

    val client by lazy { ApiClient.create() }
    var elementsList: ArrayList<Song> = ArrayList()
    private lateinit var dbManager: DbManager


    init {
        dbManager = DbManager(context)
        refreshElements()
    }


    class ElementViewAdapter(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EntityAdapter.ElementViewAdapter {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view_clerk, parent, false)

        return ElementViewAdapter(view)
    }

    override fun onBindViewHolder(holder: ElementViewAdapter, position: Int) {
        holder.view.title.text = elementsList[position].title // name
        holder.view.album.text = elementsList[position].album // field3
        holder.view.genre.text = elementsList[position].genre // field4
        holder.view.year.text = elementsList[position].year.toString() // field5
        holder.view.btnDelete.setOnClickListener { showDeleteDialog(holder, elementsList[position]) }

        holder.view.setOnClickListener {
            val aux = Intent(context, EditActivity::class.java)
            Log.d("AAA  put id:" ,elementsList[position].id.toString())
            aux.putExtra("id", elementsList[position].id.toString()) // name
            aux.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(aux)
        }
    }

    override fun getItemCount() = elementsList.size

    fun refreshElements() {
        if (checkOnline()) {
            client.getElements()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        elementsList.clear()
                        elementsList.addAll(result)
                        elementsList.sortWith(compareBy({ it.album }, { it.title })) // field3, name
                        notifyDataSetChanged()
//                        Log.d("Elements -> ", elementsList.toString())
                    },
                    { throwable ->
                        if (throwable is HttpException) {
                            val body: ResponseBody = throwable.response().errorBody()!!
                            Toast.makeText(
                                context,
                                "Error: ${JSONObject(body.string()).getString("text")}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                )
        } else {
            val cursor = dbManager.queryAll()
            elementsList.clear()
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getInt(cursor.getColumnIndex("id"))
                    val title = cursor.getString(cursor.getColumnIndex("title")) // name
                    val description = cursor.getString(cursor.getColumnIndex("description")) // field2
                    val album = cursor.getString(cursor.getColumnIndex("album")) // field3
                    val genre = cursor.getString(cursor.getColumnIndex("genre")) // field4
                    val year = cursor.getString(cursor.getColumnIndex("year")) // field5
                    // id, name, field2, field3, field4, field5
                    elementsList.add(Song(id = id, title = title, description = description, album = album, genre = genre, year = year.toInt()))
                } while (cursor.moveToNext())
            }
            Toast.makeText(context, "Not online!", Toast.LENGTH_LONG).show()
        }

    }

    private fun checkOnline(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected) {
            return true
        }
        return false

    }

    fun addElement(element: Song) {
        if (checkOnline()) {
            client.addElement(element)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        refreshElements()
//                        Log.d("Element added -> ", element.toString())
                    },
                    { throwable ->
                        if (throwable is HttpException) {
                            val body: ResponseBody = throwable.response().errorBody()!!
                            Toast.makeText(
                                context,
                                "Error: ${JSONObject(body.string()).getString("text")}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                )
        } else {
            val values = ContentValues()
            values.put("title", element.title) // name
            values.put("description", element.description) // field2
            values.put("album", element.album) // field3
            values.put("genre", element.genre) // field4
            values.put("year", element.year) // field5
            dbManager.insert(values) // insert values into db - offline only (favourites) // insert values into db - offline only (favourites)
            Toast.makeText(context, "Not online!", Toast.LENGTH_LONG).show()
        }
    }

    fun editElement(element: Song) {
        Log.d("AAAA",element.id.toString())
        if (checkOnline()) {
            client.updateElement(element.id!!, element)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        refreshElements()
                        Log.d("Element edited -> ", element.toString())
                    },
                    { throwable ->
                        if (throwable is HttpException) {
                            val body: ResponseBody = throwable.response().errorBody()!!
                            Toast.makeText(
                                context,
                                "Error: ${body.string()}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                )
        } else {
            Toast.makeText(context, "Not online!", Toast.LENGTH_LONG).show()
        }
    }

    private fun deleteElement(element: Song) { // delete
        if (checkOnline()) {
            client.deleteElement(element.id!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        refreshElements()
//                        Log.d("Element deleted -> ", element.toString())
                    },
                    { throwable ->
                        Toast.makeText(context, "Delete error: ${throwable.message}", Toast.LENGTH_LONG).show()
                    }
                )
        } else {
            Toast.makeText(context, "Not online!", Toast.LENGTH_LONG).show()
        }

    }

    private fun showDeleteDialog(holder: ElementViewAdapter, element: Song) { // confirm delete
        val dialogBuilder = AlertDialog.Builder(holder.view.context)
        dialogBuilder.setTitle("Delete")
        dialogBuilder.setMessage("Confirm delete?")
        dialogBuilder.setPositiveButton("Delete") { _, _ ->
            deleteElement(element)
        }
        dialogBuilder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }
        val b = dialogBuilder.create()
        b.show()
    }
}