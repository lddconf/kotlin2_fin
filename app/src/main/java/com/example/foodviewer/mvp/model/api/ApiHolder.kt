package com.example.foodviewer.mvp.model.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


object ApiHolder {
    val gson = GsonBuilder().create()

    val api: IDataSource by lazy {
        Retrofit.Builder()
            .baseUrl("https://www.thecocktaildb.com/api/json/v1/1/")
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(IDataSource::class.java)
    }

    val apiTemplateHolder = object : IUrlTemplateHolder {
        override val smallImageURLTempate: String = "https://www.thecocktaildb.com/images/ingredients/%s-Small.png"
    }
}