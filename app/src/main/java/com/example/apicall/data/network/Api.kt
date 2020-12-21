package com.example.apicall.data.network

import com.example.apicall.data.entity.Post
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface Api {

    @GET("posts")
    suspend fun getPost():List<Post>

    companion object{
        //     (
          //          networkConnectionInterceptor:NetworkConnectionInterceptor
        //        )
        operator fun invoke(): Api{
            val okHttpClient = OkHttpClient.Builder()
              // .addInterceptor(networkConnectionInterceptor)
                .build()
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Api::class.java)
            /*  .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();*/
        }
    }
}