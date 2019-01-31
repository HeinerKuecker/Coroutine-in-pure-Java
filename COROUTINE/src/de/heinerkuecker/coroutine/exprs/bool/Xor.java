package de.heinerkuecker.coroutine.exprs.bool;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstmt;
import de.heinerkuecker.coroutine.HasArgumentsAndVariables;
import de.heinerkuecker.coroutine.exprs.CoroExpression;
import de.heinerkuecker.coroutine.exprs.GetProcedureArgument;

/**
 * Xor condition.
 *
 * @author Heiner K&uuml;cker
 */
public class Xor
//implements ConditionOrBooleanExpression/*Condition/*<CoroutineIterator<?>>*/
extends CoroBooleanExpression
{
    //private final ConditionOrBooleanExpression/*Condition/*<CoroutineIterator<?>>*/ lhs;
    private final CoroExpression<Boolean> lhs;
    //private final ConditionOrBooleanExpression/*Condition/*<CoroutineIterator<?>>*/ rhs;
    private final CoroExpression<Boolean> rhs;

    /**
     * Constructor.
     */
    public Xor(
            //final ConditionOrBooleanExpression/*Condition/*<CoroutineIterator<?>>*/ lhs ,
            final CoroExpression<Boolean> lhs ,
            //final ConditionOrBooleanExpression/*Condition/*<CoroutineIterator<?>>*/ rhs
            final CoroExpression<Boolean> rhs )
    {
        this.lhs = Objects.requireNonNull( lhs );
        this.rhs = Objects.requireNonNull( rhs );
    }

    ///**
    // * Xors the specified {@link Condition}s.
    // *
    // * @see Condition#execute
    // */
    //@Override
    //public boolean execute(
    //        final HasArgumentsAndVariables<?>/*CoroutineOrProcedureOrComplexstmt<?, ?>*/ parent )
    //{
    //    return lhs.execute( parent ) != rhs.execute( parent );
    //}

    /**
     * Xors the specified conditions.
     */
    @Override
    public Boolean evaluate(
            final HasArgumentsAndVariables<?> parent )
    {
        return lhs.evaluate( parent ) != rhs.evaluate( parent );
    }

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

    @Override
    public void checkUseVariables(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstmt<?, ?> parent ,
            final Map<String, Class<?>> globalVariableTypes ,
            final Map<String, Class<?>> localVariableTypes )
    {
        this.lhs.checkUseVariables(
                alreadyCheckedProcedureNames ,
                parent ,
                globalVariableTypes, localVariableTypes );

        this.rhs.checkUseVariables(
                alreadyCheckedProcedureNames ,
                parent ,
                globalVariableTypes, localVariableTypes );
    }

    @Override
    public void checkUseArguments(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstmt<?, ?> parent )
    {
        this.lhs.checkUseArguments( alreadyCheckedProcedureNames, parent );
        this.rhs.checkUseArguments( alreadyCheckedProcedureNames, parent );
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return "( " + lhs + " xor " + rhs + " )";
    }

}
