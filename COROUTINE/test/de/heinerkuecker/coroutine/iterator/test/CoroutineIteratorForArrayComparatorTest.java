package de.heinerkuecker.coroutine.iterator.test;

import java.util.Arrays;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;

import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.condition.IteratorHasNext;
import de.heinerkuecker.coroutine.expression.GetLocalVar;
import de.heinerkuecker.coroutine.expression.IteratorNext;
import de.heinerkuecker.coroutine.expression.NewArray;
import de.heinerkuecker.coroutine.expression.Value;
import de.heinerkuecker.coroutine.step.complex.While;
import de.heinerkuecker.coroutine.step.retrn.YieldReturn;
import de.heinerkuecker.coroutine.step.simple.SetLocalVar;
import de.heinerkuecker.util.ArrayDeepToString;

public class CoroutineIteratorForArrayComparatorTest
{
    @Test
    public void testIntArrDim1()
    {
        final CoroutineIterator<int[]> intArrDim1CoroIter =
                new CoroutineIterator<>(
                        // type
                        int[].class ,
                        new YieldReturn<>( new Value<>( null ) ) ,
                        new YieldReturn<>( new Value<>( new int[ 0 ] ) ) ,
                        new YieldReturn<>( new Value<>( new int[] { 0 } ) ) ,
                        new YieldReturn<>( new Value<>( new int[] { 1 } ) ) ,
                        new YieldReturn<>( new Value<>( new int[] { 0 , 1 } ) ) ,
                        new YieldReturn<>( new Value<>( new int[] { 1 , 0 } ) ) );

        assertNext(
                intArrDim1CoroIter ,
                null );

        assertNext(
                intArrDim1CoroIter ,
                new int[ 0 ] );

        assertNext(
                intArrDim1CoroIter ,
                new int[] { 0 } );

        assertNext(
                intArrDim1CoroIter ,
                new int[] { 1 } );

        assertNext(
                intArrDim1CoroIter ,
                new int[] { 0 , 1 } );

        assertNext(
                intArrDim1CoroIter ,
                new int[] { 1 , 0 } );

        assertHasNextFalse(
                intArrDim1CoroIter );
    }

    /**
     * @param coroIter
     */
    public static void assertNext(
            final CoroutineIterator<int[]> coroIter ,
            final int[] expected )
    {
        //System.out.println( coroIter );

        Assert.assertTrue(
                coroIter.hasNext() );

        System.out.println( coroIter );

        final int[] actual = coroIter.next();

        System.out.println( Arrays.toString( actual ) );
        System.out.println();

        Assert.assertArrayEquals(
                expected ,
                actual );
    }

    /**
     * @param coroIter
     */
    public static void assertHasNextFalse(
            final CoroutineIterator<?> coroIter )
    {
        //System.out.println( coroIter );

        Assert.assertFalse(
                coroIter.hasNext() );

        System.out.println( "hasNext: false" );
        System.out.println();

        System.out.println( coroIter );
    }

    @Test
    public void testCmpblArrDim1()
    {
        final CoroutineIterator<Integer[]> intArrDim1CoroIter =
                new CoroutineIterator<>(
                        // type
                        Integer[].class ,
                        new YieldReturn<>( new Value<>( null ) ) ,
                        new YieldReturn<>( new Value<>( new Integer[ 0 ] ) ) ,
                        new YieldReturn<>( new Value<>( new Integer[] { 0 } ) ) ,
                        new YieldReturn<>( new Value<>( new Integer[] { 1 } ) ) ,
                        new YieldReturn<>( new Value<>( new Integer[] { 0 , 1 } ) ) ,
                        new YieldReturn<>( new Value<>( new Integer[] { 1 , 0 } ) ) );

        assertNext(
                intArrDim1CoroIter ,
                null );

        assertNext(
                intArrDim1CoroIter ,
                new Integer[ 0 ] );

        assertNext(
                intArrDim1CoroIter ,
                new Integer[] { 0 } );

        assertNext(
                intArrDim1CoroIter ,
                new Integer[] { 1 } );

        assertNext(
                intArrDim1CoroIter ,
                new Integer[] { 0 , 1 } );

        assertNext(
                intArrDim1CoroIter ,
                new Integer[] { 1 , 0 } );

        assertHasNextFalse(
                intArrDim1CoroIter );
    }

