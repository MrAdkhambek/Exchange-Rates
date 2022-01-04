import org.jsoup.Jsoup
import org.jsoup.nodes.Node
import org.jsoup.select.Elements
import java.io.File
import java.io.PrintWriter
import java.util.*

data class Exchange(
    val date: String,
    val code: String,
    val title: String,
    val cbPrice: Double,
    val nbuBuyPrice: Double,
    val nbuCellPrice: Double,
)


fun main() {

    val doc = Jsoup.connect("https://nbu.uz/en/exchange-rates/").get()
    val body: Elements = doc.getElementsByClass("currency_block").select("tbody")
    val tableChild: List<Node> = Objects.requireNonNull(body[0]).childNodes()

    val exchanges: ArrayList<Exchange> = ArrayList<Exchange>()
    var i = 2
    while (i < tableChild.size) {
        try {
            val node: Node = tableChild[i]

            val date = Date().toString()
            val fullName: String = node.childNodes()[1].childNodes()[1].toString()
            val regex = "1\\s(\\w+\\s\\w+)\\s,\\s(\\w+)".toRegex()

            val matchEntire = regex.find(fullName)
            val title = requireNotNull(matchEntire?.groupValues?.get(1))
            val code = requireNotNull(matchEntire?.groupValues?.get(2))

            val cbPrice: String = node.childNodes()[7].childNodes()[0].toString()
            val nbuBuyPrice: String = node.childNodes()[3].childNodes()[0].toString()
            val nbuCellPrice: String = node.childNodes()[5].childNodes()[0].toString()

            exchanges.add(
                Exchange(
                    date = date,
                    code = code,
                    title = title,
                    cbPrice = cbPrice.toDouble(),
                    nbuBuyPrice = nbuBuyPrice.toDouble(),
                    nbuCellPrice = nbuCellPrice.toDouble(),
                )
            )
        } catch (e: Exception) {
            // TODO()
        }
        i += 2
    }

    val listOfData = exchanges
        .stream()
        .map {
            arrayOf<String>(
                it.title,
                it.code,
                it.nbuCellPrice.toString(),
                it.nbuBuyPrice.toString(),
                it.cbPrice.toString(),
                it.date
            )
        }.map {
            it.joinToString(",")
        }

    val csvOutputFile = File("data.csv")
    PrintWriter(csvOutputFile).use { pw ->
        listOfData.forEach(pw::println)
    }
}