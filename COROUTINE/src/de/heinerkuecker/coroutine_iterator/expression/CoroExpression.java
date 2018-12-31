package de.heinerkuecker.coroutine_iterator.expression;

import java.util.List;

import de.heinerkuecker.coroutine_iterator.CoroIteratorOrProcedure;

public interface CoroExpression<T>
{
    T getValue(
            final CoroIteratorOrProcedure<?> parent );

    abstract public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure();
}
