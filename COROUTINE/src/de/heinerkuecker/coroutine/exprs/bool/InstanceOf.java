package de.heinerkuecker.coroutine.exprs.bool;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineDebugSwitches;
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
public class InstanceOf<INSTANCE , COROUTINE_RETURN , RESUME_ARGUMENT>
//implements CoroBooleanExpression
extends CoroBooleanExpression<COROUTINE_RETURN , RESUME_ARGUMENT>
{
    /**
     * Left hand side expression, value.
     */
    public final SimpleExpression<? extends INSTANCE , COROUTINE_RETURN , RESUME_ARGUMENT> valueExpression;

    /**
     * Right hand side expression, type.
     */
    public final SimpleExpression<? extends Class<? extends INSTANCE>, COROUTINE_RETURN , RESUME_ARGUMENT> typeExpression;

    /**
     * Constructor.
     *
     * @param valueExpression
     * @param typeExpression
     */
    public InstanceOf(
            final SimpleExpression<? extends INSTANCE , COROUTINE_RETURN , RESUME_ARGUMENT> valueExpression ,
            final SimpleExpression<? extends Class<? extends INSTANCE>, COROUTINE_RETURN , RESUME_ARGUMENT> typeExpression )
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
            final SimpleExpression<? extends INSTANCE , COROUTINE_RETURN , RESUME_ARGUMENT> valueExpression ,
            final Class<? extends INSTANCE> type )
    {
        this.valueExpression =
                Objects.requireNonNull(
                        valueExpression );

        this.typeExpression =
                new Value<Class<? extends INSTANCE> , COROUTINE_RETURN , RESUME_ARGUMENT>(
                        Objects.requireNonNull(
                                type  ) );
    }

    @Override
    public Boolean evaluate(
            final HasArgumentsAndVariables<? extends RESUME_ARGUMENT> parent )
    {
        if ( CoroutineDebugSwitches.logSimpleStatementsAndExpressions )
        {
            System.out.println( "evaluate " + this );
        }

        final Object value = valueExpression.evaluate( parent );
        final Class<?> type = typeExpression.evaluate( parent );

        return type.isInstance( value );
    }

    @Override
    public List<GetFunctionArgument<? , ? , ?>> getFunctionArgumentGetsNotInFunction()
    {
        final List<GetFunctionArgument<? , ? , ?>> result = new ArrayList<>();

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
