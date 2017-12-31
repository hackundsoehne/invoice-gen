package de.hackundsoehne.invoicegen

import de.hackundsoehne.invoicegen.handlers.DBHandler
import de.hackundsoehne.invoicegen.model.Order

object Debug {
  def main(args: Array[String]): Unit = {
    DBHandler.init()

    val o1 = Order(1, "Basic 1", List(), 1, 1500)
    val o2 = Order(1, "Basic 2", List("a"), 2, 1500)
    val o3 = Order(1, "Basic 3", List("a", "b"), 3, 1500)
    val o4 = Order(1, "Basic 4", List("a", "b", "c"), 4, 1500)

    val o = List(o1, o2, o3, o4)

    DBHandler.insertOrders(o)

    DBHandler.selectOrders()
  }
}
