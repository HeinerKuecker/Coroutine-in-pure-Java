package de.heinerkuecker.coroutine.exprs.bool;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineOrFunctioncallOrComplexstmt;
import de.heinerkuecker.coroutine.HasArgumentsAndVariables;
import de.heinerkuecker.coroutine.exprs.GetFunctionArgument;
import de.heinerkuecker.coroutine.exprs.SimpleExpression;

/**
 * Xor condition.
 *
 * @author Heiner K&uuml;cker
 */
public class Xor<COROUTINE_RETURN>
//implements ConditionOrBooleanExpression/*Condition/*<CoroutineIterator<?>>*/
extends CoroBooleanExpression<COROUTINE_RETURN>
{
    private final SimpleExpression<Boolean , COROUTINE_RETURN> lhs;
    private final SimpleExpression<Boolean , COROUTINE_RETURN> rhs;

    /**
     * Constructor.
     */
    public Xor(
            final SimpleExpression<Boolean , COROUTINE_RETURN> lhs ,
            final SimpleExpression<Boolean , COROUTINE_RETURN> rhs )
    {
        this.lhs = Objects.requireNonNull( lhs );
        this.rhs = Objects.requireNonNull( rhs );
    }

    /**
     * Xors the specified conditions.
     */
    @Override
    public Boolean evaluate(
            final HasArgumentsAndVariables<?> parent )
    {
        return lhs.evaluate( parent ) != rhs.evaluate( parent );
    }

    @Override
    public List<GetFunctionArgument<? , ?>> getFunctionArgumentGetsNotInFunction()
    {
        final List<GetFunctionArgument<? , ?>> result = new ArrayList<>();

        result.addAll(
                lhs.getFunctionArgumentGetsNotInFunction() );

        result.addAll(
                rhs.getFunctionArgumentGetsNotInFunction() );

        return result;
    }

    @Override
    public void checkUseVariables(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent ,
            final Map<String, Class<?>> globalVariableTypes ,
            final Map<String, Class<?>> localVariableTypes )
    {
        this.lhs.checkUseVariables(
                alreadyCheckedFunctionNames ,
                parent ,
                globalVariableTypes, localVariableTypes );

        this.rhs.checkUseVariables(
                alreadyCheckedFunctionNames ,
                parent ,
                globalVariableTypes, localVariableTypes );
    }

    @Override
    public void checkUseArguments(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent )
    {
        this.lhs.checkUseArguments( alreadyCheckedFunctionNames, parent );
        this.rhs.checkUseArguments( alreadyCheckedFunctionNames, parent );
    }

    @Override
    final public void setExprCoroutineReturnType(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent ,
            final Class<?> coroutineReturnType )
    {
        this.lhs.setExprCoroutineReturnType(
                alreadyCheckedFunctionNames ,
                parent ,
                coroutineReturnType );

        this.rhs.setExprCoroutineReturnType(
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
        return "( " + lhs + " xor " + rhs + " )";
    }

}
