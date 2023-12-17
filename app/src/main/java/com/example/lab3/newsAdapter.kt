package com.example.lab3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class newsAdapter : RecyclerView.Adapter<newsAdapter.ViewHolder>() {
    private val newsList = ArrayList<NewPoint>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var title: TextView
        private var content: TextView

        init {
            title = itemView.findViewById(R.id.titleView)
            content = itemView.findViewById(R.id.bodyView)
        }

        fun bind(entry: NewPoint) {
            title.text = entry.title
            content.text = entry.content
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val layoutId: Int = R.layout.news_item
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(layoutId, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(newsList[position])
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    fun addNewsPoints(newsEntries: List<NewPoint>) {
        for (entry in newsEntries) {
            this.newsList.add(entry)
        }
        notifyItemRangeInserted(0, newsEntries.size - 1)
    }

    fun clear() {
        val oldSize = newsList.size
        newsList.clear()
        notifyItemRangeRemoved(0, oldSize - 1)
    }
}