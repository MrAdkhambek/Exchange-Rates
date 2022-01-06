package mr.adkhambek

import kotlinx.coroutines.*
import mr.adkhambek.network.NetworkModule
import mr.adkhambek.repository.ExchangeRepository
import java.util.*


class Application(
    private val exchangeRepository: ExchangeRepository
) {

    private var isFinish = false

    fun onStart() {
        runBlocking {
            while (!isFinish && this.isActive) {
                printCommands()

                val sc = Scanner(System.`in`)
                val command = sc.nextLine()

                when {
                    command.equals("q", true) -> isFinish = true
                    command.equals("show", true) -> handleShow()
                    command.equals("save", true) -> handleSave()
                    command.equals("read", true) -> handleRead()
                    command.startsWith("search") -> handleSearch(command)
                    else -> println("Error command")
                }
            }
        }
    }

    private suspend fun handleSearch(command: String) {
        val code = command.replace("search ", "")
        val exchange = exchangeRepository.searchExchange(code)
        exchange.forEach(::println)
    }

    private suspend fun handleRead() {
        exchangeRepository.loadExchanges().forEach(::println)
    }

    private suspend fun handleSave() {
        val list = exchangeRepository.loadExchanges()
        exchangeRepository.saveExchanges(list)
    }

    private suspend fun handleShow() {
        exchangeRepository.loadExchanges().forEach(::println)
    }

    private fun printCommands() {
        println("\uD83C\uDC30".repeat(32))
        println("\uD83C\uDC2B 🕹 command     \uD83C\uDC2B comment                   \uD83C\uDC2B")
        println("\uD83C\uDC30".repeat(32))
        println(
            """
            🀫 👁 show        🀫 Load and show exchanges   🀫
            🀫 💾 save        🀫 Load and save exchanges   🀫
            🀫 📜 read        🀫 Read exchanges from DB    🀫
            🀫 🔎 search args 🀫 Search exchanges from DB  🀫
            🀫 ❌ q           🀫 Exit                      🀫
        """.trimIndent()
        )
        println("\uD83C\uDC30".repeat(32))
    }


    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val networkModule = NetworkModule()

            val repository = ExchangeRepository(
                exchangeLoader = ExchangeLoader(),
                exchangeDatabase = ExchangeDatabase(),
                networkAPI = networkModule.networkAPI
            )

            val app = Application(
                exchangeRepository = repository,
            )
            app.onStart()
        }
    }
}