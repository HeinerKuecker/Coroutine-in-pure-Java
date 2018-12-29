package de.heinerkuecker.coroutine_iterator;

import org.junit.Test;

import de.heinerkuecker.coroutine_iterator.proc.arg.ValueProcedureArgument;
import de.heinerkuecker.coroutine_iterator.step.complex.ProcedureCall;
import de.heinerkuecker.coroutine_iterator.step.retrn.FinallyReturnWithoutResult;
import de.heinerkuecker.coroutine_iterator.step.retrn.YieldReturnGlobalVar;
import de.heinerkuecker.coroutine_iterator.step.retrn.YieldReturnLocalVar;
import de.heinerkuecker.coroutine_iterator.step.retrn.YieldReturnProcedureArgument;
import de.heinerkuecker.coroutine_iterator.step.retrn.YieldReturnValue;
import de.heinerkuecker.coroutine_iterator.step.simple.IncGlobalVar;
import de.heinerkuecker.coroutine_iterator.step.simple.IncLocalVar;
import de.heinerkuecker.coroutine_iterator.step.simple.SetLocalVar;

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
                        new ProcedureCall<Integer>(
                                empty_procedure ) );

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
                        new ProcedureCall<Integer>(
                                procedure ) );

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
                        new YieldReturnValue<>( 0 ) );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        new ProcedureCall<Integer>(
                                procedure ) );

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
                                //varValue
                                0 ) ,
                        new IncLocalVar<>( "counter" ) ,
                        new YieldReturnLocalVar<>( "counter" ) );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        new ProcedureCall<Integer>(
                                procedure ) );

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
                        new YieldReturnGlobalVar<>( "counter" ) );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        new SetLocalVar<>(
                                //varName
                                "counter" ,
                                //varValue
                                0 ) ,
                        new ProcedureCall<Integer>(
                                procedure ) );

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
                                //varValue
                                0 ) ,
                        new IncLocalVar<>( "counter" ) ,
                        new YieldReturnLocalVar<>( "counter" ) );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        new ProcedureCall<Integer>(
                                procedure ) );

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
                        new YieldReturnGlobalVar<>( "counter" ) );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        new SetLocalVar<>(
                                //varName
                                "counter" ,
                                //varValue
                                0 ) ,
                        new ProcedureCall<Integer>(
                                procedure ) ,
                        new ProcedureCall<Integer>(
                                procedure ) );

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
    public void test_Argument_0_1()
    {
        CoroutineIterator.initializationChecks = true;

        final Procedure<Integer> procedure =
                new Procedure<>(
                        "procedure" ,
                        new YieldReturnProcedureArgument<>( "argument" ) );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        new ProcedureCall<Integer>(
                                procedure ,
                                new ValueProcedureArgument(
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

}
