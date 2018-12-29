package de.heinerkuecker.coroutine_iterator.step.simple;

import java.util.Objects;

import de.heinerkuecker.coroutine_iterator.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine_iterator.step.result.CoroIterStepResult;

public final class SetLocalVar<RESULT>
extends SimpleStep<RESULT/*, CoroutineIterator<RESULT>*/>
{
    /**
     * Name of variable to set in
     * {@link CoroIteratorOrProcedure#localVars()}
     */
    public final String varName;

    public final Object varValue;

    /**
     * Constructor.
     */
    public SetLocalVar(
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
     * @see SimpleStep#execute
     */
    @Override
    public CoroIterStepResult<RESULT> execute(
            final CoroIteratorOrProcedure<RESULT> parent )
    {
        parent.localVars().put( varName , varValue );
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
