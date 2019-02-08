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
public class InstanceOf
//implements CoroBooleanExpression
extends CoroBooleanExpression
{
    /**
     * Left hand side expression, value.
     */
    public final CoroExpression<?> valueExpression;

    /**
     * Right hand side expression, type.
     */
    public final CoroExpression<? extends Class<?>> typeExpression;

    /**
     * Constructor.
     *
     * @param valueExpression
     * @param typeExpression
     */
    public InstanceOf(
            final CoroExpression<?> valueExpression ,
            final CoroExpression<? extends Class<?>> typeExpression )
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
            final CoroExpression<?> valueExpression ,
            final Class<?> type )
    {
        this.valueExpression =
                Objects.requireNonNull(
                        valueExpression );

        this.typeExpression =
                new Value<Class<?>>(
                        Objects.requireNonNull(
                                type  ) );
    }

    //@Override
    //public boolean execute(
    //        final HasArgumentsAndVariables<?>/*CoroutineOrFunctioncallOrComplexstmt<?, ?>*/ parent )
    //{
    //    final Object value = valueExpression.evaluate( parent );
    //    final Class<?> type = typeExpression.evaluate( parent );
    //
    //    return type.isInstance( value );
    //}

    @Override
    public Boolean evaluate(
            final HasArgumentsAndVariables<?> parent )
    {
        final Object value = valueExpression.evaluate( parent );
        final Class<?> type = typeExpression.evaluate( parent );

        return type.isInstance( value );
    }

    @Override
    public List<GetFunctionArgument<?>> getFunctionArgumentGetsNotInFunction()
    {
        final List<GetFunctionArgument<?>> result = new ArrayList<>();

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
        this.valueExpression.checkUseArguments( alreadyCheckedFunctionNames, parent );
        this.typeExpression.checkUseArguments( alreadyCheckedFunctionNames, parent );
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
