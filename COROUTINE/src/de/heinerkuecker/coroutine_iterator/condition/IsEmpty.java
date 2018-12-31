package de.heinerkuecker.coroutine_iterator.condition;

import java.util.Collection;
import java.util.Map;

import de.heinerkuecker.coroutine_iterator.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine_iterator.expression.CoroExpression;

/**
 * Is empty {@link Condition}
 * to check emptiness of the result
 * of the specified
 * expression {@link CoroExpression}.
 *
 * Checks is
 * <code>null</code>,
 * empty array,
 * empty {@link String},
 * empty {@link Map} or
 * empty {@link Collection}.
 *
 * @author Heiner K&uuml;cker
 */
public class IsEmpty
implements ConditionOrBooleanExpression/*Condition/*<CoroutineIterator<?>>*/
{
    /**
     * Expression to check.
     */
    public final CoroExpression<?> expression;

    /**
     * Constructor.
     */
    public IsEmpty(
            final CoroExpression<?> expression )
    {
        this.expression = expression;
    }

    /**
     * Checks variable is
     * <code>null</code>,
     * empty array,
     * empty {@link String},
     * empty {@link Map} or
     * empty {@link Collection}.
     *
     * @see Condition#execute(Object)
     */
    @Override
    public boolean execute(
            final CoroIteratorOrProcedure<?> parent )
    {
        //final Object varValue = parent.localVars().get( varName );
        final Object varValue = expression.getValue( parent );

        if ( varValue == null )
        {
            return true;
        }

        if ( varValue instanceof String )
        {
            return ( (String) varValue ).isEmpty();
        }

        if ( varValue instanceof Collection )
        {
            return ( (Collection<?>) varValue ).isEmpty();
        }

        if ( varValue instanceof Map )
        {
            return ( (Map<?, ?>) varValue ).isEmpty();
        }

        if ( varValue instanceof Object[] )
        {
            return ( (Object[]) varValue ).length == 0;
        }

        if ( varValue instanceof boolean[] )
        {
            return ( (boolean[]) varValue ).length == 0;
        }

        if ( varValue instanceof byte[] )
        {
            return ( (byte[]) varValue ).length == 0;
        }

        if ( varValue instanceof short[] )
        {
            return ( (short[]) varValue ).length == 0;
        }

        if ( varValue instanceof char[] )
        {
            return ( (char[]) varValue ).length == 0;
        }

        if ( varValue instanceof int[] )
        {
            return ( (int[]) varValue ).length == 0;
        }

        if ( varValue instanceof long[] )
        {
            return ( (long[]) varValue ).length == 0;
        }

        if ( varValue instanceof float[] )
        {
            return ( (float[]) varValue ).length == 0;
        }

        if ( varValue instanceof double[] )
        {
            return ( (double[]) varValue ).length == 0;
        }

        return false;
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return expression + " is empty";
    }

}
