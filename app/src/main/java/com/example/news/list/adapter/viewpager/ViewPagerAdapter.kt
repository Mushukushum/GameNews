package com.example.news.list.adapter.viewpager

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.news.list.NewsFragment

class ViewPagerAdapter(activity: AppCompatActivity , private val itemsCount: Int): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return itemsCount
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> NewsFragment().newInstance("strories")
            1 -> NewsFragment().newInstance("video")
            else -> NewsFragment().newInstance("favourites")
        }

    }
}