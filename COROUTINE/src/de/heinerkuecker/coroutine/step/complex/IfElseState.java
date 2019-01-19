package de.heinerkuecker.coroutine.step.complex;

import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep;
import de.heinerkuecker.coroutine.step.CoroIterStepResult;
import de.heinerkuecker.util.HCloneable;

class IfElseState<RESULT/*, PARENT extends CoroutineIterator<RESULT>*/>
extends ComplexStepState<
    IfElseState<RESULT/*, PARENT*/>,
    IfElse<RESULT/*, PARENT*/>,
    RESULT
    //PARENT
    >
{
    private final IfElse<RESULT/*, PARENT*/> ifElse;

    // TODO getter
    boolean runInCondition = true;
    private boolean runInThenBody;
    private boolean runInElseBody;

    // TODO getter
    ComplexStepState<?, ?, RESULT/*, PARENT*/> thenBodyComplexState;
    ComplexStepState<?, ?, RESULT/*, PARENT*/> elseBodyComplexState;

    //private final CoroutineIterator<RESULT> rootParent;
    private final CoroutineOrProcedureOrComplexstep<RESULT> parent;

    /**
     * Constructor.
     */
    public IfElseState(
            final IfElse<RESULT/*, PARENT*/> ifElse ,
            //final CoroutineIterator<RESULT> rootParent
            final CoroutineOrProcedureOrComplexstep<RESULT> parent )
    {
        super( parent );
        this.ifElse = ifElse;

        //this.rootParent = Objects.requireNonNull( rootParent );

        this.parent =
                Objects.requireNonNull(
                        parent );
    }

    @Override
    public CoroIterStepResult<RESULT> execute(
            final CoroutineOrProcedureOrComplexstep<RESULT> parent )
    {
        if ( runInCondition )
        {
            // TODO ist dies notwendig?
            parent.saveLastStepState();

            final boolean conditionResult = ifElse.condition.execute( parent );

            this.runInCondition = false;

            if ( conditionResult )
            {
                runInThenBody = true;
            }
            else
            {
                runInElseBody = true;
            }
        }

        if ( runInThenBody )
        {
            final ComplexStep<?, ?, RESULT/*, PARENT*/> thenBodyStep =
                    ifElse.thenBodyComplexStep;

            if ( this.thenBodyComplexState == null )
                // no existing state from previous execute call
            {
                this.thenBodyComplexState =
                        thenBodyStep.newState(
                                //this.rootParent
                                //this.parent
                                this );
            }

            // TODO only before executing simple step: parent.saveLastStepState();

            final CoroIterStepResult<RESULT> executeResult =
                    this.thenBodyComplexState.execute(
                            //parent
                            this );

            if ( this.thenBodyComplexState.isFinished() )
            {
                finish();
            }

            if ( ! ( executeResult == null ||
                    executeResult instanceof CoroIterStepResult.ContinueCoroutine ) )
            {
                return executeResult;
            }

            finish();
        }
        else if ( runInElseBody )
        {
            final ComplexStep<?, ?, RESULT/*, PARENT*/> elseBodyStep =
                    ifElse.elseBodyComplexStep;

            if ( this.elseBodyComplexState == null )
                // no existing state from previous execute call
            {
                this.elseBodyComplexState =
                        elseBodyStep.newState(
                                //this.rootParent
                                //this.parent
                                this );
            }

            // TODO only before executing simple step: parent.saveLastStepState();

            final CoroIterStepResult<RESULT> executeResult =
                    this.elseBodyComplexState.execute(
                            //parent
                            this );

            if ( this.elseBodyComplexState.isFinished() )
            {
                finish();
            }

            if ( ! ( executeResult == null ||
                    executeResult instanceof CoroIterStepResult.ContinueCoroutine ) )
            {
                return executeResult;
            }

            finish();
        }

        return CoroIterStepResult.continueCoroutine();
    }

    private void finish()
    {
        this.runInCondition = false;
        this.runInThenBody = false;
        this.runInElseBody = false;
    }

    /**
     * @see ComplexStepState#isFinished()
     */
    @Override
    public boolean isFinished()
    {
        return
                ( ! runInCondition ) &&
                ( ! runInThenBody ) &&
                ( ! runInElseBody );
    }

    /**
     * @see ComplexStepState#getStep()
     */
    @Override
    public IfElse<RESULT/*, PARENT*/> getStep()
    {
        return this.ifElse;
    }

    /**
     * @see HCloneable#createClone()
     */
    @Override
    public IfElseState<RESULT/*, PARENT*/> createClone()
    {
        final IfElseState<RESULT/*, PARENT*/> clone =
                new IfElseState<>(
                        ifElse ,
                        //this.rootParent
                        this.parent );

        clone.runInCondition = runInCondition;
        clone.runInThenBody = runInThenBody;
        clone.runInElseBody = runInElseBody;
        clone.thenBodyComplexState = ( thenBodyComplexState != null ? thenBodyComplexState.createClone() : null );
        clone.elseBodyComplexState = ( elseBodyComplexState != null ? elseBodyComplexState.createClone() : null );

        return clone;
    }

}
