package de.heinerkuecker.coroutine.condition;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine.expression.CoroExpression;
import de.heinerkuecker.coroutine.expression.GetProcedureArgument;
import de.heinerkuecker.coroutine.step.CoroIterStep;

/**
 * Compare {@link ConditionOrBooleanExpression}
 * to check lesserness of result of the left
 * expression {@link CoroExpression}
 * to the result of the right
 * expression {@link CoroExpression}.
 *
 * @author Heiner K&uuml;cker
 */
public class Lesser<T extends Comparable<T>>
implements ConditionOrBooleanExpression
{
    /**
     * Left hand side expression to check.
     */
    public final CoroExpression<? extends T> lhs;

    /**
     * Right hand side expression to check.
     */
    public final CoroExpression<? extends T> rhs;

    /**
     * Constructor.
     *
     * @param lhs
     * @param rhs
     */
    public Lesser(
            final CoroExpression<? extends T> lhs ,
            final CoroExpression<? extends T> rhs )
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
        final T lhsResult = lhs.evaluate( parent );
        final T rhsResult = rhs.evaluate( parent );

        if ( lhsResult == null && rhsResult == null )
        {
            return false;
        }

        if ( lhsResult == null )
            // null is lesser
        {
            return true;
        }

        if ( rhsResult == null )
            // null is lesser
        {
            return false;
        }

        return lhsResult.compareTo( rhsResult ) < 0;
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

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return lhs + " < " + rhs;
    }

}
