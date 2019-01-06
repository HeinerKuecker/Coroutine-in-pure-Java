package de.heinerkuecker.coroutine.iterator.test;

import org.junit.Test;

import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.condition.Equals;
import de.heinerkuecker.coroutine.condition.True;
import de.heinerkuecker.coroutine.expression.GetLocalVar;
import de.heinerkuecker.coroutine.expression.Value;
import de.heinerkuecker.coroutine.step.complex.If;
import de.heinerkuecker.coroutine.step.complex.While;
import de.heinerkuecker.coroutine.step.retrn.FinallyReturn;
import de.heinerkuecker.coroutine.step.retrn.YieldReturn;
import de.heinerkuecker.coroutine.step.simple.IncLocalVar;
import de.heinerkuecker.coroutine.step.simple.SetLocalVar;

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
                        new If<Integer/*, CoroutineIterator<Integer>*/>(
                                //condition
                                new True() ,
                                // steps
                                new YieldReturn<>(
                                        new Value<>( 0 ) ) ,
                                new FinallyReturn<>(
                                        new Value<>( 1 ) ) ) );

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
    public void test_If_True_No_Steps()
    {
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        new If<Integer/*, CoroutineIterator<Integer>*/>(
                                //condition
                                new True()
                                // steps
                                ) );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    @Test
    public void test_While_If_0()
    {
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        new SetLocalVar<>(
                                "number" ,
                                new Value<>( 0 ) ) ,
                        new While<Integer/*, CoroutineIterator<Integer>*/>(
                                //condition
                                new True() ,
                                // steps
                                new If<Integer/*, CoroutineIterator<Integer>*/>(
                                        //condition
                                        new Equals<>(
                                                new GetLocalVar<>(
                                                        "number" ,
                                                        Integer.class ) ,
                                                new Value<>( 0 ) ) ,
                                        // steps
                                        new YieldReturn<>(
                                                new Value<>( 0 ) ) ) ,
                                new If<Integer/*, CoroutineIterator<Integer>*/>(
                                        //condition
                                        new Equals<>(
                                                new GetLocalVar<>(
                                                        "number" ,
                                                        Integer.class ) ,
                                                new Value<>( 1 ) ) ,
                                        // steps
                                        new FinallyReturn<>(
                                                new Value<>( 1 ) ) ) ,
                                new IncLocalVar<>( "number" ) ) );

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
