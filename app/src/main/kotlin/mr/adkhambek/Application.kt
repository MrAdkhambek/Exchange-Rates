package mr.adkhambek

import mrakhambeksqldelight.ExchangeEntity
import java.util.*


class Application {

    private var isFinish = false

    fun onStart() {
        val sc = Scanner(System.`in`)

        while (!isFinish) {
            printCommands()
            val command = sc.nextLine()

            when {
                command.equals("q" , true) -> isFinish = true
                command.equals("show" , true) -> handleShow()
                command.equals("save" , true) -> handleSave()
                command.equals("read" , true) -> handleRead()
                command.startsWith("search") -> handleSearch(command)
                else -> println("Error command")
            }

            println("\n\n")
        }
    }

    private fun handleSearch(command: String) {
        val code = command.replace("search ", "")
        AppDatabase
            .getExchangeRates()
            .first {
                it.code.equals(code, true)
            }.let {
                println(it)
            }
    }

    private fun handleRead() {
        AppDatabase
            .getExchangeRates()
            .forEach(::println)
    }

    private fun handleSave() {
       val exchanges = ExchangeLoader.invoke()
        AppDatabase.saveExchangeRates(exchanges.map {
            ExchangeEntity(
                date = it.date,
                code = it.code,
                title = it.title,
                cbPrice = it.cbPrice,
                nbuBuyPrice = it.nbuBuyPrice,
                nbuCellPrice = it.nbuCellPrice,
            )
        })
    }

    private fun handleShow() {
        ExchangeLoader.invoke().forEach(::println)
    }

    private fun printCommands() {
        println("\uD83C\uDC30".repeat(32))
        println("\uD83C\uDC2B 🕹 command     \uD83C\uDC2B comment                   \uD83C\uDC2B")
        println("\uD83C\uDC30".repeat(32))
        println("""
            🀫 👁 show        🀫 Load and show exchanges   🀫
            🀫 💾 save        🀫 Load and save exchanges   🀫
            🀫 📜 read        🀫 Read exchanges from DB    🀫
            🀫 🔎 search args 🀫 Search exchanges from DB  🀫
            🀫 ❌ q           🀫 Exit                      🀫
        """.trimIndent())
        println("\uD83C\uDC30".repeat(32))
    }


    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val app = Application()
            app.onStart()
        }
    }
}