package ru.kondrashin.diplomappv10.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

internal class AuthenticationPagerAdapter(fa: FragmentActivity): FragmentStateAdapter(fa) {
    private val fragmentList: ArrayList<Fragment> = ArrayList()
    private val nameList: ArrayList<String> = ArrayList()
    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

    fun addFragment(fragment: Fragment, name: String) {
        fragmentList.add(fragment)
        nameList.add(name)
    }

    fun  getNameOfItem(position: Int): String{
        return nameList[position]
    }

}