package de.heinerkuecker.coroutine.iterator.test;

import org.junit.Assert;
import org.junit.Test;

import de.heinerkuecker.coroutine.CoroutineDebugSwitches;
import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.GlobalVariables;
import de.heinerkuecker.coroutine.exprs.GetLocalVar;
import de.heinerkuecker.coroutine.exprs.Value;
import de.heinerkuecker.coroutine.exprs.bool.Lesser;
import de.heinerkuecker.coroutine.stmt.complex.For;
import de.heinerkuecker.coroutine.stmt.complex.While;
import de.heinerkuecker.coroutine.stmt.flow.Continue;
import de.heinerkuecker.coroutine.stmt.flow.exc.UnresolvedBreakOrContinueException;
import de.heinerkuecker.coroutine.stmt.ret.YieldReturn;
import de.heinerkuecker.coroutine.stmt.simple.DeclareVariable;
import de.heinerkuecker.coroutine.stmt.simple.IncrementLocalVar;
import de.heinerkuecker.coroutine.stmt.simple.NoOperation;
import de.heinerkuecker.coroutine.stmt.simple.SetLocalVar;

/**
 * JUnit4 test case for {@link CoroutineIterator}.
 *
 * @author Heiner K&uuml;cker
 */
public class CoroutineIteratorContinueTest
{
    @Test
    public void test_For_Continue()
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
                                new Continue<>() ,
                                // this statement is never executed
                                new NoOperation<>() ) );

        for ( int i = 0 ; i < 3 ; i++ )
            // limited count of repetitions, but can run endless
        {
            CoroutineIteratorTest.assertNext(
                    coroIter ,
                    0 );

            CoroutineIteratorTest.assertNext(
                    coroIter ,
                    1 );
        }

        Assert.assertTrue(
                coroIter.hasNext() );
    }

    @Test
    public void test_For_Continue_Label()
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
                        new For<Void , Integer , Void>(
                                // label
                                "outer_for" ,
                                // initialStmt
                                new NoOperation<>() ,
                                // condition
                                new Lesser<>(
                                        number ,
                                        1 ) ,
                                // updateStmt
                                new IncrementLocalVar<>( "number" ) ,
                                // stmts
                                new For<>(
                                        // initialStmt
                                        new NoOperation<>() ,
                                        // condition
                                        Value.trueValue() ,
                                        // updateStmt
                                        new NoOperation<>() ,
                                        // stmts
                                        new Continue<>(
                                                // label
                                                "outer_for" ) ,
                                        // this yield return is never executed
                                        new YieldReturn<>( 1 ) ) ) );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    @Test( expected = IllegalArgumentException.class )
    public void test_Negative_For_Continue_in_Initializer()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // stmts
                        new While<Void , Integer , Void>(
                                // condition
                                Value.trueValue() ,
                                // stmts
                                new SetLocalVar<>(
                                        "number" ,
                                        0 ) ,
                                new For<>(
                                        // initialStmt
                                        new Continue<>() ,
                                        // condition
                                        Value.trueValue() ,
                                        // updateStmt
                                        new NoOperation<>() ,
                                        // stmts
                                        new NoOperation<>() ) ) );

        coroIter.hasNext();
    }

    @Test( expected = IllegalArgumentException.class )
    public void test_Negative_For_Continue_labeled_in_Initializer()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // stmts
                        new While<Void , Integer , Void>(
                                // condition
                                Value.trueValue() ,
                                // stmts
                                new SetLocalVar<>(
                                        "number" ,
                                        0 ) ,
                                new For<>(
                                        // initialStmt
                                        new Continue<>( "any" ) ,
                                        // condition
                                        Value.trueValue() ,
                                        // updateStmt
                                        new NoOperation<>() ,
                                        // stmts
                                        new NoOperation<>() ) ) );

        coroIter.hasNext();
    }

    @Test( expected = IllegalArgumentException.class )
    public void test_Negative_For_Continue_in_Update()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // stmts
                        new While<Void , Integer , Void>(
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
                                        new Continue<>() ,
                                        // stmts
                                        new NoOperation<>() ) ) );

        coroIter.hasNext();
    }

    @Test( expected = IllegalArgumentException.class )
    public void test_Negative_For_Continue_labeled_in_Update()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // stmts
                        new While<Void , Integer , Void>(
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
                                        new Continue<>( "any" ) ,
                                        // stmts
                                        new NoOperation<>() ) ) );

        coroIter.hasNext();
    }

    @Test( expected = GlobalVariables.VariableNotDeclaredException.class )
    public void test_Negative_For_Continue_wrong_labeled()
    {
        // avoid initialization checks
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
                        new SetLocalVar<>(
                                "number" ,
                                0 ) ,
                        new For<Void , Integer , Void>(
                                // label
                                "outer_for" ,
                                // initialStmt
                                new NoOperation<>() ,
                                // condition
                                new Lesser<>(
                                        number ,
                                        1 ) ,
                                // updateStmt
                                new IncrementLocalVar<>( "number" ) ,
                                // stmts
                                new For<>(
                                        // initialStmt
                                        new NoOperation<>() ,
                                        // condition
                                        Value.trueValue() ,
                                        // updateStmt
                                        new NoOperation<>() ,
                                        // stmts
                                        new Continue<>(
                                                // label
                                                "wrong" ) ,
                                        // this yield return is never executed
                                        new YieldReturn<>( 1 ) ) ) );

        coroIter.hasNext();
    }

    @Test( expected = UnresolvedBreakOrContinueException.class )
    public void test_Negative_For_Continue_wrong_labeled_with_initialization_checks()
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
                        new For<Void , Integer , Void>(
                                // label
                                "outer_for" ,
                                // initialStmt
                                new NoOperation<>() ,
                                // condition
                                new Lesser<>(
                                        number ,
                                        1 ) ,
                                // updateStmt
                                new IncrementLocalVar<>( "number" ) ,
                                // stmts
                                new For<>(
                                        // initialStmt
                                        new NoOperation<>() ,
                                        // condition
                                        Value.trueValue() ,
                                        // updateStmt
                                        new NoOperation<>() ,
                                        // stmts
                                        new Continue<>(
                                                // label
                                                "wrong" ) ,
                                        // this yield return is never executed
                                        new YieldReturn<>( 1 ) ) ) );

        coroIter.hasNext();
    }

    @Test
    public void test_While_Continue()
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
                        new While<Void , Integer , Void>(
                                // condition
                                Value.trueValue() ,
                                // stmts
                                new YieldReturn<>( 0 ) ,
                                new YieldReturn<>( 1 ) ,
                                new Continue<>() ,
                                // this statement is never executed
                                new NoOperation<>() ) );

        for ( int i = 0 ; i < 3 ; i++ )
            // limited count of repetitions, but can run endless
        {
            CoroutineIteratorTest.assertNext(
                    coroIter ,
                    0 );

            CoroutineIteratorTest.assertNext(
                    coroIter ,
                    1 );
        }

        Assert.assertTrue(
                coroIter.hasNext() );
    }

    @Test
    public void test_While_Continue_Label()
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
                                Integer.class ,
                                0 ) ,
                        new While<Void , Integer , Void>(
                                // label
                                "outer_while" ,
                                // condition
                                new Lesser<>(
                                        number ,
                                        1 ) ,
                                // stmts
                                new While<Void , Integer , Void>(
                                        // condition
                                        Value.trueValue() ,
                                        // stmts
                                        new IncrementLocalVar<>( "number" ) ,
                                        new Continue<>(
                                                // label
                                                "outer_while" ) ,
                                        // this statement is never executed
                                        new NoOperation<>() ) ) );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    @Test( expected = GlobalVariables.VariableNotDeclaredException.class )
    public void test_Negative_While_Continue_wrong_labeled()
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
                        new SetLocalVar<>(
                                "number" ,
                                0 ) ,
                        new While<Void , Integer , Void>(
                                // label
                                "outer_while" ,
                                // condition
                                new Lesser<>(
                                        number ,
                                        1 ) ,
                                // stmts
                                new While<Void , Integer , Void>(
                                        // condition
                                        Value.trueValue() ,
                                        // stmts
                                        new IncrementLocalVar<>( "number" ) ,
                                        new Continue<>(
                                                // label
                                                "wrong" ) ,
                                        // this statement is never executed
                                        new NoOperation<>() ) ) );

        coroIter.hasNext();
    }

    @Test( expected = UnresolvedBreakOrContinueException.class )
    public void test_Negative_While_Continue_wrong_labeled_with_initialization_checks()
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
                        new While<Void , Integer , Void>(
                                // label
                                "outer_while" ,
                                // condition
                                new Lesser<>(
                                        number ,
                                        1 ) ,
                                // stmts
                                new While<Void , Integer , Void>(
                                        // condition
                                        Value.trueValue() ,
                                        // stmts
                                        new IncrementLocalVar<>( "number" ) ,
                                        new Continue<>(
                                                // label
                                                "wrong" ) ,
                                        // this statement is never executed
                                        new NoOperation<>() ) ) );

        coroIter.hasNext();
    }

}
