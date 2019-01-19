package de.heinerkuecker.coroutine.step.complex;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep;
import de.heinerkuecker.coroutine.HasArgumentsAndVariables;
import de.heinerkuecker.coroutine.Procedure;
import de.heinerkuecker.coroutine.Variables;
import de.heinerkuecker.coroutine.arg.Arguments;
import de.heinerkuecker.coroutine.step.CoroIterStepResult;
import de.heinerkuecker.util.HCloneable;

class ProcedureCallState<RESULT>
extends ComplexStepState<
    ProcedureCallState<RESULT>,
    ProcedureCall<RESULT>,
    RESULT
    //ProcedureCall<RESULT>
    >
{
    private final ProcedureCall<RESULT/*, PARENT*/> procedureCall;

    boolean runInProcedure = true;

    // TODO getter
    ComplexStepState<?, ?, RESULT/*, PARENT*/> bodyComplexState;

    //private final CoroutineIterator<RESULT> rootParent;
    private final CoroutineOrProcedureOrComplexstep<RESULT> parent;

    //final Map<String, Object> procedureArgumentValues;
    final Arguments arguments;

    /**
     * Variables.
     */
    //public final HashMap<String, Object> vars = new HashMap<>();
    public final Variables variables = new Variables();

    /**
     * Constructor.
     *
     * @param procedureCall
     */
    protected ProcedureCallState(
            final boolean isInitializationCheck ,
            final ProcedureCall<RESULT> procedureCall ,
            final Class<? extends RESULT> resultType ,
            //final Map<String, Object> procedureArgumentValues
            final Arguments arguments ,
            final CoroutineOrProcedureOrComplexstep<RESULT> parent )
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

        final Procedure<RESULT> procedure =
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
            //final CoroutineOrProcedureOrComplexstep<RESULT> parent
            )
    {
        if ( runInProcedure )
        {
            final ComplexStep<?, ?, RESULT/*, PARENT*/> bodyComplexStep =
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
        for ( Entry<String, ? extends Object> initialVariableEntry : this.variables )
        {
            clone.variables.set(
                    initialVariableEntry.getKey() ,
                    initialVariableEntry.getValue() );
        }

        return clone;
    }

    /**
     * @see CoroutineOrProcedureOrComplexstep#saveLastStepState()
     */
    @Override
    public void saveLastStepState()
    {
        this.parent.getRootParent().saveLastStepState();
    }

    /**
     * @see CoroutineOrProcedureOrComplexstep#localVars()
     */
    @Override
    //public Map<String, Object> localVars()
    public Variables localVars()
    {
        return this.variables;
    }

    /**
     * @see CoroutineOrProcedureOrComplexstep#globalVars()
     */
    @Override
    //public Map<String, Object> globalVars()
    public Variables globalVars()
    {
        return this.parent/*.getRootParent()*/.globalVars();
    }

    /**
     * @see CoroutineOrProcedureOrComplexstep#procedureArgumentValues()
     */
    @Override
    //public Map<String, Object> procedureArgumentValues()
    public Arguments procedureArgumentValues()
    {
        return
                //this.procedureArgumentValues;
                this.arguments;
    }

    /**
     * @see HasArgumentsAndVariables#globalArgumentValues()
     */
    @Override
    public Arguments globalArgumentValues()
    {
        return this.parent.globalArgumentValues();
    }

    /**
     * @see CoroutineOrProcedureOrComplexstep#getRootParent()
     */
    @Override
    public CoroutineIterator<RESULT> getRootParent()
    {
        return this.parent.getRootParent();
    }

    /**
     * @see CoroutineOrProcedureOrComplexstep#getProcedure(String)
     */
    @Override
    public Procedure<RESULT> getProcedure(
            final String procedureName )
    {
        return this.getRootParent().getProcedure( procedureName );
    }

    @Override
    public boolean isCoroutineRoot()
    {
        return false;
    }

    @Override
    public Map<String, Class<?>> procedureParameterTypes()
    {
        return this.arguments.procedureParameterTypes();
    }

    @Override
    public Map<String, Class<?>> globalParameterTypes()
    {
        //throw new RuntimeException( "not implemented" );
        return this.getRootParent().arguments.procedureParameterTypes();
    }

}
