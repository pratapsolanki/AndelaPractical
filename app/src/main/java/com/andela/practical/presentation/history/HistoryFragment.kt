package com.andela.practical.presentation.history

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.andela.practical.databinding.FragmentHistoryBinding
import com.andela.practical.domain.models.Currency
import com.andela.practical.domain.models.History
import com.andela.practical.presentation.histric_data.HistoryDataViewModel
import com.andela.practical.util.Logger
import com.andela.practical.util.Resource
import com.andela.practical.util.toast
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HistoryDataViewModel by viewModels()
    lateinit var articleAdapter: HistoryAdapter


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
        val (formattedCurrentDay, formattedLastDay) = dateHandling()
        iniRecyclerview()
        viewModel.getHistory(formattedLastDay, formattedCurrentDay)
        bindObserver()
    }

    private fun dateHandling(): Pair<String, String> {
        val current = LocalDateTime.now()
        val lastDay = LocalDateTime.now().minusDays(3)
        val formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd")
        val formattedCurrentDay = current.format(formatter)
        val formattedLastDay = lastDay.format(formatter)
        Logger.d("$formattedCurrentDay $formattedLastDay")
        return Pair(formattedCurrentDay, formattedLastDay)
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

                }
                is Resource.Success -> {

                    it.data?.let {
                        val temp: ArrayList<History> = ArrayList()
                        it.rates.forEach { (k, v) ->
                            val data: ArrayList<Currency> = ArrayList()
                            val date: String = k
                            v.forEach { (k, v) ->
                                data.add(Currency(k, v))
                            }
                            temp.add(History(date, data))
                            Logger.d("Sized of" + temp.size.toString())

                        }
                        Logger.d("Sized of" + temp.size.toString())
                        articleAdapter.setData(temp)
                    }
                }
                is Resource.Error -> {
                    it.errorMessage?.let { it1 -> requireActivity().toast(it1) }
                }
            }
        }
    }
}