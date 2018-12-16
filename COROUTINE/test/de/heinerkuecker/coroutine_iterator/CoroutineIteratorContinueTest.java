package de.heinerkuecker.coroutine_iterator;

import org.junit.Assert;
import org.junit.Test;

import de.heinerkuecker.coroutine_iterator.condition.Lesser;
import de.heinerkuecker.coroutine_iterator.condition.True;
import de.heinerkuecker.coroutine_iterator.step.complex.For;
import de.heinerkuecker.coroutine_iterator.step.complex.While;
import de.heinerkuecker.coroutine_iterator.step.flow.Continue;
import de.heinerkuecker.coroutine_iterator.step.retrn.YieldReturnValue;
import de.heinerkuecker.coroutine_iterator.step.simple.IncVar;
import de.heinerkuecker.coroutine_iterator.step.simple.NoOperation;
import de.heinerkuecker.coroutine_iterator.step.simple.SetVar;

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
                        new SetVar<>( "number" , 0 ) ,
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
                        new SetVar<>( "number" , 0 ) ,
                        new For<Integer, CoroutineIterator<Integer>>(
                                //label
                                "outer_for" ,
                                // initialStep
                                new NoOperation<>() ,
                                // condition
                                new Lesser(
                                        //varName
                                        "number" ,
                                        //compareValue
                                        1 ) ,
                                // updateStep
                                new IncVar<>( "number" ) ,
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
                                        new YieldReturnValue<>( 1 ) ) ) );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    @Test( expected = IllegalArgumentException.class )
    public void test_Negative_For_Continue_in_Initializer()
    {
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        new While<Integer, CoroutineIterator<Integer>>(
                                //condition
                                new True() ,
                                // steps
                                new SetVar<>( "number" , 0 ) ,
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
                        new While<Integer, CoroutineIterator<Integer>>(
                                //condition
                                new True() ,
                                // steps
                                new SetVar<>( "number" , 0 ) ,
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
                        new While<Integer, CoroutineIterator<Integer>>(
                                //condition
                                new True() ,
                                // steps
                                new SetVar<>( "number" , 0 ) ,
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
                        new While<Integer, CoroutineIterator<Integer>>(
                                //condition
                                new True() ,
                                // steps
                                new SetVar<>( "number" , 0 ) ,
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
        CoroutineIterator.initializationChecks = false;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        new SetVar<>( "number" , 0 ) ,
                        new For<Integer, CoroutineIterator<Integer>>(
                                //label
                                "outer_for" ,
                                // initialStep
                                new NoOperation<>() ,
                                // condition
                                new Lesser(
                                        //varName
                                        "number" ,
                                        //compareValue
                                        1 ) ,
                                // updateStep
                                new IncVar<>( "number" ) ,
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
                                        new YieldReturnValue<>( 1 ) ) ) );

        coroIter.hasNext();
    }

    @Test( expected = IllegalArgumentException.class )
    public void test_Negative_For_Continue_wrong_labeled_with_initialization_checks()
    {
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        new SetVar<>( "number" , 0 ) ,
                        new For<Integer, CoroutineIterator<Integer>>(
                                //label
                                "outer_for" ,
                                // initialStep
                                new NoOperation<>() ,
                                // condition
                                new Lesser(
                                        //varName
                                        "number" ,
                                        //compareValue
                                        1 ) ,
                                // updateStep
                                new IncVar<>( "number" ) ,
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
                                        new YieldReturnValue<>( 1 ) ) ) );

        coroIter.hasNext();
    }

    @Test
    public void test_While_Continue()
    {
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        new SetVar<>( "number" , 0 ) ,
                        new While<Integer, CoroutineIterator<Integer>>(
                                //condition
                                new True() ,
                                //steps
                                new YieldReturnValue<>( 0 ) ,
                                new YieldReturnValue<>( 1 ) ,
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
                        new SetVar<>( "number" , 0 ) ,
                        new While<Integer, CoroutineIterator<Integer>>(
                                //label
                                "outer_while" ,
                                //condition
                                new Lesser(
                                        //varName
                                        "number" ,
                                        //compareValue
                                        1 ) ,
                                //steps
                                new While<Integer, CoroutineIterator<Integer>>(
                                        //condition
                                        new True() ,
                                        //steps
                                        new IncVar<>( "number" ) ,
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
                        new SetVar<>( "number" , 0 ) ,
                        new While<Integer, CoroutineIterator<Integer>>(
                                //label
                                "outer_while" ,
                                //condition
                                new Lesser(
                                        //varName
                                        "number" ,
                                        //compareValue
                                        1 ) ,
                                //steps
                                new While<Integer, CoroutineIterator<Integer>>(
                                        //condition
                                        new True() ,
                                        //steps
                                        new IncVar<>( "number" ) ,
                                        new Continue<>(
                                                //label
                                                "wrong" ) ,
                                        // this step is never executed
                                        new NoOperation<>() ) ) );

        coroIter.hasNext();
    }

    @Test( expected = IllegalArgumentException.class )
    public void test_Negative_While_Continue_wrong_labeled_with_initialization_checks()
    {
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        new SetVar<>( "number" , 0 ) ,
                        new While<Integer, CoroutineIterator<Integer>>(
                                //label
                                "outer_while" ,
                                //condition
                                new Lesser(
                                        //varName
                                        "number" ,
                                        //compareValue
                                        1 ) ,
                                //steps
                                new While<Integer, CoroutineIterator<Integer>>(
                                        //condition
                                        new True() ,
                                        //steps
                                        new IncVar<>( "number" ) ,
                                        new Continue<>(
                                                //label
                                                "wrong" ) ,
                                        // this step is never executed
                                        new NoOperation<>() ) ) );

        coroIter.hasNext();
    }

}
