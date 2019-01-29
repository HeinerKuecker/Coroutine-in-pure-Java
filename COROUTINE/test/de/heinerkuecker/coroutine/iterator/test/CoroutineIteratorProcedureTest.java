package de.heinerkuecker.coroutine.iterator.test;

import java.util.Arrays;

import org.junit.Test;

import de.heinerkuecker.coroutine.CoroutineDebugSwitches;
import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.Procedure;
import de.heinerkuecker.coroutine.arg.Argument;
import de.heinerkuecker.coroutine.arg.Arguments;
import de.heinerkuecker.coroutine.arg.Parameter;
import de.heinerkuecker.coroutine.exprs.GetGlobalVar;
import de.heinerkuecker.coroutine.exprs.GetLocalVar;
import de.heinerkuecker.coroutine.exprs.GetProcedureArgument;
import de.heinerkuecker.coroutine.exprs.Value;
import de.heinerkuecker.coroutine.exprs.bool.Lesser;
import de.heinerkuecker.coroutine.exprs.num.IntAdd;
import de.heinerkuecker.coroutine.exprs.num.LongAdd;
import de.heinerkuecker.coroutine.stmt.complex.If;
import de.heinerkuecker.coroutine.stmt.complex.ProcedureCall;
import de.heinerkuecker.coroutine.stmt.ret.FinallyReturn;
import de.heinerkuecker.coroutine.stmt.ret.FinallyReturnWithoutResult;
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
public class CoroutineIteratorProcedureTest
{
    @Test( expected = IllegalArgumentException.class )
    public void testConstructor_Empty()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        final Procedure<Integer , Void> empty_procedure =
                new Procedure<>(
                        "empty_procedure" ,
                        // params
                        null );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        Arrays.asList( empty_procedure ) ,
                        // params
                        null ,
                        // args
                        null ,
                        // globalVariables
                        null ,
                        // steps
                        new ProcedureCall<>(
                                "empty_procedure" ) );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    @Test
    public void test_0_0()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        final Procedure<Integer , Void> procedure =
                new Procedure<>(
                        "procedure" ,
                        // params
                        null ,
                        new FinallyReturnWithoutResult<>() );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        Arrays.asList( procedure ) ,
                        // params
                        null ,
                        // args
                        null ,
                        // globalVariables
                        null ,
                        // steps
                        new ProcedureCall<>(
                                "procedure" ) );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    @Test
    public void test_0_1()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        final Procedure<Integer , Void> procedure =
                new Procedure<>(
                        "procedure" ,
                        // params
                        null ,
                        new YieldReturn<>( 0 ) );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        Arrays.asList( procedure ) ,
                        // params
                        null ,
                        // args
                        null ,
                        // globalVariables
                        null ,
                        // steps
                        new ProcedureCall<>(
                                "procedure" ) );

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

        final Procedure<Integer , Void> procedure =
                new Procedure<>(
                        "procedure" ,
                        // params
                        null ,
                        // steps
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
                        Arrays.asList( procedure ) ,
                        // params
                        null ,
                        // args
                        null ,
                        // globalVariables
                        null ,
                        // steps
                        new ProcedureCall<>(
                                "procedure" ) );

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

        final Procedure<Integer , Void> procedure =
                new Procedure<>(
                        "procedure" ,
                        // params
                        null ,
                        // steps
                        new IncrementGlobalVar<>( "counter" ) ,
                        new YieldReturn<>(
                                new GetGlobalVar<>(
                                        "counter" ,
                                        Integer.class ) ) );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // procedures
                        Arrays.asList( procedure ) ,
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
                        // steps
                        new ProcedureCall<>(
                                "procedure" ) );

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

        final Procedure<Integer , Void> procedure =
                new Procedure<>(
                        "procedure" ,
                        // params
                        null ,
                        // steps
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
                        Arrays.asList( procedure ) ,
                        // params
                        null ,
                        // args
                        null ,
                        // globalVariables
                        null ,
                        // steps
                        new ProcedureCall<>(
                                "procedure" ) );

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

        final Procedure<Integer , Void> procedure =
                new Procedure<>(
                        "procedure" ,
                        // params
                        null ,
                        // steps
                        new IncrementGlobalVar<>( "counter" ) ,
                        new YieldReturn<>(
                                new GetGlobalVar<>(
                                        "counter" ,
                                        Integer.class ) ) );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        Arrays.asList( procedure ) ,
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
                        // steps
                        new ProcedureCall<>(
                                "procedure" ) ,
                        new ProcedureCall<>(
                                "procedure" ) );

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
    public void test_ValueProcedureArgument_0_1()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        final Procedure<Integer , Void> procedure =
                new Procedure<>(
                        "procedure" ,
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
                        // steps
                        new YieldReturn<>(
                                new GetProcedureArgument<>(
                                        "argument" ,
                                        Integer.class ) ) );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        Arrays.asList( procedure ) ,
                        // params
                        null ,
                        // args
                        null ,
                        // globalVariables
                        null ,
                        // steps
                        new ProcedureCall<>(
                                "procedure" ,
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
    public void test_ValueProcedureArgument_negative_wrong_initializationChecks()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        final Procedure<Integer , Void> procedure =
                new Procedure<>(
                        "procedure" ,
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
                        // steps
                        new YieldReturn<>(
                                new GetProcedureArgument<>(
                                        "argument" ,
                                        Integer.class ) ) );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        Arrays.asList( procedure ) ,
                        // params
                        null ,
                        // args
                        null ,
                        // globalVariables
                        null ,
                        // steps
                        new ProcedureCall<>(
                                "procedure" ,
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
    public void test_ValueProcedureArgument_negative_wrong()
    {
        CoroutineDebugSwitches.initializationChecks = false;

        final Procedure<Integer , Void> procedure =
                new Procedure<>(
                        "procedure" ,
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
                        // steps
                        new YieldReturn<>(
                                new GetProcedureArgument<>(
                                        "argument" ,
                                        Integer.class ) ) );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        Arrays.asList( procedure ) ,
                        // params
                        null ,
                        // args
                        null ,
                        // globalVariables
                        null ,
                        // steps
                        new ProcedureCall<>(
                                "procedure" ,
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

    @Test( expected = GetProcedureArgument.ProcedureArgumentNotDeclaredException.class )
    public void test_ValueProcedureArgument_negative_not_declared()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        final Procedure<Integer , Void> procedure =
                new Procedure<>(
                        "procedure" ,
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
                        // steps
                        new YieldReturn<>(
                                new GetProcedureArgument<>(
                                        "argument" ,
                                        Integer.class ) ) );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        Arrays.asList( procedure ) ,
                        // params
                        null ,
                        // args
                        null ,
                        // globalVariables
                        null ,
                        // steps
                        new ProcedureCall<>(
                                "procedure" ,
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
    public void test_ValueProcedureArgument_negative_missed()
    {
        CoroutineDebugSwitches.initializationChecks = false;

        final Procedure<Integer , Void> procedure =
                new Procedure<>(
                        "procedure" ,
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
                        // steps
                        new YieldReturn<>(
                                new GetProcedureArgument<>(
                                        "argument" ,
                                        Integer.class ) ) );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        Arrays.asList( procedure ) ,
                        // params
                        null ,
                        // args
                        null ,
                        // globalVariables
                        null ,
                        // steps
                        new ProcedureCall<>(
                                "procedure" ,
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
    public void test_LocalVarProcedureArgument_0_0()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        final Procedure<Integer , Void> procedure =
                new Procedure<>(
                        "procedure" ,
                        new Parameter[] {
                                new Parameter(
                                        // name
                                        "argument" ,
                                        // isMandantory
                                        true ,
                                        // type
                                        Integer.class )
                        } ,
                        // steps
                        new YieldReturn<>(
                                new GetProcedureArgument<>(
                                        "argument" ,
                                        Integer.class ) ) );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        Arrays.asList( procedure ) ,
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
                        // steps
                        new ProcedureCall<>(
                                "procedure" ,
                                new Argument<>(
                                        // procedureArgumentName
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
    public void test_GlobalVarProcedureArgument_0_0()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        final Procedure<Integer , Void> procedure0 =
                new Procedure<>(
                        "procedure0" ,
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
                        // steps
                        new YieldReturn<>(
                                new GetProcedureArgument<>(
                                        "argument" ,
                                        Integer.class ) ) );

        final Procedure<Integer , Void> procedure1 =
                new Procedure<>(
                        "procedure1" ,
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
                        // steps
                        new ProcedureCall<>(
                                "procedure0" ,
                                new Argument<>(
                                        // procedureArgumentName
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
                        Arrays.asList(
                                procedure0 ,
                                procedure1 ) ,
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
                        // steps
                        new ProcedureCall<>(
                                "procedure1" ,
                                new Argument<>(
                                        // procedureArgumentName
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
    public void test_ValueProcedureArgument_Recursive()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        // extract get procedure argument expression
        final GetProcedureArgument<Long> argument =
                new GetProcedureArgument<>(
                        //procedureArgumentName
                        "argument" ,
                        Long.class );

        final Procedure<Long , Void> procedure =
                new Procedure<Long , Void>(
                        "procedure" ,
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
                        // steps
                        new If<>(
                                new Lesser<>(
                                        argument ,
                                        3L ) ,
                                new YieldReturn<>( argument ) ,
                                new ProcedureCall<>(
                                        "procedure" ,
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
                        Arrays.asList( procedure ) ,
                        // params
                        null ,
                        // args
                        null ,
                        // globalVariables
                        null ,
                        // steps
                        new ProcedureCall<>(
                                "procedure" ,
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
    public void test_ValueProcedureArgument_LocalVariable_Recursive_Long()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        // extract get local variable expression
        final GetLocalVar<Long> variable =
                new GetLocalVar<>(
                        // varName
                        "variable" ,
                        Long.class );

        final Procedure<Long , Void> procedure =
                new Procedure<Long , Void>(
                        "procedure" ,
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
                        // steps
                        new DeclareVariable<>(
                                // varName
                                "variable" ,
                                // type
                                Long.class ,
                                // varValueExpression
                                new GetProcedureArgument<>(
                                        // procedureArgumentName
                                        "argument" ,
                                        Long.class ) ) ,
                        new If<>(
                                new Lesser<>(
                                        variable ,
                                        3L ) ,
                                new YieldReturn<>( variable ) ,
                                new ProcedureCall<>(
                                        "procedure" ,
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
                        Arrays.asList( procedure ) ,
                        // params
                        null ,
                        // args
                        null ,
                        // globalVariables
                        null ,
                        // steps
                        new ProcedureCall<>(
                                "procedure" ,
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
    public void test_ValueProcedureArgument_LocalVariable_Recursive_Int()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        // extract get local variable expression
        final GetLocalVar<Integer> variable =
                new GetLocalVar<>(
                        // localVarName
                        "variable" ,
                        Integer.class );

        // extract get procedure argument expression
        final GetProcedureArgument<Integer> procedureArgument =
                new GetProcedureArgument<>(
                        // procedureArgumentName
                        "procedureArgument" ,
                        Integer.class );

        final Procedure<Integer , Void> procedure =
                new Procedure<Integer , Void>(
                        "procedure" ,
                        // params
                        new Parameter[] {
                                new Parameter(
                                        // name
                                        "procedureArgument" ,
                                        // isMandantory
                                        true ,
                                        // type
                                        Integer.class )
                        } ,
                        // steps
                        new DeclareVariable<>(
                                // varName
                                "variable" ,
                                // type
                                Integer.class ,
                                // initialVarValueExpression
                                procedureArgument ) ,
                        new If<>(
                                new Lesser<>(
                                        variable ,
                                        3 ) ,
                                new YieldReturn<>( variable ) ,
                                new ProcedureCall<>(
                                        "procedure" ,
                                        new Argument<>(
                                                // name
                                                "procedureArgument" ,
                                                // expression
                                                new IntAdd(
                                                        variable ,
                                                        Value.intValue( 1 ) ) ) ) ) ,
                        new FinallyReturn<>( variable ) );

        CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // procedures
                        Arrays.asList( procedure ) ,
                        // params
                        null ,
                        // args
                        null ,
                        // globalVariables
                        null ,
                        // steps
                        new ProcedureCall<>(
                                "procedure" ,
                                new Argument<>(
                                        // name
                                        "procedureArgument" ,
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

}
