package de.heinerkuecker.coroutine.expression;

import java.util.Collections;
import java.util.List;

import de.heinerkuecker.coroutine.CoroIteratorOrProcedure;

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
     * @see CoroExpression#getProcedureArgumentGetsNotInProcedure()
     */
    @Override
    public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    {
        return Collections.emptyList();
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return
                this.getClass().getSimpleName() +
                "[" +
                //"value=" +
                this.value +
                "]";
    }

}
