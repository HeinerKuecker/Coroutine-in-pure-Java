package de.heinerkuecker.coroutine_iterator;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import de.heinerkuecker.coroutine_iterator.step.retrn.FinallyReturnValue;
import de.heinerkuecker.coroutine_iterator.step.retrn.YieldReturnValue;
import de.heinerkuecker.coroutine_iterator.step.retrn.YieldReturnVar;
import de.heinerkuecker.coroutine_iterator.step.simple.DecVar;
import de.heinerkuecker.coroutine_iterator.step.simple.IncVar;
import de.heinerkuecker.coroutine_iterator.step.simple.SetVar;

/**
 * JUnit4 test case for {@link CoroutineIterator}.
 *
 * @author Heiner K&uuml;cker
 */
public class CoroutineIteratorTest
{
    @Test
    public void testConstructor_Empty()
    {
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<?> coroIter =
                new CoroutineIterator<>();

        assertHasNextFalse(
                coroIter );
    }

    @Test
    public void testConstructor_Params()
    {
        CoroutineIterator.initializationChecks = true;

        final Map<String, Object> params = new HashMap<>();
        params.put(
                "param" ,
                "x" );

        final CoroutineIterator<?> coroIter =
                new CoroutineIterator<>(
                        params ,
                        new YieldReturnVar<>( "param" ) );

        assertNext(
                coroIter ,
                "x" );

        assertHasNextFalse(
                coroIter );
    }

    @Test
    public void test_1_YieldReturnWithResult()
    {
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<String> coroIter =
                new CoroutineIterator<>(
                        new YieldReturnValue<>( "a" ) );

        assertNext(
                coroIter ,
                "a" );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    @Test
    public void test_1_FinallyReturnWithResult()
    {
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<String> coroIter =
                new CoroutineIterator<>(
                        new FinallyReturnValue<>( "a" ) );

        assertNext(
                coroIter ,
                "a" );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    @Test
    public void test_2_FinallyReturnWithResult()
    {
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<String> coroIter =
                new CoroutineIterator<>(
                        //parent -> new CoroIterStepResult.FinallyReturnWithResult<String>( "a" )
                        new FinallyReturnValue<>( "a" ) ,
                        // the second step is never executed
                        new YieldReturnValue<>( "b" ) );

        assertNext(
                coroIter ,
                "a" );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    @Test
    public void test_SetVar_String()
    {
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<String> coroIter =
                new CoroutineIterator<String>(
                        new SetVar<>( "str" , "a" ) ,
                        new YieldReturnVar<String>( "str" ) );

        assertNext(
                coroIter ,
                "a" );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    @Test
    public void test_SetVar_Integer()
    {
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        new SetVar<>( "number" , 0 ) ,
                        new YieldReturnVar<Integer>( "number" ) );

        Assert.assertTrue(
                coroIter.hasNext() );

        assertNext(
                coroIter ,
                0 );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    @Test
    public void test_IncVar()
    {
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<>(
                        new SetVar<>( "number" , 0 ) ,
                        new IncVar<>( "number" ) ,
                        new YieldReturnVar<>( "number" ) );

        assertNext(
                coroIter ,
                1 );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    @Test
    public void test_DecVar()
    {
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<>(
                        new SetVar<>( "number" , 1 ) ,
                        new DecVar<>( "number" ) ,
                        new YieldReturnVar<>( "number" ) );

        assertNext(
                coroIter ,
                0 );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    /**
     * @param coroIter
     */
    public static void assertNext(
            final CoroutineIterator<? extends Object> coroIter ,
            final Object expected )
    {
        //System.out.println( coroIter );

        Assert.assertTrue(
                coroIter.hasNext() );

        System.out.println( coroIter );

        final Object actual = coroIter.next();

        System.out.println( actual );
        System.out.println();

        Assert.assertEquals(
                expected ,
                actual );
    }

    /**
     * @param coroIter
     */
    public static void assertHasNextFalse(
            final CoroutineIterator<? extends Object> coroIter )
    {
        System.out.println( coroIter );

        Assert.assertFalse(
                coroIter.hasNext() );

        System.out.println( coroIter );
    }

}
