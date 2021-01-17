package com.example.musicapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.musicapp.adapters.ClientAdapter
import com.example.musicapp.adapters.EntityAdapter
import kotlinx.android.synthetic.main.activity_client.*

class ClientActivity : AppCompatActivity() {
    lateinit var adapter: ClientAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client)

        progressBarClient.visibility = View.VISIBLE
        val handler = Handler()
        handler.postDelayed( {
            adapter = ClientAdapter(this@ClientActivity)
            val layoutManager = LinearLayoutManager(this)
            layoutManager.orientation = LinearLayoutManager.VERTICAL

            listView2.layoutManager = layoutManager
            listView2.adapter = adapter
            progressBarClient.visibility = View.GONE
        }, 1000)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.buttons, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btn_refresh -> {
                adapter.refreshElements()
            }

            R.id.btn_favourites -> {
                startActivity(Intent(this@ClientActivity, SpecialActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
