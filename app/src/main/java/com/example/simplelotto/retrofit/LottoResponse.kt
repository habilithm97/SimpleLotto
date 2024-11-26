package com.example.simplelotto.retrofit

// 동행복권 API에서 반환하는 JSON 데이터를 코틀린에서 매핑
// -> 클래스의 각 속성은 API 응답 필드에 해당
data class LottoResponse(
    val drwNo: Int,
    val drwNoDate: String,
    val drwtNo1: Int,
    val drwtNo2: Int,
    val drwtNo3: Int,
    val drwtNo4: Int,
    val drwtNo5: Int,
    val drwtNo6: Int,
    val bnusNo: Int
)
