package com.example.simplelotto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.simplelotto.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val ballList : List<TextView> by lazy {
        listOf<TextView>(
            binding.ball1, binding.ball2, binding.ball3,
            binding.ball4, binding.ball5, binding.ball6
        )
    }

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
            setLottoNums(lottoNums)

            lottoNums.forEachIndexed { i, num ->
                val ball = ballList[i] // 각 TextView를 ball이라는 변수에 할당
                setBallColor(ball, num)
            }
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

    private fun setLottoNums(result: List<Int>) {
        with(binding) {
            ball1.text = result[0].toString()
            ball2.text = result[1].toString()
            ball3.text = result[2].toString()
            ball4.text = result[3].toString()
            ball5.text = result[4].toString()
            ball6.text = result[5].toString()
        }
    }

    private fun setBallColor(ball: TextView, num: Int) {
        when(num) {
            in 1..10 -> ball.background = ContextCompat.getDrawable(this, R.drawable.ball_yellow)
            in 11..20 -> ball.background = ContextCompat.getDrawable(this, R.drawable.ball_blue)
            in 21..30 -> ball.background = ContextCompat.getDrawable(this, R.drawable.ball_red)
            in 31..40 -> ball.background = ContextCompat.getDrawable(this, R.drawable.ball_gray)
            else -> ball.background = ContextCompat.getDrawable(this, R.drawable.ball_green)
        }
    }
}