package rashjz.info.scala

import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}

/**
  * Created by rasha_000 on 6/26/2017.
  */
object AsyncDemo extends App {
  val start = System.currentTimeMillis()

  def info(msg: String) = printf("%.2f: %s\n", (System.currentTimeMillis() -start)/ 1000.0, msg)

  case class Dish(name: String) {
    def +(other: Dish) = Dish(s"$name with ${other.name}")
  }

  def cook(what: String): Dish = {
    Thread.sleep(1000L)
    info(s"$what to cooked")
    Dish(what)
  }

  def serve(dish: Dish): Unit = {
    info(s"Here is your ${dish.name}")
  }

  //    val s = cook("steak")
  //    val p = cook("potatoes")
  //    serve(s + p)

  val fs: Future[Unit] = for {
    s <- Future {
      cook("steak")
    }
    p <- Future {
      cook("potatoes")
    }
  } yield {
    serve(s + p)
  }

  Await.result(fs, 10.second)


}
