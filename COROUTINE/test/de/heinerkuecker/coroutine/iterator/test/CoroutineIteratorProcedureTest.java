package de.heinerkuecker.coroutine.iterator.test;

import java.util.Arrays;

import org.junit.Test;

import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.Procedure;
import de.heinerkuecker.coroutine.condition.Lesser;
import de.heinerkuecker.coroutine.expression.Add;
import de.heinerkuecker.coroutine.expression.GetGlobalVar;
import de.heinerkuecker.coroutine.expression.GetLocalVar;
import de.heinerkuecker.coroutine.expression.GetProcedureArgument;
import de.heinerkuecker.coroutine.expression.Value;
import de.heinerkuecker.coroutine.proc.arg.ProcedureArgument;
import de.heinerkuecker.coroutine.step.complex.If;
import de.heinerkuecker.coroutine.step.complex.ProcedureCall;
import de.heinerkuecker.coroutine.step.retrn.FinallyReturn;
import de.heinerkuecker.coroutine.step.retrn.FinallyReturnWithoutResult;
import de.heinerkuecker.coroutine.step.retrn.YieldReturn;
import de.heinerkuecker.coroutine.step.simple.IncGlobalVar;
import de.heinerkuecker.coroutine.step.simple.IncLocalVar;
import de.heinerkuecker.coroutine.step.simple.NoOperation;
import de.heinerkuecker.coroutine.step.simple.SetLocalVar;

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
        CoroutineIterator.initializationChecks = true;

        final Procedure<Integer> empty_procedure =
                new Procedure<>(
                        "empty_procedure" );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        Arrays.asList( empty_procedure ) ,
                        //initialVariableValues
                        null ,
                        new ProcedureCall<Integer>(
                                "empty_procedure" ) );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    @Test
    public void test_0_0()
    {
        CoroutineIterator.initializationChecks = true;

        final Procedure<Integer> procedure =
                new Procedure<>(
                        "procedure" ,
                        new FinallyReturnWithoutResult<>() );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        Arrays.asList( procedure ) ,
                        //initialVariableValues
                        null ,
                        new ProcedureCall<Integer>(
                                "procedure" ) );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    @Test
    public void test_0_1()
    {
        CoroutineIterator.initializationChecks = true;

        final Procedure<Integer> procedure =
                new Procedure<>(
                        "procedure" ,
                        new YieldReturn<>(
                                new Value<>( 0 ) ) );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        Arrays.asList( procedure ) ,
                        //initialVariableValues
                        null ,
                        new ProcedureCall<Integer>(
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
        CoroutineIterator.initializationChecks = true;

        final Procedure<Integer> procedure =
                new Procedure<>(
                        "procedure" ,
                        new SetLocalVar<>(
                                //varName
                                "counter" ,
                                new Value<>(
                                        //varValue
                                        0 ) ) ,
                        new IncLocalVar<>( "counter" ) ,
                        new YieldReturn<>(
                                new GetLocalVar<>(
                                        "counter" ) ) );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        Arrays.asList( procedure ) ,
                        //initialVariableValues
                        null ,
                        new ProcedureCall<Integer>(
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
        CoroutineIterator.initializationChecks = true;

        final Procedure<Integer> procedure =
                new Procedure<>(
                        "procedure" ,
                        new IncGlobalVar<>( "counter" ) ,
                        new YieldReturn<>(
                                new GetGlobalVar<>(
                                        "counter" ) ) );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        Arrays.asList( procedure ) ,
                        //initialVariableValues
                        null ,
                        new SetLocalVar<>(
                                //varName
                                "counter" ,
                                new Value<>(
                                        //varValue
                                        0 ) ) ,
                        new ProcedureCall<Integer>(
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
        CoroutineIterator.initializationChecks = true;

        final Procedure<Integer> procedure =
                new Procedure<>(
                        "procedure" ,
                        new SetLocalVar<>(
                                //varName
                                "counter" ,
                                new Value<>(
                                        //varValue
                                        0 ) ) ,
                        new IncLocalVar<>( "counter" ) ,
                        new YieldReturn<>(
                                new GetLocalVar<>(
                                        "counter" ) ) ,
                        new NoOperation<>() );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        Arrays.asList( procedure ) ,
                        //initialVariableValues
                        null ,
                        new ProcedureCall<Integer>(
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
        CoroutineIterator.initializationChecks = true;

        final Procedure<Integer> procedure =
                new Procedure<>(
                        "procedure" ,
                        new IncGlobalVar<>( "counter" ) ,
                        new YieldReturn<>(
                                new GetGlobalVar<>(
                                        "counter" ) ) );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        Arrays.asList( procedure ) ,
                        //initialVariableValues
                        null ,
                        new SetLocalVar<>(
                                //varName
                                "counter" ,
                                new Value<>(
                                        //varValue
                                        0 ) ) ,
                        new ProcedureCall<Integer>(
                                "procedure" ) ,
                        new ProcedureCall<Integer>(
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
        CoroutineIterator.initializationChecks = true;

        final Procedure<Integer> procedure =
                new Procedure<>(
                        "procedure" ,
                        new YieldReturn<>(
                                new GetProcedureArgument<>(
                                        "argument" ) ) );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        Arrays.asList( procedure ) ,
                        //initialVariableValues
                        null ,
                        new ProcedureCall<Integer>(
                                "procedure" ,
                                new ProcedureArgument<>(
                                        // name
                                        "argument" ,
                                        // expression
                                        new Value<>(
                                                // value
                                                0 ) ) ) );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                0 );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    @Test
    public void test_LocalVarProcedureArgument_0_0()
    {
        CoroutineIterator.initializationChecks = true;

        final Procedure<Integer> procedure =
                new Procedure<>(
                        "procedure" ,
                        new YieldReturn<>(
                                new GetProcedureArgument<>(
                                        "argument" ) ) );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        Arrays.asList( procedure ) ,
                        //initialVariableValues
                        null ,
                        new SetLocalVar<>(
                                //varName
                                "number" ,
                                new Value<>(
                                        //varValue
                                        0 ) ) ,
                        new ProcedureCall<Integer>(
                                "procedure" ,
                                new ProcedureArgument<>(
                                        // procedureArgumentName
                                        "argument" ,
                                        // expression
                                        new GetLocalVar<>(
                                                // localVarName
                                                "number" ) ) ) );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                0 );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    @Test
    public void test_GlobalVarProcedureArgument_0_0()
    {
        CoroutineIterator.initializationChecks = true;

        final Procedure<Integer> procedure0 =
                new Procedure<>(
                        "procedure0" ,
                        new YieldReturn<>(
                                new GetProcedureArgument<>(
                                        "argument" ) ) );

        final Procedure<Integer> procedure1 =
                new Procedure<>(
                        "procedure1" ,
                        new ProcedureCall<Integer>(
                                "procedure0" ,
                                new ProcedureArgument<>(
                                        // procedureArgumentName
                                        "argument" ,
                                        // expression
                                        new GetGlobalVar<>(
                                                // globalVarName
                                                "number" ) ) ) );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        Arrays.asList(
                                procedure0 ,
                                procedure1 ) ,
                        //initialVariableValues
                        null ,
                        new SetLocalVar<>(
                                //varName
                                "number" ,
                                new Value<>(
                                        //varValue
                                        0 ) ) ,
                        new ProcedureCall<Integer>(
                                "procedure1" ) );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                0 );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    @Test
    public void test_ValueProcedureArgument_Recursive()
    {
        CoroutineIterator.initializationChecks = true;

        final Procedure<Integer> procedure =
                new Procedure<Integer>(
                        "procedure" ,
                        new If<Integer>(
                                new Lesser<>(
                                        new GetProcedureArgument<>(
                                                //procedureArgumentName
                                                "argument" ) ,
                                        new Value<>(
                                                3L ) ) ,
                        new YieldReturn<>(
                                new GetProcedureArgument<>(
                                        "argument" ) ) ,
                        new ProcedureCall<Integer>(
                                "procedure" ,
                                new ProcedureArgument<>(
                                        // name
                                        "argument" ,
                                        // expression
                                        new Add<>(
                                                new GetProcedureArgument<>(
                                                        "argument" ) ,
                                                new Value<>(
                                                        // value
                                                        1 ) ) ) ) ) ,
                        new FinallyReturn<>(
                                new GetProcedureArgument<>(
                                        "argument" ) ) );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        Arrays.asList( procedure ) ,
                        //initialVariableValues
                        null ,
                        new ProcedureCall<Integer>(
                                "procedure" ,
                                new ProcedureArgument<>(
                                        // name
                                        "argument" ,
                                        // expression
                                        new Value<>(
                                                // value
                                                0L ) ) ) );

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

}
