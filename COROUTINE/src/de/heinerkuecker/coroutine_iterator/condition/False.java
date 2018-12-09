package de.heinerkuecker.coroutine_iterator.condition;

import de.heinerkuecker.coroutine_iterator.CoroutineIterator;

/**
 * Constant false {@link Condition}
 * to control flow in
 * {@link CoroutineIterator}.
 *
 * @author Heiner K&uuml;cker
 */
public class False
implements Condition<CoroutineIterator<?>>
{
    /**
     * Returns always false.
     *
     * @see Condition#execute(Object)
     */
    @Override
    public boolean execute(
            final CoroutineIterator<?> parent )
    {
        return false;
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return "false";
    }

}
