package de.heinerkuecker.coroutine.condition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine.HasArgumentsAndVariables;
import de.heinerkuecker.coroutine.expression.CoroExpression;
import de.heinerkuecker.coroutine.expression.GetProcedureArgument;
import de.heinerkuecker.coroutine.expression.Value;
import de.heinerkuecker.coroutine.step.CoroIterStep;

/**
 * Equals {@link Condition}
 * to check equality of
 * the results of two given
 * {@link CoroExpression}.
 *
 * @param <T> type of expression results to compare
 * @author Heiner K&uuml;cker
 */
public class Equals<T>
implements ConditionOrBooleanExpression
{
    public final CoroExpression<? extends T> lhs;
    public final CoroExpression<? extends T> rhs;

    /**
     * Constructor.
     *
     * @param lhs
     * @param rhs
     */
    public Equals(
            final CoroExpression<? extends T> lhs ,
            final CoroExpression<? extends T> rhs )
    {
        this.lhs = Objects.requireNonNull( lhs );
        this.rhs = Objects.requireNonNull( rhs );
    }

    /**
     * Constructor.
     *
     * @param lhs
     * @param rhs
     */
    public Equals(
            final T lhsValue ,
            final CoroExpression<? extends T> rhs )
    {
        this.lhs = new Value<T>( lhsValue );
        this.rhs = Objects.requireNonNull( rhs );
    }

    /**
     * Constructor.
     *
     * @param lhs
     * @param rhs
     */
    public Equals(
            final CoroExpression<? extends T> lhs ,
            final T rhsValue )
    {
        this.lhs = Objects.requireNonNull( lhs );
        this.rhs = new Value<T>( rhsValue );
    }

    /**
     * Constructor.
     *
     * @param lhs
     * @param rhs
     */
    public Equals(
            final T lhsValue ,
            final T rhsValue )
    {
        this.lhs = new Value<T>( lhsValue );
        this.rhs = new Value<T>( rhsValue );
    }

    /**
     * @see ConditionOrBooleanExpression#execute(CoroIteratorOrProcedure)
     */
    @Override
    public boolean execute(
            final HasArgumentsAndVariables/*CoroIteratorOrProcedure<?>*/ parent )
    {
        final T lhsResult = lhs.evaluate( parent );
        final T rhsResult = rhs.evaluate( parent );

        if ( lhsResult == null && rhsResult == null )
        {
            return true;
        }

        if ( lhsResult == null )
        {
            return false;
        }

        return lhsResult.equals( rhsResult );
    }

    /**
     * @see CoroIterStep#getProcedureArgumentGetsNotInProcedure()
     */
    @Override
    public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    {
        final List<GetProcedureArgument<?>> result = new ArrayList<>();

        result.addAll(
                lhs.getProcedureArgumentGetsNotInProcedure() );

        result.addAll(
                rhs.getProcedureArgumentGetsNotInProcedure() );

        return result;
    }

    @Override
    public void checkUseUndeclaredVariables(
            final CoroIteratorOrProcedure<?> parent ,
            final Map<String, Class<?>> globalVariableTypes ,
            final Map<String, Class<?>> localVariableTypes )
    {
        this.lhs.checkUseUndeclaredVariables(
                parent ,
                globalVariableTypes ,
                localVariableTypes );

        this.rhs.checkUseUndeclaredVariables(
                parent ,
                globalVariableTypes ,
                localVariableTypes );
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return lhs + " == " + rhs;
    }

}
