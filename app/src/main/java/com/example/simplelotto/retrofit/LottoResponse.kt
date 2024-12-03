package com.example.simplelotto.retrofit

// API 응답 데이터를 매핑하기 위한 데이터 클래스
// -> 클래스의 각 속성은 API 응답에서 제공되는 JSON 필드와 대응
data class LottoResponse(
    val drwNo: Int,
    val drwNoDate: String,
    val drwtNo1: Int,
    val drwtNo2: Int,
    val drwtNo3: Int,
    val drwtNo4: Int,
    val drwtNo5: Int,
    val drwtNo6: Int,
    val bnusNo: Int,
    val firstWinamnt: Long, // 1등 당첨금
    val firstPrzwnerCo: Int // 1등 당첨 복권수
)