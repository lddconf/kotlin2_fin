package com.example.foodviewer.mvp.presenters

import com.example.foodviewer.mvp.model.entity.bar.IBarProperties
import com.example.foodviewer.mvp.model.entity.bar.IFavoriteCocktails
import com.example.foodviewer.mvp.model.requests.ICocktailDetails
import com.example.foodviewer.mvp.model.requests.IIngredientDetails
import com.example.foodviewer.mvp.navigation.IAppScreens
import com.example.foodviewer.mvp.presenters.list.IIngredientsListItemView
import com.example.foodviewer.mvp.presenters.list.IIngredientsListPresenter
import com.example.foodviewer.mvp.view.IIngredientsListView
import com.example.foodviewer.ui.App
import com.example.foodviewer.ui.adapter.IngredientsInBarRVAdapterCheckableInBar
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import moxy.MvpPresenter
import javax.inject.Inject
import javax.inject.Named


class IngredientsListPresenter() : MvpPresenter<IIngredientsListView>() {

    @Inject
    lateinit var api: ICocktailDetails

    @Inject
    lateinit var ingredientsApi: IIngredientDetails

    @Inject
    lateinit var barProperties: IBarProperties

    @Inject
    lateinit var favoriteCocktails: IFavoriteCocktails

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var screens: IAppScreens

    @Inject
    @field:Named("UIThread")
    lateinit var uiSchelduer: Scheduler

    private val compositeDisposable = CompositeDisposable()
    private var barChangedSubscription: Disposable? = null

    val ingredientDetailsPresenter = IngredientsDetailsPresenter().apply {
        App.instance.appComponent.inject(this)
    }

    data class Ingredient(
        val name: String
    )

    class IngredientsDetailsPresenter() : IIngredientsListPresenter {
        @Inject
        lateinit var ingredientsApi: IIngredientDetails

        var ingredients = mutableListOf<Pair<Ingredient, Boolean>>()

        override var itemClickListener: ((IIngredientsListItemView) -> Unit)? = null
        override var itemInBarCheckedListener: ((Int, Boolean) -> Unit)? = null

        override fun bindView(view: IngredientsInBarRVAdapterCheckableInBar.IngredientsInBarViewHolder) = with(view) {
            val ingredient = ingredients[view.pos]

            with(ingredient.first) {
                ingredientName(name)
                ingredientAlternatives("")
                loadIngredientView(ingredientsApi.ingredientSmallImageURLByName(name))
            }
            ingredientExists(ingredient.second)
        }

        override fun getCount(): Int = ingredients.size
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.initIngredientsList()

        loadIngredients()

        ingredientDetailsPresenter.itemInBarCheckedListener = { pos, state ->
            val ingredient = ingredientDetailsPresenter.ingredients[pos]
            barProperties.setupIngredientByName(ingredient.first.name, state)
                    .observeOn(uiSchelduer)
                    .subscribe({
                        ingredientDetailsPresenter.ingredients[pos] = ingredientDetailsPresenter.ingredients[pos].copy(second = state)
                        viewState.updateIngredientInList(pos)
                        if (state) viewState.showIngredientAddedNotification(ingredient.first.name) else viewState.showIngredientRemovedNotification(ingredient.first.name)
                    }, { error ->
                        viewState.updateIngredientInList(pos)
                        viewState.displayError(error.localizedMessage ?: "Internal error occurred")
                    })
        }


        ingredientDetailsPresenter.itemClickListener = { view ->
            val ingredient = ingredientDetailsPresenter.ingredients[view.pos]
            router.navigateTo(screens.ingredientDetails(ingredient.first.name))
        }

        barChangedSubscription = barProperties.ingredientInBarChangedByName()
                .observeOn(uiSchelduer)
                .subscribe {
                    ingredientInBarChanged(it)
                }
        compositeDisposable.add(barChangedSubscription)
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }

    fun backClick(): Boolean {
        router.exit()
        return true
    }

    private fun checkIngredientsInBarAndDisplay(ingredients: List<Ingredient>) {
        val ingredientNames = ingredients.map { it.name }
        barProperties.ingredientPresentByNames(ingredientNames).observeOn(uiSchelduer)
                .subscribe({ presents ->
                    displayCocktailIngredientsDetails(ingredients.zip(presents))
                },
                        { error ->
                            displayCocktailIngredientsDetails(
                                    ingredients.zip(
                                            MutableList(ingredients.size) { false }
                                    ))
                            viewState.displayError(
                                    error.localizedMessage
                                            ?: "Internal error occurred"
                            )
                        })
    }

    private fun loadIngredients() {
        val disposable1 = ingredientsApi
                .allIngredients()
                .observeOn(uiSchelduer)
                .subscribe({ ingredients ->
                    checkIngredientsInBarAndDisplay(ingredients.map { Ingredient(it.strIngredient1) }
                            .sortedBy { it.name })
                },
                        { error ->
                            viewState.displayError(
                                    error.localizedMessage
                                            ?: "Internal error occurred"
                            )
                        })
        compositeDisposable.addAll(disposable1)
    }

    private fun displayCocktailIngredientsDetails(
            ingredientsPresent: List<Pair<IngredientsListPresenter.Ingredient, Boolean>>
    ) {
        ingredientDetailsPresenter.ingredients.clear()
        ingredientDetailsPresenter.ingredients.addAll(ingredientsPresent)
        viewState.updateIngredientsList()
    }

    private fun ingredientInBarChanged(ingredientChanged: IBarProperties.IngredientInBar) {
        ingredientDetailsPresenter.ingredients.forEachIndexed { index, pair ->
            if (pair.first.name == ingredientChanged.name) {
                ingredientDetailsPresenter.ingredients[index] =
                        ingredientDetailsPresenter.ingredients[index].copy(second = ingredientChanged.present)
                viewState.updateIngredientsList()
                return
            }
        }
    }
}