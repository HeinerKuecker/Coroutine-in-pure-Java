package de.heinerkuecker.coroutine_iterator.condition;

import java.util.List;

import de.heinerkuecker.coroutine_iterator.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine_iterator.expression.GetProcedureArgument;

public interface ConditionOrBooleanExpression
{
    /**
     * Execute the condition and return the result.
     *
     * @param parent the {@link CoroIteratorOrProcedure} instance
     * @return condition result
     */
    boolean execute(
            //final PARENT parent
            final CoroIteratorOrProcedure<?> parent );

    abstract public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure();
}
