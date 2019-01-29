package de.heinerkuecker.coroutine.condition;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep;
import de.heinerkuecker.coroutine.HasArgumentsAndVariables;
import de.heinerkuecker.coroutine.exprs.CoroExpression;
import de.heinerkuecker.coroutine.exprs.GetProcedureArgument;
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
            final HasArgumentsAndVariables<?>/*CoroutineOrProcedureOrComplexstep<?, ?>*/ parent )
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

    @Override
    public void checkUseVariables(
            //final boolean isCoroutineRoot ,
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstep<?, ?> parent ,
            final Map<String, Class<?>> globalVariableTypes, final Map<String, Class<?>> localVariableTypes )
    {
        this.conditionToNegate.checkUseVariables(
                //isCoroutineRoot ,
                alreadyCheckedProcedureNames ,
                parent ,
                globalVariableTypes, localVariableTypes );
    }

    @Override
    public void checkUseArguments(
            HashSet<String> alreadyCheckedProcedureNames, final CoroutineOrProcedureOrComplexstep<?, ?> parent )
    {
        this.conditionToNegate.checkUseArguments( alreadyCheckedProcedureNames, parent );
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
