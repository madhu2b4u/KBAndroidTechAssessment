package com.example.kbandroidtechassessment.transaction.domain

import android.content.Context
import com.example.kbandroidtechassessment.R
import java.util.Locale
import javax.inject.Inject

class BalanceFormatter @Inject constructor(
    private val context: Context
) {
    // Format the balance status message
    fun formatBalanceStatus(balance: Double): String {
        return if (balance < 0) {
            context.getString(
                R.string.overdrawn,
                String.format(Locale.getDefault(), "%.2f", balance)
            )
        } else {
            context.getString(
                R.string.available_balance,
                String.format(Locale.getDefault(), "%.2f", balance)
            )
        }
    }
}
