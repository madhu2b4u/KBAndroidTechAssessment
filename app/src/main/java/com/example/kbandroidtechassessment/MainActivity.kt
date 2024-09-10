package com.example.kbandroidtechassessment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.kbandroidtechassessment.transaction.formatDate
import com.example.kbandroidtechassessment.transaction.presentation.screens.DateRangePickerModal
import com.example.kbandroidtechassessment.transaction.presentation.screens.TransactionItem
import com.example.kbandroidtechassessment.transaction.presentation.viewmodel.TransactionViewModel
import com.example.kbandroidtechassessment.ui.theme.KBAndroidTechAssessmentTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: TransactionViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KBAndroidTechAssessmentTheme {

                val uiState by viewModel.uiState.collectAsState()
                var showDatePicker by remember { mutableStateOf(false) }

                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(stringResource(R.string.travel_savings_account))
                            },
                            actions = {
                                IconButton(onClick = {
                                    showDatePicker = true
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.DateRange,
                                        contentDescription = stringResource(R.string.filter)
                                    )
                                }
                            }
                        )
                    }
                ) { innerPadding ->
                    Column(
                        modifier = Modifier.padding(innerPadding),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Row(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = stringResource(R.string.balance_status),
                                fontWeight = FontWeight.W300,
                                fontSize = TextUnit(16f, TextUnitType.Sp),
                            )
                        }
                        Text(
                            text = uiState.statusMessage,
                            fontWeight = FontWeight.W600,
                            fontSize = TextUnit(24f, TextUnitType.Sp),
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )

                        if (uiState.startDate != null || uiState.endDate != null) {
                            Text(
                                text = "Selected Dates: ${formatDate(uiState.startDate)} to ${
                                    formatDate(
                                        uiState.endDate
                                    )
                                }",
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                            Button(
                                onClick = { viewModel.resetFilter() },
                                modifier = Modifier.padding(horizontal = 16.dp)
                            ) {
                                Text(stringResource(R.string.reset_filter))
                            }
                        }
                        HorizontalDivider()
                        LazyColumn {
                            items(
                                count = uiState.filteredTransactions.size,
                                contentType = { index -> "transaction" }
                            ) { index ->
                                TransactionItem(transaction = uiState.filteredTransactions[index])
                            }
                        }

                        if (showDatePicker) {
                            DateRangePickerModal(
                                onDismiss = { showDatePicker = false },
                                onDateRangeSelected = { dateRange ->
                                    val (startMillis, endMillis) = dateRange
                                    if (startMillis != null && endMillis != null) {
                                        viewModel.setDateFilter(startMillis, endMillis)

                                    }
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}

