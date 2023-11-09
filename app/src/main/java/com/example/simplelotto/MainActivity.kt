package com.example.simplelotto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.simplelotto.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        binding.btnRun.setOnClickListener {
            val lottoNums = createLottoNumber()
            Log.d("TAG", lottoNums.toString())
        }
    }

    private fun createLottoNumber() : List<Int> {
        val result = mutableListOf<Int>()
        val nums = IntArray(45) {it + 1}
        nums.shuffle(Random(System.currentTimeMillis()))
        nums.slice(0..5).forEach {num -> result.add(num)}
        result.sort()

        return result
    }
}