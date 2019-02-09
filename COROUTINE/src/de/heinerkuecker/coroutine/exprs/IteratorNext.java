package de.heinerkuecker.coroutine.exprs;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineOrFunctioncallOrComplexstmt;
import de.heinerkuecker.coroutine.HasArgumentsAndVariables;

public class IteratorNext<T , COROUTINE_RETURN>
implements CoroExpression<T , COROUTINE_RETURN>
{
    public final Class<? extends T> elementType;

    /**
     * Expression to get {@link Iterator}.
     */
    public final CoroExpression<? extends Iterator<? extends T> , COROUTINE_RETURN> iteratorExpression;

    /**
     * Constructor.
     *
     * @param value
     */
    public IteratorNext(
            final Class<? extends T> elementType ,
            final CoroExpression<? extends Iterator<? extends T> , COROUTINE_RETURN> iteratorExpression )
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
            final HasArgumentsAndVariables<?>/*CoroutineOrFunctioncallOrComplexstmt<?, ?>*/ parent )
    {
        final Iterator<? extends T> iterator = iteratorExpression.evaluate( parent );

        // TODO null handling iterator

        return elementType.cast( iterator.next() );
    }

    /**
     * @see CoroExpression#getFunctionArgumentGetsNotInFunction()
     */
    @Override
    public List<GetFunctionArgument<? , ?>> getFunctionArgumentGetsNotInFunction()
    {
        return Collections.emptyList();
    }

    @Override
    public void checkUseVariables(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent ,
            final Map<String, Class<?>> globalVariableTypes ,
            final Map<String, Class<?>> localVariableTypes )
    {
        this.iteratorExpression.checkUseVariables(
                alreadyCheckedFunctionNames ,
                parent ,
                globalVariableTypes, localVariableTypes );
    }

    @Override
    public void checkUseArguments(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent )
    {
        this.iteratorExpression.checkUseArguments( alreadyCheckedFunctionNames, parent );
    }

    @Override
    public Class<? extends T>[] type()
    {
        return new Class[] { elementType };
    }

    @Override
    public void setExprCoroutineReturnType(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent ,
            final Class<?> coroutineReturnType )
    {
        this.iteratorExpression.setExprCoroutineReturnType(
                alreadyCheckedFunctionNames ,
                parent ,
                coroutineReturnType );
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
