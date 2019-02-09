package de.heinerkuecker.coroutine.iterator.test;

import java.util.Arrays;

import org.junit.Test;

import de.heinerkuecker.coroutine.CoroutineDebugSwitches;
import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.Function;
import de.heinerkuecker.coroutine.arg.Argument;
import de.heinerkuecker.coroutine.arg.Arguments;
import de.heinerkuecker.coroutine.arg.Parameter;
import de.heinerkuecker.coroutine.exprs.GetFunctionArgument;
import de.heinerkuecker.coroutine.exprs.GetGlobalVar;
import de.heinerkuecker.coroutine.exprs.GetLocalVar;
import de.heinerkuecker.coroutine.exprs.Value;
import de.heinerkuecker.coroutine.exprs.bool.Lesser;
import de.heinerkuecker.coroutine.exprs.num.IntAdd;
import de.heinerkuecker.coroutine.exprs.num.LongAdd;
import de.heinerkuecker.coroutine.stmt.complex.FunctionCall;
import de.heinerkuecker.coroutine.stmt.complex.If;
import de.heinerkuecker.coroutine.stmt.ret.FinallyReturn;
import de.heinerkuecker.coroutine.stmt.ret.FinallyReturnWithoutResult;
import de.heinerkuecker.coroutine.stmt.ret.FunctionReturn;
import de.heinerkuecker.coroutine.stmt.ret.YieldReturn;
import de.heinerkuecker.coroutine.stmt.simple.DeclareVariable;
import de.heinerkuecker.coroutine.stmt.simple.IncrementGlobalVar;
import de.heinerkuecker.coroutine.stmt.simple.IncrementLocalVar;
import de.heinerkuecker.coroutine.stmt.simple.NoOperation;

/**
 * JUnit4 test case for {@link CoroutineIterator}.
 *
 * @author Heiner K&uuml;cker
 */
