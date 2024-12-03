package com.example.simplelotto.ui.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.simplelotto.R
import com.example.simplelotto.databinding.Fragment1Binding
import com.example.simplelotto.ui.activity.MainActivity
import kotlin.random.Random

class Fragment1 : Fragment() {
    private var _binding: Fragment1Binding? = null // 뷰 소멸 시 메모리 릭 방지
    private val binding get() = _binding!! // 뷰 생명주기 동안 바인딩 객체에 안전하게 접근

    private lateinit var mContext: MainActivity

    private val ballList : List<TextView> by lazy {
        with(binding) {
            listOf(ball1, ball2, ball3, ball4, ball5, ball6)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        mContext = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       _binding = Fragment1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btn.setOnClickListener {
                val lottoNumbers = createLottoNumbers()
                setLottoNumbers(lottoNumbers)

                lottoNumbers.forEachIndexed { index, number ->
                    val ball = ballList[index]
                    setBallColor(number, ball)
                }
            }
        }
    }

    private fun createLottoNumbers() : List<Int> {
        return (1..45).toList()
            .shuffled()
            .take(6) // 상위 6개 선택
            .sorted() // 오름차순 정렬
    }

    private fun setLottoNumbers(result: List<Int>) {
        ballList.zip(result) { textView, number ->
            textView.text = number.toString()
        }
    }

    private fun setBallColor(number: Int, ball: TextView) {
        val ballColor = when (number) {
            in 1..10 -> R.drawable.ball_yellow
            in 11..20 -> R.drawable.ball_blue
            in 21..30 -> R.drawable.ball_red
            in 31..40 -> R.drawable.ball_gray
            else -> R.drawable.ball_green
        }
        ball.background = ContextCompat.getDrawable(mContext, ballColor)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}