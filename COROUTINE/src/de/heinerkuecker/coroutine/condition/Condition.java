package de.heinerkuecker.coroutine.condition;

import de.heinerkuecker.coroutine.CoroutineIterator;

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
    ///**
    // * Execute the condition and return the result.
    // *
    // * @param parent the {@link CoroutineOrProcedureOrComplexstep} instance
    // * @return condition result
    // */
    //@Override
    //boolean execute(
    //        final HasArgumentsAndVariables<?>/*CoroutineOrProcedureOrComplexstep<?, ?>*/ parent );
}
