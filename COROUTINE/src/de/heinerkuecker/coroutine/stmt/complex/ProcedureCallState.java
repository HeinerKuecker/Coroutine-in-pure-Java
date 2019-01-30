package de.heinerkuecker.coroutine.stmt.complex;

import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstmt;
import de.heinerkuecker.coroutine.Procedure;
import de.heinerkuecker.coroutine.arg.Arguments;
import de.heinerkuecker.coroutine.stmt.CoroIterStmtResult;
import de.heinerkuecker.util.HCloneable;

class ProcedureCallState<COROUTINE_RETURN , RESUME_ARGUMENT>
extends ComplexStmtState<
    ProcedureCallState<COROUTINE_RETURN , RESUME_ARGUMENT>,
    ProcedureCall<COROUTINE_RETURN , RESUME_ARGUMENT>,
    COROUTINE_RETURN ,
    //ProcedureCall<COROUTINE_RETURN>
    RESUME_ARGUMENT
    >
{
    private final ProcedureCall<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> procedureCall;

    boolean runInProcedure = true;

    // TODO getter
    ComplexStmtState<?, ?, COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> bodyComplexState;

    //private final CoroutineIterator<COROUTINE_RETURN> rootParent;
    private final CoroutineOrProcedureOrComplexstmt<COROUTINE_RETURN, RESUME_ARGUMENT> parent;

    //final Map<String, Object> procedureArgumentValues;
    final Arguments arguments;

    /**
     * Constructor.
     *
     * @param procedureCall
     */
    protected ProcedureCallState(
            final boolean isInitializationCheck ,
            final ProcedureCall<COROUTINE_RETURN , RESUME_ARGUMENT> procedureCall ,
            final Class<? extends COROUTINE_RETURN> resultType ,
            //final Map<String, Object> procedureArgumentValues
            final Arguments arguments ,
            final CoroutineOrProcedureOrComplexstmt<COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        super( parent );
        this.procedureCall =
                Objects.requireNonNull(
                        procedureCall );

        //this.procedureArgumentValues = Collections.unmodifiableMap( procedureArgumentValues );
        this.arguments = arguments;

        //this.rootParent = Objects.requireNonNull( rootParent );

        this.parent =
                Objects.requireNonNull(
                        parent );

        final Procedure<COROUTINE_RETURN , RESUME_ARGUMENT> procedure =
                this.parent/*.getRootParent()*/.getProcedure(
                        procedureCall.procedureName );

        if ( ! isInitializationCheck )
        {
            // for showing next step in toString
            this.bodyComplexState =
                    procedure.bodyComplexStep.newState(
                            this );
        }

        if ( resultType != null )
        {
            // TODO execute only once
            procedure.bodyComplexStep.setResultType( resultType );
        }
    }

    /**
     * @see ComplexStmtState#execute
     */
    @Override
    public CoroIterStmtResult<COROUTINE_RETURN> execute(
            //final CoroutineOrProcedureOrComplexstep<COROUTINE_RETURN, RESUME_ARGUMENT> parent
            )
    {
        if ( runInProcedure )
        {
            final ComplexStmt<?, ?, COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> bodyComplexStep =
                    //procedureCall.procedure.bodyComplexStep;
                    this.parent/*.getRootParent()*/.getProcedure( procedureCall.procedureName ).bodyComplexStep;

            if ( this.bodyComplexState == null )
                // no existing state from previous execute call
            {
                this.bodyComplexState =
                        bodyComplexStep.newState(
                                //this.rootParent
                                this );
            }

            // TODO only before executing simple step: parent.saveLastStepState();

            final CoroIterStmtResult<COROUTINE_RETURN> bodyExecuteResult =
                    this.bodyComplexState.execute(
                            //parent
                            //this
                            );

            if ( this.bodyComplexState.isFinished() )
            {
                finish();
            }

            if ( ! ( bodyExecuteResult == null ||
                    bodyExecuteResult instanceof CoroIterStmtResult.ContinueCoroutine ) )
            {
                return bodyExecuteResult;
            }

            bodyComplexState = null;
            return bodyExecuteResult;
        }

        return CoroIterStmtResult.continueCoroutine();
    }

    private void finish()
    {
        this.runInProcedure = false;
        bodyComplexState = null;
    }

    /**
     * @see ComplexStmtState#isFinished()
     */
    @Override
    public boolean isFinished()
    {
        return ! runInProcedure;
    }

    /**
     * @see ComplexStmtState#getStep()
     */
    @Override
    public ProcedureCall<COROUTINE_RETURN , RESUME_ARGUMENT> getStep()
    {
        return this.procedureCall;
    }

    /**
     * @see HCloneable#createClone()
     */
    @Override
    public ProcedureCallState<COROUTINE_RETURN , RESUME_ARGUMENT> createClone()
    {
        final ProcedureCallState<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> clone =
                new ProcedureCallState<>(
                        // isInitializationCheck TODO wrong using of argument
                        true ,
                        this.procedureCall ,
                        // resultType null, because only for first call necessary
                        null ,
                        //this.procedureArgumentValues
                        this.arguments ,
                        //this.rootParent ,
                        this.parent );

        clone.bodyComplexState = ( bodyComplexState != null ? bodyComplexState.createClone() : null );

        //clone.vars.putAll( this.vars );
        //for ( Entry<String, ? extends Object> initialVariableEntry : this.globalVariables )
        //{
        //    clone.globalVariables.set(
        //            initialVariableEntry.getKey() ,
        //            initialVariableEntry.getValue() );
        //}

        return clone;
    }

    //@Override
    //public void saveLastStepState()
    //{
    //    this.parent.getRootParent().saveLastStepState();
    //}

    //@Override
    ////public Map<String, Object> localVars()
    //public GlobalVariables localVars()
    //{
    //    return this.globalVariables;
    //}

    //@Override
    ////public Map<String, Object> globalVars()
    //public GlobalVariables globalVars()
    //{
    //    return this.parent/*.getRootParent()*/.globalVars();
    //}

    /**
     * Overidden to access arguments of this procedure
     */
    @Override
    //public Map<String, Object> procedureArgumentValues()
    public Arguments procedureArgumentValues()
    {
        return
                //this.procedureArgumentValues;
                this.arguments;
    }

    //@Override
    //public Arguments globalArgumentValues()
    //{
    //    return this.parent.globalArgumentValues();
    //}

    //@Override
    //public CoroutineIterator<COROUTINE_RETURN> getRootParent()
    //{
    //    return this.parent.getRootParent();
    //}

    //@Override
    //public Procedure<COROUTINE_RETURN> getProcedure(
    //        final String procedureName )
    //{
    //    return this.getRootParent().getProcedure( procedureName );
    //}

    /**
     * Overidden to access parameter of this procedure
     */
    @Override
    public Map<String, Class<?>> procedureParameterTypes()
    {
        return this.arguments.procedureParameterTypes();
    }

    //@Override
    //public Map<String, Class<?>> globalParameterTypes()
    //{
    //    //throw new RuntimeException( "not implemented" );
    //    return this.getRootParent().arguments.procedureParameterTypes();
    //}

}
