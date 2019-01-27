package de.heinerkuecker.coroutine.step.complex;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep;
import de.heinerkuecker.coroutine.step.CoroIterStep;
import de.heinerkuecker.coroutine.step.CoroIterStepResult;
import de.heinerkuecker.coroutine.step.simple.SimpleStep;
import de.heinerkuecker.util.HCloneable;

class BlockState<RESULT /*, PARENT extends CoroutineIterator<RESULT>*/, RESUME_ARGUMENT>
extends ComplexStepState<
    BlockState<RESULT /*, PARENT*/, RESUME_ARGUMENT>,
    Block<RESULT /*, PARENT*/, RESUME_ARGUMENT>,
    RESULT ,
    /*, PARENT*/
    RESUME_ARGUMENT
    >
{
    private final Block<RESULT /*, PARENT*/, RESUME_ARGUMENT> sequence;

    // TODO getter to ensure unmodifiable from other class
    int currentStepIndex;
    ComplexStepState<?, ?, RESULT /*, ? super PARENT*/, RESUME_ARGUMENT> currentComplexState;

    //private final CoroutineIterator<RESULT> rootParent;
    //private final CoroutineOrProcedureOrComplexstep<RESULT, RESUME_ARGUMENT> parent;

    /**
     *
     */
    public BlockState(
            final Block<RESULT /*, PARENT*/, RESUME_ARGUMENT> sequence ,
            //final CoroutineIterator<RESULT> rootParent
            final CoroutineOrProcedureOrComplexstep<RESULT, RESUME_ARGUMENT> parent )
    {
        super( parent );
        this.sequence = sequence;

        //this.rootParent = Objects.requireNonNull( rootParent );

        //this.parent = Objects.requireNonNull( parent );

        if ( sequence.length() > 0 &&
                sequence.getStep( 0 ) instanceof ComplexStep )
        {
            // for toString
            this.currentComplexState =
                    ( (ComplexStep) sequence.getStep( 0 ) ).newState( this );
        }
    }

    @Override
    public CoroIterStepResult<RESULT> execute(
            //final CoroutineOrProcedureOrComplexstep<RESULT, RESUME_ARGUMENT> parent
            )
    {
        while ( currentStepIndex < sequence.length() )
        {
            final CoroIterStep<? extends RESULT /*, ? super PARENT*/> currentStep =
                    sequence.getStep( this.currentStepIndex );

            final CoroIterStepResult<RESULT> executeResult;
            if ( currentStep instanceof SimpleStep )
            {
                @SuppressWarnings("unchecked")
                final SimpleStep<RESULT /*, ? super PARENT*/, RESUME_ARGUMENT> currentSimpleStep =
                        (SimpleStep<RESULT /*, ? super PARENT*/ , RESUME_ARGUMENT>) currentStep;

                parent.saveLastStepState();

                executeResult =
                        currentSimpleStep.execute(
                                //parent
                                this );

                this.currentStepIndex++;
            }
            else
            {
                @SuppressWarnings("unchecked")
                final ComplexStep<?, ?, RESULT /*, ? super PARENT*/, RESUME_ARGUMENT> currentComplexStep =
                        (ComplexStep<?, ?, RESULT /*, ? super PARENT*/, RESUME_ARGUMENT>) currentStep;

                if ( this.currentComplexState == null )
                    // no existing state from previous execute call
                {
                    this.currentComplexState =
                            currentComplexStep.newState(
                                    //this.rootParent
                                    //parent
                                    this );
                }

                // TODO only before executing simple step: parent.saveLastStepState();

                executeResult =
                        this.currentComplexState.execute(
                                //parent
                                //this
                                );

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
                        ( (ComplexStep<?, ?, RESULT, RESUME_ARGUMENT>) sequence.getStep(
                                this.currentStepIndex ) ).newState(
                                        //this.rootParent
                                        //this.parent
                                        this );
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
        return this.currentStepIndex >= sequence.length();
    }

    /**
     * @see ComplexStepState#getStep()
     */
    @Override
    public Block<RESULT /*, PARENT*/, RESUME_ARGUMENT> getStep()
    {
        return this.sequence;
    }

    /**
     * @see HCloneable#createClone()
     */
    @Override
    public BlockState<RESULT /*, PARENT*/, RESUME_ARGUMENT> createClone()
    {
        final BlockState<RESULT /*, PARENT*/, RESUME_ARGUMENT> clone =
                new BlockState<>(
                        sequence ,
                        //this.rootParent
                        this.parent );

        clone.currentStepIndex = currentStepIndex;
        clone.currentComplexState = ( currentComplexState != null ? currentComplexState.createClone() : null );

        return clone;
    }

}
