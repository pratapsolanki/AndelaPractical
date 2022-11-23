package com.andela.practical.presentation.convert_currency

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.andela.practical.presentation.currency.CurrencyFragment
import com.andela.practical.presentation.history.HistoryFragment

private const val NUM_TABS = 2

public class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return HistoryFragment()
            1 -> return CurrencyFragment()
        }
        return CurrencyFragment()
    }
}