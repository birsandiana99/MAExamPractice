package com.example.musicapp.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.musicapp.R
import com.example.musicapp.local_db.DbManager
import com.example.musicapp.models.Song
import com.example.musicapp.networking.ApiClient
import kotlinx.android.synthetic.main.simple_view.view.*

class SpecialAdapter(val context: Context) :
    RecyclerView.Adapter<SpecialAdapter.SpecialViewAdapter>() {

    private val client by lazy { ApiClient.create() }
    var elementsList: ArrayList<Song> = ArrayList()
    private var dbManager: DbManager

    init {
        dbManager = DbManager(context)
        loadQueryAll()
    }

    private fun loadQueryAll() {
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
    }



    class SpecialViewAdapter(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SpecialAdapter.SpecialViewAdapter {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.simple_view, parent, false)

        return SpecialViewAdapter(view)
    }

    override fun onBindViewHolder(holder: SpecialViewAdapter, position: Int) {

        holder.view.title.text = elementsList[position].title // name
        holder.view.album.text = elementsList[position].album // field3
        holder.view.genre.text = elementsList[position].genre // field4
        holder.view.year.text = elementsList[position].year.toString() // field5
    }

    override fun getItemCount() = elementsList.size

}