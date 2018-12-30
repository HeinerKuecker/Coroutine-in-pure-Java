package de.heinerkuecker.coroutine_iterator.expression;

import de.heinerkuecker.coroutine_iterator.CoroIteratorOrProcedure;

public class Value<T>
implements CoroExpression<T>
{
    public final T value;

    /**
     * Constructor.
     *
     * @param value
     */
    public Value(
            final T value )
    {
        this.value = value;
    }

    /**
     * @see CoroExpression#getValue(CoroIteratorOrProcedure)
     */
    @Override
    public T getValue(
            final CoroIteratorOrProcedure<?> parent )
    {
        return this.value;
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return this.getClass().getSimpleName() + "[value=" + this.value + "]";
    }

}
