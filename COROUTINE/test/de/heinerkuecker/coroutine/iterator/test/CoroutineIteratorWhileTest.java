package de.heinerkuecker.coroutine.iterator.test;

import org.junit.Test;

import de.heinerkuecker.coroutine.CoroutineDebugSwitches;
import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.exprs.Add;
import de.heinerkuecker.coroutine.exprs.CastToInt;
import de.heinerkuecker.coroutine.exprs.GetLocalVar;
import de.heinerkuecker.coroutine.exprs.Multiply;
import de.heinerkuecker.coroutine.exprs.Value;
import de.heinerkuecker.coroutine.exprs.bool.Equals;
import de.heinerkuecker.coroutine.exprs.bool.Greater;
import de.heinerkuecker.coroutine.exprs.bool.GreaterOrEqual;
import de.heinerkuecker.coroutine.exprs.bool.Lesser;
import de.heinerkuecker.coroutine.exprs.bool.Not;
import de.heinerkuecker.coroutine.exprs.bool.Or;
import de.heinerkuecker.coroutine.stmt.CoroIterStep;
import de.heinerkuecker.coroutine.stmt.complex.IfElse;
import de.heinerkuecker.coroutine.stmt.complex.While;
import de.heinerkuecker.coroutine.stmt.ret.FinallyReturn;
import de.heinerkuecker.coroutine.stmt.ret.FinallyReturnWithoutResult;
import de.heinerkuecker.coroutine.stmt.ret.YieldReturn;
import de.heinerkuecker.coroutine.stmt.simple.DeclareVariable;
import de.heinerkuecker.coroutine.stmt.simple.DecrementLocalVar;
import de.heinerkuecker.coroutine.stmt.simple.IncrementLocalVar;
import de.heinerkuecker.coroutine.stmt.simple.NegateLocalVar;
import de.heinerkuecker.coroutine.stmt.simple.SetLocalVar;

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
                        new DeclareVariable<>(
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
                        new DeclareVariable<>(
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
                        new DeclareVariable<>(
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
                        new DeclareVariable<>(
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
                        new DeclareVariable<>(
                                "number0" ,
                                Integer.class ) ,
                        new DeclareVariable<>(
                                "number1" ,
                                Integer.class ) ,
                        new SetLocalVar<>(
                                "number0" ,
                                3 ) ,
                        new While<Integer , Void>(
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
                                new While<>(
                                        //condition
                                        new GreaterOrEqual<>(
                                                new GetLocalVar<>(
                                                        "number1" ,
                                                        Integer.class ) ,
                                                0 ) ,
                                        // steps
                                        new YieldReturn<>(
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
                        new DeclareVariable<>(
                                "number0" ,
                                3 ) ,
                        new While<Integer , Void>(
                                //condition
                                new GreaterOrEqual<>(
                                        new GetLocalVar<>(
                                                "number0" ,
                                                Integer.class ) ,
                                        0 ) ,
                                // steps
                                new DeclareVariable<>(
                                        "number1" ,
                                        3 ) ,
                                new While<>(
                                        //condition
                                        new GreaterOrEqual<>(
                                                new GetLocalVar<>(
                                                        "number1" ,
                                                        Integer.class ) ,
                                                0 ) ,
                                        // steps
                                        new YieldReturn<>(
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
    public void test_While_While_Not_2()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // steps
                        new DeclareVariable<>(
                                "number0" ,
                                3 ) ,
                        new While<Integer , Void>(
                                //condition
                                new GreaterOrEqual<>(
                                        new DeclareVariable<>(
                                                "number00" ,
                                                Integer.class ,
                                                new GetLocalVar<>(
                                                        "number0" ,
                                                        Integer.class ) ) ,
                                        0 ) ,
                                // steps
                                new DeclareVariable<>(
                                        "number1" ,
                                        3 ) ,
                                new While<>(
                                        //condition
                                        new GreaterOrEqual<>(
                                                new GetLocalVar<>(
                                                        "number1" ,
                                                        Integer.class ) ,
                                                0 ) ,
                                        // steps
                                        new YieldReturn<>(
                                                new CastToInt<>(
                                                        new Add<Integer>(
                                                                new Multiply<Integer>(
                                                                        new GetLocalVar<>(
                                                                                "number00" ,
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
                                Value.trueValue() ,
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
                                Value.falseValue()
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
                                Value.trueValue() ,
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
                        new DeclareVariable<>(
                                "number" ,
                                0 ) ,
                        new While<>(
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
                        new DeclareVariable<>(
                                "number" ,
                                2 ) ,
                        new While<>(
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
    public void test_While_IfElse_0()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        @SuppressWarnings("unchecked")
        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // steps
                        new DeclareVariable<>(
                                "first" ,
                                true ) ,
                        new While<>(
                                // condition
                                Value.trueValue() ,
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

    @Test
    public void test_While_IfElse_1()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        // extract get local variable expression
        final GetLocalVar<Boolean> first =
                new GetLocalVar<>(
                        "first" ,
                        Boolean.class );

        @SuppressWarnings("unchecked")
        final CoroutineIterator<Boolean> coroIter =
                new CoroutineIterator<Boolean>(
                        // type
                        Boolean.class ,
                        // steps
                        new DeclareVariable<>(
                                "first" ,
                                true ) ,
                        new While<>(
                                // condition
                                Value.trueValue() ,
                                // steps
                                new IfElse<>(
                                        //condition
                                        new DeclareVariable<>(
                                                "copyOfFirst" ,
                                                Boolean.class ,
                                                first ) ,
                                        // thenSteps
                                        (CoroIterStep<Boolean>[]) new CoroIterStep[] {
                                                new YieldReturn<>( first ) ,
                                        } ,
                                        // elseSteps
                                        (CoroIterStep<Boolean>[]) new CoroIterStep[] {
                                                new FinallyReturn<>( first ) ,
                                        } ) ,
                                new NegateLocalVar<>( "first" ) ) );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                true );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                false );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    @Test
    public void test_While_IfElse_2()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        // extract get local variable expression
        final GetLocalVar<Boolean> first =
                new GetLocalVar<>(
                        "first" ,
                        Boolean.class );

        // extract get local variable expression
        final GetLocalVar<Boolean> copyOfFirst =
                new GetLocalVar<>(
                        "copyOfFirst" ,
                        Boolean.class );

        @SuppressWarnings("unchecked")
        final CoroutineIterator<Boolean> coroIter =
                new CoroutineIterator<Boolean>(
                        // type
                        Boolean.class ,
                        // steps
                        new DeclareVariable<>(
                                "first" ,
                                true ) ,
                        new While<>(
                                // condition
                                Value.trueValue() ,
                                // steps
                                new IfElse<>(
                                        //condition
                                        new DeclareVariable<>(
                                                "copyOfFirst" ,
                                                Boolean.class ,
                                                first ) ,
                                        // thenSteps
                                        (CoroIterStep<Boolean>[]) new CoroIterStep[] {
                                                new YieldReturn<>( copyOfFirst ) ,
                                        } ,
                                        // elseSteps
                                        (CoroIterStep<Boolean>[]) new CoroIterStep[] {
                                                new FinallyReturn<>( copyOfFirst ) ,
                                        } ) ,
                                new NegateLocalVar<>( "first" ) ) );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                true );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                false );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

}
