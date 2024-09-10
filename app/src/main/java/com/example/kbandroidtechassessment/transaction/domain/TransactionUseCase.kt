package com.example.kbandroidtechassessment.transaction.domain

import com.example.kbandroidtechassessment.transaction.data.models.Transaction

interface TransactionUseCase {

    fun getAllTransactions(): List<Transaction>

    fun filterTransactions(
        transactions: List<Transaction>,
        startMillis: Long,
        endMillis: Long
    ): List<Transaction>

    fun calculateBalance(transactions: List<Transaction>): Double
}