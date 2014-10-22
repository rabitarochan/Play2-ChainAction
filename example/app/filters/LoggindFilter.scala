package filters

import com.github.rabitarochan.play2.chainaction.{RequestWithAttributes, ChainFilter}
import play.api.Logger
import play.api.mvc.Result

import scala.concurrent.Future


trait LoggindFilter {

  val loggingFilter = new ChainFilter {
    override def apply[A](next: (RequestWithAttributes[A]) => Future[Result])(request: RequestWithAttributes[A]): Future[Result] = {
      Logger.debug("Start Filter")
      next(request)
    }
  }

}
