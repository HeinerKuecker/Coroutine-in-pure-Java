package de.heinerkuecker.coroutine_iterator;

import org.junit.Assert;
import org.junit.Test;

import de.heinerkuecker.coroutine_iterator.condition.Equals;
import de.heinerkuecker.coroutine_iterator.condition.True;
import de.heinerkuecker.coroutine_iterator.step.complex.If;
import de.heinerkuecker.coroutine_iterator.step.complex.While;
import de.heinerkuecker.coroutine_iterator.step.retrn.FinallyReturnValue;
import de.heinerkuecker.coroutine_iterator.step.retrn.YieldReturnValue;
import de.heinerkuecker.coroutine_iterator.step.simple.IncVar;
import de.heinerkuecker.coroutine_iterator.step.simple.SetVar;

/**
 * JUnit4 test case for {@link CoroutineIterator}.
 *
 * @author Heiner K&uuml;cker
 */
public class CoroutineIteratorIfTest
{
    @Test
    public void test_If_True()
    {
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        new If<Integer, CoroutineIterator<Integer>>(
                                //condition
                                new True() ,
                                // steps
                                new YieldReturnValue<>( 0 ) ,
                                new FinallyReturnValue<>( 1 ) ) );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                0 );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                1 );

        Assert.assertFalse(
                coroIter.hasNext() );
    }

    @Test
    public void test_If_True_No_Steps()
    {
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        new If<Integer, CoroutineIterator<Integer>>(
                                //condition
                                new True()
                                // steps
                                ) );

        Assert.assertFalse(
                coroIter.hasNext() );
    }

    @Test
    public void test_While_If_0()
    {
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        new SetVar<>( "number" , 0 ) ,
                        new While<Integer, CoroutineIterator<Integer>>(
                                //condition
                                new True() ,
                                // steps
                                new If<Integer, CoroutineIterator<Integer>>(
                                        //condition
                                        new Equals( "number" , 0 ) ,
                                        // steps
                                        new YieldReturnValue<>( 0 ) ) ,
                                new If<Integer, CoroutineIterator<Integer>>(
                                        //condition
                                        new Equals( "number" , 1 ) ,
                                        // steps
                                        new FinallyReturnValue<>( 1 ) ) ,
                                new IncVar<>( "number" ) ) );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                0 );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                1 );

        Assert.assertFalse(
                coroIter.hasNext() );
    }

}
