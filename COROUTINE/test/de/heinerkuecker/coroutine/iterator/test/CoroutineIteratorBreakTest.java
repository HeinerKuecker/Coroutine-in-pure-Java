package de.heinerkuecker.coroutine.iterator.test;

import org.junit.Test;

import de.heinerkuecker.coroutine.CoroutineDebugSwitches;
import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.exprs.GetLocalVar;
import de.heinerkuecker.coroutine.exprs.Value;
import de.heinerkuecker.coroutine.exprs.bool.Lesser;
import de.heinerkuecker.coroutine.stmt.complex.Block;
import de.heinerkuecker.coroutine.stmt.complex.For;
import de.heinerkuecker.coroutine.stmt.complex.While;
import de.heinerkuecker.coroutine.stmt.flow.Break;
import de.heinerkuecker.coroutine.stmt.flow.exc.UnresolvedBreakOrContinueException;
import de.heinerkuecker.coroutine.stmt.ret.YieldReturn;
import de.heinerkuecker.coroutine.stmt.simple.DeclareVariable;
import de.heinerkuecker.coroutine.stmt.simple.NoOperation;
import de.heinerkuecker.coroutine.stmt.simple.SetLocalVar;

/**
 * JUnit4 test case for {@link CoroutineIterator}.
 *
 * @author Heiner K&uuml;cker
 */
