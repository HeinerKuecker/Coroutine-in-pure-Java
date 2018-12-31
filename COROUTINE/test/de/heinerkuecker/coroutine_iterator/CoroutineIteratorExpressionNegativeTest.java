package de.heinerkuecker.coroutine_iterator;

import org.junit.Test;

import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.condition.IsNull;
import de.heinerkuecker.coroutine.expression.GetProcedureArgument;
import de.heinerkuecker.coroutine.expression.GetProcedureArgumentNotInProcedureException;
import de.heinerkuecker.coroutine.step.complex.If;
import de.heinerkuecker.coroutine.step.simple.NoOperation;

/**
 * JUnit4 test case for {@link CoroutineIterator}.
 *
 * @author Heiner K&uuml;cker
 */
public class CoroutineIteratorExpressionNegativeTest
{
    @Test( expected = GetProcedureArgumentNotInProcedureException.class )
    public void testGetProcedureArgumentNotInProcedure()
    {
        CoroutineIterator.initializationChecks = true;

        final CoroutineIterator<?> coroIter =
                new CoroutineIterator<Object>(
                        new If<>(
                                new IsNull(
                                        new GetProcedureArgument<>(
                                                //procedureArgumentName
                                                "wrong" ) ) ,
                                new NoOperation<>() ) );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

}
