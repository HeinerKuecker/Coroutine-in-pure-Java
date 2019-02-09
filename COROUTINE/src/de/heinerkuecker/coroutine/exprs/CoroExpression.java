package de.heinerkuecker.coroutine.exprs;

import java.util.HashSet;

import de.heinerkuecker.coroutine.CoroCheckable;
import de.heinerkuecker.coroutine.CoroutineOrFunctioncallOrComplexstmt;
import de.heinerkuecker.coroutine.HasArgumentsAndVariables;

public interface CoroExpression<EXPRESSSION_RETURN , COROUTINE_RETURN>
extends CoroCheckable
{
    /**
     * Evaluate (compute) result value.
     *
     * @param parent coroutine or function or complex statement
     * @return result value
     */
    EXPRESSSION_RETURN evaluate(
            final HasArgumentsAndVariables<?> parent );

    /**
     * Get type for checks.
     *
     * @return array of alternative types
     */
    Class<? extends EXPRESSSION_RETURN>[] type();


    /**
     * Set reifier for type param {@link #COROUTINE_RETURN} to solve unchecked casts.
     */
    abstract public void setExprCoroutineReturnType(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ? , ?> parent ,
            final Class<? /*extends COROUTINE_RETURN*/> coroutineReturnType );
}
