package com.example.foodviewer.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodviewer.R
import com.example.foodviewer.databinding.FragmentIngredientsListBinding
import com.example.foodviewer.mvp.presenters.IngredientsListPresenter
import com.example.foodviewer.mvp.view.IIngredientsListView
import com.example.foodviewer.ui.App
import com.example.foodviewer.ui.adapter.IngredientsInBarRVAdapterCheckableInBar
import com.example.foodviewer.ui.listeners.OnBackClickListener
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.image.IImageLoader
import javax.inject.Inject

open class IngredientsListFragment() : MvpAppCompatFragment(), IIngredientsListView,
        OnBackClickListener {
    protected var ingredientsListBinding: FragmentIngredientsListBinding? = null
    protected var adapter: IngredientsInBarRVAdapterCheckableInBar? = null

    @Inject
    lateinit var imageLoader: IImageLoader<ImageView>


    protected val presenter by moxyPresenter {
        createPresenter().apply { App.instance.appComponent.inject(this) }
    }

    protected open fun createPresenter() : IngredientsListPresenter {
        return IngredientsListPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.instance.appComponent.inject(this)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = FragmentIngredientsListBinding.inflate(inflater, container, false).also {
        ingredientsListBinding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        ingredientsListBinding = null
    }

    companion object {
        private const val COCKTAIL_DETAILS_KEY = "IngredientsListFragment.IngredientsListFragment"

        @JvmStatic
        fun newInstance() = IngredientsListFragment()
    }

    override fun onBackClicked(): Boolean = presenter.backClick()

    override fun initIngredientsList() {
        ingredientsListBinding?.ingredientsList?.layoutManager =
                LinearLayoutManager(requireContext())
        adapter = IngredientsInBarRVAdapterCheckableInBar(presenter.ingredientDetailsPresenter, imageLoader)
        ingredientsListBinding?.ingredientsList?.adapter = adapter
    }

    override fun updateIngredientsList() {
        ingredientsListBinding?.ingredientsList?.adapter?.notifyDataSetChanged()
    }

    override fun updateIngredientInList(index: Int) {
        ingredientsListBinding?.ingredientsList?.adapter?.notifyItemChanged(index)
    }

    override fun displayError(description: String) {
        Toast.makeText(requireContext(), description, Toast.LENGTH_SHORT).show()
    }

    override fun showIngredientAddedNotification(ingredientName: String) {
        Toast.makeText(
                requireContext(),
                requireContext().getString(R.string.ingredient_added_to_bar) + "$ingredientName",
                Toast.LENGTH_SHORT
        ).show()
    }

    override fun showIngredientRemovedNotification(ingredientName: String) {
        Toast.makeText(
                requireContext(),
                requireContext().getString(R.string.ingredient_removed_from__bar) + "$ingredientName",
                Toast.LENGTH_SHORT
        ).show()
    }
}