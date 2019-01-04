package de.heinerkuecker.coroutine.step.complex;

import java.util.Objects;

import de.heinerkuecker.coroutine.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.step.CoroIterStepResult;
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

    //private final CoroutineIterator<RESULT> rootParent;
    private final CoroIteratorOrProcedure<RESULT> parent;

    /**
     * Constructor.
     */
    protected TryCatchState(
            final TryCatch<RESULT/*, PARENT*/> tryCatch ,
            //final CoroutineIterator<RESULT> rootParent
            final CoroIteratorOrProcedure<RESULT> parent )
    {
        this.tryCatch =
                Objects.requireNonNull(
                        tryCatch );

        //this.rootParent = Objects.requireNonNull( rootParent );

        this.parent =
                Objects.requireNonNull(
                        parent );
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
                this.tryBodyComplexState =
                        tryBodyStep.newState(
                                //this.rootParent
                                this.parent );
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
                this.catchBodyComplexState =
                        catchBodyStep.newState(
                                //this.rootParent
                                this.parent );
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
        final TryCatchState<RESULT/*, PARENT*/> clone =
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
