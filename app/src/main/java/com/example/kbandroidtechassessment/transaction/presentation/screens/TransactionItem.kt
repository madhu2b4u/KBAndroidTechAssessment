package com.example.kbandroidtechassessment.transaction.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.kbandroidtechassessment.transaction.data.models.Transaction
import com.example.kbandroidtechassessment.transaction.formatDate

@Composable
fun TransactionItem(transaction: Transaction) {
    Row(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = transaction.description)
            Text(text = formatDate(transaction.date), style = MaterialTheme.typography.labelSmall)
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "$${transaction.amount}",
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .align(Alignment.CenterVertically)
        )
    }
}