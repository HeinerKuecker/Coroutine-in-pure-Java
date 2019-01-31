package de.heinerkuecker.coroutine.stmt.complex;

import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstmt;
import de.heinerkuecker.coroutine.stmt.CoroIterStmtResult;
import de.heinerkuecker.util.ExceptionUnchecker;
import de.heinerkuecker.util.HCloneable;

class TryCatchState<COROUTINE_RETURN/*, PARENT extends CoroutineIterator<COROUTINE_RETURN>*/ , RESUME_ARGUMENT>
extends ComplexStmtState<
    TryCatchState<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT>,
    TryCatch<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT>,
    COROUTINE_RETURN ,
    //PARENT
    RESUME_ARGUMENT
    >
{
    private final TryCatch<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> tryCatch;

    // TODO getter
    boolean runInTry = true;
    boolean runInCatch;
    ComplexStmtState<?, ?, COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> tryBodyComplexState;
    ComplexStmtState<?, ?, COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> catchBodyComplexState;

    //private final CoroutineIterator<COROUTINE_RETURN> rootParent;
    private final CoroutineOrProcedureOrComplexstmt<COROUTINE_RETURN, RESUME_ARGUMENT> parent;

    private Throwable catchedThr;

    /**
     * Constructor.
     */
    protected TryCatchState(
            final TryCatch<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> tryCatch ,
            //final CoroutineIterator<COROUTINE_RETURN> rootParent
            final CoroutineOrProcedureOrComplexstmt<COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        super( parent );

        this.tryCatch = tryCatch;

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
        if ( runInTry )
        {
            final ComplexStmt<?, ?, COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> tryBodyStmt =
                    tryCatch.tryBodyComplexStmt;

            if ( this.tryBodyComplexState == null )
                // no existing state from previous execute call
            {
                this.tryBodyComplexState =
                        tryBodyStmt.newState(
                                //this.rootParent
                                //this.parent
                                this );
            }

            // TODO only before executing simple stmt: parent.saveLastStmtState();

            CoroIterStmtResult<COROUTINE_RETURN> executeResult = null;
            try
            {
                executeResult =
                        this.tryBodyComplexState.execute(
                                //parent
                                //this
                                );

                if ( this.tryBodyComplexState.isFinished() )
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
            catch ( final Throwable thr )
            {
                if ( ! tryCatch.catchExceptionClass.isInstance( thr ) )
                {
                    ExceptionUnchecker.rethrow( thr );
                }

                this.catchedThr = thr;
                runInTry = false;
                runInCatch = true;
                tryBodyComplexState = null;
            }
        }

        if ( runInCatch )
        {
            final ComplexStmt<?, ?, COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> catchBodyStmt =
                    tryCatch.catchBodyComplexStmt;

            if ( this.catchBodyComplexState == null )
                // no existing state from previous execute call
            {
                this.catchBodyComplexState =
                        catchBodyStmt.newState(
                                //this.rootParent
                                //this.parent
                                this );
            }

            this.catchBodyComplexState.localVars().declare(
                    //declareStmtOrExpression
                    this.tryCatch ,
                    //variableName
                    this.tryCatch.catchedExceptionVariableName ,
                    //type
                    this.tryCatch.catchExceptionClass ,
                    // value
                    this.tryCatch.catchExceptionClass.cast( catchedThr ) );

            final CoroIterStmtResult<COROUTINE_RETURN> executeResult =
                    this.catchBodyComplexState.execute(
                            //parent
                            //this
                            //catchBodyComplexState
                            );

            if ( this.catchBodyComplexState.isFinished() )
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
        this.runInTry = false;
        this.runInCatch = false;
        this.tryBodyComplexState = null;
        this.catchBodyComplexState = null;
    }

    /**
     * @see ComplexStmtState#isFinished()
     */
    @Override
    public boolean isFinished()
    {
        return
                ( ! runInTry ) &&
                ( ! runInCatch );
    }

    /**
     * @see ComplexStmtState#getStmt()
     */
    @Override
    public TryCatch<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> getStmt()
    {
        return this.tryCatch;
    }

    /**
     * @see HCloneable#createClone()
     */
    @Override
    public TryCatchState<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> createClone()
    {
        final TryCatchState<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> clone =
                new TryCatchState<>(
                        tryCatch ,
                        //this.rootParent
                        this.parent );

        clone.runInTry = runInTry;
        clone.runInCatch = runInCatch;
        clone.tryBodyComplexState = ( tryBodyComplexState != null ? tryBodyComplexState.createClone() : null );
        clone.catchBodyComplexState = ( catchBodyComplexState != null ? catchBodyComplexState.createClone() : null );

        return clone;
    }

}
