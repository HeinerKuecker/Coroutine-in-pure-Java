package de.heinerkuecker.coroutine.iterator.test;

import org.junit.Assert;
import org.junit.Test;

import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.condition.Lesser;
import de.heinerkuecker.coroutine.condition.True;
import de.heinerkuecker.coroutine.expression.GetLocalVar;
import de.heinerkuecker.coroutine.expression.Value;
import de.heinerkuecker.coroutine.step.complex.For;
import de.heinerkuecker.coroutine.step.complex.While;
import de.heinerkuecker.coroutine.step.flow.Continue;
import de.heinerkuecker.coroutine.step.flow.exc.UnresolvedBreakOrContinueException;
import de.heinerkuecker.coroutine.step.retrn.YieldReturn;
import de.heinerkuecker.coroutine.step.simple.IncLocalVar;
import de.heinerkuecker.coroutine.step.simple.NoOperation;
import de.heinerkuecker.coroutine.step.simple.SetLocalVar;

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
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // steps
                        new SetLocalVar<>(
                                "number" ,
                                new Value<>( 0 ) ) ,
                        new For<>(
                                // initialStep
                                new NoOperation<>() ,
                                // condition
                                new True() ,
                                // updateStep
                                new NoOperation<>() ,
                                // steps
                                new YieldReturn<>(
                                        new Value<>( 0 ) ) ,
                                new YieldReturn<>(
                                        new Value<>( 1 ) ) ,
                                new Continue<>() ,
                                // this step is never executed
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
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // steps
                        new SetLocalVar<>(
                                "number" ,
                                new Value<>( 0 ) ) ,
                        new For<Integer/*, CoroutineIterator<Integer>*/>(
                                //label
                                "outer_for" ,
                                // initialStep
                                new NoOperation<>() ,
                                // condition
                                new Lesser<>(
                                        new GetLocalVar<>(
                                                "number" ,
                                                Integer.class ) ,
                                        new Value<>( 1 ) ) ,
                                // updateStep
                                new IncLocalVar<>( "number" ) ,
                                // steps
                                new For<>(
                                        // initialStep
                                        new NoOperation<>() ,
                                        // condition
                                        new True() ,
                                        // updateStep
                                        new NoOperation<>() ,
                                        // steps
                                        new Continue<>(
                                                //label
                                                "outer_for" ) ,
                                        // this yield return is never executed
                                        new YieldReturn<>(
                                                new Value<>( 1 ) ) ) ) );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    @Test( expected = IllegalArgumentException.class )
    public void test_Negative_For_Continue_in_Initializer()
    {
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // steps
                        new While<Integer/*, CoroutineIterator<Integer>*/>(
                                //condition
                                new True() ,
                                // steps
                                new SetLocalVar<>(
                                        "number" ,
                                        new Value<>( 0 ) ) ,
                                new For<>(
                                        // initialStep
                                        new Continue<>() ,
                                        // condition
                                        new True() ,
                                        // updateStep
                                        new NoOperation<>() ,
                                        // steps
                                        new NoOperation<>() ) ) );

        coroIter.hasNext();
    }

    @Test( expected = IllegalArgumentException.class )
    public void test_Negative_For_Continue_labeled_in_Initializer()
    {
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // steps
                        new While<Integer/*, CoroutineIterator<Integer>*/>(
                                //condition
                                new True() ,
                                // steps
                                new SetLocalVar<>(
                                        "number" ,
                                        new Value<>( 0 ) ) ,
                                new For<>(
                                        // initialStep
                                        new Continue<>( "any" ) ,
                                        // condition
                                        new True() ,
                                        // updateStep
                                        new NoOperation<>() ,
                                        // steps
                                        new NoOperation<>() ) ) );

        coroIter.hasNext();
    }

    @Test( expected = IllegalArgumentException.class )
    public void test_Negative_For_Continue_in_Update()
    {
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // steps
                        new While<Integer/*, CoroutineIterator<Integer>*/>(
                                //condition
                                new True() ,
                                // steps
                                new SetLocalVar<>(
                                        "number" ,
                                        new Value<>( 0 ) ) ,
                                new For<>(
                                        // initialStep
                                        new NoOperation<>() ,
                                        // condition
                                        new True() ,
                                        // updateStep
                                        new Continue<>() ,
                                        // steps
                                        new NoOperation<>() ) ) );

        coroIter.hasNext();
    }

    @Test( expected = IllegalArgumentException.class )
    public void test_Negative_For_Continue_labeled_in_Update()
    {
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // steps
                        new While<Integer/*, CoroutineIterator<Integer>*/>(
                                //condition
                                new True() ,
                                // steps
                                new SetLocalVar<>(
                                        "number" ,
                                        new Value<>( 0 ) ) ,
                                new For<>(
                                        // initialStep
                                        new NoOperation<>() ,
                                        // condition
                                        new True() ,
                                        // updateStep
                                        new Continue<>( "any" ) ,
                                        // steps
                                        new NoOperation<>() ) ) );

        coroIter.hasNext();
    }

    @Test( expected = IllegalStateException.class )
    public void test_Negative_For_Continue_wrong_labeled()
    {
        // avoid initialization checks
        CoroutineIterator.initializationChecks = false;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // steps
                        new SetLocalVar<>(
                                "number" ,
                                new Value<>( 0 ) ) ,
                        new For<Integer/*, CoroutineIterator<Integer>*/>(
                                //label
                                "outer_for" ,
                                // initialStep
                                new NoOperation<>() ,
                                // condition
                                new Lesser<>(
                                        new GetLocalVar<>(
                                                "number" ,
                                                Integer.class ) ,
                                        new Value<>( 1 ) ) ,
                                // updateStep
                                new IncLocalVar<>( "number" ) ,
                                // steps
                                new For<>(
                                        // initialStep
                                        new NoOperation<>() ,
                                        // condition
                                        new True() ,
                                        // updateStep
                                        new NoOperation<>() ,
                                        // steps
                                        new Continue<>(
                                                //label
                                                "wrong" ) ,
                                        // this yield return is never executed
                                        new YieldReturn<>(
                                                new Value<>( 1 ) ) ) ) );

        coroIter.hasNext();
    }

    @Test( expected = UnresolvedBreakOrContinueException.class )
    public void test_Negative_For_Continue_wrong_labeled_with_initialization_checks()
    {
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // steps
                        new SetLocalVar<>(
                                "number" ,
                                new Value<>( 0 ) ) ,
                        new For<Integer/*, CoroutineIterator<Integer>*/>(
                                //label
                                "outer_for" ,
                                // initialStep
                                new NoOperation<>() ,
                                // condition
                                new Lesser<>(
                                        new GetLocalVar<>(
                                                "number" ,
                                                Integer.class ) ,
                                        new Value<>( 1 ) ) ,
                                // updateStep
                                new IncLocalVar<>( "number" ) ,
                                // steps
                                new For<>(
                                        // initialStep
                                        new NoOperation<>() ,
                                        // condition
                                        new True() ,
                                        // updateStep
                                        new NoOperation<>() ,
                                        // steps
                                        new Continue<>(
                                                //label
                                                "wrong" ) ,
                                        // this yield return is never executed
                                        new YieldReturn<>(
                                                new Value<>( 1 ) ) ) ) );

        coroIter.hasNext();
    }

    @Test
    public void test_While_Continue()
    {
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // steps
                        new SetLocalVar<>(
                                "number" ,
                                new Value<>( 0 ) ) ,
                        new While<Integer/*, CoroutineIterator<Integer>*/>(
                                //condition
                                new True() ,
                                //steps
                                new YieldReturn<>(
                                        new Value<>( 0 ) ) ,
                                new YieldReturn<>(
                                        new Value<>( 1 ) ) ,
                                new Continue<>() ,
                                // this step is never executed
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
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // steps
                        new SetLocalVar<>(
                                "number" ,
                                new Value<>( 0 ) ) ,
                        new While<Integer/*, CoroutineIterator<Integer>*/>(
                                //label
                                "outer_while" ,
                                //condition
                                new Lesser<>(
                                        new GetLocalVar<>(
                                                "number" ,
                                                Integer.class ) ,
                                        new Value<>( 1 ) ) ,
                                //steps
                                new While<Integer/*, CoroutineIterator<Integer>*/>(
                                        //condition
                                        new True() ,
                                        //steps
                                        new IncLocalVar<>( "number" ) ,
                                        new Continue<>(
                                                //label
                                                "outer_while" ) ,
                                        // this step is never executed
                                        new NoOperation<>() ) ) );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    @Test( expected = IllegalStateException.class )
    public void test_Negative_While_Continue_wrong_labeled()
    {
        CoroutineIterator.initializationChecks = false;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // steps
                        new SetLocalVar<>(
                                "number" ,
                                new Value<>( 0 ) ) ,
                        new While<Integer/*, CoroutineIterator<Integer>*/>(
                                //label
                                "outer_while" ,
                                //condition
                                new Lesser<>(
                                        new GetLocalVar<>(
                                                "number" ,
                                                Integer.class ) ,
                                        new Value<>( 1 ) ) ,
                                //steps
                                new While<Integer/*, CoroutineIterator<Integer>*/>(
                                        //condition
                                        new True() ,
                                        //steps
                                        new IncLocalVar<>( "number" ) ,
                                        new Continue<>(
                                                //label
                                                "wrong" ) ,
                                        // this step is never executed
                                        new NoOperation<>() ) ) );

        coroIter.hasNext();
    }

    @Test( expected = UnresolvedBreakOrContinueException.class )
    public void test_Negative_While_Continue_wrong_labeled_with_initialization_checks()
    {
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // steps
                        new SetLocalVar<>(
                                "number" ,
                                new Value<>( 0 ) ) ,
                        new While<Integer/*, CoroutineIterator<Integer>*/>(
                                //label
                                "outer_while" ,
                                //condition
                                new Lesser<>(
                                        new GetLocalVar<>(
                                                "number" ,
                                                Integer.class ) ,
                                        new Value<>( 1 ) ) ,
                                //steps
                                new While<Integer/*, CoroutineIterator<Integer>*/>(
                                        //condition
                                        new True() ,
                                        //steps
                                        new IncLocalVar<>( "number" ) ,
                                        new Continue<>(
                                                //label
                                                "wrong" ) ,
                                        // this step is never executed
                                        new NoOperation<>() ) ) );

        coroIter.hasNext();
    }

}
