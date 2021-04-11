package com.example.foodviewer.mvp.model.requests

import com.example.foodviewer.mvp.model.api.IDataSource
import com.example.foodviewer.mvp.model.api.IUrlTemplateHolder
import com.example.foodviewer.mvp.model.entity.CocktailDetails
import com.example.foodviewer.mvp.model.entity.toCocktailDetails
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.lang.RuntimeException
import java.util.*

class RetrofitIngredientDetails(
    val api: IDataSource,
    private val urlTemplates: IUrlTemplateHolder
) : IIngredientDetails {
    override fun ingredientSmallImageURLByName(name: String) =
        String.format(Locale.getDefault(), urlTemplates.smallImageURLTempate, name)
}