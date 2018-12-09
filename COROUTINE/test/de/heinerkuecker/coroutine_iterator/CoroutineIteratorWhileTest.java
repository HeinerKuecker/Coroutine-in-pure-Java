package de.heinerkuecker.coroutine_iterator;

import org.junit.Assert;
import org.junit.Test;

import de.heinerkuecker.coroutine_iterator.condition.Equals;
import de.heinerkuecker.coroutine_iterator.condition.False;
import de.heinerkuecker.coroutine_iterator.condition.Greater;
import de.heinerkuecker.coroutine_iterator.condition.IsTrue;
import de.heinerkuecker.coroutine_iterator.condition.Lesser;
import de.heinerkuecker.coroutine_iterator.condition.Not;
import de.heinerkuecker.coroutine_iterator.condition.Or;
import de.heinerkuecker.coroutine_iterator.condition.True;
import de.heinerkuecker.coroutine_iterator.step.CoroIterStep;
import de.heinerkuecker.coroutine_iterator.step.complex.IfElse;
import de.heinerkuecker.coroutine_iterator.step.complex.While;
import de.heinerkuecker.coroutine_iterator.step.retrn.FinallyReturnValue;
import de.heinerkuecker.coroutine_iterator.step.retrn.FinallyReturnWithoutResult;
import de.heinerkuecker.coroutine_iterator.step.retrn.YieldReturnValue;
import de.heinerkuecker.coroutine_iterator.step.retrn.YieldReturnVar;
import de.heinerkuecker.coroutine_iterator.step.simple.DecVar;
import de.heinerkuecker.coroutine_iterator.step.simple.IncVar;
import de.heinerkuecker.coroutine_iterator.step.simple.Negate;
import de.heinerkuecker.coroutine_iterator.step.simple.SetVar;

/**
 * JUnit4 test case for {@link CoroutineIterator}.
 *
 * @author Heiner K&uuml;cker
 */
public class CoroutineIteratorWhileTest
{
    @Test
    public void test_While_0()
    {
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        new SetVar<Integer>( "number" , 0 ) ,
                        new While<>(
                                // condition
                                new Equals( "number" , 0 ) ,
                                // steps
                                new IncVar<Integer>( "number" ) ) ,
                        new YieldReturnVar<Integer>( "number" ) );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                1 );

        Assert.assertFalse(
                coroIter.hasNext() );
    }

    @Test
    public void test_While_1()
    {
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        new SetVar<Integer>( "number" , 0 ) ,
                        new While<>(
                                // condition
                                new Lesser( "number" , 3 ) ,
                                // steps
                                new YieldReturnVar<Integer>( "number" ) ,
                                new IncVar<Integer>( "number" ) ) );

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
    public void test_While_2()
    {
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        new SetVar<>( "number" , 0 ) ,
                        new While<>(
                                // condition
                                new Equals( "number" , 0 ) ,
                                // steps
                                new IncVar<>( "number" ) ,
                                new IncVar<>( "number" ) ) ,
                        new YieldReturnVar<>( "number" ) );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                2 );

        Assert.assertFalse(
                coroIter.hasNext() );
    }

    @Test
    public void test_While_Not()
    {
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        new SetVar<>( "number" , 3 ) ,
                        new While<>(
                                //condition
                                new Not(
                                        new Equals( "number" , 0 ) ) ,
                                // steps
                                new DecVar<>( "number" ) ) ,
                        new YieldReturnVar<>( "number" ) );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                0 );

        Assert.assertFalse(
                coroIter.hasNext() );
    }

    @Test
    public void test_While_True()
    {
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        new While<>(
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

    // endless loop: @Test
    public void test_While_No_Steps_Endless_Loop()
    {
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        new While<>(
                                //condition
                                new True()
                                // steps
                                ) );

        Assert.assertFalse(
                coroIter.hasNext() );
    }

    @Test
    public void test_While_No_Steps()
    {
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        new While<>(
                                //condition
                                new False()
                                // steps
                                ) );

        Assert.assertFalse(
                coroIter.hasNext() );
    }

    @Test
    public void test_While_True_FinallyReturnWithoutResult()
    {
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        new While<>(
                                //condition
                                new True() ,
                                // steps
                                new YieldReturnValue<>( 0 ) ,
                                new YieldReturnValue<>( 1 ) ,
                                new FinallyReturnWithoutResult<>() ) );

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
    public void test_While_If_1()
    {
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        new SetVar<>( "number" , 0 ) ,
                        new While<Integer, CoroutineIterator<Integer>>(
                                //condition
                                new Lesser( "number" , 3 ) ,
                                // steps
                                new YieldReturnVar<>( "number" ) ,
                                new IncVar<>( "number" ) ) );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                0 );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                1 );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                2 );

        Assert.assertFalse(
                coroIter.hasNext() );
    }

    @Test
    public void test_While_If_2()
    {
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        new SetVar<>( "number" , 2 ) ,
                        new While<Integer, CoroutineIterator<Integer>>(
                                //condition
                                new Or(
                                        new Greater( "number" , 0 ) ,
                                        new Equals( "number" , 0 ) ) ,
                                // steps
                                new YieldReturnVar<>( "number" ) ,
                                new DecVar<>( "number" ) ) );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                2 );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                1 );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                0 );

        Assert.assertFalse(
                coroIter.hasNext() );
    }

    @Test
    public void test_While_IfElse()
    {
        CoroutineIterator.initializationChecks = true;

        @SuppressWarnings("unchecked")
        final CoroutineIterator<Integer> coroIter =
        new CoroutineIterator<Integer>(
                new SetVar<>( "first" , Boolean.TRUE ) ,
                new While<Integer, CoroutineIterator<Integer>>(
                        //condition
                        new True() ,
                        // steps
                        new IfElse<>(
                                //condition
                                new IsTrue( "first" ) ,
                                // thenSteps
                                (CoroIterStep<Integer, CoroutineIterator<Integer>>[]) new CoroIterStep[]
                                {
                                        new YieldReturnValue<>( 0 ) ,
                                } ,
                                // elseSteps
                                (CoroIterStep<Integer, CoroutineIterator<Integer>>[]) new CoroIterStep[]
                                {
                                        new FinallyReturnValue<>( 1 ) ,
                                } ) ,
                        new Negate<>( "first" ) ) );

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
