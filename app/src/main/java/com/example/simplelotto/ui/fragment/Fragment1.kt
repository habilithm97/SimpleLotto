package com.example.simplelotto.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.simplelotto.databinding.Fragment1Binding
import kotlin.random.Random

class Fragment1 : Fragment() {
    private var _binding: Fragment1Binding? = null
    private val binding get() = _binding!!

    private val ballList : List<TextView> by lazy {
        with(binding) {
            listOf(ball1, ball2, ball3, ball4, ball5, ball6)
        }
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
                //Log.d("Fragment1", lottoNumbers.toString())
                setLottoNumbers(lottoNumbers)
            }
        }
    }

    private fun createLottoNumbers() : List<Int> {
        val result = mutableListOf<Int>()
        val numbers = IntArray(45) { it + 1 }
        numbers.apply {
            shuffle(Random(System.currentTimeMillis()))
            slice(0..5).forEach { number -> result.add(number) }
        }
        result.sort()
        return result
    }

    private fun setLottoNumbers(result: List<Int>) {
        ballList.forEachIndexed { index, textView ->
            textView.text = result[index].toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}