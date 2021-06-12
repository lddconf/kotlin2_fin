package com.example.foodviewer.mvp.viewoptions

enum class SortType {
    SORT_BY_ALPHABETIC,
    SORT_BY_REVERS_ALPHABETIC
}

interface ISortableIngredients {
    fun setSortBy(type: SortType = SortType.SORT_BY_ALPHABETIC)
}