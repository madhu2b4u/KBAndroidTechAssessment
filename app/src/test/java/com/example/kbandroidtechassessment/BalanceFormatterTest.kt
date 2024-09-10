package com.example.kbandroidtechassessment

import android.content.Context
import com.example.kbandroidtechassessment.transaction.domain.BalanceFormatter
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class BalanceFormatterTest {

    private lateinit var context: Context
    private lateinit var balanceFormatter: BalanceFormatter

    @Before
    fun setup() {
        // Mock the context
        context = Mockito.mock(Context::class.java)

        // Return predefined strings for the resource values
        Mockito.`when`(context.getString(R.string.overdrawn, "-50.00"))
            .thenReturn("Overdrawn: $-50.00")
        Mockito.`when`(context.getString(R.string.available_balance, "100.00"))
            .thenReturn("Available Balance: $100.00")

        // Initialize BalanceFormatter with mocked context
        balanceFormatter = BalanceFormatter(context)
    }

    @Test
    fun `test formatBalanceStatus returns overdrawn message for negative balance`() {
        // Given
        val negativeBalance = -50.0

        // When
        val result = balanceFormatter.formatBalanceStatus(negativeBalance)

        // Then
        assertThat(result).isEqualTo("Overdrawn: $-50.00")
    }

    @Test
    fun `test formatBalanceStatus returns available balance message for positive balance`() {
        // Given
        val positiveBalance = 100.0

        // When
        val result = balanceFormatter.formatBalanceStatus(positiveBalance)

        // Then
        assertThat(result).isEqualTo("Available Balance: $100.00")
    }
}