    @Test
    public void testCmpblArrDim2()
    {
        final Iterable<Integer[]> intArrDim1CoroIterable =
                new Iterable<Integer[]>()
                {

                    @Override
                    public Iterator<Integer[]> iterator()
                    {
                        return
                                new CoroutineIterator<>(
                                        // type
                                        Integer[].class ,
                                        // steps
                                        new YieldReturn<>( new Value<>( null ) ) ,
                                        new YieldReturn<>( new Value<>( new Integer[] {} ) ) ,
                                        new YieldReturn<>( new Value<>( new Integer[] { 0 } ) ) ,
                                        new YieldReturn<>( new Value<>( new Integer[] { 1 } ) ) ,
                                        new YieldReturn<>( new Value<>( new Integer[] { 0 , 1 } ) ) ,
                                        new YieldReturn<>( new Value<>( new Integer[] { 1 , 0 } ) ) );
                    }
                };

        final Class<? extends Iterator<Integer[]>> iteratorOfQuestionmarkClass = (Class<? extends Iterator<Integer[]>>) Iterator.class;

        final CoroutineIterator<Integer[][]> intArrDim2CoroIter =
                new CoroutineIterator<>(
                        // type
                        Integer[][].class ,
                        // steps
                        new YieldReturn<>( new Value<>( null ) ) ,
                        new YieldReturn<>( new Value<>( new Integer[ 0 ][ 0 ] ) ) ,
                        new SetLocalVar<>(
                                //varName
                                "iter0" ,
                                //varValueExpression
                                new Value<>( intArrDim1CoroIterable.iterator() ) ) ,
                        new While<Integer[][]>(
                                // condition
                                new IteratorHasNext(
                                        new GetLocalVar<Iterator<?>>(
                                                //localVarName
                                                "iter0" ,
                                                //type
                                                iteratorOfQuestionmarkClass ) ) ,
                                // steps
                                new SetLocalVar<>(
                                        //varName
                                        "subArr0" ,
                                        //varValueExpression
                                        new IteratorNext<Integer[]>(
                                                new GetLocalVar<Iterator<Integer[]>>(
                                                        //localVarName
                                                        "iter0" ,
                                                        //type
                                                        iteratorOfQuestionmarkClass ) ) ) ,
                                new SetLocalVar<>(
                                        //varName
                                        "iter1" ,
                                        //varValueExpression
                                        new Value<>( intArrDim1CoroIterable.iterator() ) ) ,
                                new While<>(
                                        // condition
                                        new IteratorHasNext(
                                                new GetLocalVar<Iterator<?>>(
                                                        //localVarName
                                                        "iter1" ,
                                                        //type
                                                        iteratorOfQuestionmarkClass ) ) ,
                                        // steps
                                        new YieldReturn<Integer[][]>(
                                                new NewArray<Integer[]>(
                                                        // componentClass
                                                        Integer[].class ,
                                                        new GetLocalVar<Integer[]>(
                                                                //localVarName
                                                                "subArr0" ,
                                                                //type
                                                                Integer[].class ) ,
                                                        new IteratorNext<Integer[]>(
                                                                new GetLocalVar<Iterator<Integer[]>>(
                                                                        //localVarName
                                                                        "iter1" ,
                                                                        //type
                                                                        iteratorOfQuestionmarkClass ) ) ) ) ) ) );

        assertNext(
                intArrDim2CoroIter ,
                null );

        assertNext(
                intArrDim2CoroIter ,
                new Integer[ 0 ][ 0 ] );

        assertNext(
                intArrDim2CoroIter ,
                new Integer[][] { null , null } );

        assertNext(
                intArrDim2CoroIter ,
                new Integer[][] { null , {} } );

        assertNext(
                intArrDim2CoroIter ,
                new Integer[][] { null , { 0 } } );

        assertNext(
                intArrDim2CoroIter ,
                new Integer[][] { null , { 1 } } );

        assertNext(
                intArrDim2CoroIter ,
                new Integer[][] { null , { 0 , 1 } } );

        assertNext(
                intArrDim2CoroIter ,
                new Integer[][] { null , { 1 , 0 } } );

        assertNext(
                intArrDim2CoroIter ,
                new Integer[][] { {} , null } );

        assertNext(
                intArrDim2CoroIter ,
                new Integer[][] { {} , {} } );

        assertNext(
                intArrDim2CoroIter ,
                new Integer[][] { {} , { 1 } } );

        assertNext(
                intArrDim2CoroIter ,
                new Integer[][] { {} , { 0 , 1 } } );

        assertNext(
                intArrDim2CoroIter ,
                new Integer[][] { {} , { 1 , 0 } } );


        assertHasNextFalse(
                intArrDim2CoroIter );
    }

    /**
     * @param coroIter
     */
    public static <T extends Comparable<T>> void assertNext(
            final CoroutineIterator<T[]> coroIter ,
            final T[] expected )
    {
        //System.out.println( coroIter );

        Assert.assertTrue(
                coroIter.hasNext() );

        System.out.println( coroIter );

        final T[] actual = coroIter.next();

        System.out.println( actual );
        System.out.println();

        Assert.assertArrayEquals(
                expected ,
                actual );
    }

    /**
     * @param coroIter
     */
    public static <T extends Comparable<T>> void assertNext(
            final CoroutineIterator<T[][]> coroIter ,
            final T[][] expected )
    {
        //System.out.println( coroIter );

        Assert.assertTrue(
                coroIter.hasNext() );

        System.out.println( coroIter );

        final T[][] actual = coroIter.next();

        System.out.println( ArrayDeepToString.deepToString( actual ) );
        System.out.println();

        Assert.assertArrayEquals(
                expected ,
                actual );
    }

}
