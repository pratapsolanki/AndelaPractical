package com.andela.practical.presentation.convert_currency

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.andela.practical.R
import com.andela.practical.databinding.FragmentConvertCurrencyBinding
import com.andela.practical.util.Logger
import com.andela.practical.util.Resource
import com.andela.practical.util.isNetworkAvailable
import com.andela.practical.util.toast
import dagger.hilt.android.AndroidEntryPoint

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
        if (requireContext().isNetworkAvailable()) {
            bindObserver()
            viewModel.getAllCurrency()
        } else {
            val builder = AlertDialog.Builder(requireContext())
            with(builder)
            {
                setTitle("ERROR!!!")
                setMessage("NO INTERNET AVAILABLE")
                show()
            }
        }


        clickListener()

    }

    private fun clickListener() {
        binding.currencyDetails.setOnClickListener {
            base = binding.edtBaseCurrency.text.toString()
            val to = binding.toCurrencySpinner.text.toString()
            val from = binding.fromCurrencySpinner.text.toString()
            val amount = binding.edtBaseCurrency.text.toString()
            val action =
                ConvertCurrencyFragmentDirections.actionConvertCurrencyFragmentToHistoricDataFragment(
                    base,
                    amount,
                    to,
                    from
                )
            findNavController().navigate(action)
        }


        binding.swipeCurrencyBtn.setOnClickListener {
            if (swipe) {
                binding.swipeCurrencyBtn.setImageResource(R.drawable.arrow_left_thin_circle_outline)
                currencySymbols.let {
                    val id = fromCurrencySelectedPos
                    if (toCurrencySelectedPos != -1) {
                        binding.fromCurrencySpinner.setText(
                            currencySymbols[toCurrencySelectedPos],
                            false
                        )
                        binding.toCurrencySpinner.setText(currencySymbols[id], false)
                    }
                }
                swipe = false

            } else {

                binding.swipeCurrencyBtn.setImageResource(R.drawable.arrow_right_circle_outline)
                if (toCurrencySelectedPos != -1) {
                    val id = toCurrencySelectedPos
                    binding.toCurrencySpinner.setText(
                        currencySymbols[fromCurrencySelectedPos],
                        false
                    )
                    binding.fromCurrencySpinner.setText(currencySymbols[id], false)
                }
                swipe = true
            }
        }

        binding.btnCalculate.setOnClickListener {
            val to = binding.toCurrencySpinner.text.toString()
            val from = binding.fromCurrencySpinner.text.toString()
            val amount = binding.edtBaseCurrency.text.toString()

            viewModel.fetchValue(to, from, amount)
        }
    }


    private fun bindObserver() {

        viewModel.observeCurrencyLiveData().observe(requireActivity()) {
            when (it) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    it.data?.let {
                        currencySymbols = ArrayList(it.symbols?.keys)
                        it.symbols?.forEach { (k, v) ->
                            Logger.d("  Name -> $k and value -> $v")
                        }
                        bindAdapter()
                    }
                }
                is Resource.Error -> {
                }
            }
        }

        viewModel.observerFullLiveData().observe(requireActivity()) {
            when (it) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    it.data?.let {
                        result = it.result.toString()
                        binding.edtExchangeCurrency.setText(it.result.toString())
                    }
                }
                is Resource.Error -> {
                    requireContext().toast(it.errorMessage.toString())
                }
            }
        }

    }

    private fun bindAdapter() {
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
            binding.fromCurrencySpinner.setText(currencySymbols[position], false)
            Logger.d(position.toString() + currencySymbols[position])
        }

        binding.toCurrencySpinner.setOnItemClickListener { _, _, position, _ ->
            toCurrency = currencySymbols[position]
            toCurrencySelectedPos = position
            binding.toCurrencySpinner.setText(currencySymbols[position], false)
            Logger.d(position.toString() + currencySymbols[position])
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}