package de.heinerkuecker.coroutine_iterator.step.simple;

import java.util.Objects;

import de.heinerkuecker.coroutine_iterator.CoroutineIterator;
import de.heinerkuecker.coroutine_iterator.step.CoroIterStep;
import de.heinerkuecker.coroutine_iterator.step.result.CoroIterStepResult;

public final class SetVar<RESULT>
extends SimpleStep<RESULT, CoroutineIterator<RESULT>>
{
    /**
     * Name of variable to set in
     * {@link CoroutineIterator#vars}.
     */
    public final String varName;

    public final Object varValue;

    /**
     * Constructor.
     */
    public SetVar(
            final String varName ,
            final Object varValue )
    {
        this.varName =
                Objects.requireNonNull(
                        varName );

        // can be null
        this.varValue = varValue;
    }

    //public static <RESULT> SetVar<RESULT> setVar(
    //        final String varName ,
    //        final Object varValue )
    //{
    //    return new SetVar<>(
    //            varName ,
    //            varValue );
    //}

    /**
     * Set variable.
     *
     * @see CoroIterStep#execute(java.lang.Object)
     */
    @Override
    public CoroIterStepResult<RESULT> execute(
            final CoroutineIterator<RESULT> parent )
    {
        parent.vars.put( varName , varValue );
        return CoroIterStepResult.continueCoroutine();
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return varName + " = " + varValue +
                ( this.creationStackTraceElement != null
                    ? " " + this.creationStackTraceElement
                    : "" );
    }

}
