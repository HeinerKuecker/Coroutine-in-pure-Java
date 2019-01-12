package de.heinerkuecker.coroutine.condition;

import java.util.List;

import de.heinerkuecker.coroutine.HasArgumentsAndVariables;
import de.heinerkuecker.coroutine.expression.CoroExpression;
import de.heinerkuecker.coroutine.expression.GetProcedureArgument;
import de.heinerkuecker.coroutine.step.CoroIterStep;

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
     * Constructor.
     */
    public Not(
            final CoroExpression<Boolean> conditionToNegate )
    {
        this.conditionToNegate =
                new IsTrue(
                        conditionToNegate );
    }

    /**
     * Negates the specified {@link Condition}.
     *
     * @see Condition#execute
     */
    @Override
    public boolean execute(
            final HasArgumentsAndVariables/*CoroIteratorOrProcedure<?>*/ parent )
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

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        //return "Not [conditionToNegate=" + this.conditionToNegate + "]";
        return "! ( " + this.conditionToNegate + " )";
    }

}
