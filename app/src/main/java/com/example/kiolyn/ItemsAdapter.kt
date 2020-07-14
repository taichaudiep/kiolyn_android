package com.example.kiolyn

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class ItemsAdapter(private val mItems: ArrayList<Items>) :
    RecyclerView.Adapter<ItemsAdapter.ViewHolder>() {

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val titleTxt: TextView = itemView.findViewById<TextView>(R.id.item_title)
        val titleBtn: Button = itemView.findViewById<Button>(R.id.item_btn)
        val detailTxt: TextView = itemView.findViewById(R.id.item_detail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from((context))
        val itemView = inflater.inflate(R.layout.list_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    override fun onBindViewHolder(holder: ItemsAdapter.ViewHolder, position: Int) {
        val items: Items = mItems.get(position)
        val titleTxt = holder.titleTxt
        val titleBtn = holder.titleBtn
        val detailTxt = holder.detailTxt

        titleTxt.setText(items.title)
        titleBtn.setOnClickListener {
            detailTxt.visibility =
                if (detailTxt.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        }
    }

}