package de.heinerkuecker.coroutine.stmt.complex;

import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstmt;
import de.heinerkuecker.coroutine.stmt.CoroIterStmtResult;
import de.heinerkuecker.util.HCloneable;

class IfState<COROUTINE_RETURN/*, PARENT extends CoroutineIterator<COROUTINE_RETURN>*/ , RESUME_ARGUMENT>
extends ComplexStmtState<
    IfState<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT>,
    If<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT>,
    COROUTINE_RETURN ,
    //PARENT
    RESUME_ARGUMENT
    >
{
    private final If<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> _if;

    // TODO getter
    boolean runInCondition = true;
    private boolean runInThenBody;

    // TODO getter
    ComplexStmtState<?, ?, COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> thenBodyComplexState;

    //private final CoroutineIterator<COROUTINE_RETURN> rootParent;
    private final CoroutineOrProcedureOrComplexstmt<COROUTINE_RETURN, RESUME_ARGUMENT> parent;

    /**
     * Constructor.
     */
    public IfState(
            final If<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> _if ,
            //final CoroutineIterator<COROUTINE_RETURN> rootParent
            final CoroutineOrProcedureOrComplexstmt<COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        super( parent );
        this._if = _if;

        //this.rootParent = Objects.requireNonNull( rootParent );

        this.parent =
                Objects.requireNonNull(
                        parent );
    }

    @Override
    public CoroIterStmtResult<COROUTINE_RETURN> execute(
            //final CoroutineOrProcedureOrComplexstmt<COROUTINE_RETURN, RESUME_ARGUMENT> parent
            )
    {
        if ( this.runInCondition )
        {
            // TODO ist dies notwendig?
            parent.saveLastStmtState();

            final boolean conditionResult =
                    _if.condition.evaluate(
                            //parent
                            this );

            this.runInCondition = false;

            if ( conditionResult )
            {
                runInThenBody = true;
            }
            else
            {
                finish();
                return CoroIterStmtResult.continueCoroutine();
            }
        }

        if ( runInThenBody )
        {
            final ComplexStmt<?, ?, COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> thenBodyStmt =
                    _if.thenBodyComplexStmt;

            if ( this.thenBodyComplexState == null )
                // no existing state from previous execute call
            {
                this.thenBodyComplexState =
                        thenBodyStmt.newState(
                                //this.rootParent
                                //this.parent
                                this );
            }

            // TODO only before executing simple stmt: parent.saveLastStmtState();

            final CoroIterStmtResult<COROUTINE_RETURN> executeResult =
                    this.thenBodyComplexState.execute(
                            //parent
                            //this
                            );

            if ( this.thenBodyComplexState.isFinished() )
            {
                finish();
            }

            if ( ! ( executeResult == null ||
                    executeResult instanceof CoroIterStmtResult.ContinueCoroutine ) )
            {
                return executeResult;
            }

            finish();
        }

        return CoroIterStmtResult.continueCoroutine();
    }

    private void finish()
    {
        this.runInCondition = false;
        this.runInThenBody = false;
        this.thenBodyComplexState = null;
    }

    /**
     * @see ComplexStmtState#isFinished()
     */
    @Override
    public boolean isFinished()
    {
        return
                ( ! this.runInCondition ) &&
                ( ! this.runInThenBody );
    }

    /**
     * @see ComplexStmtState#getStmt()
     */
    @Override
    public If<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> getStmt()
    {
        return _if;
    }

    /**
     * @see HCloneable#createClone()
     */
    @Override
    public IfState<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> createClone()
    {
        //throw new RuntimeException( "not implemented" );
        final IfState<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> clone =
                new IfState<>(
                        _if ,
                        //this.rootParent
                        this.parent );

        clone.runInCondition = runInCondition;
        clone.runInThenBody = runInThenBody;
        clone.thenBodyComplexState = ( thenBodyComplexState != null ? thenBodyComplexState.createClone() : null );

        return clone;
    }

}
