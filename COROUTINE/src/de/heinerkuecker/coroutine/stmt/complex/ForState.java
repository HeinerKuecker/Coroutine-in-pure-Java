package de.heinerkuecker.coroutine.stmt.complex;

import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineOrFunctioncallOrComplexstmt;
import de.heinerkuecker.coroutine.stmt.CoroStmt;
import de.heinerkuecker.coroutine.stmt.CoroStmtResult;
import de.heinerkuecker.coroutine.stmt.simple.SimpleStmt;
import de.heinerkuecker.util.HCloneable;

class ForState<
    FUNCTION_RETURN ,
    COROUTINE_RETURN/*, PARENT extends CoroutineIterator<COROUTINE_RETURN>*/ ,
    RESUME_ARGUMENT
    >
extends ComplexStmtState<
    ForState<FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT*/, RESUME_ARGUMENT> ,
    For<FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> ,
    FUNCTION_RETURN ,
    COROUTINE_RETURN ,
    //PARENT
    RESUME_ARGUMENT
    >
{
    private final For<FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> _for;

    // TODO getter
    boolean runInInitializer = true;
    boolean runInCondition;
    boolean runInBody;
    boolean runInUpdate;

    private ComplexStmtState<? ,?, FUNCTION_RETURN , COROUTINE_RETURN/*, ? super PARENT*/ , RESUME_ARGUMENT> initializerComplexStmtState;
    // TODO getter
    ComplexStmtState<?, ?, FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> bodyComplexState;
    private ComplexStmtState<?, ?, FUNCTION_RETURN , COROUTINE_RETURN/*, ? super PARENT*/ , RESUME_ARGUMENT> updateComplexStmtState;

    //private final CoroutineIterator<COROUTINE_RETURN> rootParent;
    private final CoroutineOrFunctioncallOrComplexstmt<FUNCTION_RETURN , COROUTINE_RETURN, RESUME_ARGUMENT> parent;

    /**
     * Local variables for
     * condition {@link For#condition},
     * body {@link For#bodyComplexStmt} and
     * update stmt {@link For#updateStmt}.
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
            final For<FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT*/, RESUME_ARGUMENT> _for ,
            //final CoroutineIterator<COROUTINE_RETURN> rootParent
            final CoroutineOrFunctioncallOrComplexstmt<FUNCTION_RETURN , COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        super( parent );
        this._for = _for;

        //this.rootParent = Objects.requireNonNull( rootParent );

        this.parent =
                Objects.requireNonNull(
                        parent );
    }

    @Override
    public CoroStmtResult<FUNCTION_RETURN , COROUTINE_RETURN> execute(
            //final CoroutineOrFunctioncallOrComplexstmt<COROUTINE_RETURN, RESUME_ARGUMENT> parent
            )
    {
        if ( runInInitializer )
        {
            final CoroStmt<FUNCTION_RETURN , COROUTINE_RETURN /*, ? super PARENT*/> initializerStmt =
                    _for.initialStmt;

            final CoroStmtResult<FUNCTION_RETURN , COROUTINE_RETURN> initializerExecuteResult;
            if ( initializerStmt instanceof SimpleStmt )
            {
                final SimpleStmt<FUNCTION_RETURN , COROUTINE_RETURN/*, ? super PARENT*/, RESUME_ARGUMENT> initializerSimpleStmt =
                        (SimpleStmt<FUNCTION_RETURN , COROUTINE_RETURN/*, ? super PARENT*/, RESUME_ARGUMENT>) initializerStmt;

                parent.saveLastStmtState();

                initializerExecuteResult =
                        initializerSimpleStmt.execute(
                                //parent
                                this );

                runInInitializer = false;
                runInCondition = true;
            }
            else
            {
                final ComplexStmt<?, ?, FUNCTION_RETURN , COROUTINE_RETURN/*, ? super PARENT*/ , RESUME_ARGUMENT> initializerComplexStmt =
                        (ComplexStmt<?, ?, FUNCTION_RETURN , COROUTINE_RETURN/*, ? super PARENT*/ , RESUME_ARGUMENT>) initializerStmt;

                if ( this.initializerComplexStmtState == null )
                    // no existing state from previous execute call
                {
                    this.initializerComplexStmtState =
                            initializerComplexStmt.newState(
                                    //this.rootParent
                                    //this.parent
                                    this );
                }

                // TODO only before executing simple stmt: parent.saveLastStmtState();

                initializerExecuteResult =
                        this.initializerComplexStmtState.execute(
                                //parent
                                //this
                                );

                if ( this.initializerComplexStmtState.isFinished() )
                {
                    runInInitializer = false;
                    runInCondition = true;
                    this.initializerComplexStmtState = null;
                }
            }

            if ( initializerExecuteResult instanceof CoroStmtResult.BreakLoop ||
                    initializerExecuteResult instanceof CoroStmtResult.ContinueLoop )
            {
                throw new IllegalStateException( String.valueOf( initializerExecuteResult ) );
            }

            if ( ! ( initializerExecuteResult == null ||
                    initializerExecuteResult instanceof CoroStmtResult.ContinueCoroutine ) )
            {
                return initializerExecuteResult;
            }

            runInInitializer = false;
            runInCondition = true;
            this.initializerComplexStmtState = null;
        }

        while ( true )
        {
            if ( runInCondition )
            {
                this.conditionAndBodyAndUpdateLocalVariables =
                        new BlockLocalVariables(
                                super.localVars() );

                parent.saveLastStmtState();

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
                            _for.bodyComplexStmt.newState(
                                    //this.rootParent
                                    //this.parent
                                    this );
                }
                else
                {
                    finish();
                    return CoroStmtResult.continueCoroutine();
                }
            }

            if ( runInBody )
            {
                final ComplexStmt<?, ?, FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> bodyComplexStmt =
                        _for.bodyComplexStmt;

                if ( this.bodyComplexState == null )
                    // no existing state from previous execute call
                {
                    this.bodyComplexState =
                            bodyComplexStmt.newState(
                                    //this.rootParent
                                    //this.parent
                                    this );
                }

                // TODO only before executing simple stmt: parent.saveLastStmtState();

                final CoroStmtResult<FUNCTION_RETURN , COROUTINE_RETURN> bodyExecuteResult =
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

                if ( bodyExecuteResult instanceof CoroStmtResult.BreakLoop )
                {
                    finish();
                    final String label = ( (CoroStmtResult.BreakLoop<? , ?>) bodyExecuteResult ).label;
                    if ( label == null ||
                            label.equals( _for.label ) )
                    {
                        return CoroStmtResult.continueCoroutine();
                    }
                    // break outer loop
                    return bodyExecuteResult;
                }
                else if ( bodyExecuteResult instanceof CoroStmtResult.ContinueLoop )
                {
                    final String label = ( (CoroStmtResult.ContinueLoop<? , ?>) bodyExecuteResult ).label;
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
                        bodyExecuteResult instanceof CoroStmtResult.ContinueCoroutine ) )
                {
                    return bodyExecuteResult;
                }

                this.runInBody = false;
                this.runInUpdate = true;
                this.bodyComplexState = null;
            }

            if ( runInUpdate )
            {
                final CoroStmt<FUNCTION_RETURN , COROUTINE_RETURN/*, ? super PARENT*/> updateStmt =
                        _for.updateStmt;

                CoroStmtResult<FUNCTION_RETURN , COROUTINE_RETURN> updateExecuteResult;
                if ( updateStmt instanceof SimpleStmt )
                {
                    final SimpleStmt<FUNCTION_RETURN , COROUTINE_RETURN/*, ? super PARENT*/, RESUME_ARGUMENT> updateSimpleStmt =
                            (SimpleStmt<FUNCTION_RETURN , COROUTINE_RETURN/*, ? super PARENT*/, RESUME_ARGUMENT>) updateStmt;

                    parent.saveLastStmtState();

                    updateExecuteResult =
                            updateSimpleStmt.execute(
                                    //parent
                                    this );

                    this.runInUpdate = false;
                    this.runInCondition = true;
                }
                else
                {
                    final ComplexStmt<?, ?, FUNCTION_RETURN , COROUTINE_RETURN/*, ? super PARENT*/ , RESUME_ARGUMENT> updateComplexStmt =
                            (ComplexStmt<?, ?, FUNCTION_RETURN , COROUTINE_RETURN/*, ? super PARENT*/ , RESUME_ARGUMENT>) updateStmt;

                    if ( this.updateComplexStmtState == null )
                        // no existing state from previous execute call
                    {
                        this.updateComplexStmtState =
                                updateComplexStmt.newState(
                                        //this.rootParent
                                        //this.parent
                                        this );
                    }

                    // TODO only before executing simple stmt: parent.saveLastStmtState();

                    updateExecuteResult =
                            this.updateComplexStmtState.execute(
                                    //parent
                                    //this
                                    );

                    if ( this.updateComplexStmtState.isFinished() )
                    {
                        this.runInUpdate = false;
                        this.runInCondition = true;
                        this.updateComplexStmtState = null;
                    }
                }

                if ( updateExecuteResult instanceof CoroStmtResult.BreakLoop ||
                        updateExecuteResult instanceof CoroStmtResult.ContinueLoop )
                {
                    throw new IllegalStateException( String.valueOf( updateExecuteResult ) );
                }

                if ( ! ( updateExecuteResult == null ||
                        updateExecuteResult instanceof CoroStmtResult.ContinueCoroutine ) )
                {
                    return updateExecuteResult;
                }

                this.runInUpdate = false;
                this.runInCondition = true;
                this.updateComplexStmtState = null;
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
     * @see ComplexStmtState#getStmt()
     */
    @Override
    public For<FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT*/, RESUME_ARGUMENT> getStmt()
    {
        return _for;
    }

    /**
     * @see HCloneable#createClone()
     */
    @Override
    public ForState<FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> createClone()
    {
        final ForState<FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> clone =
                new ForState<>(
                        _for ,
                        //this.rootParent
                        this.parent ) ;

        clone.runInInitializer = runInInitializer;
        clone.runInCondition = runInCondition;
        clone.runInBody = runInBody;
        clone.runInUpdate = runInUpdate;

        clone.initializerComplexStmtState = ( initializerComplexStmtState != null ? initializerComplexStmtState.createClone() : null );
        clone.bodyComplexState = ( bodyComplexState != null ? bodyComplexState.createClone() : null );
        clone.updateComplexStmtState = ( updateComplexStmtState != null ? updateComplexStmtState.createClone() : null );

        return clone;
    }

}
