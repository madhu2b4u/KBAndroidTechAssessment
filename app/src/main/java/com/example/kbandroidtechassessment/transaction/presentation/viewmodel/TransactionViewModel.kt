package com.example.kbandroidtechassessment.transaction.presentation.viewmodel

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kbandroidtechassessment.transaction.data.models.Transaction
import com.example.kbandroidtechassessment.transaction.domain.BalanceFormatter
import com.example.kbandroidtechassessment.transaction.domain.TransactionUseCase
import com.example.kbandroidtechassessment.transaction.presentation.state.TransactionUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val transactionUseCase: TransactionUseCase,
    private val balanceFormatter: BalanceFormatter,
) : ViewModel(), LifecycleObserver {

    private val _uiState = MutableStateFlow(TransactionUiState())
    val uiState: StateFlow<TransactionUiState> = _uiState

    init {
        loadTransactions() // Load transactions on initialization
    }

    // Load all transactions and calculate the balance and status message
    fun loadTransactions() {
        viewModelScope.launch {
            val transactions = transactionUseCase.getAllTransactions()
            updateUiStateWithTransactions(transactions)
        }
    }

    // Set date filter and update UI state
    fun setDateFilter(startMillis: Long, endMillis: Long) {
        _uiState.update { currentState ->
            val filteredTransactions = transactionUseCase.filterTransactions(
                currentState.allTransactions, startMillis, endMillis
            )
            currentState.copy(
                startDate = startMillis,
                endDate = endMillis,
                filteredTransactions = filteredTransactions,
                balance = transactionUseCase.calculateBalance(filteredTransactions),
                statusMessage = balanceFormatter.formatBalanceStatus(
                    transactionUseCase.calculateBalance(filteredTransactions)
                )
            )
        }
    }

    // Reset the filter and show all transactions
    fun resetFilter() {

        _uiState.update { currentState ->
            currentState.copy(
                startDate = null,
                endDate = null,
                filteredTransactions = currentState.allTransactions,
                balance = transactionUseCase.calculateBalance(currentState.allTransactions),
                statusMessage = balanceFormatter.formatBalanceStatus(
                    transactionUseCase.calculateBalance(currentState.allTransactions)
                )
            )
        }
    }

    // Helper function to update the UI state with a list of transactions
    private fun updateUiStateWithTransactions(transactions: List<Transaction>) {
        val balance = transactionUseCase.calculateBalance(transactions)
        val statusMessage = balanceFormatter.formatBalanceStatus(balance)
        _uiState.update { currentState ->
            currentState.copy(
                allTransactions = transactions,
                filteredTransactions = transactions,
                balance = balance,
                statusMessage = statusMessage
            )
        }
    }
}