public class CoroutineIteratorBreakTest
{
    @SuppressWarnings("javadoc")
    @Test
    public void test_For_Break()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // stmts
                        new DeclareVariable<>(
                                "number" ,
                                0 ) ,
                        new For<>(
                                // initialStmt
                                new NoOperation<>() ,
                                // condition
                                Value.trueValue() ,
                                // updateStmt
                                new NoOperation<>() ,
                                // stmts
                                new YieldReturn<>( 0 ) ,
                                new YieldReturn<>( 1 ) ,
                                new Break<>() ,
                                // the third yield return is never executed
                                new YieldReturn<>( 2 ) ) );

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
    public void test_For_Break_Label()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // stmts
                        new DeclareVariable<>(
                                "number" ,
                                0 ) ,
                        new For<Void, Integer, Void>(
                                // label
                                "outer_for" ,
                                // initialStmt
                                new NoOperation<>() ,
                                // condition
                                Value.trueValue() ,
                                // updateStmt
                                new NoOperation<>() ,
                                // stmts
                                new For<>(
                                        // initialStmt
                                        new NoOperation<>() ,
                                        // condition
                                        Value.trueValue() ,
                                        // updateStmt
                                        new NoOperation<>() ,
                                        // stmts
                                        new Break<>( "outer_for" ) ,
                                        // this yield return is never executed
                                        new YieldReturn<>( 0 ) ) ,
                                // this yield return is never executed
                                new YieldReturn<>( 1 ) ) ,
                        new YieldReturn<>( 2 ) );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                2 );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    @Test( expected = IllegalArgumentException.class )
    public void test_Negative_For_Break_in_Initializer()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // stmts
                        new While<Void, Integer , Void>(
                                // condition
                                Value.trueValue() ,
                                // stmts
                                new SetLocalVar<>(
                                        "number" ,
                                        0 ) ,
                                new For<>(
                                        // initialStmt
                                        new Break<>() ,
                                        // condition
                                        Value.trueValue() ,
                                        // updateStmt
                                        new NoOperation<>() ,
                                        // stmts
                                        new NoOperation<>() ) ) );

        coroIter.hasNext();
    }

    @Test( expected = IllegalArgumentException.class )
    public void test_Negative_For_Break_in_complex_Initializer()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // stmts
                        new While<Void, Integer , Void>(
                                // condition
                                Value.trueValue() ,
                                // stmts
                                new SetLocalVar<>(
                                        "number" ,
                                        0 ) ,
                                new For<Void, Integer , Void>(
                                        // initialStmt
                                        new Block<>(
                                                // creationStackOffset
                                                1 ,
                                                // stmts
                                                new Break<>() ) ,
                                        // condition
                                        Value.trueValue() ,
                                        // updateStmt
                                        new NoOperation<>() ,
                                        // stmts
                                        new NoOperation<>() ) ) );

        coroIter.hasNext();
    }

    @Test( expected = IllegalArgumentException.class )
    public void test_Negative_For_Break_labeled_in_Initializer()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // stmts
                        new While<Void, Integer, Void>(
                                // condition
                                Value.trueValue() ,
                                // stmts
                                new SetLocalVar<>(
                                        "number" ,
                                        0 ) ,
                                new For<>(
                                        // initialStmt
                                        new Break<>( "wrong" ) ,
                                        // condition
                                        Value.trueValue() ,
                                        // updateStmt
                                        new NoOperation<>() ,
                                        // stmts
                                        new NoOperation<>() ) ) );

        coroIter.hasNext();
    }

    @Test( expected = IllegalArgumentException.class )
    public void test_Negative_For_Break_in_Update()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // stmts
                        new While<Void, Integer , Void>(
                                // condition
                                Value.trueValue() ,
                                // stmts
                                new SetLocalVar<>(
                                        "number" ,
                                        0 ) ,
                                new For<>(
                                        // initialStmt
                                        new NoOperation<>() ,
                                        // condition
                                        Value.trueValue() ,
                                        // updateStmt
                                        new Break<>() ,
                                        // stmts
                                        new NoOperation<>() ) ) );

        coroIter.hasNext();
    }

    @Test( expected = IllegalArgumentException.class )
    public void test_Negative_For_Break_in_Complex_Update()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // stmts
                        new While<Void, Integer , Void>(
                                // condition
                                Value.trueValue() ,
                                // stmts
                                new SetLocalVar<>(
                                        "number" ,
                                        0 ) ,
                                new For<Void, Integer , Void>(
                                        // initialStmt
                                        new NoOperation<>() ,
                                        // condition
                                        Value.trueValue() ,
                                        // updateStmt
                                        new Block<>(
                                                // creationStackOffset
                                                1 ,
                                                // stmts
                                                new Break<>() ) ,
                                        // stmts
                                        new NoOperation<>() ) ) );

        coroIter.hasNext();
    }

    @Test( expected = IllegalArgumentException.class )
    public void test_Negative_For_Break_labeled_in_Update()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // stmts
                        new While<Void, Integer , Void>(
                                // condition
                                Value.trueValue() ,
                                // stmts
                                new SetLocalVar<>(
                                        "number" ,
                                        0 ) ,
                                new For<>(
                                        // initialStmt
                                        new NoOperation<>() ,
                                        // condition
                                        Value.trueValue() ,
                                        // updateStmt
                                        new Break<>( "any" ) ,
                                        // stmts
                                        new NoOperation<>() ) ) );

        coroIter.hasNext();
    }

    @Test( expected = IllegalStateException.class )
    public void test_Negative_For_Break_wrong_labeled()
    {
        CoroutineDebugSwitches.initializationChecks = false;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // stmts
                        new While<Void, Integer , Void>(
                                // condition
                                Value.trueValue() ,
                                // stmts
                                new SetLocalVar<>(
                                        "number" ,
                                        0 ) ,
                                new For<Void, Integer , Void>(
                                        // label
                                        "for" ,
                                        // initialStmt
                                        new NoOperation<>() ,
                                        // condition
                                        Value.trueValue() ,
                                        // updateStmt
                                        new NoOperation<>() ,
                                        // stmts
                                        new Break<>( "wrong" ) ) ) );

        coroIter.hasNext();
    }

    @Test( expected = UnresolvedBreakOrContinueException.class )
    public void test_Negative_For_Break_wrong_labeled_with_initialization_checks()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // stmts
                        new While<Void, Integer , Void>(
                                // condition
                                Value.trueValue() ,
                                // stmts
                                new SetLocalVar<>(
                                        "number" ,
                                        0 ) ,
                                new For<>(
                                        // label
                                        "for" ,
                                        // initialStmt
                                        new NoOperation<>() ,
                                        // condition
                                        Value.trueValue() ,
                                        // updateStmt
                                        new NoOperation<>() ,
                                        // stmts
                                        new Break<>( "wrong" ) ) ) );

        coroIter.hasNext();
    }

    @Test
    public void test_While_Break()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // stmts
                        new DeclareVariable<>(
                                "number" ,
                                0 ) ,
                        new While<Void, Integer , Void>(
                                // condition
                                Value.trueValue() ,
                                //stmts
                                new YieldReturn<>( 0 ) ,
                                new YieldReturn<>( 1 ) ,
                                new Break<>() ,
                                // the third yield return is never executed
                                new YieldReturn<>( 2 ) ) );

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
    public void test_While_Break_Label()
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
                        new While<Void, Integer , Void>(
                                // label
                                "outer_while" ,
                                // condition
                                new Lesser<>(
                                        number ,
                                        //compareValue
                                        2 ) ,
                                //stmts
                                new While<Void, Integer , Void>(
                                        // condition
                                        new Lesser<>(
                                                number ,
                                                //compareValue
                                                1 ) ,
                                        //stmts
                                        new Break<>(
                                                // label
                                                "outer_while" ) ,
                                        // this yield return is never executed
                                        new YieldReturn<>( 0 ) ) ,
                                // this yield return is never executed
                                new YieldReturn<>( 1 ) ) ,
                        new YieldReturn<>( 2 ) );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                2 );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    @Test( expected = IllegalStateException.class )
    public void test_Negative_While_Break_wrong_labeled()
    {
        CoroutineDebugSwitches.initializationChecks = false;

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
                        new While<Void, Integer , Void>(
                                // label
                                "outer_while" ,
                                // condition
                                new Lesser<>(
                                        number ,
                                        // compareValue
                                        2 ) ,
                                //stmts
                                new While<Void, Integer , Void>(
                                        // condition
                                        new Lesser<>(
                                                number ,
                                                //compareValue
                                                1 ) ,
                                        //stmts
                                        new Break<>(
                                                // label
                                                "wrong" ) ,
                                        // this yield return is never executed
                                        new YieldReturn<>( 0 ) ) ,
                                // this yield return is never executed
                                new YieldReturn<>( 1 ) ) ,
                        new YieldReturn<>( 2 ) );

        coroIter.hasNext();
    }

    @Test( expected = UnresolvedBreakOrContinueException.class )
    public void test_Negative_While_Break_wrong_labeled_with_initialization_checks()
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
                        new SetLocalVar<>(
                                "number" ,
                                0 ) ,
                        new While<Void, Integer , Void>(
                                // label
                                "outer_while" ,
                                // condition
                                new Lesser<>(
                                        number ,
                                        //compareValue
                                        2 ) ,
                                //stmts
                                new While<Void, Integer , Void>(
                                        // condition
                                        new Lesser<>(
                                                number ,
                                                //compareValue
                                                Value.intValue( 1 ) ) ,
                                        //stmts
                                        new Break<>(
                                                // label
                                                "wrong" ) ,
                                        // this yield return is never executed
                                        new YieldReturn<>( 0 ) ) ,
                                // this yield return is never executed
                                new YieldReturn<>( 1 ) ) ,
                        new YieldReturn<>( 2 ) );

        coroIter.hasNext();
    }

}
