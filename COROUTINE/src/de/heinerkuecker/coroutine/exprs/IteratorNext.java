package de.heinerkuecker.coroutine.exprs;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstmt;
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
            final HasArgumentsAndVariables<?>/*CoroutineOrProcedureOrComplexstmt<?, ?>*/ parent )
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
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstmt<?, ?> parent ,
            final Map<String, Class<?>> globalVariableTypes ,
            final Map<String, Class<?>> localVariableTypes )
    {
        this.iteratorExpression.checkUseVariables(
                alreadyCheckedProcedureNames ,
                parent ,
                globalVariableTypes, localVariableTypes );
    }

    @Override
    public void checkUseArguments(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstmt<?, ?> parent )
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
