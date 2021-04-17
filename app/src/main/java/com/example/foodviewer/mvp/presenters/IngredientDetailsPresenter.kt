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

    val cocktailWithPresenter = CocktailWithIngredientPresenter().apply {
        App.instance.appComponent.inject(this)
    }


    data class CocktailWithIngredientsInBar(
            val id: Long,
            val name: String?,
            val drinkThumb: String?,
            val ingredients: List<IBarProperties.IngredientInBar>
    )

    class CocktailWithIngredientPresenter() :
            ICocktailWithIngredientListPresenter {
        @Inject
        lateinit var cocktailApi: ICocktailDetails

        var cocktailsBrief = mutableListOf<CocktailWithIngredientsInBar>()
        override var itemClickListener: ((ICocktailsWithIngredientView) -> Unit)? = null

        override fun bindView(view: CocktailWithIngredientRVAdapter.ViewHolder): Unit = with(view) {
            val cocktail = cocktailsBrief[view.pos]
            cocktailName(cocktail.name ?: "")
            cocktail.drinkThumb?.let {
                loadCocktailView(cocktailApi.cocktailSmallImageURLByBaseURL(it))
            }

            val required = cocktail.ingredients.any {
                it.present.not()
            }
            ingredients(cocktail.ingredients.filter {
                it.present != required
            }.map { it.name }, required)
        }

        override fun getCount(): Int = cocktailsBrief.size
    }


    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.initCocktailsWith()

        ingredientName?.let {
            loadData()
        }

        //OnClickHandle
        cocktailWithPresenter.itemClickListener = { view ->
            val cocktail = cocktailWithPresenter.cocktailsBrief[view.pos]
            router.navigateTo(screens.cocktailDetails(cocktail.id))
        }

        val subscribe = barProperties.ingredientInBarChangedByName()
                .observeOn(uiSchelduer)
                .subscribe { ingredientChanged ->
                    ingredientsInBarChanged(ingredientChanged)
                }
        compositeDisposable.add(subscribe)
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
                        loadCocktailWithIngredientsInBar(cocktails)
                    },
                            { error ->
                                viewState.displayError(error.localizedMessage
                                        ?: "Internal error occurred")
                            })
            compositeDisposable.add(disposable)
        }
    }

    private fun loadCocktailWithIngredientsInBar(cocktails: List<Cocktail>) {
        lateinit var request: Observable<CocktailDetails>
        var cocktailsWithIngredient = mutableListOf<CocktailWithIngredientsInBar>()

        cocktails.forEachIndexed { index, cocktail ->
            if (index == 0) {
                request = cocktailApi.cocktailById(cocktail.idDrink).toObservable()
            } else {
                request = request.mergeWith(cocktailApi.cocktailById(cocktail.idDrink).toObservable())
            }
        }
        request.flatMap { cocktail ->
            barProperties.ingredientPresentByNames(cocktail.ingredients.map { it.name }).map { presents ->
                CocktailWithIngredientsInBar(
                        id = cocktail.id,
                        name = cocktail.strDrink,
                        drinkThumb = cocktail.strDrinkThumb,
                        ingredients = cocktail.ingredients.zip(presents).map {
                            IBarProperties.IngredientInBar(it.first.name, it.second)
                        })
            }.toObservable()
        }.observeOn(uiSchelduer).subscribe({ cocktail -> cocktailsWithIngredient.add(cocktail) },
                { error ->
                    viewState.displayError(error.localizedMessage ?: "Internal error occurred")
                },
                {
                    displayCocktailsWithIngredientsDetails(cocktailsWithIngredient)
                }
        )
    }


    private fun displayIngredientDetails(ingredient: IngredientDetails) = with(ingredient) {
        viewState.ingredientName(ingredient.strIngredient)
        viewState.ingredientDescription(strDescription ?: "")
        //Form and load ingredient list
        viewState.loadIngredientThumb(ingredientsApi.ingredientMediumImageURLByName(ingredient.strIngredient))
    }

    private fun displayCocktailsWithIngredientsDetails(cocktailsWithIngredient: List<CocktailWithIngredientsInBar>) {
        cocktailWithPresenter.cocktailsBrief.clear()
        cocktailWithPresenter.cocktailsBrief.addAll(cocktailsWithIngredient)
        viewState.updateCocktailsWithList()
    }

    private fun ingredientsInBarChanged(ingredient: IBarProperties.IngredientInBar) {
        var updateView: Boolean = false
        cocktailWithPresenter.cocktailsBrief.forEach { cocktail ->
            cocktail.ingredients.forEach { oldIngredient ->
                if ((oldIngredient.name == ingredient.name) && (oldIngredient.present != ingredient.present)) {
                    updateView = true
                    oldIngredient.present = ingredient.present
                    return@forEach
                }
            }
        }
        if (updateView) {
            viewState.updateCocktailsWithList()
        }
    }
}