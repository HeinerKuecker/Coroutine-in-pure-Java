package de.heinerkuecker.coroutine.expression;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep;

public abstract class NoVariablesNoArgumentsExpression<T>
implements CoroExpression<T>
{

    /* (non-Javadoc)
     * @see de.heinerkuecker.coroutine.CoroCheckable#getProcedureArgumentGetsNotInProcedure()
     */
    @Override
    public final List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    {
        // nothing to do
        return Collections.emptyList();
    }

    /* (non-Javadoc)
     * @see de.heinerkuecker.coroutine.CoroCheckable#checkUseVariables(java.util.HashSet, de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep, java.util.Map, java.util.Map)
     */
    @Override
    public final void checkUseVariables(
            HashSet<String> alreadyCheckedProcedureNames ,
            CoroutineOrProcedureOrComplexstep<?> parent ,
            Map<String, Class<?>> globalVariableTypes ,
            Map<String, Class<?>> localVariableTypes )
    {
        // nothing to do
    }

    /* (non-Javadoc)
     * @see de.heinerkuecker.coroutine.CoroCheckable#checkUseArguments(java.util.HashSet, de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep)
     */
    @Override
    public final void checkUseArguments(HashSet<String> alreadyCheckedProcedureNames,
            CoroutineOrProcedureOrComplexstep<?> parent)
    {
        // nothing to do
    }

}
