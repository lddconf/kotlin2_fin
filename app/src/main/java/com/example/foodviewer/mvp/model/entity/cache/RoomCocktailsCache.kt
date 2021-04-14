package com.example.foodviewer.mvp.model.entity.cache

import com.example.foodviewer.mvp.model.entity.CocktailDetails
import com.example.foodviewer.mvp.model.entity.room.db.Database
import com.example.foodviewer.mvp.model.entity.room.toRoomCocktailWithRecipe

class RoomCocktailsCache(val db: Database) : ICocktailsCache {
    override fun put(cocktailsDetails: List<CocktailDetails>) {
        val roomCocktailsWithRecipe = cocktailsDetails.map {
            it.toRoomCocktailWithRecipe()
        }
        db.cocktailDao.insertCocktail(roomCocktailsWithRecipe)
    }
}