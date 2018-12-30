package de.heinerkuecker.coroutine_iterator.expression;

import de.heinerkuecker.coroutine_iterator.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine_iterator.condition.ConditionOrBooleanExpression;

public interface CoroBooleanExpression
extends CoroExpression<Boolean> ,
ConditionOrBooleanExpression
{
    /**
     * Execute the condition and return the result.
     *
     * @param parent the {@link CoroIteratorOrProcedure} instance
     * @return condition result
     */
    @Override
    boolean execute(
            //final PARENT parent
            final CoroIteratorOrProcedure<?> parent );
}
