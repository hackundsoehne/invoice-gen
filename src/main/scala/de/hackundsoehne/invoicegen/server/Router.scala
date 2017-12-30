package de.hackundsoehne.invoicegen.server

import de.hackundsoehne.invoicegen.util.InvoiceParser
import io.circe._
import io.circe.syntax._
import org.http4s._
import org.http4s.circe._
import org.http4s.dsl._

/** Specifies all routes of the server */
object Router {

  /** Route used to handle invoices */
  val invoice = HttpService {

    // Route to create an invoice
    case req @ POST -> Root / "create" =>
      for {
        // Decode json
        json <- req.as[Json]
        _ = println(json)

        // Parse to invoice
        invoice = InvoiceParser.parseInvoice(json)

        // Send response
        resp <- Ok(invoice.toString.asJson).putHeaders(Header("Access-Control-Allow-Origin", "*"))
      } yield resp
  }
}
