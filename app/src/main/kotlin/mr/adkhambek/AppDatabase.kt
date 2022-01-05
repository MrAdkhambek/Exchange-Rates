package mr.adkhambek

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import mrakhambeksqldelight.ExchangeEntity
import mrakhambeksqldelight.ExchangeRatesQueries

object AppDatabase {

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

    fun getExchangeRates(): List<ExchangeEntity> = exchangeRatesQueries.selectAll().executeAsList()

    fun clear() {
        exchangeRatesQueries.clear()
    }
}