package de.heinerkuecker.coroutine.stmt.complex;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep;
import de.heinerkuecker.util.HCloneable;

class DoWhileState<
    COROUTINE_RETURN ,
    //PARENT extends CoroutineIterator<COROUTINE_RETURN>
    RESUME_ARGUMENT
    >
extends WhileOrDoWhileState<
    DoWhile<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT>,
    DoWhileState<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> ,
    COROUTINE_RETURN ,
    //PARENT
    RESUME_ARGUMENT
    >
{

    /**
     * @param doWhile
     */
    protected DoWhileState(
            final DoWhile<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> doWhile ,
            //final CoroutineIterator<COROUTINE_RETURN> rootParent
            final CoroutineOrProcedureOrComplexstep<COROUTINE_RETURN, RESUME_ARGUMENT> parent )
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
    public DoWhile<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> getStep()
    {
        return (DoWhile<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT>) this.whileOrDoWhile;
    }

    /**
     * @see HCloneable#createClone()
     */
    @Override
    public DoWhileState<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> createClone()
    {
        final DoWhileState<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> clone =
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
