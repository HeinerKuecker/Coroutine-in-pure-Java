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
import de.heinerkuecker.coroutine.exprs.Value;

/**
 * Equals condition
 * to check equality of
 * the results of two given
 * {@link CoroExpression}.
 *
 * @param <T> type of expression results to compare
 * @author Heiner K&uuml;cker
 */
public class Equals<T>
//implements ConditionOrBooleanExpression
extends CoroBooleanExpression
{
    public final CoroExpression<? extends T> lhs;
    public final CoroExpression<? extends T> rhs;

    /**
     * Constructor.
     *
     * @param lhs
     * @param rhs
     */
    public Equals(
            final CoroExpression<? extends T> lhs ,
            final CoroExpression<? extends T> rhs )
    {
        this.lhs = Objects.requireNonNull( lhs );
        this.rhs = Objects.requireNonNull( rhs );
    }

    /**
     * Constructor.
     *
     * @param lhs
     * @param rhs
     */
    public Equals(
            final T lhsValue ,
            final CoroExpression<? extends T> rhs )
    {
        this.lhs =
                new Value<T>(
                        (Class<? extends T>) Object.class ,
                lhsValue );

        this.rhs = Objects.requireNonNull( rhs );
    }

    /**
     * Constructor.
     *
     * @param lhs
     * @param rhs
     */
    public Equals(
            final CoroExpression<? extends T> lhs ,
            final T rhsValue )
    {
        this.lhs = Objects.requireNonNull( lhs );
        this.rhs =
                new Value<T>(
                        (Class<? extends T>) Object.class ,
                        rhsValue );
    }

    /**
     * Constructor.
     *
     * @param lhs
     * @param rhs
     */
    public Equals(
            final T lhsValue ,
            final T rhsValue )
    {
        this.lhs =
                new Value<T>(
                        (Class<? extends T>) Object.class ,
                        lhsValue );

        this.rhs =
                new Value<T>(
                        (Class<? extends T>) Object.class ,
                        rhsValue );
    }

    //@Override
    //public boolean execute(
    //        final HasArgumentsAndVariables<?>/*CoroutineOrProcedureOrComplexstmt<?, ?>*/ parent )
    //{
    //    final T lhsResult = lhs.evaluate( parent );
    //    final T rhsResult = rhs.evaluate( parent );
    //
    //    if ( lhsResult == null && rhsResult == null )
    //    {
    //        return true;
    //    }
    //
    //    if ( lhsResult == null )
    //    {
    //        return false;
    //    }
    //
    //    return lhsResult.equals( rhsResult );
    //}

    @Override
    public Boolean evaluate(
            final HasArgumentsAndVariables<?> parent )
    {
        final T lhsResult = lhs.evaluate( parent );
        final T rhsResult = rhs.evaluate( parent );

        if ( lhsResult == null && rhsResult == null )
        {
            return true;
        }

        if ( lhsResult == null )
        {
            return false;
        }

        return lhsResult.equals( rhsResult );
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
        return "( " + lhs + " == " + rhs + " )";
    }

}
