package de.heinerkuecker.coroutine.iterator.test;

import org.junit.Test;

import de.heinerkuecker.coroutine.CoroutineDebugSwitches;
import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.exprs.GetFunctionArgument;
import de.heinerkuecker.coroutine.exprs.bool.IsNull;
import de.heinerkuecker.coroutine.exprs.exc.UseGetFunctionArgumentOutsideOfFunctionException;
import de.heinerkuecker.coroutine.stmt.complex.BlockLocalVariables;
import de.heinerkuecker.coroutine.stmt.complex.If;
import de.heinerkuecker.coroutine.stmt.simple.DeclareVariable;
import de.heinerkuecker.coroutine.stmt.simple.NoOperation;
import de.heinerkuecker.coroutine.stmt.simple.SetLocalVar;

/**
 * JUnit4 test case for {@link CoroutineIterator}.
 *
 * @author Heiner K&uuml;cker
 */
public class CoroutineIteratorExpressionNegativeTest
{
    @Test( expected = UseGetFunctionArgumentOutsideOfFunctionException.class )
    public void testUseGetFunctionArgumentOutsideOfFunctionException()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        new CoroutineIterator<Object>(
                // type
                Object.class ,
                // stmts
                new If<>(
                        new IsNull(
                                new GetFunctionArgument<>(
                                        //functionArgumentName
                                        "wrong" ,
                                        Object.class ) ) ,
                        new NoOperation<>() ) );
    }

    @Test( expected = BlockLocalVariables.WrongVariableClassException.class )
    public void testWrongVariableClassException()
    {
        CoroutineDebugSwitches.initializationChecks = false;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // stmts
                        new DeclareVariable<>(
                                // varName ,
                                "int_number" ,
                                // type
                                Integer.class ) ,
                        new SetLocalVar<>(
                                // varName ,
                                "int_number" ,
                                // varValue
                                "a" ) );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

}
