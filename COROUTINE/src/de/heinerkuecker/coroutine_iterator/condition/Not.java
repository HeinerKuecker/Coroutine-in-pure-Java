package de.heinerkuecker.coroutine_iterator.condition;

import de.heinerkuecker.coroutine_iterator.CoroIteratorOrProcedure;

public class Not
implements Condition/*<CoroutineIterator<?>>*/
{
    /**
     * Expression to negate.
     */
    public final ConditionOrBooleanExpression/*Condition/*<CoroutineIterator<?>>*/ conditionToNegate;

    /**
     * Constructor.
     */
    public Not(
            final ConditionOrBooleanExpression/*Condition/*<CoroutineIterator<?>>*/ conditionToNegate )
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
            final CoroIteratorOrProcedure<?> parent )
    {
        return ! conditionToNegate.execute( parent );
    }

}
