package com.example.kiolyn

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class ListActivity : AppCompatActivity() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.activity_list)

        initializeUi()
        setupPagerView()
    }

    private fun initializeUi() {
        tabLayout = findViewById(R.id.tab_layout)
        viewPager = findViewById(R.id.view_pager)
    }

    private fun setupPagerView() {
        val adapter = ListFragmentPagerAdapter(supportFragmentManager)

        val firstFragment = ListFragment.newInstance("9.am - 12.am")
        val secondFragment = ListFragment.newInstance("8.pm - 11.pm")

        adapter.addFragment(firstFragment, "9.am - 12.am")
        adapter.addFragment(secondFragment, "8.pm - 11.pm")

        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
    }

}