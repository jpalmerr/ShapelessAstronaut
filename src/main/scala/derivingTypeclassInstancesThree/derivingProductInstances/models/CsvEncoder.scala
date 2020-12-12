package derivingTypeclassInstancesThree.derivingProductInstances.models

trait CsvEncoder[A] {
  def encode(value: A): List[String]
}

object CsvEncoder {
  // "Summoner" method
  def apply[A](implicit enc: CsvEncoder[A]): CsvEncoder[A] =
    enc
  // "Constructor" method
  def instance[A](func: A => List[String]): CsvEncoder[A] = new CsvEncoder[A] {
    def encode(value: A): List[String] =
      func(value)
  }
  // Globally visible type class instances

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
}