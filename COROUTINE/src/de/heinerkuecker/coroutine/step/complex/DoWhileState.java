package de.heinerkuecker.coroutine.step.complex;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep;
import de.heinerkuecker.util.HCloneable;

class DoWhileState<
    RESULT ,
    //PARENT extends CoroutineIterator<RESULT>
    RESUME_ARGUMENT
    >
extends WhileOrDoWhileState<
    DoWhile<RESULT/*, PARENT*/ , RESUME_ARGUMENT>,
    DoWhileState<RESULT/*, PARENT*/ , RESUME_ARGUMENT> ,
    RESULT ,
    //PARENT
    RESUME_ARGUMENT
    >
{

    /**
     * @param doWhile
     */
    protected DoWhileState(
            final DoWhile<RESULT/*, PARENT*/ , RESUME_ARGUMENT> doWhile ,
            //final CoroutineIterator<RESULT> rootParent
            final CoroutineOrProcedureOrComplexstep<RESULT, RESUME_ARGUMENT> parent )
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
    public DoWhile<RESULT/*, PARENT*/ , RESUME_ARGUMENT> getStep()
    {
        return (DoWhile<RESULT/*, PARENT*/ , RESUME_ARGUMENT>) this.whileOrDoWhile;
    }

    /**
     * @see HCloneable#createClone()
     */
    @Override
    public DoWhileState<RESULT/*, PARENT*/ , RESUME_ARGUMENT> createClone()
    {
        final DoWhileState<RESULT/*, PARENT*/ , RESUME_ARGUMENT> clone =
                new DoWhileState<>(
                        getStep() ,
                        //super.rootParent
                        super.parent );

        clone.runInCondition = runInCondition;
        clone.runInBody = runInBody;
        clone.bodyComplexState = ( bodyComplexState != null ? bodyComplexState.createClone() : null );

        return clone;
    }

}
