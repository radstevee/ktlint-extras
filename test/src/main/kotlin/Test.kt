private fun someHigherOrderFunction(block: (Unit) -> Unit) {}

private fun testImplicitIt() {
  someHigherOrderFunction { it -> println(it) }
  someHigherOrderFunction { println(it) } // Only this should fail.
  someHigherOrderFunction { unit -> println(unit) }
  someHigherOrderFunction(::println)
}

/** Some KDoc - This should pass. */
private val thing = Unit

/**
 * Some KDoc - This should fail.
 */
private val otherThing = Unit
