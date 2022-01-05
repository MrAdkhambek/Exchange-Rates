package mr.adkhambek

import mrakhambeksqldelight.ExchangeEntity
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class AppDatabaseTest {


    @Test
    fun `test doesn't throw`() {
        assertDoesNotThrow {
            AppDatabase.clear()
            AppDatabase.getExchangeRates()
        }
    }

    @Test
    fun `test save`() {
        AppDatabase.saveExchangeRates(
            listOf(
                ExchangeEntity(
                    date = "Thu Jan 06 04:04:48 UZT 2022",
                    code = "KZT",
                    title = "Kazakhstan Tenge",
                    cbPrice = 24.65,
                    nbuBuyPrice = 12.0,
                    nbuCellPrice = 30.0
                )
            )
        )

        assertEquals(AppDatabase.getExchangeRates().size, 1)
        assertEquals(AppDatabase.getExchangeRates().first().code, "KZT")
    }
}