package com.example.musicapp

import android.content.ContentValues
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.example.musicapp.adapters.ClientAdapter
import com.example.musicapp.adapters.EntityAdapter
import com.example.musicapp.models.Song
import kotlinx.android.synthetic.main.activity_item.*

class ItemActivity : AppCompatActivity() {
    var id = 0
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)
        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            id = bundle.getInt("MainActId", 0)
            if (id != 0) {
                titleText.setText(bundle.getString("MainActTitle"))
            }
        }

        sendButton.setOnClickListener {
            //val dbManager = DbManager(this)
            val elementAdapter = EntityAdapter(this)

            val values = ContentValues()


            if (id == 0) {
                val item = Song(1,"","")
                item.title = titleText.text.toString()
                item.description = descriptionText.text.toString()
                item.album =  albumText.text.toString()
                item.genre = genreText.text.toString()
                item.year = yearText.text.toString().toInt()
                progressBar.visibility = View.VISIBLE


                val handler = Handler()
                handler.postDelayed( {
                    elementAdapter.addElement(item)
                    elementAdapter.refreshElements()
                    progressBar.visibility = View.GONE
                    finish()
                }, 1000)


            } else {
//                val selectionArs = arrayOf(id.toString())
//                val mID = dbManager.update(values, "Id=?", selectionArs)
//                var item = ItemTodo(id,"","now")
//                item.title = editTitle.text.toString()
//                item.description =  editTask.text.toString()
//                val mId = itemTodoAdapter.updateItemTodo(item)
//                itemTodoAdapter.refreshItemTodo()

//                if (mID > 0) {
//                    toast("Added task!")
//                    finish()
//                } else {
//                    toast("Error at add!")
//                }
            }
        }
    }
}
