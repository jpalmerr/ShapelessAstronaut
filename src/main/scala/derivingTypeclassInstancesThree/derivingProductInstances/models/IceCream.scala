package derivingTypeclassInstancesThree.derivingProductInstances.models

case class IceCream(name: String, numCherries: Int, inCone: Boolean)

object IceCream {

  /*
  implicit val iceCreamEncoder: CsvEncoder[IceCream] = new CsvEncoder[IceCream] {
    def encode(i: IceCream): List[String] =
      List(
        i.name,
        i.numCherries.toString,
        if(i.inCone) "yes" else "no"
      )
  }
  */
}
