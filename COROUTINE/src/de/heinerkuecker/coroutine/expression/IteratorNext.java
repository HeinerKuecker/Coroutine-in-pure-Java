package de.heinerkuecker.coroutine.expression;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import de.heinerkuecker.coroutine.CoroIteratorOrProcedure;

public class IteratorNext<T>
implements CoroExpression<T>
{
    /**
     * Expression to get {@link Iterator}.
     */
    public final CoroExpression<Iterator<T>> iteratorExpression;

    /**
     * Constructor.
     *
     * @param value
     */
    public IteratorNext(
            final CoroExpression<Iterator<T>> iteratorExpression )
    {
        this.iteratorExpression = iteratorExpression;
    }

    /**
     * @see CoroExpression#getValue(CoroIteratorOrProcedure)
     */
    @Override
    public T getValue(
            final CoroIteratorOrProcedure<?> parent )
    {
        final Iterator<T> iterator = iteratorExpression.getValue( parent );

        return iterator.next();
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
        return this.iteratorExpression + ".next()";
    }

}
