package de.heinerkuecker.coroutine.exprs.bool;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import de.heinerkuecker.coroutine.CoroutineOrFunctioncallOrComplexstmt;
import de.heinerkuecker.coroutine.HasArgumentsAndVariables;
import de.heinerkuecker.coroutine.exprs.CoroExpression;
import de.heinerkuecker.coroutine.exprs.GetFunctionArgument;

/**
 * {@link Iterator#hasNext} condition
 * to check has next element
 * of the specified {@link Iterator}
 * expression {@link CoroExpression}.
 *
 * @author Heiner K&uuml;cker
 */
public class IteratorHasNext<COROUTINE_RETURN>
//implements ConditionOrBooleanExpression/*Condition/*<CoroutineIterator<?>>*/
extends CoroBooleanExpression<COROUTINE_RETURN>
{
    /**
     * Expression to check.
     */
    public final CoroExpression<Iterator<?> , COROUTINE_RETURN> iteratorExpression;

    /**
     * Constructor.
     */
    public IteratorHasNext(
            final CoroExpression<Iterator<?> , COROUTINE_RETURN> iteratorExpression )
    {
        this.iteratorExpression = iteratorExpression;
    }

    //@Override
    //public boolean execute(
    //        final HasArgumentsAndVariables<?>/*CoroutineOrFunctioncallOrComplexstmt<?, ?>*/ parent )
    //{
    //    //final Object varValue = parent.localVars().get( varName );
    //    final Iterator<?> iterator = iteratorExpression.evaluate( parent );
    //
    //    // TODO null handling
    //    return iterator.hasNext();
    //}

    @Override
    public Boolean evaluate(
            final HasArgumentsAndVariables<?> parent )
    {
        final Iterator<?> iterator = iteratorExpression.evaluate( parent );

        // TODO null handling
        return iterator.hasNext();
    }

    @Override
    public List<GetFunctionArgument<? , ?>> getFunctionArgumentGetsNotInFunction()
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
    public void setExprCoroutineReturnType(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent, Class<?> coroutineReturnType )
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
        return iteratorExpression + ".hasNext()";
    }

}