public class CoroutineIteratorFunctionTest
{
    @Test( expected = IllegalArgumentException.class )
    public void testConstructor_Empty()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        final Function<Void, Integer , Void> empty_function =
                new Function<>(
                        "empty_function" ,
                        // params
                        null );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // functions
                        Arrays.asList(
                                empty_function ) ,
                        // params
                        null ,
                        // args
                        null ,
                        // globalVariables
                        null ,
                        // stmts
                        new FunctionCall<Void, Integer, Void>(
                                "empty_function" ,
                                //functionReturnType
                                Void.class ) );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    @Test
    public void test_0_0()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        final Function<Void, Integer , Void> function =
                new Function<>(
                        "function" ,
                        // params
                        null ,
                        new FinallyReturnWithoutResult<>() );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // functions
                        Arrays.asList( function ) ,
                        // params
                        null ,
                        // args
                        null ,
                        // globalVariables
                        null ,
                        // stmts
                        new FunctionCall<>(
                                "function" ,
                                //functionReturnType
                                Void.class ) );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    @Test
    public void test_0_1()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        final Function<Void, Integer , Void> function =
                new Function<>(
                        "function" ,
                        // params
                        null ,
                        new YieldReturn<>( 0 ) );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // functions
                        Arrays.asList( function ) ,
                        // params
                        null ,
                        // args
                        null ,
                        // globalVariables
                        null ,
                        // stmts
                        new FunctionCall<>(
                                "function" ,
                                //functionReturnType
                                Void.class ) );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                0 );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    @Test
    public void test_1_0_LocalVar()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        final Function<Void, Integer , Void> function =
                new Function<>(
                        "function" ,
                        // params
                        null ,
                        // stmts
                        new DeclareVariable<>(
                                // varName
                                "counter" ,
                                // type
                                Integer.class ,
                                // varValue
                                0 ) ,
                        new IncrementLocalVar<>( "counter" ) ,
                        new YieldReturn<>(
                                new GetLocalVar<>(
                                        "counter" ,
                                        Integer.class ) ) );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // functions
                        Arrays.asList( function ) ,
                        // params
                        null ,
                        // args
                        null ,
                        // globalVariables
                        null ,
                        // stmts
                        new FunctionCall<>(
                                "function" ,
                                //functionReturnType
                                Void.class ) );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                1 );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    @Test
    public void test_1_0_GlobalVar()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        final Function<Void, Integer , Void> function =
                new Function<>(
                        "function" ,
                        // params
                        null ,
                        // stmts
                        new IncrementGlobalVar<>( "counter" ) ,
                        new YieldReturn<>(
                                new GetGlobalVar<>(
                                        "counter" ,
                                        Integer.class ) ) );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // functions
                        Arrays.asList( function ) ,
                        // params
                        null ,
                        // args
                        null ,
                        // globalVariables
                        new DeclareVariable[] {
                                new DeclareVariable<>(
                                        // varName
                                        "counter" ,
                                        // type
                                        Integer.class ,
                                        // varValue
                                        0 )
                        } ,
                        // stmts
                        new FunctionCall<>(
                                "function" ,
                                //functionReturnType
                                Void.class ) );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                1 );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    @Test
    public void test_1_1()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        final Function<Void, Integer , Void> function =
                new Function<>(
                        "function" ,
                        // params
                        null ,
                        // stmts
                        new DeclareVariable<>(
                                // varName
                                "counter" ,
                                // type
                                Integer.class ,
                                // varValue
                                0 ) ,
                        new IncrementLocalVar<>( "counter" ) ,
                        new YieldReturn<>(
                                new GetLocalVar<>(
                                        "counter" ,
                                        Integer.class ) ) ,
                        new NoOperation<>() );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // functions
                        Arrays.asList( function ) ,
                        // params
                        null ,
                        // args
                        null ,
                        // globalVariables
                        null ,
                        // stmts
                        new FunctionCall<>(
                                "function" ,
                                //functionReturnType
                                Void.class ) );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                1 );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    @Test
    public void test_2_0_GlobalVar()
    {
        //CoroutineDebugSwitches.initializationChecks = true;
        CoroutineDebugSwitches.initializationChecks = true;
        CoroutineDebugSwitches.saveToStringInfos = true;

        final Function<Void, Integer , Void> function =
                new Function<>(
                        "function" ,
                        // params
                        null ,
                        // stmts
                        new IncrementGlobalVar<>( "counter" ) ,
                        new YieldReturn<>(
                                new GetGlobalVar<>(
                                        "counter" ,
                                        Integer.class ) ) );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // functions
                        Arrays.asList( function ) ,
                        // params
                        null ,
                        // args
                        null ,
                        // globalVariables
                        new DeclareVariable[] {
                            new DeclareVariable<>(
                                    // varName
                                    "counter" ,
                                    // type
                                    Integer.class ,
                                    // varValue
                                    0 )
                        } ,
                        // stmts
                        new FunctionCall<>(
                                "function" ,
                                //functionReturnType
                                Void.class ) ,
                        new FunctionCall<>(
                                "function" ,
                                //functionReturnType
                                Void.class ) );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                1 );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                2 );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    @Test
    public void test_ValueFunctionArgument_0_1()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        final Function<Void, Integer , Void> function =
                new Function<>(
                        "function" ,
                        // params
                        new Parameter[] {
                                new Parameter(
                                        // name
                                        "argument" ,
                                        // isMandantory
                                        true ,
                                        // type
                                        Integer.class )
                        } ,
                        // stmts
                        new YieldReturn<>(
                                new GetFunctionArgument<>(
                                        "argument" ,
                                        Integer.class ) ) );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // functions
                        Arrays.asList( function ) ,
                        // params
                        null ,
                        // args
                        null ,
                        // globalVariables
                        null ,
                        // stmts
                        new FunctionCall<>(
                                "function" ,
                                //functionReturnType
                                Void.class ,
                                new Argument<>(
                                        // name
                                        "argument" ,
                                        // value
                                        0 ) ) );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                0 );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    @Test( expected = Argument.ArgumentNotDeclaredException.class )
    public void test_ValueFunctionArgument_negative_wrong_initializationChecks()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        final Function<Void, Integer , Void> function =
                new Function<>(
                        "function" ,
                        // params
                        new Parameter[] {
                                new Parameter(
                                        // name
                                        "argument" ,
                                        // isMandantory
                                        true ,
                                        // type
                                        Integer.class )
                        } ,
                        // stmts
                        new YieldReturn<>(
                                new GetFunctionArgument<>(
                                        "argument" ,
                                        Integer.class ) ) );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // functions
                        Arrays.asList( function ) ,
                        // params
                        null ,
                        // args
                        null ,
                        // globalVariables
                        null ,
                        // stmts
                        new FunctionCall<>(
                                "function" ,
                                //functionReturnType
                                Void.class ,
                                new Argument<>(
                                        // name
                                        "wrong_argument" ,
                                        // value
                                        0 ) ) );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                0 );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    @Test( expected = IllegalArgumentException.class )
    public void test_ValueFunctionArgument_negative_wrong()
    {
        CoroutineDebugSwitches.initializationChecks = false;

        final Function<Void, Integer , Void> function =
                new Function<>(
                        "function" ,
                        // params
                        new Parameter[] {
                                new Parameter(
                                        // name
                                        "argument" ,
                                        // isMandantory
                                        true ,
                                        // type
                                        Integer.class )
                        } ,
                        // stmts
                        new YieldReturn<>(
                                new GetFunctionArgument<>(
                                        "argument" ,
                                        Integer.class ) ) );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // functions
                        Arrays.asList( function ) ,
                        // params
                        null ,
                        // args
                        null ,
                        // globalVariables
                        null ,
                        // stmts
                        new FunctionCall<>(
                                "function" ,
                                //functionReturnType
                                Void.class ,
                                new Argument<>(
                                        // name
                                        "wrong_argument" ,
                                        // value
                                        0 ) ) );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                0 );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    @Test( expected = GetFunctionArgument.FunctionArgumentNotDeclaredException.class )
    public void test_ValueFunctionArgument_negative_not_declared()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        final Function<Void, Integer , Void> function =
                new Function<>(
                        "function" ,
                        // params
                        new Parameter[] {
                                new Parameter(
                                        // name
                                        "argument0" ,
                                        // isMandantory
                                        true ,
                                        // type
                                        Integer.class ) ,
                                new Parameter(
                                        // name
                                        "argument1" ,
                                        // isMandantory
                                        true ,
                                        // type
                                        Integer.class )
                        } ,
                        // stmts
                        new YieldReturn<>(
                                new GetFunctionArgument<>(
                                        "argument" ,
                                        Integer.class ) ) );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // functions
                        Arrays.asList( function ) ,
                        // params
                        null ,
                        // args
                        null ,
                        // globalVariables
                        null ,
                        // stmts
                        new FunctionCall<>(
                                "function" ,
                                //functionReturnType
                                Void.class ,
                                new Argument<>(
                                        // name
                                        "argument0" ,
                                        // value
                                        0 ) ) );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                0 );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    @Test( expected = Arguments.MissedArgumentException.class )
    public void test_ValueFunctionArgument_negative_missed()
    {
        CoroutineDebugSwitches.initializationChecks = false;

        final Function<Void, Integer , Void> function =
                new Function<>(
                        "function" ,
                        // params
                        new Parameter[] {
                                new Parameter(
                                        //name
                                        "argument0" ,
                                        // isMandantory
                                        true ,
                                        // type
                                        Integer.class ) ,
                                new Parameter(
                                        // name
                                        "argument1" ,
                                        // isMandantory
                                        true ,
                                        // type
                                        Integer.class )
                        } ,
                        // stmts
                        new YieldReturn<>(
                                new GetFunctionArgument<>(
                                        "argument" ,
                                        Integer.class ) ) );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // functions
                        Arrays.asList( function ) ,
                        // params
                        null ,
                        // args
                        null ,
                        // globalVariables
                        null ,
                        // stmts
                        new FunctionCall<>(
                                "function" ,
                                //functionReturnType
                                Void.class ,
                                new Argument<>(
                                        // name
                                        "argument0" ,
                                        // value
                                        0 ) ) );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                0 );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    @Test
    public void test_LocalVarFunctionArgument_0_0()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        final Function<Void, Integer , Void> function =
                new Function<>(
                        "function" ,
                        new Parameter[] {
                                new Parameter(
                                        // name
                                        "argument" ,
                                        // isMandantory
                                        true ,
                                        // type
                                        Integer.class )
                        } ,
                        // stmts
                        new YieldReturn<>(
                                new GetFunctionArgument<>(
                                        "argument" ,
                                        Integer.class ) ) );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // functions
                        Arrays.asList( function ) ,
                        // params
                        null ,
                        // args
                        null ,
                        // globalVariables
                        new DeclareVariable[] {
                                new DeclareVariable<>(
                                        // varName
                                        "number" ,
                                        // type
                                        Integer.class ,
                                        // varValue
                                        0 ) ,
                        } ,
                        // stmts
                        new FunctionCall<>(
                                "function" ,
                                //functionReturnType
                                Void.class ,
                                new Argument<>(
                                        // functionArgumentName
                                        "argument" ,
                                        // expression
                                        new GetLocalVar<>(
                                                // localVarName
                                                "number" ,
                                                Integer.class ) ) ) );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                0 );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    @Test
    public void test_GlobalVarFunctionArgument_0_0()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        final Function<Void, Integer , Void> function0 =
                new Function<>(
                        "function0" ,
                        // params
                        new Parameter[] {
                                new Parameter(
                                        //name
                                        "argument" ,
                                        // isMandantory
                                        true ,
                                        // type
                                        Integer.class )
                        } ,
                        // stmts
                        new YieldReturn<>(
                                new GetFunctionArgument<>(
                                        "argument" ,
                                        Integer.class ) ) );

        final Function<Void, Integer , Void> function1 =
                new Function<>(
                        "function1" ,
                        // params
                        new Parameter[] {
                                new Parameter(
                                        // name
                                        "argument" ,
                                        // isMandantory
                                        true ,
                                        // type
                                        Long.class )
                        } ,
                        // stmts
                        new FunctionCall<>(
                                "function0" ,
                                //functionReturnType
                                Void.class ,
                                new Argument<>(
                                        // functionArgumentName
                                        "argument" ,
                                        // expression
                                        new GetGlobalVar<>(
                                                // globalVarName
                                                "number" ,
                                                Integer.class ) ) ) );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // functions
                        Arrays.asList(
                                function0 ,
                                function1 ) ,
                        // params
                        null ,
                        // args
                        null ,
                        // globalVariables
                        new DeclareVariable[] {
                                new DeclareVariable<>(
                                        // varName
                                        "number" ,
                                        // type
                                        Integer.class ,
                                        // varValue
                                        0 ) ,
                        } ,
                        // stmts
                        new FunctionCall<>(
                                "function1" ,
                                //functionReturnType
                                Void.class ,
                                new Argument<>(
                                        // functionArgumentName
                                        "argument" ,
                                        // expression
                                        Value.longValue( 0L ) ) ) );

        System.out.println( coroIter );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                0 );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    @Test
    public void test_ValueFunctionArgument_Recursive()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        // extract get function argument expression
        final GetFunctionArgument<Long , Long> argument =
                new GetFunctionArgument<>(
                        //functionArgumentName
                        "argument" ,
                        Long.class );

        final Function<Void, Long , Void> function =
                new Function<Void, Long , Void>(
                        "function" ,
                        // params
                        new Parameter[] {
                                new Parameter(
                                        // name
                                        "argument" ,
                                        // isMandantory
                                        true ,
                                        // type
                                        Long.class )
                        } ,
                        // stmts
                        new If<>(
                                new Lesser<>(
                                        argument ,
                                        3L ) ,
                                new YieldReturn<>( argument ) ,
                                new FunctionCall<>(
                                        "function" ,
                                        //functionReturnType
                                        Void.class ,
                                        new Argument<>(
                                                // name
                                                "argument" ,
                                                // expression
                                                new LongAdd(
                                                        argument ,
                                                        Value.longValue( 1L ) ) ) ) ) ,
                        new FinallyReturn<>( argument ) );

        final CoroutineIterator<Long> coroIter =
                new CoroutineIterator<Long>(
                        // type
                        Long.class ,
                        // functions
                        Arrays.asList( function ) ,
                        // params
                        null ,
                        // args
                        null ,
                        // globalVariables
                        null ,
                        // stmts
                        new FunctionCall<>(
                                "function" ,
                                //functionReturnType
                                Void.class ,
                                new Argument<>(
                                        // name
                                        "argument" ,
                                        // value
                                        0L ) ) );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                0L );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                1L );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                2L );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                3L );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    @Test
    public void test_ValueFunctionArgument_LocalVariable_Recursive_Long()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        // extract get local variable expression
        final GetLocalVar<Long , Long> variable =
                new GetLocalVar<>(
                        // varName
                        "variable" ,
                        Long.class );

        final Function<Void, Long , Void> function =
                new Function<Void, Long , Void>(
                        "function" ,
                        // params
                        new Parameter[] {
                                new Parameter(
                                        // name
                                        "argument" ,
                                        // isMandantory
                                        true ,
                                        // type
                                        Long.class )
                        } ,
                        // stmts
                        new DeclareVariable<>(
                                // varName
                                "variable" ,
                                // type
                                Long.class ,
                                // varValueExpression
                                new GetFunctionArgument<>(
                                        // functionArgumentName
                                        "argument" ,
                                        Long.class ) ) ,
                        new If<>(
                                new Lesser<>(
                                        variable ,
                                        3L ) ,
                                new YieldReturn<>( variable ) ,
                                new FunctionCall<>(
                                        "function" ,
                                        //functionReturnType
                                        Void.class ,
                                        new Argument<>(
                                                // name
                                                "argument" ,
                                                // expression
                                                new LongAdd(
                                                        variable ,
                                                        Value.longValue( 1L ) ) ) ) ) ,
                        new FinallyReturn<>( variable ) );

        final CoroutineIterator<Long> coroIter =
                new CoroutineIterator<Long>(
                        // type
                        Long.class ,
                        // functions
                        Arrays.asList( function ) ,
                        // params
                        null ,
                        // args
                        null ,
                        // globalVariables
                        null ,
                        // stmts
                        new FunctionCall<>(
                                "function" ,
                                //functionReturnType
                                Void.class ,
                                new Argument<>(
                                        // name
                                        "argument" ,
                                        // value
                                        0L ) ) );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                0L );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                1L );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                2L );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                3L );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    @Test
    public void test_ValueFunctionArgument_LocalVariable_Recursive_Int()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        // extract get local variable expression
        final GetLocalVar<Integer , Integer> variable =
                new GetLocalVar<>(
                        // localVarName
                        "variable" ,
                        Integer.class );

        // extract get function argument expression
        final GetFunctionArgument<Integer , Integer> functionArgument =
                new GetFunctionArgument<>(
                        // functionArgumentName
                        "functionArgument" ,
                        Integer.class );

        final Function<Void, Integer , Void> function =
                new Function<Void, Integer , Void>(
                        "function" ,
                        // params
                        new Parameter[] {
                                new Parameter(
                                        // name
                                        "functionArgument" ,
                                        // isMandantory
                                        true ,
                                        // type
                                        Integer.class )
                        } ,
                        // stmts
                        new DeclareVariable<>(
                                // varName
                                "variable" ,
                                // type
                                Integer.class ,
                                // initialVarValueExpression
                                functionArgument ) ,
                        new If<>(
                                new Lesser<>(
                                        variable ,
                                        3 ) ,
                                new YieldReturn<>( variable ) ,
                                new FunctionCall<>(
                                        "function" ,
                                        //functionReturnType
                                        Void.class ,
                                        new Argument<>(
                                                // name
                                                "functionArgument" ,
                                                // expression
                                                new IntAdd(
                                                        variable ,
                                                        Value.intValue( 1 ) ) ) ) ) ,
                        new FinallyReturn<>( variable ) );

        CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // functions
                        Arrays.asList( function ) ,
                        // params
                        null ,
                        // args
                        null ,
                        // globalVariables
                        null ,
                        // stmts
                        new FunctionCall<>(
                                "function" ,
                                //functionReturnType
                                Void.class ,
                                new Argument<>(
                                        // name
                                        "functionArgument" ,
                                        // value
                                        0 ) ) );

        System.out.println( coroIter );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                0 );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                1 );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                2 );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                3 );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    @Test
    public void test_FunctionReturn_0()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        final Function</*FUNCTION_RETURN*/Integer, /*COROUTINE_RETURN*/Integer , /*RESUME_ARGUMENT*/Void> function0 =
                new Function<>(
                        "function0" ,
                        // params
                        null ,
                        // bodyStmts
                        new FunctionReturn<>(
                                // functionReturnType
                                Integer.class ,
                                //expression
                                new Value<>( 0 ) ) );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // functions
                        Arrays.asList(
                                function0 ) ,
                        // params
                        null ,
                        // args
                        null ,
                        // globalVariables
                        null ,
                        // stmts
                        new YieldReturn<>(
                                new FunctionCall<>(
                                        "function0" ,
                                        //functionReturnType
                                        Integer.class ) ) );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                0 );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );

        //Assert.fail( "TODO" );
    }

    @Test
    public void test_FunctionReturn_1()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        final Function</*FUNCTION_RETURN*/Integer, /*COROUTINE_RETURN*/Integer , /*RESUME_ARGUMENT*/Void> function1 =
                new Function<>(
                        "function1" ,
                        // params
                        null ,
                        // bodyStmts
                        new FunctionReturn<>(
                                // functionReturnType
                                Integer.class ,
                                //expression
                                new Value<>( 1 ) ) );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // functions
                        Arrays.asList(
                                function1 ) ,
                        // params
                        null ,
                        // args
                        null ,
                        // globalVariables
                        null ,
                        // stmts
                        new YieldReturn<>( 0 ) ,
                        new YieldReturn<>(
                                new FunctionCall<>(
                                        "function1" ,
                                        //functionReturnType
                                        Integer.class ) ) );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                0 );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                1 );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );

        //Assert.fail( "TODO" );
    }

    @Test
    public void test_FunctionReturn_2()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        final Function</*FUNCTION_RETURN*/Integer, /*COROUTINE_RETURN*/Integer , /*RESUME_ARGUMENT*/Void> function =
                new Function<>(
                        "function" ,
                        // params
                        null ,
                        // bodyStmts
                        new YieldReturn<>( 0 ) ,
                        new FunctionReturn<>(
                                // functionReturnType
                                Integer.class ,
                                //expression
                                new Value<>( 1 ) ) );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // functions
                        Arrays.asList(
                                function ) ,
                        // params
                        null ,
                        // args
                        null ,
                        // globalVariables
                        null ,
                        // stmts
                        new YieldReturn<>(
                                new FunctionCall<>(
                                        "function" ,
                                        //functionReturnType
                                        Integer.class ) ) );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                0 );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                1 );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );

        //Assert.fail( "TODO" );
    }

}
