package de.heinerkuecker.coroutine.expression;

import de.heinerkuecker.coroutine.CoroCheckable;
import de.heinerkuecker.coroutine.HasArgumentsAndVariables;

public interface CoroExpression<T>
extends CoroCheckable
{

    T evaluate(
            final HasArgumentsAndVariables<?> parent );

    //abstract public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure();

    /**
     * Get type for checks.
     *
     * @return array of alternative types
     */
    Class<? extends T>[] type();

}
