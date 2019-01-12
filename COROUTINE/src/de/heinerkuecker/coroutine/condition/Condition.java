package de.heinerkuecker.coroutine.condition;

import de.heinerkuecker.coroutine.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.HasArgumentsAndVariables;

/**
 * Interface for condition
 * to control the flow in
 * {@link CoroutineIterator}.
 *
 * @param <PARENT> type the {@link CoroutineIterator} instance
 * @author Heiner K&uuml;cker
 */
public interface Condition/*<PARENT>*/
extends ConditionOrBooleanExpression
{
    /**
     * Execute the condition and return the result.
     *
     * @param parent the {@link CoroIteratorOrProcedure} instance
     * @return condition result
     */
    @Override
    boolean execute(
            final HasArgumentsAndVariables/*CoroIteratorOrProcedure<?>*/ parent );
}
