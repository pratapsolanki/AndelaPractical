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
        val current = LocalDateTime.now()
        var lastDay =  LocalDateTime.now().minusDays(3)
        val formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd")
        val formattedCurrentDay = current.format(formatter)
        val formattedLastDay = lastDay.format(formatter)
        Logger.d(formattedCurrentDay + " " + formattedLastDay)
        iniRecyclerview()
        viewModel.getHistory(formattedLastDay,formattedCurrentDay)
        bindObserver()
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
                        var temp : ArrayList<History> = ArrayList()
                        it.rates.forEach { (k, v) ->
                            var date : String
                            var data : ArrayList<Currency> = ArrayList()
                            date = k
                            v.forEach{(k ,v) ->
                                data.add(Currency(k ,v))
                            }
                            temp.add(History(date , data))
                            Logger.d("Sized of" + temp.size.toString())

                        }
                        Logger.d("Sized of fdfd" + temp.size.toString())
                        articleAdapter.setData(temp)
                    }
                }
                is Resource.Error -> {

                }
            }
        }
    }
}