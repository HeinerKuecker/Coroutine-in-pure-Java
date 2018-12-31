package de.heinerkuecker.coroutine_iterator.condition;

import java.util.Objects;

import de.heinerkuecker.coroutine_iterator.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine_iterator.expression.CoroExpression;

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
            CoroExpression<? extends T> lhs ,
            CoroExpression<? extends T> rhs )
    {
        this.lhs = Objects.requireNonNull( lhs );
        this.rhs = Objects.requireNonNull( rhs );
    }

    /**
     * @see ConditionOrBooleanExpression#execute(CoroIteratorOrProcedure)
     */
    @Override
    public boolean execute(
            final CoroIteratorOrProcedure<?> parent )
    {
        final T lhsResult = lhs.getValue( parent );
        final T rhsResult = rhs.getValue( parent );

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
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return lhs + " == " + rhs;
    }

}
