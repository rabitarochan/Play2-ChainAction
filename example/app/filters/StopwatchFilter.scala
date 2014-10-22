package filters

import com.github.rabitarochan.play2.chainaction.{RequestWithAttributes, ChainFilter}
import play.api.Logger
import play.api.mvc.Result
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future

trait StopwatchFilter {

  val stopwatchFilter = new ChainFilter {
    override def apply[A](next: (RequestWithAttributes[A]) => Future[Result])(request: RequestWithAttributes[A]): Future[Result] = {
      val start = System.currentTimeMillis
      next(request) map { res =>
        val span = System.currentTimeMillis - start
        Logger.debug(s"Stopwatch: ${span}ms")
        res
      }
    }
  }

}
