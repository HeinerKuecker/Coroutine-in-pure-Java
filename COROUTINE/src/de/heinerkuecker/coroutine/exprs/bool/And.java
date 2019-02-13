package de.heinerkuecker.coroutine.exprs.bool;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import de.heinerkuecker.coroutine.CoroutineOrFunctioncallOrComplexstmt;
import de.heinerkuecker.coroutine.HasArgumentsAndVariables;
import de.heinerkuecker.coroutine.exprs.CoroExpression;
import de.heinerkuecker.coroutine.exprs.GetFunctionArgument;
import de.heinerkuecker.coroutine.exprs.SimpleExpression;

/**
 * And condition.
 *
 * @author Heiner K&uuml;cker
 */
public class And<COROUTINE_RETURN>
extends CoroBooleanExpression<COROUTINE_RETURN>
{
    private final SimpleExpression<Boolean , COROUTINE_RETURN>[] conditionsToAnd;

    /**
     * Constructor.
     */
    @SafeVarargs
    public And(
            final SimpleExpression<Boolean , COROUTINE_RETURN>... conditionsToAnd )
    {
        this.conditionsToAnd = conditionsToAnd;
    }

    @Override
    public Boolean evaluate(
            final HasArgumentsAndVariables<?> parent )
    {
        for ( final SimpleExpression<Boolean , COROUTINE_RETURN> condition : conditionsToAnd )
        {
            if ( ! condition.evaluate( parent ) )
            {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<GetFunctionArgument<? , ?>> getFunctionArgumentGetsNotInFunction()
    {
        final List<GetFunctionArgument<? , ?>> result = new ArrayList<>();

        //for ( final ConditionOrBooleanExpression/*Condition<CoroutineIterator<?>>*/ condition : conditionsToAnd )
        for ( final CoroExpression<? , COROUTINE_RETURN> condition : conditionsToAnd )
        {
            result.addAll(
                    condition.getFunctionArgumentGetsNotInFunction() );
        }

        return result;
    }

    @Override
    public void checkUseVariables(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent ,
            final Map<String, Class<?>> globalVariableTypes ,
            final Map<String, Class<?>> localVariableTypes )
    {
        //for ( final ConditionOrBooleanExpression/*Condition<CoroutineIterator<?>>*/ condition : conditionsToAnd )
        for ( final CoroExpression<? , COROUTINE_RETURN> condition : conditionsToAnd )
        {
            condition.checkUseVariables(
                    alreadyCheckedFunctionNames ,
                    parent ,
                    globalVariableTypes, localVariableTypes );
        }
    }

    @Override
    public void checkUseArguments(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent )
    {
        for ( final CoroExpression<? , COROUTINE_RETURN> condition : conditionsToAnd )
        {
            condition.checkUseArguments( alreadyCheckedFunctionNames, parent );
        }
    }

    @Override
    public void setExprCoroutineReturnType(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent ,
            final Class<?> coroutineReturnType )
    {
        for ( final CoroExpression<Boolean, COROUTINE_RETURN> arrayElementExpression : this.conditionsToAnd )
        {
            arrayElementExpression.setExprCoroutineReturnType(
                    alreadyCheckedFunctionNames ,
                    parent ,
                    coroutineReturnType );
        }
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        final StringBuilder buff = new StringBuilder();

        for ( final CoroExpression<? , COROUTINE_RETURN> condition : conditionsToAnd )
        {
            if ( buff.length() > 0 )
            {
                buff.append( " && " );
            }
            buff.append( condition );
        }

        return "( " + buff + " )";
    }

}
