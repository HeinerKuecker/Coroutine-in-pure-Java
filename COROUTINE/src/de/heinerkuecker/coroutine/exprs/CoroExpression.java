package de.heinerkuecker.coroutine.exprs;

import java.util.HashSet;

import de.heinerkuecker.coroutine.CoroCheckable;
import de.heinerkuecker.coroutine.CoroutineOrFunctioncallOrComplexstmt;

/**
 * TODO
 *
 * @author Heiner K&uuml;cker
 */
public interface CoroExpression<EXPRESSION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT>
extends CoroCheckable
{
    /**
     * Get array of possible return types for checks.
     *
     * @return array of alternative types
     */
    Class<? extends EXPRESSION_RETURN>[] type();

    ///**
    // * Set reifier for type param {@link #COROUTINE_RETURN} to solve unchecked casts.
    // */
    //abstract public void setExprCoroutineReturnType(
    //        final HashSet<String> alreadyCheckedFunctionNames ,
    //        final CoroutineOrFunctioncallOrComplexstmt<?, ? , ?> parent ,
    //        final Class<? /*extends COROUTINE_RETURN*/> coroutineReturnType );

    /**
     * Set reifier for type param {@link #COROUTINE_RETURN} and {@link #RESUME_ARGUMENT} to solve unchecked casts.
     */
    abstract public void setExprCoroutineReturnTypeAndResumeArgumentType(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ? , ?> parent ,
            //final Class<? /*extends COROUTINE_RETURN*/> coroutineReturnType ,
            final Class<? extends COROUTINE_RETURN> coroutineReturnType ,
            final Class<? extends RESUME_ARGUMENT> resumeArgumentType );
}
