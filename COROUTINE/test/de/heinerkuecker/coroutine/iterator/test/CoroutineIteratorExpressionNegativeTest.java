package de.heinerkuecker.coroutine.iterator.test;

import org.junit.Test;

import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.Variables;
import de.heinerkuecker.coroutine.condition.IsNull;
import de.heinerkuecker.coroutine.expression.GetProcedureArgument;
import de.heinerkuecker.coroutine.expression.Value;
import de.heinerkuecker.coroutine.expression.exc.UseGetProcedureArgumentOutsideOfProcedureException;
import de.heinerkuecker.coroutine.step.complex.If;
import de.heinerkuecker.coroutine.step.simple.DeclareLocalVar;
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
        CoroutineIterator.initializationChecks = true;

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
        CoroutineIterator.initializationChecks = false;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // steps
                        new DeclareLocalVar<>(
                                // varName ,
                                "int_number" ,
                                // type
                                Integer.class ) ,
                        new SetLocalVar<>(
                                // varName ,
                                "int_number" ,
                                // varValueExpression
                                new Value<>( "a" ) ) );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

}
