package de.heinerkuecker.coroutine_iterator.step.complex;

import java.util.Objects;

import de.heinerkuecker.coroutine_iterator.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine_iterator.step.result.CoroIterStepResult;
import de.heinerkuecker.util.HCloneable;

public class ProcedureState<RESULT>
implements ComplexStepState<
ProcedureState<RESULT>,
Procedure<RESULT>,
RESULT
//Procedure<RESULT>
>
{
    private final Procedure<RESULT/*, PARENT*/> procedure;

    boolean runInProcedure = true;

    // TODO getter
    ComplexStepState<?, ?, RESULT/*, PARENT*/> bodyComplexState;

    /**
     * Constructor.
     *
     * @param procedure
     */
    protected ProcedureState(
            final Procedure<RESULT> procedure )
    {
        this.procedure =
                Objects.requireNonNull(
                        procedure );
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
                    procedure.bodyComplexStep;

            if ( this.bodyComplexState == null )
                // no existing state from previous execute call
            {
                this.bodyComplexState = bodyComplexStep.newState();
            }

            // TODO only before executing simple step: parent.saveLastStepState();

            final CoroIterStepResult<RESULT> bodyExecuteResult =
                    this.bodyComplexState.execute(
                            parent );

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
    public Procedure<RESULT> getStep()
    {
        return this.procedure;
    }

    /**
     * @see HCloneable#createClone()
     */
    @Override
    public ProcedureState<RESULT> createClone()
    {
        final ProcedureState<RESULT/*, PARENT*/> clone = new ProcedureState<>( procedure );

        clone.bodyComplexState = ( bodyComplexState != null ? bodyComplexState.createClone() : null );

        return clone;
    }

}
