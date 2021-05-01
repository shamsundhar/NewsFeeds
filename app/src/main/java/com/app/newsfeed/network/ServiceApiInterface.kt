package com.app.newsfeed.network

import com.app.newsfeed.newbulletin.NewsFeed
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceApiInterface {

    @GET("v2/top-headlines?apiKey=${API_KEY}")
    suspend fun getNewsFeed(
        @Query("country") country: String,
        @Query("category") category: String
    ): Response<NewsFeed>


    companion object {
        private const val BASE_URL = "https://newsapi.org/"
        private const val API_KEY = "f71af7261c434b5d8be60816ed910d8b"
        operator fun invoke(): ServiceApiInterface {
            val okkHttpclient = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()

            return Retrofit.Builder()
                .client(okkHttpclient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ServiceApiInterface::class.java)
        }
    }
}