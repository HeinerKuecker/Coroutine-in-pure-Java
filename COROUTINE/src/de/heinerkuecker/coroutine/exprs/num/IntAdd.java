package de.heinerkuecker.coroutine.exprs.num;

import de.heinerkuecker.coroutine.HasArgumentsAndVariables;
import de.heinerkuecker.coroutine.exprs.AbstrLhsRhsExpression;
import de.heinerkuecker.coroutine.exprs.CoroExpression;
import de.heinerkuecker.coroutine.exprs.Value;

/**
 * {@link Integer} add
 * result of the right
 * expression {@link CoroExpression}
 * to the result of the left
 * expression {@link CoroExpression}.
 *
 * @author Heiner K&uuml;cker
 */
public class IntAdd
//implements CoroExpression<Integer>
extends AbstrLhsRhsExpression<Integer>
{
    /**
     * Left hand side expression.
     */
    //public final CoroExpression<Integer> lhs;

    /**
     * Right hand side expression to add.
     */
    //public final CoroExpression<Integer> rhs;

    /**
     * Constructor.
     *
     * @param lhs
     * @param rhs
     */
    public IntAdd(
            final CoroExpression<Integer> lhs ,
            final CoroExpression<Integer> rhs )
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
    public IntAdd(
            final CoroExpression<Integer> lhs ,
            final Integer rhs )
    {
        //this.lhs = Objects.requireNonNull( lhs );
        //this.rhs = new Value<Integer>( rhs );
        super(
                lhs ,
                new Value<Integer>( rhs ) );
    }

    /**
     * Add.
     */
    @Override
    public Integer evaluate(
            final HasArgumentsAndVariables<?>/*CoroutineOrFunctioncallOrComplexstmt<?, ?>*/ parent )
    {
        final Integer lhsResult = lhs.evaluate( parent );
        final Integer rhsResult = rhs.evaluate( parent );

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
    public Class<Integer>[] type()
    {
        //return (Class<? extends T>) Number.class;
        return new Class[] { Integer.class };
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
