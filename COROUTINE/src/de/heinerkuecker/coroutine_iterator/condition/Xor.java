package de.heinerkuecker.coroutine_iterator.condition;

import de.heinerkuecker.coroutine_iterator.CoroIteratorOrProcedure;

/**
 * Xor {@link Condition}.
 *
 * @author Heiner K&uuml;cker
 */
public class Xor
implements ConditionOrBooleanExpression/*Condition/*<CoroutineIterator<?>>*/
{
    private final ConditionOrBooleanExpression/*Condition/*<CoroutineIterator<?>>*/ lhs;
    private final ConditionOrBooleanExpression/*Condition/*<CoroutineIterator<?>>*/ rhs;

    /**
     * Constructor.
     */
    public Xor(
            final ConditionOrBooleanExpression/*Condition/*<CoroutineIterator<?>>*/ lhs ,
            final ConditionOrBooleanExpression/*Condition/*<CoroutineIterator<?>>*/ rhs )
    {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    /**
     * Xors the specified {@link Condition}s.
     *
     * @see Condition#execute(Object)
     */
    @Override
    public boolean execute(
            final CoroIteratorOrProcedure<?> parent )
    {
        return lhs.execute( parent ) != rhs.execute( parent );
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return lhs + " != " + rhs;
    }

}
