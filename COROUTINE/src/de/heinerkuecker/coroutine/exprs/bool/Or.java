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
 * Or condition.
 *
 * @author Heiner K&uuml;cker
 */
public class Or<COROUTINE_RETURN , RESUME_ARGUMENT>
extends CoroBooleanExpression<COROUTINE_RETURN , RESUME_ARGUMENT>
{
    private final SimpleExpression<Boolean , COROUTINE_RETURN , RESUME_ARGUMENT>[] conditionsToOr;

    /**
     * Constructor.
     */
    @SafeVarargs
    public Or(
            final SimpleExpression<Boolean , COROUTINE_RETURN , RESUME_ARGUMENT>... conditionsToOr )
    {
        this.conditionsToOr = conditionsToOr;
    }

    @Override
    public Boolean evaluate(
            final HasArgumentsAndVariables<? extends RESUME_ARGUMENT> parent )
    {
        for ( final SimpleExpression<Boolean , COROUTINE_RETURN , RESUME_ARGUMENT> condition : conditionsToOr )
        {
            if ( condition.evaluate( parent ) )
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<GetFunctionArgument<? , ? , ?>> getFunctionArgumentGetsNotInFunction()
    {
        final List<GetFunctionArgument<? , ? , ?>> result = new ArrayList<>();

        for ( final CoroExpression<Boolean , COROUTINE_RETURN , RESUME_ARGUMENT> condition : conditionsToOr )
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
        for ( final CoroExpression<? , COROUTINE_RETURN , RESUME_ARGUMENT> condition : conditionsToOr )
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
        for ( final CoroExpression<? , COROUTINE_RETURN , RESUME_ARGUMENT> condition : conditionsToOr )
        {
            condition.checkUseArguments( alreadyCheckedFunctionNames, parent );
        }
    }

    @Override
    public void setExprCoroutineReturnTypeAndResumeArgumentType(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ? , ?> parent ,
            //final Class<? /*extends COROUTINE_RETURN*/> coroutineReturnType ,
            final Class<? extends COROUTINE_RETURN> coroutineReturnType ,
            final Class<? extends RESUME_ARGUMENT> resumeArgumentType )
    {
        for ( final CoroExpression<Boolean , COROUTINE_RETURN , RESUME_ARGUMENT> condition : conditionsToOr )
        {
            condition.setExprCoroutineReturnTypeAndResumeArgumentType(
                    alreadyCheckedFunctionNames ,
                    parent ,
                    coroutineReturnType ,
                    resumeArgumentType );
        }
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        final StringBuilder buff = new StringBuilder();

        for ( final CoroExpression<? , ? , ?> condition : conditionsToOr )
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
