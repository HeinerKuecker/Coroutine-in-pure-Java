package de.heinerkuecker.coroutine_iterator;

import org.junit.Test;

import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.condition.False;
import de.heinerkuecker.coroutine.expression.Value;
import de.heinerkuecker.coroutine.step.complex.DoWhile;
import de.heinerkuecker.coroutine.step.retrn.YieldReturn;

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
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        new DoWhile<>(
                                // condition
                                new False() ,
                                // steps
                                new YieldReturn<>(
                                        new Value<>(
                                                0 ) ) ) ,
                        new YieldReturn<>(
                                new Value<>(
                                        1 ) ) );

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
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        new DoWhile<>(
                                // condition
                                new False()
                                // no steps
                                ) ,
                        new YieldReturn<>(
                                new Value<>(
                                        1 ) ) );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                1 );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    // TODO more tests

}
