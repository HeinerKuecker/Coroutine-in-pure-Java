package de.heinerkuecker.coroutine.stmt.complex;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstmt;
import de.heinerkuecker.coroutine.stmt.CoroIterStmtResult;

abstract class WhileOrDoWhileState<
    WHILE_OR_DO_WHILE extends WhileOrDoWhile<WHILE_OR_DO_WHILE, WHILE_OR_DO_WHILE_STATE, COROUTINE_RETURN/*, PARENT*/, RESUME_ARGUMENT>,
    WHILE_OR_DO_WHILE_STATE extends WhileOrDoWhileState<WHILE_OR_DO_WHILE, WHILE_OR_DO_WHILE_STATE, COROUTINE_RETURN/*, PARENT*/, RESUME_ARGUMENT>,
    COROUTINE_RETURN ,
    //PARENT extends CoroutineIterator<COROUTINE_RETURN>
    RESUME_ARGUMENT
    >
extends ComplexStmtState<
    WHILE_OR_DO_WHILE_STATE,
    WHILE_OR_DO_WHILE,
    COROUTINE_RETURN ,
    //PARENT
    RESUME_ARGUMENT
    >
{
    protected final WhileOrDoWhile<WHILE_OR_DO_WHILE, WHILE_OR_DO_WHILE_STATE, COROUTINE_RETURN /*, PARENT*/, RESUME_ARGUMENT> whileOrDoWhile;

    // TODO getter
    protected boolean runInCondition;
    protected boolean runInBody;
    protected ComplexStmtState<?, ?, COROUTINE_RETURN /*, PARENT*/, RESUME_ARGUMENT> bodyComplexState;

    //protected final CoroutineIterator<COROUTINE_RETURN> rootParent;
    //protected final CoroutineOrProcedureOrComplexstep<COROUTINE_RETURN, RESUME_ARGUMENT> parent;

    /**
     * Constructor.
     */
    protected WhileOrDoWhileState(
            final WhileOrDoWhile<WHILE_OR_DO_WHILE, WHILE_OR_DO_WHILE_STATE, COROUTINE_RETURN /*, PARENT*/, RESUME_ARGUMENT> whileOrDoWhile ,
            //final CoroutineIterator<COROUTINE_RETURN> rootParent
            final CoroutineOrProcedureOrComplexstmt<COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        super( parent );
        this.whileOrDoWhile = whileOrDoWhile;

        //this.rootParent = Objects.requireNonNull( rootParent );

        //this.parent = Objects.requireNonNull( parent );
    }

    @Override
    public CoroIterStmtResult<COROUTINE_RETURN> execute(
            //final PARENT parent
            //final CoroutineOrProcedureOrComplexstep<COROUTINE_RETURN, RESUME_ARGUMENT> parent
            )
    {
        while ( true )
        {
            if ( runInCondition )
            {
                // for debug toString
                parent.saveLastStepState();

                final boolean conditionResult =
                        whileOrDoWhile.condition.evaluate(
                                //parent
                                this );

                this.runInCondition = false;

                if ( conditionResult )
                {
                    runInBody = true;
                }
                else
                {
                    finish();
                    return CoroIterStmtResult.continueCoroutine();
                }
            }

            if ( runInBody )
            {
                final ComplexStmt<?, ?, COROUTINE_RETURN /*, PARENT*/, RESUME_ARGUMENT> bodyStep =
                        whileOrDoWhile.bodyComplexStep;

                if ( this.bodyComplexState == null )
                    // no existing state from previous execute call
                {
                    this.bodyComplexState =
                            bodyStep.newState(
                                    //this.rootParent
                                    //this.parent
                                    this );
                }

                // TODO only before executing simple step: parent.saveLastStepState();

                final CoroIterStmtResult<COROUTINE_RETURN> bodyExecuteResult =
                        this.bodyComplexState.execute(
                                //parent
                                //this
                                );

                if ( this.bodyComplexState.isFinished() )
                {
                    this.runInCondition = true;
                    this.runInBody = false;
                    this.bodyComplexState = null;
                }

                if ( bodyExecuteResult instanceof CoroIterStmtResult.Break )
                {
                    finish();
                    final String label = ( (CoroIterStmtResult.Break<?>) bodyExecuteResult ).label;
                    if ( label == null ||
                            label.equals( whileOrDoWhile.label ) )
                    {
                        return CoroIterStmtResult.continueCoroutine();
                    }
                    // break outer loop
                    return bodyExecuteResult;
                }
                else if ( bodyExecuteResult instanceof CoroIterStmtResult.ContinueLoop )
                {
                    final String label = ( (CoroIterStmtResult.ContinueLoop<?>) bodyExecuteResult ).label;
                    if ( label != null &&
                            ! label.equals( whileOrDoWhile.label ) )
                        // continue outer loop
                    {
                        finish();
                        return bodyExecuteResult;
                    }
                    // default behaviour
                }
                else if ( ! ( bodyExecuteResult == null ||
                        bodyExecuteResult instanceof CoroIterStmtResult.ContinueCoroutine ) )
                {
                    return bodyExecuteResult;
                }

                this.runInCondition = true;
                this.runInBody = false;
                this.bodyComplexState = null;
                super.localVars().reset();
            }
        }
    }

    private void finish()
    {
        this.runInCondition = false;
        this.runInBody = false;
        // keep for debug: this.bodyComplexState = null;
    }

    /**
     * @see ComplexStmtState#isFinished()
     */
    @Override
    public boolean isFinished()
    {
        return
                ( ! this.runInCondition ) &&
                ( ! this.runInBody );
    }

}
