package de.heinerkuecker.coroutine.exprs.complex;

import de.heinerkuecker.coroutine.CoroutineOrFunctioncallOrComplexstmt;
import de.heinerkuecker.coroutine.stmt.complex.ComplexStmtState;

/**
 * TODO
 *
 * @author Heiner K&uuml;cker
 */
abstract public class ComplexExpressionState<
COMPLEX_EXPRESSION_STATE extends ComplexExpressionState<COMPLEX_EXPRESSION_STATE , COMPLEX_EXPRESSION , FUNCTION_RETURN , COROUTINE_RETURN /*, PARENT*/, RESUME_ARGUMENT>,
COMPLEX_EXPRESSION extends ComplexExpression<COMPLEX_EXPRESSION, COMPLEX_EXPRESSION_STATE, FUNCTION_RETURN , COROUTINE_RETURN /*, PARENT*/, RESUME_ARGUMENT>,
FUNCTION_RETURN ,
COROUTINE_RETURN ,
//PARENT extends CoroutineOrFunctioncallOrComplexstmt<COROUTINE_RETURN, RESUME_ARGUMENT>
RESUME_ARGUMENT
>
extends ComplexStmtState<COMPLEX_EXPRESSION_STATE , COMPLEX_EXPRESSION , FUNCTION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT>
{
    /**
     * Constructor.
     *
     * @param parent
     */
    protected ComplexExpressionState(
            final CoroutineOrFunctioncallOrComplexstmt<FUNCTION_RETURN, COROUTINE_RETURN, RESUME_ARGUMENT> parent)
    {
        super( parent );
    }

}
