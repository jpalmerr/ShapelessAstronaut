package derivingTypeclassInstancesThree

import shapeless.{::, Generic, HList, HNil}
import derivingTypeclassInstancesThree.derivingProductInstances.models.{CsvEncoder, IceCream}

case class Employee(name: String, number: Int, manager: Boolean)

object Employee {
  implicit val employeeEncoder: CsvEncoder[Employee] = new CsvEncoder[Employee] {
    def encode(e: Employee): List[String] =
      List(
        e.name,
        e.number.toString,
        if(e.manager) "yes" else "no"
      ) }
}

/**
 * We can use writeCsv with any data type we like,
 * provided we have a corresponding implicit CsvEncoder in scope:
 */

/**
 * Type classes are very flexible but they require us to define instances for every type we care about.
 * Fortunately, the Scala compiler has a few tricks up its sleeve to resolve instances for us given sets
 * of user-defined rules.
 *
 * For example, we can write a rule that creates a CsvEncoder for (A, B) given CsvEncoders forA and B:
 * see pairEncoder
 */


/**
 * In this secti􏰀on we’re going to use shapeless to derive type class instances for product types (i.e. case classes).
 * We’ll use two intuitions:
 *
 * 1. If we have type class instances for the head and tail of an HList, we can derive an instance for the whole HList.
 *
 * 2. If we have a case class A, a Generic[A], and a type class instance for the generic’s Repr,
 *    we can combine them to create an instance for A.
 */

object ExampleProductInstances {
  def createEncoder[A](func: A => List[String]): CsvEncoder[A] = new CsvEncoder[A] {
    def encode(value: A): List[String] = func(value)
  }

  implicit val stringEncoder: CsvEncoder[String] =
    createEncoder(str => List(str))
  implicit val intEncoder: CsvEncoder[Int] =
    createEncoder(num => List(num.toString))
  implicit val booleanEncoder: CsvEncoder[Boolean] = createEncoder(bool => List(if(bool) "yes" else "no"))

  implicit val hnilEncoder: CsvEncoder[HNil] =
    createEncoder(hnil => Nil)

  implicit def hlistEncoder[H, T <: HList](implicit hEncoder: CsvEncoder[H],
                                           tEncoder: CsvEncoder[T]): CsvEncoder[H :: T] = {
    createEncoder {
      case h :: t =>
        hEncoder.encode(h) ++ tEncoder.encode(t)
    }
  }

  val reprEncoder: CsvEncoder[String :: Int :: Boolean :: HNil] = implicitly

  reprEncoder.encode("abc" :: 123 :: true :: HNil)

  def test: Unit = {
    println(reprEncoder.encode("abc" :: 123 :: true :: HNil))
  }

  /**
   * now lets do it for concrete types
   */

  implicit val iceCreamEncoderGen: CsvEncoder[IceCream] = { val gen = Generic[IceCream]
    val enc = CsvEncoder[gen.Repr]
    createEncoder(iceCream => enc.encode(gen.to(iceCream)))
  }
}

object RunStuffThree extends App {

  import ExampleProductInstances._

  def writeCsv[A](values: List[A])(implicit enc: CsvEncoder[A]): String = {
    values.map(value => enc.encode(value).mkString(",")).mkString("\n")
  }

  val employees: List[Employee] = List(
    Employee("Bill", 1, true),
    Employee("Peter", 2, false),
    Employee("Milton", 3, false)
  )

  println("employees")
  println(writeCsv(employees))
  println("")

  val iceCreams: List[IceCream] = List(
    IceCream("Sundae", 1, false),
    IceCream("Cornetto", 0, true),
    IceCream("Banana Split", 0, false)
  )

  println("iceCreams")
  println(writeCsv(iceCreams))
  println("")

  println("employees & iceCreams")
  println(writeCsv(employees zip iceCreams)) // pairEncoder
  println("")

  println("HList instance for products")
  println(ExampleProductInstances.test)
  println("")
}

