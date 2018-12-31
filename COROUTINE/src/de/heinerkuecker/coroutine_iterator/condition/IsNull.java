package de.heinerkuecker.coroutine_iterator.condition;

import java.util.List;

import de.heinerkuecker.coroutine_iterator.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine_iterator.expression.CoroExpression;
import de.heinerkuecker.coroutine_iterator.expression.GetProcedureArgument;
import de.heinerkuecker.coroutine_iterator.step.CoroIterStep;

/**
 * Is <code>null</code> {@link Condition}
 * to check nullness of the result
 * of the specified
 * expression {@link CoroExpression}.
 *
 * @author Heiner K&uuml;cker
 */
public class IsNull
implements ConditionOrBooleanExpression/*Condition/*<CoroutineIterator<?>>*/
{
    /**
     * Expression to check.
     */
    public final CoroExpression<?> expression;

    /**
     * Constructor.
     */
    public IsNull(
            final CoroExpression<?> expression )
    {
        this.expression = expression;
    }

    /**
     * Equals variable to <code>null</code>.
     *
     * @see Condition#execute(Object)
     */
    @Override
    public boolean execute(
            final CoroIteratorOrProcedure<?> parent )
    {
        //final Object varValue = parent.localVars().get( varName );
        final Object varValue = expression.getValue( parent );

        return varValue == null;
    }

    /**
     * @see CoroIterStep#getProcedureArgumentGetsNotInProcedure()
     */
    @Override
    public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    {
        return this.expression.getProcedureArgumentGetsNotInProcedure();
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return expression + " == null";
    }

}
