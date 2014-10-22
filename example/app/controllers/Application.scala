package controllers

import com.github.rabitarochan.play2.chainaction._
import filters._
import play.api.mvc._

object Application extends Controller with MyApplicationFilterSet {

  def index = Action {
    Ok(views.html.index())
  }

  def withLogging = TestAction { request =>
    Ok(views.html.index())
  }

  def test = TestAction { request =>
    Ok(views.html.index())
  }

}

trait MyApplicationFilterSet
  extends ChainSupport
     with LoggindFilter
     with StopwatchFilter { self: Controller =>

  def TestAction = ChainAction(stopwatchFilter, loggingFilter)

}
