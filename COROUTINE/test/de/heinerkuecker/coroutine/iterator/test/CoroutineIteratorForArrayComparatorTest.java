package de.heinerkuecker.coroutine.iterator.test;

import static de.heinerkuecker.coroutine.exprs.NullValue.nullValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.exprs.GetLocalVar;
import de.heinerkuecker.coroutine.exprs.NewArray;
import de.heinerkuecker.coroutine.exprs.Value;
import de.heinerkuecker.coroutine.stmt.complex.ForEach;
import de.heinerkuecker.coroutine.stmt.ret.YieldReturn;
import de.heinerkuecker.util.ArrayDeepToString;

/**
 * JUnit4 test case for {@link CoroutineIterator}.
 *
 * Special tests to generate test data for
 * <a href="https://github.com/HeinerKuecker/Array-Comparator-Java">Array-Comparator-Java</a>.
 *
 * @author Heiner K&uuml;cker
 */
public class CoroutineIteratorForArrayComparatorTest
{
    // TODO more dims
    // TODO tuple with expected compare result

    @Test
    public void testIntArrDim1()
    {
        final CoroutineIterator<int[]> intArrDim1CoroIter =
                new CoroutineIterator<>(
                        // type
                        int[].class ,
                        new YieldReturn<>( nullValue() ) ,
                        new YieldReturn<>( new int[ 0 ] ) ,
                        new YieldReturn<>( new int[] { 0 } ) ,
                        new YieldReturn<>( new int[] { 1 } ) ,
                        new YieldReturn<>( new int[] { 0 , 1 } ) ,
                        new YieldReturn<>( new int[] { 1 , 0 } ) );

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

        CoroutineIteratorTest.assertHasNextFalse(
                intArrDim1CoroIter );
    }

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

    @Test
    public void testIntArrDim2()
    {
        final Iterable<int[]> intArrDim1CoroIterable =
                new Iterable<int[]>() {

                    @Override
                    public Iterator<int[]> iterator()
                    {
                        return
                                new CoroutineIterator<>(
                                        // type
                                        int[].class ,
                                        new YieldReturn<>( nullValue() ) ,
                                        new YieldReturn<>( new int[ 0 ] ) ,
                                        new YieldReturn<>( new int[] { 0 } ) ,
                                        new YieldReturn<>( new int[] { 1 } ) ,
                                        new YieldReturn<>( new int[] { 0 , 1 } ) ,
                                        new YieldReturn<>( new int[] { 1 , 0 } ) );
                    }

                };

                final CoroutineIterator<int[][]> intArrDim2CoroIter =
                        new CoroutineIterator<int[][]>(
                                // type
                                int[][].class ,
                                // stmts
                                new YieldReturn<Void, int[][] , Void>( nullValue() ) ,
                                new YieldReturn<Void, int[][] , Void>( new int[][] {} ) ,
                                // generate arrays of size 1
                                new ForEach<Void, int[][], Void , int[]>(
                                        // variableName
                                        "intValue" ,
                                        // elementType
                                        int[].class ,
                                        // iterableExpression
                                        new Value<Iterable<int[]>, int[][] , Void>(
                                                (Class<? extends Iterable<int[]>>) Iterable.class ,
                                                intArrDim1CoroIterable ) ,
                                        // stmts
                                        new YieldReturn<Void, int[][] , Void>(
                                                new NewArray<int[], int[][] , Void>(
                                                        // arrayClass
                                                        int[][].class ,
                                                        new GetLocalVar<>(
                                                                // localVarName
                                                                "intValue" ,
                                                                // type
                                                                int[].class ) ) ) ) ,
                                // generate arrays of size 2
                                new ForEach<Void, int[][] , Void, int[]>(
                                        // variableName
                                        "intValue0" ,
                                        // elementType
                                        int[].class ,
                                        // iterableExpression
                                        new Value<Iterable<int[]> , int[][] , Void>(
                                                (Class<? extends Iterable<int[]>>) Iterable.class ,
                                                intArrDim1CoroIterable ) ,
                                        // stmts
                                        new ForEach<Void, int[][], Void , int[]>(
                                                // variableName
                                                "intValue1" ,
                                                // elementType
                                                int[].class ,
                                                // iterableExpression
                                                new Value<Iterable<int[]> , int[][] , Void>(
                                                        (Class<? extends Iterable<int[]>>) Iterable.class ,
                                                        intArrDim1CoroIterable ) ,
                                                new YieldReturn<>(
                                                        new NewArray<int[] , int[][] , Void>(
                                                                // arrayClass
                                                                int[][].class ,
                                                                new GetLocalVar<>(
                                                                        // localVarName
                                                                        "intValue0" ,
                                                                        // type
                                                                        int[].class ) ,
                                                                new GetLocalVar<>(
                                                                        // localVarName
                                                                        "intValue1" ,
                                                                        // type
                                                                        int[].class ) ) ) ) ) );


                int[][] expected = null;
                //System.out.println( ArrayDeepToString.deepToString( expected ) );
                assertNext(
                        intArrDim2CoroIter ,
                        expected );

                expected = new int[][] {};
                //System.out.println( ArrayDeepToString.deepToString( expected ) );
                assertNext(
                        intArrDim2CoroIter ,
                        expected );

                // check array of length 1
                for ( final int[] integerDim1Arr : intArrDim1CoroIterable )
                {
                    expected = new int[][] { integerDim1Arr };
                    //System.out.println( ArrayDeepToString.deepToString( expected ) );
                    assertNext(
                            intArrDim2CoroIter ,
                            expected );
                }

                // check array of length 2
                for ( final int[] integerDim1Arr0 : intArrDim1CoroIterable )
                {

                    for ( final int[] integerDim1Arr1 : intArrDim1CoroIterable )
                    {
                        expected = new int[][] { integerDim1Arr0 , integerDim1Arr1 };
                        //System.out.println( ArrayDeepToString.deepToString( expected ) );
                        assertNext(
                                intArrDim2CoroIter ,
                                expected );
                    }
                }

                CoroutineIteratorTest.assertHasNextFalse(
                        intArrDim2CoroIter );
    }


