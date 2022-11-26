package com.andela.practical.presentation.convertCurrency

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.andela.practical.presentation.currency.CurrencyFragment
import com.andela.practical.presentation.history.HistoryFragment
import com.andela.practical.util.Constants


class ViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    var data: String
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return Constants.NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return HistoryFragment.newInstance(data)
            1 -> return CurrencyFragment.newInstance(data)
        }
        return CurrencyFragment()
    }
}