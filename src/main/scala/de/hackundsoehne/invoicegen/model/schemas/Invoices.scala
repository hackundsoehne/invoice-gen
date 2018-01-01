package de.hackundsoehne.invoicegen.model.schemas

import java.util.Locale

import de.hackundsoehne.invoicegen.model._
import slick.jdbc.SQLiteProfile.api._

class Invoices(tag: Tag) extends Table[Invoice](tag, "INVOICES") {
  private implicit val localeStringMapper = MappedColumnType.base[Locale, String](
    {
      case Locale.GERMAN => Locale.GERMAN.toString
      case Locale.ENGLISH => Locale.ENGLISH.toString
    }, {
      case Locale.GERMAN.toString => Locale.GERMAN
      case Locale.ENGLISH.toString => Locale.ENGLISH
    }
  )

  private implicit val targetStringMapper = MappedColumnType.base[Target, Int](
    {
      case HackUndSoehne => 0
      case TalKIT => 1
    }, {
      case 0 => HackUndSoehne
      case 1 => TalKIT
    }
  )

  private implicit val orderStringMapper = MappedColumnType.base[List[Order], String](
    orders => orders.toString,
    string => List()
  )

  def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)
  def address = column[String]("ADDRESS")
  def city = column[String]("CITY")
  def company = column[String]("COMPANY")
  def country = column[String]("COUNTRY")
  def dealDate = column[String]("DEALDATE")
  def invoiceDate = column[String]("INVOICEDATE")
  def locale = column[Locale]("LOCALE")
  def orders = column[List[Order]]("ORDERS")
  def person = column[String]("PERSON")
  def target = column[Target]("TARGET")
  def transferDate = column[String]("TRANSFERDATE")
  def zip = column[String]("ZIP")
  def * = (address, city, company, country, dealDate, invoiceDate, locale, orders,
    person, target, transferDate, zip) <> (Invoice.tupled, Invoice.unapply)
}
