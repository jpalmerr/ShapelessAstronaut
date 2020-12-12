/*
Let’s start by defining an instance constructor and CsvEncoders for
String, Int, and Boolean:
*/

trait CsvEncoder[A] {
  def encode(value: A): List[String]
}

def createEncoder[A](func: A => List[String]): CsvEncoder[A] = new CsvEncoder[A] {
  def encode(value: A): List[String] = func(value)
}
implicit val stringEncoder: CsvEncoder[String] =
  createEncoder(str => List(str))
implicit val intEncoder: CsvEncoder[Int] =
  createEncoder(num => List(num.toString))
implicit val booleanEncoder: CsvEncoder[Boolean] =
  createEncoder(bool => List(if(bool) "yes" else "no"))

/*
We can combine these building blocks to create an encoder for our HList.
We’ll use two rules: one for HNil and one for ::
 */

import derivingTypeclassInstancesThree.derivingProductInstances.models.IceCream
import shapeless.{::, Generic, HList, HNil}

implicit val hnilEncoder: CsvEncoder[HNil] =
  createEncoder(hnil => Nil)

implicit def hlistEncoder[H, T <: HList](
                                          implicit
                                          hEncoder: CsvEncoder[H],
                                          tEncoder: CsvEncoder[T]
                                        ): CsvEncoder[H :: T] = {
  createEncoder {
    case h :: t =>
      hEncoder.encode(h) ++ tEncoder.encode(t)
  }
}

/*
these five instances allow us to summon CsvEncoders for any HList involving Strings, Ints, and Booleans
 */

val reprEncoder: CsvEncoder[String :: Int :: Boolean :: HNil] = implicitly

reprEncoder.encode("abc" :: 123 :: true :: HNil)
