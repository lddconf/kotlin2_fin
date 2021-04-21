package com.example.foodviewer.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import com.example.foodviewer.R
import com.example.foodviewer.databinding.FragmentAppAboutBinding
import com.example.foodviewer.mvp.fragments.IFragmentAppRestoreRequestListener
import com.example.foodviewer.mvp.fragments.IFragmentStartedListener
import com.example.foodviewer.mvp.presenters.FragmentAppAboutPresenter
import com.example.foodviewer.mvp.view.IAppAboutView
import com.example.foodviewer.ui.App
import com.example.foodviewer.ui.listeners.OnBackClickListener
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class AppAboutFragment : MvpAppCompatFragment(), IAppAboutView, OnBackClickListener {
    companion object {
        fun newInstance(): AppAboutFragment {
            return AppAboutFragment()
        }
    }

    private var appAboutBinding: FragmentAppAboutBinding? = null

    private val presenter by moxyPresenter {
        FragmentAppAboutPresenter().apply { App.instance.appComponent.inject(this) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentAppAboutBinding.inflate(inflater, container, false).also {
        appAboutBinding = it
    }.root

    override fun onStart() {
        super.onStart()
        notifyParentFragmentStarted()
    }

    override fun initAppBar() {
        appBarRestore()
        setHasOptionsMenu(true) //use appbar actions
        val bar: View? = activity?.findViewById(R.id.main_toolbar)
        bar?.let {
            if (bar is Toolbar) {
                bar.title = null
                bar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
                bar.setNavigationOnClickListener {
                    presenter.backClick()
                }
            }
        }
    }

    private fun appBarRestore() {
        val parent = activity
        if (parent is IFragmentAppRestoreRequestListener) {
            parent.restoreRequest()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        appAboutBinding = null
    }

    override fun onBackClicked(): Boolean = presenter.backClick()


    private fun notifyParentFragmentStarted() {
        val parent = activity
        if ( parent is IFragmentStartedListener) {
            parent.fragmentStarted()
        }
    }
}
