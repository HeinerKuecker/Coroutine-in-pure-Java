package de.heinerkuecker.coroutine.iterator.test;

import org.junit.Test;

import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.condition.Equals;
import de.heinerkuecker.coroutine.condition.False;
import de.heinerkuecker.coroutine.condition.Greater;
import de.heinerkuecker.coroutine.condition.IsTrue;
import de.heinerkuecker.coroutine.condition.Lesser;
import de.heinerkuecker.coroutine.condition.Not;
import de.heinerkuecker.coroutine.condition.Or;
import de.heinerkuecker.coroutine.condition.True;
import de.heinerkuecker.coroutine.expression.GetLocalVar;
import de.heinerkuecker.coroutine.expression.Value;
import de.heinerkuecker.coroutine.step.CoroIterStep;
import de.heinerkuecker.coroutine.step.complex.IfElse;
import de.heinerkuecker.coroutine.step.complex.While;
import de.heinerkuecker.coroutine.step.retrn.FinallyReturn;
import de.heinerkuecker.coroutine.step.retrn.FinallyReturnWithoutResult;
import de.heinerkuecker.coroutine.step.retrn.YieldReturn;
import de.heinerkuecker.coroutine.step.simple.DecLocalVar;
import de.heinerkuecker.coroutine.step.simple.IncLocalVar;
import de.heinerkuecker.coroutine.step.simple.NegateLocalVar;
import de.heinerkuecker.coroutine.step.simple.SetLocalVar;

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
                        new SetLocalVar<Integer>(
                                "number" ,
                                new Value<>(
                                        0 ) ) ,
                        new While<Integer>(
                                // condition
                                new Equals<Integer>(
                                        new GetLocalVar<>(
                                                "number" ) ,
                                        new Value<>(
                                                0 ) ) ,
                                // steps
                                new IncLocalVar<Integer>( "number" ) ) ,
                        new YieldReturn<>(
                                new GetLocalVar<>(
                                        "number" ) ) );

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
                        new SetLocalVar<Integer>(
                                "number" ,
                                new Value<>(
                                        0 ) ) ,
                        new While<>(
                                // condition
                                new Lesser<>(
                                        new GetLocalVar<>(
                                                "number" ) ,
                                        new Value<>(
                                                3 ) ) ,
                                // steps
                                new YieldReturn<>(
                                        new GetLocalVar<>(
                                                "number" ) ) ,
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
                        new SetLocalVar<>(
                                "number" ,
                                new Value<>(
                                        0 ) ) ,
                        new While<>(
                                // condition
                                new Equals<>(
                                        new GetLocalVar<>(
                                                "number" ) ,
                                        new Value<>(
                                                0 ) ) ,
                                // steps
                                new IncLocalVar<>( "number" ) ,
                                new IncLocalVar<>( "number" ) ) ,
                        new YieldReturn<>(
                                new GetLocalVar<>(
                                        "number" ) ) );

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
                        new SetLocalVar<>(
                                "number" ,
                                new Value<>(
                                        3 ) ) ,
                        new While<>(
                                //condition
                                new Not(
                                        new Equals<>(
                                                new GetLocalVar<>(
                                                        "number" ) ,
                                                new Value<>(
                                                        0 ) ) ) ,
                                // steps
                                new DecLocalVar<>( "number" ) ) ,
                        new YieldReturn<>(
                                new GetLocalVar<>(
                                        "number" ) ) );

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
                                new YieldReturn<>(
                                        new Value<>(
                                                0 ) ) ,
                                new FinallyReturn<>(
                                        new Value<>(
                                                1 ) ) ) );

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
                                new YieldReturn<>(
                                        new Value<>(
                                                0 ) ) ,
                                new YieldReturn<>(
                                        new Value<>(
                                                1 ) ) ,
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
                        new SetLocalVar<>(
                                "number" ,
                                new Value<>(
                                        0 ) ) ,
                        new While<Integer/*, CoroutineIterator<Integer>*/>(
                                //condition
                                new Lesser<>(
                                        new GetLocalVar<>(
                                                "number" ) ,
                                        new Value<>(
                                                3 ) ) ,
                                // steps
                                new YieldReturn<>(
                                        new GetLocalVar<>(
                                                "number" ) ) ,
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
                        new SetLocalVar<>(
                                "number" ,
                                new Value<>(
                                        2 ) ) ,
                        new While<Integer/*, CoroutineIterator<Integer>*/>(
                                //condition
                                new Or(
                                        new Greater<>(
                                                new GetLocalVar<>(
                                                        "number" ) ,
                                                new Value<>(
                                                        0 ) ) ,
                                        new Equals<>(
                                                new GetLocalVar<>(
                                                        "number" ) ,
                                                new Value<>(
                                                        0 ) ) ) ,
                                // steps
                                new YieldReturn<>(
                                        new GetLocalVar<>(
                                                "number" ) ) ,
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
                new SetLocalVar<>(
                        "first" ,
                        new Value<>(
                                Boolean.TRUE ) ) ,
                new While<Integer/*, CoroutineIterator<Integer>*/>(
                        //condition
                        new True() ,
                        // steps
                        new IfElse<>(
                                //condition
                                new IsTrue(
                                        new GetLocalVar<>(
                                                "first" ) ) ,
                                // thenSteps
                                (CoroIterStep<Integer/*, CoroutineIterator<Integer>*/>[]) new CoroIterStep[]
                                        {
                                                new YieldReturn<>(
                                                        new Value<>(
                                                                0 ) ) ,
                                        } ,
                                        // elseSteps
                                        (CoroIterStep<Integer/*, CoroutineIterator<Integer>*/>[]) new CoroIterStep[]
                                                {
                                                        new FinallyReturn<>(
                                                                new Value<>(
                                                                        1 ) ) ,
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