package de.heinerkuecker.coroutine.exprs;

import de.heinerkuecker.coroutine.CoroCheckable;
import de.heinerkuecker.coroutine.HasArgumentsAndVariables;

public interface CoroExpression<T>
extends CoroCheckable
{
    /**
     * Evaluate (compute) result value.
     *
     * @param parent coroutine or function or complex statement
     * @return result value
     */
    T evaluate(
            final HasArgumentsAndVariables<?> parent );

    /**
     * Get type for checks.
     *
     * @return array of alternative types
     */
    Class<? extends T>[] type();

}
