package mr.adkhambek

data class Exchange(
    val date: String,
    val code: String,
    val title: String,
    val cbPrice: Double,
    val nbuBuyPrice: Double,
    val nbuCellPrice: Double,
)