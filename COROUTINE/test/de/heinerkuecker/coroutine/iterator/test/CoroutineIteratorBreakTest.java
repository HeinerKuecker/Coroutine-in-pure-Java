package de.heinerkuecker.coroutine.iterator.test;

import org.junit.Test;

import de.heinerkuecker.coroutine.CoroutineDebugSwitches;
import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.condition.Lesser;
import de.heinerkuecker.coroutine.condition.True;
import de.heinerkuecker.coroutine.expression.GetLocalVar;
import de.heinerkuecker.coroutine.expression.Value;
import de.heinerkuecker.coroutine.step.complex.Block;
import de.heinerkuecker.coroutine.step.complex.For;
import de.heinerkuecker.coroutine.step.complex.While;
import de.heinerkuecker.coroutine.step.flow.Break;
import de.heinerkuecker.coroutine.step.flow.exc.UnresolvedBreakOrContinueException;
import de.heinerkuecker.coroutine.step.ret.YieldReturn;
import de.heinerkuecker.coroutine.step.simple.DeclareVariable;
import de.heinerkuecker.coroutine.step.simple.NoOperation;
import de.heinerkuecker.coroutine.step.simple.SetLocalVar;

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
                        // steps
                        new DeclareVariable<>(
                                "number" ,
                                0 ) ,
                        new For<>(
                                // initialStep
                                new NoOperation<>() ,
                                // condition
                                new True() ,
                                // updateStep
                                new NoOperation<>() ,
                                // steps
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
                        // steps
                        new DeclareVariable<>(
                                "number" ,
                                0 ) ,
                        new For<Integer/*, CoroutineIterator<Integer>*/>(
                                // label
                                "outer_for" ,
                                // initialStep
                                new NoOperation<Integer>() ,
                                // condition
                                new True() ,
                                // updateStep
                                new NoOperation<>() ,
                                // steps
                                new For<>(
                                        // initialStep
                                        new NoOperation<>() ,
                                        // condition
                                        new True() ,
                                        // updateStep
                                        new NoOperation<>() ,
                                        // steps
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
                        // steps
                        new While<Integer/*, CoroutineIterator<Integer>*/>(
                                // condition
                                new True() ,
                                // steps
                                new SetLocalVar<>(
                                        "number" ,
                                        0 ) ,
                                new For<>(
                                        // initialStep
                                        new Break<>() ,
                                        // condition
                                        new True() ,
                                        // updateStep
                                        new NoOperation<>() ,
                                        // steps
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
                        // steps
                        new While<Integer/*, CoroutineIterator<Integer>*/>(
                                // condition
                                new True() ,
                                // steps
                                new SetLocalVar<>(
                                        "number" ,
                                        0 ) ,
                                new For<Integer/*, CoroutineIterator<Integer>*/>(
                                        // initialStep
                                        new Block<>(
                                                // creationStackOffset
                                                1 ,
                                                // steps
                                                new Break<>() ) ,
                                        // condition
                                        new True() ,
                                        // updateStep
                                        new NoOperation<>() ,
                                        // steps
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
                        // steps
                        new While<Integer/*, CoroutineIterator<Integer>*/>(
                                // condition
                                new True() ,
                                // steps
                                new SetLocalVar<>(
                                        "number" ,
                                        0 ) ,
                                new For<>(
                                        // initialStep
                                        new Break<>( "wrong" ) ,
                                        // condition
                                        new True() ,
                                        // updateStep
                                        new NoOperation<>() ,
                                        // steps
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
                        // steps
                        new While<Integer/*, CoroutineIterator<Integer>*/>(
                                // condition
                                new True() ,
                                // steps
                                new SetLocalVar<>(
                                        "number" ,
                                        0 ) ,
                                new For<>(
                                        // initialStep
                                        new NoOperation<>() ,
                                        // condition
                                        new True() ,
                                        // updateStep
                                        new Break<>() ,
                                        // steps
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
                        // steps
                        new While<Integer/*, CoroutineIterator<Integer>*/>(
                                // condition
                                new True() ,
                                // steps
                                new SetLocalVar<>(
                                        "number" ,
                                        0 ) ,
                                new For<Integer/*, CoroutineIterator<Integer>*/>(
                                        // initialStep
                                        new NoOperation<>() ,
                                        // condition
                                        new True() ,
                                        // updateStep
                                        new Block<>(
                                                // creationStackOffset
                                                1 ,
                                                // steps
                                                new Break<>() ) ,
                                        // steps
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
                        // steps
                        new While<Integer/*, CoroutineIterator<Integer>*/>(
                                // condition
                                new True() ,
                                // steps
                                new SetLocalVar<>(
                                        "number" ,
                                        0 ) ,
                                new For<>(
                                        // initialStep
                                        new NoOperation<>() ,
                                        // condition
                                        new True() ,
                                        // updateStep
                                        new Break<>( "any" ) ,
                                        // steps
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
                        // steps
                        new While<Integer/*, CoroutineIterator<Integer>*/>(
                                // condition
                                new True() ,
                                // steps
                                new SetLocalVar<>(
                                        "number" ,
                                        0 ) ,
                                new For<Integer>(
                                        // label
                                        "for" ,
                                        // initialStep
                                        new NoOperation<Integer>() ,
                                        // condition
                                        new True() ,
                                        // updateStep
                                        new NoOperation<>() ,
                                        // steps
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
                        // steps
                        new While<Integer/*, CoroutineIterator<Integer>*/>(
                                // condition
                                new True() ,
                                // steps
                                new SetLocalVar<>(
                                        "number" ,
                                        0 ) ,
                                new For<Integer>(
                                        // label
                                        "for" ,
                                        // initialStep
                                        new NoOperation<>() ,
                                        // condition
                                        new True() ,
                                        // updateStep
                                        new NoOperation<>() ,
                                        // steps
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
                        // steps
                        new DeclareVariable<>(
                                "number" ,
                                0 ) ,
                        new While<Integer/*, CoroutineIterator<Integer>*/>(
                                // condition
                                new True() ,
                                //steps
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
                        new While<Integer/*, CoroutineIterator<Integer>*/>(
                                // label
                                "outer_while" ,
                                // condition
                                new Lesser<>(
                                        number ,
                                        //compareValue
                                        2 ) ,
                                //steps
                                new While<Integer/*, CoroutineIterator<Integer>*/>(
                                        // condition
                                        new Lesser<>(
                                                number ,
                                                //compareValue
                                                1 ) ,
                                        //steps
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
                        new While<Integer/*, CoroutineIterator<Integer>*/>(
                                // label
                                "outer_while" ,
                                // condition
                                new Lesser<>(
                                        number ,
                                        // compareValue
                                        2 ) ,
                                //steps
                                new While<Integer/*, CoroutineIterator<Integer>*/>(
                                        // condition
                                        new Lesser<>(
                                                number ,
                                                //compareValue
                                                1 ) ,
                                        //steps
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
                        new While<Integer/*, CoroutineIterator<Integer>*/>(
                                // label
                                "outer_while" ,
                                // condition
                                new Lesser<>(
                                        number ,
                                        //compareValue
                                        2 ) ,
                                //steps
                                new While<Integer/*, CoroutineIterator<Integer>*/>(
                                        // condition
                                        new Lesser<>(
                                                number ,
                                                //compareValue
                                                Value.intValue( 1 ) ) ,
                                        //steps
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
