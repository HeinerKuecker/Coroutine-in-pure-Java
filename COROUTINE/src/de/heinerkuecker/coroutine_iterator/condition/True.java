package de.heinerkuecker.coroutine_iterator.condition;

import de.heinerkuecker.coroutine_iterator.CoroutineIterator;

/**
 * Constant true {@link Condition}
 * to control flow in
 * {@link CoroutineIterator}.
 *
 * @author Heiner K&uuml;cker
 */
public class True
implements Condition<CoroutineIterator<?>>
{
    /**
     * Returns always true.
     *
     * @see Condition#execute(Object)
     */
    @Override
    public boolean execute(
            final CoroutineIterator<?> parent )
    {
        return true;
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return "true";
    }

}
