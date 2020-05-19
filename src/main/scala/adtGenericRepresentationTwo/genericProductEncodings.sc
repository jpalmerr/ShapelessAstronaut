/*
An HList is either the empty list HNil, or a pair ::[H, T] where H is an arbitrary type and T is another HList.

Because every :: has its own H and T, the type of each element is encoded separately in the type of the overall list:
 */

import shapeless.{HList, ::, HNil}

val product: String :: Int :: Boolean :: HNil = "Sunday" :: 1 :: false :: HNil

val first = product.head
// first: String = Sunday
val second = product.tail.head
// second: Int = 1
val rest = product.tail.tail
// rest: Boolean :: shapeless.HNil = false :: HNil

/*
 the compiler knows the length of any HList
 => head/tail of empty = compiler error
*/

val newProduct = 42L :: product
// newProduct: Long :: String :: Int :: Boolean :: shapeless.HNil = 42 :: Sunday :: 1 :: false :: HNil

/*
Shapeless provides a type class called Generic that allows us to
switch back and forth between a concrete ADT and its generic representa􏰀on.
*/

import shapeless.Generic

case class IceCream(name: String, numCherries: Int, inCone: Boolean)

val iceCreamGen = Generic[IceCream]

val iceCream = IceCream("Sundae", 1, false)
// iceCream: IceCream = IceCream(Sundae,1,false)
val repr = iceCreamGen.to(iceCream)
// repr: iceCreamGen.Repr = Sundae :: 1 :: false :: HNil
val iceCream2 = iceCreamGen.from(repr)
// iceCream2: IceCream = IceCream(Sundae,1,false)

/*
If two ADTs have the same Repr, we can convert back and forth between them
using their Generics:
*/

case class Employee(name: String, number: Int, manager: Boolean)
// Create an employee from an ice cream:

val employee = Generic[Employee].from(Generic[IceCream].to(iceCream))
// employee: Employee = Employee(Sundae,1,false)

/*
Worth noting tuples are just case classes
so works with them too
*/

val tupleGen = Generic[(String, Int, Boolean)]
tupleGen.to(("Hello", 123, true))
// tupleGen.Repr = Hello :: 123 :: true :: HNil
tupleGen.from("Hello" :: 123 :: true :: HNil)
// (String, Int, Boolean) = (Hello,123,true)