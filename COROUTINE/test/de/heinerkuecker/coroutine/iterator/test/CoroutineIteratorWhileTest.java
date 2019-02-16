package de.heinerkuecker.coroutine.iterator.test;

import org.junit.Test;

import de.heinerkuecker.coroutine.CoroutineDebugSwitches;
import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.exprs.GetLocalVar;
import de.heinerkuecker.coroutine.exprs.Value;
import de.heinerkuecker.coroutine.exprs.bool.Equals;
import de.heinerkuecker.coroutine.exprs.bool.Greater;
import de.heinerkuecker.coroutine.exprs.bool.GreaterOrEqual;
import de.heinerkuecker.coroutine.exprs.bool.Lesser;
import de.heinerkuecker.coroutine.exprs.bool.Not;
import de.heinerkuecker.coroutine.exprs.bool.Or;
import de.heinerkuecker.coroutine.exprs.num.Add;
import de.heinerkuecker.coroutine.exprs.num.CastToInt;
import de.heinerkuecker.coroutine.exprs.num.Multiply;
import de.heinerkuecker.coroutine.stmt.CoroStmt;
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
        final GetLocalVar<Integer , Integer , Void> number =
                new GetLocalVar<>(
                        // varName
                        "number" ,
                        Integer.class );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // stmts
                        new DeclareVariable<>(
                                "number" ,
                                0 ) ,
                        new While<>(
                                // condition
                                new Equals<Integer , Integer , Void>(
                                        number ,
                                        0 ) ,
                                // stmts
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
        final GetLocalVar<Integer , Integer , Void> number =
                new GetLocalVar<>(
                        // varName
                        "number" ,
                        Integer.class );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // stmts
                        new DeclareVariable<>(
                                "number" ,
                                0 ) ,
                        new While<>(
                                // condition
                                new Lesser<>(
                                        number ,
                                        3 ) ,
                                // stmts
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
        final GetLocalVar<Integer , Integer , Void> number =
                new GetLocalVar<>(
                        // varName
                        "number" ,
                        Integer.class );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // stmts
                        new DeclareVariable<>(
                                "number" ,
                                0 ) ,
                        new While<>(
                                // condition
                                new Equals<>(
                                        number ,
                                        0 ) ,
                                // stmts
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
        final GetLocalVar<Integer , Integer , Void> number =
                new GetLocalVar<>(
                        // varName
                        "number" ,
                        Integer.class );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // stmts
                        new DeclareVariable<>(
                                "number" ,
                                3 ) ,
                        new While<>(
                                //condition
                                new Not(
                                        new Equals<>(
                                                number ,
                                                0 ) ) ,
                                // stmts
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
                        // stmts
                        new DeclareVariable<>(
                                "number0" ,
                                Integer.class ) ,
                        new DeclareVariable<>(
                                "number1" ,
                                Integer.class ) ,
                        new SetLocalVar<>(
                                "number0" ,
                                3 ) ,
                        new While<Void, Integer , Void>(
                                //condition
                                new GreaterOrEqual<>(
                                        new GetLocalVar<>(
                                                "number0" ,
                                                Integer.class ) ,
                                        0 ) ,
                                // stmts
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
                                        // stmts
                                        new YieldReturn<>(
                                                new CastToInt<>(
                                                        new Add<Integer , Integer , Void>(
                                                                new Multiply<Integer , Integer , Void>(
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
                        // stmts
                        new DeclareVariable<>(
                                "number0" ,
                                3 ) ,
                        new While<Void, Integer , Void>(
                                //condition
                                new GreaterOrEqual<>(
                                        new GetLocalVar<>(
                                                "number0" ,
                                                Integer.class ) ,
                                        0 ) ,
                                // stmts
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
                                        // stmts
                                        new YieldReturn<>(
                                                new CastToInt<>(
                                                        new Add<Integer , Integer , Void>(
                                                                new Multiply<Integer , Integer , Void>(
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
                        // stmts
                        new DeclareVariable<>(
                                "number0" ,
                                3 ) ,
                        new While<Void, Integer , Void>(
                                //condition
                                new GreaterOrEqual<>(
                                        new DeclareVariable<>(
                                                "number00" ,
                                                Integer.class ,
                                                new GetLocalVar<>(
                                                        "number0" ,
                                                        Integer.class ) ) ,
                                        0 ) ,
                                // stmts
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
                                        // stmts
                                        new YieldReturn<>(
                                                new CastToInt<>(
                                                        new Add<Integer , Integer , Void>(
                                                                new Multiply<Integer , Integer , Void>(
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
                        // stmts
                        new While<>(
                                //condition
                                Value.trueValue() ,
                                // stmts
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
    public void test_While_No_Stmts_Endless_Loop()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // stmts
                        new While<>(
                                //condition
                                true
                                // stmts
                                ) );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    @Test
    public void test_While_No_Stmts()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // stmts
                        new While<>(
                                //condition
                                Value.falseValue()
                                // stmts
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
                        // stmts
                        new While<>(
                                //condition
                                Value.trueValue() ,
                                // stmts
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
        final GetLocalVar<Integer , Integer , Void> number =
                new GetLocalVar<>(
                        // varName
                        "number" ,
                        Integer.class );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // stmts
                        new DeclareVariable<>(
                                "number" ,
                                0 ) ,
                        new While<>(
                                //condition
                                new Lesser<>(
                                        number ,
                                        3 ) ,
                                // stmts
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
        final GetLocalVar<Integer , Integer , Void> number =
                new GetLocalVar<>(
                        // varName
                        "number" ,
                        Integer.class );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // stmts
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
                                // stmts
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
                        // stmts
                        new DeclareVariable<>(
                                "first" ,
                                true ) ,
                        new While<>(
                                // condition
                                Value.trueValue() ,
                                // stmts
                                new IfElse<>(
                                        //condition
                                        new GetLocalVar<>(
                                                "first" ,
                                                Boolean.class ) ,
                                        // thenStmts
                                        (CoroStmt<Void, Integer/*, CoroutineIterator<Integer>*/>[]) new CoroStmt[] {
                                                new YieldReturn<>( 0 ) ,
                                        } ,
                                        // elseStmts
                                        (CoroStmt<Void, Integer/*, CoroutineIterator<Integer>*/>[]) new CoroStmt[] {
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
        final GetLocalVar<Boolean , Boolean , Void> first =
                new GetLocalVar<>(
                        "first" ,
                        Boolean.class );

        @SuppressWarnings("unchecked")
        final CoroutineIterator<Boolean> coroIter =
                new CoroutineIterator<Boolean>(
                        // type
                        Boolean.class ,
                        // stmts
                        new DeclareVariable<>(
                                "first" ,
                                true ) ,
                        new While<>(
                                // condition
                                Value.trueValue() ,
                                // stmts
                                new IfElse<>(
                                        //condition
                                        new DeclareVariable<>(
                                                "copyOfFirst" ,
                                                Boolean.class ,
                                                first ) ,
                                        // thenStmts
                                        (CoroStmt<Void, Boolean>[]) new CoroStmt[] {
                                                new YieldReturn<>( first ) ,
                                        } ,
                                        // elseStmts
                                        (CoroStmt<Void, Boolean>[]) new CoroStmt[] {
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
        final GetLocalVar<Boolean , Boolean , Void> first =
                new GetLocalVar<>(
                        "first" ,
                        Boolean.class );

        // extract get local variable expression
        final GetLocalVar<Boolean , Boolean , Void> copyOfFirst =
                new GetLocalVar<>(
                        "copyOfFirst" ,
                        Boolean.class );

        @SuppressWarnings("unchecked")
        final CoroutineIterator<Boolean> coroIter =
                new CoroutineIterator<Boolean>(
                        // type
                        Boolean.class ,
                        // stmts
                        new DeclareVariable<>(
                                "first" ,
                                true ) ,
                        new While<>(
                                // condition
                                Value.trueValue() ,
                                // stmts
                                new IfElse<>(
                                        //condition
                                        new DeclareVariable<>(
                                                "copyOfFirst" ,
                                                Boolean.class ,
                                                first ) ,
                                        // thenStmts
                                        (CoroStmt<Void, Boolean>[]) new CoroStmt[] {
                                                new YieldReturn<>( copyOfFirst ) ,
                                        } ,
                                        // elseStmts
                                        (CoroStmt<Void, Boolean>[]) new CoroStmt[] {
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
