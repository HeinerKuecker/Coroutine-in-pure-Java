package de.heinerkuecker.coroutine.step.complex;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep;
import de.heinerkuecker.coroutine.condition.ConditionOrBooleanExpression;
import de.heinerkuecker.coroutine.condition.IsTrue;
import de.heinerkuecker.coroutine.expression.CoroExpression;
import de.heinerkuecker.coroutine.expression.Value;
import de.heinerkuecker.coroutine.step.CoroIterStep;

public class DoWhile<RESULT /*, PARENT extends CoroutineIterator<RESULT>*/ , RESUME_ARGUMENT>
extends WhileOrDoWhile<
    DoWhile<RESULT /*, PARENT*/ , RESUME_ARGUMENT>,
    DoWhileState<RESULT /*, PARENT*/ , RESUME_ARGUMENT>,
    RESULT ,
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
            final CoroIterStep<? extends RESULT /*, PARENT*/>... steps )
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
            final CoroIterStep<? extends RESULT /*, PARENT*/>... steps )
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
            final CoroIterStep<? extends RESULT /*, PARENT*/>... steps )
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
            final CoroIterStep<? extends RESULT /*, PARENT*/>... steps )
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
            final CoroIterStep<? extends RESULT /*, PARENT*/>... steps )
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
            final CoroIterStep<? extends RESULT /*, PARENT*/>... steps )
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
    public DoWhileState<RESULT /*, PARENT*/ , RESUME_ARGUMENT> newState(
            final CoroutineOrProcedureOrComplexstep<RESULT , RESUME_ARGUMENT> parent )
    {
        return new DoWhileState<>(
                this ,
                //parent.getRootParent()
                parent );
    }

}
