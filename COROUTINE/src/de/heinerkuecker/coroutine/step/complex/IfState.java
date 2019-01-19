package de.heinerkuecker.coroutine.step.complex;

import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep;
import de.heinerkuecker.coroutine.step.CoroIterStepResult;
import de.heinerkuecker.util.HCloneable;

class IfState<RESULT/*, PARENT extends CoroutineIterator<RESULT>*/>
extends ComplexStepState<
    IfState<RESULT/*, PARENT*/>,
    If<RESULT/*, PARENT*/>,
    RESULT
    //PARENT
    >
{
    private final If<RESULT/*, PARENT*/> _if;

    // TODO getter
    boolean runInCondition = true;
    private boolean runInThenBody;

    // TODO getter
    ComplexStepState<?, ?, RESULT/*, PARENT*/> thenBodyComplexState;

    //private final CoroutineIterator<RESULT> rootParent;
    private final CoroutineOrProcedureOrComplexstep<RESULT> parent;

    /**
     * Constructor.
     */
    public IfState(
            final If<RESULT/*, PARENT*/> _if ,
            //final CoroutineIterator<RESULT> rootParent
            final CoroutineOrProcedureOrComplexstep<RESULT> parent )
    {
        super( parent );
        this._if = _if;

        //this.rootParent = Objects.requireNonNull( rootParent );

        this.parent =
                Objects.requireNonNull(
                        parent );
    }

    @Override
    public CoroIterStepResult<RESULT> execute(
            //final CoroutineOrProcedureOrComplexstep<RESULT> parent
            )
    {
        if ( this.runInCondition )
        {
            // TODO ist dies notwendig?
            parent.saveLastStepState();

            final boolean conditionResult =
                    _if.condition.execute(
                            //parent
                            this );

            this.runInCondition = false;

            if ( conditionResult )
            {
                runInThenBody = true;
            }
            else
            {
                finish();
                return CoroIterStepResult.continueCoroutine();
            }
        }

        if ( runInThenBody )
        {
            final ComplexStep<?, ?, RESULT/*, PARENT*/> thenBodyStep =
                    _if.thenBodyComplexStep;

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
                            //this
                            );

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

        return CoroIterStepResult.continueCoroutine();
    }

    private void finish()
    {
        this.runInCondition = false;
        this.runInThenBody = false;
        this.thenBodyComplexState = null;
    }

    /**
     * @see ComplexStepState#isFinished()
     */
    @Override
    public boolean isFinished()
    {
        return
                ( ! this.runInCondition ) &&
                ( ! this.runInThenBody );
    }

    /**
     * @see ComplexStepState#getStep()
     */
    @Override
    public If<RESULT/*, PARENT*/> getStep()
    {
        return _if;
    }

    /**
     * @see HCloneable#createClone()
     */
    @Override
    public IfState<RESULT/*, PARENT*/> createClone()
    {
        //throw new RuntimeException( "not implemented" );
        final IfState<RESULT/*, PARENT*/> clone =
                new IfState<>(
                        _if ,
                        //this.rootParent
                        this.parent );

        clone.runInCondition = runInCondition;
        clone.runInThenBody = runInThenBody;
        clone.thenBodyComplexState = ( thenBodyComplexState != null ? thenBodyComplexState.createClone() : null );

        return clone;
    }

}
