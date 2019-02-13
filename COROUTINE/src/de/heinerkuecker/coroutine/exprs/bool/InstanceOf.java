package de.heinerkuecker.coroutine.exprs.bool;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineOrFunctioncallOrComplexstmt;
import de.heinerkuecker.coroutine.HasArgumentsAndVariables;
import de.heinerkuecker.coroutine.exprs.CoroExpression;
import de.heinerkuecker.coroutine.exprs.GetFunctionArgument;
import de.heinerkuecker.coroutine.exprs.SimpleExpression;
import de.heinerkuecker.coroutine.exprs.Value;

/**
 * Check instanceof TODO
 * result of the right
 * expression {@link CoroExpression}
 * to the result of the left
 * expression {@link CoroExpression}.
 *
 * @author Heiner K&uuml;cker
 */
public class InstanceOf<COROUTINE_RETURN>
//implements CoroBooleanExpression
extends CoroBooleanExpression<COROUTINE_RETURN>
{
    /**
     * Left hand side expression, value.
     */
    public final SimpleExpression<?, COROUTINE_RETURN> valueExpression;

    /**
     * Right hand side expression, type.
     */
    public final SimpleExpression<? extends Class<?>, COROUTINE_RETURN> typeExpression;

    /**
     * Constructor.
     *
     * @param valueExpression
     * @param typeExpression
     */
    public InstanceOf(
            final SimpleExpression<?, COROUTINE_RETURN> valueExpression ,
            final SimpleExpression<? extends Class<?>, COROUTINE_RETURN> typeExpression )
    {
        this.valueExpression = Objects.requireNonNull( valueExpression );
        this.typeExpression = Objects.requireNonNull( typeExpression );
    }

    /**
     * Constructor.
     *
     * @param valueExpression
     * @param type
     */
    public InstanceOf(
            final SimpleExpression<? , COROUTINE_RETURN> valueExpression ,
            final Class<?> type )
    {
        this.valueExpression =
                Objects.requireNonNull(
                        valueExpression );

        this.typeExpression =
                new Value<Class<?> , COROUTINE_RETURN>(
                        Objects.requireNonNull(
                                type  ) );
    }

    @Override
    public Boolean evaluate(
            final HasArgumentsAndVariables<?> parent )
    {
        final Object value = valueExpression.evaluate( parent );
        final Class<?> type = typeExpression.evaluate( parent );

        return type.isInstance( value );
    }

    @Override
    public List<GetFunctionArgument<? , ?>> getFunctionArgumentGetsNotInFunction()
    {
        final List<GetFunctionArgument<? , ?>> result = new ArrayList<>();

        result.addAll(
                valueExpression.getFunctionArgumentGetsNotInFunction() );

        result.addAll(
                typeExpression.getFunctionArgumentGetsNotInFunction() );

        return result;
    }

    @Override
    public void checkUseVariables(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent,
            final Map<String, Class<?>> globalVariableTypes ,
            final Map<String, Class<?>> localVariableTypes )
    {
        this.valueExpression.checkUseVariables(
                alreadyCheckedFunctionNames ,
                parent ,
                globalVariableTypes, localVariableTypes );

        this.typeExpression.checkUseVariables(
                alreadyCheckedFunctionNames ,
                parent ,
                globalVariableTypes, localVariableTypes );
    }

    @Override
    public void checkUseArguments(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent )
    {
        this.valueExpression.checkUseArguments(
                alreadyCheckedFunctionNames ,
                parent );

        this.typeExpression.checkUseArguments(
                alreadyCheckedFunctionNames ,
                parent );
    }

    @Override
    public void setExprCoroutineReturnType(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent, Class<?> coroutineReturnType )
    {
        this.valueExpression.setExprCoroutineReturnType(
                alreadyCheckedFunctionNames ,
                parent ,
                coroutineReturnType );

        this.typeExpression.setExprCoroutineReturnType(
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
        return "( " + valueExpression + " instanceof " + typeExpression + " )";
    }

}
