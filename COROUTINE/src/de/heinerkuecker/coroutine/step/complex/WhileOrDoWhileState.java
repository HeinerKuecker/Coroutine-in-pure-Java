package de.heinerkuecker.coroutine.step.complex;

import java.util.Objects;

import de.heinerkuecker.coroutine.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.step.CoroIterStepResult;

abstract class WhileOrDoWhileState<
    WHILE_OR_DO_WHILE extends WhileOrDoWhile<WHILE_OR_DO_WHILE, WHILE_OR_DO_WHILE_STATE, RESULT/*, PARENT*/>,
    WHILE_OR_DO_WHILE_STATE extends WhileOrDoWhileState<WHILE_OR_DO_WHILE, WHILE_OR_DO_WHILE_STATE, RESULT/*, PARENT*/>,
    RESULT
    //PARENT extends CoroutineIterator<RESULT>
    >
implements ComplexStepState<
    WHILE_OR_DO_WHILE_STATE,
    WHILE_OR_DO_WHILE,
    RESULT
    //PARENT
    >
{
    protected final WhileOrDoWhile<WHILE_OR_DO_WHILE, WHILE_OR_DO_WHILE_STATE, RESULT /*, PARENT*/> whileOrDoWhile;

    // TODO getter
    protected boolean runInCondition;
    protected boolean runInBody;
    protected ComplexStepState<?, ?, RESULT /*, PARENT*/> bodyComplexState;

    //protected final CoroutineIterator<RESULT> rootParent;
    protected final CoroIteratorOrProcedure<RESULT> parent;

    /**
     * Constructor.
     */
    protected WhileOrDoWhileState(
            final WhileOrDoWhile<WHILE_OR_DO_WHILE, WHILE_OR_DO_WHILE_STATE, RESULT /*, PARENT*/> whileOrDoWhile ,
            //final CoroutineIterator<RESULT> rootParent
            final CoroIteratorOrProcedure<RESULT> parent )
    {
        this.whileOrDoWhile = whileOrDoWhile;

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
            //final PARENT parent
            final CoroIteratorOrProcedure<RESULT> parent )
    {
        while ( true )
        {
            if ( runInCondition )
            {
                // TODO ist dies notwendig?
                parent.saveLastStepState();

                final boolean conditionResult =
                        whileOrDoWhile.condition.execute(
                                parent );

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
                final ComplexStep<?, ?, RESULT /*, PARENT*/> bodyStep =
                        whileOrDoWhile.bodyComplexStep;

                if ( this.bodyComplexState == null )
                    // no existing state from previous execute call
                {
                    this.bodyComplexState =
                            bodyStep.newState(
                                    //this.rootParent
                                    this.parent );
                }

                // TODO only before executing simple step: parent.saveLastStepState();

                final CoroIterStepResult<RESULT> bodyExecuteResult =
                        this.bodyComplexState.execute(
                                parent );

                if ( this.bodyComplexState.isFinished() )
                {
                    this.runInCondition = true;
                    this.runInBody = false;
                    bodyComplexState = null;
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
                bodyComplexState = null;
            }
        }
    }

    private void finish()
    {
        this.runInCondition = false;
        this.runInBody = false;
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
