package com.andela.practical.presentation.histric_data

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.andela.practical.databinding.FragmentHistoricDataBinding
import com.andela.practical.presentation.MainActivity
import com.andela.practical.presentation.convert_currency.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoricDataFragment : Fragment() {
    private val tabArray = arrayOf(
        "History",
        "Currency"
    )
    private val args: HistoricDataFragmentArgs by navArgs()

    private var _binding: FragmentHistoricDataBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoricDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        bindData()
        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout

        val adapter = ViewPagerAdapter((activity as MainActivity).supportFragmentManager, lifecycle)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabArray[position]
        }.attach()
    }

    private fun bindData() {

    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}