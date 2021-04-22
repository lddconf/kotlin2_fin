package com.example.foodviewer.mvp.model.entity.cache

import com.example.foodviewer.mvp.model.entity.json.IngredientDetails
import com.example.foodviewer.mvp.model.entity.room.RoomIngredient
import com.example.foodviewer.mvp.model.entity.room.RoomIngredientRecord
import com.example.foodviewer.mvp.model.entity.room.db.Database
import com.example.foodviewer.mvp.model.entity.room.toRoomIngredient

class RoomIngredientsCache(val db: Database) : IIngredientsCache {
    override fun put(ingredients: List<IngredientDetails>) {
        val roomIngredients = ingredients.map {
            it.toRoomIngredient()
        }
        db.ingredientsDao.insert(roomIngredients)
    }



}