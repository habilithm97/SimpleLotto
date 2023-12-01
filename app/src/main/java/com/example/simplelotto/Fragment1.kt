package com.example.simplelotto

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.simplelotto.databinding.Fragment1Binding
import kotlin.random.Random

class Fragment1 : Fragment() {
    private lateinit var binding: Fragment1Binding
    private lateinit var activity: MainActivity // Context를 가져올 변수 선언

    private val ballList : List<TextView> by lazy {
        listOf(binding.ball1, binding.ball2, binding.ball3,
            binding.ball4, binding.ball5, binding.ball6)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        activity = context as MainActivity // Context를 액티비티로 타입 캐스팅해서 할당
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = Fragment1Binding.inflate(inflater, container, false)
        initView()

        return binding.root
    }

    private fun initView() {
        binding.btnCreate.setOnClickListener {
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
            in 1..10 -> ball.background = ContextCompat.getDrawable(activity, R.drawable.ball_yellow)
            in 11..20 -> ball.background = ContextCompat.getDrawable(activity, R.drawable.ball_blue)
            in 21..30 -> ball.background = ContextCompat.getDrawable(activity, R.drawable.ball_red)
            in 31..40 -> ball.background = ContextCompat.getDrawable(activity, R.drawable.ball_gray)
            else -> ball.background = ContextCompat.getDrawable(activity, R.drawable.ball_green)
        }
    }
}