package com.andela.practical.presentation.convertCurrency

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.os.postDelayed
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.andela.practical.R
import com.andela.practical.databinding.FragmentConvertCurrencyBinding
import com.andela.practical.util.Logger
import com.andela.practical.util.Resource
import com.andela.practical.util.isNetworkAvailable
import com.andela.practical.util.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ConvertCurrencyFragment : Fragment() {

    private var _binding: FragmentConvertCurrencyBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ConvertCurrencyViewModel by viewModels()

    private var currencySymbols = emptyList<String>()
    private var base: String = "0"
    private var result: String = "0"

    private var fromCurrency: String = ""
    private var toCurrency: String = ""

    private var fromCurrencySelectedPos: Int = -1
    private var toCurrencySelectedPos: Int = -1

    private var swipe: Boolean = true
    private var counter = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConvertCurrencyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         * init data loading...
         */
        if (requireContext().isNetworkAvailable()) {
            bindObserver()
            viewModel.getAllCurrencyFlow()
        } else {
            requireContext().toast("No Internet")
        }
        clickListener()
    }

    @Suppress("LongMethod")
    private fun clickListener() {
        binding.currencyDetails.setOnClickListener {
            base = binding.edtBaseCurrency.text.toString()
            val to = binding.toCurrencySpinner.text.toString()
            val from = binding.fromCurrencySpinner.text.toString()
            val amount = binding.edtExchangeCurrency.text.toString()
            val action =
                ConvertCurrencyFragmentDirections.actionConvertCurrencyFragmentToHistoricDataFragment(
                    amount,
                    base,
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
                        Logger.d("******IN*****")
                        Logger.d(fromCurrencySelectedPos.toString())
                        Logger.d(toCurrencySelectedPos.toString())
                    }
                }
                val base = binding.edtBaseCurrency.text
                binding.edtBaseCurrency.text = binding.edtExchangeCurrency.text
                binding.edtExchangeCurrency.text = base
                swipe = false
            } else {
                binding.swipeCurrencyBtn.setImageResource(R.drawable.arrow_right_circle_outline)
                if (toCurrencySelectedPos != -1) {
                    val id = toCurrencySelectedPos
                    binding.toCurrencySpinner.setText(
                        currencySymbols[id],
                        false
                    )
                    binding.fromCurrencySpinner.setText(
                        currencySymbols[fromCurrencySelectedPos],
                        false
                    )
                    Logger.d("******Out******")
                    Logger.d(toCurrencySelectedPos.toString())
                    Logger.d(fromCurrencySelectedPos.toString())
                }
                val exchange = binding.edtExchangeCurrency.text
                binding.edtExchangeCurrency.text = binding.edtBaseCurrency.text
                binding.edtBaseCurrency.text = exchange
                swipe = true
            }
        }

        binding.btnCalculate.setOnClickListener {
            convert()
        }

        binding.edtBaseCurrency.doAfterTextChanged {
            try {
                binding.edtBaseCurrency.handler.removeCallbacksAndMessages(counter)
                binding.edtBaseCurrency.handler.postDelayed(500L, ++counter) {
                    if (binding.edtBaseCurrency.text.toString().isNotEmpty()) {
                        Logger.d(binding.edtBaseCurrency.text.toString())
                        convert()
                    }
                }
            } catch (e: Exception) {
                Logger.d(e.toString())
            }
        }
    }

    private fun convert() {
        val to = binding.toCurrencySpinner.text.toString()
        val from = binding.fromCurrencySpinner.text.toString()
        val amount = binding.edtBaseCurrency.text.toString()

        if (requireContext().isNetworkAvailable()) {
            Logger.d("from =>$from")
            Logger.d("to =>$to")
            Logger.d("amount$amount")
            Logger.d("swipe $swipe")
            Logger.d("from $fromCurrencySelectedPos")
            Logger.d("to $toCurrencySelectedPos")
            viewModel.fetchValue(to, from, amount)
        } else {
            requireContext().toast("No Internet")
        }
    }

    private fun bindObserver() {
        /**
         * Handling different state
         */
        lifecycleScope.launchWhenStarted {
            viewModel.currencyFlowUI.collectLatest {
                when (it) {
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        it.data?.let {
                            currencySymbols = ArrayList(it.currencies.keys)
                            bindAdapter()
                        }
                    }
                    is Resource.Error -> {
                        try {
                            it.errorMessage?.let { it1 -> requireActivity().toast(it1) }
                        } catch (e: Exception) {
                            Logger.d(e.toString())
                        }
                    }
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.convertUIState.collectLatest {
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
                        try {
                            requireContext().toast(it.errorMessage.toString())
                        } catch (e: Exception) {
                            Logger.d(e.toString())
                        }
                    }
                }
            }
        }
    }

    private fun bindAdapter() {
        val arrayAdapter =
            ArrayAdapter(requireContext(), R.layout.dropdown_item, currencySymbols)

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
