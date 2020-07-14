package com.example.kiolyn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class List : AppCompatActivity() {

    lateinit var items: ArrayList<Items>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.activity_list)

        val rvItems = findViewById<View>(R.id.rv_items) as RecyclerView
        items = Items.createItems(20)
        val adapter = ItemsAdapter(items)
        rvItems.adapter = adapter
        rvItems.layoutManager = LinearLayoutManager(this)
    }


}