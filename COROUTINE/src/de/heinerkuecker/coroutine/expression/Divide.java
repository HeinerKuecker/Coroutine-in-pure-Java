package de.heinerkuecker.coroutine.expression;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import de.heinerkuecker.coroutine.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine.step.CoroIterStep;

/**
 * Divide
 * result of the left
 * expression {@link CoroExpression}
 * by the result of the right
 * expression {@link CoroExpression}.
 *
 * @author Heiner K&uuml;cker
 */
public class Divide<T extends Number>
implements CoroExpression<T>
{
    /**
     * Left hand side expression.
     */
    public final CoroExpression<? extends T> lhs;

    /**
     * Right hand side expression to divide by.
     */
    public final CoroExpression<? extends T> rhs;

    /**
     * Constructor.
     *
     * @param lhs
     * @param rhs
     */
    public Divide(
            CoroExpression<? extends T> lhs ,
            CoroExpression<? extends T> rhs )
    {
        this.lhs = Objects.requireNonNull( lhs );
        this.rhs = Objects.requireNonNull( rhs );
    }

    /**
     * Add.
     *
     * @see CoroExpression#getValue(CoroIteratorOrProcedure)
     */
    @Override
    public T getValue(
            final CoroIteratorOrProcedure<?> parent)
    {
        final T lhsResult = lhs.getValue( parent );
        final T rhsResult = rhs.getValue( parent );

        if ( lhsResult == null )
        {
            throw new NullPointerException( "lhs" );
        }

        if ( rhsResult == null )
        {
            throw new NullPointerException( "rhs" );
        }

        if ( lhsResult instanceof Byte ||
                lhsResult instanceof Short ||
                lhsResult instanceof Integer ||
                lhsResult instanceof AtomicInteger ||
                lhsResult instanceof Long )
        {
            final long lhsLong = lhsResult.longValue();

            if ( rhsResult instanceof Byte ||
                    rhsResult instanceof Short ||
                    rhsResult instanceof Integer ||
                    rhsResult instanceof AtomicInteger ||
                    rhsResult instanceof Long ||
                    rhsResult instanceof AtomicLong )
            {
                return (T) (Long) ( ( (long) lhsLong ) / rhsResult.longValue() );
            }
            else if ( rhsResult instanceof Float ||
                    rhsResult instanceof Double )
            {
                return (T) (Double) ( ( (double) lhsLong ) / rhsResult.doubleValue() );
            }
            else if ( rhsResult instanceof BigInteger )
            {
                return (T) BigInteger.valueOf( lhsLong ).divide( ( (BigInteger) rhsResult ) );
            }
            else if ( rhsResult instanceof BigDecimal )
            {
                return (T) BigDecimal.valueOf( lhsLong ).divide( ( (BigDecimal) rhsResult ) );
            }
        }

        return (T) (Double) ( lhsResult.doubleValue() / rhsResult.doubleValue() );
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
        return lhs + " / " + rhs;
    }

}
