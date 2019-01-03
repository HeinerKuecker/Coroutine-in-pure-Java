package de.heinerkuecker.coroutine.step;

import java.util.List;

import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.HasCreationStackTraceElement;
import de.heinerkuecker.coroutine.expression.GetProcedureArgument;

/**
 * Abstract super class for one step
 * in {@link CoroutineIterator}.
 *
 * @param <RESULT> result type of method {@link CoroutineIterator#next()}
 * @param <PARENT> type the {@link CoroutineIterator} instance
 * @author Heiner K&uuml;cker
 */
abstract public class CoroIterStep<RESULT/*, PARENT*/>
extends HasCreationStackTraceElement
{
    ///**
    // * Execute one step.
    // *
    // * @param parent
    // * @return object to return a value and to control the flow
    // */
    //CoroIterStepResult<RESULT> execute(
    //        final PARENT parent );

    /**
     * Constructor with safe creation line number optional.
     */
    protected CoroIterStep(
            final int creationStackOffset )
    {
        super( creationStackOffset + 1 );
    }

    abstract public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure();
}