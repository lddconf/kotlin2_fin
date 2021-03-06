package com.example.foodviewer.ui.fragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.foodviewer.R
import com.example.foodviewer.databinding.FragmentIngredientDetailsBinding
import com.example.foodviewer.databinding.FragmentIngredientsDetailsTabBinding
import com.example.foodviewer.mvp.fragments.IFragmentAppRestoreRequestListener
import com.example.foodviewer.mvp.fragments.IFragmentStartedListener
import com.example.foodviewer.mvp.presenters.IngredientsDetailsTabPresenter
import com.example.foodviewer.mvp.view.IIngredientsDetailsTabView
import com.example.foodviewer.ui.App
import com.example.foodviewer.ui.adapter.tab.SimpleTabSPAdapter
import com.example.foodviewer.ui.listeners.OnBackClickListener
import com.google.android.material.tabs.TabLayoutMediator
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class IngredientsDetailsTabFragment : MvpAppCompatFragment(), OnBackClickListener,
    IIngredientsDetailsTabView {
    private var vb: FragmentIngredientsDetailsTabBinding? = null
    private var ingredientsTabSPAdapter: SimpleTabSPAdapter? = null
    private var tabLayoutMediator: TabLayoutMediator? = null

    private val presenter by moxyPresenter {
        IngredientsDetailsTabPresenter().apply { App.instance.appComponent.inject(this) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentIngredientsDetailsTabBinding.inflate(inflater, container, false).also {
        vb = it
    }.root

    override fun initAppBar() {
        appBarRestore()
        setHasOptionsMenu(true) //use appbar actions
        val bar: View? = activity?.findViewById(R.id.main_toolbar)
        bar?.let {
            if (bar is Toolbar) {
                bar.title = null
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        //inflater.inflate(R.menu.cocktails_search_menu, menu)
    }

    override fun onStart() {
        super.onStart()
        notifyParentFragmentStarted()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_search -> super.onOptionsItemSelected(item)
        else -> super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        vb = null
    }

    override fun onBackClicked(): Boolean = presenter.backClick()

    override fun displayError(description: String) {
        Toast.makeText(requireContext(), description, Toast.LENGTH_SHORT).show()
    }

    override fun initTabs() {
        tabLayoutMediator?.detach()
        ingredientsTabSPAdapter = SimpleTabSPAdapter(this, presenter.tabsViewPresenter)
        vb?.igdPager?.adapter = ingredientsTabSPAdapter
        vb?.apply {
            tabLayoutMediator = TabLayoutMediator(igdTabLayout, igdPager) { tab, position ->
                presenter.tabsViewPresenter.fragmentFactory(position)?.also {
                    tab.text = it.tabName
                }
            }
            tabLayoutMediator?.attach()
        }
    }

    private fun appBarRestore() {
        val parent = activity
        if ( parent is IFragmentAppRestoreRequestListener) {
            parent.restoreRequest()
        }
    }

    private fun notifyParentFragmentStarted() {
        val parent = activity
        if ( parent is IFragmentStartedListener) {
            parent.fragmentStarted()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = IngredientsDetailsTabFragment()
    }
}