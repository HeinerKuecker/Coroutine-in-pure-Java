package de.heinerkuecker.coroutine.exprs;

import de.heinerkuecker.coroutine.HasArgumentsAndVariables;

/**
 * TODO
 *
 * @author Heiner K&uuml;cker
 */
public interface SimpleExpression<EXPRESSION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT>
extends CoroExpression<EXPRESSION_RETURN , COROUTINE_RETURN>
{
    /**
     * Evaluate (compute) result value.
     *
     * @param parent coroutine or function or complex statement
     * @return result value
     */
    EXPRESSION_RETURN evaluate(
            final HasArgumentsAndVariables<? extends RESUME_ARGUMENT> parent );
}
