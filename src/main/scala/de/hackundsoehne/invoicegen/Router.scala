package de.hackundsoehne.invoicegen

import de.hackundsoehne.invoicegen.util.JsonParser
import io.circe._
import io.circe.generic.auto._
import io.circe.syntax._
import org.http4s._
import org.http4s.circe._
import org.http4s.dsl._


case class User(name: String)

case class Hello(greeting: String)

object Router {
  val service = HttpService {
    case req @ POST -> Root / "invoice" =>
      for {
        // Decode json
        json <- req.as[Json]
        _ = println(json)

        // Parse to invoice
        invoice = JsonParser.parseInvoice(json)

        // Encode a hello response
        resp <- Ok(Hello(invoice.toString()).asJson).putHeaders(Header("Access-Control-Allow-Origin", "*"))
      } yield resp
  }
}
