package filters

import com.github.rabitarochan.play2.stackableaction._
import play.api.Logger
import play.api.mvc.Result
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future

trait StopwatchFilter extends StackableFilter {

  abstract override def filter[A](req: RequestWithAttributes[A])(f: RequestWithAttributes[A] => Future[Result]): Future[Result] = {
    val start = System.currentTimeMillis()
    super.filter(req)(f) map { res =>
      val span = System.currentTimeMillis() - start
      Logger.debug(s"Stopwatch: ${span}ms")
      res
    }
  }

}
