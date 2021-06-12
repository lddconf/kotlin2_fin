package com.example.foodviewer.mvp.presenters.list

import com.example.foodviewer.ui.adapter.IngredientsInBarRVAdapterCheckable

interface IIngredientsListPresenter {
    var itemClickListener: ((IIngredientsListItemView) -> Unit)?
    var itemInBarCheckedListener: ((Int, Boolean) -> Unit)?

    fun bindView(view: IngredientsInBarRVAdapterCheckable.IngredientsInBarViewHolder)
    fun getCount(): Int
}