package com.andela.practical.presentation.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.andela.practical.databinding.FragmentHistoryBinding
import com.andela.practical.domain.models.Currency
import com.andela.practical.domain.models.History
import com.andela.practical.presentation.history_data.HistoryDataViewModel
import com.andela.practical.util.*
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HistoryDataViewModel by viewModels()
    lateinit var articleAdapter: HistoryAdapter
    private var baseCurrency: String = ""

    companion object {
        fun newInstance(baseCurrency: String) = HistoryFragment().apply {
            this.baseCurrency = baseCurrency
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val current = LocalDateTime.now()
        val lastDay = LocalDateTime.now().minusDays(2)
        val formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd")
        val formattedCurrentDay = current.format(formatter)
        val formattedLastDay = lastDay.format(formatter)
        Logger.d("$formattedCurrentDay $formattedLastDay")
        iniRecyclerview()
        if (requireContext().isNetworkAvailable()) {
            bindObserver()

            viewModel.getHistory(baseCurrency, formattedLastDay, formattedCurrentDay)
            bindObserver()
        } else {
            binding.progressBar.gone()
            requireContext().toast("No Internet")
        }

    }


    private fun iniRecyclerview() {
        articleAdapter = HistoryAdapter()
        binding.rcv.layoutManager = LinearLayoutManager(requireActivity())
        binding.rcv.adapter = articleAdapter
        articleAdapter.notifyDataSetChanged()
    }


    private fun bindObserver() {
        viewModel.observeHistoryLiveData().observe(requireActivity()) {
            when (it) {
                is Resource.Loading -> {
                    binding.progressBar.visible()

                }
                is Resource.Success -> {
                    binding.progressBar.gone()
                    it.data?.let {
                        val temp: ArrayList<History> = ArrayList()
                        it.quotes.forEach { (k, v) ->
                            val data: ArrayList<Currency> = ArrayList()
                            val date: String = k
                            v.forEach { (k, v) ->
                                data.add(Currency(k, v))
                            }
                            temp.add(History(date, data))
                            Logger.d("Size " + temp.size.toString())

                        }
                        Logger.d("Size" + temp.size.toString())
                        articleAdapter.setData(temp)
                    }
                }
                is Resource.Error -> {
                    binding.progressBar.gone()
                    try {
                        it.errorMessage?.let { it1 -> requireActivity().toast(it1) }
                    } catch (e: Exception) {
                        Logger.d("")
                    }
                }
            }
        }
    }
}