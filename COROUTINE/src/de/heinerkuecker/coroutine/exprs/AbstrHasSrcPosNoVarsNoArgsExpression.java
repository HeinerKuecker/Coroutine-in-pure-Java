package de.heinerkuecker.coroutine.exprs;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep;
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
            HashSet<String> alreadyCheckedProcedureNames ,
            CoroutineOrProcedureOrComplexstep<?, ?> parent ,
            Map<String, Class<?>> globalVariableTypes ,
            Map<String, Class<?>> localVariableTypes )
    {
        // nothing to do
    }

    @Override
    public final void checkUseArguments(HashSet<String> alreadyCheckedProcedureNames,
            CoroutineOrProcedureOrComplexstep<?, ?> parent)
    {
        // nothing to do
    }

}
