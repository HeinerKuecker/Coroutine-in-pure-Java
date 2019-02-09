package de.heinerkuecker.coroutine.exprs.num;

import de.heinerkuecker.coroutine.HasArgumentsAndVariables;
import de.heinerkuecker.coroutine.exprs.AbstrLhsRhsExpression;
import de.heinerkuecker.coroutine.exprs.CoroExpression;
import de.heinerkuecker.coroutine.exprs.Value;

/**
 * {@link Long} add
 * result of the right
 * expression {@link CoroExpression}
 * to the result of the left
 * expression {@link CoroExpression}.
 *
 * @author Heiner K&uuml;cker
 */
public class LongAdd<COROUTINE_RETURN>
//implements CoroExpression<Long>
extends AbstrLhsRhsExpression<Long , COROUTINE_RETURN>
{
    /**
     * Left hand side expression.
     */
    //public final CoroExpression<Long> lhs;

    /**
     * Right hand side expression to add.
     */
    //public final CoroExpression<Long> rhs;

    /**
     * Constructor.
     *
     * @param lhs
     * @param rhs
     */
    public LongAdd(
            final CoroExpression<Long , COROUTINE_RETURN> lhs ,
            final CoroExpression<Long , COROUTINE_RETURN> rhs )
    {
        //this.lhs = Objects.requireNonNull( lhs );
        //this.rhs = Objects.requireNonNull( rhs );
        super(
                lhs ,
                rhs );
    }

    /**
     * Convenience constructor.
     *
     * @param lhs
     * @param rhs
     */
    public LongAdd(
            final CoroExpression<Long , COROUTINE_RETURN> lhs ,
            final Long rhs )
    {
        //this.lhs = Objects.requireNonNull( lhs );
        //this.rhs = new Value<Long>( rhs );
        super(
                lhs ,
                new Value<Long , COROUTINE_RETURN>( rhs ) );
    }

    /**
     * Add.
     */
    @Override
    public Long evaluate(
            final HasArgumentsAndVariables<?>/*CoroutineOrFunctioncallOrComplexstmt<?, ?>*/ parent )
    {
        final Long lhsResult = lhs.evaluate( parent );
        final Long rhsResult = rhs.evaluate( parent );

        if ( lhsResult == null )
        {
            throw new NullPointerException( "lhs: " + lhs );
        }

        if ( rhsResult == null )
        {
            throw new NullPointerException( "rhs: " + rhs );
        }

        return lhsResult + rhsResult;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<Long>[] type()
    {
        //return (Class<? extends T>) Number.class;
        return new Class[] { Long.class };
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return lhs + " + " + rhs;
    }

}
