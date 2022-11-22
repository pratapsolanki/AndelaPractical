package com.andela.practical.presentation.convert_currency

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.andela.practical.R
import com.andela.practical.databinding.FragmentConvertCurrencyBinding
import com.andela.practical.util.Logger
import com.andela.practical.util.Resource
import com.pratap_solanki.andelapractical.presentation.convert_currency.ConvertCurrencyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ConvertCurrencyFragment : Fragment() {

    private var _binding: FragmentConvertCurrencyBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ConvertCurrencyViewModel by viewModels()


    var currencySymbols = emptyList<String>()
    var base: String = "0"
    var result: String = "0"

    private var fromCurrency: String = ""
    private var toCurrency: String = ""

    var fromCurrencySelectedPos: Int = -1
    var toCurrencySelectedPos: Int = -1

    var swipe: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConvertCurrencyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindObserver()
        viewModel.fetchAllCurrencySymbol()
        viewModel.getPopularMovies()
        viewModel.fetchValue()

        clickListener()

    }

    private fun clickListener() {
        binding.currencyDetails.setOnClickListener {
            base = binding.edtBaseCurrency.text.toString()
            val action =
                ConvertCurrencyFragmentDirections.actionConvertCurrencyFragmentToHistoricDataFragment(
                    base,
                    result,
                    toCurrency,
                    fromCurrency
                )
            findNavController().navigate(action)
        }


        binding.swipeCurrencyBtn.setOnClickListener {
            if (swipe) {
                swipe = false
                currencySymbols.let {
                    val pos = fromCurrencySelectedPos
                    binding.fromCurrencySpinner.setText(
                        currencySymbols[toCurrencySelectedPos],
                        false
                    )
                    binding.toCurrencySpinner.setText(currencySymbols[pos], false)
                }

            } else {
                swipe = true
                currencySymbols.let {
                    val pos = toCurrencySelectedPos

                    binding.fromCurrencySpinner.setText(
                        currencySymbols[toCurrencySelectedPos],
                        false
                    )
                    binding.toCurrencySpinner.setText(
                        currencySymbols[pos],
                        false
                    )
                }

            }
        }
    }


    private fun bindObserver() {

        viewModel.observeMovieLiveData().observe(requireActivity()) {
            when (it) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    it.data?.let {
                        currencySymbols = ArrayList(it.symbols?.keys)
                        it.symbols?.forEach { (k, v) ->
                            Logger.d("  Name -> $k and value -> $v")
                        }


                        val arrayAdapter =
                            ArrayAdapter(requireContext(), R.layout.dropdown_item, currencySymbols)
                        binding.fromCurrencySpinner.setAdapter(arrayAdapter)
                        binding.toCurrencySpinner.setAdapter(arrayAdapter)

                        binding.fromCurrencySpinner.setAdapter(arrayAdapter)
                        binding.toCurrencySpinner.setAdapter(arrayAdapter)

                        fromCurrencySelectedPos = 0
                        toCurrencySelectedPos = 0
                        binding.fromCurrencySpinner.setText(
                            currencySymbols[fromCurrencySelectedPos],
                            false
                        )
                        binding.toCurrencySpinner.setText(
                            currencySymbols[toCurrencySelectedPos],
                            false
                        )

                        binding.fromCurrencySpinner.setOnItemClickListener { _, _, position, _ ->
                            fromCurrency = currencySymbols[position]
                            fromCurrencySelectedPos = position
                            Logger.d(position.toString() + currencySymbols[position])
                        }

                        binding.toCurrencySpinner.setOnItemClickListener { _, _, position, _ ->
                            toCurrency = currencySymbols[position]
                            toCurrencySelectedPos = position
                            Logger.d(position.toString() + currencySymbols[position])
                        }
                    }
                }
                is Resource.Error -> {

                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.convertUIState.collectLatest {
                when (it) {
                    is Resource.Success -> {
                        it.data?.let {
                            result = it.result.toString()
                            binding.edtExchangeCurrency.setText(it.result.toString())
                        }
                    }
                    is Resource.Error -> {

                    }
                    is Resource.Loading -> {

                    }
                }
            }
        }

    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}