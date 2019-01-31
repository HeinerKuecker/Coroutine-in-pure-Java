package de.heinerkuecker.coroutine.stmt.complex;

import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstmt;
import de.heinerkuecker.coroutine.stmt.CoroIterStmt;
import de.heinerkuecker.coroutine.stmt.CoroIterStmtResult;
import de.heinerkuecker.coroutine.stmt.simple.SimpleStmt;
import de.heinerkuecker.util.HCloneable;

class ForState<COROUTINE_RETURN/*, PARENT extends CoroutineIterator<COROUTINE_RETURN>*/ , RESUME_ARGUMENT>
extends ComplexStmtState<
    ForState<COROUTINE_RETURN/*, PARENT*/, RESUME_ARGUMENT> ,
    For<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> ,
    COROUTINE_RETURN ,
    //PARENT
    RESUME_ARGUMENT
    >
{
    private final For<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> _for;

    // TODO getter
    boolean runInInitializer = true;
    boolean runInCondition;
    boolean runInBody;
    boolean runInUpdate;

    private ComplexStmtState<? ,?, COROUTINE_RETURN/*, ? super PARENT*/ , RESUME_ARGUMENT> initializerComplexStepState;
    // TODO getter
    ComplexStmtState<?, ?, COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> bodyComplexState;
    private ComplexStmtState<?, ?, COROUTINE_RETURN/*, ? super PARENT*/ , RESUME_ARGUMENT> updateComplexStepState;

    //private final CoroutineIterator<COROUTINE_RETURN> rootParent;
    private final CoroutineOrProcedureOrComplexstmt<COROUTINE_RETURN, RESUME_ARGUMENT> parent;

    /**
     * Local variables for
     * condition {@link For#condition},
     * body {@link For#bodyComplexStep} and
     * update stmt {@link For#updateStep}.
     *
     * The super {@link ComplexStmtState#blockLocalVariables}
     * is used for
     * initial stmt
     * and as parent for this
     * {@link #subBlockLocalVariables}.
     */
    private BlockLocalVariables conditionAndBodyAndUpdateLocalVariables;

    /**
     * Constructor.
     */
    public ForState(
            final For<COROUTINE_RETURN/*, PARENT*/, RESUME_ARGUMENT> _for ,
            //final CoroutineIterator<COROUTINE_RETURN> rootParent
            final CoroutineOrProcedureOrComplexstmt<COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        super( parent );
        this._for = _for;

        //this.rootParent = Objects.requireNonNull( rootParent );

        this.parent =
                Objects.requireNonNull(
                        parent );
    }

    @Override
    public CoroIterStmtResult<COROUTINE_RETURN> execute(
            //final CoroutineOrProcedureOrComplexstmt<COROUTINE_RETURN, RESUME_ARGUMENT> parent
            )
    {
        if ( runInInitializer )
        {
            final CoroIterStmt<COROUTINE_RETURN /*, ? super PARENT*/> initializerStep =
                    _for.initialStep;

            final CoroIterStmtResult<COROUTINE_RETURN> initializerExecuteResult;
            if ( initializerStep instanceof SimpleStmt )
            {
                final SimpleStmt<COROUTINE_RETURN/*, ? super PARENT*/, RESUME_ARGUMENT> initializerSimpleStep =
                        (SimpleStmt<COROUTINE_RETURN/*, ? super PARENT*/, RESUME_ARGUMENT>) initializerStep;

                parent.saveLastStepState();

                initializerExecuteResult =
                        initializerSimpleStep.execute(
                                //parent
                                this );

                runInInitializer = false;
                runInCondition = true;
            }
            else
            {
                final ComplexStmt<?, ?, COROUTINE_RETURN/*, ? super PARENT*/ , RESUME_ARGUMENT> initializerComplexStep =
                        (ComplexStmt<?, ?, COROUTINE_RETURN/*, ? super PARENT*/ , RESUME_ARGUMENT>) initializerStep;

                if ( this.initializerComplexStepState == null )
                    // no existing state from previous execute call
                {
                    this.initializerComplexStepState =
                            initializerComplexStep.newState(
                                    //this.rootParent
                                    //this.parent
                                    this );
                }

                // TODO only before executing simple stmt: parent.saveLastStepState();

                initializerExecuteResult =
                        this.initializerComplexStepState.execute(
                                //parent
                                //this
                                );

                if ( this.initializerComplexStepState.isFinished() )
                {
                    runInInitializer = false;
                    runInCondition = true;
                    this.initializerComplexStepState = null;
                }
            }

            if ( initializerExecuteResult instanceof CoroIterStmtResult.Break ||
                    initializerExecuteResult instanceof CoroIterStmtResult.ContinueLoop )
            {
                throw new IllegalStateException( String.valueOf( initializerExecuteResult ) );
            }

            if ( ! ( initializerExecuteResult == null ||
                    initializerExecuteResult instanceof CoroIterStmtResult.ContinueCoroutine ) )
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
                this.conditionAndBodyAndUpdateLocalVariables =
                        new BlockLocalVariables(
                                super.localVars() );

                parent.saveLastStepState();

                final boolean conditionResult =
                        _for.condition.evaluate(
                                //parent
                                this );

                if ( conditionResult )
                {
                    this.runInCondition = false;
                    this.runInBody = true;
                    // for toString
                    this.bodyComplexState =
                            _for.bodyComplexStep.newState(
                                    //this.rootParent
                                    //this.parent
                                    this );
                }
                else
                {
                    finish();
                    return CoroIterStmtResult.continueCoroutine();
                }
            }

            if ( runInBody )
            {
                final ComplexStmt<?, ?, COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> bodyComplexStep =
                        _for.bodyComplexStep;

                if ( this.bodyComplexState == null )
                    // no existing state from previous execute call
                {
                    this.bodyComplexState =
                            bodyComplexStep.newState(
                                    //this.rootParent
                                    //this.parent
                                    this );
                }

                // TODO only before executing simple stmt: parent.saveLastStepState();

                final CoroIterStmtResult<COROUTINE_RETURN> bodyExecuteResult =
                        this.bodyComplexState.execute(
                                //parent
                                //this
                                );

                if ( this.bodyComplexState.isFinished() )
                {
                    this.runInBody = false;
                    this.runInUpdate = true;
                    this.bodyComplexState = null;
                }

                if ( bodyExecuteResult instanceof CoroIterStmtResult.Break )
                {
                    finish();
                    final String label = ( (CoroIterStmtResult.Break<?>) bodyExecuteResult ).label;
                    if ( label == null ||
                            label.equals( _for.label ) )
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
                            ! label.equals( _for.label ) )
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

                this.runInBody = false;
                this.runInUpdate = true;
                this.bodyComplexState = null;
            }

            if ( runInUpdate )
            {
                final CoroIterStmt<COROUTINE_RETURN/*, ? super PARENT*/> updateStep =
                        _for.updateStep;

                CoroIterStmtResult<COROUTINE_RETURN> updateExecuteResult;
                if ( updateStep instanceof SimpleStmt )
                {
                    final SimpleStmt<COROUTINE_RETURN/*, ? super PARENT*/, RESUME_ARGUMENT> updateSimpleStep =
                            (SimpleStmt<COROUTINE_RETURN/*, ? super PARENT*/, RESUME_ARGUMENT>) updateStep;

                    parent.saveLastStepState();

                    updateExecuteResult =
                            updateSimpleStep.execute(
                                    //parent
                                    this );

                    this.runInUpdate = false;
                    this.runInCondition = true;
                }
                else
                {
                    final ComplexStmt<?, ?, COROUTINE_RETURN/*, ? super PARENT*/ , RESUME_ARGUMENT> updateComplexStep =
                            (ComplexStmt<?, ?, COROUTINE_RETURN/*, ? super PARENT*/ , RESUME_ARGUMENT>) updateStep;

                    if ( this.updateComplexStepState == null )
                        // no existing state from previous execute call
                    {
                        this.updateComplexStepState =
                                updateComplexStep.newState(
                                        //this.rootParent
                                        //this.parent
                                        this );
                    }

                    // TODO only before executing simple stmt: parent.saveLastStepState();

                    updateExecuteResult =
                            this.updateComplexStepState.execute(
                                    //parent
                                    //this
                                    );

                    if ( this.updateComplexStepState.isFinished() )
                    {
                        this.runInUpdate = false;
                        this.runInCondition = true;
                        this.updateComplexStepState = null;
                    }
                }

                if ( updateExecuteResult instanceof CoroIterStmtResult.Break ||
                        updateExecuteResult instanceof CoroIterStmtResult.ContinueLoop )
                {
                    throw new IllegalStateException( String.valueOf( updateExecuteResult ) );
                }

                if ( ! ( updateExecuteResult == null ||
                        updateExecuteResult instanceof CoroIterStmtResult.ContinueCoroutine ) )
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
     * @see ComplexStmtState#isFinished()
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

    @Override
    public BlockLocalVariables localVars()
    {
        if ( this.conditionAndBodyAndUpdateLocalVariables == null )
            // run in initializer
        {
            return super.localVars();
        }
        // run in condition, body or update
        return this.conditionAndBodyAndUpdateLocalVariables;
    }

    /**
     * @see ComplexStmtState#getStep()
     */
    @Override
    public For<COROUTINE_RETURN/*, PARENT*/, RESUME_ARGUMENT> getStep()
    {
        return _for;
    }

    /**
     * @see HCloneable#createClone()
     */
    @Override
    public ForState<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> createClone()
    {
        final ForState<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> clone =
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
