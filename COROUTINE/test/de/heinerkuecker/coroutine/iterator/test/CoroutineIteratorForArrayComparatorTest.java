package de.heinerkuecker.coroutine.iterator.test;

import java.util.Arrays;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;

import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.expression.ArrayDeepToStr;
import de.heinerkuecker.coroutine.expression.GetLocalVar;
import de.heinerkuecker.coroutine.expression.NewArray;
import de.heinerkuecker.coroutine.expression.StrConcat;
import de.heinerkuecker.coroutine.expression.Value;
import de.heinerkuecker.coroutine.step.complex.ForEach;
import de.heinerkuecker.coroutine.step.retrn.YieldReturn;
import de.heinerkuecker.coroutine.step.simple.SystemOutPrintln;
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
                        new ForEach<Integer[][], Integer[]>(
                                // variableName
                                "subArr0" ,
                                // iterableExpression
                                new Value<>( intArrDim1CoroIterable ) ,
                                // steps
                                new SystemOutPrintln<Integer[][]>(
                                        new StrConcat(
                                                new Value<>( "subArr0: " ) ,
                                                new ArrayDeepToStr(
                                                        new GetLocalVar<Integer[]>(
                                                                //localVarName
                                                                "subArr0" ,
                                                                //type
                                                                Integer[].class ) ) ) ) ,
                        new ForEach<Integer[][], Integer[]>(
                                // variableName
                                "subArr1" ,
                                // iterableExpression
                                new Value<>( intArrDim1CoroIterable ) ,
                                // steps
                                new SystemOutPrintln<Integer[][]>(
                                        new StrConcat(
                                                new Value<>( "subArr1: " ) ,
                                                new ArrayDeepToStr(
                                                        new GetLocalVar<Integer[]>(
                                                                //localVarName
                                                                "subArr1" ,
                                                                //type
                                                                Integer[].class ) ) ) ) ,
                                new YieldReturn<Integer[][]>(
                                        new NewArray<Integer[]>(
                                                // componentClass
                                                Integer[].class ,
                                                new GetLocalVar<Integer[]>(
                                                        //localVarName
                                                        "subArr0" ,
                                                        //type
                                                        Integer[].class ) ,
                                                new GetLocalVar<Integer[]>(
                                                        //localVarName
                                                        "subArr1" ,
                                                        //type
                                                        Integer[].class ) ) ) ) ) );

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
                new Integer[][] { {} , { 0 } } );

        assertNext(
                intArrDim2CoroIter ,
                new Integer[][] { {} , { 1 } } );

        assertNext(
                intArrDim2CoroIter ,
                new Integer[][] { {} , { 0 , 1 } } );

        assertNext(
                intArrDim2CoroIter ,
                new Integer[][] { {} , { 1 , 0 } } );

        assertNext(
                intArrDim2CoroIter ,
                new Integer[][] { { 0 } , null } );

        assertNext(
                intArrDim2CoroIter ,
                new Integer[][] { { 0 } , {} } );

        assertNext(
                intArrDim2CoroIter ,
                new Integer[][] { { 0 } , { 0 } } );

        assertNext(
                intArrDim2CoroIter ,
                new Integer[][] { { 0 } , { 1 } } );

        assertNext(
                intArrDim2CoroIter ,
                new Integer[][] { { 0 } , { 0 , 1 } } );

        assertNext(
                intArrDim2CoroIter ,
                new Integer[][] { { 0 } , { 1 , 0 } } );

        assertNext(
                intArrDim2CoroIter ,
                new Integer[][] { { 1 } , null } );

        assertNext(
                intArrDim2CoroIter ,
                new Integer[][] { { 1 } , {} } );

        assertNext(
                intArrDim2CoroIter ,
                new Integer[][] { { 1 } , { 0 } } );

        assertNext(
                intArrDim2CoroIter ,
                new Integer[][] { { 1 } , { 1 } } );

        assertNext(
                intArrDim2CoroIter ,
                new Integer[][] { { 1 } , { 0 , 1 } } );

        assertNext(
                intArrDim2CoroIter ,
                new Integer[][] { { 1 } , { 1 , 0 } } );

        assertNext(
                intArrDim2CoroIter ,
                new Integer[][] { { 0 , 1 } , null } );

        assertNext(
                intArrDim2CoroIter ,
                new Integer[][] { { 0 , 1 } , {} } );

        assertNext(
                intArrDim2CoroIter ,
                new Integer[][] { { 0 , 1 } , { 0 } } );

        assertNext(
                intArrDim2CoroIter ,
                new Integer[][] { { 0 , 1 } , { 1 } } );

        assertNext(
                intArrDim2CoroIter ,
                new Integer[][] { { 0 , 1 } , { 0 , 1 } } );

        assertNext(
                intArrDim2CoroIter ,
                new Integer[][] { { 0 , 1 } , { 1 , 0 } } );

        assertNext(
                intArrDim2CoroIter ,
                new Integer[][] { { 1 , 0 } , null } );

        assertNext(
                intArrDim2CoroIter ,
                new Integer[][] { { 1 , 0 } , {} } );

        assertNext(
                intArrDim2CoroIter ,
                new Integer[][] { { 1 , 0 } , { 0 } } );

        assertNext(
                intArrDim2CoroIter ,
                new Integer[][] { { 1 , 0 } , { 1 } } );

        assertNext(
                intArrDim2CoroIter ,
                new Integer[][] { { 1 , 0 } , { 0 , 1 } } );

        assertNext(
                intArrDim2CoroIter ,
                new Integer[][] { { 1 , 0 } , { 1 , 0 } } );

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
