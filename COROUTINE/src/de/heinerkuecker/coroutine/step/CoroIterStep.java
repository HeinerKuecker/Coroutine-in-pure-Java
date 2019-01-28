package de.heinerkuecker.coroutine.step;

import de.heinerkuecker.coroutine.CoroCheckable;
import de.heinerkuecker.coroutine.Coroutine;
import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.HasCreationStackTraceElement;

/**
 * Abstract super class for one step
 * in {@link CoroutineIterator}.
 *
 * @param <COROUTINE_RETURN> result type of method {@link Coroutine#resume} or {@link CoroutineIterator#next()}
 * @param <PARENT> type the {@link CoroutineIterator} instance
 * @author Heiner K&uuml;cker
 */
abstract public class CoroIterStep<COROUTINE_RETURN/*, PARENT*/>
extends HasCreationStackTraceElement
implements CoroCheckable
{
    ///**
    // * Execute one step.
    // *
    // * @param parent
    // * @return object to return a value and to control the flow
    // */
    //CoroIterStepResult<COROUTINE_RETURN> execute(
    //        final PARENT parent );

    /**
     * Constructor with safe creation line number optional.
     */
    protected CoroIterStep(
            final int creationStackOffset )
    {
        super( creationStackOffset + 1 );
    }

    //abstract public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure();

    /**
     * Set reifier for type param {@link #COROUTINE_RETURN} to solve unchecked casts.
     */
    abstract public void setResultType(
            final Class<? extends COROUTINE_RETURN> resultType );
}
