package de.hackundsoehne.invoicegen.model

case class Order(amount: Int,
                 description: String,
                 infos: List[String],
                 position: Int,
                 price: Int) {
}
