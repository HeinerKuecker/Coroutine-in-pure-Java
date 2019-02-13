package de.heinerkuecker.coroutine.exprs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineOrFunctioncallOrComplexstmt;

/**
 * Like map from expression
 * to expression.
 *
 * @param <RETURN> return type
 * @param <ARGUMENT> argument type
 * @author Heiner K&uuml;cker
 */
public abstract class AbstrOneExprExpression<RETURN , ARGUMENT , COROUTINE_RETURN>
implements SimpleExpression<RETURN , COROUTINE_RETURN>
{
    /**
     * Expression to map.
     */
    public final SimpleExpression<? extends ARGUMENT , COROUTINE_RETURN> expr;

    /**
     * Constructor.
     */
    protected AbstrOneExprExpression(
            final SimpleExpression<? extends ARGUMENT , COROUTINE_RETURN> expr )
    {
        this.expr =
                Objects.requireNonNull(
                        expr );
    }

    @Override
    final public List<GetFunctionArgument<? , ?>> getFunctionArgumentGetsNotInFunction()
    {
        final List<GetFunctionArgument<? , ?>> result = new ArrayList<>();

        result.addAll(
                expr.getFunctionArgumentGetsNotInFunction() );

        return result;
    }

    @Override
    final public void checkUseVariables(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent,
            final Map<String, Class<?>> globalVariableTypes ,
            final Map<String, Class<?>> localVariableTypes)
    {
        this.expr.checkUseVariables(
                alreadyCheckedFunctionNames ,
                parent ,
                globalVariableTypes, localVariableTypes );
    }

    @Override
    final public void checkUseArguments(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent )
    {
        this.expr.checkUseArguments(
                alreadyCheckedFunctionNames ,
                parent );
    }

    @Override
    public void setExprCoroutineReturnType(
            HashSet<String> alreadyCheckedFunctionNames ,
            CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent ,
            Class<?> coroutineReturnType )
    {
        this.expr.setExprCoroutineReturnType(
                alreadyCheckedFunctionNames ,
                parent ,
                coroutineReturnType );
    }

}
