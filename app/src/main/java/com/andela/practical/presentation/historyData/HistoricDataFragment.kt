package com.andela.practical.presentation.historyData

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.andela.practical.databinding.FragmentHistoricDataBinding
import com.andela.practical.presentation.convertCurrency.ViewPagerAdapter
import com.andela.practical.util.Constants
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

/**
 * Consumer - can access all the class from the container
 */
@AndroidEntryPoint
class HistoricDataFragment : Fragment() {

    /**
     * Argument retrieve from source
     */
    private val args: HistoricDataFragmentArgs by navArgs()

    private var _binding: FragmentHistoricDataBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoricDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTitleHeader()

        bindData()


    }

    private fun setTitleHeader() {
        binding.txtBase.text = "Base Currency is : ${args.fromCurrency} "
            .plus("\nFrom Currency is : ${args.toCurrency} ")
            .plus("\nInput is : ${args.exchangeBaseCurrency} ${args.fromCurrency}  ")
            .plus("\nResult is : ${args.exchangeCurrencyResult} ${args.toCurrency} ")
    }

    private fun bindData() {
        /**
         * bind viewpager with fragment
         */
        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout

        val adapter = ViewPagerAdapter(childFragmentManager, lifecycle, args.fromCurrency)
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = 2

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = Constants.tabArray[position]
        }.attach()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
