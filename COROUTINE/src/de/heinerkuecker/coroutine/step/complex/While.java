package de.heinerkuecker.coroutine.step.complex;

import de.heinerkuecker.coroutine.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine.condition.ConditionOrBooleanExpression;
import de.heinerkuecker.coroutine.step.CoroIterStep;

public class While<RESULT /*, PARENT extends CoroutineIterator<RESULT>*/>
extends WhileOrDoWhile<
    While<RESULT/*, PARENT*/>,
    WhileState<RESULT/*, PARENT*/>,
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
    public While(
            ConditionOrBooleanExpression/*Condition*/ condition ,
            CoroIterStep<? extends RESULT/*, PARENT*/>... steps )
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
    public While(
            String label ,
            ConditionOrBooleanExpression/*Condition*/ condition ,
            CoroIterStep<? extends RESULT/*, PARENT*/>... steps )
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
    public WhileState<RESULT/*, PARENT*/> newState(
            final CoroIteratorOrProcedure<RESULT> parent )
    {
        return new WhileState<>(
                this ,
                parent.getRootParent() );
    }

}
