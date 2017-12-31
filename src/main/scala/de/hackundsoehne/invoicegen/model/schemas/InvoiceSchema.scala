//package de.hackundsoehne.invoicegen.model.schemas
//
//import de.hackundsoehne.invoicegen.model.{Invoice, Order}
//import slick.jdbc.SQLiteProfile.api._
//
//class InvoiceSchema(tag: Tag) extends Table[Invoice](tag, "INVOICES") {
//  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
//  def first = column[String]("first")
//  def last = column[String]("last")
//  // def * = (id.?, first, last) <> (Invoice.tupled, Invoice.unapply)
//  def * = (id, first, last)
//}
//
//class OrderSchema(tag: Tag) extends Table[Order](tag, "ORDERS") {
//  def amount = column[Int]("id", O.PrimaryKey, O.AutoInc)
//  def description = column[String]("first")
//  def position = column[String]("last")
//  def price = column[String]("last")
//  def infos = column[List[String]]("last")
//  // def * = (id.?, first, last) <> (Invoice.tupled, Invoice.unapply)
//  def * = (id, first, last)
//
//  implicit val stringListMapper = MappedColumnType.base[List[String],String](
//    list => list.mkString(","),
//    string => string.split(',').toList
//  )
//
//}
//
//val users = TableQuery[Invoices]