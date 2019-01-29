package de.heinerkuecker.coroutine.iterator.test;

import org.junit.Test;

import de.heinerkuecker.coroutine.CoroutineDebugSwitches;
import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.stmt.complex.DoWhile;
import de.heinerkuecker.coroutine.stmt.ret.YieldReturn;

/**
 * JUnit4 test case for {@link CoroutineIterator}.
 *
 * @author Heiner K&uuml;cker
 */
public class CoroutineIteratorDoWhileTest
{
    @Test
    public void test_DoWhile_0()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // steps
                        new DoWhile<>(
                                // condition
                                false ,
                                // steps
                                new YieldReturn<>( 0 ) ) ,
                        new YieldReturn<>( 1 ) );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                0 );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                1 );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    @Test
    public void test_DoWhile_1()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // steps
                        new DoWhile<>(
                                // condition
                                false
                                // no steps
                                ) ,
                        new YieldReturn<>( 1 ) );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                1 );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    // TODO more tests

}
