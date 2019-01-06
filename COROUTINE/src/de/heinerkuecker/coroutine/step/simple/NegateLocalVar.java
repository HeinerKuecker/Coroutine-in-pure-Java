package de.heinerkuecker.coroutine.step.simple;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine.expression.GetProcedureArgument;
import de.heinerkuecker.coroutine.step.CoroIterStep;
import de.heinerkuecker.coroutine.step.CoroIterStepResult;

public final class NegateLocalVar<RESULT>
extends SimpleStep<RESULT/*, CoroutineIterator<RESULT>*/>
{
    /**
     * Name of variable to negate in
     * {@link CoroIteratorOrProcedure#localVars()}
     */
    public final String varName;

    /**
     * Constructor.
     */
    public NegateLocalVar(
            final String varName )
    {
        this.varName =
                Objects.requireNonNull(
                        varName );
    }

    public static <RESULT> NegateLocalVar<RESULT> negate(
            final String varName )
    {
        return new NegateLocalVar<>(
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
        final Object varValue = parent.localVars().get( varName );

        if ( varValue instanceof Boolean )
        {
            parent.localVars().set(
                    varName ,
                    ! (boolean) varValue );
        }
        else
            // null or not boolean is handled as false
        {
            parent.localVars().set(
                    varName ,
                    true );
        }
        return CoroIterStepResult.continueCoroutine();
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return varName + " = ! " + varName +
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
