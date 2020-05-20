import eu.timepit.refined._
import eu.timepit.refined.api.Refined
import eu.timepit.refined.auto._
import eu.timepit.refined.numeric._

// checks via an implicit macro that the assigned value satisfies it
val i1: Int Refined Positive = 5
// val i2: Int Refined Positive = -5 // -- won't compile, predicate failed

// explicit
refineMV[Positive](5)

/*
Macros can only validate literals because their values are known at
compile-time. To validate arbitrary (runtime) values we can use the
refineV function
*/

val x = 42 // suppose the value of x is not known at compile-time

refineV[Positive](x) // Right(42)
refineV[Positive](-x) // Left(Predicate failed: (-42 > 0).)

/*
refined also contains inference rules for converting between different refined types.
For example,
Int Refined Greater[W.`10`.T] can be safely converted to
Int Refined Positive
because all integers greater than ten are also positive.
*/

val a: Int Refined Greater[W.`5`.T] = 10

val b: Int Refined Greater[W.`4`.T] = a

// val c: Int Refined Greater[W.`6`.T] = a // wont compile

