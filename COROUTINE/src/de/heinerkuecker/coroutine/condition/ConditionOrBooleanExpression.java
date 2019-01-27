package de.heinerkuecker.coroutine.condition;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep;
import de.heinerkuecker.coroutine.HasArgumentsAndVariables;
import de.heinerkuecker.coroutine.expression.CoroExpression;
import javafx.beans.binding.BooleanExpression;

/**
 * Common interface for
 * {@link Condition} and
 * {@link BooleanExpression}.
 *
 * @author Heiner K&uuml;cker
 */
public interface ConditionOrBooleanExpression
extends CoroExpression<Boolean>
{
    /**
     * Execute the condition and return the result.
     *
     * @param parent the {@link CoroutineOrProcedureOrComplexstep} instance
     * @return condition result
     */
    boolean execute(
            final HasArgumentsAndVariables<?>/*CoroutineOrProcedureOrComplexstep<?, ?>*/ parent );

    /**
     * Evaluates expression.
     *
     * Using of Java8 default method,
     * for using older Java versions
     * copy this method implementation
     * in all sub classes.
     */
    @Override
    default public Boolean evaluate(
            final HasArgumentsAndVariables<?>/*CoroutineOrProcedureOrComplexstep<?, ?>*/ parent )
    {
        return execute( parent );
    }

    //abstract public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure();

    /**
     * Get type.
     *
     * Using of Java8 default method,
     * for using older Java versions
     * copy this method implementation
     * in all sub classes.
     */
    @SuppressWarnings("unchecked")
    @Override
    default Class<? extends Boolean>[] type()
    {
        //return Boolean.class;
        return new Class[] { Boolean.class };
    }
}
