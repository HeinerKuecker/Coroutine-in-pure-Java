package de.heinerkuecker.coroutine.iterator.test;

import org.junit.Test;

import de.heinerkuecker.coroutine.CoroutineDebugSwitches;
import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.condition.IsNull;
import de.heinerkuecker.coroutine.exprs.GetProcedureArgument;
import de.heinerkuecker.coroutine.exprs.exc.UseGetProcedureArgumentOutsideOfProcedureException;
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
    @Test( expected = UseGetProcedureArgumentOutsideOfProcedureException.class )
    public void testUseGetProcedureArgumentOutsideOfProcedureException()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        new CoroutineIterator<Object>(
                // type
                Object.class ,
                // steps
                new If<>(
                        new IsNull(
                                new GetProcedureArgument<>(
                                        //procedureArgumentName
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
                        // steps
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
