package com.example.foodviewer.mvp.model.entity.json

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryType(
    val strCategory: String
) : Parcelable