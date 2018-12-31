package de.heinerkuecker.coroutine_iterator;

import org.junit.Test;

import de.heinerkuecker.coroutine_iterator.condition.IsNull;
import de.heinerkuecker.coroutine_iterator.expression.GetProcedureArgument;
import de.heinerkuecker.coroutine_iterator.expression.GetProcedureArgumentNotInProcedureException;
import de.heinerkuecker.coroutine_iterator.step.complex.If;
import de.heinerkuecker.coroutine_iterator.step.simple.NoOperation;

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
