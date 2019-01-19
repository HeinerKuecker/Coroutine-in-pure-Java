package de.heinerkuecker.coroutine.iterator.test;

import org.junit.Test;

import de.heinerkuecker.coroutine.CoroutineDebugSwitches;
import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.Variables;
import de.heinerkuecker.coroutine.condition.IsNull;
import de.heinerkuecker.coroutine.expression.GetProcedureArgument;
import de.heinerkuecker.coroutine.expression.exc.UseGetProcedureArgumentOutsideOfProcedureException;
import de.heinerkuecker.coroutine.step.complex.If;
import de.heinerkuecker.coroutine.step.simple.DeclareVariable;
import de.heinerkuecker.coroutine.step.simple.NoOperation;
import de.heinerkuecker.coroutine.step.simple.SetLocalVar;

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

    @Test( expected = Variables.WrongVariableClassException.class )
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
