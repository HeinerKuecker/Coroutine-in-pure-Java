package de.heinerkuecker.coroutine_iterator;

import org.junit.Assert;
import org.junit.Test;

import de.heinerkuecker.coroutine_iterator.condition.False;
import de.heinerkuecker.coroutine_iterator.step.complex.DoWhile;
import de.heinerkuecker.coroutine_iterator.step.retrn.YieldReturnValue;

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
                                new YieldReturnValue<Integer>( 0 ) ) ,
                        new YieldReturnValue<Integer>( 1 ) );

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
                        new YieldReturnValue<Integer>( 1 ) );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                1 );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    // TODO more tests

}
