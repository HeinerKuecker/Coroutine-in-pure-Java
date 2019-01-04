package de.heinerkuecker.coroutine.step.simple;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine.expression.GetProcedureArgument;
import de.heinerkuecker.coroutine.step.CoroIterStep;
import de.heinerkuecker.coroutine.step.CoroIterStepResult;

public final class NegateGlobalVar<RESULT>
extends SimpleStep<RESULT/*, CoroutineIterator<RESULT>*/>
{
    /**
     * Name of variable to negate in
     * {@link CoroIteratorOrProcedure#globalVars()}
     */
    public final String varName;

    /**
     * Constructor.
     */
    public NegateGlobalVar(
            final String varName )
    {
        this.varName =
                Objects.requireNonNull(
                        varName );
    }

    public static <RESULT> NegateGlobalVar<RESULT> negate(
            final String varName )
    {
        return new NegateGlobalVar<>(
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
        final Object varValue = parent.globalVars().get( varName );

        if ( varValue instanceof Boolean )
        {
            parent.globalVars().put( varName , ! (boolean) varValue );
        }
        else
            // null or not boolean is handled as false
        {
            parent.globalVars().put( varName , true );
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
