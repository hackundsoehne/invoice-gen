package de.hackundsoehne.invoicegen

import de.hackundsoehne.invoicegen.server.Router
import org.http4s.dsl._
import org.http4s.{Method, Request, Response, Status}
import org.specs2.matcher.MatchResult

class RouterSpec extends org.specs2.mutable.Specification {
  "HelloWorld" >> {
    "return 200" >> {
      uriReturns200()
    }
    "return hello world" >> {
      uriReturnsHelloWorld()
    }
  }

  private[this] val retHelloWorld: Response = {
    val getHW = Request(Method.GET, uri("/hello/world"))
    val task = Router.invoice.orNotFound(getHW)
    task.unsafeRun()
  }

  private[this] def uriReturns200(): MatchResult[Status] =
    retHelloWorld.status must beEqualTo(Status.Ok)

  private[this] def uriReturnsHelloWorld(): MatchResult[String] =
    retHelloWorld.as[String].unsafeRun() must beEqualTo("{\"message\":\"Hello, world\"}")
}
