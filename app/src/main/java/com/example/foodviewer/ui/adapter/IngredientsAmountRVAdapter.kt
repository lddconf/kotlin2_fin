package com.example.foodviewer.ui.adapter

import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView
import com.example.foodviewer.R
import com.example.foodviewer.databinding.IngredientsAmountBinding
import com.example.foodviewer.mvp.presenters.list.IIngredientsAmountItemView
import com.example.foodviewer.mvp.presenters.list.IIngredientsAmountListPresenter
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.image.IImageLoader

class IngredientsAmountRVAdapter(
    val presenter: IIngredientsAmountListPresenter,
    val imageLoader: IImageLoader<ImageView>
) :
    RecyclerView.Adapter<IngredientsAmountRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            IngredientsAmountBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            itemView.setOnClickListener {
                presenter.itemClickListener?.invoke(this)
            }
        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        presenter.bindView(holder.apply {
            pos = position
        })
    }

    override fun getItemCount(): Int = presenter.getCount()

    inner class ViewHolder(val vb: IngredientsAmountBinding) : RecyclerView.ViewHolder(vb.root),
        IIngredientsAmountItemView {

        override var pos = -1

        override fun ingredientName(text: String) = with(vb) {
            ingredientName.text = text
        }

        override fun loadIngredientView(url: String) = with(vb) {
            imageLoader.load(url, ingredientImage)
        }

        override fun ingredientAlternatives(text: String) = with(vb) {
            ingredientAlternatives.text = text
        }

        override fun ingredientAmount(text: String) = with(vb) {
            ingredientAmount.text = text
        }

        override fun ingredientExists(state: Boolean) = with(vb) {
            ingredientExists.isChecked = state
            if (!state) {
                ingredientExists.visibility = View.INVISIBLE
                rootLayout.setBackgroundColor(Color.TRANSPARENT)
            } else {
                rootLayout.background = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    ingredientExists.context.resources.getColor(R.color.ingredient_exists)
                        .toDrawable()
                } else {
                    ingredientExists.context.resources.getColor(R.color.ingredient_exists, null)
                        .toDrawable()
                }
            }
        }
    }
}