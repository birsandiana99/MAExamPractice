package com.example.musicapp.local_db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.musicapp.models.EntityContract

private const val SQL_CREATE_ENTRIES =
    """CREATE TABLE IF NOT EXISTS ${EntityContract.TaskEntry.DB_TABLE} (
            ${EntityContract.TaskEntry.COLUMN_ID} INTEGER PRIMARY KEY,
            ${EntityContract.TaskEntry.COLUMN_TITLE} TEXT,
            ${EntityContract.TaskEntry.COLUMN_DESCRIPTION} TEXT,
            ${EntityContract.TaskEntry.COLUMN_ALBUM} TEXT,
            ${EntityContract.TaskEntry.COLUMN_GENRE} TEXT,
            ${EntityContract.TaskEntry.COLUMN_YEAR} INTEGER
            );
        """

private const val SQL_DELETE_ENTRIES =
    "DROP TABLE IF EXISTS ${EntityContract.TaskEntry.DB_TABLE}"

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(
        context,
        EntityContract.DB_NAME,
        null,
        EntityContract.DB_VERSION
    ) {

    var context: Context? = context

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(SQL_CREATE_ENTRIES)
//        context?.toast(" database is created")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL(SQL_DELETE_ENTRIES)
    }
}