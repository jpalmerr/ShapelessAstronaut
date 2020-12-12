package derivingTypeclassInstancesThree

import derivingTypeclassInstancesThree.ExampleProductInstances.createEncoder
import derivingTypeclassInstancesThree.RunStuffThree.writeCsv
import derivingTypeclassInstancesThree.derivingProductInstances.models.CsvEncoder
import shapeless.{:+:, CNil, Coproduct, Inl, Inr}

/**
 * Deriving instances for coproducts
 */

sealed trait Shape

final case class Rectangle(width: Double, height: Double) extends Shape
final case class Circle(radius: Double) extends Shape

object Shape {

  implicit val doubleEncoder: CsvEncoder[Double]=
    createEncoder(d => List(d.toString))

  implicit val cnilEncoder: CsvEncoder[CNil] =
    createEncoder(cnil => throw new Exception("Inconceivable!"))

  implicit def coproductEncoder[H, T <: Coproduct](
                                                    implicit hEncoder: CsvEncoder[H],
                                                    tEncoder: CsvEncoder[T]
  ): CsvEncoder[H :+: T] = createEncoder {
    case Inl(h) => hEncoder.encode(h)
    case Inr(t) => tEncoder.encode(t)
  }

  implicit val shapeEncoder: CsvEncoder[Shape] = new CsvEncoder[Shape] {
    def encode(s: Shape): List[String] = List(s.toString)
  }
}

/**
 * There are two key points of note:
 *
 * 1. Because Coproducts are disjunc􏰃tions of types
 *    the encoder for :+: has to choose whether to encode a left􏰄 or right value.
 *
 *    We pa􏰁ttern match on the two subtypes of :+:, which are Inl for le􏰄 and Inr for right.
 *
 * 2. Alarmingly, the encoder for CNil throws an excep􏰀tion!
 *    Don’t panic, though. Remember that we can’t create values of type CNil, so the throw expression is dead code.
 *    It’s ok to fail abruptly here because we will never reach this point.
*/

object RunStuffProduct extends App {

  val shapes: List[Shape] = List(
    Rectangle(3.0, 4.0),
    Circle(1.0)
  )

  println("write csv shapes")
  println(writeCsv(shapes))
}