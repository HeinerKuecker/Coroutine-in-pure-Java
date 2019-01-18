package de.heinerkuecker.coroutine.expression;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep;
import de.heinerkuecker.coroutine.HasArgumentsAndVariables;
import de.heinerkuecker.coroutine.condition.ConditionOrBooleanExpression;

public interface CoroBooleanExpression
extends
    CoroExpression<Boolean> ,
    ConditionOrBooleanExpression
{
    /**
     * Execute the condition and return the result.
     *
     * @param parent the {@link CoroutineOrProcedureOrComplexstep} instance
     * @return condition result
     */
    @Override
    boolean execute(
            final HasArgumentsAndVariables/*CoroutineOrProcedureOrComplexstep<?>*/ parent );

}
