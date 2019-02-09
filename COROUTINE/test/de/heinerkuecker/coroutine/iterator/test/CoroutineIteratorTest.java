package de.heinerkuecker.coroutine.iterator.test;

import org.junit.Assert;
import org.junit.Test;

import de.heinerkuecker.coroutine.CoroutineDebugSwitches;
import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.arg.Argument;
import de.heinerkuecker.coroutine.arg.Parameter;
import de.heinerkuecker.coroutine.exprs.GetGlobalArgument;
import de.heinerkuecker.coroutine.exprs.GetLocalVar;
import de.heinerkuecker.coroutine.stmt.ret.FinallyReturn;
import de.heinerkuecker.coroutine.stmt.ret.YieldReturn;
import de.heinerkuecker.coroutine.stmt.simple.DeclareVariable;
import de.heinerkuecker.coroutine.stmt.simple.DecrementLocalVar;
import de.heinerkuecker.coroutine.stmt.simple.IncrementLocalVar;

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
        CoroutineDebugSwitches.initializationChecks = true;

        final CoroutineIterator<?> coroIter =
                new CoroutineIterator<>(
                        // type
                        Object.class
                        // stmts
                        );

        assertHasNextFalse(
                coroIter );
    }

    @Test
    public void testConstructor_Params_Args()
    {
        CoroutineDebugSwitches.initializationChecks = true;


        final Parameter[] params = {
                new Parameter(
                        // name
                        "param" ,
                        // isMandantory
                        true ,
                        // type
                        String.class )
        };

        final Argument<? , ?>[] args = {
                new Argument<String , String>(
                        // name
                        "param" ,
                        // value
                        "x" )
        };

        final CoroutineIterator<String> coroIter =
                new CoroutineIterator<>(
                        // type
                        String.class ,
                        // functions
                        null ,
                        params ,
                        args ,
                        // globalVariables
                        null ,
                        // stmts
                        new YieldReturn<>(
                                new GetGlobalArgument<>(
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
        CoroutineDebugSwitches.initializationChecks = true;

        final CoroutineIterator<String> coroIter =
                new CoroutineIterator<>(
                        // type
                        String.class ,
                        // stmts
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
        CoroutineDebugSwitches.initializationChecks = true;

        final CoroutineIterator<String> coroIter =
                new CoroutineIterator<>(
                        // type
                        String.class ,
                        // stmts
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
        CoroutineDebugSwitches.initializationChecks = true;

        final CoroutineIterator<String> coroIter =
                new CoroutineIterator<>(
                        // type
                        String.class ,
                        // stmts
                        new FinallyReturn<>( "a" ) ,
                        // the second statement is never executed
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
        CoroutineDebugSwitches.initializationChecks = true;

        final CoroutineIterator<String> coroIter =
                new CoroutineIterator<String>(
                        // type
                        String.class ,
                        // stmts
                        new DeclareVariable<>(
                                "str" ,
                                String.class ,
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
        CoroutineDebugSwitches.initializationChecks = true;

        // extract get local variable expression
        final GetLocalVar<Integer , Integer> number =
                new GetLocalVar<>(
                        // varName
                        "number" ,
                        Integer.class );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // stmts
                        new DeclareVariable<>(
                                "number" ,
                                Integer.class ,
                                0 ) ,
                        new YieldReturn<>( number ) );

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
        CoroutineDebugSwitches.initializationChecks = true;

        // extract get local variable expression
        final GetLocalVar<Integer , Integer> number =
                new GetLocalVar<>(
                        "number" ,
                        Integer.class );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<>(
                        // type
                        Integer.class ,
                        // stmts
                        new DeclareVariable<>(
                                "number" ,
                                Integer.class ,
                                0 ) ,
                        new IncrementLocalVar<>( "number" ) ,
                        new YieldReturn<>( number ) );

        assertNext(
                coroIter ,
                1 );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    @Test
    public void test_DecVar()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        // extract get local variable expression
        final GetLocalVar<Integer , Integer> number =
                new GetLocalVar<>(
                        // varName
                        "number" ,
                        Integer.class );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<>(
                        // type
                        Integer.class ,
                        // stmts
                        new DeclareVariable<>(
                                "number" ,
                                Integer.class ,
                                1 ) ,
                        new DecrementLocalVar<>( "number" ) ,
                        new YieldReturn<>( number ) );

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
