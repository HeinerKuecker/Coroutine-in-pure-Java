package de.heinerkuecker.coroutine.iterator.test;

import org.junit.Test;

import de.heinerkuecker.coroutine.CoroutineDebugSwitches;
import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.exprs.GetLocalVar;
import de.heinerkuecker.coroutine.exprs.Value;
import de.heinerkuecker.coroutine.exprs.bool.Equals;
import de.heinerkuecker.coroutine.exprs.bool.Not;
import de.heinerkuecker.coroutine.stmt.complex.If;
import de.heinerkuecker.coroutine.stmt.complex.While;
import de.heinerkuecker.coroutine.stmt.ret.FinallyReturn;
import de.heinerkuecker.coroutine.stmt.ret.YieldReturn;
import de.heinerkuecker.coroutine.stmt.simple.DeclareVariable;
import de.heinerkuecker.coroutine.stmt.simple.IncrementLocalVar;

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
        CoroutineDebugSwitches.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // stmts
                        new If< Integer , Void >(
                                // condition
                                true ,
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

    @Test
    public void test_If_True_1_0()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        // extract get local variable expression
        final GetLocalVar<Boolean> condition_var =
                new GetLocalVar<>(
                        // localVarName
                        "condition_var" ,
                        // type
                        Boolean.class );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // stmts
                        new DeclareVariable<>(
                                // varName
                                "condition_var" ,
                                // varValue
                                true ) ,
                        new If<Integer , Void>(
                                // condition
                                condition_var ,
                                // stmts
                                new YieldReturn<>( 0 ) ,
                                new If<Integer , Void>(
                                        // condition
                                        new Not( condition_var ) ,
                                        // stmts
                                        new FinallyReturn<>( 1 ) ) ) );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                0 );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    @Test
    public void test_If_True_1_1()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        // extract get local variable expression
        final GetLocalVar<Boolean> condition_var =
                new GetLocalVar<>(
                        // localVarName
                        "condition_var" ,
                        // type
                        Boolean.class );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // stmts
                        new DeclareVariable<>(
                                // varName
                                "condition_var" ,
                                // varValue
                                true ) ,
                        new If<Integer , Void>(
                                // condition
                                condition_var ,
                                // stmts
                                new YieldReturn<>( 0 ) ,
                                new If<Integer , Void>(
                                        // condition
                                        condition_var ,
                                        // stmts
                                        new FinallyReturn<>( 1 ) ) ) );

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
    public void test_If_True_No_Stmts()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // stmts
                        new If<Integer , Void>(
                                // condition
                                true
                                // stmts
                                ) );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    @Test
    public void test_While_If_0()
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
                        // stmts
                        new DeclareVariable<>(
                                "number" ,
                                0 ) ,
                        new While<Integer , Void>(
                                // condition
                                Value.trueValue() ,
                                // stmts
                                new If<Integer , Void>(
                                        // condition
                                        new Equals<>(
                                                number ,
                                                0 ) ,
                                        // stmts
                                        new YieldReturn<>( 0 ) ) ,
                                new If<Integer , Void>(
                                        // condition
                                        new Equals<>(
                                                number ,
                                                1 ) ,
                                        // stmts
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

    @Test
    public void test_While_DeclareLocalVar_in_Condition_0()
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
                        // stmts
                        new While<Integer , Void>(
                                // condition
                                new Equals<>(
                                        // use DeclareVariable as expression
                                        new DeclareVariable<>(
                                                "number" ,
                                                0 ) ,
                                        0 ) ,
                                // stmts
                                new FinallyReturn<>( number ) ) );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                0 );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

}
