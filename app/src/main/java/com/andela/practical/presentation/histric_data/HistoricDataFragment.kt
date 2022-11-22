package com.andela.practical.presentation.histric_data

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.andela.practical.databinding.FragmentHistoricDataBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoricDataFragment : Fragment() {
    private val args: HistoricDataFragmentArgs by navArgs()

    private var _binding: FragmentHistoricDataBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoricDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindData()

    }

    private fun bindData() {
        binding.baseAmout.text = args.exchangeBaseCurrency
        binding.resultAmount.text = args.exchangeCurrencyResult
        binding.toAmout.text = args.toCurrency
        binding.fromAmout.text = args.fromCurrency
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}