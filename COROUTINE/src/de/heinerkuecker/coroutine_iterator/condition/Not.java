package de.heinerkuecker.coroutine_iterator.condition;

import java.util.List;

import de.heinerkuecker.coroutine_iterator.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine_iterator.expression.GetProcedureArgument;
import de.heinerkuecker.coroutine_iterator.step.CoroIterStep;

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

    /**
     * @see CoroIterStep#getProcedureArgumentGetsNotInProcedure()
     */
    @Override
    public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    {
        return this.conditionToNegate.getProcedureArgumentGetsNotInProcedure();
    }


}
