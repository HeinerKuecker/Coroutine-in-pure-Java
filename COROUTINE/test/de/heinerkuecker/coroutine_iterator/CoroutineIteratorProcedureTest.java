package de.heinerkuecker.coroutine_iterator;

import org.junit.Test;

import de.heinerkuecker.coroutine_iterator.step.complex.Procedure;
import de.heinerkuecker.coroutine_iterator.step.retrn.FinallyReturnWithoutResult;
import de.heinerkuecker.coroutine_iterator.step.retrn.YieldReturnValue;
import de.heinerkuecker.coroutine_iterator.step.retrn.YieldReturnVar;
import de.heinerkuecker.coroutine_iterator.step.simple.IncVar;
import de.heinerkuecker.coroutine_iterator.step.simple.SetVar;

/**
 * JUnit4 test case for {@link CoroutineIterator}.
 *
 * @author Heiner K&uuml;cker
 */
public class CoroutineIteratorProcedureTest
{
    @Test( expected = IllegalArgumentException.class )
    public void testConstructor_Empty()
    {
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        new Procedure<Integer>(
                                ) );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    @Test
    public void test_0_0()
    {
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        new Procedure<Integer>(
                                new FinallyReturnWithoutResult<>() ) );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    @Test
    public void test_0_1()
    {
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        new Procedure<Integer>(
                                new YieldReturnValue<>( 0 ) ) );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                0 );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    @Test
    public void test_1_0()
    {
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        new SetVar<>(
                                //varName
                                "counter" ,
                                //varValue
                                0 ) ,
                        new Procedure<Integer>(
                                new IncVar<>( "counter" ) ,
                                new YieldReturnVar<>( "counter" ) ) );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                1 );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    @Test
    public void test_1_1()
    {
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        new Procedure<Integer>(
                                new SetVar<>(
                                        //varName
                                        "counter" ,
                                        //varValue
                                        0 ) ,
                                new IncVar<>( "counter" ) ,
                                new YieldReturnVar<>( "counter" ) ) );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                1 );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    @Test
    public void test_2_0()
    {
        CoroutineIterator.initializationChecks = true;

        final Procedure<Integer> procedure =
                new Procedure<Integer>(
                        new IncVar<>( "counter" ) ,
                        new YieldReturnVar<>( "counter" ) );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        new SetVar<>(
                                //varName
                                "counter" ,
                                //varValue
                                0 ) ,
                        procedure ,
                        procedure );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                1 );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                2 );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

}
