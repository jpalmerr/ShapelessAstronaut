package adtGenericRepresentationTwo

import shapeless.Generic

sealed trait Shape
final case class Rectangle(width: Double, height: Double) extends Shape
final case class Circle(radius: Double) extends Shape

object Thing extends App {

  val gen = Generic[Shape]

  val rectangle = gen.to(Rectangle(3.0, 4.0))
  val circle = gen.to(Circle(1.0))

  println(s"rectangle: $rectangle")
  println(s"circle: $circle")
}