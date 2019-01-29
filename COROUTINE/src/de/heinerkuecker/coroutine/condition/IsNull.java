package de.heinerkuecker.coroutine.condition;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep;
import de.heinerkuecker.coroutine.HasArgumentsAndVariables;
import de.heinerkuecker.coroutine.exprs.CoroExpression;
import de.heinerkuecker.coroutine.exprs.GetProcedureArgument;
import de.heinerkuecker.coroutine.stmt.CoroIterStep;

/**
 * Is <code>null</code> {@link Condition}
 * to check nullness of the result
 * of the specified
 * expression {@link CoroExpression}.
 *
 * @author Heiner K&uuml;cker
 */
public class IsNull
implements ConditionOrBooleanExpression/*Condition/*<CoroutineIterator<?>>*/
{
    /**
     * Expression to check.
     */
    public final CoroExpression<?> expression;

    /**
     * Constructor.
     */
    public IsNull(
            final CoroExpression<?> expression )
    {
        this.expression = expression;
    }

    /**
     * Equals variable to <code>null</code>.
     *
     * @see Condition#execute
     */
    @Override
    public boolean execute(
            final HasArgumentsAndVariables<?>/*CoroutineOrProcedureOrComplexstep<?, ?>*/ parent )
    {
        //final Object varValue = parent.localVars().get( varName );
        final Object varValue = expression.evaluate( parent );

        return varValue == null;
    }

    /**
     * @see CoroIterStep#getProcedureArgumentGetsNotInProcedure()
     */
    @Override
    public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    {
        return this.expression.getProcedureArgumentGetsNotInProcedure();
    }

    @Override
    public void checkUseVariables(
            //final boolean isCoroutineRoot ,
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstep<?, ?> parent ,
            final Map<String, Class<?>> globalVariableTypes, final Map<String, Class<?>> localVariableTypes )
    {
        this.expression.checkUseVariables(
                //isCoroutineRoot ,
                alreadyCheckedProcedureNames ,
                parent ,
                globalVariableTypes, localVariableTypes );
    }

    @Override
    public void checkUseArguments(
            HashSet<String> alreadyCheckedProcedureNames, final CoroutineOrProcedureOrComplexstep<?, ?> parent )
    {
        this.expression.checkUseArguments( alreadyCheckedProcedureNames, parent );
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return expression + " == null";
    }

}
