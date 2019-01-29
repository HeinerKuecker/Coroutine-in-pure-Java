package de.heinerkuecker.coroutine.exprs.bool;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep;
import de.heinerkuecker.coroutine.HasArgumentsAndVariables;
import de.heinerkuecker.coroutine.exprs.CoroExpression;
import de.heinerkuecker.coroutine.exprs.GetProcedureArgument;
import de.heinerkuecker.coroutine.stmt.CoroIterStep;

public class Not
//implements Condition/*<CoroutineIterator<?>>*/
extends CoroBooleanExpression
{
    /**
     * Expression to negate.
     */
    //public final ConditionOrBooleanExpression/*Condition/*<CoroutineIterator<?>>*/ conditionToNegate;
    public final CoroExpression<Boolean> conditionToNegate;

    /**
     * Constructor.
     */
    public Not(
            //final ConditionOrBooleanExpression/*Condition/*<CoroutineIterator<?>>*/ conditionToNegate
            final CoroExpression<Boolean> conditionToNegate )
    {
        this.conditionToNegate = Objects.requireNonNull( conditionToNegate );
    }

    ///**
    // * Constructor.
    // */
    //public Not(
    //        final CoroExpression<Boolean> conditionToNegate )
    //{
    //    this.conditionToNegate =
    //            new IsTrue(
    //                    conditionToNegate );
    //}

    ///**
    // * Negates the specified condition.
    // */
    //@Override
    //public boolean execute(
    //        final HasArgumentsAndVariables<?>/*CoroutineOrProcedureOrComplexstep<?, ?>*/ parent )
    //{
    //    return ! conditionToNegate.execute( parent );
    //}

    /**
     * Negates the specified condition.
     */
    @Override
    public Boolean evaluate(
            final HasArgumentsAndVariables<?> parent )
    {
        return ! conditionToNegate.evaluate( parent );
    }

    /**
     * @see CoroIterStep#getProcedureArgumentGetsNotInProcedure()
     */
    @Override
    public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    {
        return this.conditionToNegate.getProcedureArgumentGetsNotInProcedure();
    }

    @Override
    public void checkUseVariables(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstep<?, ?> parent ,
            final Map<String, Class<?>> globalVariableTypes, final Map<String, Class<?>> localVariableTypes )
    {
        this.conditionToNegate.checkUseVariables(
                alreadyCheckedProcedureNames ,
                parent ,
                globalVariableTypes, localVariableTypes );
    }

    @Override
    public void checkUseArguments(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstep<?, ?> parent )
    {
        this.conditionToNegate.checkUseArguments( alreadyCheckedProcedureNames , parent );
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        //return "Not [conditionToNegate=" + this.conditionToNegate + "]";
        return "( ! " + this.conditionToNegate + " )";
    }

}
