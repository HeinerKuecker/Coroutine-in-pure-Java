package de.heinerkuecker.coroutine.stmt.complex;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep;
import de.heinerkuecker.coroutine.stmt.CoroIterStepResult;

abstract class WhileOrDoWhileState<
    WHILE_OR_DO_WHILE extends WhileOrDoWhile<WHILE_OR_DO_WHILE, WHILE_OR_DO_WHILE_STATE, COROUTINE_RETURN/*, PARENT*/, RESUME_ARGUMENT>,
    WHILE_OR_DO_WHILE_STATE extends WhileOrDoWhileState<WHILE_OR_DO_WHILE, WHILE_OR_DO_WHILE_STATE, COROUTINE_RETURN/*, PARENT*/, RESUME_ARGUMENT>,
    COROUTINE_RETURN ,
    //PARENT extends CoroutineIterator<COROUTINE_RETURN>
    RESUME_ARGUMENT
    >
extends ComplexStepState<
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
    protected ComplexStepState<?, ?, COROUTINE_RETURN /*, PARENT*/, RESUME_ARGUMENT> bodyComplexState;

    //protected final CoroutineIterator<COROUTINE_RETURN> rootParent;
    //protected final CoroutineOrProcedureOrComplexstep<COROUTINE_RETURN, RESUME_ARGUMENT> parent;

    /**
     * Constructor.
     */
    protected WhileOrDoWhileState(
            final WhileOrDoWhile<WHILE_OR_DO_WHILE, WHILE_OR_DO_WHILE_STATE, COROUTINE_RETURN /*, PARENT*/, RESUME_ARGUMENT> whileOrDoWhile ,
            //final CoroutineIterator<COROUTINE_RETURN> rootParent
            final CoroutineOrProcedureOrComplexstep<COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        super( parent );
        this.whileOrDoWhile = whileOrDoWhile;

        //this.rootParent = Objects.requireNonNull( rootParent );

        //this.parent = Objects.requireNonNull( parent );
    }

    @Override
    public CoroIterStepResult<COROUTINE_RETURN> execute(
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
                        whileOrDoWhile.condition.execute(
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
                    return CoroIterStepResult.continueCoroutine();
                }
            }

            if ( runInBody )
            {
                final ComplexStep<?, ?, COROUTINE_RETURN /*, PARENT*/, RESUME_ARGUMENT> bodyStep =
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

                final CoroIterStepResult<COROUTINE_RETURN> bodyExecuteResult =
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

                if ( bodyExecuteResult instanceof CoroIterStepResult.Break )
                {
                    finish();
                    final String label = ( (CoroIterStepResult.Break<?>) bodyExecuteResult ).label;
                    if ( label == null ||
                            label.equals( whileOrDoWhile.label ) )
                    {
                        return CoroIterStepResult.continueCoroutine();
                    }
                    // break outer loop
                    return bodyExecuteResult;
                }
                else if ( bodyExecuteResult instanceof CoroIterStepResult.ContinueLoop )
                {
                    final String label = ( (CoroIterStepResult.ContinueLoop<?>) bodyExecuteResult ).label;
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
                        bodyExecuteResult instanceof CoroIterStepResult.ContinueCoroutine ) )
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
     * @see ComplexStepState#isFinished()
     */
    @Override
    public boolean isFinished()
    {
        return
                ( ! this.runInCondition ) &&
                ( ! this.runInBody );
    }

}
