package de.heinerkuecker.coroutine.iterator.test;

import org.junit.Test;

import de.heinerkuecker.coroutine.CoroutineDebugSwitches;
import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.exprs.GetLocalVar;
import de.heinerkuecker.coroutine.exprs.bool.Lesser;
import de.heinerkuecker.coroutine.exprs.bool.LesserOrEqual;
import de.heinerkuecker.coroutine.exprs.num.IntAdd;
import de.heinerkuecker.coroutine.stmt.complex.For;
import de.heinerkuecker.coroutine.stmt.ret.YieldReturn;
import de.heinerkuecker.coroutine.stmt.simple.DeclareVariable;
import de.heinerkuecker.coroutine.stmt.simple.IncrementLocalVar;
import de.heinerkuecker.coroutine.stmt.simple.NegateLocalVar;
import de.heinerkuecker.coroutine.stmt.simple.NoOperation;
import de.heinerkuecker.coroutine.stmt.simple.SetLocalVar;

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
        CoroutineDebugSwitches.initializationChecks = true;

        // extract get local variable expression
        final GetLocalVar<Integer , Integer> number =
                new GetLocalVar<>(
                        // varName
                        "number" ,
                        Integer.class );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // stmts
                        new For<>(
                                // initialStmt
                                new DeclareVariable<>(
                                        "number" ,
                                        0 ) ,
                                // condition
                                new Lesser<>(
                                        number ,
                                        3 ) ,
                                // updateStmt
                                new IncrementLocalVar<>( "number" ) ,
                                // stmts
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
        CoroutineDebugSwitches.initializationChecks = true;

        // extract get local variable expression
        final GetLocalVar<Integer , Integer> number =
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
                        new YieldReturn<>( number ) ,
                        new For<>(
                                // initialStmt
                                new NoOperation<>() ,
                                // condition
                                new Lesser<>(
                                        number ,
                                        3 ) ,
                                // updateStmt
                                new IncrementLocalVar<>( "number" ) ,
                                // stmts
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
    public void test_For_01()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        // extract get local variable expression
        final GetLocalVar<Integer , Integer> number =
                new GetLocalVar<>(
                        // varName
                        "number" ,
                        Integer.class );

        final GetLocalVar<Integer , Integer> number1 =
                new GetLocalVar<>(
                        // varName
                        "number1" ,
                        Integer.class );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // stmts
                        new For<>(
                                // initialStmt
                                new DeclareVariable<>(
                                        "number" ,
                                        0 ) ,
                                // condition
                                new Lesser<>(
                                        new DeclareVariable<>(
                                                "number1" ,
                                                Integer.class ,
                                                number ) ,
                                        3 ) ,
                                // updateStmt
                                //new IncrementLocalVar<>( "number" ) ,
                                new SetLocalVar<>(
                                        //localVarName
                                        "number" ,
                                        //varValueExpression
                                        new IntAdd(
                                                number ,
                                                1 ) ) ,
                                // stmts
                                new YieldReturn<>( number1 ) ) );

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
    public void test_For_02()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        // extract get local variable expression
        final GetLocalVar<Integer , Integer> number =
                new GetLocalVar<>(
                        // varName
                        "number" ,
                        Integer.class );

        // extract get local variable expression
        final GetLocalVar<Integer , Integer> number1 =
                new GetLocalVar<>(
                        // varName
                        "number1" ,
                        Integer.class );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // stmts
                        new For<>(
                                // initialStmt
                                new DeclareVariable<>(
                                        "number" ,
                                        0 ) ,
                                // condition
                                new Lesser<>(
                                        new DeclareVariable<>(
                                                "number1" ,
                                                Integer.class ,
                                                number ) ,
                                        3 ) ,
                                // updateStmt
                                new SetLocalVar<>(
                                        //localVarName
                                        "number" ,
                                        //varValueExpression
                                        new IntAdd(
                                                number1 ,
                                                1 ) ) ,
                                // stmts
                                new YieldReturn<>( number1 ) ) );

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
    public void test_For_03()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        // extract get local variable expression
        final GetLocalVar<Integer , Integer> number =
                new GetLocalVar<>(
                        // varName
                        "number" ,
                        Integer.class );

        // extract get local variable expression
        final GetLocalVar<Integer , Integer> number1 =
                new GetLocalVar<>(
                        // varName
                        "number1" ,
                        Integer.class );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // stmts
                        new For<>(
                                // initialStmt
                                new DeclareVariable<>(
                                        "number" ,
                                        0 ) ,
                                // condition
                                new Lesser<>(
                                        new DeclareVariable<>(
                                                "number1" ,
                                                Integer.class ,
                                                number ) ,
                                        3 ) ,
                                // updateStmt
                                new IncrementLocalVar<>(
                                        //localVarName
                                        "number" ) ,
                                // stmts
                                new YieldReturn<>( number1 ) ) );

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
        CoroutineDebugSwitches.initializationChecks = true;

        // extract get local variable expression
        final GetLocalVar<Integer , Integer> number =
                new GetLocalVar<>(
                        // varName
                        "number" ,
                        Integer.class );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // stmts
                        new For<>(
                                // initialStmt
                                new DeclareVariable<>(
                                        "number" ,
                                        0 ) ,
                                // condition
                                new LesserOrEqual<>(
                                        number ,
                                        3 ) ,
                                // updateStmt
                                new IncrementLocalVar<>( "number" ) ,
                                // stmts
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
        CoroutineDebugSwitches.initializationChecks = true;

        // extract get local variable expression
        final GetLocalVar<Integer , Integer> number =
                new GetLocalVar<>(
                        // varName
                        "number" ,
                        Integer.class );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // stmts
                        new For<>(
                                // initialStmt
                                new DeclareVariable<>(
                                        "number" ,
                                        0 ) ,
                                // condition
                                new LesserOrEqual<>(
                                        number ,
                                        6 ) ,
                                // updateStmt
                                new IncrementLocalVar<>( "number" ) ,
                                // stmts
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
        CoroutineDebugSwitches.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // stmts
                        new For<>(
                                // initialStmt
                                new DeclareVariable<>(
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
                                // updateStmt
                                new NegateLocalVar<>( "first_round" ) ,
                                // stmts
                                new YieldReturn<>( 0 ) ) );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                0 );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

}
