package com.example.musicapp.networking

import com.example.musicapp.models.Song
import io.reactivex.Completable
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiClient {

    @GET("all") fun getElements(): Observable<List<Song>> // Observable<SongEmbedded>
    @GET("genres") fun getGenres(): Observable<List<String>>
    @GET("songs/{genre}") fun getSongs(@Path("genre") genre: String): Observable<List<Song>>
    @POST("song") fun addElement(@Body element: Song): Completable
    @DELETE("song/{id}") fun deleteElement(@Path("id") id: Int) : Completable
    @PUT("element/{id}") fun updateElement(@Path("id")id: Int, @Body element: Song) : Completable
    @POST("buySong") fun buyElement(@Body element: Song) : Completable
    companion object {

        fun create(): ApiClient {

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://10.0.2.2:2224/")
                .build()

            return retrofit.create(ApiClient::class.java)
        }
    }
}
