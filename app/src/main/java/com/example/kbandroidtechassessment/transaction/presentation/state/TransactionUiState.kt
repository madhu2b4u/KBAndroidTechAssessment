package com.example.kbandroidtechassessment.transaction.presentation.state

import com.example.kbandroidtechassessment.transaction.data.models.Transaction

data class TransactionUiState(
    val allTransactions: List<Transaction> = emptyList(),
    val filteredTransactions: List<Transaction> = emptyList(),
    val balance: Double = 0.0,
    val startDate: Long? = null,
    val endDate: Long? = null,
    val statusMessage: String = ""
)