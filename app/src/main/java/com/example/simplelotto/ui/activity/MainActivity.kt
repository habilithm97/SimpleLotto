package com.example.simplelotto.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.simplelotto.R
import com.example.simplelotto.databinding.ActivityMainBinding
import com.example.simplelotto.ui.fragment.Fragment1
import com.example.simplelotto.ui.fragment.Fragment2
import com.example.simplelotto.ui.fragment.Fragment3
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        init()

        if (savedInstanceState == null) {
            replaceFragment(Fragment1())
        }
    }

    private fun init() {
        binding.apply {
            tabs.apply {
                addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                    override fun onTabSelected(tab: TabLayout.Tab?) {
                        when (tab?.position) {
                            0 -> replaceFragment(Fragment1())
                            1 -> replaceFragment(Fragment2())
                            2 -> replaceFragment(Fragment3())
                        }
                    }
                    override fun onTabUnselected(tab: TabLayout.Tab?) {}
                    override fun onTabReselected(tab: TabLayout.Tab?) {}
                })
                addTab(newTab().setText("시뮬레이터"))
                addTab(newTab().setText("당첨번호"))
                addTab(newTab().setText("설정"))
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }
}