package de.heinerkuecker.coroutine_iterator.condition;

import de.heinerkuecker.coroutine_iterator.CoroIteratorOrProcedure;

/**
 * And {@link Condition}.
 *
 * @author Heiner K&uuml;cker
 *
 * TODO rename to AndVals
 */
public class And
implements Condition/*<CoroutineIterator<?>>*/
{
    private final ConditionOrBooleanExpression/*Condition/*<CoroutineIterator<?>>*/[] conditionsToAnd;

    /**
     * Constructor.
     */
    @SafeVarargs
    public And(
            final ConditionOrBooleanExpression/*Condition/*<CoroutineIterator<?>>*/... conditionsToAnd )
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
        for ( final ConditionOrBooleanExpression/*Condition/*<CoroutineIterator<?>>*/ condition : conditionsToAnd )
        {
            if ( ! condition.execute( parent ) )
            {
                return false;
            }
        }
        return true;
    }

}
