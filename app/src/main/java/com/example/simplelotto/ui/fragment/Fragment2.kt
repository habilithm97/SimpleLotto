package com.example.simplelotto.ui.fragment

import android.content.Context
import android.os.Build
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
import java.time.LocalDate
import java.time.temporal.ChronoUnit

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

        getLatestLottoResult() // 가장 최근 회차 표시

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

    private fun getLatestLottoResult() {
        val latestRound = calculateLatestRound() // 가장 최근 회차 계산
        getLottoResult(latestRound)
    }

    private fun calculateLatestRound(): Int {
        // 1회차 추첨일 설정
        val firstDrawDate = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDate.of(2002, 12, 7)
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        val date = LocalDate.now()
        // 1회차 추첨일로부터 현재 날짜까지의 경과 주 수 계산
        val weeksElapsed = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ChronoUnit.WEEKS.between(firstDrawDate, date)
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        return (weeksElapsed + 1).toInt()  // 1회차부터 시작하므로 경과 주 수에 1을 더함
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
        val drwtNoList = with(lottoResponse) {
            listOf(drwtNo1, drwtNo2, drwtNo3, drwtNo4, drwtNo5, drwtNo6, bnusNo)
        }
        binding.apply {
            ballList.forEachIndexed { index, textView ->
                val number = drwtNoList[index]
                textView.text = number.toString()
                setBallColor(number, textView)
            }
            // 회차
            val round = lottoResponse.drwNo.toString()
            tvRound.text = "${round}회차"

            // 날짜
            val drwNoDate = lottoResponse.drwNoDate
            tvDate.text = drwNoDate

            // 당첨금
            val prizeAmount = lottoResponse.firstWinamnt.toString()
            val format1 = prizeAmount.toLongOrNull()?.let {
                String.format("%,d", it)
            } ?: "0"
            tvPrizeAmount.text = "1등 당첨금 ${format1}원"

            // 당첨 수
            val prizeWinners = lottoResponse.firstPrzwnerCo.toString()
            val format2 = prizeWinners.toIntOrNull()?.let {
                String.format("%,d", it)
            } ?: "0"
            tvPrizeWinners.text = "당첨 복권 수 : ${format2}개"
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