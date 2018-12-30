package de.heinerkuecker.coroutine_iterator.condition;

import de.heinerkuecker.coroutine_iterator.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine_iterator.CoroutineIterator;

/**
 * Equals {@link Condition}
 * to check equality of a
 * variable in
 * {@link CoroutineIterator}'s
 * variables {@link CoroutineIterator#localVars()}
 * and the specified object.
 *
 * @author Heiner K&uuml;cker
 *
 * TODO remove
 */
public class LocalVarEqualsValue
implements Condition/*<CoroutineIterator<?>>*/
{
    /**
     * Name of a variable in
     * {@link CoroutineIterator#localVars()}.
     */
    public final String varName;

    public final Object equalValue;

    /**
     * Constructor.
     */
    public LocalVarEqualsValue(
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
        final Object varValue = parent.localVars().get( varName );

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
