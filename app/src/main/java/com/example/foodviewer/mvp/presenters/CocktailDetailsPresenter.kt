package com.example.foodviewer.mvp.presenters

import android.graphics.Paint
import com.example.foodviewer.mvp.model.entity.CocktailDetails
import com.example.foodviewer.mvp.model.entity.IngredientAmount
import com.example.foodviewer.mvp.model.entity.bar.IBarProperties
import com.example.foodviewer.mvp.model.entity.bar.IFavoriteCocktails
import com.example.foodviewer.mvp.model.requests.ICocktailDetails
import com.example.foodviewer.mvp.model.requests.IIngredientDetails
import com.example.foodviewer.mvp.navigation.IAppScreens
import com.example.foodviewer.mvp.presenters.list.IIngredientsAmountItemView
import com.example.foodviewer.mvp.presenters.list.IIngredientsAmountListPresenter
import com.example.foodviewer.mvp.view.ICocktailDetailsView
import com.example.foodviewer.ui.App
import com.example.foodviewer.ui.adapter.IngredientsAmountRVAdapter
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import moxy.MvpPresenter
import javax.inject.Inject
import javax.inject.Named


class CocktailDetailsPresenter(
        val cocktailId: Long?
) : MvpPresenter<ICocktailDetailsView>() {

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



    private var recipeCollapsed = false
    private val compositeDisposable = CompositeDisposable()
    private var barChangedSubscription: Disposable? = null

    val ingredientAmountPresenter = IngredientsAmountPresenter().apply {
        App.instance.appComponent.inject(this)
    }

    class IngredientsAmountPresenter() : IIngredientsAmountListPresenter {
        @Inject
        lateinit var ingredientsApi: IIngredientDetails

        var ingredients = mutableListOf<Pair<IngredientAmount, Boolean>>()

        override var itemClickListener: ((IIngredientsAmountItemView) -> Unit)? = null

        override fun bindView(view: IngredientsAmountRVAdapter.ViewHolder) = with(view) {
            val ingredient = ingredients[view.pos]

            with(ingredient.first) {
                ingredientName(name)
                ingredientAlternatives("")
                ingredientAmount(amount)
                loadIngredientView(ingredientsApi.ingredientSmallImageURLByName(name))
            }
            ingredientExists(ingredient.second)
        }

        override fun getCount(): Int = ingredients.size
    }


    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.initIngredients()

        cocktailId?.let {
            loadCocktailDetails()
        }

        ingredientAmountPresenter.itemClickListener = { view ->
            val ingredient = ingredientAmountPresenter.ingredients[view.pos]
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

    fun recipeViewClicked() {
        recipeCollapsed = recipeCollapsed.not()
        viewState.collapseRecipeText(recipeCollapsed)
    }

    fun favoriteChanged(state: Boolean) {
        cocktailId?.let {
            val disposable = favoriteCocktails.favoriteCocktailByIdSet(it, state)
                    .observeOn(uiSchelduer)
                    .subscribe({
                        viewState.favoriteState(state)
                        if (state) viewState.showCocktailAddedToFavoriteNotification() else viewState.showCocktailRemovedFromFavoriteNotification()
                    },
                            { error ->
                                viewState.favoriteState(state.not()) //Roll back
                                viewState.displayError(error.message ?: "Internal error")
                            }
                    )
            compositeDisposable.add(disposable)
        }
    }

    fun backClick(): Boolean {
        router.exit()
        return true
    }

    private fun checkCocktailIngredientsInBar(cocktailDetails: CocktailDetails) {
        val ingredientNames = cocktailDetails.ingredients.map { it.name }
        barProperties.ingredientPresentByNames(ingredientNames).observeOn(uiSchelduer)
                .subscribe({
                    displayCocktailIngredientsDetails(cocktailDetails.ingredients.zip(it))
                },
                        { error ->
                            displayCocktailIngredientsDetails(
                                    cocktailDetails.ingredients.zip(
                                            MutableList(cocktailDetails.ingredients.size) { false }
                                    ))
                            viewState.displayError(
                                    error.localizedMessage
                                            ?: "Internal error occurred"
                            )
                        })
    }

    private fun loadCocktailDetails() {
        cocktailId?.apply {
            val disposable1 = api.cocktailById(cocktailId)
                    .observeOn(uiSchelduer)
                    .subscribe({ cocktailDetails ->
                        displayCocktailDetails(cocktailDetails)
                        checkCocktailIngredientsInBar(cocktailDetails)
                    },
                            { error ->
                                viewState.displayError(
                                        error.localizedMessage
                                                ?: "Internal error occurred"
                                )
                            })
            val disposable2 =
                    favoriteCocktails.favoriteCocktailById(cocktailId).observeOn(uiSchelduer)
                            .subscribe { result ->
                                viewState.favoriteState(result) //Check from DB
                            }
            compositeDisposable.addAll(disposable1, disposable2)
        }
    }

    private fun displayCocktailDetails(
            cocktailDetails: CocktailDetails
    ) = with(cocktailDetails) {
        viewState.cocktailName(strDrink ?: "")
        viewState.recipeText(strInstructions ?: "")
        viewState.loadCocktailThumb(strDrinkThumb ?: "")
    }

    private fun displayCocktailIngredientsDetails(
            ingredientsPresent: List<Pair<IngredientAmount, Boolean>>
    ) {
        ingredientAmountPresenter.ingredients.clear()
        ingredientAmountPresenter.ingredients.addAll(ingredientsPresent)
        viewState.updateIngredientList()
    }

    private fun ingredientInBarChanged(ingredientName: String) {
        val res = ingredientAmountPresenter.ingredients.find {
            ingredientName == it.first.name
        }?.apply {
            barProperties.ingredientPresentByName(ingredientName)
                    .observeOn(uiSchelduer)
                    .subscribe({ state ->
                        val index = ingredientAmountPresenter.ingredients.indexOf(this)
                        val element = ingredientAmountPresenter.ingredients[index]
                        ingredientAmountPresenter.ingredients[index] = element.copy(second = state)
                        viewState.notifyIngredientInBarChanged(index)
                    },
                            { error ->
                                viewState.displayError(
                                        error.localizedMessage
                                                ?: "Internal error occurred"
                                )
                            })
        }
    }
}