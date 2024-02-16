package ru.kondrashin.diplomappv10.activity

import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import ru.kondrashin.diplomappv10.R

abstract class SingleFragmentActivity: AppCompatActivity() {
    protected abstract fun createFragment(): Fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)
        val fm = supportFragmentManager
        var fragment = fm.findFragmentById(R.id.fragmentContainer)
        if (fragment == null){
            fragment = createFragment()
            fm.beginTransaction().add(R.id.fragmentContainer, fragment)
                .commit()
        }
        addMenuProvider(object : MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.main_page_registred_user_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return true
            }
        })


        makeAnim(R.anim.scaile_in_fragment_animation, R.anim.scalie_out_fragment_animation)
    }

    fun makeAnim(startAnim: Int, endAnim: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            overrideActivityTransition(
                OVERRIDE_TRANSITION_OPEN,
                startAnim, endAnim
            )
        } else {
            @Suppress("DEPRECATION")
            overridePendingTransition(
                startAnim,
                endAnim
            )
        }
    }
}