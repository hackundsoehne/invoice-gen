package de.hackundsoehne.invoicegen

import de.hackundsoehne.invoicegen.server.Router
import fs2.Task
import org.http4s.server.blaze.BlazeBuilder
import org.http4s.util.StreamApp

import scala.util.Properties.envOrNone

/** Starting point of server */
object Main extends StreamApp {

  val port: Int = envOrNone("HTTP_PORT").fold(3001)(_.toInt)

  /** Start the server */
  def stream(args: List[String]): fs2.Stream[Task, Nothing] = BlazeBuilder.bindHttp(port)
    .mountService(Router.invoice, "/invoice")
    .serve
}
