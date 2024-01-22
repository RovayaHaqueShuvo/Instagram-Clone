package com.own_world.instragramclone.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentViewHolder

class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(
    fm,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {
    val fragmentList = mutableListOf<Fragment>()
    val fragmentTitleList = mutableListOf<String>()
    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList.get(position)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentTitleList.get(position)

    }
    fun addFragment(fragment: Fragment, title: String){
        fragmentList.add(fragment)
        fragmentTitleList.add(title)
    }
}