package de.hackundsoehne.invoicegen.model.schemas

import de.hackundsoehne.invoicegen.model.Order
import slick.ast.BaseTypedType
import slick.jdbc.JdbcType
import slick.jdbc.SQLiteProfile.api._

class Orders(tag: Tag) extends Table[Order](tag, "ORDERS") {
  private implicit val stringListMapper = MappedColumnType.base[List[String], String](
    list => list.map(str => str.replace("b", "b2"))
      .map(str => str.replace("a", "b1"))
      .mkString("a"),
    string => string.split("a").toList
      .filter(str => str != "")
      .map(str => str.replace("b1", "a"))
      .map(str => str.replace("b2", "b"))
  )

  def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)
  def amount = column[Int]("AMOUNT")
  def description = column[String]("DESCRIPTION")
  def infos = column[List[String]]("INFOS")
  def position = column[Int]("POSITION")
  def price = column[Int]("PRICE")
  def * = (amount, description, infos, position, price) <> (Order.tupled, Order.unapply)
}
