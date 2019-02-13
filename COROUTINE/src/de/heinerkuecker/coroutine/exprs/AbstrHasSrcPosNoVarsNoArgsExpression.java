package de.heinerkuecker.coroutine.exprs;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import de.heinerkuecker.coroutine.CoroutineOrFunctioncallOrComplexstmt;
import de.heinerkuecker.coroutine.HasCreationStackTraceElement;

public abstract class AbstrHasSrcPosNoVarsNoArgsExpression<EXPRESSSION_RETURN , COROUTINE_RETURN>
extends HasCreationStackTraceElement
implements SimpleExpression<EXPRESSSION_RETURN , COROUTINE_RETURN>
{
    /**
     * Constructor.
     *
     * @param creationStackOffset
     */
    protected AbstrHasSrcPosNoVarsNoArgsExpression(
            final int creationStackOffset )
    {
        super( creationStackOffset );
    }

    @Override
    public final List<GetFunctionArgument<? , ?>> getFunctionArgumentGetsNotInFunction()
    {
        // nothing to do
        return Collections.emptyList();
    }

    @Override
    public final void checkUseVariables(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent ,
            final Map<String, Class<?>> globalVariableTypes ,
            final Map<String, Class<?>> localVariableTypes )
    {
        // nothing to do
    }

    @Override
    public final void checkUseArguments(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent )
    {
        // nothing to do
    }

    @Override
    public void setExprCoroutineReturnType(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent ,
            final Class<?> coroutineReturnType )
    {
        // do nothing
    }

}
