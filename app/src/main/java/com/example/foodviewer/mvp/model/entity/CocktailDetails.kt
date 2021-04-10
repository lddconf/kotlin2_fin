package com.example.foodviewer.mvp.model.entity

import android.os.Parcelable
import com.example.foodviewer.mvp.model.entity.json.CocktailDetailsJSON
import kotlinx.parcelize.Parcelize

@Parcelize
data class CocktailDetails(
    val id: String,
    val strDrink: String,
    val strDrinkAlternative: String?,
    val strTags: String?,
    val strVideo: String?,
    val strCategory: String,
    val strAlcoholic: String,
    val strGlass: String,
    val strInstructions: String?,
    val ingredients: List<Ingredient>,
    val strImageSource: String,
    val strImageAttribution: String,
    val strCreativeCommonsConfirmed: Boolean,
    val dateModified: String
) : Parcelable


fun CocktailDetailsJSON.toCocktailDetails(): CocktailDetails {
    return CocktailDetails(
        id = id,
        strDrink = strDrink,
        strDrinkAlternative = strDrinkAlternative,
        strTags = strTags,
        strVideo = strVideo,
        strCategory = strCategory,
        strAlcoholic = strAlcoholic,
        strGlass = strGlass,
        strInstructions = strInstructions,
        ingredients = listOf(
            Ingredient(strIngredient1 ?: "", strMeasure1 ?: ""),
            Ingredient(strIngredient2 ?: "", strMeasure2 ?: ""),
            Ingredient(strIngredient3 ?: "", strMeasure3 ?: ""),
            Ingredient(strIngredient4 ?: "", strMeasure4 ?: ""),
            Ingredient(strIngredient5 ?: "", strMeasure5 ?: ""),
            Ingredient(strIngredient6 ?: "", strMeasure6 ?: ""),
            Ingredient(strIngredient7 ?: "", strMeasure7 ?: ""),
            Ingredient(strIngredient8 ?: "", strMeasure8 ?: ""),
            Ingredient(strIngredient9 ?: "", strMeasure9 ?: ""),
            Ingredient(strIngredient10 ?: "", strMeasure10 ?: ""),
            Ingredient(strIngredient11 ?: "", strMeasure11 ?: ""),
            Ingredient(strIngredient12 ?: "", strMeasure12 ?: ""),
            Ingredient(strIngredient13 ?: "", strMeasure13 ?: ""),
            Ingredient(strIngredient14 ?: "", strMeasure14 ?: ""),
            Ingredient(strIngredient15 ?: "", strMeasure15 ?: "")
        ).filter{
            it.name.isNotEmpty()
        },
        strImageSource = strImageSource,
        strImageAttribution = strImageAttribution,
        strCreativeCommonsConfirmed = strCreativeCommonsConfirmed,
        dateModified = dateModified
    )
}