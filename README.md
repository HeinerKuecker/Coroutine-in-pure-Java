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

