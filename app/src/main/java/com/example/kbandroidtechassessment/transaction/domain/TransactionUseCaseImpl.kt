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
        return repository.getTransactions().sortedBy { it.date }
    }

    override fun filterTransactions(
        transactions: List<Transaction>,
        startMillis: Long,
        endMillis: Long
    ): List<Transaction> {
        val startOfDayMillis = startMillis.minus(24 * 60 * 60 * 1000 - 1) // Start from the beginning of the selected start date
        val endOfDayMillis = endMillis // Include full end day
        return transactions.filter { transaction ->
            val transactionDate = parseDate(transaction.date)
            transactionDate in startOfDayMillis..endOfDayMillis
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
