package de.heinerkuecker.coroutine.stmt.complex;

import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep;
import de.heinerkuecker.coroutine.stmt.CoroIterStepResult;
import de.heinerkuecker.util.HCloneable;

class IfElseState<COROUTINE_RETURN/*, PARENT extends CoroutineIterator<COROUTINE_RETURN>*/ , RESUME_ARGUMENT>
extends ComplexStepState<
    IfElseState<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT>,
    IfElse<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT>,
    COROUTINE_RETURN ,
    //PARENT
    RESUME_ARGUMENT
    >
{
    private final IfElse<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> ifElse;

    // TODO getter
    boolean runInCondition = true;
    private boolean runInThenBody;
    private boolean runInElseBody;

    // TODO getter
    ComplexStepState<?, ?, COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> thenBodyComplexState;
    ComplexStepState<?, ?, COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> elseBodyComplexState;

    //private final CoroutineIterator<COROUTINE_RETURN> rootParent;
    private final CoroutineOrProcedureOrComplexstep<COROUTINE_RETURN, RESUME_ARGUMENT> parent;

    /**
     * Constructor.
     */
    public IfElseState(
            final IfElse<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> ifElse ,
            //final CoroutineIterator<COROUTINE_RETURN> rootParent
            final CoroutineOrProcedureOrComplexstep<COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        super( parent );
        this.ifElse = ifElse;

        //this.rootParent = Objects.requireNonNull( rootParent );

        this.parent =
                Objects.requireNonNull(
                        parent );
    }

    @Override
    public CoroIterStepResult<COROUTINE_RETURN> execute(
            //final CoroutineOrProcedureOrComplexstep<COROUTINE_RETURN, RESUME_ARGUMENT> parent
            )
    {
        if ( runInCondition )
        {
            // TODO ist dies notwendig?
            parent.saveLastStepState();

            final boolean conditionResult =
                    ifElse.condition.evaluate(
                            //parent
                            this );

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
            final ComplexStep<?, ?, COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> thenBodyStep =
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

            final CoroIterStepResult<COROUTINE_RETURN> executeResult =
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
        else if ( runInElseBody )
        {
            final ComplexStep<?, ?, COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> elseBodyStep =
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

            final CoroIterStepResult<COROUTINE_RETURN> executeResult =
                    this.elseBodyComplexState.execute(
                            //parent
                            //this
                            );

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
    public IfElse<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> getStep()
    {
        return this.ifElse;
    }

    /**
     * @see HCloneable#createClone()
     */
    @Override
    public IfElseState<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> createClone()
    {
        final IfElseState<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> clone =
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
