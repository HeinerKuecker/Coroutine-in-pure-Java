package de.heinerkuecker.coroutine;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import de.heinerkuecker.coroutine.exprs.GetProcedureArgument;

public interface CoroCheckable
{

    abstract public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure();

    abstract public void checkUseVariables(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstep<?, ?> parent ,
            final Map<String, Class<?>> globalVariableTypes ,
            final Map<String, Class<?>> localVariableTypes );

    abstract public void checkUseArguments(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstep<?, ?> parent );

}
