package de.heinerkuecker.coroutine.step.complex;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep;
import de.heinerkuecker.coroutine.condition.ConditionOrBooleanExpression;
import de.heinerkuecker.coroutine.condition.IsTrue;
import de.heinerkuecker.coroutine.exprs.CoroExpression;
import de.heinerkuecker.coroutine.exprs.Value;
import de.heinerkuecker.coroutine.step.CoroIterStep;

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
            final ConditionOrBooleanExpression/*Condition/*<? super PARENT>*/ condition ,
            final CoroIterStep<? extends COROUTINE_RETURN /*, PARENT*/>... steps )
    {
        super(
                //label
                null ,
                condition ,
                steps );
    }

    /**
     * Constructor.
     *
     * @param condition
     * @param steps
     */
    @SafeVarargs
    public DoWhile(
            final CoroExpression<Boolean> condition ,
            final CoroIterStep<? extends COROUTINE_RETURN /*, PARENT*/>... steps )
    {
        super(
                //label
                null ,
                new IsTrue(
                        condition ) ,
                steps );
    }

    /**
     * Constructor.
     *
     * @param condition
     * @param steps
     */
    @SafeVarargs
    public DoWhile(
            final boolean condition ,
            final CoroIterStep<? extends COROUTINE_RETURN /*, PARENT*/>... steps )
    {
        super(
                //label
                null ,
                new IsTrue(
                        Value.booleanValue(
                                condition ) ) ,
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
            final ConditionOrBooleanExpression/*Condition/*<? super PARENT>*/ condition ,
            final CoroIterStep<? extends COROUTINE_RETURN /*, PARENT*/>... steps )
    {
        super(
                label ,
                condition ,
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
            final CoroExpression<Boolean> condition ,
            final CoroIterStep<? extends COROUTINE_RETURN /*, PARENT*/>... steps )
    {
        super(
                label ,
                new IsTrue(
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
            final Boolean condition ,
            final CoroIterStep<? extends COROUTINE_RETURN /*, PARENT*/>... steps )
    {
        super(
                label ,
                new IsTrue(
                        Value.booleanValue(
                                condition ) ) ,
                steps );
    }

    /**
     * @see ComplexStep#newState()
     */
    @Override
    public DoWhileState<COROUTINE_RETURN /*, PARENT*/ , RESUME_ARGUMENT> newState(
            final CoroutineOrProcedureOrComplexstep<COROUTINE_RETURN , RESUME_ARGUMENT> parent )
    {
        return new DoWhileState<>(
                this ,
                //parent.getRootParent()
                parent );
    }

}
