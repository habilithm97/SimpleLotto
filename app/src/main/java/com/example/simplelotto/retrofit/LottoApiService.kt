package com.example.simplelotto.retrofit

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LottoApiService {
    @GET("/common.do") // 서버의 엔드 포인트 경로
    suspend fun getLottoNumbers(
        @Query("method") method: String = "getLottoNumber", // 파라미터의 기본값
        @Query("drwNo") drawNo: Int
    ): Response<LottoResponse> // 서버에서 반환된 데이터를 포함하는 Retrofit의 응답 객체
}