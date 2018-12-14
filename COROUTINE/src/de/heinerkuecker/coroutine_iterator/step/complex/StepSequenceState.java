package de.heinerkuecker.coroutine_iterator.step.complex;

import de.heinerkuecker.coroutine_iterator.CoroutineIterator;
import de.heinerkuecker.coroutine_iterator.step.CoroIterStep;
import de.heinerkuecker.coroutine_iterator.step.result.CoroIterStepResult;
import de.heinerkuecker.coroutine_iterator.step.simple.SimpleStep;
import de.heinerkuecker.util.HCloneable;

public class StepSequenceState<RESULT, PARENT extends CoroutineIterator<RESULT>>
implements ComplexStepState<StepSequenceState<RESULT, PARENT>, StepSequence<RESULT, PARENT>, RESULT, PARENT>
{
    private final StepSequence<RESULT, PARENT> sequence;

    // TODO getter to ensure unmodifiable from other class
    int currentStepIndex;
    ComplexStepState<?, ?, RESULT, ? super PARENT> currentComplexState;

    /**
     *
     */
    public StepSequenceState(
            final StepSequence<RESULT, PARENT> sequence )
    {
        this.sequence = sequence;
    }

    /**
     * @see CoroIterStep#execute(java.lang.Object)
     */
    @Override
    public CoroIterStepResult<RESULT> execute(
            final PARENT parent )
    {
        while ( currentStepIndex < sequence.length() )
        {
            final CoroIterStep<RESULT, ? super PARENT> currentStep =
                    sequence.getStep( this.currentStepIndex );

            final CoroIterStepResult<RESULT> executeResult;
            if ( currentStep instanceof SimpleStep )
            {
                final SimpleStep<RESULT, ? super PARENT> currentSimpleStep =
                        (SimpleStep<RESULT, ? super PARENT>) currentStep;

                parent.saveLastStepState();

                executeResult =
                        currentSimpleStep.execute(
                                parent );

                this.currentStepIndex++;
            }
            else
            {
                final ComplexStep<?, ?, RESULT, ? super PARENT> currentComplexStep =
                        (ComplexStep<?, ?, RESULT, ? super PARENT>) currentStep;

                if ( this.currentComplexState == null )
                    // no existing state from previous execute call
                {
                    this.currentComplexState = currentComplexStep.newState();
                }

                // TODO only before executing simple step: parent.saveLastStepState();

                executeResult =
                        this.currentComplexState.execute(
                                parent );

                if ( this.currentComplexState.isFinished() )
                {
                    this.currentStepIndex++;
                    this.currentComplexState = null;
                }
            }

            // TODO only for toString
            if ( ! this.isFinished() &&
                    this.currentComplexState == null &&
                     sequence.getStep( this.currentStepIndex ) instanceof ComplexStep )
            {
                this.currentComplexState =
                        ( (ComplexStep) sequence.getStep( this.currentStepIndex ) ).newState();
            }

            if ( ! ( executeResult == null ||
                    executeResult instanceof CoroIterStepResult.ContinueCoroutine ) )
            {
                return executeResult;
            }
        }
        return CoroIterStepResult.continueCoroutine();
    }

    /**
     * @see ComplexStepState#isFinished()
     */
    @Override
    public boolean isFinished()
    {
        return this.currentStepIndex == sequence.length();
    }

    /**
     * @see ComplexStepState#getStep()
     */
    @Override
    public StepSequence<RESULT, PARENT> getStep()
    {
        return this.sequence;
    }

    /**
     * @see HCloneable#createClone()
     */
    @Override
    public StepSequenceState<RESULT, PARENT> createClone()
    {
        final StepSequenceState<RESULT, PARENT> clone = new StepSequenceState<>( sequence );

        clone.currentStepIndex = currentStepIndex;
        clone.currentComplexState = ( currentComplexState != null ? currentComplexState.createClone() : null );

        return clone;
    }

}
