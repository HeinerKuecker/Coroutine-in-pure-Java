package de.heinerkuecker.coroutine_iterator.condition;

import de.heinerkuecker.coroutine_iterator.CoroIteratorOrProcedure;

/**
 * Or {@link Condition}.
 *
 * @author Heiner K&uuml;cker
 */
public class Or
implements Condition/*<CoroutineIterator<?>>*/
{
    private final ConditionOrBooleanExpression/*Condition<CoroutineIterator<?>>*/[] conditionsToAnd;

    /**
     * Constructor.
     */
    @SafeVarargs
    public Or(
            final ConditionOrBooleanExpression/*Condition<CoroutineIterator<?>>*/... conditionsToAnd )
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
            final CoroIteratorOrProcedure<?> parent )
    {
        for ( final ConditionOrBooleanExpression/*Condition<CoroutineIterator<?>>*/ condition : conditionsToAnd )
        {
            if ( condition.execute( parent ) )
            {
                return true;
            }
        }
        return false;
    }

}