    @Test
    public void testCmpblArrDim1()
    {
        final CoroutineIterator<Integer[]> intArrDim1CoroIter =
                new CoroutineIterator<>(
                        // type
                        Integer[].class ,
                        new YieldReturn<>( nullValue() ) ,
                        new YieldReturn<>( new Integer[ 0 ] ) ,
                        new YieldReturn<>( new Integer[] { 0 } ) ,
                        new YieldReturn<>( new Integer[] { 1 } ) ,
                        new YieldReturn<>( new Integer[] { 0 , 1 } ) ,
                        new YieldReturn<>( new Integer[] { 1 , 0 } ) );

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

        CoroutineIteratorTest.assertHasNextFalse(
                intArrDim1CoroIter );
    }

    @Test
    public void testCmpblArrDim2()
    {
        final Iterable<Integer> integerIterable =
                Arrays.asList(
                        null ,
                        0 ,
                        1 );

        final Iterable<Integer[]> integerArrDim1Iterable =
                new Iterable<Integer[]>()
        {
                    @Override
                    public Iterator<Integer[]> iterator()
                    {
                        return
                                new CoroutineIterator<Integer[]>(
                                        // type
                                        Integer[].class ,
                                        // stmts
                                        new YieldReturn<>( nullValue() ) ,
                                        new YieldReturn<>( new Integer[] {} ) ,
                                        // generate arrays of size 1
                                        new ForEach<Void, Integer[] , Void, Integer>(
                                                // variableName
                                                "intValue" ,
                                                // elementType
                                                Integer.class ,
                                                // iterableExpression
                                                new Value<Iterable<Integer> , Integer[] , Void>(
                                                        (Class<? extends Iterable<Integer>>) Iterable.class ,
                                                        integerIterable ) ,
                                                // stmts
                                                new YieldReturn<Void, Integer[] , Void>(
                                                        new NewArray<Integer , Integer[] , Void>(
                                                                // arrayClass
                                                                Integer[].class ,
                                                                new GetLocalVar<Integer , Integer[] , Void>(
                                                                        // localVarName
                                                                        "intValue" ,
                                                                        // type
                                                                        Integer.class ) ) ) ) ,
                                        // generate arrays of size 2
                                        new ForEach<Void, Integer[] , Void, Integer>(
                                                // variableName
                                                "intValue0" ,
                                                // elementType
                                                Integer.class ,
                                                // iterableExpression
                                                new Value<Iterable<Integer> , Integer[] , Void>(
                                                        (Class<? extends Iterable<Integer>>) Iterable.class ,
                                                        integerIterable ) ,
                                                // stmts
                                                new ForEach<Void, Integer[] , Void , Integer>(
                                                        // variableName
                                                        "intValue1" ,
                                                        // elementType
                                                        Integer.class ,
                                                        // iterableExpression
                                                        new Value<Iterable<Integer> , Integer[] , Void>(
                                                                (Class<? extends Iterable<Integer>>) Iterable.class ,
                                                                integerIterable ) ,
                                                        new YieldReturn<Void, Integer[] , Void>(
                                                                new NewArray<Integer , Integer[] , Void>(
                                                                        // arrayClass
                                                                        Integer[].class ,
                                                                        new GetLocalVar<Integer , Integer[] , Void>(
                                                                                // localVarName
                                                                                "intValue0" ,
                                                                                // type
                                                                                Integer.class ) ,
                                                                        new GetLocalVar<Integer , Integer[] , Void>(
                                                                                // localVarName
                                                                                "intValue1" ,
                                                                                // type
                                                                                Integer.class ) ) ) ) ) );
                    }

                    @Override
                    public String toString()
                    {
                        return "integerArrDim1Iterable";
                    }

        };

        //for ( final Integer[] integers : integerArrDim1Iterable)
        //{
        //    System.out.println( ArrayDeepToString.deepToString( integers ) );
        //}

        //final Class<? extends Iterator<Integer[]>> iteratorOfQuestionmarkClass = (Class<? extends Iterator<Integer[]>>) Iterator.class;

        final CoroutineIterator<Integer[][]> integerArrDim2CoroIter =
                new CoroutineIterator<>(
                        // type
                        Integer[][].class ,
                        // stmts
                        new YieldReturn<>( nullValue() ) ,
                        new YieldReturn<>( new Integer[][] {} ) ,
                        new ForEach<Void, Integer[][] , Void , Integer[]>(
                                // variableName
                                "subArr" ,
                                // elementType
                                Integer[].class ,
                                // iterableExpression
                                new Value<Iterable<Integer[]> , Integer[][] , Void>(
                                        (Class<? extends Iterable<Integer[]>>) Iterable.class ,
                                        integerArrDim1Iterable ) ,
                                // stmts
                                //new SystemOutPrintln<Integer[][]>(
                                //        new StrConcat(
                                //                "subArr: " ,
                                //                new GetLocalVar<Integer[]>(
                                //                        // localVarName
                                //                        "subArr" ,
                                //                        // type
                                //                        Integer[].class ) ) ) ,
                                new YieldReturn<Void, Integer[][] , Void>(
                                        new NewArray<Integer[] , Integer[][] , Void>(
                                                // arrayClass
                                                Integer[][].class ,
                                                new GetLocalVar<Integer[] , Integer[][] , Void>(
                                                        // localVarName
                                                        "subArr" ,
                                                        // type
                                                        Integer[].class ) ) ) ) ,
                        new ForEach<Void, Integer[][] , Void , Integer[]>(
                                // variableName
                                "subArr0" ,
                                // elementType
                                Integer[].class ,
                                // iterableExpression
                                new Value<Iterable<Integer[]> , Integer[][] , Void>(
                                        (Class<? extends Iterable<Integer[]>>) Iterable.class ,
                                        integerArrDim1Iterable ) ,
                                // stmts
                                //new SystemOutPrintln<Integer[][]>(
                                //        new StrConcat(
                                //                "subArr0: " ,
                                //                new GetLocalVar<Integer[]>(
                                //                        // localVarName
                                //                        "subArr0" ,
                                //                        // type
                                //                        Integer[].class ) ) ) ,
                                new ForEach<Void, Integer[][] , Void , Integer[]>(
                                        // variableName
                                        "subArr1" ,
                                        // elementType
                                        Integer[].class ,
                                        // iterableExpression
                                        new Value<Iterable<Integer[]> , Integer[][] , Void>(
                                                (Class<? extends Iterable<Integer[]>>) Iterable.class ,
                                                integerArrDim1Iterable ) ,
                                        // stmts
                                        //new SystemOutPrintln<Integer[][]>(
                                        //        new StrConcat(
                                        //                "subArr1: " ,
                                        //                new GetLocalVar<Integer[]>(
                                        //                        // localVarName
                                        //                        "subArr1" ,
                                        //                        // type
                                        //                        Integer[].class ) ) ) ,
                                        new YieldReturn<Void, Integer[][] , Void>(
                                                new NewArray<Integer[] , Integer[][] , Void>(
                                                        // arrayClass
                                                        Integer[][].class ,
                                                        new GetLocalVar<Integer[] , Integer[][] , Void>(
                                                                // localVarName
                                                                "subArr0" ,
                                                                // type
                                                                Integer[].class ) ,
                                                        new GetLocalVar<Integer[] , Integer[][] , Void>(
                                                                // localVarName
                                                                "subArr1" ,
                                                                // type
                                                                Integer[].class ) ) ) ) ) );

        Integer[][] expected = null;
        //System.out.println( ArrayDeepToString.deepToString( expected ) );
        assertNext(
                integerArrDim2CoroIter ,
                expected );

        expected = new Integer[][] {};
        //System.out.println( ArrayDeepToString.deepToString( expected ) );
        assertNext(
                integerArrDim2CoroIter ,
                expected );

        // check array of length 1
        for ( final Integer[] integerDim1Arr : cmplDim1ArrIterable() )
        {
            expected = new Integer[][] { integerDim1Arr };
            //System.out.println( ArrayDeepToString.deepToString( expected ) );
            assertNext(
                    integerArrDim2CoroIter ,
                    expected );
        }

        // check array of length 2
        for ( final Integer[] integerDim1Arr0 : cmplDim1ArrIterable() )
        {

            for ( final Integer[] integerDim1Arr1 : cmplDim1ArrIterable() )
            {
                expected = new Integer[][] { integerDim1Arr0 , integerDim1Arr1 };
                //System.out.println( ArrayDeepToString.deepToString( expected ) );
                assertNext(
                        integerArrDim2CoroIter ,
                        expected );
            }
        }

        CoroutineIteratorTest.assertHasNextFalse(
                integerArrDim2CoroIter );
    }

