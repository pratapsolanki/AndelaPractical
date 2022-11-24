package com.andela.practical.presentation.currency

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.andela.practical.databinding.FragmentCurrencyBinding
import com.andela.practical.domain.models.CurrencyData
import com.andela.practical.presentation.histric_data.HistoryDataViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrencyFragment : Fragment() {
    private var _binding: FragmentCurrencyBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HistoryDataViewModel by viewModels()
    lateinit var adapter: CurrencyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCurrencyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCurrency()
        setAdapter()
        val data = ArrayList<CurrencyData>()
        for (i in 1..10) {
            data.add(CurrencyData("Value", "$i"))
        }
        adapter.setData(data)

    }

    private fun setAdapter() {
        adapter = CurrencyAdapter()
        binding.rcv.layoutManager = LinearLayoutManager(requireActivity())
        binding.rcv.adapter = adapter
    }

}