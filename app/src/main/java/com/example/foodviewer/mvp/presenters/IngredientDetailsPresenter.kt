package com.example.foodviewer.mvp.presenters

import com.example.foodviewer.mvp.model.entity.CocktailDetails
import com.example.foodviewer.mvp.model.entity.bar.IBarProperties
import com.example.foodviewer.mvp.model.entity.json.Cocktail
import com.example.foodviewer.mvp.model.entity.json.IngredientDetails
import com.example.foodviewer.mvp.model.requests.ICocktailDetails
import com.example.foodviewer.mvp.model.requests.IIngredientDetails
import com.example.foodviewer.mvp.navigation.IAppScreens
import com.example.foodviewer.mvp.presenters.list.ICocktailWithIngredientListPresenter
import com.example.foodviewer.mvp.presenters.list.ICocktailsWithIngredientView
import com.example.foodviewer.mvp.view.IIngredientDetailsView
import com.example.foodviewer.ui.App
import com.example.foodviewer.ui.adapter.CocktailWithIngredientRVAdapter
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.MvpPresenter
import javax.inject.Inject
import javax.inject.Named


class IngredientDetailsPresenter(
        val ingredientName: String?
) : MvpPresenter<IIngredientDetailsView>() {
    @Inject
    lateinit var cocktailApi: ICocktailDetails

    @Inject
    lateinit var ingredientsApi: IIngredientDetails

    @Inject
    lateinit var barProperties: IBarProperties

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var screens: IAppScreens

    @Inject
    @field:Named("UIThread")
    lateinit var uiSchelduer: Scheduler

    private var descriptionCollapsed = false
    private val compositeDisposable = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        ingredientName?.let {
            loadData()
        }
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }

    fun ingredientDescriptionViewClicked() {
        descriptionCollapsed = descriptionCollapsed.not()
        viewState.collapseIngredientDescription(descriptionCollapsed)

    }

    fun inBarState(state: Boolean) {
        viewState.inBarState(state)
        //Some stuff code
        ingredientName?.let {
            val disposable = barProperties.setupIngredientByName(ingredientName, state)
                    .observeOn(uiSchelduer)
                    .subscribe({
                        if (state) {
                            viewState.showIngredientAddedNotification(ingredientName)
                        } else {
                            viewState.showIngredientRemovedNotification(ingredientName)
                        }
                    }, { error ->
                        viewState.inBarState(state.not()) //Roll back
                        viewState.displayError(error.message ?: "Internal error")
                    })
            compositeDisposable.addAll(disposable)
        }
    }

    fun backClick(): Boolean {
        router.exit()
        return true
    }

    private fun loadData() {
        loadIngredientDetails()
        loadCocktailsWithIngredient()
    }

    private fun loadIngredientDetails() {
        ingredientName?.apply {
            val disposable1 = ingredientsApi.ingredientByName(ingredientName)
                    .observeOn(uiSchelduer)
                    .subscribe({ ingredientDetails ->
                        displayIngredientDetails(ingredientDetails)
                    },
                            { error ->
                                viewState.displayError(error.localizedMessage
                                        ?: "Internal error occurred")
                            })
            val disposable2 = barProperties.ingredientPresentByName(ingredientName)
                    .observeOn(uiSchelduer)
                    .subscribe { result ->
                        viewState.inBarState(result)
                    }
            compositeDisposable.addAll(disposable1, disposable2)
        }
    }

    private fun loadCocktailsWithIngredient() {
        ingredientName?.apply {
            val disposable = cocktailApi.cocktailsWithIngredient(ingredientName)
                    .observeOn(uiSchelduer)
                    .subscribe({ cocktails ->
                        //displayCocktailsWithIngredientsDetails(cocktails)
                        viewState.updateCocktailsWithList(cocktails)
                    },
                            { error ->
                                viewState.displayError(error.localizedMessage
                                        ?: "Internal error occurred")
                            })
            compositeDisposable.add(disposable)
        }
    }

    private fun displayIngredientDetails(ingredient: IngredientDetails) = with(ingredient) {
        viewState.ingredientName(ingredient.strIngredient)
        viewState.ingredientDescription(strDescription ?: "")
        //Form and load ingredient list
        viewState.loadIngredientThumb(ingredientsApi.ingredientMediumImageURLByName(ingredient.strIngredient))
    }
}