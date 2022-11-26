package com.andela.practical.presentation.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andela.practical.databinding.SingleHistoryDesignBinding
import com.andela.practical.domain.models.History

class HistoryAdapter : RecyclerView.Adapter<MainViewHolder>() {
    private var historyList: ArrayList<History> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SingleHistoryDesignBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val article = historyList[position]
        holder.binding.textView.text =
            article.data + "\n" + article.list.joinToString { it.toString() }

    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    fun setData(articleModel: List<History>) {
        historyList = articleModel as ArrayList<History>
        notifyDataSetChanged()
    }
}

class MainViewHolder(var binding: SingleHistoryDesignBinding) :
    RecyclerView.ViewHolder(binding.root)
