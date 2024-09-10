package com.example.kbandroidtechassessment.transaction.data.repository

import com.example.kbandroidtechassessment.transaction.data.models.Transaction

interface TransactionRepository {
    fun getTransactions(): List<Transaction>
}