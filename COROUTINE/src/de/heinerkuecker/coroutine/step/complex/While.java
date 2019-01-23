package de.heinerkuecker.coroutine.step.complex;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep;
import de.heinerkuecker.coroutine.condition.ConditionOrBooleanExpression;
import de.heinerkuecker.coroutine.condition.IsTrue;
import de.heinerkuecker.coroutine.expression.CoroExpression;
import de.heinerkuecker.coroutine.expression.Value;
import de.heinerkuecker.coroutine.step.CoroIterStep;

public class While<RESULT /*, PARENT extends CoroutineIterator<RESULT>*/, RESUME_ARGUMENT>
extends WhileOrDoWhile<
    While<RESULT/*, PARENT*/, RESUME_ARGUMENT>,
    WhileState<RESULT/*, PARENT*/, RESUME_ARGUMENT>,
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
    public While(
            final ConditionOrBooleanExpression/*Condition*/ condition ,
            final CoroIterStep<? extends RESULT/*, PARENT*/>... steps )
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
    public While(
            final CoroExpression<Boolean> condition ,
            final CoroIterStep<? extends RESULT/*, PARENT*/>... steps )
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
    public While(
            final boolean condition ,
            final CoroIterStep<? extends RESULT/*, PARENT*/>... steps )
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
    public While(
            final String label ,
            final ConditionOrBooleanExpression/*Condition*/ condition ,
            final CoroIterStep<? extends RESULT/*, PARENT*/>... steps )
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
    public While(
            final String label ,
            final CoroExpression<Boolean> condition ,
            final CoroIterStep<? extends RESULT/*, PARENT*/>... steps )
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
    public While(
            final String label ,
            final boolean condition ,
            final CoroIterStep<? extends RESULT/*, PARENT*/>... steps )
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
    public WhileState<RESULT/*, PARENT*/, RESUME_ARGUMENT> newState(
            final CoroutineOrProcedureOrComplexstep<RESULT, RESUME_ARGUMENT> parent )
    {
        return new WhileState<>(
                this ,
                //parent.getRootParent()
                parent );
    }

}
