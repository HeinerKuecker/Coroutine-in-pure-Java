package de.heinerkuecker.coroutine_iterator.step.complex;

import de.heinerkuecker.coroutine_iterator.CoroutineIterator;
import de.heinerkuecker.coroutine_iterator.step.result.CoroIterStepResult;
import de.heinerkuecker.util.HCloneable;

public class DoWhileState<RESULT, PARENT extends CoroutineIterator<RESULT>>
implements ComplexStepState<DoWhileState<RESULT, PARENT>, DoWhile<RESULT, PARENT>, RESULT, PARENT>
{
    private final DoWhile<RESULT, PARENT> doWhile;

    // TODO getter
    boolean runInCondition;
    private boolean runInBody = true;

    // TODO getter
    ComplexStepState<?, ?, RESULT, PARENT> bodyComplexState;

    /**
     * Constructor.
     */
    public DoWhileState(
            final DoWhile<RESULT, PARENT> doWhile )
    {
        this.doWhile = doWhile;
    }

    /**
     * @see ComplexStepState#execute(CoroutineIterator)
     */
    @Override
    public CoroIterStepResult<RESULT> execute(
            final PARENT parent )
    {
        while ( true )
        {
            if ( runInCondition )
            {
                // TODO ist dies notwendig?
                parent.saveLastStepState();

                final boolean conditionResult = doWhile.condition.execute( parent );

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
                final ComplexStep<?, ?, RESULT, PARENT> bodyStep =
                        doWhile.bodyComplexStep;

                if ( this.bodyComplexState == null )
                    // no existing state from previous execute call
                {
                    this.bodyComplexState = bodyStep.newState();
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
                            label.equals( doWhile.label ) )
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
                            ! label.equals( doWhile.label ) )
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

    /**
     * @see ComplexStepState#getStep()
     */
    @Override
    public DoWhile<RESULT, PARENT> getStep()
    {
        return doWhile;
    }

    /**
     * @see HCloneable#createClone()
     */
    @Override
    public DoWhileState<RESULT, PARENT> createClone()
    {
        final DoWhileState<RESULT, PARENT> clone = new DoWhileState<RESULT, PARENT>( doWhile );

        clone.runInCondition = runInCondition;
        clone.runInBody = runInBody;
        clone.bodyComplexState = ( bodyComplexState != null ? bodyComplexState.createClone() : null );

        return clone;
    }

}
