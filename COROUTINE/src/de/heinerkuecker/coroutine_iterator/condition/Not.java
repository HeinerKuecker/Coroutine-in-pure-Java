package de.heinerkuecker.coroutine_iterator.condition;

import de.heinerkuecker.coroutine_iterator.CoroutineIterator;

public class Not
implements Condition<CoroutineIterator<?>>
{
    public final Condition<CoroutineIterator<?>> conditionToNegate;

    /**
     * Constructor.
     */
    public Not(
            final Condition<CoroutineIterator<?>> conditionToNegate )
    {
        this.conditionToNegate = conditionToNegate;
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
        return ! conditionToNegate.execute( parent );
    }

}
