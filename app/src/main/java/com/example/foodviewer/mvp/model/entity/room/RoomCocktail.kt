package com.example.foodviewer.mvp.model.entity.room

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

data class RoomCocktail(
    @Embedded
    val roomCocktailRecord: RoomCocktailRecord,

    @Relation(parentColumn = "strAlcoholicId", entityColumn = "id")
    val alcoholic: RoomCocktailAlcoholic,

    @Relation(parentColumn = "strAlcoholicId", entityColumn = "id")
    val glass: RoomCocktailGlass,

    @Relation(parentColumn = "strCategoryId", entityColumn = "id")
    val category: RoomCocktailCategory
)