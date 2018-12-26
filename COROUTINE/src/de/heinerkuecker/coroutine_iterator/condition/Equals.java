package de.heinerkuecker.coroutine_iterator.condition;

import de.heinerkuecker.coroutine_iterator.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine_iterator.CoroutineIterator;

/**
 * Equals {@link Condition}
 * to check equality of a
 * variable in
 * {@link CoroutineIterator}'s
 * variables {@link CoroutineIterator#vars}
 * and the specified object.
 *
 * @author Heiner K&uuml;cker
 *
 * TODO rename to VarEqualsVal
 */
public class Equals
implements Condition/*<CoroutineIterator<?>>*/
{
    /**
     * Name of a variable in
     * {@link CoroutineIterator#vars}.
     */
    public final String varName;

    public final Object equalValue;

    /**
     * Constructor.
     */
    public Equals(
            final String varName ,
            final Object equalValue )
    {
        this.varName = varName;
        this.equalValue = equalValue;
    }

    /**
     * Equals variable to {@link #equalValue}.
     *
     * @see Condition#execute(java.lang.Object)
     */
    @Override
    public boolean execute(
            final CoroIteratorOrProcedure<?> parent )
    {
        final Object varValue = parent.vars().get( varName );

        if ( varValue == null )
        {
            return equalValue == null;
        }

        return varValue.equals( equalValue );
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return varName + " == " + equalValue;
    }

}
