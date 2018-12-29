package de.heinerkuecker.coroutine_iterator;

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
import de.heinerkuecker.coroutine_iterator.step.retrn.YieldReturnLocalVar;
import de.heinerkuecker.coroutine_iterator.step.retrn.YieldReturnValue;
import de.heinerkuecker.coroutine_iterator.step.simple.DecLocalVar;
import de.heinerkuecker.coroutine_iterator.step.simple.IncLocalVar;
import de.heinerkuecker.coroutine_iterator.step.simple.NegateLocalVar;
import de.heinerkuecker.coroutine_iterator.step.simple.SetLocalVar;

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
                        new SetLocalVar<Integer>( "number" , 0 ) ,
                        new While<>(
                                // condition
                                new Equals( "number" , 0 ) ,
                                // steps
                                new IncLocalVar<Integer>( "number" ) ) ,
                        new YieldReturnLocalVar<Integer>( "number" ) );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                1 );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    @Test
    public void test_While_1()
    {
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        new SetLocalVar<Integer>( "number" , 0 ) ,
                        new While<>(
                                // condition
                                new Lesser( "number" , 3 ) ,
                                // steps
                                new YieldReturnLocalVar<Integer>( "number" ) ,
                                new IncLocalVar<Integer>( "number" ) ) );

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
                        new SetLocalVar<>( "number" , 0 ) ,
                        new While<>(
                                // condition
                                new Equals( "number" , 0 ) ,
                                // steps
                                new IncLocalVar<>( "number" ) ,
                                new IncLocalVar<>( "number" ) ) ,
                        new YieldReturnLocalVar<>( "number" ) );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                2 );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    @Test
    public void test_While_Not()
    {
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        new SetLocalVar<>( "number" , 3 ) ,
                        new While<>(
                                //condition
                                new Not(
                                        new Equals( "number" , 0 ) ) ,
                                // steps
                                new DecLocalVar<>( "number" ) ) ,
                        new YieldReturnLocalVar<>( "number" ) );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                0 );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
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

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
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

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
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

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
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

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    @Test
    public void test_While_If_1()
    {
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        new SetLocalVar<>( "number" , 0 ) ,
                        new While<Integer/*, CoroutineIterator<Integer>*/>(
                                //condition
                                new Lesser( "number" , 3 ) ,
                                // steps
                                new YieldReturnLocalVar<>( "number" ) ,
                                new IncLocalVar<>( "number" ) ) );

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
    public void test_While_If_2()
    {
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        new SetLocalVar<>( "number" , 2 ) ,
                        new While<Integer/*, CoroutineIterator<Integer>*/>(
                                //condition
                                new Or(
                                        new Greater( "number" , 0 ) ,
                                        new Equals( "number" , 0 ) ) ,
                                // steps
                                new YieldReturnLocalVar<>( "number" ) ,
                                new DecLocalVar<>( "number" ) ) );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                2 );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                1 );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                0 );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    @Test
    public void test_While_IfElse()
    {
        CoroutineIterator.initializationChecks = true;

        @SuppressWarnings("unchecked")
        final CoroutineIterator<Integer> coroIter =
        new CoroutineIterator<Integer>(
                new SetLocalVar<>( "first" , Boolean.TRUE ) ,
                new While<Integer/*, CoroutineIterator<Integer>*/>(
                        //condition
                        new True() ,
                        // steps
                        new IfElse<>(
                                //condition
                                new IsTrue( "first" ) ,
                                // thenSteps
                                (CoroIterStep<Integer/*, CoroutineIterator<Integer>*/>[]) new CoroIterStep[]
                                {
                                        new YieldReturnValue<>( 0 ) ,
                                } ,
                                // elseSteps
                                (CoroIterStep<Integer/*, CoroutineIterator<Integer>*/>[]) new CoroIterStep[]
                                {
                                        new FinallyReturnValue<>( 1 ) ,
                                } ) ,
                        new NegateLocalVar<>( "first" ) ) );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                0 );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                1 );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

}
