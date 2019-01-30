package de.heinerkuecker.coroutine.stmt.complex;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstmt;
import de.heinerkuecker.coroutine.exprs.CoroExpression;
import de.heinerkuecker.coroutine.exprs.Value;
import de.heinerkuecker.coroutine.stmt.CoroIterStmt;

public class DoWhile<COROUTINE_RETURN /*, PARENT extends CoroutineIterator<COROUTINE_RETURN>*/ , RESUME_ARGUMENT>
extends WhileOrDoWhile<
    DoWhile<COROUTINE_RETURN /*, PARENT*/ , RESUME_ARGUMENT>,
    DoWhileState<COROUTINE_RETURN /*, PARENT*/ , RESUME_ARGUMENT>,
    COROUTINE_RETURN ,
    //PARENT
    RESUME_ARGUMENT
    >
{
    /**
     * Constructor.
     *
     * @param condition
     * @param steps
     */
    @SafeVarargs
    public DoWhile(
            //final ConditionOrBooleanExpression/*Condition*/ condition
            final CoroExpression<Boolean> condition ,
            final CoroIterStmt<? extends COROUTINE_RETURN /*, PARENT*/>... steps )
    {
        super(
                //label
                null ,
                condition ,
                steps );
    }

    ///**
    // * Constructor.
    // *
    // * @param condition
    // * @param steps
    // */
    //@SafeVarargs
    //public DoWhile(
    //        final CoroExpression<Boolean> condition ,
    //        final CoroIterStep<? extends COROUTINE_RETURN /*, PARENT*/>... steps )
    //{
    //    super(
    //            //label
    //            null ,
    //            new IsTrue(
    //                    condition ) ,
    //            steps );
    //}

    /**
     * Constructor.
     *
     * @param condition
     * @param steps
     */
    @SafeVarargs
    public DoWhile(
            final boolean condition ,
            final CoroIterStmt<? extends COROUTINE_RETURN /*, PARENT*/>... steps )
    {
        super(
                //label
                null ,
                //new IsTrue(
                        Value.booleanValue(
                                condition ) ,
                steps );
    }

    /**
     * Constructor.
     *
     * @param label
     * @param condition
     * @param steps
     */
    @SafeVarargs
    public DoWhile(
            final String label ,
            //final ConditionOrBooleanExpression/*Condition*/ condition
            final CoroExpression<Boolean> condition ,
            final CoroIterStmt<? extends COROUTINE_RETURN /*, PARENT*/>... steps )
    {
        super(
                label ,
                condition ,
                steps );
    }

    ///**
    // * Constructor.
    // *
    // * @param label
    // * @param condition
    // * @param steps
    // */
    //@SafeVarargs
    //public DoWhile(
    //        final String label ,
    //        final CoroExpression<Boolean> condition ,
    //        final CoroIterStep<? extends COROUTINE_RETURN /*, PARENT*/>... steps )
    //{
    //    super(
    //            label ,
    //            new IsTrue(
    //                    condition ) ,
    //            steps );
    //}

    /**
     * Constructor.
     *
     * @param label
     * @param condition
     * @param steps
     */
    @SafeVarargs
    public DoWhile(
            final String label ,
            final Boolean condition ,
            final CoroIterStmt<? extends COROUTINE_RETURN /*, PARENT*/>... steps )
    {
        super(
                label ,
                //new IsTrue(
                        Value.booleanValue(
                                condition ) ,
                steps );
    }

    @Override
    public DoWhileState<COROUTINE_RETURN /*, PARENT*/ , RESUME_ARGUMENT> newState(
            final CoroutineOrProcedureOrComplexstmt<COROUTINE_RETURN , RESUME_ARGUMENT> parent )
    {
        return new DoWhileState<>(
                this ,
                //parent.getRootParent()
                parent );
    }

}
