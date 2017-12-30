package de.hackundsoehne.invoicegen.util

import java.util.Locale

import io.circe._
import de.hackundsoehne.invoicegen.model.{HackUndSoehne, Invoice, Order, TalKIT, Target}

object JsonParser {
  def parseInvoice(rawJson: Json): Invoice = {
    val c: HCursor = rawJson.hcursor
    var target: Target = HackUndSoehne
    var locale: Locale = Locale.GERMAN
    var company, person, address, zip,city, country, invoiceDate, dealDate, transferDeadline = ""
    try {
      target = parseTarget(c)
      locale = parseLocale(c)
      company = decodeString(c, "company")
      person = decodeString(c, "person")
      address = decodeString(c, "address")
      zip = decodeString(c, "zip")
      city = decodeString(c, "city")
      country = decodeString(c, "country")
      invoiceDate = decodeString(c, "invoiceDate")
      dealDate = decodeString(c, "dealDate")
      transferDeadline = decodeString(c, "transferDeadline")
    } catch {
      case e: IllegalArgumentException => println("exception caught: " + e);
    }

    val orders = parseOrders(c)

    Invoice(address, city, company, country, dealDate, invoiceDate, locale, orders, person, target, transferDeadline, zip)
  }

  def parseOrders(c: HCursor): List[Order] = {
    val orders = for {
      tuple <- c.downField("orders").focus.flatMap(_.asArray).getOrElse(Vector.empty).zipWithIndex
    } yield parseOrder(tuple)

    orders.toList
  }

  def parseOrder(tuple: (Json, Int)): Order = {
    val c: HCursor = tuple._1.hcursor

    var amount, price = 0
    var description = ""
    try {
      amount = decodeInt(c, "amount")
      description = decodeString(c, "description")
      price = decodeInt(c, "price")
    } catch {
      case e: IllegalArgumentException => println("exception caught: " + e);
    }

    val vector: Vector[Json] = c.downField("infos")
      .focus
      .flatMap(_.asArray)
      .getOrElse(Vector.empty)
    val infos: List[String] = vector.toList
      .map(_.as[String])
      .filter(_.isRight)
      .map(_.getOrElse(Unit))
      .map(_.toString)

    Order(amount, description, infos, tuple._2 + 1, price)
  }

  @throws(classOf[IllegalArgumentException])
  def parseLocale(c: HCursor): Locale = decodeString(c, "locale") match {
    case "de" => Locale.GERMAN
    case "en" => Locale.ENGLISH
    case _ => throw new IllegalArgumentException("Locale not defined.")
  }

  @throws(classOf[IllegalArgumentException])
  def parseTarget(c: HCursor): Target = decodeInt(c, "target") match {
    case 0 => HackUndSoehne
    case 1 => TalKIT
    case _ => throw new IllegalArgumentException("Target not defined.")
  }

  @throws(classOf[IllegalArgumentException])
  def decodeString(c: HCursor, key: String): String = c.get[String](key) match {
    case Left(error) => throw new IllegalArgumentException("Error parsing " + key + " to String.")
    case Right(json) => json
  }

  @throws(classOf[IllegalArgumentException])
  def decodeInt(c: HCursor, key: String): Int = c.get[Int](key) match {
    case Left(error) => throw new IllegalArgumentException("Error parsing " + key + " to Int.")
    case Right(json) => json
  }
}
