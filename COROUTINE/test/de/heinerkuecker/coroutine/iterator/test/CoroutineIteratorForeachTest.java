package de.heinerkuecker.coroutine.iterator.test;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import de.heinerkuecker.coroutine.CoroutineDebugSwitches;
import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.exprs.GetLocalVar;
import de.heinerkuecker.coroutine.exprs.Value;
import de.heinerkuecker.coroutine.stmt.complex.ForEach;
import de.heinerkuecker.coroutine.stmt.ret.YieldReturn;

/**
 * JUnit4 test case for {@link CoroutineIterator}.
 *
 * @author Heiner K&uuml;cker
 */
public class CoroutineIteratorForeachTest
{
    @Test
    public void test_For_0()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // stmts
                        new ForEach<Void, Integer, Void , Integer>(
                                // variableName
                                "for_variable" ,
                                // elementType
                                Integer.class ,
                                // iterableExpression
                                new Value<List<Integer>>(
                                        (Class<? extends List<Integer>>) List.class ,
                                        Arrays.asList( 1 , 2 , 3 ) ) ,
                                // stmts
                                new YieldReturn<>(
                                        new GetLocalVar<>(
                                                "for_variable" ,
                                                Integer.class ) ) ) );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                1 );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                2 );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                3 );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    // TODO more tests
}
