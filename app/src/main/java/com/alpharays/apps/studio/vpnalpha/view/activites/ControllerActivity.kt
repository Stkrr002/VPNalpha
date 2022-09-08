package com.alpharays.apps.studio.vpnalpha.view.activites

import android.os.Bundle
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.alpharays.apps.studio.vpnalpha.R
import com.alpharays.apps.studio.vpnalpha.databinding.ActivityControllerBinding

class ControllerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityControllerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityControllerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigation()
    }

    /*
    * Controller activity control the Navigation behaviour of the Fragments
    * */
    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val popupMenu = PopupMenu(this, null)
        popupMenu.inflate(R.menu.main_nav_menu)
        val menu = popupMenu.menu
        binding.mainBottomNav.setupWithNavController(menu, navHostFragment.navController)
    }
}