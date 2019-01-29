package de.heinerkuecker.coroutine.exprs.bool;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep;
import de.heinerkuecker.coroutine.HasArgumentsAndVariables;
import de.heinerkuecker.coroutine.exprs.CoroExpression;
import de.heinerkuecker.coroutine.exprs.GetProcedureArgument;

/**
 * Or condition.
 *
 * @author Heiner K&uuml;cker
 */
public class Or
//implements Condition/*<CoroutineIterator<?>>*/
extends CoroBooleanExpression
{
    //private final ConditionOrBooleanExpression/*Condition<CoroutineIterator<?>>*/[] conditionsToOr;
    private final CoroExpression<Boolean>[] conditionsToOr;

    /**
     * Constructor.
     */
    @SafeVarargs
    public Or(
            //final ConditionOrBooleanExpression/*Condition/*<CoroutineIterator<?>>*/... conditionsToOr
            final CoroExpression<Boolean>... conditionsToOr )
    {
        this.conditionsToOr = conditionsToOr;
    }

    //@Override
    //public boolean execute(
    //        final HasArgumentsAndVariables<?>/*CoroutineOrProcedureOrComplexstep<?, ?>*/ parent )
    //{
    //    for ( final ConditionOrBooleanExpression/*Condition<CoroutineIterator<?>>*/ condition : conditionsToOr )
    //    {
    //        if ( condition.execute( parent ) )
    //        {
    //            return true;
    //        }
    //    }
    //    return false;
    //}

    @Override
    public Boolean evaluate(
            final HasArgumentsAndVariables<?> parent )
    {
        for ( final CoroExpression<Boolean> condition : conditionsToOr )
        {
            if ( condition.evaluate( parent ) )
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    {
        final List<GetProcedureArgument<?>> result = new ArrayList<>();

        //for ( final ConditionOrBooleanExpression/*Condition<CoroutineIterator<?>>*/ condition : conditionsToOr )
        for ( final CoroExpression<?> condition : conditionsToOr )
        {
            result.addAll(
                    condition.getProcedureArgumentGetsNotInProcedure() );
        }

        return result;
    }

    @Override
    public void checkUseVariables(
            //final boolean isCoroutineRoot ,
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstep<?, ?> parent ,
            final Map<String, Class<?>> globalVariableTypes ,
            final Map<String, Class<?>> localVariableTypes )
    {
        //for ( final ConditionOrBooleanExpression/*Condition<CoroutineIterator<?>>*/ condition : conditionsToOr )
        for ( final CoroExpression<?> condition : conditionsToOr )
        {
            condition.checkUseVariables(
                    //isCoroutineRoot ,
                    alreadyCheckedProcedureNames ,
                    parent ,
                    globalVariableTypes, localVariableTypes );
        }
    }

    @Override
    public void checkUseArguments(
            HashSet<String> alreadyCheckedProcedureNames, final CoroutineOrProcedureOrComplexstep<?, ?> parent )
    {
        //for ( final ConditionOrBooleanExpression/*Condition<CoroutineIterator<?>>*/ condition : conditionsToOr )
        for ( final CoroExpression<?> condition : conditionsToOr )
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

        //for ( final ConditionOrBooleanExpression/*Condition<CoroutineIterator<?>>*/ condition : conditionsToOr )
        for ( final CoroExpression<?> condition : conditionsToOr )
        {
            if ( buff.length() > 0 )
            {
                buff.append( " || " );
            }
            buff.append( condition );
        }

        return "( " + buff + " )";
    }

}
