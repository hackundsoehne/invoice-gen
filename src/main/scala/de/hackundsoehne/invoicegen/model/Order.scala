package de.hackundsoehne.invoicegen.model

import slick.lifted.MappedTo

/** Specifies structure of an order as it is received from the client.
  *
  * @param amount Number of times the order was purchased
  * @param description Brief description of order
  * @param infos Extra information asociated with order
  * @param position Index of order among all orders, starting at 1
  * @param price Price in EUR of the order without tax
  */
case class Order(amount: Int,
                 description: String,
                 infos: List[String],
                 position: Int,
                 price: Int) {
}

