package com.example.kiolyn

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.kiolyn.databinding.ActivityListBinding

class ListActivity : AppCompatActivity() {
    lateinit var binding: ActivityListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        binding = DataBindingUtil.setContentView(this, R.layout.activity_list)
        setupPagerView()
    }

    private fun setupPagerView() {
        val adapter = ListFragmentPagerAdapter(supportFragmentManager)

        val firstFragment = ListFragment.newInstance("9.am - 12.am")
        val secondFragment = ListFragment.newInstance("8.pm - 11.pm")

        adapter.addFragment(firstFragment, "9.am - 12.am")
        adapter.addFragment(secondFragment, "8.pm - 11.pm")

        binding.viewPager.adapter = adapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

}