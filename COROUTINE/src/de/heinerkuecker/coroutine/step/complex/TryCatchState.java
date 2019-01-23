package de.heinerkuecker.coroutine.step.complex;

import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep;
import de.heinerkuecker.coroutine.step.CoroIterStepResult;
import de.heinerkuecker.util.ExceptionUnchecker;
import de.heinerkuecker.util.HCloneable;

class TryCatchState<RESULT/*, PARENT extends CoroutineIterator<RESULT>*/ , RESUME_ARGUMENT>
extends ComplexStepState<
    TryCatchState<RESULT/*, PARENT*/ , RESUME_ARGUMENT>,
    TryCatch<RESULT/*, PARENT*/ , RESUME_ARGUMENT>,
    RESULT ,
    //PARENT
    RESUME_ARGUMENT
    >
{
    private final TryCatch<RESULT/*, PARENT*/ , RESUME_ARGUMENT> tryCatch;

    // TODO getter
    boolean runInTry = true;
    boolean runInCatch;
    ComplexStepState<?, ?, RESULT/*, PARENT*/ , RESUME_ARGUMENT> tryBodyComplexState;
    ComplexStepState<?, ?, RESULT/*, PARENT*/ , RESUME_ARGUMENT> catchBodyComplexState;

    //private final CoroutineIterator<RESULT> rootParent;
    private final CoroutineOrProcedureOrComplexstep<RESULT, RESUME_ARGUMENT> parent;

    private Throwable catchedThr;

    /**
     * Constructor.
     */
    protected TryCatchState(
            final TryCatch<RESULT/*, PARENT*/ , RESUME_ARGUMENT> tryCatch ,
            //final CoroutineIterator<RESULT> rootParent
            final CoroutineOrProcedureOrComplexstep<RESULT, RESUME_ARGUMENT> parent )
    {
        super( parent );

        this.tryCatch = tryCatch;

        //this.rootParent = Objects.requireNonNull( rootParent );

        this.parent =
                Objects.requireNonNull(
                        parent );
    }

    @Override
    public CoroIterStepResult<RESULT> execute(
            //final CoroutineOrProcedureOrComplexstep<RESULT, RESUME_ARGUMENT> parent
            )
    {
        if ( runInTry )
        {
            final ComplexStep<?, ?, RESULT/*, PARENT*/ , RESUME_ARGUMENT> tryBodyStep =
                    tryCatch.tryBodyComplexStep;

            if ( this.tryBodyComplexState == null )
                // no existing state from previous execute call
            {
                this.tryBodyComplexState =
                        tryBodyStep.newState(
                                //this.rootParent
                                //this.parent
                                this );
            }

            // TODO only before executing simple step: parent.saveLastStepState();

            CoroIterStepResult<RESULT> executeResult = null;
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
                        executeResult instanceof CoroIterStepResult.ContinueCoroutine ) )
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
            final ComplexStep<?, ?, RESULT/*, PARENT*/ , RESUME_ARGUMENT> catchBodyStep =
                    tryCatch.catchBodyComplexStep;

            if ( this.catchBodyComplexState == null )
                // no existing state from previous execute call
            {
                this.catchBodyComplexState =
                        catchBodyStep.newState(
                                //this.rootParent
                                //this.parent
                                this );
            }

            this.catchBodyComplexState.localVars().declare(
                    //declareStepOrExpression
                    this.tryCatch ,
                    //variableName
                    this.tryCatch.catchedExceptionVariableName ,
                    //type
                    this.tryCatch.catchExceptionClass ,
                    // value
                    this.tryCatch.catchExceptionClass.cast( catchedThr ) );

            final CoroIterStepResult<RESULT> executeResult =
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
                    executeResult instanceof CoroIterStepResult.ContinueCoroutine ) )
            {
                return executeResult;
            }

            finish();
        }

        return CoroIterStepResult.continueCoroutine();
    }

    private void finish()
    {
        this.runInTry = false;
        this.runInCatch = false;
        this.tryBodyComplexState = null;
        this.catchBodyComplexState = null;
    }

    /**
     * @see ComplexStepState#isFinished()
     */
    @Override
    public boolean isFinished()
    {
        return
                ( ! runInTry ) &&
                ( ! runInCatch );
    }

    /**
     * @see ComplexStepState#getStep()
     */
    @Override
    public TryCatch<RESULT/*, PARENT*/ , RESUME_ARGUMENT> getStep()
    {
        return this.tryCatch;
    }

    /**
     * @see HCloneable#createClone()
     */
    @Override
    public TryCatchState<RESULT/*, PARENT*/ , RESUME_ARGUMENT> createClone()
    {
        final TryCatchState<RESULT/*, PARENT*/ , RESUME_ARGUMENT> clone =
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
