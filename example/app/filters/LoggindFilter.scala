package filters

import com.github.rabitarochan.play2.stackableaction._
import play.api.Logger
import play.api.mvc.Result

import scala.concurrent.Future


trait LoggindFilter extends StackableFilter {

  abstract override def filter[A](request: RequestWithAttributes[A])(f: RequestWithAttributes[A] => Future[Result]): Future[Result] = {
    Logger.debug("LoggingFilter")
    super.filter(request.set(LoggingKey, "LoggingValue"))(f)
  }

  case object LoggingKey extends AttributeKey[String]

  def loggingKey(implicit request: RequestWithAttributes[_]): String = request.get(LoggingKey).get

}
