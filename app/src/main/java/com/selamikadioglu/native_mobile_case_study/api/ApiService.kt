package com.selamikadioglu.native_mobile_case_study.api

import com.selamikadioglu.native_mobile_case_study.model.ProductResponseModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("/products")
    suspend fun getPosts(): Response<List<ProductResponseModel>>
}