package de.heinerkuecker.coroutine.step.complex;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep;
import de.heinerkuecker.util.HCloneable;

class WhileState<
    RESULT ,
    //PARENT extends CoroutineIterator<RESULT>
    RESUME_ARGUMENT
    >
extends WhileOrDoWhileState<
    While<RESULT/*, PARENT*/, RESUME_ARGUMENT>,
    WhileState<RESULT/*, PARENT*/, RESUME_ARGUMENT>,
    RESULT ,
    //PARENT
    RESUME_ARGUMENT
    >
{

    /**
     * @param _while
     */
    protected WhileState(
            final While<RESULT/*, PARENT*/, RESUME_ARGUMENT> _while ,
            //final CoroutineIterator<RESULT> rootParent
            final CoroutineOrProcedureOrComplexstep<RESULT, RESUME_ARGUMENT> parent )
    {
        super(
                _while ,
                //rootParent
                parent );
        this.runInCondition = true;
        this.runInBody = false;
    }

    /**
     * @see ComplexStepState#getStep()
     */
    @Override
    public While<RESULT/*, PARENT*/, RESUME_ARGUMENT> getStep()
    {
        return (While<RESULT/*, PARENT*/, RESUME_ARGUMENT>) this.whileOrDoWhile;
    }

    /**
     * @see HCloneable#createClone()
     */
    @Override
    public WhileState<RESULT/*, PARENT*/, RESUME_ARGUMENT> createClone()
    {
        final WhileState<RESULT/*, PARENT*/, RESUME_ARGUMENT> clone =
                new WhileState<>(
                        getStep() ,
                        //super.rootParent
                        super.parent );

        clone.runInCondition = this.runInCondition;
        clone.runInBody = this.runInBody;
        clone.bodyComplexState = ( bodyComplexState != null ? bodyComplexState.createClone() : null );

        return clone;
    }

}
