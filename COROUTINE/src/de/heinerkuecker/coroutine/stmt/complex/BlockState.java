package de.heinerkuecker.coroutine.stmt.complex;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstmt;
import de.heinerkuecker.coroutine.stmt.CoroIterStmt;
import de.heinerkuecker.coroutine.stmt.CoroIterStmtResult;
import de.heinerkuecker.coroutine.stmt.simple.SimpleStmt;
import de.heinerkuecker.util.HCloneable;

class BlockState<COROUTINE_RETURN /*, PARENT extends CoroutineIterator<COROUTINE_RETURN>*/, RESUME_ARGUMENT>
extends ComplexStmtState<
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
    ComplexStmtState<?, ?, COROUTINE_RETURN /*, ? super PARENT*/, RESUME_ARGUMENT> currentComplexState;

    //private final CoroutineIterator<COROUTINE_RETURN> rootParent;
    //private final CoroutineOrProcedureOrComplexstmt<COROUTINE_RETURN, RESUME_ARGUMENT> parent;

    /**
     *
     */
    public BlockState(
            final Block<COROUTINE_RETURN /*, PARENT*/, RESUME_ARGUMENT> sequence ,
            //final CoroutineIterator<COROUTINE_RETURN> rootParent
            final CoroutineOrProcedureOrComplexstmt<COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        super( parent );
        this.sequence = sequence;

        //this.rootParent = Objects.requireNonNull( rootParent );

        //this.parent = Objects.requireNonNull( parent );

        if ( sequence.length() > 0 &&
                sequence.getStep( 0 ) instanceof ComplexStmt )
            // for toString
        {
            @SuppressWarnings("unchecked")
            final ComplexStmt<?, ?, COROUTINE_RETURN, RESUME_ARGUMENT> firstComplexStmt =
                    (ComplexStmt<? , ? , COROUTINE_RETURN , RESUME_ARGUMENT>) sequence.getStep( 0 );

            this.currentComplexState =
                    firstComplexStmt.newState( this );
        }
    }

    @Override
    public CoroIterStmtResult<COROUTINE_RETURN> execute(
            //final CoroutineOrProcedureOrComplexstmt<COROUTINE_RETURN, RESUME_ARGUMENT> parent
            )
    {
        while ( currentStepIndex < sequence.length() )
        {
            final CoroIterStmt<? extends COROUTINE_RETURN /*, ? super PARENT*/> currentStep =
                    sequence.getStep( this.currentStepIndex );

            final CoroIterStmtResult<COROUTINE_RETURN> executeResult;
            if ( currentStep instanceof SimpleStmt )
            {
                @SuppressWarnings("unchecked")
                final SimpleStmt<COROUTINE_RETURN /*, ? super PARENT*/, RESUME_ARGUMENT> currentSimpleStep =
                        (SimpleStmt<COROUTINE_RETURN /*, ? super PARENT*/ , RESUME_ARGUMENT>) currentStep;

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
                final ComplexStmt<?, ?, COROUTINE_RETURN /*, ? super PARENT*/, RESUME_ARGUMENT> currentComplexStep =
                        (ComplexStmt<?, ?, COROUTINE_RETURN /*, ? super PARENT*/, RESUME_ARGUMENT>) currentStep;

                if ( this.currentComplexState == null )
                    // no existing state from previous execute call
                {
                    this.currentComplexState =
                            currentComplexStep.newState(
                                    //this.rootParent
                                    //parent
                                    this );
                }

                // TODO only before executing simple stmt: parent.saveLastStepState();

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
                     sequence.getStep( this.currentStepIndex ) instanceof ComplexStmt )
            {
                this.currentComplexState =
                        ( (ComplexStmt<?, ?, COROUTINE_RETURN, RESUME_ARGUMENT>) sequence.getStep(
                                this.currentStepIndex ) ).newState(
                                        //this.rootParent
                                        //this.parent
                                        this );
            }

            if ( ! ( executeResult == null ||
                    executeResult instanceof CoroIterStmtResult.ContinueCoroutine ) )
            {
                return executeResult;
            }
        }
        return CoroIterStmtResult.continueCoroutine();
    }

    /**
     * @see ComplexStmtState#isFinished()
     */
    @Override
    public boolean isFinished()
    {
        return this.currentStepIndex >= sequence.length();
    }

    /**
     * @see ComplexStmtState#getStep()
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
