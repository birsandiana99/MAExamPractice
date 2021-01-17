package com.example.musicapp.adapters

import android.content.Context
import android.net.ConnectivityManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.musicapp.R
import com.example.musicapp.models.Song
import com.example.musicapp.networking.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.report_view.view.*
import okhttp3.ResponseBody
import retrofit2.HttpException

class EntityReportAdapter(val context: Context) :
    RecyclerView.Adapter<EntityReportAdapter.ElementReportAdapter>()
    {

        val client by lazy { ApiClient.create() }
        var elementsList: ArrayList<Song> = ArrayList()

        init {
            refreshElements()
        }


        class ElementReportAdapter(val view: View) : RecyclerView.ViewHolder(view)

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): EntityReportAdapter.ElementReportAdapter {

            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.report_view, parent, false)

            return ElementReportAdapter(view)
        }

        override fun onBindViewHolder(holder: ElementReportAdapter, position: Int) {
            // prepare details and special operation activity
            Log.d("AAAAAAAAAAAAA",elementsList[position].year.toString())
            holder.view.fieldNameAnd5.text = elementsList[position].title +" : "+ elementsList[position].year.toString()// name
//            holder.view.year.text = elementsList[position].year.toString() // field5
        }

        override fun getItemCount() = elementsList.size

        private fun refreshElements() {
            if (checkOnline()) {
                client.getElements() // /songs/:genre
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { result ->
                            elementsList.clear()
                            for ( el in result)
                            {
                                if(el.year>1990)
                                    elementsList.add(el)
                            }
                            elementsList.sortByDescending { T -> T.year }
                            var elementsList2: ArrayList<Song> = ArrayList()
                            elementsList2.add(elementsList[0])
                            elementsList2.add(elementsList[1])
                            elementsList2.add(elementsList[2])
                            elementsList = elementsList2
//                            elementsList.addAll(result)
                            notifyDataSetChanged()
//                        Log.d("Elements -> ", elementsList.toString())
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

        private fun checkOnline(): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = cm.activeNetworkInfo
            if (networkInfo != null && networkInfo.isConnected) {
                return true
            }
            return false

        }

}
