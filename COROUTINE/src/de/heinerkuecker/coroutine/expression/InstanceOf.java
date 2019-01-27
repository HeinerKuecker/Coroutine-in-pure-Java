package de.heinerkuecker.coroutine.expression;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep;
import de.heinerkuecker.coroutine.HasArgumentsAndVariables;

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
implements CoroBooleanExpression
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

    @Override
    public boolean execute(
            final HasArgumentsAndVariables/*CoroutineOrProcedureOrComplexstep<?, ?>*/ parent )
    {
        final Object value = valueExpression.evaluate( parent );
        final Class<?> type = typeExpression.evaluate( parent );

        return type.isInstance( value );
    }

    @Override
    public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    {
        final List<GetProcedureArgument<?>> result = new ArrayList<>();

        result.addAll(
                valueExpression.getProcedureArgumentGetsNotInProcedure() );

        result.addAll(
                typeExpression.getProcedureArgumentGetsNotInProcedure() );

        return result;
    }

    @Override
    public void checkUseVariables(
            ////final boolean isCoroutineRoot ,
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstep<?, ?> parent,
            final Map<String, Class<?>> globalVariableTypes, final Map<String, Class<?>> localVariableTypes)
    {
        this.valueExpression.checkUseVariables(
                //isCoroutineRoot ,
                alreadyCheckedProcedureNames ,
                parent ,
                globalVariableTypes, localVariableTypes );

        this.typeExpression.checkUseVariables(
                //isCoroutineRoot ,
                alreadyCheckedProcedureNames ,
                parent ,
                globalVariableTypes, localVariableTypes );
    }

    @Override
    public void checkUseArguments(
            HashSet<String> alreadyCheckedProcedureNames, final CoroutineOrProcedureOrComplexstep<?, ?> parent )
    {
        this.valueExpression.checkUseArguments( alreadyCheckedProcedureNames, parent );
        this.typeExpression.checkUseArguments( alreadyCheckedProcedureNames, parent );
    }

    //@SuppressWarnings("unchecked")
    //@Override
    //public Class<? extends T>[] type()
    //{
    //    //return (Class<? extends T>) Number.class;
    //    return new Class[] { Number.class };
    //}

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return valueExpression + " instanceof " + typeExpression;
    }

}