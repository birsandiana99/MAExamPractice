package com.example.musicapp.models

import com.google.gson.annotations.SerializedName

data class Song (
    @field:SerializedName("id")
    var id: Int = 0,
    @field:SerializedName("title") //fieldName
    var title: String = "",
    @field:SerializedName("description") //field2
    var description: String = "",
    @field:SerializedName("album") //field3
    var album: String = "new",
    @field:SerializedName("genre") //field4
    var genre: String = "",
    @field:SerializedName("year") //field5
    var year :Int = 0)