package com.flipper.bottombar.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.flipper.bottombar.R
import com.flipper.bottombar.di.BottomBarComponent
import com.flipper.bottombar.main.compose.ComposeBottomBar
import com.flipper.bottombar.main.service.BottomNavigationViewModel
import com.flipper.bottombar.model.FlipperBottomTab
import com.flipper.core.di.ComponentHolder
import com.flipper.core.navigation.delegates.OnBackPressListener
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class BottomNavigationActivity : FragmentActivity() {
    @Inject
    lateinit var router: Router

    private val bottomNavigationViewModel by viewModels<BottomNavigationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        ComponentHolder.component<BottomBarComponent>().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_with_bottombar)

        selectTab(FlipperBottomTab.DEVICE)

        findViewById<ComposeView>(R.id.bottom_bar).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val selectedItem by bottomNavigationViewModel.selectedTab.collectAsState()
                ComposeBottomBar(selectedItem) {
                    selectTab(it)
                }
            }
        }
    }

    private fun selectTab(tab: FlipperBottomTab) {
        bottomNavigationViewModel.onSelectTab(tab)
        val fm = supportFragmentManager
        val tabName = tab.name
        val currentFragment: Fragment? = fm.fragments.find { it.isVisible }
        val newFragment = fm.findFragmentByTag(tabName)
        if (currentFragment != null && newFragment != null && currentFragment === newFragment) return
        val transaction = fm.beginTransaction()
        if (newFragment == null) {
            transaction.add(
                R.id.fragment_container,
                TabContainerFragment.getNewInstance(tab),
                tabName
            )
        }
        if (currentFragment != null) {
            transaction.hide(currentFragment)
        }
        if (newFragment != null) {
            transaction.show(newFragment)
        }
        transaction.commitNow()
    }

    override fun onBackPressed() {
        val currentFragment: Fragment? = supportFragmentManager.fragments.find { it.isVisible }
        if ((currentFragment as? OnBackPressListener)?.onBackPressed() == true) {
            return
        } else {
            router.exit()
        }
    }
}