import shapeless.{Coproduct, :+:, CNil, Inl, Inr}

case class Red()
case class Amber()
case class Green()

type Light = Red :+: Amber :+: Green :+: CNil

/*
In general coproducts take the form A :+: B :+: C :+: CNil
meaning “A or B or C”

where :+: can be loosely interpreted as Either

The overall type of a coproduct encodes all the possible types in the disjunction,
but each concrete instance contains a value for just one of the possibilities.

:+: has two subtypes,
Inl and Inr,

that correspond loosely to Left and Right.
 */

/** `H :+: T` can either be `H` or `T`.
 * In this case it is `H`.
 */

val red: Light = Inl(Red())

/** `H :+: T` can either be `H` or `T`.
 * In this case it is `T`.
 */

val green: Light = Inr(Inr(Inl(Green())))

val amber: Light = Inr(Inl(Amber()))
