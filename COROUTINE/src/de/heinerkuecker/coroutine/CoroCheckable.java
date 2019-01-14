package de.heinerkuecker.coroutine;

import java.util.List;
import java.util.Map;

import de.heinerkuecker.coroutine.expression.GetProcedureArgument;

public interface CoroCheckable
{

    abstract public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure();

    abstract public void checkUseUndeclaredVariables(
            //final boolean isCoroutineRoot ,
            final CoroIteratorOrProcedure<?> parent ,
            final Map<String, Class<?>> globalVariableTypes ,
            final Map<String, Class<?>> localVariableTypes );
}
