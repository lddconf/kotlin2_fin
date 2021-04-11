package com.example.foodviewer.mvp.model.entity

import android.os.Parcelable
import com.example.foodviewer.mvp.model.entity.json.CocktailDetailsJSON
import kotlinx.parcelize.Parcelize

@Parcelize
data class CocktailDetails(
    val id: Long,
    val strDrink: String?,
    val strDrinkAlternative: String?,
    val strTags: String?,
    val strVideo: String?,
    val strCategory: String,
    val strAlcoholic: String,
    val strGlass: String,
    val strInstructions: String?,
    val strDrinkThumb: String?,
    val ingredients: List<IngredientAmount>,
    val strImageAttribution: String?,
    val strCreativeCommonsConfirmed: Boolean,
    val dateModified: String
) : Parcelable


fun CocktailDetailsJSON.toCocktailDetails() = CocktailDetails(
    id = idDrink.toLong(),
    strDrink = strDrink,
    strDrinkAlternative = strDrinkAlternative,
    strTags = strTags,
    strVideo = strVideo,
    strCategory = strCategory,
    strAlcoholic = strAlcoholic,
    strGlass = strGlass,
    strInstructions = strInstructions,
    ingredients = listOf(
        IngredientAmount(strIngredient1 ?: "", strMeasure1 ?: ""),
        IngredientAmount(strIngredient2 ?: "", strMeasure2 ?: ""),
        IngredientAmount(strIngredient3 ?: "", strMeasure3 ?: ""),
        IngredientAmount(strIngredient4 ?: "", strMeasure4 ?: ""),
        IngredientAmount(strIngredient5 ?: "", strMeasure5 ?: ""),
        IngredientAmount(strIngredient6 ?: "", strMeasure6 ?: ""),
        IngredientAmount(strIngredient7 ?: "", strMeasure7 ?: ""),
        IngredientAmount(strIngredient8 ?: "", strMeasure8 ?: ""),
        IngredientAmount(strIngredient9 ?: "", strMeasure9 ?: ""),
        IngredientAmount(strIngredient10 ?: "", strMeasure10 ?: ""),
        IngredientAmount(strIngredient11 ?: "", strMeasure11 ?: ""),
        IngredientAmount(strIngredient12 ?: "", strMeasure12 ?: ""),
        IngredientAmount(strIngredient13 ?: "", strMeasure13 ?: ""),
        IngredientAmount(strIngredient14 ?: "", strMeasure14 ?: ""),
        IngredientAmount(strIngredient15 ?: "", strMeasure15 ?: "")
    ).filter {
        it.name.isNotEmpty()
    },
    strDrinkThumb = strDrinkThumb,
    strImageAttribution = strImageAttribution,
    strCreativeCommonsConfirmed = strCreativeCommonsConfirmed,
    dateModified = dateModified
)
