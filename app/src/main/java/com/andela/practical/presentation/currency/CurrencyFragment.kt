package com.andela.practical.presentation.currency

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.andela.practical.databinding.FragmentHistoryBinding
import com.andela.practical.domain.models.Currency
import com.andela.practical.presentation.history_data.HistoryDataViewModel
import com.andela.practical.util.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrencyFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HistoryDataViewModel by viewModels()
    lateinit var adapter: CurrencyAdapter
    var baseCurrency: String = ""

    companion object {
        fun newInstance(baseCurrency: String) = CurrencyFragment().apply {
            this.baseCurrency = baseCurrency
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iniRecyclerview()
        if (requireContext().isNetworkAvailable()) {
            viewModel.getCurrency(baseCurrency)
            bindObserver()
        } else {
            binding.progressBar.gone()
            requireContext().toast("No Internet")
        }

    }

    private fun iniRecyclerview() {
        adapter = CurrencyAdapter()
        binding.rcv.layoutManager = LinearLayoutManager(requireActivity())
        binding.rcv.adapter = adapter
        adapter.notifyDataSetChanged()
    }


    private fun bindObserver() {
        viewModel.observeCurrencyLiveData().observe(requireActivity()) {
            when (it) {
                is Resource.Loading -> {
                    binding.progressBar.visible()

                }
                is Resource.Success -> {
                    binding.progressBar.gone()

                    it.data?.let {
                        val temp: ArrayList<Currency> = ArrayList()
                        it.quotes.forEach { (k, v) ->
                            temp.add(Currency(k ,v))
                        }
                        adapter.setData(temp)
                    }
                }
                is Resource.Error -> {
                    binding.progressBar.gone()
                    it.errorMessage?.let { it1 -> requireActivity().toast(it1) }
                }
            }
        }
    }

}