package de.heinerkuecker.coroutine.expression;

import java.util.Collections;
import java.util.List;

import de.heinerkuecker.coroutine.CoroIteratorOrProcedure;
import de.heinerkuecker.util.ArrayDeepToString;

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
     * @see CoroExpression#evaluate(CoroIteratorOrProcedure)
     */
    @Override
    public T evaluate(
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
                //this.getClass().getSimpleName() +
                //"[" +
                //"value=" +
                //String.valueOf( this.value )
                ArrayDeepToString.deepToString( this.value );
                //"]";
    }

}
