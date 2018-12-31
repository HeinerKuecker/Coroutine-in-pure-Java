package de.heinerkuecker.coroutine_iterator.condition;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.heinerkuecker.coroutine_iterator.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine_iterator.expression.CoroExpression;
import de.heinerkuecker.coroutine_iterator.expression.GetProcedureArgument;
import de.heinerkuecker.coroutine_iterator.step.CoroIterStep;

/**
 * Compare {@link ConditionOrBooleanExpression}
 * to check greaterness of result of the left
 * expression {@link CoroExpression}
 * to the result of the right
 * expression {@link CoroExpression}.
 *
 * @author Heiner K&uuml;cker
 */
public class Greater<T extends Comparable<T>>
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
    public Greater(
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
            return false;
        }

        if ( lhsResult == null )
            // null is lesser
        {
            return false;
        }

        if ( rhsResult == null )
            // null is lesser
        {
            return true;
        }

        return lhsResult.compareTo( rhsResult ) > 0;
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
        return lhs + " > " + rhs;
    }

}
