package com.selamikadioglu.native_mobile_case_study.repository

import com.selamikadioglu.native_mobile_case_study.base.ApiResult
import com.selamikadioglu.native_mobile_case_study.base.BaseRepository
import com.selamikadioglu.native_mobile_case_study.model.ProductResponseModel
import kotlinx.coroutines.flow.Flow

interface ServiceRepository : BaseRepository {
    suspend fun getData(): Flow<ApiResult<List<ProductResponseModel>>>
}