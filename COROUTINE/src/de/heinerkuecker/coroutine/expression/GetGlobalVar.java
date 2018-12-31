package de.heinerkuecker.coroutine.expression;

import java.util.Collections;
import java.util.List;

import de.heinerkuecker.coroutine.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine.step.CoroIterStep;

public class GetGlobalVar<T>
implements CoroExpression<T>
{
    public final String globalVarName;

    /**
     * Constructor.
     * @param globalVarName
     */
    public GetGlobalVar(
            final String globalVarName )
    {
        this.globalVarName = globalVarName;
    }

    /**
     * @see CoroExpression#getValue(CoroIteratorOrProcedure)
     */
    @SuppressWarnings("unchecked")
    @Override
    public T getValue(
            final CoroIteratorOrProcedure<?> parent )
    {
        return (T) parent.globalVars().get( globalVarName );
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return this.getClass().getSimpleName() + "[globalVarName=" + this.globalVarName + "]";
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
