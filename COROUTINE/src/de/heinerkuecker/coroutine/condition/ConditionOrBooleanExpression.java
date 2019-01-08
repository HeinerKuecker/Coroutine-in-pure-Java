package de.heinerkuecker.coroutine.condition;

import de.heinerkuecker.coroutine.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine.expression.CoroExpression;
import javafx.beans.binding.BooleanExpression;

/**
 * Common interface for
 * {@link Condition} and
 * {@link BooleanExpression}.
 *
 * @author Heiner K&uuml;cker
 */
public interface ConditionOrBooleanExpression
extends CoroExpression<Boolean>
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

    /**
     * @see CoroExpression#evaluate(CoroIteratorOrProcedure)
     */
    @Override
    default public Boolean evaluate(
            final CoroIteratorOrProcedure<?> parent )
    {
        return execute( parent );
    }

    //abstract public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure();
}
