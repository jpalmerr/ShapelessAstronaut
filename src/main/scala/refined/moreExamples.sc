//import eu.timepit.refined._
import eu.timepit.refined.refineMV
import eu.timepit.refined.W
import eu.timepit.refined.api.Refined
import eu.timepit.refined.auto._
import eu.timepit.refined.boolean.{And, AnyOf, Not}
import eu.timepit.refined.char.{Digit, Letter, Whitespace}
import eu.timepit.refined.numeric.{Greater, Less}
import eu.timepit.refined.collection._
import eu.timepit.refined.string.{Regex, Url}
import shapeless.{::, HNil}

refineMV[NonEmpty]("Hello")

type ZeroToOne = Not[Less[W.`0.0`.T]] And Not[Greater[W.`1.0`.T]]

refineMV[ZeroToOne](0.8)

refineMV[AnyOf[Digit :: Letter :: Whitespace :: HNil]]('F')

val r1: String Refined Regex = "(a|b)"

val u1: String Refined Url = "http://example.com"

case class Urls(url: String Refined Url)

Urls("http://example.com")
