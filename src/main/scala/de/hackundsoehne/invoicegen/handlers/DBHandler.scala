package de.hackundsoehne.invoicegen.handlers

import java.sql.DriverManager

import de.hackundsoehne.invoicegen.model.Order
import slick.util.AsyncExecutor
import slick.jdbc.SQLiteProfile.api._
import de.hackundsoehne.invoicegen.model.schemas.Orders

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.Await
import scala.concurrent.duration._
import scala.util.{Failure, Success}

object DBHandler {

  val dbURL = "jdbc:sqlite:invoice.db"
  val dbDriver = "org.sqlite.JDBC"

  def init(): Unit = {
    // Create DB
    // DriverManager.getConnection(dbURL)

    // Connect to DB
    val db = connectDB()

    // Create schema
    applySchema(db)
  }

  def insertOrders(list: List[Order]): Unit = {
    val db = connectDB()

    try {
      val orders = TableQuery[Orders]

      val setup = DBIO.seq(
        orders ++= list
      )

      db.run(setup)
    } finally db.close
  }

  def selectOrders() = {
    val db = connectDB()

    try {
      val orders = TableQuery[Orders]

      val query = for {
        c <- orders
      } yield c

      db.run(query.result) onComplete {
        case Success(orderVec) => p(orderVec)
        case Failure(t) => println("An error has occured: " + t.getMessage)
      }
    } finally db.close
  }

  private def p(v: Seq[Order]): Unit = {
    println(v.toList.toString)
  }

  private def connectDB(): Database = Database.forURL(dbURL,
      driver=dbDriver,
      executor = AsyncExecutor("test1", numThreads=10, queueSize=1000)
    )

  private def applySchema(db: Database): Unit = {
    try {
      val orders = TableQuery[Orders]

      val setup = DBIO.seq(
        orders.schema.create
      )

      db.run(setup)
    } finally db.close
  }
}
