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
     * @see Condition#execute
     */
    @Override
    public boolean execute(
            final HasArgumentsAndVariables/*CoroutineOrProcedureOrComplexstep<?>*/ parent )
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

    @Override
    public void checkUseVariables(
            final boolean isCoroutineRoot ,
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstep<?> parent ,
            final Map<String, Class<?>> globalVariableTypes ,
            final Map<String, Class<?>> localVariableTypes )
    {
        this.lhs.checkUseVariables(
                isCoroutineRoot ,
                alreadyCheckedProcedureNames ,
                parent ,
                globalVariableTypes, localVariableTypes );

        this.rhs.checkUseVariables(
                isCoroutineRoot ,
                alreadyCheckedProcedureNames ,
                parent ,
                globalVariableTypes, localVariableTypes );
    }

    @Override
    public void checkUseArguments(
            HashSet<String> alreadyCheckedProcedureNames, final CoroutineOrProcedureOrComplexstep<?> parent )
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
        return lhs + " != " + rhs;
    }

}
