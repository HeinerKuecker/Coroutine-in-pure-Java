package de.heinerkuecker.coroutine.step.simple;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine.HasVariableName;
import de.heinerkuecker.coroutine.expression.GetGlobalVar;
import de.heinerkuecker.coroutine.expression.GetProcedureArgument;
import de.heinerkuecker.coroutine.step.CoroIterStep;
import de.heinerkuecker.coroutine.step.CoroIterStepResult;

public final class ResetGlobalVar<RESULT>
extends SimpleStep<RESULT/*, CoroutineIterator<RESULT>*/>
implements HasVariableName
{
    /**
     * Name of variable to reset in
     * {@link CoroIteratorOrProcedure#globalVars()}
     */
    public final String globalVarName;

    /**
     * Constructor.
     */
    public ResetGlobalVar(
            final String globalVarName )
    {
        this.globalVarName =
                Objects.requireNonNull(
                        globalVarName );
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
        //parent.globalVars().remove( varName );
        parent.globalVars().set( globalVarName , null );
        return CoroIterStepResult.continueCoroutine();
    }

    @Override
    public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    {
        return Collections.emptyList();
    }

    /**
     * @see CoroIterStep#setResultType(Class)
     */
    @Override
    public void setResultType(
            final Class<? extends RESULT> resultType )
    {
        // do nothing
    }

    @Override
    public void checkUseUndeclaredVariables(
            final CoroIteratorOrProcedure<?> parent ,
            final Map<String, Class<?>> globalVariableTypes ,
            final Map<String, Class<?>> localVariableTypes )
    {
        if ( ! globalVariableTypes.containsKey( this.globalVarName ) )
        {
            throw new GetGlobalVar.GlobalVariableNotDeclaredException( this );
        }
    }

    @Override
    public String getVariableName()
    {
        return this.globalVarName;
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return "global " + globalVarName + " = null" +
                ( this.creationStackTraceElement != null
                    ? " " + this.creationStackTraceElement
                    : "" );
    }

}
