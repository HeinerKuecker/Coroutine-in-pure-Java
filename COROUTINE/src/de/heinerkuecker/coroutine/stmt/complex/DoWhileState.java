package de.heinerkuecker.coroutine.stmt.complex;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstmt;
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
            final CoroutineOrProcedureOrComplexstmt<COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        super(
                doWhile ,
                //rootParent
                parent );
        this.runInCondition = false;
        this.runInBody = true;
    }

    /**
     * @see ComplexStmtState#getStmt()
     */
    @Override
    public DoWhile<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> getStmt()
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
                        getStmt() ,
                        //super.rootParent
                        super.parent );

        clone.runInCondition = runInCondition;
        clone.runInBody = runInBody;
        clone.bodyComplexState = ( bodyComplexState != null ? bodyComplexState.createClone() : null );

        return clone;
    }

}
