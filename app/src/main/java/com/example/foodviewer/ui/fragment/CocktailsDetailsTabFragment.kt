package com.example.foodviewer.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.foodviewer.R
import com.example.foodviewer.databinding.FragmentCocktailsDetailsTabBinding
import com.example.foodviewer.databinding.FragmentCoctailDetailsBinding
import com.example.foodviewer.mvp.model.entity.bar.IFavoriteCocktails
import com.example.foodviewer.mvp.navigation.IAppScreens
import com.example.foodviewer.mvp.presenters.CocktailDetailsTabPresenter
import com.example.foodviewer.mvp.view.ICocktailsDetailsTabView
import com.example.foodviewer.ui.App
import com.example.foodviewer.ui.adapter.CocktailsTabSPAdapter
import com.example.foodviewer.ui.listeners.OnBackClickListener
import com.github.terrakok.cicerone.Router
import com.google.android.material.tabs.TabLayoutMediator
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Named

class CocktailsDetailsTabFragment : MvpAppCompatFragment(), OnBackClickListener, ICocktailsDetailsTabView {
    private var vb: FragmentCocktailsDetailsTabBinding? = null
    private var cocktailsTabSPAdapter : CocktailsTabSPAdapter? = null
    private var tabLayoutMediator: TabLayoutMediator? = null

    private val presenter by moxyPresenter {
        CocktailDetailsTabPresenter().apply { App.instance.appComponent.inject(this) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = FragmentCocktailsDetailsTabBinding.inflate(inflater, container, false).also {
        vb = it
    }.root

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
        cocktailsTabSPAdapter = CocktailsTabSPAdapter(this, presenter.tabsViewPresenter)
        vb?.pager?.adapter = cocktailsTabSPAdapter
        vb?.apply {
            tabLayoutMediator = TabLayoutMediator(tabLayout, pager) { tab, position ->
                presenter.tabsViewPresenter.fragmentFactory(position)?.also {
                    tab.text = it.tabName
                }
            }
            tabLayoutMediator?.attach()
        }
    }
/*
    override fun updateTabs() {
        cocktailsTabSPAdapter?.notifyDataSetChanged()
    }

    override fun updateTab(tabId: Int) {
        cocktailsTabSPAdapter?.notifyItemChanged(tabId)
    }
*/
    companion object {
        @JvmStatic
        fun newInstance() = CocktailsDetailsTabFragment()
    }
}