    @Test // TODO remove
    public void testCmplDim1Arr()
    {
        final Iterable<Integer> integerIterable =
                Arrays.asList(
                        null ,
                        0 ,
                        1 );

        Integer[] actual = null;

        System.out.println( ArrayDeepToString.deepToString( actual ) );

        actual = new Integer[] {};

        System.out.println( ArrayDeepToString.deepToString( actual ) );

        //actual = new Integer[] { null };

        for ( final Integer integer : integerIterable )
        {
            //actual[ 0 ] = integer;
            actual = new Integer[] { integer };

            System.out.println( ArrayDeepToString.deepToString( actual ) );
        }
    }

    private Iterable<Integer[]> cmplDim1ArrIterable()
    {
        final List<Integer[]> result = new ArrayList<>();

        final Iterable<Integer> integerIterable =
                Arrays.asList(
                        null ,
                        0 ,
                        1 );

        Integer[] actual = null;

        //System.out.println( ArrayDeepToString.deepToString( actual ) );
        result.add( actual );

        actual = new Integer[] {};

        //System.out.println( ArrayDeepToString.deepToString( actual ) );
        result.add( actual );

        //actual = new Integer[] { null };

        for ( final Integer integer : integerIterable )
        {
            //actual[ 0 ] = integer;
            actual = new Integer[] { integer };

            //System.out.println( ArrayDeepToString.deepToString( actual ) );
            result.add( actual );
        }

        for ( final Integer integer0 : integerIterable )
        {

            for ( final Integer integer1 : integerIterable )
            {
                //actual[ 0 ] = integer;
                actual = new Integer[] { integer0 , integer1 };

                //System.out.println( ArrayDeepToString.deepToString( actual ) );
                result.add( actual );
            }
        }

        return result;
    }

