package com.example.f32.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.f32.R
import com.example.f32.base.BaseFragment
import com.example.f32.databinding.ActivityMainBinding
import com.example.f32.ui.cart.CartFragment
import com.example.f32.ui.favorite.FavoriteFragment
import com.example.f32.ui.home.HomeFragment
import com.example.f32.ui.profile.ProfileFragment
import com.example.f32.util.Constant.Companion.INDEX_CART
import com.example.f32.util.Constant.Companion.INDEX_FAVORITE
import com.example.f32.util.Constant.Companion.INDEX_HOME
import com.example.f32.util.Constant.Companion.INDEX_PROFILE
import com.example.f32.util.toGone
import com.example.f32.util.toVisible
import com.ncapdevi.fragnav.FragNavController
import com.ncapdevi.fragnav.FragNavSwitchController
import com.ncapdevi.fragnav.FragNavTransactionOptions
import com.ncapdevi.fragnav.tabhistory.UniqueTabHistoryStrategy

class MainActivity : AppCompatActivity(), BaseFragment.FragmentNavigation,
    FragNavController.TransactionListener, FragNavController.RootFragmentListener {

    private lateinit var binding: ActivityMainBinding

    private val fragNavController: FragNavController = FragNavController(supportFragmentManager, R.id.container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initFragNav(savedInstanceState)
    }

    private fun initFragNav(savedInstanceState: Bundle?) {
        fragNavController.apply {
            rootFragmentListener = this@MainActivity
            createEager = true
            fragmentHideStrategy = FragNavController.HIDE
            navigationStrategy = UniqueTabHistoryStrategy(object : FragNavSwitchController {
                override fun switchTab(index: Int, transactionOptions: FragNavTransactionOptions?) {
                    binding.navView.selectedItemId = index
                }
            })
        }
        fragNavController.initialize(INDEX_HOME, savedInstanceState)

        if (savedInstanceState == null) binding.navView.selectedItemId = INDEX_HOME

        binding.navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    fragNavController.switchTab(INDEX_HOME)
                }
                R.id.navigation_cart -> {
                    fragNavController.switchTab(INDEX_CART)
                }
                R.id.navigation_favorite -> {
                    fragNavController.switchTab(INDEX_FAVORITE)
                }
                R.id.navigation_profile -> {
                    fragNavController.switchTab(INDEX_PROFILE)
                }
            }
            true
        }

        binding.navView.setOnItemReselectedListener { fragNavController.clearStack() }
        binding.navView.itemIconTintList = null
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount <= 0 || !supportFragmentManager.popBackStackImmediate()) {
            if (!this.fragNavController.isRootFragment) {
                if (this.fragNavController.popFragment(
                        FragNavTransactionOptions.newBuilder().build()
                    )
                )
                    return
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> fragNavController.popFragment()
        }
        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        fragNavController.onSaveInstanceState(outState)
    }

    /****************************************************************************************************
     **  FragNav (BaseFragment interface implementation)
     ****************************************************************************************************/
    override fun clearStack() = fragNavController.clearStack()
    override fun pushFragment(fragment: Fragment, sharedElementList: List<Pair<View, String>>?) {
        val options = FragNavTransactionOptions.newBuilder()
        sharedElementList?.let {
            it.forEach { pair ->
                options.addSharedElement(pair)
            }
        }
        fragNavController.pushFragment(fragment, options.build())
    }

    override fun navigateBack() = this.onBackPressed()

    override fun navigateBack(i: Int) {
        if (!fragNavController.isRootFragment) {
            fragNavController.popFragments(i, FragNavTransactionOptions.newBuilder().build())
        }
    }

    override fun navigateBack(z: Boolean) {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        }
    }

    override fun navigateTo(fragment: Fragment) =
        fragNavController.pushFragment(fragment, FragNavTransactionOptions.newBuilder().build())

    override fun switchToHome() {
        binding.navView.selectedItemId = R.id.navigation_home
        fragNavController.switchTab(INDEX_HOME)
        showBottomNavBar()
    }

    override fun switchToCart() {
        binding.navView.selectedItemId = R.id.navigation_cart
        fragNavController.switchTab(INDEX_CART)
        hideBottomNavBar()
    }

    override fun switchToFavorite() {
        binding.navView.selectedItemId = R.id.navigation_favorite
        fragNavController.switchTab(INDEX_FAVORITE)
        fragNavController.clearStack()
        showBottomNavBar()
    }

    override fun switchToProfile() {
        binding.navView.selectedItemId = R.id.navigation_profile
        fragNavController.switchTab(INDEX_PROFILE)
        fragNavController.clearStack()
        showBottomNavBar()
    }

    override fun hideBottomNavBar() = binding.navView.toGone()
    override fun showBottomNavBar() = binding.navView.toVisible()
    override val numberOfRootFragments: Int = 4

    override fun getRootFragment(index: Int): Fragment {
        when (index) {
            INDEX_HOME -> return HomeFragment()
            INDEX_CART -> return CartFragment()
            INDEX_FAVORITE -> return FavoriteFragment()
            INDEX_PROFILE -> return ProfileFragment()
        }
        throw IllegalStateException("Need to send an index that we know")
    }

    override fun onFragmentTransaction(
        fragment: Fragment?,
        transactionType: FragNavController.TransactionType
    ) {
        supportActionBar?.setDisplayHomeAsUpEnabled(fragNavController.isRootFragment.not())
    }

    override fun onTabTransaction(fragment: Fragment?, index: Int) {
        supportActionBar?.setDisplayHomeAsUpEnabled(fragNavController.isRootFragment.not())
    }
}