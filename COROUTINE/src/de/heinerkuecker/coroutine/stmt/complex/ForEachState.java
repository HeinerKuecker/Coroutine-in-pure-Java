package de.heinerkuecker.coroutine.stmt.complex;

import java.util.Iterator;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineOrFunctioncallOrComplexstmt;
import de.heinerkuecker.coroutine.stmt.CoroStmtResult;
import de.heinerkuecker.coroutine.stmt.simple.DeclareVariable;
import de.heinerkuecker.util.HCloneable;

class ForEachState<
    FUNCTION_RETURN ,
    COROUTINE_RETURN/*, PARENT extends CoroutineIterator<COROUTINE_RETURN>*/ ,
    RESUME_ARGUMENT ,
    ELEMENT
    >
extends ComplexStmtState<
    ForEachState<FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT , ELEMENT> ,
    ForEach<FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT , ELEMENT> ,
    FUNCTION_RETURN ,
    COROUTINE_RETURN ,
    //PARENT
    RESUME_ARGUMENT
    >
{
    private final ForEach<FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT , ELEMENT> forEach;

    // TODO getter
    boolean runInInitializer = true;
    boolean runInBody;
    boolean runInConditionAndUpdate;

    // TODO getter
    ComplexStmtState<?, ?, FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> bodyComplexState;

    Iterator<ELEMENT> iterator;

    private ELEMENT variableValue;

    //private final CoroutineIterator<COROUTINE_RETURN> rootParent;
    private final CoroutineOrFunctioncallOrComplexstmt<FUNCTION_RETURN , COROUTINE_RETURN, RESUME_ARGUMENT> parent;

    /**
     * Constructor.
     */
    public ForEachState(
            final ForEach<FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT , ELEMENT> forEach ,
            //final CoroutineIterator<COROUTINE_RETURN> rootParent
            final CoroutineOrFunctioncallOrComplexstmt<FUNCTION_RETURN , COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        super( parent );
        this.forEach = forEach;

        //this.rootParent = Objects.requireNonNull( rootParent );

        this.parent =
                Objects.requireNonNull(
                        parent );

        new DeclareVariable<FUNCTION_RETURN , COROUTINE_RETURN, RESUME_ARGUMENT, ELEMENT>(
                forEach.variableName ,
                forEach.elementType ).execute(
                        this );
    }

    @Override
    public CoroStmtResult<FUNCTION_RETURN , COROUTINE_RETURN> execute(
            //final CoroutineOrFunctioncallOrComplexstmt<COROUTINE_RETURN, RESUME_ARGUMENT> parent
            )
    {
        if ( runInInitializer )
        {
            parent.saveLastStmtState();

            final Iterable<ELEMENT> iterable = forEach.iterableExpression.evaluate( parent );
            this.iterator = iterable.iterator();

            if ( ! iterator.hasNext() )
            {
                finish();
                return CoroStmtResult.continueCoroutine();
            }

            this.variableValue = iterator.next();

            /*parent*/this.localVars().set(
                    forEach.variableName ,
                    variableValue );

            // for toString
            this.bodyComplexState =
                    forEach.bodyComplexStmt.newState(
                            //this.parent
                            this );

            runInInitializer = false;
            runInBody = true;
        }

        while ( true )
        {
            if ( runInConditionAndUpdate )
            {
                parent.saveLastStmtState();

                if ( ! iterator.hasNext() )
                {
                    finish();
                    return CoroStmtResult.continueCoroutine();
                }

                this.variableValue = iterator.next();

                /*parent*/this.localVars().set(
                        forEach.variableName ,
                        variableValue );

                this.runInConditionAndUpdate = false;
                this.runInBody = true;
            }

            if ( runInBody )
            {
                final ComplexStmt<?, ?, FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> bodyComplexStmt =
                        forEach.bodyComplexStmt;

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
                    this.runInConditionAndUpdate = true;
                    this.bodyComplexState = null;
                }

                if ( bodyExecuteResult instanceof CoroStmtResult.Break )
                {
                    finish();
                    final String label = ( (CoroStmtResult.Break<? , ?>) bodyExecuteResult ).label;
                    if ( label == null ||
                            label.equals( forEach.label ) )
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
                            ! label.equals( forEach.label ) )
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
                this.runInConditionAndUpdate = true;
                this.bodyComplexState = null;
            }
        }
    }

    private void finish()
    {
        this.runInInitializer = false;
        this.runInBody = false;
        //this.bodySequence.reset();
        this.runInConditionAndUpdate = false;
        this.variableValue = null;
    }

    /**
     * @see ComplexStmtState#isFinished()
     */
    @Override
    public boolean isFinished()
    {
        return
                ( ! runInInitializer ) &&
                ( ! runInBody ) &&
                ( ! runInConditionAndUpdate );
    }

    /**
     * @see ComplexStmtState#getStmt()
     */
    @Override
    public ForEach<FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT , ELEMENT> getStmt()
    {
        return forEach;
    }

    /**
     * @see HCloneable#createClone()
     */
    @Override
    public ForEachState<FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT, ELEMENT> createClone()
    {
        final ForEachState<FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT , ELEMENT> clone =
                new ForEachState<>(
                        forEach ,
                        //this.rootParent
                        this.parent ) ;

        clone.runInInitializer = runInInitializer;
        clone.runInBody = runInBody;
        clone.runInConditionAndUpdate = runInConditionAndUpdate;

        clone.bodyComplexState = ( bodyComplexState != null ? bodyComplexState.createClone() : null );

        // Attention, iterator is not cloned, not necessary for toString, not good for using clone in flow control

        return clone;
    }

}
