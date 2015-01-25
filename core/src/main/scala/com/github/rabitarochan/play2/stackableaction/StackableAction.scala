package com.github.rabitarochan.play2.stackableaction

import play.api.mvc._

import scala.collection.concurrent.TrieMap
import scala.concurrent.Future

abstract class StackableAction extends ActionBuilder[RequestWithAttributes] with StackableFilter {

  override def filter[A](request: RequestWithAttributes[A])(f: RequestWithAttributes[A] => Future[Result]): Future[Result] = {
    f(request)
  }

  def invokeBlock[A](req: Request[A], block: RequestWithAttributes[A] => Future[Result]): Future[Result] = {
    val reqWA = new RequestWithAttributes(req, new TrieMap[AttributeKey[_], Any]())
    filter(reqWA)(block)
  }

}

trait StackableFilter {
  def filter[A](request: RequestWithAttributes[A])(f: RequestWithAttributes[A] => Future[Result]): Future[Result]
}

trait AttributeKey[A] {
  def ->(value: A): Attribute[A] = Attribute(this, value)
}

case class Attribute[A](key: AttributeKey[A], value: A) {
  def toTuple: (AttributeKey[A], A) = (key, value)
}

class RequestWithAttributes[A](request: Request[A], attributes: TrieMap[AttributeKey[_], Any]) extends WrappedRequest[A](request) {
  def get[B](key: AttributeKey[B]): Option[B] = attributes.get(key).asInstanceOf[Option[B]]
  def set[B](key: AttributeKey[B], value: B): RequestWithAttributes[A] = {
    attributes.put(key, value)
    this
  }
}
