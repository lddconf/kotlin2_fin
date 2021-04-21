package com.example.foodviewer.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.navigation.ui.AppBarConfiguration
import com.example.foodviewer.R
import com.example.foodviewer.databinding.ActivityMainNavDrawerBinding
import com.example.foodviewer.mvp.fragments.IFragmentAppRestoreRequestListener
import com.example.foodviewer.mvp.presenters.MainPresenter
import com.example.foodviewer.mvp.view.IMainActivityView
import com.example.foodviewer.ui.App
import com.example.foodviewer.ui.listeners.OnBackClickListener
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import javax.inject.Inject

class MainActivity : MvpAppCompatActivity(), IMainActivityView, IFragmentAppRestoreRequestListener {
    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    companion object {
        fun getIntent(context: Context) = Intent(context, MainActivity::class.java)
    }

    private var vb: ActivityMainNavDrawerBinding? = null
    private val navigator = AppNavigator(this, R.id.container)

    private val presenter by moxyPresenter {
        MainPresenter().apply {
            App.instance.appComponent.inject(this)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.instance.appComponent.inject(this)
        vb = ActivityMainNavDrawerBinding.inflate(layoutInflater)
        setContentView(vb?.root)
        setSupportActionBar(vb?.mainActivity?.mainToolbar)
    }

    override fun initAppBar() {
        supportActionBar?.title = getString(R.string.app_name)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onBackPressed() {
        supportFragmentManager.fragments.forEach {
            if (it is OnBackClickListener && it.onBackClicked()) {
                val fragmentsCount = supportFragmentManager.fragments.size
                if ( fragmentsCount < 1) {
                    return@forEach
                } else return
            }
        }
        presenter.backClicked()
    }

    override fun onDestroy() {
        super.onDestroy()
        vb = null
    }

    override fun initNavigationDrawer() {
        vb?.apply {
            // Passing each menu ID as a set of Ids because each
            // menu should be considered as top level destinations.

            // Passing each menu ID as a set of Ids because each
            // menu should be considered as top level destinations.
            val mAppBarConfiguration: AppBarConfiguration = AppBarConfiguration.Builder(
                    R.id.nav_cocktails, R.id.nav_cocktail_ingredients, R.id.nav_about)
                    .setOpenableLayout(drawerLayout)
                    .build()

            val toggle = ActionBarDrawerToggle(
                    this@MainActivity, drawerLayout, mainActivity.mainToolbar, R.string.nav_open, R.string.nav_close)
            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()
        }
    }

    override fun restoreRequest() {
        presenter.restoreAppBar()
    }
}