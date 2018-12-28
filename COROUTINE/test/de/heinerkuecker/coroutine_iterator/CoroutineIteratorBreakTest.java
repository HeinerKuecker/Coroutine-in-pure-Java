package de.heinerkuecker.coroutine_iterator;

import org.junit.Test;

import de.heinerkuecker.coroutine_iterator.condition.Lesser;
import de.heinerkuecker.coroutine_iterator.condition.True;
import de.heinerkuecker.coroutine_iterator.step.complex.For;
import de.heinerkuecker.coroutine_iterator.step.complex.StepSequence;
import de.heinerkuecker.coroutine_iterator.step.complex.While;
import de.heinerkuecker.coroutine_iterator.step.flow.Break;
import de.heinerkuecker.coroutine_iterator.step.retrn.YieldReturnValue;
import de.heinerkuecker.coroutine_iterator.step.simple.NoOperation;
import de.heinerkuecker.coroutine_iterator.step.simple.SetLocalVar;

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
                        new SetLocalVar<>( "number" , 0 ) ,
                        new For<>(
                                // initialStep
                                new NoOperation<>() ,
                                // condition
                                new True() ,
                                // updateStep
                                new NoOperation<>() ,
                                // steps
                                new YieldReturnValue<>( 0 ) ,
                                new YieldReturnValue<>( 1 ) ,
                                new Break<>() ,
                                // the third yield return is never executed
                                new YieldReturnValue<>( 2 ) ) );

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
                        new SetLocalVar<>( "number" , 0 ) ,
                        new For<Integer/*, CoroutineIterator<Integer>*/>(
                                //label
                                "outer_for" ,
                                // initialStep
                                new NoOperation<>() ,
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
                                        new YieldReturnValue<>( 0 ) ) ,
                                // this yield return is never executed
                                new YieldReturnValue<>( 1 ) ) ,
                        new YieldReturnValue<>( 2 ) );

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
                                new SetLocalVar<>( "number" , 0 ) ,
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
                                new SetLocalVar<>( "number" , 0 ) ,
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
                                new SetLocalVar<>( "number" , 0 ) ,
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
                                new SetLocalVar<>( "number" , 0 ) ,
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
                                new SetLocalVar<>( "number" , 0 ) ,
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
                                new SetLocalVar<>( "number" , 0 ) ,
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
                                new SetLocalVar<>( "number" , 0 ) ,
                                new For<>(
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

    @Test( expected = IllegalArgumentException.class )
    public void test_Negative_For_Break_wrong_labeled_with_initialization_checks()
    {
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        new While<Integer/*, CoroutineIterator<Integer>*/>(
                                //condition
                                new True() ,
                                // steps
                                new SetLocalVar<>( "number" , 0 ) ,
                                new For<>(
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
                        new SetLocalVar<>( "number" , 0 ) ,
                        new While<Integer/*, CoroutineIterator<Integer>*/>(
                                //condition
                                new True() ,
                                //steps
                                new YieldReturnValue<>( 0 ) ,
                                new YieldReturnValue<>( 1 ) ,
                                new Break<>() ,
                                // the third yield return is never executed
                                new YieldReturnValue<>( 2 ) ) );

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
                        new SetLocalVar<>( "number" , 0 ) ,
                        new While<Integer/*, CoroutineIterator<Integer>*/>(
                                //label
                                "outer_while" ,
                                //condition
                                new Lesser(
                                        //varName
                                        "number" ,
                                        //compareValue
                                        2 ) ,
                                //steps
                                new While<Integer/*, CoroutineIterator<Integer>*/>(
                                        //condition
                                        new Lesser(
                                                //varName
                                                "number" ,
                                                //compareValue
                                                1 ) ,
                                        //steps
                                        new Break<>(
                                                //label
                                                "outer_while" ) ,
                                        // this yield return is never executed
                                        new YieldReturnValue<>( 0 ) ) ,
                                // this yield return is never executed
                                new YieldReturnValue<>( 1 ) ) ,
                        new YieldReturnValue<>( 2 ) );

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
                        new SetLocalVar<>( "number" , 0 ) ,
                        new While<Integer/*, CoroutineIterator<Integer>*/>(
                                //label
                                "outer_while" ,
                                //condition
                                new Lesser(
                                        //varName
                                        "number" ,
                                        //compareValue
                                        2 ) ,
                                //steps
                                new While<Integer/*, CoroutineIterator<Integer>*/>(
                                        //condition
                                        new Lesser(
                                                //varName
                                                "number" ,
                                                //compareValue
                                                1 ) ,
                                        //steps
                                        new Break<>(
                                                //label
                                                "wrong" ) ,
                                        // this yield return is never executed
                                        new YieldReturnValue<>( 0 ) ) ,
                                // this yield return is never executed
                                new YieldReturnValue<>( 1 ) ) ,
                        new YieldReturnValue<>( 2 ) );

        coroIter.hasNext();
    }

    @Test( expected = IllegalArgumentException.class )
    public void test_Negative_While_Break_wrong_labeled_with_initialization_checks()
    {
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        new SetLocalVar<>( "number" , 0 ) ,
                        new While<Integer/*, CoroutineIterator<Integer>*/>(
                                //label
                                "outer_while" ,
                                //condition
                                new Lesser(
                                        //varName
                                        "number" ,
                                        //compareValue
                                        2 ) ,
                                //steps
                                new While<Integer/*, CoroutineIterator<Integer>*/>(
                                        //condition
                                        new Lesser(
                                                //varName
                                                "number" ,
                                                //compareValue
                                                1 ) ,
                                        //steps
                                        new Break<>(
                                                //label
                                                "wrong" ) ,
                                        // this yield return is never executed
                                        new YieldReturnValue<>( 0 ) ) ,
                                // this yield return is never executed
                                new YieldReturnValue<>( 1 ) ) ,
                        new YieldReturnValue<>( 2 ) );

        coroIter.hasNext();
    }

}
