package com.github.rabitarochan.play2.chainaction

import play.api.mvc.{WrappedRequest, Request, ActionBuilder, Result}

import scala.collection.concurrent.TrieMap
import scala.concurrent.Future

trait ChainFilter {
  def apply[A](next: RequestWithAttributes[A] => Future[Result])(request: RequestWithAttributes[A]): Future[Result]
}

trait ChainSupport {

  final class ChainActionBuilder(filters: ChainFilter*) extends ActionBuilder[RequestWithAttributes] {

    def invokeBlock[A](req: Request[A], block: RequestWithAttributes[A] => Future[Result]): Future[Result] = {
      val request = new RequestWithAttributes(req, new TrieMap[AttributeKey[_], Any])
      val newBlock = filters.reverse.foldLeft(block)((b, m) => m(b))
      newBlock(request)
    }

  }

  object ChainAction {
    def apply(filters: ChainFilter*) = new ChainActionBuilder(filters :_*)
  }

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
