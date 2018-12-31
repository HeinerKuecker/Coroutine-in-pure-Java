package de.heinerkuecker.coroutine.condition;

import java.util.Collections;
import java.util.List;

import de.heinerkuecker.coroutine.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.expression.GetProcedureArgument;
import de.heinerkuecker.coroutine.step.CoroIterStep;

/**
 * Constant true {@link Condition}
 * to control flow in
 * {@link CoroutineIterator}.
 *
 * @author Heiner K&uuml;cker
 */
public class True
implements ConditionOrBooleanExpression/*Condition/*<CoroutineIterator<?>>*/
{
    /**
     * Returns always true.
     *
     * @see Condition#execute(Object)
     */
    @Override
    public boolean execute(
            final CoroIteratorOrProcedure<?> parent )
    {
        return true;
    }

    /**
     * @see CoroIterStep#getProcedureArgumentsNotInProcedure()
     */
    @Override
    public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    {
        return Collections.emptyList();
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return "true";
    }

}
