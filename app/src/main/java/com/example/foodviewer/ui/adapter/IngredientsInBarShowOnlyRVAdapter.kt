package com.example.foodviewer.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.foodviewer.databinding.IngredientsListItemBinding
import com.example.foodviewer.mvp.presenters.IngredientsListPresenter
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.image.IImageLoader


class IngredientsInBarShowOnlyRVAdapter(
        presenter: IngredientsListPresenter.IngredientsDetailsPresenter,
        imageLoader: IImageLoader<ImageView>
) : IngredientsInBarRVAdapterCheckable(presenter, imageLoader) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsInBarViewHolder =
            IngredientsInBarViewHolder(
                    IngredientsListItemBinding.inflate(
                            LayoutInflater.from(parent.context),
                            parent,
                            false
                    ).apply {
                        this.ingredientInBar.visibility = View.INVISIBLE
                    }
            ).apply {
                itemView.setOnClickListener {
                    presenter.itemClickListener?.invoke(this)
                }
                this.vb.ingredientInBar.setOnClickListener {
                    presenter.itemInBarCheckedListener?.invoke(pos, this.vb.ingredientInBar.isChecked)
                }
            }
}