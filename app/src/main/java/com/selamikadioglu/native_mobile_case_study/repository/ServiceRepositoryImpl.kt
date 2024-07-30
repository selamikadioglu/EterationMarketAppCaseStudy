package com.selamikadioglu.native_mobile_case_study.repository

import com.selamikadioglu.native_mobile_case_study.api.ApiService
import com.selamikadioglu.native_mobile_case_study.base.ApiResult
import com.selamikadioglu.native_mobile_case_study.model.ProductResponseModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by emre.sutuna on 24.07.2024.
 */
class ServiceRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ServiceRepository {
    override suspend fun getData(): Flow<ApiResult<List<ProductResponseModel>>> = createServiceFlow {
        apiService.getPosts()
    }

}