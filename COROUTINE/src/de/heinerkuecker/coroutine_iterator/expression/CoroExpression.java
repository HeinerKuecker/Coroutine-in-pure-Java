package de.heinerkuecker.coroutine_iterator.expression;

import de.heinerkuecker.coroutine_iterator.CoroIteratorOrProcedure;

public interface CoroExpression<T>
{
    T getValue(
            final CoroIteratorOrProcedure<?> parent );
}
