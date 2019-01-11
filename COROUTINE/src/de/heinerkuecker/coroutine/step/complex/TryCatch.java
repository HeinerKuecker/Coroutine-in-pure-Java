package de.heinerkuecker.coroutine.step.complex;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import de.heinerkuecker.coroutine.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine.expression.GetProcedureArgument;
import de.heinerkuecker.coroutine.step.CoroIterStep;
import de.heinerkuecker.coroutine.step.flow.BreakOrContinue;

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
                    new StepSequence<>(
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
                    new StepSequence<>(
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
    public TryCatchState<RESULT/*, PARENT*/> newState(
            final CoroIteratorOrProcedure<RESULT> parent )
    {
        return new TryCatchState<RESULT/*, PARENT*/>(
                this ,
                parent );
    }

    /**
     * @see ComplexStep#getUnresolvedBreaksOrContinues()
     */
    @Override
    public List<BreakOrContinue<RESULT>> getUnresolvedBreaksOrContinues(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroIteratorOrProcedure<RESULT> parent )
    {
        final List<BreakOrContinue<RESULT>> result = new ArrayList<>();

        if ( tryBodyComplexStep instanceof ComplexStep )
        {
            result.addAll( ((ComplexStep<?, ?, RESULT>) tryBodyComplexStep).getUnresolvedBreaksOrContinues(
                    alreadyCheckedProcedureNames ,
                    parent ) );
        }

        if ( catchBodyComplexStep instanceof ComplexStep )
        {
            result.addAll( ((ComplexStep<?, ?, RESULT>) catchBodyComplexStep).getUnresolvedBreaksOrContinues(
                    alreadyCheckedProcedureNames ,
                    parent ) );
        }

        return result;
    }

    /**
     * @see CoroIterStep#getProcedureArgumentGetsNotInProcedure()
     */
    @Override
    public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    {
        final List<GetProcedureArgument<?>> result = new ArrayList<>();

        result.addAll(
                tryBodyComplexStep.getProcedureArgumentGetsNotInProcedure() );

        result.addAll(
                catchBodyComplexStep.getProcedureArgumentGetsNotInProcedure() );

        return result;
    }

    /**
     * @see CoroIterStep#setResultType(Class)
     */
    @Override
    public void setResultType(
            final Class<? extends RESULT> resultType )
    {
        this.tryBodyComplexStep.setResultType( resultType );
        this.catchBodyComplexStep.setResultType( resultType );
    }

    /**
     * @see ComplexStep#checkLabelAlreadyInUse(Set)
     */
    @Override
    public void checkLabelAlreadyInUse(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroIteratorOrProcedure<RESULT> parent ,
            final Set<String> labels )
    {
        this.tryBodyComplexStep.checkLabelAlreadyInUse(
                alreadyCheckedProcedureNames ,
                parent ,
                labels );

        this.catchBodyComplexStep.checkLabelAlreadyInUse(
                alreadyCheckedProcedureNames ,
                parent ,
                labels );
    }

    ///**
    // * @see ComplexStep#setRootParent
    // */
    //@Override
    //public void setRootParent(
    //        final CoroutineIterator<RESULT> rootParent )
    //{
    //    this.tryBodyComplexStep.setRootParent( rootParent );
    //    this.catchBodyComplexStep.setRootParent( rootParent );
    //}

    /**
     * @see ComplexStep#toString(String, ComplexStepState, ComplexStepState)
     */
    @Override
    public String toString(
            final CoroIteratorOrProcedure<RESULT> parent ,
            String indent ,
            ComplexStepState<?, ?, RESULT/*, PARENT*/> lastStepExecuteState ,
            ComplexStepState<?, ?, RESULT/*, PARENT*/> nextStepExecuteState )
    {
        @SuppressWarnings("unchecked")
        final TryCatchState<RESULT/*, PARENT*/> lastTryCatchExecuteState =
                (TryCatchState<RESULT/*, PARENT*/>) lastStepExecuteState;

        @SuppressWarnings("unchecked")
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
                        parent ,
                        indent + " " ,
                        lastTryBodyState ,
                        nextTryBodyState ) +
                indent + "catch ( " + this.catchExceptionClass.getName() + " )" + "\n" +
                this.catchBodyComplexStep.toString(
                        parent ,
                        indent + " " ,
                        lastCatchBodyState ,
                        nextCatchBodyState );
    }

}
