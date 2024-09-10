package com.example.kbandroidtechassessment

import com.example.kbandroidtechassessment.transaction.data.models.Transaction
import com.example.kbandroidtechassessment.transaction.data.repository.TransactionRepository
import com.example.kbandroidtechassessment.transaction.domain.TransactionUseCaseImpl
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalCoroutinesApi::class)
class TransactionUseCaseTest {

    private lateinit var repository: TransactionRepository
    private lateinit var useCase: TransactionUseCaseImpl

    @Before
    fun setup() {
        repository = mock() // Mocking the repository
        useCase = TransactionUseCaseImpl(repository) // Inject the mocked repository
    }

    @Test
    fun `test getAllTransactions returns expected transactions`() = runTest {
        // Given
        val transactions = listOf(
            Transaction("2024-09-01", "Groceries", -50.0),
            Transaction("2024-09-02", "Rent", -1000.0)
        )

        // When
        whenever(repository.getTransactions()).thenReturn(transactions)
        val result = useCase.getAllTransactions()

        // Then
        assertThat(result).isEqualTo(transactions)
    }

    @Test
    fun `test calculateBalance returns correct balance`() {
        // Given
        val transactions = listOf(
            Transaction("2024-09-01", "Groceries", -50.0),
            Transaction("2024-09-02", "Rent", -1000.0)
        )

        // When
        val balance = useCase.calculateBalance(transactions)

        // Then
        assertThat(balance).isEqualTo(-1050.0)
    }

    @Test
    fun `test filterTransactions returns correct filtered transactions`() {
        // Given
        val transactions = listOf(
            Transaction("2024-09-01", "Groceries", -50.0),
            Transaction("2024-09-05", "Rent", -1000.0)
        )
        val startMillis = parseDate("2024-09-01")
        val endMillis = parseDate("2024-09-03")

        // When
        val filteredTransactions = useCase.filterTransactions(transactions, startMillis, endMillis)

        // Then
        assertThat(filteredTransactions.size).isEqualTo(1)
        assertThat(filteredTransactions[0].description).isEqualTo("Groceries")
    }

    private fun parseDate(dateString: String): Long {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.parse(dateString)?.time ?: 0L
    }
}
