package mr.adkhambek

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import mrakhambeksqldelight.ExchangeEntity
import mrakhambeksqldelight.ExchangeRatesQueries


class ExchangeDatabase {

    private val exchangeRatesQueries: ExchangeRatesQueries

    init {
        val driver: SqlDriver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        MyDatabase.Schema.create(driver)

        val database = MyDatabase(driver)
        exchangeRatesQueries = database.exchangeRatesQueries
    }

    fun saveExchangeRates(list: List<ExchangeEntity>) {
        list.forEach { entity ->
            exchangeRatesQueries.insert(entity)
        }
    }

    fun searchByCode(code: String): List<ExchangeEntity> {
        return exchangeRatesQueries.searchByCode(code).executeAsList()
    }

    fun getExchangeRates(): List<ExchangeEntity> = exchangeRatesQueries.selectAll().executeAsList()

    fun clear() {
        exchangeRatesQueries.clear()
    }
}