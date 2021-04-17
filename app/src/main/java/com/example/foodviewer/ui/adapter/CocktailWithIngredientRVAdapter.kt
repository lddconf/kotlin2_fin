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
import com.example.foodviewer.databinding.CocktailWithIngredientBinding
import com.example.foodviewer.mvp.presenters.list.ICocktailWithIngredientListPresenter
import com.example.foodviewer.mvp.presenters.list.ICocktailsWithIngredientView
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.image.IImageLoader

class CocktailWithIngredientRVAdapter(
        val presenter: ICocktailWithIngredientListPresenter,
        val imageLoader: IImageLoader<ImageView>
) : RecyclerView.Adapter<CocktailWithIngredientRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(
                    CocktailWithIngredientBinding.inflate(
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

    inner class ViewHolder(val vb: CocktailWithIngredientBinding) :
            RecyclerView.ViewHolder(vb.root),
            ICocktailsWithIngredientView {

        override var pos = -1

        override fun cocktailName(text: String) = with(vb) {
            cocktailName.text = text
        }

        override fun loadCocktailView(url: String) = with(vb) {
            imageLoader.load(url, cocktailImage)
        }

        override fun ingredients(ingredients: List<String>, required: Boolean) = with(vb) {
            cocktailAvailable.isChecked = required.not()
            val sb = StringBuffer(100)
            if (ingredients.isNotEmpty()) {
                for (ingredient in ingredients) {
                    sb.append("$ingredient,")
                }
                sb.deleteCharAt(sb.length - 1)
            }
            if (required) {
                if (ingredients.size > 2) {
                    //Reset representation
                    sb.setLength(0)
                    sb.append("${ingredients.size} " + cocktailIngredients.context.getString(R.string.ingredients))
                }
                sb.insert(0, cocktailIngredients.context.getString(R.string.require_ingredients))
                cocktailAvailable.visibility = View.INVISIBLE
                rootLayout.setBackgroundColor(Color.TRANSPARENT)
            } else {
                cocktailAvailable.visibility = View.VISIBLE
                rootLayout.background = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    cocktailAvailable.context.resources.getColor(R.color.ingredient_exists)
                            .toDrawable()
                } else {
                    cocktailAvailable.context.resources.getColor(R.color.ingredient_exists, null)
                            .toDrawable()
                }
            }
            cocktailIngredients.text = sb.toString()
        }
    }
}