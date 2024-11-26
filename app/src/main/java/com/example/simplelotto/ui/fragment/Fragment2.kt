package com.example.simplelotto.ui.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.simplelotto.R
import com.example.simplelotto.databinding.Fragment2Binding
import com.example.simplelotto.retrofit.LottoApiService
import com.example.simplelotto.retrofit.LottoResponse
import com.example.simplelotto.ui.activity.MainActivity
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Fragment2 : Fragment() {
    private var _binding: Fragment2Binding? = null
    private val binding get() = _binding!!
    private var toast: Toast? = null
    private lateinit var mContext: MainActivity

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://www.dhlottery.co.kr/") // 동행복권 API의 기본 URL
            // Gson을 사용해 JSON 데이터를 코틀린 객체로 변환
            .addConverterFactory(GsonConverterFactory.create())
            .build() // Retrofit 인스턴스 생성
    }

    private val lottoApiService by lazy {
        // Retrofit 인터페이스 구현체 생성
        retrofit.create(LottoApiService::class.java)
    }

    private val ballList : List<TextView> by lazy {
        with(binding) {
            listOf(ball1, ball2, ball3, ball4, ball5, ball6, ball7)
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
        _binding = Fragment2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btn.setOnClickListener {
                val round = edt.text.toString()
                if (round.isNotBlank()) {
                    getLottoResult(round.toInt())
                } else {
                    showToast(requireContext(), "회차 번호를 입력하세요. ")
                }
            }
        }
    }

    private fun getLottoResult(round: Int) {
        lifecycleScope.launch { // 비동기적 네트워크 요청 처리
            try {
                val response = lottoApiService.getLottoNumbers(drawNo = round)
                if (response.isSuccessful) {
                    // 응답의 body를 확인하여 데이터가 존재할 경우 처리
                    response.body()?.let { lottoResponse ->
                        setLottoResult(lottoResponse)
                    } ?: run { // 응답 body가 null인 경우
                        showToast(requireContext(), "데이터를 가져오지 못했습니다. ")
                    }
                } else {
                    showToast(requireContext(), "오류 : ${response.message()}")
                }
            } catch (e: Exception) {
                showToast(requireContext(), "네트워크 오류가 발생했습니다. ")
            }
        }
    }

    private fun setLottoResult(lottoResponse: LottoResponse) {
        binding.apply {
            // 당첨 번호 리스트
            val drwtNoList = with(lottoResponse) {
                listOf(drwtNo1, drwtNo2, drwtNo3, drwtNo4, drwtNo5, drwtNo6, bnusNo)
            }
            ballList.forEachIndexed { index, textView ->
                val number = drwtNoList[index]
                textView.text = number.toString()
                setBallColor(number, textView)
            }
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

    private fun showToast(context: Context, msg: String) {
        toast?.cancel()
        toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT).apply { show() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}