package de.heinerkuecker.coroutine.iterator.test;

import org.junit.Test;

import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.condition.Lesser;
import de.heinerkuecker.coroutine.condition.LesserOrEqual;
import de.heinerkuecker.coroutine.expression.GetLocalVar;
import de.heinerkuecker.coroutine.step.complex.For;
import de.heinerkuecker.coroutine.step.ret.YieldReturn;
import de.heinerkuecker.coroutine.step.simple.IncrementLocalVar;
import de.heinerkuecker.coroutine.step.simple.NegateLocalVar;
import de.heinerkuecker.coroutine.step.simple.NoOperation;
import de.heinerkuecker.coroutine.step.simple.SetLocalVar;

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

        // extract get local variable expression
        final GetLocalVar<Integer> number =
                new GetLocalVar<>(
                        // varName
                        "number" ,
                        Integer.class );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // steps
                        new For<>(
                                // initialStep
                                new SetLocalVar<>(
                                        "number" ,
                                        0 ) ,
                                // condition
                                new Lesser<>(
                                        number ,
                                        3 ) ,
                                // updateStep
                                new IncrementLocalVar<>( "number" ) ,
                                // steps
                                new YieldReturn<>( number ) ) );

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

        // extract get local variable expression
        final GetLocalVar<Integer> number =
                new GetLocalVar<>(
                        // varName
                        "number" ,
                        Integer.class );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // steps
                        new SetLocalVar<>(
                                "number" ,
                                0 ) ,
                        new YieldReturn<>( number ) ,
                        new For<>(
                                // initialStep
                                new NoOperation<>() ,
                                // condition
                                new Lesser<>(
                                        number ,
                                        3 ) ,
                                // updateStep
                                new IncrementLocalVar<>( "number" ) ,
                                // steps
                                new YieldReturn<>( number ) ) );

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

        // extract get local variable expression
        final GetLocalVar<Integer> number =
                new GetLocalVar<>(
                        // varName
                        "number" ,
                        Integer.class );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // steps
                        new For<>(
                                // initialStep
                                new SetLocalVar<>(
                                        "number" ,
                                        0 ) ,
                                // condition
                                new LesserOrEqual<>(
                                        number ,
                                        3 ) ,
                                // updateStep
                                new IncrementLocalVar<>( "number" ) ,
                                // steps
                                new YieldReturn<>( number ) ) );

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

        // extract get local variable expression
        final GetLocalVar<Integer> number =
                new GetLocalVar<>(
                        // varName
                        "number" ,
                        Integer.class );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // steps
                        new For<>(
                                // initialStep
                                new SetLocalVar<>(
                                        "number" ,
                                        0 ) ,
                                // condition
                                new LesserOrEqual<>(
                                        number ,
                                        6 ) ,
                                // updateStep
                                new IncrementLocalVar<>( "number" ) ,
                                // steps
                                new IncrementLocalVar<>( "number" ) ,
                                new YieldReturn<>( number ) ) );

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

    @Test
    public void test_For_3()
    {
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // steps
                        new For<>(
                                // initialStep
                                new SetLocalVar<>(
                                        // varName
                                        "first_round" ,
                                        // varValue
                                        true ) ,
                                // condition
                                new GetLocalVar<>(
                                        // localVarName
                                        "first_round" ,
                                        // type
                                        Boolean.class ) ,
                                // updateStep
                                new NegateLocalVar<>( "first_round" ) ,
                                // steps
                                new YieldReturn<>( 0 ) ) );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                0 );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

}
