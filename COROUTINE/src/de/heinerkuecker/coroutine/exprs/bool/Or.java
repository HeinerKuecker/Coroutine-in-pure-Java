package de.heinerkuecker.coroutine.exprs.bool;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import de.heinerkuecker.coroutine.CoroutineOrFunctioncallOrComplexstmt;
import de.heinerkuecker.coroutine.HasArgumentsAndVariables;
import de.heinerkuecker.coroutine.exprs.CoroExpression;
import de.heinerkuecker.coroutine.exprs.GetFunctionArgument;

/**
 * Or condition.
 *
 * @author Heiner K&uuml;cker
 */
public class Or<COROUTINE_RETURN>
//implements Condition/*<CoroutineIterator<?>>*/
extends CoroBooleanExpression<COROUTINE_RETURN>
{
    //private final ConditionOrBooleanExpression/*Condition<CoroutineIterator<?>>*/[] conditionsToOr;
    private final CoroExpression<Boolean , COROUTINE_RETURN>[] conditionsToOr;

    /**
     * Constructor.
     */
    @SafeVarargs
    public Or(
            final CoroExpression<Boolean , COROUTINE_RETURN>... conditionsToOr )
    {
        this.conditionsToOr = conditionsToOr;
    }

    @Override
    public Boolean evaluate(
            final HasArgumentsAndVariables<?> parent )
    {
        for ( final CoroExpression<Boolean , COROUTINE_RETURN> condition : conditionsToOr )
        {
            if ( condition.evaluate( parent ) )
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<GetFunctionArgument<? , ?>> getFunctionArgumentGetsNotInFunction()
    {
        final List<GetFunctionArgument<? , ?>> result = new ArrayList<>();

        for ( final CoroExpression<Boolean , COROUTINE_RETURN> condition : conditionsToOr )
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
        for ( final CoroExpression<? , COROUTINE_RETURN> condition : conditionsToOr )
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
        //for ( final ConditionOrBooleanExpression/*Condition<CoroutineIterator<?>>*/ condition : conditionsToOr )
        for ( final CoroExpression<? , COROUTINE_RETURN> condition : conditionsToOr )
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
        for ( final CoroExpression<Boolean , COROUTINE_RETURN> condition : conditionsToOr )
        {
            condition.setExprCoroutineReturnType(
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

        for ( final CoroExpression<? , ?> condition : conditionsToOr )
        {
            if ( buff.length() > 0 )
            {
                buff.append( " || " );
            }
            buff.append( condition );
        }

        return "( " + buff + " )";
    }

}
