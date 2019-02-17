package de.heinerkuecker.coroutine.exprs.bool;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import de.heinerkuecker.coroutine.CoroutineOrFunctioncallOrComplexstmt;
import de.heinerkuecker.coroutine.HasArgumentsAndVariables;
import de.heinerkuecker.coroutine.exprs.CoroExpression;
import de.heinerkuecker.coroutine.exprs.GetFunctionArgument;
import de.heinerkuecker.coroutine.exprs.SimpleExpression;

/**
 * {@link Iterator#hasNext} condition
 * to check has next element
 * of the specified {@link Iterator}
 * expression {@link CoroExpression}.
 *
 * @author Heiner K&uuml;cker
 */
public class IteratorHasNext<COROUTINE_RETURN , RESUME_ARGUMENT>
//implements ConditionOrBooleanExpression/*Condition/*<CoroutineIterator<?>>*/
extends CoroBooleanExpression<COROUTINE_RETURN , RESUME_ARGUMENT>
{
    /**
     * Expression to check.
     */
    public final SimpleExpression<Iterator<?> , COROUTINE_RETURN , RESUME_ARGUMENT> iteratorExpression;

    /**
     * Constructor.
     */
    public IteratorHasNext(
            final SimpleExpression<Iterator<?> , COROUTINE_RETURN , RESUME_ARGUMENT> iteratorExpression )
    {
        this.iteratorExpression = iteratorExpression;
    }

    @Override
    public Boolean evaluate(
            final HasArgumentsAndVariables<? extends RESUME_ARGUMENT> parent )
    {
        final Iterator<?> iterator = iteratorExpression.evaluate( parent );

        // TODO null handling
        return iterator.hasNext();
    }

    @Override
    public List<GetFunctionArgument<? , ? , ?>> getFunctionArgumentGetsNotInFunction()
    {
        return this.iteratorExpression.getFunctionArgumentGetsNotInFunction();
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
        this.iteratorExpression.checkUseArguments(
                alreadyCheckedFunctionNames ,
                parent );
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
        return iteratorExpression + ".hasNext()";
    }

}
