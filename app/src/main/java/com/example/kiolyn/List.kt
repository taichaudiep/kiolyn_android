package com.example.kiolyn

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class List : AppCompatActivity() {

    private val GET_URL = "https://postman-echo.com/basic-auth"

    lateinit var items: ArrayList<Items>
    lateinit var rvItems: RecyclerView
    lateinit var swipeLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.activity_list)

        initializeUi()

        items = Items.createItems(20)
        val adapter = ItemsAdapter(items)
        rvItems.adapter = adapter
        rvItems.layoutManager = LinearLayoutManager(this)

        swipeLayout.setOnRefreshListener {
            swipeLayout.isRefreshing = !swipeLayout.isRefreshing
            val queue: RequestQueue = Volley.newRequestQueue(this)

            val stringRequests =
                StringRequest(Request.Method.GET,
                    GET_URL,
                    Response.Listener<String> { response ->
                        Toast.makeText(
                            this,
                            "" + response,
                            Toast.LENGTH_SHORT
                        ).show()
                        swipeLayout.isRefreshing = false
                    },
                    Response.ErrorListener { response ->
                        Toast.makeText(
                            this,
                            "" + response,
                            Toast.LENGTH_SHORT
                        ).show()
                        swipeLayout.isRefreshing = false
                    })
            queue.add(stringRequests)
        }
    }

    private fun initializeUi(){
        rvItems = findViewById<View>(R.id.rv_items) as RecyclerView
        swipeLayout = findViewById(R.id.swipe_refresh)
    }

    fun addItem(view: View) {
        items.addAll(Items.createItems(1))
        val adapter = ItemsAdapter(items)
        rvItems.adapter = adapter
    }
}