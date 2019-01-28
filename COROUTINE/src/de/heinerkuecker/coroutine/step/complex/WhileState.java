package de.heinerkuecker.coroutine.step.complex;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep;
import de.heinerkuecker.util.HCloneable;

class WhileState<
    COROUTINE_RETURN ,
    //PARENT extends CoroutineIterator<COROUTINE_RETURN>
    RESUME_ARGUMENT
    >
extends WhileOrDoWhileState<
    While<COROUTINE_RETURN/*, PARENT*/, RESUME_ARGUMENT>,
    WhileState<COROUTINE_RETURN/*, PARENT*/, RESUME_ARGUMENT>,
    COROUTINE_RETURN ,
    //PARENT
    RESUME_ARGUMENT
    >
{

    /**
     * @param _while
     */
    protected WhileState(
            final While<COROUTINE_RETURN/*, PARENT*/, RESUME_ARGUMENT> _while ,
            //final CoroutineIterator<COROUTINE_RETURN> rootParent
            final CoroutineOrProcedureOrComplexstep<COROUTINE_RETURN, RESUME_ARGUMENT> parent )
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
    public While<COROUTINE_RETURN/*, PARENT*/, RESUME_ARGUMENT> getStep()
    {
        return (While<COROUTINE_RETURN/*, PARENT*/, RESUME_ARGUMENT>) this.whileOrDoWhile;
    }

    /**
     * @see HCloneable#createClone()
     */
    @Override
    public WhileState<COROUTINE_RETURN/*, PARENT*/, RESUME_ARGUMENT> createClone()
    {
        final WhileState<COROUTINE_RETURN/*, PARENT*/, RESUME_ARGUMENT> clone =
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
