package de.heinerkuecker.coroutine_iterator.condition;

import de.heinerkuecker.coroutine_iterator.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine_iterator.CoroutineIterator;

/**
 * Compare {@link Condition}
 * to check greaterness of a
 * variable in
 * {@link CoroutineIterator}'s
 * variables {@link CoroutineIterator#vars}
 * and the specified object.
 *
 * @author Heiner K&uuml;cker
 *
 * TODO rename to VarGreaterVal
 */
@SuppressWarnings("rawtypes")
public class Greater
implements Condition/*<CoroutineIterator<?>>*/
{
    /**
     * Name of a variable in
     * {@link CoroutineIterator#vars}.
     */
    public final String varName;

    public final Comparable compareValue;

    /**
     * Constructor.
     */
    public Greater(
            final String varName ,
            final Comparable compareValue )
    {
        this.varName = varName;
        this.compareValue = compareValue;
    }

    /**
     * Compars variable to {@link #compareValue}.
     *
     * @see Condition#execute(java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean execute(
            final CoroIteratorOrProcedure<?> parent )
    {
        final Comparable varValue = (Comparable) parent.vars().get( varName );

        if ( varValue == null )
        {
            return compareValue == null;
        }

        return varValue.compareTo( compareValue ) > 0;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return varName + " > " + compareValue;
    }

}
