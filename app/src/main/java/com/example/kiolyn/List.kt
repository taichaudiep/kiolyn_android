package com.example.kiolyn

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.ViewPager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.tabs.TabLayout

class List : AppCompatActivity() {

    private lateinit var queue: RequestQueue
    private val GET_URL = "https://postman-echo.com/basic-auth"

    private lateinit var items: ArrayList<Items>

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager
    private lateinit var rvItems: RecyclerView
    private lateinit var swipeLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.activity_list)

        initializeUi()
        setupPagerView()

        items = Items.createItems(20)
        val adapter = ItemsAdapter(items)
        rvItems.adapter = adapter
        rvItems.layoutManager = LinearLayoutManager(this)

        queue = Volley.newRequestQueue(this)

        swipeLayout.setOnRefreshListener {
            setupRefresh()
        }
    }


    private fun initializeUi() {
        tabLayout = findViewById(R.id.tab_layout)
        viewPager = findViewById(R.id.view_pager)
        rvItems = findViewById<View>(R.id.rv_items) as RecyclerView
        swipeLayout = findViewById(R.id.swipe_refresh)
    }

    private fun setupPagerView() {
        val adapter = MyFragmentPagerAdapter(supportFragmentManager)

        val firstFragment = MyFragment.newInstance("First Fragment")
        val secondFragment = MyFragment.newInstance("First Fragment")

        adapter.addFragment(firstFragment, "9.am - 12.am")
        adapter.addFragment(secondFragment, "8.pm - 11.pm")

        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
    }

    private fun setupRefresh() {
        swipeLayout.isRefreshing = !swipeLayout.isRefreshing
        val jsonRequest = object : JsonObjectRequest(Request.Method.GET, GET_URL, null,
            Response.Listener { response ->
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
                    "Json: " + response,
                    Toast.LENGTH_SHORT
                ).show()
                swipeLayout.isRefreshing = false
            }) {
            override fun getHeaders(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["Authorization"] = "Basic cG9zdG1hbjpwYXNzd29yZA=="
                return params
            }
        }
        queue.add(jsonRequest)
    }

    fun addItem(view: View) {
        items.addAll(Items.createItems(1))
        val adapter = ItemsAdapter(items)
        rvItems.adapter = adapter
    }
}