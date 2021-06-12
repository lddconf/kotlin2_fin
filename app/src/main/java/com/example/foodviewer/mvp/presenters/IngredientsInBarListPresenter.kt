package com.example.foodviewer.mvp.presenters

import com.example.foodviewer.mvp.model.entity.bar.IBarProperties
import com.example.foodviewer.mvp.model.entity.bar.IFavoriteCocktails
import com.example.foodviewer.mvp.model.entity.json.IngredientDetails
import com.example.foodviewer.mvp.model.entity.json.IngredientsDescription
import com.example.foodviewer.mvp.model.requests.ICocktailDetails
import com.example.foodviewer.mvp.model.requests.IIngredientDetails
import com.example.foodviewer.mvp.navigation.IAppScreens
import com.example.foodviewer.mvp.presenters.list.IIngredientsListItemView
import com.example.foodviewer.mvp.presenters.list.IIngredientsListPresenter
import com.example.foodviewer.mvp.view.IIngredientsListView
import com.example.foodviewer.ui.App
import com.example.foodviewer.ui.adapter.IngredientsInBarRVAdapterCheckable
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.BiConsumer
import io.reactivex.rxjava3.internal.operators.observable.ObservableFromIterable
import moxy.MvpPresenter
import java.util.*
import javax.inject.Inject
import javax.inject.Named


class IngredientsInBarListPresenter() : IngredientsListPresenter() {
    override fun loadIngredients() {
        val disposable = barProperties
            .allIngredientsInBar()
            .flatMap {
                val result = Observable.fromIterable(it)
                    .flatMap {
                        ingredientsApi.ingredientById(it).toObservable()
                    }
                result.collectInto(
                    mutableListOf<IngredientDetails>(),
                    BiConsumer { l, i -> l.add(i) })
            }
            .observeOn(uiSchelduer)
            .subscribe({ ingredients ->
                checkIngredientsInBarAndDisplay(ingredients.map { Ingredient(it.strIngredient) }
                    .sortedBy { it.name })
            },
                { error ->
                    viewState.displayError(
                        error.localizedMessage
                            ?: "Internal error occurred"
                    )
                })
        compositeDisposable.addAll(disposable)
    }

    override fun ingredientInBarChanged(ingredientChanged: IBarProperties.IngredientInBar) {
        super.ingredientInBarChanged(ingredientChanged)
    }
}