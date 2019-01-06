package de.heinerkuecker.coroutine.iterator.test;

import org.junit.Test;

import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.condition.Lesser;
import de.heinerkuecker.coroutine.condition.True;
import de.heinerkuecker.coroutine.expression.GetLocalVar;
import de.heinerkuecker.coroutine.expression.Value;
import de.heinerkuecker.coroutine.step.complex.For;
import de.heinerkuecker.coroutine.step.complex.StepSequence;
import de.heinerkuecker.coroutine.step.complex.While;
import de.heinerkuecker.coroutine.step.flow.Break;
import de.heinerkuecker.coroutine.step.flow.exc.UnresolvedBreakOrContinueException;
import de.heinerkuecker.coroutine.step.retrn.YieldReturn;
import de.heinerkuecker.coroutine.step.simple.NoOperation;
import de.heinerkuecker.coroutine.step.simple.SetLocalVar;

/**
 * JUnit4 test case for {@link CoroutineIterator}.
 *
 * @author Heiner K&uuml;cker
 */
public class CoroutineIteratorBreakTest
{
    @Test
    public void test_For_Break()
    {
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        new SetLocalVar<>( "number" ,
                                new Value<>(
                                        0 ) ) ,
                        new For<>(
                                // initialStep
                                new NoOperation<>() ,
                                // condition
                                new True() ,
                                // updateStep
                                new NoOperation<>() ,
                                // steps
                                new YieldReturn<>(
                                        new Value<>(
                                                0 ) ) ,
                                new YieldReturn<>(
                                        new Value<>(
                                                1 ) ) ,
                                new Break<>() ,
                                // the third yield return is never executed
                                        new YieldReturn<>(
                                                new Value<>(
                                                        2 ) ) ) );

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
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        new SetLocalVar<>(
                                "number" ,
                                new Value<>(
                                        0 ) ) ,
                        new For<Integer/*, CoroutineIterator<Integer>*/>(
                                //label
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
                                        new YieldReturn<>(
                                                new Value<>(
                                                        0 ) ) ) ,
                                // this yield return is never executed
                                new YieldReturn<>(
                                        new Value<>(
                                                1 ) ) ) ,
                        new YieldReturn<>(
                                new Value<>(
                                        2 ) ) );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                2 );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    @Test( expected = IllegalArgumentException.class )
    public void test_Negative_For_Break_in_Initializer()
    {
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        new While<Integer/*, CoroutineIterator<Integer>*/>(
                                //condition
                                new True() ,
                                // steps
                                new SetLocalVar<>(
                                        "number" ,
                                        new Value<>(
                                                0 ) ) ,
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
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        new While<Integer/*, CoroutineIterator<Integer>*/>(
                                //condition
                                new True() ,
                                // steps
                                new SetLocalVar<>(
                                        "number" ,
                                        new Value<>(
                                                0 ) ) ,
                                new For<Integer/*, CoroutineIterator<Integer>*/>(
                                        // initialStep
                                        new StepSequence<>(
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
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        new While<Integer/*, CoroutineIterator<Integer>*/>(
                                //condition
                                new True() ,
                                // steps
                                new SetLocalVar<>(
                                        "number" ,
                                        new Value<>(
                                                0 ) ) ,
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
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        new While<Integer/*, CoroutineIterator<Integer>*/>(
                                //condition
                                new True() ,
                                // steps
                                new SetLocalVar<>(
                                        "number" ,
                                        new Value<>(
                                                0 ) ) ,
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
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        new While<Integer/*, CoroutineIterator<Integer>*/>(
                                //condition
                                new True() ,
                                // steps
                                new SetLocalVar<>(
                                        "number" ,
                                        new Value<>(
                                                0 ) ) ,
                                new For<Integer/*, CoroutineIterator<Integer>*/>(
                                        // initialStep
                                        new NoOperation<>() ,
                                        // condition
                                        new True() ,
                                        // updateStep
                                        new StepSequence<>(
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
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        new While<Integer/*, CoroutineIterator<Integer>*/>(
                                //condition
                                new True() ,
                                // steps
                                new SetLocalVar<>(
                                        "number" ,
                                        new Value<>(
                                                0 ) ) ,
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
        CoroutineIterator.initializationChecks = false;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        new While<Integer/*, CoroutineIterator<Integer>*/>(
                                //condition
                                new True() ,
                                // steps
                                new SetLocalVar<>(
                                        "number" ,
                                        new Value<>(
                                                0 ) ) ,
                                new For<Integer>(
                                        //label
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
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        new While<Integer/*, CoroutineIterator<Integer>*/>(
                                //condition
                                new True() ,
                                // steps
                                new SetLocalVar<>(
                                        "number" ,
                                        new Value<>(
                                                0 ) ) ,
                                new For<Integer>(
                                        //label
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
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        new SetLocalVar<>(
                                "number" ,
                                new Value<>(
                                        0 ) ) ,
                        new While<Integer/*, CoroutineIterator<Integer>*/>(
                                //condition
                                new True() ,
                                //steps
                                new YieldReturn<>(
                                        new Value<>(
                                                0 ) ) ,
                                new YieldReturn<>(
                                        new Value<>(
                                                1 ) ) ,
                                new Break<>() ,
                                // the third yield return is never executed
                                        new YieldReturn<>(
                                                new Value<>(
                                                        2 ) ) ) );

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
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        new SetLocalVar<>(
                                "number" ,
                                new Value<>(
                                        0 ) ) ,
                        new While<Integer/*, CoroutineIterator<Integer>*/>(
                                //label
                                "outer_while" ,
                                //condition
                                new Lesser<>(
                                        new GetLocalVar<>(
                                                //varName
                                                "number" ,
                                                Integer.class ) ,
                                        new Value<>(
                                                //compareValue
                                                2 ) ) ,
                                //steps
                                new While<Integer/*, CoroutineIterator<Integer>*/>(
                                        //condition
                                        new Lesser<>(
                                                new GetLocalVar<>(
                                                        //varName
                                                        "number" ,
                                                        Integer.class ) ,
                                                new Value<>(
                                                        //compareValue
                                                        1 ) ) ,
                                        //steps
                                        new Break<>(
                                                //label
                                                "outer_while" ) ,
                                        // this yield return is never executed
                                        new YieldReturn<>(
                                                new Value<>(
                                                        0 ) ) ) ,
                                // this yield return is never executed
                                new YieldReturn<>(
                                        new Value<>(
                                                1 ) ) ) ,
                        new YieldReturn<>(
                                new Value<>(
                                        2 ) ) );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                2 );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    @Test( expected = IllegalStateException.class )
    public void test_Negative_While_Break_wrong_labeled()
    {
        CoroutineIterator.initializationChecks = false;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        new SetLocalVar<>(
                                "number" ,
                                new Value<>(
                                        0 ) ) ,
                        new While<Integer/*, CoroutineIterator<Integer>*/>(
                                //label
                                "outer_while" ,
                                //condition
                                new Lesser<>(
                                        new GetLocalVar<>(
                                                //varName
                                                "number" ,
                                                Integer.class ) ,
                                        new Value<>(
                                                //compareValue
                                                2 ) ) ,
                                //steps
                                new While<Integer/*, CoroutineIterator<Integer>*/>(
                                        //condition
                                        new Lesser<>(
                                                new GetLocalVar<>(
                                                        //varName
                                                        "number" ,
                                                        Integer.class ) ,
                                                new Value<>(
                                                        //compareValue
                                                        1 ) ) ,
                                        //steps
                                        new Break<>(
                                                //label
                                                "wrong" ) ,
                                        // this yield return is never executed
                                        new YieldReturn<>(
                                                new Value<>(
                                                        0 ) ) ) ,
                                // this yield return is never executed
                                new YieldReturn<>(
                                        new Value<>(
                                                1 ) ) ) ,
                        new YieldReturn<>(
                                new Value<>(
                                        2 ) ) );

        coroIter.hasNext();
    }

    @Test( expected = UnresolvedBreakOrContinueException.class )
    public void test_Negative_While_Break_wrong_labeled_with_initialization_checks()
    {
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        new SetLocalVar<>(
                                "number" ,
                                new Value<>(
                                        0 ) ) ,
                        new While<Integer/*, CoroutineIterator<Integer>*/>(
                                //label
                                "outer_while" ,
                                //condition
                                new Lesser<>(
                                        new GetLocalVar<>(
                                                //varName
                                                "number" ,
                                                Integer.class ) ,
                                        new Value<>(
                                                //compareValue
                                                2 ) ) ,
                                //steps
                                new While<Integer/*, CoroutineIterator<Integer>*/>(
                                        //condition
                                        new Lesser<>(
                                                new GetLocalVar<>(
                                                        //varName
                                                        "number" ,
                                                        Integer.class ) ,
                                                new Value<>(
                                                        //compareValue
                                                        1 ) ) ,
                                        //steps
                                        new Break<>(
                                                //label
                                                "wrong" ) ,
                                        // this yield return is never executed
                                        new YieldReturn<>(
                                                new Value<>(
                                                        0 ) ) ) ,
                                // this yield return is never executed
                                new YieldReturn<>(
                                        new Value<>(
                                                1 ) ) ) ,
                        new YieldReturn<>(
                                new Value<>(
                                        2 ) ) );

        coroIter.hasNext();
    }

}
