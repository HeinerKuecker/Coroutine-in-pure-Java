package de.heinerkuecker.coroutine.step.complex;

import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep;
import de.heinerkuecker.coroutine.Procedure;
import de.heinerkuecker.coroutine.arg.Arguments;
import de.heinerkuecker.coroutine.step.CoroIterStepResult;
import de.heinerkuecker.util.HCloneable;

class ProcedureCallState<RESULT , RESUME_ARGUMENT>
extends ComplexStepState<
    ProcedureCallState<RESULT , RESUME_ARGUMENT>,
    ProcedureCall<RESULT , RESUME_ARGUMENT>,
    RESULT ,
    //ProcedureCall<RESULT>
    RESUME_ARGUMENT
    >
{
    private final ProcedureCall<RESULT/*, PARENT*/ , RESUME_ARGUMENT> procedureCall;

    boolean runInProcedure = true;

    // TODO getter
    ComplexStepState<?, ?, RESULT/*, PARENT*/ , RESUME_ARGUMENT> bodyComplexState;

    //private final CoroutineIterator<RESULT> rootParent;
    private final CoroutineOrProcedureOrComplexstep<RESULT, RESUME_ARGUMENT> parent;

    //final Map<String, Object> procedureArgumentValues;
    final Arguments arguments;

    /**
     * Constructor.
     *
     * @param procedureCall
     */
    protected ProcedureCallState(
            final boolean isInitializationCheck ,
            final ProcedureCall<RESULT , RESUME_ARGUMENT> procedureCall ,
            final Class<? extends RESULT> resultType ,
            //final Map<String, Object> procedureArgumentValues
            final Arguments arguments ,
            final CoroutineOrProcedureOrComplexstep<RESULT, RESUME_ARGUMENT> parent )
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

        final Procedure<RESULT , RESUME_ARGUMENT> procedure =
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
     * @see ComplexStepState#execute
     */
    @Override
    public CoroIterStepResult<RESULT> execute(
            //final CoroutineOrProcedureOrComplexstep<RESULT, RESUME_ARGUMENT> parent
            )
    {
        if ( runInProcedure )
        {
            final ComplexStep<?, ?, RESULT/*, PARENT*/ , RESUME_ARGUMENT> bodyComplexStep =
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

            final CoroIterStepResult<RESULT> bodyExecuteResult =
                    this.bodyComplexState.execute(
                            //parent
                            //this
                            );

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
    public ProcedureCall<RESULT , RESUME_ARGUMENT> getStep()
    {
        return this.procedureCall;
    }

    /**
     * @see HCloneable#createClone()
     */
    @Override
    public ProcedureCallState<RESULT , RESUME_ARGUMENT> createClone()
    {
        final ProcedureCallState<RESULT/*, PARENT*/ , RESUME_ARGUMENT> clone =
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
    //public CoroutineIterator<RESULT> getRootParent()
    //{
    //    return this.parent.getRootParent();
    //}

    //@Override
    //public Procedure<RESULT> getProcedure(
    //        final String procedureName )
    //{
    //    return this.getRootParent().getProcedure( procedureName );
    //}

    //@Override
    //public boolean isCoroutineRoot()
    //{
    //    return false;
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
