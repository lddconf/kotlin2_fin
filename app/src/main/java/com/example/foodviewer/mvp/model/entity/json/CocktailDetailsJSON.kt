package com.example.foodviewer.mvp.model.entity.json

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class CocktailDetailsJSON(
    val id : String,
    val strDrink : String,
    val strDrinkAlternative : String?,
    val strTags : String?,
    val strVideo : String?,
    val strCategory: String,
    val strIBA : String?,
    val strAlcoholic : String,
    val strGlass : String,
    val strInstructions : String?,
    val strInstructionsES : String?,
    val strInstructionsDE : String?,
    val strInstructionsFR : String?,
    val strInstructionsIT : String?,
    @SerializedName(value = "strInstructionsZH-HANS")
    val strInstructionsZHHANS : String?,
    @SerializedName(value = "strInstructionsZH-HANT")
    val strInstructionsZHHANT : String?,
    val strDrinkThumb : String?,
    val strIngredient1 : String?,
    val strIngredient2 : String?,
    val strIngredient3 : String?,
    val strIngredient4 : String?,
    val strIngredient5 : String?,
    val strIngredient6 : String?,
    val strIngredient7 : String?,
    val strIngredient8 : String?,
    val strIngredient9 : String?,
    val strIngredient10 : String?,
    val strIngredient11 : String?,
    val strIngredient12 : String?,
    val strIngredient13 : String?,
    val strIngredient14 : String?,
    val strIngredient15 : String?,
    val strMeasure1 : String?,
    val strMeasure2 : String?,
    val strMeasure3 : String?,
    val strMeasure4 : String?,
    val strMeasure5 : String?,
    val strMeasure6 : String?,
    val strMeasure7 : String?,
    val strMeasure8 : String?,
    val strMeasure9 : String?,
    val strMeasure10: String?,
    val strMeasure11: String?,
    val strMeasure12: String?,
    val strMeasure13: String?,
    val strMeasure14: String?,
    val strMeasure15: String?,
    val strImageSource : String,
    val strImageAttribution : String,
    val strCreativeCommonsConfirmed: Boolean,
    val dateModified : String
) : Parcelable