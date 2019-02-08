package de.heinerkuecker.coroutine.exprs;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import de.heinerkuecker.coroutine.CoroutineOrFunctioncallOrComplexstmt;
import de.heinerkuecker.coroutine.HasCreationStackTraceElement;

public abstract class AbstrHasSrcPosNoVarsNoArgsExpression<T>
extends HasCreationStackTraceElement
implements CoroExpression<T>
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
    public final List<GetFunctionArgument<?>> getFunctionArgumentGetsNotInFunction()
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

}
