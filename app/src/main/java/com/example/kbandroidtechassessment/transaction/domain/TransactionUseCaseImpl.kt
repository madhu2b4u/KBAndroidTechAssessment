package com.example.kbandroidtechassessment.transaction.domain

import com.example.kbandroidtechassessment.transaction.DateFormats
import com.example.kbandroidtechassessment.transaction.data.models.Transaction
import com.example.kbandroidtechassessment.transaction.data.repository.TransactionRepository
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class TransactionUseCaseImpl @Inject constructor(private val repository: TransactionRepository) :
    TransactionUseCase {

    override fun getAllTransactions(): List<Transaction> {
        return repository.getTransactions()
    }

    override fun filterTransactions(
        transactions: List<Transaction>,
        startMillis: Long,
        endMillis: Long
    ): List<Transaction> {
        return transactions.filter { transaction ->
            val transactionDate = parseDate(transaction.date)
            (transactionDate >= startMillis) && (transactionDate <= endMillis)
        }
    }

    override fun calculateBalance(transactions: List<Transaction>): Double {
        return transactions.sumOf { it.amount }
    }

    private fun parseDate(dateString: String): Long {
        val dateFormat = SimpleDateFormat(DateFormats.YYYY_MM_DD, Locale.getDefault())
        return dateFormat.parse(dateString)?.time ?: 0L
    }
}
