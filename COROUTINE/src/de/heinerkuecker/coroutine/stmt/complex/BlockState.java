package de.heinerkuecker.coroutine.stmt.complex;

import de.heinerkuecker.coroutine.CoroutineOrFunctioncallOrComplexstmt;
import de.heinerkuecker.coroutine.stmt.CoroStmt;
import de.heinerkuecker.coroutine.stmt.CoroStmtResult;
import de.heinerkuecker.coroutine.stmt.simple.SimpleStmt;
import de.heinerkuecker.util.HCloneable;

class BlockState<FUNCTION_RETURN , COROUTINE_RETURN /*, PARENT extends CoroutineIterator<COROUTINE_RETURN>*/, RESUME_ARGUMENT>
extends ComplexStmtState<
    BlockState<FUNCTION_RETURN , COROUTINE_RETURN /*, PARENT*/, RESUME_ARGUMENT>,
    Block<FUNCTION_RETURN , COROUTINE_RETURN /*, PARENT*/, RESUME_ARGUMENT>,
    FUNCTION_RETURN ,
    COROUTINE_RETURN ,
    /*, PARENT*/
    RESUME_ARGUMENT
    >
{
    private final Block<FUNCTION_RETURN , COROUTINE_RETURN /*, PARENT*/, RESUME_ARGUMENT> sequence;

    // TODO getter to ensure unmodifiable from other class
    int currentStmtIndex;
    ComplexStmtState<?, ?, FUNCTION_RETURN , COROUTINE_RETURN /*, ? super PARENT*/, RESUME_ARGUMENT> currentComplexState;

    //private final CoroutineIterator<COROUTINE_RETURN> rootParent;
    //private final CoroutineOrFunctioncallOrComplexstmt<COROUTINE_RETURN, RESUME_ARGUMENT> parent;

    /**
     *
     */
    public BlockState(
            final Block<FUNCTION_RETURN , COROUTINE_RETURN /*, PARENT*/, RESUME_ARGUMENT> sequence ,
            //final CoroutineIterator<COROUTINE_RETURN> rootParent
            final CoroutineOrFunctioncallOrComplexstmt<FUNCTION_RETURN , COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        super( parent );
        this.sequence = sequence;

        //this.rootParent = Objects.requireNonNull( rootParent );

        //this.parent = Objects.requireNonNull( parent );

        if ( sequence.length() > 0 &&
                sequence.getStmt( 0 ) instanceof ComplexStmt )
            // for toString
        {
            @SuppressWarnings("unchecked")
            final ComplexStmt<?, ?, FUNCTION_RETURN , COROUTINE_RETURN, RESUME_ARGUMENT> firstComplexStmt =
                    (ComplexStmt<? , ? , FUNCTION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT>) sequence.getStmt( 0 );

            this.currentComplexState =
                    firstComplexStmt.newState( this );
        }
    }

    @Override
    public CoroStmtResult<FUNCTION_RETURN , COROUTINE_RETURN> execute(
            //final CoroutineOrFunctioncallOrComplexstmt<COROUTINE_RETURN, RESUME_ARGUMENT> parent
            )
    {
        while ( currentStmtIndex < sequence.length() )
        {
            final CoroStmt<FUNCTION_RETURN , ? extends COROUTINE_RETURN /*, ? super PARENT*/> currentStmt =
                    sequence.getStmt( this.currentStmtIndex );

            final CoroStmtResult<FUNCTION_RETURN , COROUTINE_RETURN> executeResult;
            if ( currentStmt instanceof SimpleStmt )
            {
                @SuppressWarnings("unchecked")
                final SimpleStmt<FUNCTION_RETURN , COROUTINE_RETURN /*, ? super PARENT*/, RESUME_ARGUMENT> currentSimpleStmt =
                        (SimpleStmt<FUNCTION_RETURN , COROUTINE_RETURN /*, ? super PARENT*/ , RESUME_ARGUMENT>) currentStmt;

                parent.saveLastStmtState();

                executeResult =
                        currentSimpleStmt.execute(
                                //parent
                                this );

                this.currentStmtIndex++;
            }
            else
            {
                @SuppressWarnings("unchecked")
                final ComplexStmt<?, ?, FUNCTION_RETURN , COROUTINE_RETURN /*, ? super PARENT*/, RESUME_ARGUMENT> currentComplexStmt =
                        (ComplexStmt<?, ?, FUNCTION_RETURN , COROUTINE_RETURN /*, ? super PARENT*/, RESUME_ARGUMENT>) currentStmt;

                if ( this.currentComplexState == null )
                    // no existing state from previous execute call
                {
                    this.currentComplexState =
                            currentComplexStmt.newState(
                                    //this.rootParent
                                    //parent
                                    this );
                }

                // TODO only before executing simple stmt: parent.saveLastStmtState();

                executeResult =
                        this.currentComplexState.execute(
                                //parent
                                //this
                                );

                if ( this.currentComplexState.isFinished() )
                {
                    this.currentStmtIndex++;
                    this.currentComplexState = null;
                }
            }

            // TODO only for toString
            if ( ! this.isFinished() &&
                    this.currentComplexState == null &&
                     sequence.getStmt( this.currentStmtIndex ) instanceof ComplexStmt )
            {
                this.currentComplexState =
                        ( (ComplexStmt<?, ?, FUNCTION_RETURN , COROUTINE_RETURN, RESUME_ARGUMENT>) sequence.getStmt(
                                this.currentStmtIndex ) ).newState(
                                        //this.rootParent
                                        //this.parent
                                        this );
            }

            if ( ! ( executeResult == null ||
                    executeResult instanceof CoroStmtResult.ContinueCoroutine ) )
            {
                return executeResult;
            }
        }
        return CoroStmtResult.continueCoroutine();
    }

    /**
     * @see ComplexStmtState#isFinished()
     */
    @Override
    public boolean isFinished()
    {
        return this.currentStmtIndex >= sequence.length();
    }

    /**
     * @see ComplexStmtState#getStmt()
     */
    @Override
    public Block<FUNCTION_RETURN , COROUTINE_RETURN /*, PARENT*/, RESUME_ARGUMENT> getStmt()
    {
        return this.sequence;
    }

    /**
     * @see HCloneable#createClone()
     */
    @Override
    public BlockState<FUNCTION_RETURN , COROUTINE_RETURN /*, PARENT*/, RESUME_ARGUMENT> createClone()
    {
        final BlockState<FUNCTION_RETURN , COROUTINE_RETURN /*, PARENT*/, RESUME_ARGUMENT> clone =
                new BlockState<>(
                        sequence ,
                        //this.rootParent
                        this.parent );

        clone.currentStmtIndex = currentStmtIndex;
        clone.currentComplexState = ( currentComplexState != null ? currentComplexState.createClone() : null );

        return clone;
    }

}
