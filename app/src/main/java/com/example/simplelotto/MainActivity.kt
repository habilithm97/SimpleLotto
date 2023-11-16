package com.example.simplelotto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import com.example.simplelotto.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        val viewPagerAdapter = ViewPagerAdapter(this)
        viewPagerAdapter.addFragment(Fragment1())
        viewPagerAdapter.addFragment(Fragment2())
        viewPagerAdapter.addFragment(Fragment3())

        binding.viewPager.apply {
            adapter = viewPagerAdapter // 생성한 어댑터를 ViewPager에 연결
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    Log.d("TAG", "Tab ${position + 1}")
                }
            })
        }
        // TabLayout과 ViewPager를 연결
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            when(position) {
                0 -> tab.text = "Tab1"
                1 -> tab.text = "Tab2"
                2 -> tab.text = "Tab3"
            }
        }.attach()
    }
}