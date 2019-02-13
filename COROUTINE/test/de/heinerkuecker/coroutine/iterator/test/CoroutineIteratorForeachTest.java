package de.heinerkuecker.coroutine.iterator.test;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import de.heinerkuecker.coroutine.CoroutineDebugSwitches;
import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.Function;
import de.heinerkuecker.coroutine.arg.Argument;
import de.heinerkuecker.coroutine.arg.Parameter;
import de.heinerkuecker.coroutine.exprs.GetFunctionArgument;
import de.heinerkuecker.coroutine.exprs.GetLocalVar;
import de.heinerkuecker.coroutine.exprs.Value;
import de.heinerkuecker.coroutine.stmt.complex.ForEach;
import de.heinerkuecker.coroutine.stmt.complex.FunctionCall;
import de.heinerkuecker.coroutine.stmt.ret.FunctionReturn;
import de.heinerkuecker.coroutine.stmt.ret.YieldReturn;

/**
 * JUnit4 test case for {@link CoroutineIterator}.
 *
 * @author Heiner K&uuml;cker
 */
public class CoroutineIteratorForeachTest
{
    @Test
    public void test_For_0()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // stmts
                        new ForEach<Void, Integer, Void , Integer>(
                                // variableName
                                "for_variable" ,
                                // elementType
                                Integer.class ,
                                // iterableExpression
                                new Value<List<Integer> , Integer>(
                                        (Class<? extends List<Integer>>) List.class ,
                                        Arrays.asList( 1 , 2 , 3 ) ) ,
                                // stmts
                                new YieldReturn<>(
                                        new GetLocalVar<>(
                                                "for_variable" ,
                                                Integer.class ) ) ) );

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
    public void test_For_1()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        final Function</*FUNCTION_RETURN*/Integer , /*COROUTINE_RETURN*/Integer , /*RESUME_ARGUMENT*/ Void> getForVariable =
                new Function<>(
                        //name
                        "getForVariable" ,
                        //params
                        new Parameter[] {
                            new Parameter(
                                    //name
                                    "for_variable_param" ,
                                    //isMandantory
                                    true ,
                                    //type
                                    Integer.class )
                        },
                        //bodyStmts
                        new FunctionReturn</*FUNCTION_RETURN*/Integer , /*COROUTINE_RETURN*/Integer , /*RESUME_ARGUMENT*/ Void>(
                                // functionReturnType
                                Integer.class ,
                                new GetFunctionArgument</*FUNCTION_ARGUMENT*/ Integer , /*COROUTINE_RETURN*/ Integer>(
                                        // functionArgumentName
                                        "for_variable_param" ,
                                        // type
                                        Integer.class ) ) );

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // functions
                        Arrays.asList(
                                getForVariable ) ,
                        // params
                        null ,
                        // args
                        null ,
                        // globalVariableDeclarations
                        null ,
                        // stmts
                        new ForEach<Void, Integer, Void , Integer>(
                                // variableName
                                "for_variable" ,
                                // elementType
                                Integer.class ,
                                // iterableExpression
                                new Value<List<Integer> , Integer>(
                                        (Class<? extends List<Integer>>) List.class ,
                                        Arrays.asList( 1 , 2 , 3 ) ) ,
                                // stmts
                                new YieldReturn<>(
                                        new FunctionCall<>(
                                                //functionName
                                                "getForVariable" ,
                                                //functionReturnType
                                                Integer.class ,
                                                // args
                                                new Argument<>(
                                                        //name
                                                        "for_variable_param" ,
                                                        //expression
                                                        new GetLocalVar<>(
                                                                "for_variable" ,
                                                                Integer.class ) ) ) ) ) );

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

    // TODO more tests
}
