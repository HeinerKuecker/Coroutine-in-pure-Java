package de.heinerkuecker.coroutine_iterator.condition;

import de.heinerkuecker.coroutine_iterator.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine_iterator.CoroutineIterator;

/**
 * Is <code>true</code> {@link Condition}
 * to check trueness of a variable in
 * {@link CoroutineIterator}'s
 * variables {@link CoroutineIterator#vars}.
 *
 * @author Heiner K&uuml;cker
 *
 * TODO rename to VarIsTrue
 */
public class IsTrue
implements Condition/*<CoroutineIterator<?>>*/
{
    /**
     * Name of a variable in
     * {@link CoroutineIterator#vars}.
     */
    public final String varName;

    /**
     * Constructor.
     */
    public IsTrue(
            final String varName )
    {
        this.varName = varName;
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
        final Object varValue = parent.vars().get( varName );

        if ( varValue instanceof Boolean )
        {
            return (boolean) varValue;
        }
        return false;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return varName + " == true";
    }

}
