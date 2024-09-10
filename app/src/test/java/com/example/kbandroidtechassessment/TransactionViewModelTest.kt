import com.example.kbandroidtechassessment.MainDispatcherRule
import com.example.kbandroidtechassessment.transaction.data.models.Transaction
import com.example.kbandroidtechassessment.transaction.domain.BalanceFormatter
import com.example.kbandroidtechassessment.transaction.domain.TransactionUseCase
import com.example.kbandroidtechassessment.transaction.presentation.viewmodel.TransactionViewModel
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TransactionViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var transactionUseCase: TransactionUseCase
    private lateinit var balanceFormatter: BalanceFormatter
    private lateinit var viewModel: TransactionViewModel

    @Before
    fun setup() {
        transactionUseCase = mockk()
        balanceFormatter = mockk()

        // Mock BalanceFormatter responses for positive and negative balances
        every { balanceFormatter.formatBalanceStatus(-1050.0) } returns "Overdrawn: $-1050.00"
        every { balanceFormatter.formatBalanceStatus(-50.0) } returns "Overdrawn: $-50.00"
        every { balanceFormatter.formatBalanceStatus(-1000.0) } returns "Overdrawn: $-1000.00"
        every { balanceFormatter.formatBalanceStatus(0.0) } returns "Available Balance: $0.00" // Mock for 0.0 balance


        // Mock calculateBalance() to return the sum of transaction amounts
        every { transactionUseCase.calculateBalance(any()) } answers {
            firstArg<List<Transaction>>().sumOf { it.amount }
        }

        // Mock getAllTransactions() to return a list of transactions
        every { transactionUseCase.getAllTransactions() } returns listOf(
            Transaction("2024-09-01", "Groceries", -50.0),
            Transaction("2024-09-02", "Rent", -1000.0)
        )

        // Initialize the ViewModel
        viewModel = TransactionViewModel(transactionUseCase, balanceFormatter)
    }

    // Test case for loading all transactions and verifying balance and status
    @Test
    fun `test loadTransactions updates UI state with transactions and balance`() = runTest {
        // When
        viewModel.loadTransactions()

        // Ensure coroutine execution completes
        advanceUntilIdle()

        // Then
        val uiState = viewModel.uiState.first()
        val expectedTransactions = listOf(
            Transaction("2024-09-01", "Groceries", -50.0),
            Transaction("2024-09-02", "Rent", -1000.0)
        )

        // Assertions
        assertThat(uiState.allTransactions).isEqualTo(expectedTransactions)
        assertThat(uiState.balance).isEqualTo(-1050.0) // Balance calculation
        assertThat(uiState.statusMessage).isEqualTo("Overdrawn: $-1050.00")
    }

    /* @Test
     fun `test setDateFilter updates UI state with filtered transactions and balance`() = runTest {
         // Given
         val allTransactions = listOf(
             Transaction("2024-09-01", "Groceries", -50.0),
             Transaction("2024-09-02", "Rent", -1000.0)
         )
         val filteredTransactions = listOf(
             Transaction("2024-09-01", "Groceries", -50.0)
         )

         // Date format for parsing transaction dates
         val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

         // Convert "2024-09-01" 00:00:00 to millis
         val calendarStart = Calendar.getInstance().apply {
             set(2024, Calendar.SEPTEMBER, 1, 0, 0, 0)
         }
         val startMillis = calendarStart.timeInMillis

         // Set endMillis to the start of "2024-09-02" 00:00:00 to exclude it
         val calendarEnd = Calendar.getInstance().apply {
             set(2024, Calendar.SEPTEMBER, 2, 0, 0, 0)  // Set to the start of 2024-09-02
         }
         val endMillis = calendarEnd.timeInMillis

         // Log for debugging the filtering range
         println("Start millis: $startMillis (${dateFormat.format(startMillis)})")
         println("End millis: $endMillis (${dateFormat.format(endMillis)})")

         // Mock filterTransactions to filter based on date range
         every { transactionUseCase.filterTransactions(allTransactions, startMillis, endMillis) } answers {
             allTransactions.filter { transaction ->
                 val transactionDate = dateFormat.parse(transaction.date)?.time ?: 0L
                 // Log the transaction date for debugging
                 println("Transaction date: ${transaction.date} (millis: $transactionDate)")
                 // Filter strictly within the date range (start <= transactionDate < end)
                 transactionDate >= startMillis && transactionDate < endMillis  // STRICTLY LESS THAN endMillis
             }
         }

         // Preload all transactions
         viewModel.loadTransactions()

         // When
         viewModel.setDateFilter(startMillis, endMillis)

         // Ensure coroutine execution completes
         advanceUntilIdle()

         // Then
         val uiState = viewModel.uiState.first()

         // Log for debugging to see the filtered transactions
         println("Filtered Transactions: ${uiState.filteredTransactions}")

         // Assertions
         assertThat(uiState.filteredTransactions).isEqualTo(filteredTransactions)
         assertThat(uiState.balance).isEqualTo(-1000.0)
         assertThat(uiState.statusMessage).isEqualTo("Overdrawn: $-1000.00")

     }


     @Test
     fun `test resetFilter restores all transactions and balance`() = runTest {
         // Given
         val allTransactions = listOf(
             Transaction("2024-09-01", "Groceries", -50.0),
             Transaction("2024-09-05", "Rent", -1000.0)
         )
         val filteredTransactions = listOf(
             Transaction("2024-09-01", "Groceries", -50.0)
         )

         // Mock getAllTransactions() to return the full transaction list
         every { transactionUseCase.getAllTransactions() } returns allTransactions

         // Mock filterTransactions() to return a filtered list based on any date range
         every { transactionUseCase.filterTransactions(allTransactions, any(), any()) } returns filteredTransactions

         // Mock the balance formatting
         every { balanceFormatter.formatBalanceStatus(-50.0) } returns "Overdrawn: $-50.00"
         every { balanceFormatter.formatBalanceStatus(-1050.0) } returns "Overdrawn: $-1050.00"

         // Preload transactions and apply a filter
         viewModel.loadTransactions()
         viewModel.setDateFilter(1000L, 2000L)

         // Ensure coroutine execution completes
         advanceUntilIdle()

         // When: Reset the filter to get back all transactions
         viewModel.resetFilter()

         // Ensure coroutine execution completes
         advanceUntilIdle()

         // Then: Check if all transactions and the full balance are restored
         val uiState = viewModel.uiState.first()

         assertThat(uiState.filteredTransactions).isEqualTo(allTransactions)  // Should restore all transactions
         assertThat(uiState.balance).isEqualTo(-1050.0)  // Correct total balance after reset
         assertThat(uiState.statusMessage).isEqualTo("Overdrawn: $-1050.00")  // Correct status message for the total balance
     }*/


    // Test case to ensure balance is updated based on the transactions
    @Test
    fun `test balance calculation works correctly`() = runTest {
        // Given
        val transactions = listOf(
            Transaction("2024-09-01", "Groceries", -50.0),
            Transaction("2024-09-02", "Rent", 100.0)
        )
        every { transactionUseCase.getAllTransactions() } returns transactions
        every { balanceFormatter.formatBalanceStatus(50.0) } returns "Available Balance: $50.00"

        // When
        viewModel.loadTransactions()

        // Ensure coroutine execution completes
        advanceUntilIdle()

        // Then
        val uiState = viewModel.uiState.first()

        // Assert balance and status message
        assertThat(uiState.balance).isEqualTo(50.0)
        assertThat(uiState.statusMessage).isEqualTo("Available Balance: $50.00")
    }
}
