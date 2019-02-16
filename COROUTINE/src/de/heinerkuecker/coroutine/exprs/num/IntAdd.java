package de.heinerkuecker.coroutine.exprs.num;

import de.heinerkuecker.coroutine.HasArgumentsAndVariables;
import de.heinerkuecker.coroutine.exprs.AbstrLhsRhsExpression;
import de.heinerkuecker.coroutine.exprs.SimpleExpression;
import de.heinerkuecker.coroutine.exprs.Value;

/**
 * {@link Integer} add
 * result of the right
 * expression {@link SimpleExpression}
 * to the result of the left
 * expression {@link SimpleExpression}.
 *
 * @author Heiner K&uuml;cker
 */
public class IntAdd<COROUTINE_RETURN , RESUME_ARGUMENT>
//implements SimpleExpression<Integer>
extends AbstrLhsRhsExpression<Integer , COROUTINE_RETURN , RESUME_ARGUMENT>
{
    /**
     * Left hand side expression.
     */
    //public final SimpleExpression<Integer> lhs;

    /**
     * Right hand side expression to add.
     */
    //public final SimpleExpression<Integer> rhs;

    /**
     * Constructor.
     *
     * @param lhs
     * @param rhs
     */
    public IntAdd(
            final SimpleExpression<Integer , COROUTINE_RETURN , RESUME_ARGUMENT> lhs ,
            final SimpleExpression<Integer , COROUTINE_RETURN , RESUME_ARGUMENT> rhs )
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
            final SimpleExpression<Integer , COROUTINE_RETURN , RESUME_ARGUMENT> lhs ,
            final Integer rhs )
    {
        //this.lhs = Objects.requireNonNull( lhs );
        //this.rhs = new Value<Integer>( rhs );
        super(
                lhs ,
                new Value<Integer , COROUTINE_RETURN , RESUME_ARGUMENT>( rhs ) );
    }

    /**
     * Add.
     */
    @Override
    public Integer evaluate(
            final HasArgumentsAndVariables<? extends RESUME_ARGUMENT>/*CoroutineOrFunctioncallOrComplexstmt<?, ?>*/ parent )
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
