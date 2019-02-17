package de.heinerkuecker.coroutine.exprs;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineOrFunctioncallOrComplexstmt;
import de.heinerkuecker.coroutine.HasArgumentsAndVariables;

public class IteratorNext<ITERATOR_ELEMENT , COROUTINE_RETURN , RESUME_ARGUMENT>
implements SimpleExpression<ITERATOR_ELEMENT , COROUTINE_RETURN , RESUME_ARGUMENT>
{
    public final Class<? extends ITERATOR_ELEMENT> elementType;

    /**
     * Expression to get {@link Iterator}.
     */
    public final SimpleExpression<? extends Iterator<? extends ITERATOR_ELEMENT> , COROUTINE_RETURN , RESUME_ARGUMENT> iteratorExpression;

    /**
     * Constructor.
     *
     * @param value
     */
    public IteratorNext(
            final Class<? extends ITERATOR_ELEMENT> elementType ,
            final SimpleExpression<? extends Iterator<? extends ITERATOR_ELEMENT> , COROUTINE_RETURN , RESUME_ARGUMENT> iteratorExpression )
    {
        this.elementType =
                Objects.requireNonNull(
                        elementType );

        this.iteratorExpression =
                Objects.requireNonNull(
                        iteratorExpression );
    }

    @Override
    public ITERATOR_ELEMENT evaluate(
            final HasArgumentsAndVariables<? extends RESUME_ARGUMENT>/*CoroutineOrFunctioncallOrComplexstmt<?, ?>*/ parent )
    {
        final Iterator<? extends ITERATOR_ELEMENT> iterator = iteratorExpression.evaluate( parent );

        // TODO null handling iterator

        return elementType.cast( iterator.next() );
    }

    /**
     * @see CoroExpression#getFunctionArgumentGetsNotInFunction()
     */
    @Override
    public List<GetFunctionArgument<? , ? , ?>> getFunctionArgumentGetsNotInFunction()
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
    public Class<? extends ITERATOR_ELEMENT>[] type()
    {
        return new Class[] { elementType };
    }

    @Override
    public void setExprCoroutineReturnTypeAndResumeArgumentType(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ? , ?> parent ,
            //final Class<? /*extends COROUTINE_RETURN*/> coroutineReturnType ,
            final Class<? extends COROUTINE_RETURN> coroutineReturnType ,
            final Class<? extends RESUME_ARGUMENT> resumeArgumentType )
    {
        this.iteratorExpression.setExprCoroutineReturnTypeAndResumeArgumentType(
                alreadyCheckedFunctionNames ,
                parent ,
                coroutineReturnType ,
                resumeArgumentType );
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
