package de.heinerkuecker.coroutine_iterator.step.complex;

import java.util.Objects;

import de.heinerkuecker.coroutine_iterator.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine_iterator.step.result.CoroIterStepResult;
import de.heinerkuecker.util.HCloneable;

class ProcedureCallState<RESULT>
implements ComplexStepState<
    ProcedureCallState<RESULT>,
    ProcedureCall<RESULT>,
    RESULT
    //ProcedureCall<RESULT>
    >
{
    private final ProcedureCall<RESULT/*, PARENT*/> procedureCall;

    boolean runInProcedure = true;

    // TODO getter
    ComplexStepState<?, ?, RESULT/*, PARENT*/> bodyComplexState;

    /**
     * Constructor.
     *
     * @param procedureCall
     */
    protected ProcedureCallState(
            final ProcedureCall<RESULT> procedureCall )
    {
        this.procedureCall =
                Objects.requireNonNull(
                        procedureCall );
    }

    /**
     * @see ComplexStepState#execute
     */
    @Override
    public CoroIterStepResult<RESULT> execute(
            final CoroIteratorOrProcedure<RESULT> parent )
    {
        if ( runInProcedure )
        {
            final ComplexStep<?, ?, RESULT/*, PARENT*/> bodyComplexStep =
                    procedureCall.procedure.bodyComplexStep;

            if ( this.bodyComplexState == null )
                // no existing state from previous execute call
            {
                this.bodyComplexState = bodyComplexStep.newState();
            }

            // TODO only before executing simple step: parent.saveLastStepState();

            final CoroIterStepResult<RESULT> bodyExecuteResult =
                    this.bodyComplexState.execute(
                            //parent
                            this.procedureCall );

            if ( this.bodyComplexState.isFinished() )
            {
                finish();
            }

            if ( ! ( bodyExecuteResult == null ||
                    bodyExecuteResult instanceof CoroIterStepResult.ContinueCoroutine ) )
            {
                return bodyExecuteResult;
            }

            bodyComplexState = null;
            return bodyExecuteResult;
        }

        return CoroIterStepResult.continueCoroutine();
    }

    private void finish()
    {
        this.runInProcedure = false;
        bodyComplexState = null;
    }

    /**
     * @see ComplexStepState#isFinished()
     */
    @Override
    public boolean isFinished()
    {
        return ! runInProcedure;
    }

    /**
     * @see ComplexStepState#getStep()
     */
    @Override
    public ProcedureCall<RESULT> getStep()
    {
        return this.procedureCall;
    }

    /**
     * @see HCloneable#createClone()
     */
    @Override
    public ProcedureCallState<RESULT> createClone()
    {
        final ProcedureCallState<RESULT/*, PARENT*/> clone = new ProcedureCallState<>( procedureCall );

        clone.bodyComplexState = ( bodyComplexState != null ? bodyComplexState.createClone() : null );

        return clone;
    }

}
