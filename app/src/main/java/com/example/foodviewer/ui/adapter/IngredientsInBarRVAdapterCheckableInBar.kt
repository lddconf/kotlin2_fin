package com.example.foodviewer.ui.adapter

import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView
import com.example.foodviewer.R
import com.example.foodviewer.databinding.IngredientsListItemBinding
import com.example.foodviewer.mvp.presenters.IngredientsListPresenter
import com.example.foodviewer.mvp.presenters.list.IIngredientsListItemView
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.image.IImageLoader



open class IngredientsInBarRVAdapterCheckableInBar(
    val presenter: IngredientsListPresenter.IngredientsDetailsPresenter,
    val imageLoader: IImageLoader<ImageView>
) :
    RecyclerView.Adapter<IngredientsInBarRVAdapterCheckableInBar.IngredientsInBarViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsInBarViewHolder =
        IngredientsInBarViewHolder(
            IngredientsListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            itemView.setOnClickListener {
                presenter.itemClickListener?.invoke(this)
            }
            this.vb.ingredientInBar.setOnClickListener {
                presenter.itemInBarCheckedListener?.invoke(pos, this.vb.ingredientInBar.isChecked)
            }
        }

    override fun onBindViewHolder(holder: IngredientsInBarViewHolder, position: Int) {
        presenter.bindView(holder.apply {
            pos = position
        })
    }

    override fun getItemCount(): Int = presenter.getCount()

    inner class IngredientsInBarViewHolder(val vb: IngredientsListItemBinding) :
            RecyclerView.ViewHolder(vb.root),
            IIngredientsListItemView {

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

        override fun ingredientExists(state: Boolean) = with(vb) {
            ingredientInBar.isChecked = state
            if (!state) {
                rootLayout.setBackgroundColor(Color.TRANSPARENT)
            } else {
                rootLayout.background = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    ingredientInBar.context.resources.getColor(R.color.ingredient_exists)
                            .toDrawable()
                } else {
                    ingredientInBar.context.resources.getColor(R.color.ingredient_exists, null)
                            .toDrawable()
                }
            }
        }
    }

}