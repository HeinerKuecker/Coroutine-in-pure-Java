package de.heinerkuecker.coroutine.exprs.bool;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineOrFunctioncallOrComplexstmt;
import de.heinerkuecker.coroutine.exprs.GetFunctionArgument;
import de.heinerkuecker.coroutine.exprs.SimpleExpression;

abstract public class LhsRhsBoolExpression<OPERAND, COROUTINE_RETURN , RESUME_ARGUMENT>
extends CoroBooleanExpression<COROUTINE_RETURN , RESUME_ARGUMENT>
{
    public final SimpleExpression<? extends OPERAND , COROUTINE_RETURN , RESUME_ARGUMENT> lhs;
    public final SimpleExpression<? extends OPERAND , COROUTINE_RETURN , RESUME_ARGUMENT> rhs;

    /**
     * Constructor.
     *
     * @param lhs
     * @param rhs
     */
    protected LhsRhsBoolExpression(
            final SimpleExpression<? extends OPERAND, COROUTINE_RETURN , RESUME_ARGUMENT> lhs ,
            final SimpleExpression<? extends OPERAND, COROUTINE_RETURN , RESUME_ARGUMENT> rhs )
    {
        this.lhs =
                Objects.requireNonNull(
                        lhs );

        this.rhs =
                Objects.requireNonNull(
                        rhs );
    }


    @Override
    final public List<GetFunctionArgument<? , ? , ?>> getFunctionArgumentGetsNotInFunction()
    {
        final List<GetFunctionArgument<? , ? , ?>> result = new ArrayList<>();

        result.addAll(
                lhs.getFunctionArgumentGetsNotInFunction() );

        result.addAll(
                rhs.getFunctionArgumentGetsNotInFunction() );

        return result;
    }

    @Override
    final public void checkUseVariables(
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
    final public void checkUseArguments(
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

}
