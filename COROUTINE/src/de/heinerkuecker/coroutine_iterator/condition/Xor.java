package de.heinerkuecker.coroutine_iterator.condition;

import java.util.ArrayList;
import java.util.List;

import de.heinerkuecker.coroutine_iterator.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine_iterator.expression.GetProcedureArgument;
import de.heinerkuecker.coroutine_iterator.step.CoroIterStep;

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
     * @see CoroIterStep#getProcedureArgumentGetsNotInProcedure()
     */
    @Override
    public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    {
        final List<GetProcedureArgument<?>> result = new ArrayList<>();

        result.addAll(
                lhs.getProcedureArgumentGetsNotInProcedure() );

        result.addAll(
                rhs.getProcedureArgumentGetsNotInProcedure() );

        return result;
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
