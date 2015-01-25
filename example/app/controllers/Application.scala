package controllers

import com.github.rabitarochan.play2.chainaction._
import com.github.rabitarochan.play2.stackableaction.StackableAction
import filters._
import play.api.Logger
import play.api.mvc._

object Application extends Controller {

  def index = Action {
    Ok(views.html.index())
  }

  def withLogging = MyAction { request =>
    Logger.debug(s"withLogging: LoggingKey = ${MyAction.loggingKey(request)}")
    Ok(views.html.index())
  }

  def test = MyAction { request =>
    Ok(views.html.index())
  }

}

object MyAction extends StackableAction with StopwatchFilter with LoggindFilter
