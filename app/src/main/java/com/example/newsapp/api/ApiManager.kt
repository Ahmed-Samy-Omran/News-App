package com.example.newsapp.api

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiManager {
    companion object{
        private val Base_URL="https://newsapi.org/"
        private var retrofit:Retrofit?=null
        private fun getInstance():Retrofit{ // this fun return instance/obj from retrofit in case it = null in other case it work on the same retrofit
            if (retrofit==null){
                //create Retrofit
                val logging = HttpLoggingInterceptor(
                    object :HttpLoggingInterceptor.Logger{
                        override fun log(message: String) {
                            Log.e("api",message)
                        }
                    }
                )
                logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                val client = OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build()
                retrofit=Retrofit.Builder()
                    .baseUrl(Base_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!;

        }
        fun getApis():Services{
            return getInstance().create(Services::class.java)
        }
    }
}