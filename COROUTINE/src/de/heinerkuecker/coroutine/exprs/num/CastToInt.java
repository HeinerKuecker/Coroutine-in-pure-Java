package de.heinerkuecker.coroutine.exprs.num;

import de.heinerkuecker.coroutine.HasArgumentsAndVariables;
import de.heinerkuecker.coroutine.exprs.AbstrOneExprExpression;
import de.heinerkuecker.coroutine.exprs.SimpleExpression;

/**
 * Cast result of the specified
 * expression {@link SimpleExpression}
 * to {@link Integer}.
 *
 * @param <NUMBER> number type to cast
 * @author Heiner K&uuml;cker
 */
public class CastToInt<NUMBER extends Number , COROUTINE_RETURN>
extends AbstrOneExprExpression<Integer , Number , COROUTINE_RETURN>
//implements SimpleExpression<Integer>
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
    public CastToInt(
            final SimpleExpression<? extends NUMBER , COROUTINE_RETURN> numberExpression )
    {
        //this.numberExpression = Objects.requireNonNull( numberExpression );
        super( numberExpression );
    }

    /**
     * Add.
     */
    @Override
    public Integer evaluate(
            final HasArgumentsAndVariables<?>/*CoroutineOrFunctioncallOrComplexstmt<?, ?>*/ parent )
    {
        final Number numberExpressionResult = /*numberExpression*/expr.evaluate( parent );

        if ( numberExpressionResult == null )
        {
            //throw new NullPointerException( "result of numberExpression: " + /*numberExpression*/expr );
            return null;
        }

        return numberExpressionResult.intValue();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<? extends Integer>[] type()
    {
        //return Integer.class;
        return new Class[] { Integer.class };
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return " ( (int) " + /*numberExpression*/expr + " )";
    }

}
