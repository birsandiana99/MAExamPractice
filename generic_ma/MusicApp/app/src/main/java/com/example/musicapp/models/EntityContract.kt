package com.example.musicapp.models

import android.provider.BaseColumns

object EntityContract {
    const val DB_NAME = "db_example"
    const val DB_VERSION = 1

    // Table contents are grouped together in an anonymous object.
    object TaskEntry : BaseColumns {
        const val DB_TABLE = "my_table"
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title" //fieldName
        const val COLUMN_DESCRIPTION = "description" //field2
        const val COLUMN_ALBUM = "album" //field3
        const val COLUMN_GENRE = "genre" //field4
        const val COLUMN_YEAR = "year" //field5

    }
}