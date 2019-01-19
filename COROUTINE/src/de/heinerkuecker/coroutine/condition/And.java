package de.heinerkuecker.coroutine.condition;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep;
import de.heinerkuecker.coroutine.HasArgumentsAndVariables;
import de.heinerkuecker.coroutine.expression.GetProcedureArgument;
import de.heinerkuecker.coroutine.step.CoroIterStep;

/**
 * And {@link Condition}.
 *
 * @author Heiner K&uuml;cker
 */
public class And
implements Condition/*<CoroutineIterator<?>>*/
{
    private final ConditionOrBooleanExpression/*Condition/*<CoroutineIterator<?>>*/[] conditionsToAnd;

    /**
     * Constructor.
     */
    @SafeVarargs
    public And(
            final ConditionOrBooleanExpression/*Condition/*<CoroutineIterator<?>>*/... conditionsToAnd )
    {
        this.conditionsToAnd = conditionsToAnd;
    }

    /**
     * Negates the specified {@link Condition}.
     */
    @Override
    public boolean execute(
            final HasArgumentsAndVariables/*CoroutineOrProcedureOrComplexstep<?>*/ parent )
    {
        for ( final ConditionOrBooleanExpression/*Condition/*<CoroutineIterator<?>>*/ condition : conditionsToAnd )
        {
            if ( ! condition.execute( parent ) )
            {
                return false;
            }
        }
        return true;
    }

    /**
     * @see CoroIterStep#getProcedureArgumentGetsNotInProcedure()
     */
    @Override
    public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    {
        final List<GetProcedureArgument<?>> result = new ArrayList<>();

        for ( final ConditionOrBooleanExpression/*Condition<CoroutineIterator<?>>*/ condition : conditionsToAnd )
        {
            result.addAll(
                    condition.getProcedureArgumentGetsNotInProcedure() );
        }

        return result;
    }

    @Override
    public void checkUseVariables(
            final boolean isCoroutineRoot ,
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstep<?> parent ,
            final Map<String, Class<?>> globalVariableTypes, final Map<String, Class<?>> localVariableTypes )
    {
        for ( final ConditionOrBooleanExpression/*Condition<CoroutineIterator<?>>*/ condition : conditionsToAnd )
        {
            condition.checkUseVariables(
                    isCoroutineRoot ,
                    alreadyCheckedProcedureNames ,
                    parent ,
                    globalVariableTypes, localVariableTypes );
        }
    }

    @Override
    public void checkUseArguments(
            HashSet<String> alreadyCheckedProcedureNames, final CoroutineOrProcedureOrComplexstep<?> parent )
    {
        for ( final ConditionOrBooleanExpression/*Condition<CoroutineIterator<?>>*/ condition : conditionsToAnd )
        {
            condition.checkUseArguments( alreadyCheckedProcedureNames, parent );
        }
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        final StringBuilder buff = new StringBuilder();

        for ( final ConditionOrBooleanExpression/*Condition<CoroutineIterator<?>>*/ condition : conditionsToAnd )
        {
            if ( buff.length() > 0 )
            {
                buff.append( " && " );
            }
            buff.append( condition );
        }

        return "! ( " + buff + " )";
    }

}
