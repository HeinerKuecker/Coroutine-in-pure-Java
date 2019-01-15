# Coroutine in pure Java
Coroutine as iterator or procedure with yield return only with pure Java.

This lib needs

-no Threads

-no confusing *Rx lib (I dont understand what flatMap means :) )

-no memory consuming Buffer

-no Bytecode manipulation

-no Java-agent

Just pure java.

For users of Kotlin, this library is probably useless because Kotlin himself has coroutines.

Java8 lambdas and method references are usable but not necessary.

Therefore, this code should also work with older Java versions, possibly with insignificant changes, such as removing the SafeVarargs annotation.

The use cases for this library are coroutines with or without an iterator, where managing the state is too difficult for self-written code.

There are symmetric and asymmetrical coroutines, this library only supports asymmetric coroutines because I have no use for symmetric coroutines.

In the coroutine is the full power of the Turing machine available, not just an state machine.

In the coroutine procedures can be used, which can call themselves or each other recursively.

The programming of functions is not possible and not planned.

Instead of formulating functions in the steps of the coroutine, custom functions should be implemented as derivatives of CoroExpression in Java.

The toString method returns debug information such as the last step, next step, global variables, local variables and procedure parameters.

In the toString method, the steps of currently running procedures are output, which makes the display different from the usual debuggers, but does not need to be navigated within the stack.

The stack is visible through the issued procedures with their parameters and local variables as part of the coroutine code (steps).

This repository contains an eclipse project COROUTINE to import in your IDE.

To extend this library for your own tasks, you can extend the Java classes SimpleStep and CoroExpression using the existing code.

Local and global variables are stored in HashMaps.

The parameters of the procedures are stored in immutable maps because I am a supporter of immutable parameters.
