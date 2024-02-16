package ru.kondrashin.diplomappv10.activity

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.fragment.app.FragmentActivity
import ru.kondrashin.diplomappv10.R

import ru.kondrashin.diplomappv10.fragment.MainFragmentUnReg

class MainPageActivity: SingleFragmentActivity() {

    companion object{
        fun newIntent(packageContext: Context?) = Intent(
            packageContext, MainPageActivity::class.java)
    }



    override fun createFragment(): MainFragmentUnReg{

        return MainFragmentUnReg.newInstance()
    }

    fun restartActivity(docFirstPage: MainPageActivity){
        docFirstPage.recreate()
    }
}