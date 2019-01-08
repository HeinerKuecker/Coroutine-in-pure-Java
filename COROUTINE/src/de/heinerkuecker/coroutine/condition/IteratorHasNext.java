package de.heinerkuecker.coroutine.condition;

import java.util.Iterator;
import java.util.List;

import de.heinerkuecker.coroutine.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine.expression.CoroExpression;
import de.heinerkuecker.coroutine.expression.GetProcedureArgument;
import de.heinerkuecker.coroutine.step.CoroIterStep;

/**
 * {@link Iterator#hasNext} {@link Condition}
 * to check has next element
 * of the specified {@link Iterator}
 * expression {@link CoroExpression}.
 *
 * @author Heiner K&uuml;cker
 */
public class IteratorHasNext
implements ConditionOrBooleanExpression/*Condition/*<CoroutineIterator<?>>*/
{
    /**
     * Expression to check.
     */
    public final CoroExpression<Iterator<?>> iteratorExpression;

    /**
     * Constructor.
     */
    public IteratorHasNext(
            final CoroExpression<Iterator<?>> iteratorExpression )
    {
        this.iteratorExpression = iteratorExpression;
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
        final Iterator<?> iterator = iteratorExpression.evaluate( parent );

        // TODO null handling
        return iterator.hasNext();
    }

    /**
     * @see CoroIterStep#getProcedureArgumentGetsNotInProcedure()
     */
    @Override
    public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    {
        return this.iteratorExpression.getProcedureArgumentGetsNotInProcedure();
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return iteratorExpression + ".hasNext()";
    }

}
