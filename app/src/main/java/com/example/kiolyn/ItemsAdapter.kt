package com.example.kiolyn

import android.os.Build
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView

class ItemsAdapter(private val mItems: ArrayList<Items>) :
    RecyclerView.Adapter<ItemsAdapter.ViewHolder>() {

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val itemLayout: View = itemView.findViewById(R.id.item_contain)
        val titleTxt: TextView = itemView.findViewById<TextView>(R.id.item_title)
        val titleImg: ImageView = itemView.findViewById<ImageView>(R.id.img_view)

        val detailLayout: ViewGroup = itemView.findViewById(R.id.detail_layout)
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

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onBindViewHolder(holder: ItemsAdapter.ViewHolder, position: Int) {
        val items: Items = mItems[position]
        val itemLayout = holder.itemLayout
        val titleTxt = holder.titleTxt
        val titleImg = holder.titleImg

        val detailLayout = holder.detailLayout

        titleTxt.text = items.title
        itemLayout.setOnClickListener {
            TransitionManager.beginDelayedTransition(detailLayout)

            if (detailLayout.visibility == View.VISIBLE) {
                detailLayout.visibility = View.GONE
                titleImg.setImageResource(R.drawable.ic_down_arrow)
            } else {
                detailLayout.visibility = View.VISIBLE
                titleImg.setImageResource(R.drawable.ic_up_arrow)
            }
        }
    }
}