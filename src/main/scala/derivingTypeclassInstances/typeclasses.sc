import derivingTypeclassInstances.derivingProductInstances.models._

// Custom data type:
case class Employee(name: String, number: Int, manager: Boolean)
// CsvEncoder instance for the custom data type:
implicit val employeeEncoder: CsvEncoder[Employee] = new CsvEncoder[Employee] {
  def encode(e: Employee): List[String] =
    List(
      e.name,
      e.number.toString,
      if(e.manager) "yes" else "no"
    ) }

def writeCsv[A](values: List[A])(implicit enc: CsvEncoder[A]): String = {
  values.map(value => enc.encode(value).mkString(",")).mkString("\n")
}

val employees: List[Employee] = List(
  Employee("Bill", 1, true),
  Employee("Peter", 2, false),
  Employee("Milton", 3, false)
)

writeCsv(employees)

// another
import derivingTypeclassInstances.derivingProductInstances.models._

implicit val iceCreamEncoder: CsvEncoder[IceCream] = new CsvEncoder[IceCream] {
  def encode(i: IceCream): List[String] =
    List(
      i.name,
      i.numCherries.toString,
      if(i.inCone) "yes" else "no"
    ) }
val iceCreams: List[IceCream] = List(
  IceCream("Sundae", 1, false),
  IceCream("Cornetto", 0, true),
  IceCream("Banana Split", 0, false)
)
writeCsv(iceCreams)

/*
 Type classes are very flexible but they require us to
 define instances for every type we care about.
*/

implicit def pairEncoder[A, B](
                                implicit
                                aEncoder: CsvEncoder[A],
                                bEncoder: CsvEncoder[B]
                              ): CsvEncoder[(A, B)] =
  new CsvEncoder[(A, B)] {
    def encode(pair: (A, B)): List[String] = {
      val (a, b) = pair
      aEncoder.encode(a) ++ bEncoder.encode(b)
    } }

writeCsv(employees zip iceCreams)

import shapeless._

the[CsvEncoder[IceCream]]

// this can get you from

implicit val booleanEncoder: CsvEncoder[Boolean] = new CsvEncoder[Boolean] {
  def encode(b: Boolean): List[String] =
    if(b) List("yes") else List("no")
}

/* to more concise / less boiler plate-y

implicit val booleanEncoder: CsvEncoder[Boolean] =
  instance(b => if(b) List("yes") else List("no"))
 */
