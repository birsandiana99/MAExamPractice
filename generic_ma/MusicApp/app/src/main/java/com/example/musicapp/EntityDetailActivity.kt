package com.example.musicapp

import android.content.ContentValues
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.musicapp.local_db.DbManager
import com.example.musicapp.models.Song
import kotlinx.android.synthetic.main.activity_entity_detail.*
import kotlinx.android.synthetic.main.activity_entity_detail.view.*

class EntityDetailActivity : AppCompatActivity() {
    private val dbManager = DbManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entity_detail)

        titleDetail.text = intent.getStringExtra("title") // name
        descriptionDetail.text = intent.getStringExtra("description") // field2
        albumDetail.text = intent.getStringExtra("album") // field3
        genreDetail.text = intent.getStringExtra("genre") // field4
        yearDetail.text = intent.getStringExtra("year") // field5

        favBtn.setOnClickListener {
            val values = ContentValues()
            values.put("title", intent.getStringExtra("title")) // name
            values.put("description", intent.getStringExtra("description")) // field2
            values.put("album", intent.getStringExtra("album")) // field3
            values.put("genre", intent.getStringExtra("genre")) // field4
            values.put("year", intent.getStringExtra("year").toInt()) // field5
            dbManager.insert(values) // insert values into db - offline only (favourites)

        }
    }
}
