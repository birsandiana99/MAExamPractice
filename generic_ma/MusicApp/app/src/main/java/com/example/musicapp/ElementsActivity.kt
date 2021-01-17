package com.example.musicapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.musicapp.adapters.EntityClientAdapter
import kotlinx.android.synthetic.main.activity_elements.*

class ElementsActivity : AppCompatActivity() {
    lateinit var adapter: EntityClientAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_elements)

        progressBar.visibility = View.VISIBLE
        val handler = Handler()
        handler.postDelayed( {
            adapter = EntityClientAdapter(this@ElementsActivity, intent.getStringExtra("genre"))
            val layoutManager = LinearLayoutManager(this)
            layoutManager.orientation = LinearLayoutManager.VERTICAL

            listView3.layoutManager = layoutManager
            listView3.adapter = adapter
            progressBar.visibility = View.GONE
        }, 1000)
    }
}
