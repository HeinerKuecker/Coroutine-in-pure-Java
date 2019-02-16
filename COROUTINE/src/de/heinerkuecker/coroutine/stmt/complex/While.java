package de.heinerkuecker.coroutine.stmt.complex;

import de.heinerkuecker.coroutine.CoroutineOrFunctioncallOrComplexstmt;
import de.heinerkuecker.coroutine.exprs.SimpleExpression;
import de.heinerkuecker.coroutine.exprs.Value;
import de.heinerkuecker.coroutine.exprs.complex.ComplexExpression;
import de.heinerkuecker.coroutine.stmt.CoroStmt;

public class While<FUNCTION_RETURN , COROUTINE_RETURN /*, PARENT extends CoroutineIterator<COROUTINE_RETURN>*/, RESUME_ARGUMENT>
extends WhileOrDoWhile<
    While<FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT*/, RESUME_ARGUMENT> ,
    WhileState<FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT*/, RESUME_ARGUMENT> ,
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
     * @param stmts
     */
    @SafeVarargs
    public While(
            final ComplexExpression<? , ? , Boolean , COROUTINE_RETURN , RESUME_ARGUMENT> condition ,
            final CoroStmt<FUNCTION_RETURN , ? extends COROUTINE_RETURN/*, PARENT*/>... stmts )
    {
        super(
                //label
                null ,
                condition ,
                stmts );
    }

    /**
     * Constructor.
     *
     * @param condition
     * @param stmts
     */
    @SafeVarargs
    public While(
            final SimpleExpression<Boolean , COROUTINE_RETURN , RESUME_ARGUMENT> condition ,
            final CoroStmt<FUNCTION_RETURN , ? extends COROUTINE_RETURN/*, PARENT*/>... stmts )
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
    // * @param stmts
    // */
    //@SafeVarargs
    //public While(
    //        final SimpleExpression<Boolean> condition ,
    //        final CoroIterStmt<? extends COROUTINE_RETURN/*, PARENT*/>... stmts )
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
     * @param stmts
     */
    @SafeVarargs
    public While(
            final boolean condition ,
            final CoroStmt<FUNCTION_RETURN , ? extends COROUTINE_RETURN/*, PARENT*/>... stmts )
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
     * @param stmts
     */
    @SafeVarargs
    public While(
            final String label ,
            final SimpleExpression<Boolean , COROUTINE_RETURN , RESUME_ARGUMENT> condition ,
            final CoroStmt<FUNCTION_RETURN , ? extends COROUTINE_RETURN/*, PARENT*/>... stmts )
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
    //public While(
    //        final String label ,
    //        final SimpleExpression<Boolean> condition ,
    //        final CoroIterStmt<? extends COROUTINE_RETURN/*, PARENT*/>... stmts )
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
    public While(
            final String label ,
            final boolean condition ,
            final CoroStmt<FUNCTION_RETURN , ? extends COROUTINE_RETURN/*, PARENT*/>... stmts )
    {
        super(
                label ,
                //new IsTrue(
                        Value.booleanValue(
                                condition ) ,
                stmts );
    }

    @Override
    public WhileState<FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT*/, RESUME_ARGUMENT> newState(
            final CoroutineOrFunctioncallOrComplexstmt<FUNCTION_RETURN , COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        return new WhileState<>(
                this ,
                //parent.getRootParent()
                parent );
    }

}
