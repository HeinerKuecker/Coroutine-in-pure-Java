package de.heinerkuecker.coroutine.step.complex;

import de.heinerkuecker.coroutine.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine.condition.ConditionOrBooleanExpression;
import de.heinerkuecker.coroutine.condition.IsTrue;
import de.heinerkuecker.coroutine.expression.CoroExpression;
import de.heinerkuecker.coroutine.expression.Value;
import de.heinerkuecker.coroutine.step.CoroIterStep;

public class DoWhile<RESULT /*, PARENT extends CoroutineIterator<RESULT>*/>
extends WhileOrDoWhile<
    DoWhile<RESULT /*, PARENT*/>,
    DoWhileState<RESULT /*, PARENT*/>,
    RESULT
    //PARENT
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
            final Boolean condition ,
            final CoroIterStep<? extends RESULT /*, PARENT*/>... steps )
    {
        super(
                //label
                null ,
                new IsTrue(
                        new Value<Boolean>(
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
                        new Value<Boolean>(
                                condition ) ) ,
                steps );
    }

    /**
     * @see ComplexStep#newState()
     */
    @Override
    public DoWhileState<RESULT /*, PARENT*/> newState(
            final CoroIteratorOrProcedure<RESULT> parent )
    {
        return new DoWhileState<>(
                this ,
                //parent.getRootParent()
                parent );
    }

}