    @Test // TODO remove
    public void testCmplDim2Arr()
    {
        Integer[][] actual = null;
        System.out.println( ArrayDeepToString.deepToString( actual ) );

        actual = new Integer[][] {};
        System.out.println( ArrayDeepToString.deepToString( actual ) );

        //actual = new Integer[][] { null };
        //System.out.println( ArrayDeepToString.deepToString( actual ) );

        for ( final Integer[] integerDim1Arr : cmplDim1ArrIterable() )
        {
            actual = new Integer[][] { integerDim1Arr };
            System.out.println( ArrayDeepToString.deepToString( actual ) );
        }

        for ( final Integer[] integerDim1Arr0 : cmplDim1ArrIterable() )
        {

            for ( final Integer[] integerDim1Arr1 : cmplDim1ArrIterable() )
            {
                actual = new Integer[][] { integerDim1Arr0 , integerDim1Arr1 };
                System.out.println( ArrayDeepToString.deepToString( actual ) );
            }
        }
    }

    /**
     * @param coroIter
     */
    public static <T /*extends Comparable<T>*/> void assertNext(
            final CoroutineIterator<? extends T[]> coroIter ,
                    final T[] expected )
    {
        //System.out.println( coroIter );

        Assert.assertTrue(
                coroIter.hasNext() );

        System.out.println( coroIter );

        final T[] actual = coroIter.next();

        System.out.println( ArrayDeepToString.deepToString( actual ) );
        System.out.println();

        Assert.assertArrayEquals(
                expected ,
                actual );
    }

    /**
     * @param coroIter
     */
    //public static <T extends Comparable<T>> void assertNext(
    //        final CoroutineIterator<T[][]> coroIter ,
    //        final T[][] expected )
    //{
    //    //System.out.println( coroIter );
    //
    //    Assert.assertTrue(
    //            coroIter.hasNext() );
    //
    //    System.out.println( coroIter );
    //
    //    final T[][] actual = coroIter.next();
    //
    //    System.out.println( ArrayDeepToString.deepToString( actual ) );
    //    System.out.println();
    //
    //    Assert.assertArrayEquals(
    //            expected ,
    //            actual );
    //}

}
