package de.heinerkuecker.coroutine_iterator.condition;

import de.heinerkuecker.coroutine_iterator.CoroutineIterator;

/**
 * Interface for condition
 * to control the flow in
 * {@link CoroutineIterator}.
 *
 * @param <PARENT> type the {@link CoroutineIterator} instance
 * @author Heiner K&uuml;cker
 */
public interface Condition<PARENT>
{
    /**
     * Execute the condition and return the result.
     *
     * @param parent the {@link CoroutineIterator} instance
     * @return condition result
     */
    boolean execute(
            final PARENT parent );
}
