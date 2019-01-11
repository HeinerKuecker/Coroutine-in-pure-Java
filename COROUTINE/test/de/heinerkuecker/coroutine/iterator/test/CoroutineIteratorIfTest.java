package de.heinerkuecker.coroutine.iterator.test;

import org.junit.Test;

import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.condition.Equals;
import de.heinerkuecker.coroutine.condition.True;
import de.heinerkuecker.coroutine.expression.GetLocalVar;
import de.heinerkuecker.coroutine.step.complex.If;
import de.heinerkuecker.coroutine.step.complex.While;
import de.heinerkuecker.coroutine.step.ret.FinallyReturn;
import de.heinerkuecker.coroutine.step.ret.YieldReturn;
import de.heinerkuecker.coroutine.step.simple.IncrementLocalVar;
import de.heinerkuecker.coroutine.step.simple.SetLocalVar;

/**
 * JUnit4 test case for {@link CoroutineIterator}.
 *
 * @author Heiner K&uuml;cker
 */
public class CoroutineIteratorIfTest
{
    @Test
    public void test_If_True_0()
    {
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // steps
                        new If<Integer/*, CoroutineIterator<Integer>*/>(
                                //condition
                                //new True()
                                true ,
                                // steps
                                new YieldReturn<>( 0 ) ,
                                new FinallyReturn<>( 1 ) ) );

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
    public void test_If_True_1()
    {
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // steps
                        new SetLocalVar<>(
                                //varName
                                "condition_var" ,
                                //varValue
                                true ) ,
                        new If<Integer/*, CoroutineIterator<Integer>*/>(
                                //condition
                                //new True() ,
                                new GetLocalVar<>(
                                        // localVarName
                                        "condition_var" ,
                                        // type
                                        Boolean.class ) ,
                                // steps
                                new YieldReturn<>( 0 ) ,
                                new FinallyReturn<>( 1 ) ) );

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
                        // type
                        Integer.class ,
                        // steps
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
                        // type
                        Integer.class ,
                        // steps
                        new SetLocalVar<>(
                                "number" ,
                                0 ) ,
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
                                                0 ) ,
                                        // steps
                                        new YieldReturn<>( 0 ) ) ,
                                new If<Integer/*, CoroutineIterator<Integer>*/>(
                                        //condition
                                        new Equals<>(
                                                new GetLocalVar<>(
                                                        "number" ,
                                                        Integer.class ) ,
                                                1 ) ,
                                        // steps
                                        new FinallyReturn<>( 1 ) ) ,
                                new IncrementLocalVar<>( "number" ) ) );

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
