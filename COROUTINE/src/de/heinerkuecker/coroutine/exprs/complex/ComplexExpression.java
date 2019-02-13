package de.heinerkuecker.coroutine.exprs.complex;

import de.heinerkuecker.coroutine.stmt.complex.ComplexStmt;

/**
 * TODO
 *
 * @author Heiner K&uuml;cker
 */
public abstract class ComplexExpression<
    COMPLEX_EXPRESSION extends ComplexExpression<COMPLEX_EXPRESSION, COMPLEX_EXPRESSION_STATE, FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT*/, RESUME_ARGUMENT>,
    COMPLEX_EXPRESSION_STATE extends ComplexExpressionState<COMPLEX_EXPRESSION_STATE, COMPLEX_EXPRESSION, FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT*/, RESUME_ARGUMENT>,
    FUNCTION_RETURN ,
    COROUTINE_RETURN ,
    //PARENT extends CoroutineOrFunctioncallOrComplexstmt<COROUTINE_RETURN/*, PARENT*/>
    RESUME_ARGUMENT
    >
extends ComplexStmt<COMPLEX_EXPRESSION , COMPLEX_EXPRESSION_STATE , FUNCTION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT>
{
    /**
     * Constructor.
     *
     * @param creationStackOffset
     */
    protected ComplexExpression(
            final int creationStackOffset )
    {
        super( creationStackOffset +  1 );
    }

}
