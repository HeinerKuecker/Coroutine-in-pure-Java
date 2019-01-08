package de.heinerkuecker.coroutine.expression;

import java.util.List;

import de.heinerkuecker.coroutine.CoroIteratorOrProcedure;

public interface CoroExpression<T>
{
    T evaluate(
            final CoroIteratorOrProcedure<?> parent );

    abstract public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure();
}
