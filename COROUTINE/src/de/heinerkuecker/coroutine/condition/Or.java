package de.heinerkuecker.coroutine.condition;

import java.util.ArrayList;
import java.util.List;

import de.heinerkuecker.coroutine.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine.expression.GetProcedureArgument;
import de.heinerkuecker.coroutine.step.CoroIterStep;

/**
 * Or {@link Condition}.
 *
 * @author Heiner K&uuml;cker
 */
public class Or
implements Condition/*<CoroutineIterator<?>>*/
{
    private final ConditionOrBooleanExpression/*Condition<CoroutineIterator<?>>*/[] conditionsToOr;

    /**
     * Constructor.
     */
    @SafeVarargs
    public Or(
            final ConditionOrBooleanExpression/*Condition<CoroutineIterator<?>>*/... conditionsToAnd )
    {
        this.conditionsToOr = conditionsToAnd;
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
        for ( final ConditionOrBooleanExpression/*Condition<CoroutineIterator<?>>*/ condition : conditionsToOr )
        {
            if ( condition.execute( parent ) )
            {
                return true;
            }
        }
        return false;
    }

    /**
     * @see CoroIterStep#getProcedureArgumentGetsNotInProcedure()
     */
    @Override
    public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    {
        final List<GetProcedureArgument<?>> result = new ArrayList<>();

        for ( final ConditionOrBooleanExpression/*Condition<CoroutineIterator<?>>*/ condition : conditionsToOr )
        {
            result.addAll(
                    condition.getProcedureArgumentGetsNotInProcedure() );
        }

        return result;
    }

}
