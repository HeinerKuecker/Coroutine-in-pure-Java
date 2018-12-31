package de.heinerkuecker.coroutine_iterator.condition;

import de.heinerkuecker.coroutine_iterator.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine_iterator.expression.CoroExpression;

/**
 * Is <code>true</code> {@link Condition}
 * to check trueness of the result
 * of the specified
 * expression {@link CoroExpression}.
 *
 * @author Heiner K&uuml;cker
 *
 * TODO rename to VarIsTrue
 */
public class IsTrue
implements ConditionOrBooleanExpression/*Condition/*<CoroutineIterator<?>>*/
{
    /**
     * Expression to check.
     */
    public final CoroExpression<Boolean> expression;

    /**
     * Constructor.
     */
    public IsTrue(
            final CoroExpression<Boolean> expression )
    {
        this.expression = expression;
    }

    /**
     * Equals variable to <code>true</code>.
     *
     * @see Condition#execute(java.lang.Object)
     */
    @Override
    public boolean execute(
            final CoroIteratorOrProcedure<?> parent )
    {
        //final Object varValue = parent.localVars().get( varName );
        final Object varValue = expression.getValue( parent );

        if ( varValue instanceof Boolean )
        {
            return (boolean) varValue;
        }
        return false;
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return expression + " == true";
    }

}
