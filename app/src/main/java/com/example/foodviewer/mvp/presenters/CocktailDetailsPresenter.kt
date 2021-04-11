package com.example.foodviewer.mvp.presenters

import android.util.Log
import com.example.foodviewer.mvp.model.entity.CocktailDetails
import com.example.foodviewer.mvp.model.entity.IngredientAmount
import com.example.foodviewer.mvp.model.entity.json.Cocktail
import com.example.foodviewer.mvp.model.entity.json.CocktailsDetailsJSON
import com.example.foodviewer.mvp.model.entity.toCocktailDetails
import com.example.foodviewer.mvp.model.requests.ICocktailDetails
import com.example.foodviewer.mvp.model.requests.IIngredientDetails
import com.example.foodviewer.mvp.navigation.IAppScreens
import com.example.foodviewer.mvp.presenters.list.IIngredientsAmountItemView
import com.example.foodviewer.mvp.presenters.list.IIngredientsAmountListPresenter
import com.example.foodviewer.mvp.view.ICocktailDetailsView
import com.example.foodviewer.ui.adapter.IngredientsAmountRVAdapter
import com.github.terrakok.cicerone.Router
import com.google.gson.GsonBuilder
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.MvpPresenter


class CocktailDetailsPresenter(
    val cocktail: Cocktail?,
    val api: ICocktailDetails,
    val ingredientsApi: IIngredientDetails,
    val router: Router,
    val screens: IAppScreens,
    val uiSchelduer: Scheduler
) : MvpPresenter<ICocktailDetailsView>() {
    private var recipeCollapsed = false
    private val compositeDisposable = CompositeDisposable()


    val ingredientAmountPresenter = IngredientsAmountPresenter(ingredientsApi)

    class IngredientsAmountPresenter(val ingredientsApi: IIngredientDetails) : IIngredientsAmountListPresenter {
        var ingredients = mutableListOf<IngredientAmount>()
        override var itemClickListener: ((IIngredientsAmountItemView) -> Unit)? = null

        override fun bindView(view: IngredientsAmountRVAdapter.ViewHolder) = with(view) {
            val ingredient = ingredients[view.pos]
            ingredientName(ingredient.name)
            ingredientAlternatives("")
            ingredientExists(true)
            ingredientAmount(ingredient.amount)
            loadIngredientView(ingredientsApi.ingredientSmallImageURLByName(ingredient.name))
        }

        override fun getCount(): Int = ingredients.size
    }


    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.initIngredients()

        cocktail?.let {
            loadCocktailDetails()
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

    fun recipeViewClicked() {
        recipeCollapsed = recipeCollapsed.not()
        viewState.collapseRecipeText(recipeCollapsed)
    }

    fun favoriteChanged(state: Boolean) {
        viewState.favoriteState(state)
    }

    fun backClick(): Boolean {
        router.exit()
        return true
    }

    private fun loadCocktailDetails() {
        cocktail?.apply {
            //displayCocktailDetails(test())
            val disposable = api.cocktailById(cocktail.idDrink)
                .observeOn(uiSchelduer)
                .subscribe({ cocktailDetails ->
                    displayCocktailDetails(cocktailDetails)
                },
                    { error ->
                        viewState.displayError(error.localizedMessage ?: "Internal error occurred")
                    })
            compositeDisposable.add(disposable)

        }
    }

    private fun displayCocktailDetails(cocktailDetails: CocktailDetails) = with(cocktailDetails) {
        viewState.cocktailName(strDrink ?: "")
        viewState.recipeText(strInstructions ?: "")
        viewState.favoriteState(false) //Check from DB
        viewState.loadCocktailThumb(strDrinkThumb ?: "")

        ingredientAmountPresenter.ingredients.clear()
        ingredientAmountPresenter.ingredients.addAll(cocktailDetails.ingredients)
        viewState.updateIngredientList()
    }


/*
    private fun test() :CocktailDetails {
        val builder = GsonBuilder()
        val gson = builder.create()
        val text = "{\n" +
                "    \"drinks\": [\n" +
                "        {\n" +
                "            \"idDrink\": \"16108\",\n" +
                "            \"strDrink\": \"9 1/2 Weeks\",\n" +
                "            \"strDrinkAlternate\": null,\n" +
                "            \"strTags\": null,\n" +
                "            \"strVideo\": null,\n" +
                "            \"strCategory\": \"Cocktail\",\n" +
                "            \"strIBA\": null,\n" +
                "            \"strAlcoholic\": \"Alcoholic\",\n" +
                "            \"strGlass\": \"Cocktail glass\",\n" +
                "            \"strInstructions\": \"Combine all ingredients in glass mixer. Chill and strain into Cocktail glass. Garnish with sliced strawberry.\",\n" +
                "            \"strInstructionsES\": null,\n" +
                "            \"strInstructionsDE\": \"Alle Zutaten in einem Glasmischer vermengen. Abkühlen lassen und in das Cocktailglas abseihen. Mit Erdbeerscheiben garnieren.\",\n" +
                "            \"strInstructionsFR\": null,\n" +
                "            \"strInstructionsIT\": \"Unisci tutti gli ingredienti in una planetaria.\\r\\nRaffredda e versa in un bicchiere da cocktail.\\r\\nGuarnire con la fragola a fette.\",\n" +
                "            \"strInstructionsZH-HANS\": null,\n" +
                "            \"strInstructionsZH-HANT\": null,\n" +
                "            \"strDrinkThumb\": \"https://www.thecocktaildb.com/images/media/drink/xvwusr1472669302.jpg\",\n" +
                "            \"strIngredient1\": \"Absolut Citron\",\n" +
                "            \"strIngredient2\": \"Orange Curacao\",\n" +
                "            \"strIngredient3\": \"Strawberry liqueur\",\n" +
                "            \"strIngredient4\": \"Orange juice\",\n" +
                "            \"strIngredient5\": null,\n" +
                "            \"strIngredient6\": null,\n" +
                "            \"strIngredient7\": null,\n" +
                "            \"strIngredient8\": null,\n" +
                "            \"strIngredient9\": null,\n" +
                "            \"strIngredient10\": null,\n" +
                "            \"strIngredient11\": null,\n" +
                "            \"strIngredient12\": null,\n" +
                "            \"strIngredient13\": null,\n" +
                "            \"strIngredient14\": null,\n" +
                "            \"strIngredient15\": null,\n" +
                "            \"strMeasure1\": \"2 oz \",\n" +
                "            \"strMeasure2\": \"1/2 oz \",\n" +
                "            \"strMeasure3\": \"1 splash \",\n" +
                "            \"strMeasure4\": \"1 oz \",\n" +
                "            \"strMeasure5\": null,\n" +
                "            \"strMeasure6\": null,\n" +
                "            \"strMeasure7\": null,\n" +
                "            \"strMeasure8\": null,\n" +
                "            \"strMeasure9\": null,\n" +
                "            \"strMeasure10\": null,\n" +
                "            \"strMeasure11\": null,\n" +
                "            \"strMeasure12\": null,\n" +
                "            \"strMeasure13\": null,\n" +
                "            \"strMeasure14\": null,\n" +
                "            \"strMeasure15\": null,\n" +
                "            \"strImageSource\": null,\n" +
                "            \"strImageAttribution\": null,\n" +
                "            \"strCreativeCommonsConfirmed\": \"No\",\n" +
                "            \"dateModified\": \"2016-08-31 19:48:22\"\n" +
                "        }\n" +
                "    ]\n" +
                "}"
        val testmodel = gson.fromJson(text, CocktailsDetailsJSON::class.java)
        return testmodel.cocktails.first().toCocktailDetails()
    }
*/

}