package de.heinerkuecker.coroutine.stmt.complex;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstmt;
import de.heinerkuecker.coroutine.exprs.CoroExpression;
import de.heinerkuecker.coroutine.exprs.Value;
import de.heinerkuecker.coroutine.stmt.CoroIterStmt;

public class While<COROUTINE_RETURN /*, PARENT extends CoroutineIterator<COROUTINE_RETURN>*/, RESUME_ARGUMENT>
extends WhileOrDoWhile<
    While<COROUTINE_RETURN/*, PARENT*/, RESUME_ARGUMENT>,
    WhileState<COROUTINE_RETURN/*, PARENT*/, RESUME_ARGUMENT>,
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
            //final ConditionOrBooleanExpression/*Condition*/ condition
            final CoroExpression<Boolean> condition ,
            final CoroIterStmt<? extends COROUTINE_RETURN/*, PARENT*/>... stmts )
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
    //        final CoroExpression<Boolean> condition ,
    //        final CoroIterStep<? extends COROUTINE_RETURN/*, PARENT*/>... stmts )
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
            final CoroIterStmt<? extends COROUTINE_RETURN/*, PARENT*/>... stmts )
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
            //final ConditionOrBooleanExpression/*Condition*/ condition
            final CoroExpression<Boolean> condition ,
            final CoroIterStmt<? extends COROUTINE_RETURN/*, PARENT*/>... stmts )
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
    //        final CoroExpression<Boolean> condition ,
    //        final CoroIterStep<? extends COROUTINE_RETURN/*, PARENT*/>... stmts )
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
            final CoroIterStmt<? extends COROUTINE_RETURN/*, PARENT*/>... stmts )
    {
        super(
                label ,
                //new IsTrue(
                        Value.booleanValue(
                                condition ) ,
                stmts );
    }

    @Override
    public WhileState<COROUTINE_RETURN/*, PARENT*/, RESUME_ARGUMENT> newState(
            final CoroutineOrProcedureOrComplexstmt<COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        return new WhileState<>(
                this ,
                //parent.getRootParent()
                parent );
    }

}
