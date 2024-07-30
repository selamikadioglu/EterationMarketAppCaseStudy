package com.selamikadioglu.native_mobile_case_study.di

import com.selamikadioglu.native_mobile_case_study.repository.ServiceRepository
import com.selamikadioglu.native_mobile_case_study.repository.ServiceRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideServiceRepository(serviceRepositoryImpl: ServiceRepositoryImpl): ServiceRepository
}