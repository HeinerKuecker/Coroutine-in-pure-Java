package de.heinerkuecker.coroutine_iterator.condition;

import de.heinerkuecker.coroutine_iterator.CoroutineIterator;

/**
 * Or {@link Condition}.
 *
 * @author Heiner K&uuml;cker
 */
public class Or
implements Condition<CoroutineIterator<?>>
{
    private final Condition<CoroutineIterator<?>>[] conditionsToAnd;

    /**
     * Constructor.
     */
    @SafeVarargs
    public Or(
            final Condition<CoroutineIterator<?>>... conditionsToAnd )
    {
        this.conditionsToAnd = conditionsToAnd;
    }

    /**
     * Negates the specified {@link Condition}.
     *
     * @see Condition#execute(java.lang.Object)
     */
    @Override
    public boolean execute(
            final CoroutineIterator<?> parent )
    {
        for ( final Condition<CoroutineIterator<?>> condition : conditionsToAnd )
        {
            if ( condition.execute( parent ) )
            {
                return true;
            }
        }
        return false;
    }

}
