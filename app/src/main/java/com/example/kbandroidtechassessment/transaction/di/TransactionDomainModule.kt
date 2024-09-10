package com.example.kbandroidtechassessment.transaction.di

import com.example.kbandroidtechassessment.transaction.data.repository.TransactionRepository
import com.example.kbandroidtechassessment.transaction.data.repository.TransactionRepositoryImpl
import com.example.kbandroidtechassessment.transaction.domain.TransactionUseCase
import com.example.kbandroidtechassessment.transaction.domain.TransactionUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class TransactionDomainModule {
    @Binds
    internal abstract fun bindRepository(
        repoImpl: TransactionRepositoryImpl
    ): TransactionRepository


    @Binds
    internal abstract fun bindsUseCase(
        useCaseImpl: TransactionUseCaseImpl
    ): TransactionUseCase

}