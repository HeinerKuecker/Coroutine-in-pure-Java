package de.heinerkuecker.coroutine.exprs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineOrFunctioncallOrComplexstmt;
import de.heinerkuecker.coroutine.stmt.CoroStmt;

public abstract class AbstrLhsRhsExpression<EXPRESSSION_RETURN , COROUTINE_RETURN>
implements SimpleExpression<EXPRESSSION_RETURN , COROUTINE_RETURN>
{
    /**
     * Left hand side expression.
     */
    public final SimpleExpression<? extends EXPRESSSION_RETURN , COROUTINE_RETURN> lhs;

    /**
     * Right hand side expression to add.
     */
    public final SimpleExpression<? extends EXPRESSSION_RETURN , COROUTINE_RETURN> rhs;

    /**
     * Constructor.
     *
     * @param lhs
     * @param rhs
     */
    protected AbstrLhsRhsExpression(
            final SimpleExpression<? extends EXPRESSSION_RETURN , COROUTINE_RETURN> lhs ,
            final SimpleExpression<? extends EXPRESSSION_RETURN , COROUTINE_RETURN> rhs )
    {
        this.lhs = Objects.requireNonNull( lhs );
        this.rhs = Objects.requireNonNull( rhs );
    }

    /**
     * @see CoroStmt#getFunctionArgumentGetsNotInFunction()
     */
    @Override
    final public List<GetFunctionArgument<? , ?>> getFunctionArgumentGetsNotInFunction()
    {
        final List<GetFunctionArgument<? , ?>> result = new ArrayList<>();

        result.addAll(
                lhs.getFunctionArgumentGetsNotInFunction() );

        result.addAll(
                rhs.getFunctionArgumentGetsNotInFunction() );

        return result;
    }

    @Override
    final public void checkUseVariables(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent,
            final Map<String, Class<?>> globalVariableTypes ,
            final Map<String, Class<?>> localVariableTypes)
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
    public void setExprCoroutineReturnType(
            HashSet<String> alreadyCheckedFunctionNames ,
            CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent ,
            Class<?> coroutineReturnType )
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

}
