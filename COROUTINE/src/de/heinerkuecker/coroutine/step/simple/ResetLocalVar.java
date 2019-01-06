package de.heinerkuecker.coroutine.step.simple;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine.expression.GetProcedureArgument;
import de.heinerkuecker.coroutine.step.CoroIterStep;
import de.heinerkuecker.coroutine.step.CoroIterStepResult;

public final class ResetLocalVar<RESULT>
extends SimpleStep<RESULT/*, CoroutineIterator<RESULT>*/>
{
    /**
     * Name of variable to reset in
     * {@link CoroIteratorOrProcedure#localVars()}
     */
    public final String varName;

    /**
     * Constructor.
     */
    public ResetLocalVar(
            final String varName )
    {
        this.varName =
                Objects.requireNonNull(
                        varName );
    }

    /**
     * Set variable.
     *
     * @see SimpleStep#execute
     */
    @Override
    public CoroIterStepResult<RESULT> execute(
            final CoroIteratorOrProcedure<RESULT> parent )
    {
        //parent.localVars().remove( varName );
        parent.localVars().set( varName , null );
        return CoroIterStepResult.continueCoroutine();
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return varName + " = null" +
                ( this.creationStackTraceElement != null
                    ? " " + this.creationStackTraceElement
                    : "" );
    }

    /**
     * @see CoroIterStep#getProcedureArgumentsNotInProcedure()
     */
    @Override
    public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    {
        return Collections.emptyList();
    }

}
