package com.example.simplelotto

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.simplelotto.databinding.Fragment2Binding

class Fragment2 : Fragment() {
    private lateinit var binding: Fragment2Binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = Fragment2Binding.inflate(inflater, container, false)
        initView()

        return binding.root
    }

    private fun initView() {
        binding.btnCheck.setOnClickListener {

        }
    }
}