package de.heinerkuecker.coroutine_iterator.condition;

import de.heinerkuecker.coroutine_iterator.CoroutineIterator;

/**
 * Xor {@link Condition}.
 *
 * @author Heiner K&uuml;cker
 */
public class ConditionEquals
implements Condition<CoroutineIterator<?>>
{
    private final Condition<CoroutineIterator<?>> lhs;
    private final Condition<CoroutineIterator<?>> rhs;

    /**
     * Constructor.
     */
    public ConditionEquals(
            final Condition<CoroutineIterator<?>> lhs ,
            final Condition<CoroutineIterator<?>> rhs )
    {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    /**
     * Equals the specified {@link Condition}s.
     *
     * @see Condition#execute(Object)
     */
    @Override
    public boolean execute(
            final CoroutineIterator<?> parent )
    {
        return lhs.execute( parent ) == rhs.execute( parent );
    }

}
