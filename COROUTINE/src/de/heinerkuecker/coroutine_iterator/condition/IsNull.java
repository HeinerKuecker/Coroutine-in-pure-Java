package de.heinerkuecker.coroutine_iterator.condition;

import java.util.Collection;
import java.util.Map;

import de.heinerkuecker.coroutine_iterator.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine_iterator.CoroutineIterator;

/**
 * Is <code>null</code> {@link Condition}
 * to check nullness of a variable in
 * {@link CoroutineIterator}'s
 * variables {@link CoroutineIterator#vars}.
 *
 * Checks variable is
 * empty array,
 * empty {@link String},
 * empty {@link Map} or
 * empty {@link Collection}.
 *
 * @author Heiner K&uuml;cker
 *
 * TODO rename to VarIsNull
 */
public class IsNull
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
    public IsNull(
            final String varName )
    {
        this.varName = varName;
    }

    /**
     * Equals variable to <code>null</code>.
     *
     * @see Condition#execute(Object)
     */
    @Override
    public boolean execute(
            final CoroIteratorOrProcedure<?> parent )
    {
        final Object varValue = parent.localVars().get( varName );

        return varValue == null;
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return varName + " == null";
    }

}
