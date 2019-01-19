package de.heinerkuecker.coroutine.iterator.test;

import org.junit.Test;

import de.heinerkuecker.coroutine.CoroutineDebugSwitches;
import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.condition.Equals;
import de.heinerkuecker.coroutine.condition.False;
import de.heinerkuecker.coroutine.condition.Greater;
import de.heinerkuecker.coroutine.condition.GreaterOrEqual;
import de.heinerkuecker.coroutine.condition.Lesser;
import de.heinerkuecker.coroutine.condition.Not;
import de.heinerkuecker.coroutine.condition.Or;
import de.heinerkuecker.coroutine.condition.True;
import de.heinerkuecker.coroutine.expression.Add;
import de.heinerkuecker.coroutine.expression.CastToInt;
import de.heinerkuecker.coroutine.expression.GetLocalVar;
import de.heinerkuecker.coroutine.expression.Multiply;
import de.heinerkuecker.coroutine.expression.Value;
import de.heinerkuecker.coroutine.step.CoroIterStep;
import de.heinerkuecker.coroutine.step.complex.IfElse;
import de.heinerkuecker.coroutine.step.complex.While;
import de.heinerkuecker.coroutine.step.ret.FinallyReturn;
import de.heinerkuecker.coroutine.step.ret.FinallyReturnWithoutResult;
import de.heinerkuecker.coroutine.step.ret.YieldReturn;
import de.heinerkuecker.coroutine.step.simple.DeclareLocalVar;
import de.heinerkuecker.coroutine.step.simple.DecrementLocalVar;
import de.heinerkuecker.coroutine.step.simple.IncrementLocalVar;
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
        CoroutineDebugSwitches.initializationChecks = true;

        // extract get local variable expression
        final GetLocalVar<Integer> number =
                new GetLocalVar<>(
                        // varName
                        "number" ,
                        Integer.class );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<>(
                        // type
                        Integer.class ,
                        // steps
                        new DeclareLocalVar<>(
                                "number" ,
                                0 ) ,
                        new While<>(
                                // condition
                                new Equals<Integer>(
                                        number ,
                                        0 ) ,
                                // steps
                                new IncrementLocalVar<>( "number" ) ) ,
                        new YieldReturn<>( number ) );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                1 );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    @Test
    public void test_While_1()
    {
        CoroutineDebugSwitches.initializationChecks = true;

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
                        new DeclareLocalVar<>(
                                "number" ,
                                0 ) ,
                        new While<>(
                                // condition
                                new Lesser<>(
                                        number ,
                                        3 ) ,
                                // steps
                                new YieldReturn<>( number ) ,
                                new IncrementLocalVar<>( "number" ) ) );

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
        CoroutineDebugSwitches.initializationChecks = true;

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
                        new DeclareLocalVar<>(
                                "number" ,
                                0 ) ,
                        new While<>(
                                // condition
                                new Equals<>(
                                        number ,
                                        0 ) ,
                                // steps
                                new IncrementLocalVar<>( "number" ) ,
                                new IncrementLocalVar<>( "number" ) ) ,
                        new YieldReturn<>( number ) );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                2 );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    @Test
    public void test_While_Not()
    {
        CoroutineDebugSwitches.initializationChecks = true;

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
                        new DeclareLocalVar<>(
                                "number" ,
                                3 ) ,
                        new While<>(
                                //condition
                                new Not(
                                        new Equals<>(
                                                number ,
                                                0 ) ) ,
                                // steps
                                new DecrementLocalVar<>( "number" ) ) ,
                        new YieldReturn<>( number ) );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                0 );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    @Test
    public void test_While_While_Not_0()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // steps
                        new DeclareLocalVar<>(
                                "number0" ,
                                Integer.class ) ,
                        new DeclareLocalVar<>(
                                "number1" ,
                                Integer.class ) ,
                        new SetLocalVar<>(
                                "number0" ,
                                3 ) ,
                        new While<Integer>(
                                //condition
                                new GreaterOrEqual<>(
                                        new GetLocalVar<>(
                                                "number0" ,
                                                Integer.class ) ,
                                        0 ) ,
                                // steps
                                new SetLocalVar<>(
                                        "number1" ,
                                        3 ) ,
                                new While<Integer>(
                                        //condition
                                        new GreaterOrEqual<>(
                                                new GetLocalVar<>(
                                                        "number1" ,
                                                        Integer.class ) ,
                                                0 ) ,
                                        // steps
                                        new YieldReturn<Integer>(
                                                new CastToInt<>(
                                                        new Add<Integer>(
                                                                new Multiply<Integer>(
                                                                        new GetLocalVar<>(
                                                                                "number0" ,
                                                                                Integer.class ) ,
                                                                        Value.intValue( 4 ) ) ,
                                                                new GetLocalVar<>(
                                                                        "number1" ,
                                                                        Integer.class ) ) ) ) ,
                                        new DecrementLocalVar<>( "number1" ) ) ,
                                new DecrementLocalVar<>( "number0" ) ) );

        for ( int i = 15 ; i >= 0 ; i-- )
        {
            CoroutineIteratorTest.assertNext(
                    coroIter ,
                    i );
        }

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    @Test
    public void test_While_While_Not_1()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // steps
                        new DeclareLocalVar<>(
                                "number0" ,
                                3 ) ,
                        new While<Integer>(
                                //condition
                                new GreaterOrEqual<>(
                                        new GetLocalVar<>(
                                                "number0" ,
                                                Integer.class ) ,
                                        0 ) ,
                                // steps
                                new DeclareLocalVar<>(
                                        "number1" ,
                                        3 ) ,
                                new While<Integer>(
                                        //condition
                                        new GreaterOrEqual<>(
                                                new GetLocalVar<>(
                                                        "number1" ,
                                                        Integer.class ) ,
                                                0 ) ,
                                        // steps
                                        new YieldReturn<Integer>(
                                                new CastToInt<>(
                                                        new Add<Integer>(
                                                                new Multiply<Integer>(
                                                                        new GetLocalVar<>(
                                                                                "number0" ,
                                                                                Integer.class ) ,
                                                                        Value.intValue( 4 ) ) ,
                                                                new GetLocalVar<>(
                                                                        "number1" ,
                                                                        Integer.class ) ) ) ) ,
                                        new DecrementLocalVar<>( "number1" ) ) ,
                                new DecrementLocalVar<>( "number0" ) ) );

        for ( int i = 15 ; i >= 0 ; i-- )
        {
            CoroutineIteratorTest.assertNext(
                    coroIter ,
                    i );
        }

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    @Test
    public void test_While_True()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // steps
                        new While<>(
                                //condition
                                new True() ,
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

    // endless loop: @Test
    public void test_While_No_Steps_Endless_Loop()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // steps
                        new While<>(
                                //condition
                                true
                                // steps
                                ) );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    @Test
    public void test_While_No_Steps()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // steps
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
        CoroutineDebugSwitches.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // steps
                        new While<>(
                                //condition
                                new True() ,
                                // steps
                                new YieldReturn<>( 0 ) ,
                                new YieldReturn<>( 1 ) ,
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
        CoroutineDebugSwitches.initializationChecks = true;

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
                        new DeclareLocalVar<>(
                                "number" ,
                                0 ) ,
                        new While<Integer/*, CoroutineIterator<Integer>*/>(
                                //condition
                                new Lesser<>(
                                        number ,
                                        3 ) ,
                                // steps
                                new YieldReturn<>( number  ) ,
                                new IncrementLocalVar<>( "number" ) ) );

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
        CoroutineDebugSwitches.initializationChecks = true;

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
                        new DeclareLocalVar<>(
                                "number" ,
                                2 ) ,
                        new While<Integer/*, CoroutineIterator<Integer>*/>(
                                //condition
                                new Or(
                                        new Greater<>(
                                                number ,
                                                0 ) ,
                                        new Equals<>(
                                                number ,
                                                0 ) ) ,
                                // steps
                                new YieldReturn<>( number ) ,
                                new DecrementLocalVar<>( "number" ) ) );

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
        CoroutineDebugSwitches.initializationChecks = true;

        @SuppressWarnings("unchecked")
        final CoroutineIterator<Integer> coroIter =
        new CoroutineIterator<Integer>(
                // type
                Integer.class ,
                // steps
                new DeclareLocalVar<>(
                        "first" ,
                        true ) ,
                new While<Integer/*, CoroutineIterator<Integer>*/>(
                        // condition
                        new True() ,
                        // steps
                        new IfElse<>(
                                //condition
                                new GetLocalVar<>(
                                        "first" ,
                                        Boolean.class ) ,
                                // thenSteps
                                (CoroIterStep<Integer/*, CoroutineIterator<Integer>*/>[]) new CoroIterStep[] {
                                        new YieldReturn<>( 0 ) ,
                                } ,
                                // elseSteps
                                (CoroIterStep<Integer/*, CoroutineIterator<Integer>*/>[]) new CoroIterStep[] {
                                        new FinallyReturn<>( 1 ) ,
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
