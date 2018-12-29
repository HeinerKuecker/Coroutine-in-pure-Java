package de.heinerkuecker.coroutine_iterator.step.complex;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.heinerkuecker.coroutine_iterator.CoroutineIterator;
import de.heinerkuecker.coroutine_iterator.step.CoroIterStep;
import de.heinerkuecker.coroutine_iterator.step.flow.BreakOrContinue;

public class TryCatch<RESULT/*, PARENT extends CoroutineIterator<RESULT>*/>
extends ComplexStep<
    TryCatch<RESULT/*, PARENT*/>,
    TryCatchState<RESULT/*, PARENT*/>,
    RESULT
    //PARENT
    >
{
    final ComplexStep<?, ?, RESULT/*, PARENT/*CoroutineIterator<RESULT>*/> tryBodyComplexStep;
    final Class<? extends Throwable> catchExceptionClass;
    final ComplexStep<?, ?, RESULT/*, PARENT/*CoroutineIterator<RESULT>*/> catchBodyComplexStep;

    /**
     * Constructor.
     */
    @SafeVarargs
    public TryCatch(
            final CoroIterStep<RESULT/*, PARENT /*CoroutineIterator<RESULT>*/> tryStep ,
            final Class<? extends Throwable> catchExceptionClass ,
            final CoroIterStep<RESULT/*, ? super PARENT/*CoroutineIterator<RESULT>*/> ... catchBodySteps )
    {
        super(
                //creationStackOffset
                3 );

        if ( tryStep instanceof ComplexStep )
        {
            this.tryBodyComplexStep =
                    (ComplexStep<?, ?, RESULT/*, PARENT*/>) tryStep;
        }
        else
        {
            // TODO support simple step
            this.tryBodyComplexStep =
                    new StepSequence(
                            // creationStackOffset
                            3 ,
                            tryStep );
        }

        this.catchExceptionClass =
                Objects.requireNonNull(
                        catchExceptionClass );

        if ( catchBodySteps.length == 1 &&
                catchBodySteps[ 0 ] instanceof ComplexStep )
        {
            this.catchBodyComplexStep =
                    (ComplexStep<?, ?, RESULT/*, PARENT/*? super CoroutineIterator<RESULT>*/>) catchBodySteps[ 0 ];
        }
        else
        {
            this.catchBodyComplexStep =
                    new StepSequence(
                            // creationStackOffset
                            3 ,
                            catchBodySteps );
        }
    }

    /**
     * Factroy method.
     */
    @SafeVarargs
    public static <RESULT/*, PARENT extends CoroutineIterator<RESULT>*/> TryCatch<RESULT/*, PARENT*/> newTryCatch(
            final CoroIterStep<RESULT/*, PARENT /*CoroutineIterator<RESULT>*/> tryStep ,
            final Class<? extends Throwable> catchExceptionClass ,
            final CoroIterStep<RESULT/*, ? super PARENT/*CoroutineIterator<RESULT>*/> ... catchBodySteps )
    {
        return new TryCatch<>(
                tryStep ,
                catchExceptionClass ,
                catchBodySteps);
    }

    /**
     * @see ComplexStep#newState()
     */
    @Override
    public TryCatchState<RESULT/*, PARENT*/> newState()
    {
        return new TryCatchState<RESULT/*, PARENT*/>( this );
    }

    /**
     * @see ComplexStep#getUnresolvedBreaksOrContinues()
     */
    @Override
    public List<BreakOrContinue<RESULT>> getUnresolvedBreaksOrContinues()
    {
        final List<BreakOrContinue<RESULT>> result = new ArrayList<>();

        if ( tryBodyComplexStep instanceof ComplexStep )
        {
            result.addAll( ((ComplexStep) tryBodyComplexStep).getUnresolvedBreaksOrContinues() );
        }

        if ( catchBodyComplexStep instanceof ComplexStep )
        {
            result.addAll( ((ComplexStep) catchBodyComplexStep).getUnresolvedBreaksOrContinues() );
        }

        return result;
    }

    /**
     * @see ComplexStep#setRootParent
     */
    @Override
    public void setRootParent(
            final CoroutineIterator<RESULT> rootParent )
    {
        this.tryBodyComplexStep.setRootParent( rootParent );
        this.catchBodyComplexStep.setRootParent( rootParent );
    }

    /**
     * @see ComplexStep#toString(String, ComplexStepState, ComplexStepState)
     */
    @Override
    public String toString(
            String indent ,
            ComplexStepState<?, ?, RESULT/*, PARENT*/> lastStepExecuteState ,
            ComplexStepState<?, ?, RESULT/*, PARENT*/> nextStepExecuteState )
    {
        final TryCatchState<RESULT/*, PARENT*/> lastTryCatchExecuteState =
                (TryCatchState<RESULT/*, PARENT*/>) lastStepExecuteState;

        final TryCatchState<RESULT/*, PARENT*/> nextTryCatchExecuteState =
                (TryCatchState<RESULT/*, PARENT*/>) nextStepExecuteState;

        final ComplexStepState<?, ?, RESULT/*, PARENT*/> lastTryBodyState;
        if ( lastTryCatchExecuteState != null )
        {
            lastTryBodyState = lastTryCatchExecuteState.tryBodyComplexState;
        }
        else
        {
            lastTryBodyState = null;
        }

        final ComplexStepState<?, ?, RESULT/*, PARENT*/> nextTryBodyState;
        if ( nextTryCatchExecuteState != null )
        {
            nextTryBodyState = nextTryCatchExecuteState.tryBodyComplexState;
        }
        else
        {
            nextTryBodyState = null;
        }

        final ComplexStepState<?, ?, RESULT/*, PARENT*/> lastCatchBodyState;
        if ( lastTryCatchExecuteState != null )
        {
            lastCatchBodyState = lastTryCatchExecuteState.catchBodyComplexState;
        }
        else
        {
            lastCatchBodyState = null;
        }

        final ComplexStepState<?, ?, RESULT/*, PARENT*/> nextCatchBodyState;
        if ( nextTryCatchExecuteState != null )
        {
            nextCatchBodyState = nextTryCatchExecuteState.catchBodyComplexState;
        }
        else
        {
            nextCatchBodyState = null;
        }

        return
                indent +
                this.getClass().getSimpleName() +
                ( this.creationStackTraceElement != null ? " " + this.creationStackTraceElement : "" ) +
                "\n" +
                this.tryBodyComplexStep.toString(
                        indent + " " ,
                        lastTryBodyState ,
                        nextTryBodyState ) +
                indent + "catch ( " + this.catchExceptionClass.getName() + " )" + "\n" +
                this.catchBodyComplexStep.toString(
                        indent + " " ,
                        lastCatchBodyState ,
                        nextCatchBodyState );
    }

}
