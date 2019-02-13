package de.heinerkuecker.coroutine.exprs.num;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import de.heinerkuecker.coroutine.HasArgumentsAndVariables;
import de.heinerkuecker.coroutine.exprs.AbstrLhsRhsExpression;
import de.heinerkuecker.coroutine.exprs.SimpleExpression;

/**
 * Multiply
 * result of the left
 * expression {@link SimpleExpression}
 * with the result of the right
 * expression {@link SimpleExpression}.
 *
 * @author Heiner K&uuml;cker
 */
public class Multiply<NUMBER extends Number , COROUTINE_RETURN>
//implements SimpleExpression<T>
extends AbstrLhsRhsExpression<NUMBER , COROUTINE_RETURN>
{
    /**
     * Left hand side expression.
     */
    //public final SimpleExpression<? extends T> lhs;

    /**
     * Right hand side expression to multiply with.
     */
    //public final SimpleExpression<? extends T> rhs;

    /**
     * Constructor.
     *
     * @param lhs
     * @param rhs
     */
    public Multiply(
            final SimpleExpression<? extends NUMBER , COROUTINE_RETURN> lhs ,
            final SimpleExpression<? extends NUMBER , COROUTINE_RETURN> rhs )
    {
        //this.lhs = Objects.requireNonNull( lhs );
        //this.rhs = Objects.requireNonNull( rhs );
        super(
                lhs ,
                rhs );
    }

    /**
     * Add.
     */
    @Override
    public NUMBER evaluate(
            final HasArgumentsAndVariables<?>/*CoroutineOrFunctioncallOrComplexstmt<?, ?>*/ parent )
    {
        final NUMBER lhsResult = lhs.evaluate( parent );
        final NUMBER rhsResult = rhs.evaluate( parent );

        if ( lhsResult == null )
        {
            throw new NullPointerException( "lhs: " + lhs );
        }

        if ( rhsResult == null )
        {
            throw new NullPointerException( "rhs: " + rhs );
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
                return (NUMBER) (Long) ( ( (long) lhsLong ) * rhsResult.longValue() );
            }
            else if ( rhsResult instanceof Float ||
                    rhsResult instanceof Double )
            {
                return (NUMBER) (Double) ( ( (double) lhsLong ) * rhsResult.doubleValue() );
            }
            else if ( rhsResult instanceof BigInteger )
            {
                return (NUMBER) BigInteger.valueOf( lhsLong ).multiply( ( (BigInteger) rhsResult ) );
            }
            else if ( rhsResult instanceof BigDecimal )
            {
                return (NUMBER) BigDecimal.valueOf( lhsLong ).multiply( ( (BigDecimal) rhsResult ) );
            }
        }

        return (NUMBER) (Double) ( lhsResult.doubleValue() * rhsResult.doubleValue() );
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<? extends NUMBER>[] type()
    {
        //return (Class<? extends T>) Number.class;
        return new Class[] { Number.class };
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return lhs + " * " + rhs;
    }

}
