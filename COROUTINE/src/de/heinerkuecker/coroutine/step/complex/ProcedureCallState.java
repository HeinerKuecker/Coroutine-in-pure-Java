package de.heinerkuecker.coroutine.step.complex;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.Procedure;
import de.heinerkuecker.coroutine.step.result.CoroIterStepResult;
import de.heinerkuecker.util.HCloneable;

class ProcedureCallState<RESULT>
implements ComplexStepState<
    ProcedureCallState<RESULT>,
    ProcedureCall<RESULT>,
    RESULT
    //ProcedureCall<RESULT>
    > ,
CoroIteratorOrProcedure<RESULT/*, CoroutineIterator<RESULT>*/>
{
    private final ProcedureCall<RESULT/*, PARENT*/> procedureCall;

    boolean runInProcedure = true;

    // TODO getter
    ComplexStepState<?, ?, RESULT/*, PARENT*/> bodyComplexState;

    private final CoroutineIterator<RESULT> rootParent;

    final Map<String, Object> procedureArgumentValues;

    /**
     * Variables.
     */
    public final HashMap<String, Object> vars = new HashMap<>();

    /**
     * Constructor.
     *
     * @param procedureCall
     */
    protected ProcedureCallState(
            final ProcedureCall<RESULT> procedureCall ,
            final CoroutineIterator<RESULT> rootParent ,
            final Map<String, Object> procedureArgumentValues )
    {
        this.procedureCall =
                Objects.requireNonNull(
                        procedureCall );

        this.rootParent =
                Objects.requireNonNull(
                        rootParent );

        this.procedureArgumentValues =
                Collections.unmodifiableMap(
                        procedureArgumentValues );
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
                    //procedureCall.procedure.bodyComplexStep;
                    this.rootParent.getProcedure( procedureCall.procedureName ).bodyComplexStep;

            if ( this.bodyComplexState == null )
                // no existing state from previous execute call
            {
                this.bodyComplexState =
                        bodyComplexStep.newState(
                                this.rootParent );
            }

            // TODO only before executing simple step: parent.saveLastStepState();

            final CoroIterStepResult<RESULT> bodyExecuteResult =
                    this.bodyComplexState.execute(
                            //parent
                            this );

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
        final ProcedureCallState<RESULT/*, PARENT*/> clone =
                new ProcedureCallState<>(
                        this.procedureCall ,
                        this.rootParent ,
                        this.procedureArgumentValues );

        clone.bodyComplexState = ( bodyComplexState != null ? bodyComplexState.createClone() : null );

        clone.vars.putAll( this.vars );

        return clone;
    }

    /**
     * @see CoroIteratorOrProcedure#saveLastStepState()
     */
    @Override
    public void saveLastStepState()
    {
        this.rootParent.saveLastStepState();
    }

    ///**
    // * @see ComplexStep#setRootParent
    // */
    //@Override
    //public void setRootParent(
    //        final CoroutineIterator<RESULT> rootParent )
    //{
    //    this.rootParent = rootParent;
    //}

    /**
     * @see CoroIteratorOrProcedure#vars()
     */
    @Override
    public Map<String, Object> localVars()
    {
        return this.vars;
    }

    /**
     * @see CoroIteratorOrProcedure#globalVars()
     */
    @Override
    public Map<String, Object> globalVars()
    {
        return this.rootParent.globalVars();
    }

    /**
     * @see CoroIteratorOrProcedure#procedureArgumentValues()
     */
    @Override
    public Map<String, Object> procedureArgumentValues()
    {
        return this.procedureArgumentValues;
    }

    /**
     * @see CoroIteratorOrProcedure#getRootParent()
     */
    @Override
    public CoroutineIterator<RESULT> getRootParent()
    {
        return this.rootParent;
    }

    /**
     * @see CoroIteratorOrProcedure#getProcedure(String)
     */
    @Override
    public Procedure<RESULT> getProcedure(
            final String procedureName )
    {
        return this.getRootParent().getProcedure( procedureName );
    }

}
