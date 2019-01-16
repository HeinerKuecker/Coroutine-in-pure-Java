package de.heinerkuecker.coroutine.expression;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine.HasArgumentsAndVariables;

public class IteratorNext<T>
implements CoroExpression<T>
{
    public final Class<? extends T> elementType;

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
            final Class<? extends T> elementType ,
            final CoroExpression<? extends Iterator<? extends T>> iteratorExpression )
    {
        this.elementType =
                Objects.requireNonNull(
                        elementType );

        this.iteratorExpression =
                Objects.requireNonNull(
                iteratorExpression );
    }

    @Override
    public T evaluate(
            final HasArgumentsAndVariables/*CoroIteratorOrProcedure<?>*/ parent )
    {
        final Iterator<? extends T> iterator = iteratorExpression.evaluate( parent );

        // TODO null handling iterator

        return elementType.cast( iterator.next() );
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
    public void checkUseVariables(
            HashSet<String> alreadyCheckedProcedureNames ,
            final CoroIteratorOrProcedure<?> parent ,
            final Map<String, Class<?>> globalVariableTypes, final Map<String, Class<?>> localVariableTypes )
    {
        this.iteratorExpression.checkUseVariables(
                alreadyCheckedProcedureNames ,
                parent ,
                globalVariableTypes, localVariableTypes );
    }

    @Override
    public void checkUseArguments(
            HashSet<String> alreadyCheckedProcedureNames, final CoroIteratorOrProcedure<?> parent )
    {
        this.iteratorExpression.checkUseArguments( alreadyCheckedProcedureNames, parent );
    }

    @Override
    public Class<? extends T>[] type()
    {
        return new Class[] { elementType };
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
