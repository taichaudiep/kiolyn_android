package com.example.kiolyn

import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class ListFragment : Fragment() {
    private lateinit var queue: RequestQueue
    private val GET_URL = "https://postman-echo.com/basic-auth"

    private lateinit var items: ArrayList<Items>

    private lateinit var rvItems: RecyclerView
    private lateinit var swipeLayout: SwipeRefreshLayout
    private lateinit var textView: TextView

    companion object {
        fun newInstance(message: String): ListFragment {

            val f = ListFragment()
            val bdl = Bundle(1)
            bdl.putString(EXTRA_MESSAGE, message)
            f.arguments = bdl

            return f
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_list, container, false)

        val message = arguments!!.getString(EXTRA_MESSAGE)
        initializeUi(view)

        textView.text = message

        items = Items.createItems(20)
        val adapter = ItemsAdapter(items)
        rvItems.adapter = adapter
        rvItems.layoutManager = LinearLayoutManager(this.context)

        swipeLayout.setOnRefreshListener {
            setupRefresh()
        }

        queue = Volley.newRequestQueue(this.context)

        return view
    }

    private fun initializeUi(view: View) {
        textView = view.findViewById(R.id.fragment_txt)
        rvItems = view.findViewById(R.id.rv_items)
        swipeLayout = view.findViewById(R.id.swipe_refresh)
    }

    private fun setupRefresh() {
        swipeLayout.isRefreshing = !swipeLayout.isRefreshing
        val jsonRequest = object : JsonObjectRequest(
            Request.Method.GET, GET_URL, null,
            Response.Listener { response ->
                Toast.makeText(
                    this.context,
                    "" + response,
                    Toast.LENGTH_SHORT
                ).show()
                swipeLayout.isRefreshing = false
            },
            Response.ErrorListener { response ->
                Toast.makeText(
                    this.context,
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