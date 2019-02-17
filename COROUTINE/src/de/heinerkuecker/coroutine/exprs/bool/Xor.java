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
public class Xor<COROUTINE_RETURN , RESUME_ARGUMENT>
extends CoroBooleanExpression<COROUTINE_RETURN , RESUME_ARGUMENT>
{
    private final SimpleExpression<Boolean , COROUTINE_RETURN , RESUME_ARGUMENT> lhs;
    private final SimpleExpression<Boolean , COROUTINE_RETURN , RESUME_ARGUMENT> rhs;

    /**
     * Constructor.
     */
    public Xor(
            final SimpleExpression<Boolean , COROUTINE_RETURN , RESUME_ARGUMENT> lhs ,
            final SimpleExpression<Boolean , COROUTINE_RETURN , RESUME_ARGUMENT> rhs )
    {
        this.lhs = Objects.requireNonNull( lhs );
        this.rhs = Objects.requireNonNull( rhs );
    }

    /**
     * Xors the specified conditions.
     */
    @Override
    public Boolean evaluate(
            final HasArgumentsAndVariables<? extends RESUME_ARGUMENT> parent )
    {
        return lhs.evaluate( parent ) != rhs.evaluate( parent );
    }

    @Override
    public List<GetFunctionArgument<? , ? , ?>> getFunctionArgumentGetsNotInFunction()
    {
        final List<GetFunctionArgument<? , ? , ?>> result = new ArrayList<>();

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
    public void setExprCoroutineReturnTypeAndResumeArgumentType(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ? , ?> parent ,
            //final Class<? /*extends COROUTINE_RETURN*/> coroutineReturnType ,
            final Class<? extends COROUTINE_RETURN> coroutineReturnType ,
            final Class<? extends RESUME_ARGUMENT> resumeArgumentType )
    {
        this.lhs.setExprCoroutineReturnTypeAndResumeArgumentType(
                alreadyCheckedFunctionNames ,
                parent ,
                coroutineReturnType ,
                resumeArgumentType );

        this.rhs.setExprCoroutineReturnTypeAndResumeArgumentType(
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
        return "( " + lhs + " xor " + rhs + " )";
    }

}
