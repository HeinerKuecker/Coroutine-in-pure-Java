package de.heinerkuecker.coroutine.step.complex;

import de.heinerkuecker.coroutine.CoroIteratorOrProcedure;
import de.heinerkuecker.util.HCloneable;

class DoWhileState<
    RESULT
    //PARENT extends CoroutineIterator<RESULT>
    >
extends WhileOrDoWhileState<
    DoWhile<RESULT/*, PARENT*/>,
    DoWhileState<RESULT/*, PARENT*/>,
    RESULT
    //PARENT
    >
{

    /**
     * @param doWhile
     */
    protected DoWhileState(
            final DoWhile<RESULT/*, PARENT*/> doWhile ,
            //final CoroutineIterator<RESULT> rootParent
            final CoroIteratorOrProcedure<RESULT> parent )
    {
        super(
                doWhile ,
                //rootParent
                parent );
        this.runInCondition = false;
        this.runInBody = true;
    }

    /**
     * @see ComplexStepState#getStep()
     */
    @Override
    public DoWhile<RESULT/*, PARENT*/> getStep()
    {
        return (DoWhile<RESULT/*, PARENT*/>) this.whileOrDoWhile;
    }

    /**
     * @see HCloneable#createClone()
     */
    @Override
    public DoWhileState<RESULT/*, PARENT*/> createClone()
    {
        final DoWhileState<RESULT/*, PARENT*/> clone =
                new DoWhileState<RESULT/*, PARENT*/>(
                        getStep() ,
                        //super.rootParent
                        super.parent );

        clone.runInCondition = runInCondition;
        clone.runInBody = runInBody;
        clone.bodyComplexState = ( bodyComplexState != null ? bodyComplexState.createClone() : null );

        return clone;
    }

}
