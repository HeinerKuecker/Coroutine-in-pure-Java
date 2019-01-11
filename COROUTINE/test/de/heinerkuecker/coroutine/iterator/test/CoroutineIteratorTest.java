package de.heinerkuecker.coroutine.iterator.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.expression.GetLocalVar;
import de.heinerkuecker.coroutine.step.ret.FinallyReturn;
import de.heinerkuecker.coroutine.step.ret.YieldReturn;
import de.heinerkuecker.coroutine.step.simple.DecrementLocalVar;
import de.heinerkuecker.coroutine.step.simple.IncrementLocalVar;
import de.heinerkuecker.coroutine.step.simple.SetLocalVar;

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
                new CoroutineIterator<>(
                        // type
                        Object.class
                        // steps
                        );

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

        final CoroutineIterator<String> coroIter =
                new CoroutineIterator<>(
                        // type
                        String.class ,
                        //procedures
                        null ,
                        params ,
                        // steps
                        new YieldReturn<>(
                                new GetLocalVar<>(
                                        "param" ,
                                        String.class ) ) );

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
                        // type
                        String.class ,
                        // steps
                        new YieldReturn<>( "a" ) );

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
                        // type
                        String.class ,
                        // steps
                        new FinallyReturn<>( "a" ) );

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
                        // type
                        String.class ,
                        // steps
                        new FinallyReturn<>( "a" ) ,
                        // the second step is never executed
                        new YieldReturn<>( "b" ) );

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
                        // type
                        String.class ,
                        // steps
                        new SetLocalVar<>(
                                "str" ,
                                "a" ) ,
                        new YieldReturn<>(
                                new GetLocalVar<>(
                                        "str" ,
                                        String.class ) ) );

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
                        new YieldReturn<>(
                                number ) );

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

        // extract get local variable expression
        final GetLocalVar<Integer> number =
                new GetLocalVar<>(
                        "number" ,
                        Integer.class );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<>(
                        // type
                        Integer.class ,
                        // steps
                        new SetLocalVar<>(
                                "number" ,
                                0 ) ,
                        new IncrementLocalVar<>( "number" ) ,
                        new YieldReturn<>(
                                number ) );

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

        // extract get local variable expression
        final GetLocalVar<Integer> number =
                new GetLocalVar<>(
                        // varName
                        "number" ,
                        Integer.class );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<>(
                        // type
                        Integer.class ,
                        // steps
                        new SetLocalVar<>(
                                "number" ,
                                1 ) ,
                        new DecrementLocalVar<>( "number" ) ,
                        new YieldReturn<>(
                                number ) );

        assertNext(
                coroIter ,
                0 );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    /**
     * @param coroIter
     */
    public static <T> void assertNext(
            final CoroutineIterator</*? extends*/ T> coroIter ,
            final T expected )
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
            //final CoroutineIterator<? extends Object> coroIter
            final CoroutineIterator<?> coroIter )
    {
        //System.out.println( coroIter );

        Assert.assertFalse(
                coroIter.hasNext() );

        System.out.println( "hasNext: false" );
        System.out.println();

        System.out.println( coroIter );
    }

}
