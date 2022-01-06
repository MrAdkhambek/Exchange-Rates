package mr.adkhambek.repository

import mr.adkhambek.Exchange
import mr.adkhambek.ExchangeDatabase
import mr.adkhambek.ExchangeLoader
import mr.adkhambek.network.NetworkAPI
import mrakhambeksqldelight.ExchangeEntity


class ExchangeRepository(
    private val networkAPI: NetworkAPI,
    private val exchangeLoader: ExchangeLoader,
    private val exchangeDatabase: ExchangeDatabase,
) {

    suspend fun loadExchanges(): List<Exchange> {
        val response = networkAPI.loadFromNBU()
        if (!response.isSuccessful) {
            println(response.errorBody()?.string())
            return emptyList()
        }

        val exchanges = response.body() ?: throw IllegalArgumentException()
        saveExchanges(exchanges)
        return exchanges
    }

    suspend fun saveExchanges(list: List<Exchange>) {
        list.map {
            ExchangeEntity(
                date = it.date,
                code = it.code,
                title = it.title,
                cbPrice = it.cbPrice,
                nbuBuyPrice = it.nbuBuyPrice,
                nbuCellPrice = it.nbuCellPrice,
            )
        }.let { entities ->
            exchangeDatabase.saveExchangeRates(entities)
        }
    }

    suspend fun searchExchange(query: String): List<Exchange> {
        return exchangeDatabase.searchByCode(query).map {
            Exchange(
                date = it.date,
                code = it.code,
                title = it.title,
                cbPrice = it.cbPrice,
                nbuBuyPrice = it.nbuBuyPrice,
                nbuCellPrice = it.nbuCellPrice,
            )
        }
    }

    suspend fun clearExchanges() {
        exchangeDatabase.clear()
    }
}