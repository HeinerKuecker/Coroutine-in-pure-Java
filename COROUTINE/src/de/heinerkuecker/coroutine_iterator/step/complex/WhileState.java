package de.heinerkuecker.coroutine_iterator.step.complex;

import de.heinerkuecker.util.HCloneable;

class WhileState<
    RESULT
    //PARENT extends CoroutineIterator<RESULT>
    >
extends WhileOrDoWhileState<
    While<RESULT/*, PARENT*/>,
    WhileState<RESULT/*, PARENT*/>,
    RESULT
    //PARENT
    >
{

    /**
     * @param _while
     */
    protected WhileState(
            final While<RESULT/*, PARENT*/> _while )
    {
        super( _while );
        this.runInCondition = true;
        this.runInBody = false;
    }

    /**
     * @see ComplexStepState#getStep()
     */
    @Override
    public While<RESULT/*, PARENT*/> getStep()
    {
        return (While<RESULT/*, PARENT*/>) this.whileOrDoWhile;
    }

    /**
     * @see HCloneable#createClone()
     */
    @Override
    public WhileState<RESULT/*, PARENT*/> createClone()
    {
        final WhileState<RESULT/*, PARENT*/> clone = new WhileState<RESULT/*, PARENT*/>( getStep() );

        clone.runInCondition = this.runInCondition;
        clone.runInBody = this.runInBody;
        clone.bodyComplexState = ( bodyComplexState != null ? bodyComplexState.createClone() : null );

        return clone;
    }

}
