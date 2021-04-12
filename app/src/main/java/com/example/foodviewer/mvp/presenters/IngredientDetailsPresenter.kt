package com.example.foodviewer.mvp.presenters

import com.example.foodviewer.mvp.model.entity.json.IngredientDetails
import com.example.foodviewer.mvp.model.requests.ICocktailDetails
import com.example.foodviewer.mvp.model.requests.IIngredientDetails
import com.example.foodviewer.mvp.navigation.IAppScreens
import com.example.foodviewer.mvp.view.IIngredientDetailsView
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.MvpPresenter


class IngredientDetailsPresenter(
    val ingredientName: String?,
    val cocktailApi: ICocktailDetails,
    val ingredientsApi: IIngredientDetails,
    val router: Router,
    val screens: IAppScreens,
    val uiSchelduer: Scheduler
) : MvpPresenter<IIngredientDetailsView>() {
    private var descriptionCollapsed = false
    private val compositeDisposable = CompositeDisposable()
/*
    val ingredientAmountPresenter = IngredientsAmountPresenter(ingredientsApi)

    class IngredientsAmountPresenter(val ingredientsApi: IIngredientDetails) : IIngredientsAmountListPresenter {
        var ingredients = mutableListOf<IngredientAmount>()
        override var itemClickListener: ((IIngredientsAmountItemView) -> Unit)? = null

        override fun bindView(view: IngredientsAmountRVAdapter.ViewHolder) = with(view) {
            val ingredient = ingredients[view.pos]
            ingredientName(ingredient.name)
            ingredientAlternatives( "")
            ingredientExists(true)
            ingredientAmount(ingredient.amount)
            loadIngredientView(ingredientsApi.ingredientSmallImageURLByName(ingredient.name))
        }

        override fun getCount(): Int = ingredients.size
    }
*/

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.initCocktailsWith()

        ingredientName?.let {
            loadIngredientDetails()
        }
/*
        ingredientAmountPresenter.itemClickListener = { view->
            val ingredient = ingredientAmountPresenter.ingredients[view.pos]
            router.navigateTo(screens.ingredientDetails(ingredient))
        }
*/

    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }

    fun ingredientDescriptionViewClicked() {
        descriptionCollapsed = descriptionCollapsed.not()
        viewState.collapseIngredientDescription(descriptionCollapsed)
        //Some stuff code
        ingredientName?.let {
            viewState.showIngredientAddedNotification(ingredientName)
        }
    }

    fun inBarState(state: Boolean) {
        viewState.inBarState(state)
        //Some stuff code
        ingredientName?.let {
            if ( state ) {
                viewState.showIngredientAddedNotification(ingredientName)
            } else {
                viewState.showIngredientRemovedNotification(ingredientName)
            }
        }
    }

    fun backClick(): Boolean {
        router.exit()
        return true
    }

    private fun loadIngredientDetails() {
        ingredientName?.apply {
            val disposable = ingredientsApi.ingredientByName(ingredientName)
                .observeOn(uiSchelduer)
                .subscribe({ ingredientDetails ->
                    displayIngredientDetails(ingredientDetails)
                },
                    { error ->
                        viewState.displayError(error.localizedMessage ?: "Internal error occurred")
                    })
            compositeDisposable.add(disposable)
        }
    }

    private fun displayIngredientDetails(ingredient: IngredientDetails) = with(ingredient) {
        viewState.ingredientName(ingredient.strIngredient)
        viewState.ingredientDescription(strDescription ?: "")
        viewState.inBarState(false) //Check from DB

        //Form and load ingredient list
        viewState.loadIngredientThumb(ingredientsApi.ingredientMediumImageURLByName(ingredient.strIngredient))

        //Request coctails with list
        /*
        ingredientAmountPresenter.ingredients.clear()
        ingredientAmountPresenter.ingredients.addAll(cocktailDetails.ingredients)
        viewState.updateCocktailsWithList()
         */
    }
}