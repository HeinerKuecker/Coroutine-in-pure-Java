package de.heinerkuecker.coroutine.step.complex;

import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep;
import de.heinerkuecker.coroutine.step.CoroIterStep;
import de.heinerkuecker.coroutine.step.CoroIterStepResult;
import de.heinerkuecker.coroutine.step.simple.SimpleStep;
import de.heinerkuecker.util.HCloneable;

class ForState<RESULT/*, PARENT extends CoroutineIterator<RESULT>*/>
extends ComplexStepState<
    ForState<RESULT/*, PARENT*/>,
    For<RESULT/*, PARENT*/>,
    RESULT
    //PARENT
    >
{
    private final For<RESULT/*, PARENT*/> _for;

    // TODO getter
    boolean runInInitializer = true;
    boolean runInCondition;
    boolean runInBody;
    boolean runInUpdate;

    private ComplexStepState<? ,?, RESULT/*, ? super PARENT*/> initializerComplexStepState;
    // TODO getter
    ComplexStepState<?, ?, RESULT/*, PARENT*/> bodyComplexState;
    private ComplexStepState<?, ?, RESULT/*, ? super PARENT*/> updateComplexStepState;

    //private final CoroutineIterator<RESULT> rootParent;
    private final CoroutineOrProcedureOrComplexstep<RESULT> parent;

    /**
     * Constructor.
     */
    public ForState(
            final For<RESULT/*, PARENT*/> _for ,
            //final CoroutineIterator<RESULT> rootParent
            final CoroutineOrProcedureOrComplexstep<RESULT> parent )
    {
        super( parent );
        this._for = _for;

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
            final CoroutineOrProcedureOrComplexstep<RESULT> parent )
    {
        if ( runInInitializer )
        {
            final CoroIterStep<RESULT /*, ? super PARENT*/> initializerStep =
                    _for.initialStep;

            final CoroIterStepResult<RESULT> initializerExecuteResult;
            if ( initializerStep instanceof SimpleStep )
            {
                final SimpleStep<RESULT/*, ? super PARENT*/> initializerSimpleStep =
                        (SimpleStep<RESULT/*, ? super PARENT*/>) initializerStep;

                parent.saveLastStepState();

                initializerExecuteResult =
                        initializerSimpleStep.execute(
                                parent );

                runInInitializer = false;
                runInCondition = true;
            }
            else
            {
                final ComplexStep<?, ?, RESULT/*, ? super PARENT*/> initializerComplexStep =
                        (ComplexStep<?, ?, RESULT/*, ? super PARENT*/>) initializerStep;

                if ( this.initializerComplexStepState == null )
                    // no existing state from previous execute call
                {
                    this.initializerComplexStepState =
                            initializerComplexStep.newState(
                                    //this.rootParent
                                    this.parent );
                }

                // TODO only before executing simple step: parent.saveLastStepState();

                initializerExecuteResult =
                        this.initializerComplexStepState.execute(
                                parent );

                if ( this.initializerComplexStepState.isFinished() )
                {
                    runInInitializer = false;
                    runInCondition = true;
                    this.initializerComplexStepState = null;
                }
            }

            if ( initializerExecuteResult instanceof CoroIterStepResult.Break ||
                    initializerExecuteResult instanceof CoroIterStepResult.ContinueLoop )
            {
                throw new IllegalStateException( String.valueOf( initializerExecuteResult ) );
            }

            if ( ! ( initializerExecuteResult == null ||
                    initializerExecuteResult instanceof CoroIterStepResult.ContinueCoroutine ) )
            {
                return initializerExecuteResult;
            }

            runInInitializer = false;
            runInCondition = true;
            this.initializerComplexStepState = null;
        }

        while ( true )
        {
            if ( runInCondition )
            {
                // TODO ist dies notwendig?
                parent.saveLastStepState();

                final boolean conditionResult =
                        _for.condition.execute( parent );

                if ( conditionResult )
                {
                    this.runInCondition = false;
                    this.runInBody = true;
                    // for toString
                    this.bodyComplexState =
                            _for.bodyComplexStep.newState(
                                    //this.rootParent
                                    this.parent );
                }
                else
                {
                    finish();
                    return CoroIterStepResult.continueCoroutine();
                }
            }

            if ( runInBody )
            {
                final ComplexStep<?, ?, RESULT/*, PARENT*/> bodyComplexStep =
                        _for.bodyComplexStep;

                if ( this.bodyComplexState == null )
                    // no existing state from previous execute call
                {
                    this.bodyComplexState =
                            bodyComplexStep.newState(
                                    //this.rootParent
                                    this.parent );
                }

                // TODO only before executing simple step: parent.saveLastStepState();

                final CoroIterStepResult<RESULT> bodyExecuteResult =
                        this.bodyComplexState.execute(
                                parent );

                if ( this.bodyComplexState.isFinished() )
                {
                    this.runInBody = false;
                    this.runInUpdate = true;
                    this.bodyComplexState = null;
                }

                if ( bodyExecuteResult instanceof CoroIterStepResult.Break )
                {
                    finish();
                    final String label = ( (CoroIterStepResult.Break<?>) bodyExecuteResult ).label;
                    if ( label == null ||
                            label.equals( _for.label ) )
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
                            ! label.equals( _for.label ) )
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

                this.runInBody = false;
                this.runInUpdate = true;
                this.bodyComplexState = null;
            }

            if ( runInUpdate )
            {
                final CoroIterStep<RESULT/*, ? super PARENT*/> updateStep =
                        _for.updateStep;

                CoroIterStepResult<RESULT> updateExecuteResult;
                if ( updateStep instanceof SimpleStep )
                {
                    final SimpleStep<RESULT/*, ? super PARENT*/> updateSimpleStep =
                            (SimpleStep<RESULT/*, ? super PARENT*/>) updateStep;

                    parent.saveLastStepState();

                    updateExecuteResult =
                            updateSimpleStep.execute(
                                    parent );

                    this.runInUpdate = false;
                    this.runInCondition = true;
                }
                else
                {
                    final ComplexStep<?, ?, RESULT/*, ? super PARENT*/> updateComplexStep =
                            (ComplexStep<?, ?, RESULT/*, ? super PARENT*/>) updateStep;

                    if ( this.updateComplexStepState == null )
                        // no existing state from previous execute call
                    {
                        this.updateComplexStepState =
                                updateComplexStep.newState(
                                        //this.rootParent
                                        this.parent );
                    }

                    // TODO only before executing simple step: parent.saveLastStepState();

                    updateExecuteResult =
                            this.updateComplexStepState.execute(
                                    parent );

                    if ( this.updateComplexStepState.isFinished() )
                    {
                        this.runInUpdate = false;
                        this.runInCondition = true;
                        this.updateComplexStepState = null;
                    }
                }

                if ( updateExecuteResult instanceof CoroIterStepResult.Break ||
                        updateExecuteResult instanceof CoroIterStepResult.ContinueLoop )
                {
                    throw new IllegalStateException( String.valueOf( updateExecuteResult ) );
                }

                if ( ! ( updateExecuteResult == null ||
                        updateExecuteResult instanceof CoroIterStepResult.ContinueCoroutine ) )
                {
                    return updateExecuteResult;
                }

                this.runInUpdate = false;
                this.runInCondition = true;
                this.updateComplexStepState = null;
            }
        }
    }

    private void finish()
    {
        this.runInInitializer = false;
        this.runInCondition = false;
        this.runInBody = false;
        //this.bodySequence.reset();
        this.runInUpdate = false;
    }

    /**
     * @see ComplexStepState#isFinished()
     */
    @Override
    public boolean isFinished()
    {
        return
                ( ! runInInitializer ) &&
                ( ! runInCondition ) &&
                ( ! runInBody ) &&
                ( ! runInUpdate );
    }

    /**
     * @see ComplexStepState#getStep()
     */
    @Override
    public For<RESULT/*, PARENT*/> getStep()
    {
        return _for;
    }

    /**
     * @see HCloneable#createClone()
     */
    @Override
    public ForState<RESULT/*, PARENT*/> createClone()
    {
        final ForState<RESULT/*, PARENT*/> clone =
                new ForState<>(
                        _for ,
                        //this.rootParent
                        this.parent ) ;

        clone.runInInitializer = runInInitializer;
        clone.runInCondition = runInCondition;
        clone.runInBody = runInBody;
        clone.runInUpdate = runInUpdate;

        clone.initializerComplexStepState = ( initializerComplexStepState != null ? initializerComplexStepState.createClone() : null );
        clone.bodyComplexState = ( bodyComplexState != null ? bodyComplexState.createClone() : null );
        clone.updateComplexStepState = ( updateComplexStepState != null ? updateComplexStepState.createClone() : null );

        return clone;
    }

}
