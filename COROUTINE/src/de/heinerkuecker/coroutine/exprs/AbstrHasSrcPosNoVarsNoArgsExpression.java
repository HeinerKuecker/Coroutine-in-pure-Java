package de.heinerkuecker.coroutine.exprs;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstmt;
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
    public final List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    {
        // nothing to do
        return Collections.emptyList();
    }

    @Override
    public final void checkUseVariables(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstmt<?, ?> parent ,
            final Map<String, Class<?>> globalVariableTypes ,
            final Map<String, Class<?>> localVariableTypes )
    {
        // nothing to do
    }

    @Override
    public final void checkUseArguments(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstmt<?, ?> parent )
    {
        // nothing to do
    }

}
