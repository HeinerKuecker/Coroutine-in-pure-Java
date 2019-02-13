package de.heinerkuecker.coroutine.exprs.num;

import de.heinerkuecker.coroutine.HasArgumentsAndVariables;
import de.heinerkuecker.coroutine.exprs.AbstrOneExprExpression;
import de.heinerkuecker.coroutine.exprs.SimpleExpression;

/**
 * Cast result of the specified
 * expression {@link SimpleExpression}
 * to {@link Long}.
 *
 * @param <NUMBER> number type to cast
 * @author Heiner K&uuml;cker
 */
public class CastToLong<NUMBER extends Number , COROUTINE_RETURN>
extends AbstrOneExprExpression<Long , Number , COROUTINE_RETURN>
//implements SimpleExpression<Long>
{
    /**
     * Number expression to deliver number object to cast.
     */
    //public final SimpleExpression<? extends T> numberExpression;

    /**
     * Constructor.
     *
     * @param numberExpression expression to deliver number object to cast
     */
    public CastToLong(
            final SimpleExpression<? extends NUMBER , COROUTINE_RETURN> numberExpression )
    {
        //this.numberExpression = Objects.requireNonNull( numberExpression );
        super( numberExpression );
    }

    /**
     * Add.
     */
    @Override
    public Long evaluate(
            final HasArgumentsAndVariables<?>/*CoroutineOrFunctioncallOrComplexstmt<?, ?>*/ parent )
    {
        final Number numberExpressionResult = /*numberExpression*/expr.evaluate( parent );

        if ( numberExpressionResult == null )
        {
            //throw new NullPointerException( "result of numberExpression: " + /*numberExpression*/expr );
            return null;
        }

        return numberExpressionResult.longValue();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<? extends Long>[] type()
    {
        //return Long.class;
        return new Class[] { Long.class };
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return " ( (long) " + /*numberExpression*/expr + " )";
    }

}
