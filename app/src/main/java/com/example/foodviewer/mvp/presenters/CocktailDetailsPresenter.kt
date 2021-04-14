package com.example.foodviewer.mvp.presenters

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
import com.example.foodviewer.ui.adapter.IngredientsAmountRVAdapter
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.MvpPresenter


class CocktailDetailsPresenter(
        val cocktailId: Long?,
        val api: ICocktailDetails,
        val ingredientsApi: IIngredientDetails,
        val barProperties: IBarProperties,
        val favoriteCocktails: IFavoriteCocktails,
        val router: Router,
        val screens: IAppScreens,
        val uiSchelduer: Scheduler
) : MvpPresenter<ICocktailDetailsView>() {
    private var recipeCollapsed = false
    private val compositeDisposable = CompositeDisposable()


    val ingredientAmountPresenter =
            IngredientsAmountPresenter(ingredientsApi)

    class IngredientsAmountPresenter(
            val ingredientsApi: IIngredientDetails
    ) :
            IIngredientsAmountListPresenter {

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

    private fun loadCocktailDetails() {
        cocktailId?.apply {
            val disposable1 = api.cocktailById(cocktailId)
                    .observeOn(uiSchelduer)
                    .subscribe({ cocktailDetails ->
                        val ingredientNames = cocktailDetails.ingredients.map { it.name }
                        barProperties.ingredientPresentByNames(ingredientNames).observeOn(uiSchelduer)
                                .subscribe({
                                    displayCocktailDetails(cocktailDetails)
                                    displayIngredientDetails(cocktailDetails.ingredients.zip(it))

                                },
                                        { error ->
                                            displayCocktailDetails(
                                                    cocktailDetails)
                                            displayIngredientDetails(cocktailDetails.ingredients.zip(MutableList(cocktailDetails.ingredients.size) { false }))
                                            viewState.displayError(
                                                    error.localizedMessage
                                                            ?: "Internal error occurred"
                                            )
                                        })
                    },
                            { error ->
                                viewState.displayError(error.localizedMessage
                                        ?: "Internal error occurred")
                            })
            val disposable2 = favoriteCocktails.favoriteCocktailById(cocktailId).observeOn(uiSchelduer).subscribe { result->
                viewState.favoriteState(result) //Check from DB
            }
            compositeDisposable.addAll(disposable1,disposable2)
        }
    }

    private fun displayCocktailDetails(
            cocktailDetails: CocktailDetails
    ) = with(cocktailDetails) {
        viewState.cocktailName(strDrink ?: "")
        viewState.recipeText(strInstructions ?: "")
        viewState.loadCocktailThumb(strDrinkThumb ?: "")
    }


    private fun displayIngredientDetails(
            ingredientsPresent: List<Pair<IngredientAmount, Boolean>>
    ) {
        ingredientAmountPresenter.ingredients.clear()
        ingredientAmountPresenter.ingredients.addAll(ingredientsPresent)
        viewState.updateIngredientList()
    }

}