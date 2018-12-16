package de.heinerkuecker.coroutine_iterator.step.simple;

import java.util.Objects;

import de.heinerkuecker.coroutine_iterator.CoroutineIterator;
import de.heinerkuecker.coroutine_iterator.step.CoroIterStep;
import de.heinerkuecker.coroutine_iterator.step.result.CoroIterStepResult;

public final class ResetVar<RESULT>
extends SimpleStep<RESULT, CoroutineIterator<RESULT>>
{
    /**
     * Name of variable to reset in
     * {@link CoroutineIterator#vars}.
     */
    public final String varName;

    /**
     * Constructor.
     */
    public ResetVar(
            final String varName )
    {
        this.varName =
                Objects.requireNonNull(
                        varName );
    }

    /**
     * Set variable.
     *
     * @see CoroIterStep#execute(java.lang.Object)
     */
    @Override
    public CoroIterStepResult<RESULT> execute(
            final CoroutineIterator<RESULT> parent )
    {
        parent.vars.remove( varName );
        return CoroIterStepResult.continueCoroutine();
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return varName + " = null" +
                ( this.creationStackTraceElement != null
                    ? " " + this.creationStackTraceElement
                    : "" );
    }

}
