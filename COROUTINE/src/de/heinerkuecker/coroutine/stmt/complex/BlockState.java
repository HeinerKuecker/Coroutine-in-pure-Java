package de.heinerkuecker.coroutine.stmt.complex;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep;
import de.heinerkuecker.coroutine.stmt.CoroIterStep;
import de.heinerkuecker.coroutine.stmt.CoroIterStepResult;
import de.heinerkuecker.coroutine.stmt.simple.SimpleStep;
import de.heinerkuecker.util.HCloneable;

class BlockState<COROUTINE_RETURN /*, PARENT extends CoroutineIterator<COROUTINE_RETURN>*/, RESUME_ARGUMENT>
extends ComplexStepState<
    BlockState<COROUTINE_RETURN /*, PARENT*/, RESUME_ARGUMENT>,
    Block<COROUTINE_RETURN /*, PARENT*/, RESUME_ARGUMENT>,
    COROUTINE_RETURN ,
    /*, PARENT*/
    RESUME_ARGUMENT
    >
{
    private final Block<COROUTINE_RETURN /*, PARENT*/, RESUME_ARGUMENT> sequence;

    // TODO getter to ensure unmodifiable from other class
    int currentStepIndex;
    ComplexStepState<?, ?, COROUTINE_RETURN /*, ? super PARENT*/, RESUME_ARGUMENT> currentComplexState;

    //private final CoroutineIterator<COROUTINE_RETURN> rootParent;
    //private final CoroutineOrProcedureOrComplexstep<COROUTINE_RETURN, RESUME_ARGUMENT> parent;

    /**
     *
     */
    public BlockState(
            final Block<COROUTINE_RETURN /*, PARENT*/, RESUME_ARGUMENT> sequence ,
            //final CoroutineIterator<COROUTINE_RETURN> rootParent
            final CoroutineOrProcedureOrComplexstep<COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        super( parent );
        this.sequence = sequence;

        //this.rootParent = Objects.requireNonNull( rootParent );

        //this.parent = Objects.requireNonNull( parent );

        if ( sequence.length() > 0 &&
                sequence.getStep( 0 ) instanceof ComplexStep )
            // for toString
        {
            @SuppressWarnings("unchecked")
            final ComplexStep<?, ?, COROUTINE_RETURN, RESUME_ARGUMENT> firstComplexStmt =
                    (ComplexStep<? , ? , COROUTINE_RETURN , RESUME_ARGUMENT>) sequence.getStep( 0 );

            this.currentComplexState =
                    firstComplexStmt.newState( this );
        }
    }

    @Override
    public CoroIterStepResult<COROUTINE_RETURN> execute(
            //final CoroutineOrProcedureOrComplexstep<COROUTINE_RETURN, RESUME_ARGUMENT> parent
            )
    {
        while ( currentStepIndex < sequence.length() )
        {
            final CoroIterStep<? extends COROUTINE_RETURN /*, ? super PARENT*/> currentStep =
                    sequence.getStep( this.currentStepIndex );

            final CoroIterStepResult<COROUTINE_RETURN> executeResult;
            if ( currentStep instanceof SimpleStep )
            {
                @SuppressWarnings("unchecked")
                final SimpleStep<COROUTINE_RETURN /*, ? super PARENT*/, RESUME_ARGUMENT> currentSimpleStep =
                        (SimpleStep<COROUTINE_RETURN /*, ? super PARENT*/ , RESUME_ARGUMENT>) currentStep;

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
                final ComplexStep<?, ?, COROUTINE_RETURN /*, ? super PARENT*/, RESUME_ARGUMENT> currentComplexStep =
                        (ComplexStep<?, ?, COROUTINE_RETURN /*, ? super PARENT*/, RESUME_ARGUMENT>) currentStep;

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
                        ( (ComplexStep<?, ?, COROUTINE_RETURN, RESUME_ARGUMENT>) sequence.getStep(
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
    public Block<COROUTINE_RETURN /*, PARENT*/, RESUME_ARGUMENT> getStep()
    {
        return this.sequence;
    }

    /**
     * @see HCloneable#createClone()
     */
    @Override
    public BlockState<COROUTINE_RETURN /*, PARENT*/, RESUME_ARGUMENT> createClone()
    {
        final BlockState<COROUTINE_RETURN /*, PARENT*/, RESUME_ARGUMENT> clone =
                new BlockState<>(
                        sequence ,
                        //this.rootParent
                        this.parent );

        clone.currentStepIndex = currentStepIndex;
        clone.currentComplexState = ( currentComplexState != null ? currentComplexState.createClone() : null );

        return clone;
    }

}
