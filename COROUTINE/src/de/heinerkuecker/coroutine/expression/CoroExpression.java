package de.heinerkuecker.coroutine.expression;

import java.util.List;

import de.heinerkuecker.coroutine.HasArgumentsAndVariables;

public interface CoroExpression<T>
{
    T evaluate(
            final HasArgumentsAndVariables parent );

    abstract public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure();
}
