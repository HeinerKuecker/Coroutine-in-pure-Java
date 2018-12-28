package de.heinerkuecker.coroutine_iterator.step.complex;

import java.util.Objects;

import de.heinerkuecker.coroutine_iterator.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine_iterator.CoroutineIterator;
import de.heinerkuecker.coroutine_iterator.step.result.CoroIterStepResult;
import de.heinerkuecker.util.ExceptionUnchecker;
import de.heinerkuecker.util.HCloneable;

class TryCatchState<RESULT/*, PARENT extends CoroutineIterator<RESULT>*/>
implements ComplexStepState<
    TryCatchState<RESULT/*, PARENT*/>,
    TryCatch<RESULT/*, PARENT*/>,
    RESULT
    //PARENT
    >
{
    private final TryCatch<RESULT/*, PARENT*/> tryCatch;

    // TODO getter
    boolean runInTry = true;
    boolean runInCatch;
    ComplexStepState<?, ?, RESULT/*, PARENT*/> tryBodyComplexState;
    ComplexStepState<?, ?, RESULT/*, PARENT*/> catchBodyComplexState;

    /**
     * Constructor.
     */
    protected TryCatchState(
            final TryCatch<RESULT/*, PARENT*/> tryCatch )
    {
        this.tryCatch =
                Objects.requireNonNull(
                        tryCatch );
    }

    /**
     * @see ComplexStepState#execute(CoroutineIterator)
     */
    @Override
    public CoroIterStepResult<RESULT> execute(
            final CoroIteratorOrProcedure<RESULT> parent)
    {
        if ( runInTry )
        {
            final ComplexStep<?, ?, RESULT/*, PARENT*/> tryBodyStep =
                    tryCatch.tryBodyComplexStep;

            if ( this.tryBodyComplexState == null )
                // no existing state from previous execute call
            {
                this.tryBodyComplexState = tryBodyStep.newState();
            }

            // TODO only before executing simple step: parent.saveLastStepState();

            CoroIterStepResult<RESULT> executeResult = null;
            try
            {
                executeResult =
                        this.tryBodyComplexState.execute(
                                parent );

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

                runInTry = false;
                runInCatch = true;
                tryBodyComplexState = null;
            }
        }

        if ( runInCatch )
        {
            final ComplexStep<?, ?, RESULT/*, PARENT*/> catchBodyStep =
                    tryCatch.catchBodyComplexStep;

            if ( this.catchBodyComplexState == null )
                // no existing state from previous execute call
            {
                this.catchBodyComplexState = catchBodyStep.newState();
            }

            // TODO only before executing simple step: parent.saveLastStepState();

            final CoroIterStepResult<RESULT> executeResult =
                    this.catchBodyComplexState.execute(
                            parent );

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
    public TryCatch<RESULT/*, PARENT*/> getStep()
    {
        return this.tryCatch;
    }

    /**
     * @see HCloneable#createClone()
     */
    @Override
    public TryCatchState<RESULT/*, PARENT*/> createClone()
    {
        final TryCatchState<RESULT/*, PARENT*/> clone = new TryCatchState<>( tryCatch );

        clone.runInTry = runInTry;
        clone.runInCatch = runInCatch;
        clone.tryBodyComplexState = ( tryBodyComplexState != null ? tryBodyComplexState.createClone() : null );
        clone.catchBodyComplexState = ( catchBodyComplexState != null ? catchBodyComplexState.createClone() : null );

        return clone;
    }

}
