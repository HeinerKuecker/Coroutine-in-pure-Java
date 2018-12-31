package de.heinerkuecker.coroutine.expression;

import java.util.Collections;
import java.util.List;

import de.heinerkuecker.coroutine.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine.step.CoroIterStep;

public class GetLocalVar<T>
implements CoroExpression<T>
{
    public final String localVarName;

    /**
     * Constructor.
     * @param localVarName
     */
    public GetLocalVar(
            final String localVarName )
    {
        this.localVarName = localVarName;
    }

    /**
     * @see CoroExpression#getValue(CoroIteratorOrProcedure)
     */
    @SuppressWarnings("unchecked")
    @Override
    public T getValue(
            final CoroIteratorOrProcedure<?> parent )
    {
        return (T) parent.localVars().get( localVarName );
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return this.getClass().getSimpleName() + "[localVarName=" + this.localVarName + "]";
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
