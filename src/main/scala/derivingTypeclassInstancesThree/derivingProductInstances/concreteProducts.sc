// We can combine our deriva􏰀tion rules for HLists with an instance of Generic to produce a CsvEncoder for IceCream

import shapeless.Generic

import derivingTypeclassInstancesThree.derivingProductInstances.models._

implicit val iceCreamEncoder: CsvEncoder[IceCream] = { val gen = Generic[IceCream]
  val enc = CsvEncoder[gen.Repr]
  createEncoder(iceCream => enc.encode(gen.to(iceCream)))
}