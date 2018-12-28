package de.heinerkuecker.coroutine_iterator;
import org.junit.Test;

import de.heinerkuecker.coroutine_iterator.condition.Lesser;
import de.heinerkuecker.coroutine_iterator.condition.LesserOrEqual;
import de.heinerkuecker.coroutine_iterator.step.complex.For;
import de.heinerkuecker.coroutine_iterator.step.retrn.YieldReturnVar;
import de.heinerkuecker.coroutine_iterator.step.simple.IncLocalVar;
import de.heinerkuecker.coroutine_iterator.step.simple.NoOperation;
import de.heinerkuecker.coroutine_iterator.step.simple.SetLocalVar;

/**
 * JUnit4 test case for {@link CoroutineIterator}.
 *
 * @author Heiner K&uuml;cker
 */
public class CoroutineIteratorForTest
{
    @Test
    public void test_For_0()
    {
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        new For<>(
                                // initialStep
                                new SetLocalVar<>( "number" , 0 ) ,
                                // condition
                                new Lesser( "number" , 3 ) ,
                                // updateStep
                                new IncLocalVar<>( "number" ) ,
                                // steps
                                new YieldReturnVar<>( "number" ) ) );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                0 );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                1 );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                2 );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    @Test
    public void test_For_00()
    {
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        new SetLocalVar<>( "number" , 0 ) ,
                        new YieldReturnVar<>( "number" ) ,
                        new For<>(
                                // initialStep
                                new NoOperation<>() ,
                                // condition
                                new Lesser( "number" , 3 ) ,
                                // updateStep
                                new IncLocalVar<>( "number" ) ,
                                // steps
                                new YieldReturnVar<>( "number" ) ) );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                0 );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                0 );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                1 );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                2 );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    @Test
    public void test_For_1()
    {
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        new For<>(
                                // initialStep
                                new SetLocalVar<>( "number" , 0 ) ,
                                // condition
                                new LesserOrEqual( "number" , 3 ) ,
                                // updateStep
                                new IncLocalVar<>( "number" ) ,
                                // steps
                                new YieldReturnVar<>( "number" ) ) );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                0 );

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

    @Test
    public void test_For_2()
    {
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        new For<>(
                                // initialStep
                                new SetLocalVar<>( "number" , 0 ) ,
                                // condition
                                new LesserOrEqual( "number" , 6 ) ,
                                // updateStep
                                new IncLocalVar<>( "number" ) ,
                                // steps
                                new IncLocalVar<>( "number" ) ,
                                new YieldReturnVar<>( "number" ) ) );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                1 );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                3 );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                5 );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                7 );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

}
