package de.heinerkuecker.coroutine.expression;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import de.heinerkuecker.coroutine.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine.HasArgumentsAndVariables;

public class IteratorNext<T>
implements CoroExpression<T>
{
    /**
     * Expression to get {@link Iterator}.
     */
    public final CoroExpression<? extends Iterator<? extends T>> iteratorExpression;

    /**
     * Constructor.
     *
     * @param value
     */
    public IteratorNext(
            final CoroExpression<? extends Iterator<? extends T>> iteratorExpression )
    {
        this.iteratorExpression = iteratorExpression;
    }

    /**
     * @see CoroExpression#evaluate(CoroIteratorOrProcedure)
     */
    @Override
    public T evaluate(
            final HasArgumentsAndVariables/*CoroIteratorOrProcedure<?>*/ parent )
    {
        final Iterator<? extends T> iterator = iteratorExpression.evaluate( parent );
        // TODO null handling
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

    @Override
    public void checkUseUndeclaredVariables(
            final CoroIteratorOrProcedure<?> parent ,
            final Map<String, Class<?>> globalVariableTypes ,
            final Map<String, Class<?>> localVariableTypes )
    {
        this.iteratorExpression.checkUseUndeclaredVariables(
                parent ,
                globalVariableTypes ,
                localVariableTypes );
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
