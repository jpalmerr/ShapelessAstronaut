use shapeless to derive type class instances for product types (i.e. case classes)

- If we have type class instances for the head and tail of an HList,
    we can derive an instance for the whole HList.
    
- If we have a `case class A`, a `Generic[A]`, and a type class instance for the `generic’s Repr`,
    we can combine them to create an instance for `A`.
    
----------------------------



The commonly accepted idioma􏰀c style for type class defini􏰀ons includes a companion object containing some standard methods


------------------------------


In this sec􏰀on we’re going to use shapeless to derive type class instances for product types (i.e. case classes).
We’ll use two intuit􏰀ons:

1. If we have type class instances for the head and tail of an HList, we can derive an instance for the whole HList.
2. If we have a case class A, a Generic[A], and a type class instance for the generic’s Repr, we can combine them to create an instance for A.    