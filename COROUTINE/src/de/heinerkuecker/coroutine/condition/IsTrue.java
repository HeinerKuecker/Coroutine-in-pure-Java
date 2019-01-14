package de.heinerkuecker.coroutine.condition;

import java.util.List;
import java.util.Map;

import de.heinerkuecker.coroutine.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine.HasArgumentsAndVariables;
import de.heinerkuecker.coroutine.expression.CoroExpression;
import de.heinerkuecker.coroutine.expression.GetProcedureArgument;
import de.heinerkuecker.coroutine.step.CoroIterStep;

/**
 * Is <code>true</code> {@link Condition}
 * to check trueness of the result
 * of the specified
 * expression {@link CoroExpression}.
 *
 * @author Heiner K&uuml;cker
 *
 * TODO rename to VarIsTrue
 */
public class IsTrue
implements ConditionOrBooleanExpression/*Condition/*<CoroutineIterator<?>>*/
{
    /**
     * Expression to check.
     */
    public final CoroExpression<Boolean> expression;

    /**
     * Constructor.
     */
    public IsTrue(
            final CoroExpression<Boolean> expression )
    {
        this.expression = expression;
    }

    /**
     * Equals variable to <code>true</code>.
     *
     * @see Condition#execute
     */
    @Override
    public boolean execute(
            final HasArgumentsAndVariables/*CoroIteratorOrProcedure<?>*/ parent )
    {
        //final Object varValue = parent.localVars().get( varName );
        final Object varValue = expression.evaluate( parent );

        if ( varValue instanceof Boolean )
        {
            return (boolean) varValue;
        }
        return false;
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
    public void checkUseUndeclaredVariables(
            final CoroIteratorOrProcedure<?> parent ,
            final Map<String, Class<?>> globalVariableTypes ,
            final Map<String, Class<?>> localVariableTypes )
    {
        this.expression.checkUseUndeclaredVariables(
                parent ,
                globalVariableTypes ,
                localVariableTypes );
    }

    @Override
    public void checkUseUndeclaredParameters(
            final CoroIteratorOrProcedure<?> parent )
    {
        this.expression.checkUseUndeclaredParameters( parent );
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return expression + " == true";
    }

}
