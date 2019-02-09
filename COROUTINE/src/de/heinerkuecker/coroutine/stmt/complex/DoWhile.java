package de.heinerkuecker.coroutine.stmt.complex;

import de.heinerkuecker.coroutine.CoroutineOrFunctioncallOrComplexstmt;
import de.heinerkuecker.coroutine.exprs.CoroExpression;
import de.heinerkuecker.coroutine.exprs.Value;
import de.heinerkuecker.coroutine.stmt.CoroStmt;

public class DoWhile<
    FUNCTION_RETURN ,
    COROUTINE_RETURN /*, PARENT extends CoroutineIterator<COROUTINE_RETURN>*/ ,
    RESUME_ARGUMENT
    >
extends WhileOrDoWhile<
    DoWhile<FUNCTION_RETURN , COROUTINE_RETURN /*, PARENT*/ , RESUME_ARGUMENT> ,
    DoWhileState<FUNCTION_RETURN , COROUTINE_RETURN /*, PARENT*/ , RESUME_ARGUMENT> ,
    FUNCTION_RETURN ,
    COROUTINE_RETURN ,
    //PARENT
    RESUME_ARGUMENT
    >
{
    /**
     * Constructor.
     *
     * @param condition
     * @param statements
     */
    @SafeVarargs
    public DoWhile(
            final CoroExpression<Boolean , COROUTINE_RETURN> condition ,
            final CoroStmt<FUNCTION_RETURN , ? extends COROUTINE_RETURN /*, PARENT*/>... stmts )
    {
        super(
                //label
                null ,
                condition ,
                stmts );
    }

    ///**
    // * Constructor.
    // *
    // * @param condition
    // * @param statements
    // */
    //@SafeVarargs
    //public DoWhile(
    //        final CoroExpression<Boolean> condition ,
    //        final CoroIterStmt<? extends COROUTINE_RETURN /*, PARENT*/>... stmts )
    //{
    //    super(
    //            //label
    //            null ,
    //            new IsTrue(
    //                    condition ) ,
    //            stmts );
    //}

    /**
     * Constructor.
     *
     * @param condition
     * @param statements
     */
    @SafeVarargs
    public DoWhile(
            final boolean condition ,
            final CoroStmt<FUNCTION_RETURN , ? extends COROUTINE_RETURN /*, PARENT*/>... stmts )
    {
        super(
                //label
                null ,
                //new IsTrue(
                        Value.booleanValue(
                                condition ) ,
                stmts );
    }

    /**
     * Constructor.
     *
     * @param label
     * @param condition
     * @param statements
     */
    @SafeVarargs
    public DoWhile(
            final String label ,
            final CoroExpression<Boolean , COROUTINE_RETURN> condition ,
            final CoroStmt<FUNCTION_RETURN , ? extends COROUTINE_RETURN /*, PARENT*/>... stmts )
    {
        super(
                label ,
                condition ,
                stmts );
    }

    ///**
    // * Constructor.
    // *
    // * @param label
    // * @param condition
    // * @param stmts
    // */
    //@SafeVarargs
    //public DoWhile(
    //        final String label ,
    //        final CoroExpression<Boolean> condition ,
    //        final CoroIterStmt<? extends COROUTINE_RETURN /*, PARENT*/>... stmts )
    //{
    //    super(
    //            label ,
    //            new IsTrue(
    //                    condition ) ,
    //            stmts );
    //}

    /**
     * Constructor.
     *
     * @param label
     * @param condition
     * @param stmts
     */
    @SafeVarargs
    public DoWhile(
            final String label ,
            final Boolean condition ,
            final CoroStmt<FUNCTION_RETURN , ? extends COROUTINE_RETURN /*, PARENT*/>... stmts )
    {
        super(
                label ,
                //new IsTrue(
                        Value.booleanValue(
                                condition ) ,
                stmts );
    }

    @Override
    public DoWhileState<FUNCTION_RETURN , COROUTINE_RETURN /*, PARENT*/ , RESUME_ARGUMENT> newState(
            final CoroutineOrFunctioncallOrComplexstmt<FUNCTION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT> parent )
    {
        return new DoWhileState<>(
                this ,
                //parent.getRootParent()
                parent );
    }

}
