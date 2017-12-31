package de.hackundsoehne.invoicegen.handlers

import java.util.Locale

import io.circe._
import de.hackundsoehne.invoicegen.model.{HackUndSoehne, Invoice, Order, TalKIT, Target}

/**
  * Creates an [[Invoice]] from a [[Json]] object
  */
object InvoiceParser {

  /** Creates an [[Invoice]] from a [[Json]] object
    *
    * @param json [[Json]] object to parse into an [[Invoice]]
    * @return A new [[Invoice]] object
    */
  def parseInvoice(json: Json): Invoice = {

    // Define variables
    val c: HCursor = json.hcursor
    var target: Target = HackUndSoehne
    var locale: Locale = Locale.GERMAN
    var orders: List[Order] = List()
    var company, person, address, zip,city, country, invoiceDate, dealDate, transferDeadline = ""

    // Extract all invoice components from the json
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
      orders = parseOrders(c)
      transferDeadline = decodeString(c, "transferDeadline")
    } catch {
      // TODO: Handle error properly
      case e: IllegalArgumentException => println("exception caught: " + e);
    }

    Invoice(address, city, company, country, dealDate, invoiceDate, locale, orders, person, target, transferDeadline, zip)
  }

  /** Creates a list of [[Order]]s from the [[Json]] component of the invoice json
    *
    * @param c [[HCursor]] for root [[Json]] object
    * @return A list of [[Order]] objects
    */
  private def parseOrders(c: HCursor): List[Order] = {
    val orders = for {
      tuple <- c.downField("orders").focus.flatMap(_.asArray).getOrElse(Vector.empty).zipWithIndex
    } yield parseOrder(tuple)

    orders.toList
  }

  /** Creates a single [[Order]] from the [[Json]] component of the invoice json
    *
    * @param tuple The json component with the index of its position in the orders list
    * @return A new [[Order]] object
    */
  private def parseOrder(tuple: (Json, Int)): Order = {
    val c: HCursor = tuple._1.hcursor

    val amount = decodeInt(c, "amount")
    val description = decodeString(c, "description")
    val price = decodeInt(c, "price")

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

  /** Parse the locale string into a [[Locale]] object
    *
    * @param c [[HCursor]] for a [[Json]] object
    * @throws java.lang.IllegalArgumentException Thrown if json is not correct
    * @return The corresponding [[Locale]] object
    */
  @throws(classOf[IllegalArgumentException])
  private def parseLocale(c: HCursor): Locale = decodeString(c, "locale") match {
    case "de" => Locale.GERMAN
    case "en" => Locale.ENGLISH
    case _ => throw new IllegalArgumentException("Locale not defined.")
  }

  /** Parse the target integer into a [[Target]] object
    *
    * @param c [[HCursor]] for a [[Json]] object
    * @throws java.lang.IllegalArgumentException Thrown if json is not correct
    * @return The corresponding [[Target]] object
    */
  @throws(classOf[IllegalArgumentException])
  private def parseTarget(c: HCursor): Target = decodeInt(c, "target") match {
    case 0 => HackUndSoehne
    case 1 => TalKIT
    case _ => throw new IllegalArgumentException("Target not defined.")
  }

  /** Decodes the value of a key as long as it's a string
    *
    * @param c [[HCursor]] for a [[Json]] object
    * @param key The key corresponding to the value to decode
    * @throws java.lang.IllegalArgumentException Thrown if json is not correct
    * @return The string corresponding to the key
    */
  @throws(classOf[IllegalArgumentException])
  private def decodeString(c: HCursor, key: String): String = c.get[String](key) match {
    case Left(error) => throw new IllegalArgumentException("Error parsing " + key + " to String.")
    case Right(json) => json
  }

  /** Decodes the value of a key as long as it's an integer
    *
    * @param c [[HCursor]] for a [[Json]] object
    * @param key The key corresponding to the value to decode
    * @throws java.lang.IllegalArgumentException Thrown if json is not correct
    * @return The integer corresponding to the key
    */
  @throws(classOf[IllegalArgumentException])
  private def decodeInt(c: HCursor, key: String): Int = c.get[Int](key) match {
    case Left(error) => throw new IllegalArgumentException("Error parsing " + key + " to Int.")
    case Right(json) => json
  }
}
