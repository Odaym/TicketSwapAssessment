package com.ticketswap.assessment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item.view.*

class RecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items = listOf<String>()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        // create a new view
        val textView = LayoutInflater.from(p0.context)
                .inflate(R.layout.item, p0, false)
        return ViewHolder(textView)
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        (p0 as ViewHolder).setTitle(items[p1])
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun setTitle(string: String) {
            itemView.name.text = string
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}