package com.example.foodviewer.mvp.model.api

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


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

    val apiTemplateHolder = object : IIngredientImageUrlSource {
        private val baseUrl= "https://www.thecocktaildb.com/images/ingredients/"

        override fun ingredientSmallImageURLByName(name: String): String {
            return String.format(Locale.getDefault(), "$baseUrl%s-Small.png", name)
        }

        override fun ingredientMediumImageURLByName(name: String): String {
            return String.format(Locale.getDefault(), "$baseUrl%s-Medium.png", name)
        }
    }
}