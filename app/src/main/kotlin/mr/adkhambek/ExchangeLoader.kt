package mr.adkhambek

import org.jsoup.Jsoup
import org.jsoup.nodes.Node
import org.jsoup.select.Elements
import java.util.*


class ExchangeLoader {

    operator fun invoke(): List<Exchange> {
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

        return exchanges
    }
}