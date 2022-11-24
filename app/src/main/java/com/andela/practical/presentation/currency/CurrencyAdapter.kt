package com.andela.practical.presentation.currency

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andela.practical.databinding.SingleOtherCurrencyDesignBinding
import com.andela.practical.domain.models.Currency

class CurrencyAdapter() : RecyclerView.Adapter<MainViewHolder>() {
    private var historyList: ArrayList<Currency> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SingleOtherCurrencyDesignBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val price = historyList[position]
        holder.binding.currencyName.text = price.key
        holder.binding.currencyValue.text = price.value.toString()

    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    fun setData(articleModel: List<Currency>) {
        historyList = articleModel as ArrayList<Currency>
        notifyDataSetChanged()
    }

}

class MainViewHolder(var binding: SingleOtherCurrencyDesignBinding) :
    RecyclerView.ViewHolder(binding.root)
