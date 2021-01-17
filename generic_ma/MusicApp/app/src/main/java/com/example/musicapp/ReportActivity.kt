package com.example.musicapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.musicapp.adapters.EntityClientAdapter
import com.example.musicapp.adapters.EntityReportAdapter
import kotlinx.android.synthetic.main.activity_elements.*
import kotlinx.android.synthetic.main.activity_elements.progressBar
import kotlinx.android.synthetic.main.activity_report.*

class ReportActivity : AppCompatActivity() {
    lateinit var adapter: EntityReportAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)

        progressBar.visibility = View.VISIBLE
        val handler = Handler()
        handler.postDelayed( {
            adapter = EntityReportAdapter(this@ReportActivity) //make adapter for filtering with genre (field4)
            val layoutManager = LinearLayoutManager(this)
            layoutManager.orientation = LinearLayoutManager.VERTICAL

            listView5.layoutManager = layoutManager
            listView5.adapter = adapter
            progressBar.visibility = View.GONE
        }, 1000)
    }
}