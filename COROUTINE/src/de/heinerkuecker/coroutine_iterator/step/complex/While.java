package de.heinerkuecker.coroutine_iterator.step.complex;

import de.heinerkuecker.coroutine_iterator.condition.Condition;
import de.heinerkuecker.coroutine_iterator.step.CoroIterStep;

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
            Condition condition ,
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
            Condition condition ,
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
    public WhileState<RESULT/*, PARENT*/> newState()
    {
        return new WhileState<>( this );
    }

}
