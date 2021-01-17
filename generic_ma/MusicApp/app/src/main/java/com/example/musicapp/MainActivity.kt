package com.example.musicapp

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        clerkButton.setOnClickListener {
            openClerk()
        }

        clientButton.setOnClickListener {
            openClient()
        }
    }

    fun openClerk() {
        if (checkOnline()) {
            startActivity(Intent(this, ClerkActivity::class.java))
        } else {
            Toast.makeText(this, "You don't have internet", Toast.LENGTH_LONG).show()
        }
    }

    fun openClient() {
        startActivity(Intent(this, ClientActivity::class.java))
    }


    fun checkOnline(): Boolean {
        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected) {
            return true
        }

        return false

    }
}