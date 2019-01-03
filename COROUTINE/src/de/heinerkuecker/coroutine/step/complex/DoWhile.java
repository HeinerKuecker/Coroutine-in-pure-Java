package de.heinerkuecker.coroutine.step.complex;

import de.heinerkuecker.coroutine.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine.condition.ConditionOrBooleanExpression;
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
            ConditionOrBooleanExpression/*Condition/*<? super PARENT>*/ condition ,
            CoroIterStep<? extends RESULT /*, PARENT*/>... steps )
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
     * @param label
     * @param condition
     * @param steps
     */
    @SafeVarargs
    public DoWhile(
            String label ,
            ConditionOrBooleanExpression/*Condition/*<? super PARENT>*/ condition ,
            CoroIterStep<? extends RESULT /*, PARENT*/>... steps )
    {
        super(
                label ,
                condition ,
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
                parent.getRootParent() );
    }

}