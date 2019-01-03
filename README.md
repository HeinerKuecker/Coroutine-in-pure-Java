# Coroutine in pure Java
Coroutine as iterator or procedure with yield return only with pure Java.

This lib contains an iterator with yield return.

This lib needs

-no Threads

-no confusing *Rx lib (I dont understand what flatMap means :) )

-no memory consuming Buffer

-no Bytecode manipulation

-no Java-agent

Just pure java.


Java8 lambdas and method references are usable but not necessary.

Therefore, this code should also work with older Java versions, possibly with insignificant changes, such as removing the SafeVarargs annotation.

The use cases for this library are coroutines with or without an iterator, where managing the state is too difficult for self-written code.

In the coroutine is the full power of the Turing machine available, not just an state machine.

In the coroutine procedures can be used, which can call themselves recursively or each other.

For users of Kotlin, this library is probably useless because Kotlin himself has coroutines.
