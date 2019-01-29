package de.heinerkuecker.coroutine;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.arg.Argument;
import de.heinerkuecker.coroutine.arg.Arguments;
import de.heinerkuecker.coroutine.arg.Parameter;
import de.heinerkuecker.coroutine.exprs.GetProcedureArgument;
import de.heinerkuecker.coroutine.exprs.exc.UseGetProcedureArgumentOutsideOfProcedureException;
import de.heinerkuecker.coroutine.stmt.CoroIterStep;
import de.heinerkuecker.coroutine.stmt.CoroIterStepResult;
import de.heinerkuecker.coroutine.stmt.complex.Block;
import de.heinerkuecker.coroutine.stmt.complex.ComplexStep;
import de.heinerkuecker.coroutine.stmt.complex.ComplexStepState;
import de.heinerkuecker.coroutine.stmt.flow.BreakOrContinue;
import de.heinerkuecker.coroutine.stmt.flow.exc.UnresolvedBreakOrContinueException;
import de.heinerkuecker.coroutine.stmt.simple.DeclareVariable;

public class Coroutine<COROUTINE_RETURN, RESUME_ARGUMENT>
implements CoroutineOrProcedureOrComplexstep<COROUTINE_RETURN, RESUME_ARGUMENT>
{
    /**
     * Es muss ein ComplexStep sein,
     * weil dieser mit ComplexStepState
     * einen State hat, welcher bei
     * einem SimpleStep nicht vorhanden
     * ist und dessen State in dieser
     * Klasse verwaltet werden müsste.
     */
    private final ComplexStep<?, ?, COROUTINE_RETURN /*, /*PARENT* / CoroutineIterator<COROUTINE_RETURN>*/ , RESUME_ARGUMENT> complexStep;
    private ComplexStepState<?, ?, COROUTINE_RETURN /*, CoroutineIterator<COROUTINE_RETURN>*/ , RESUME_ARGUMENT> nextComplexStepState;

    // for debug
    private ComplexStepState<?, ?, COROUTINE_RETURN /*, CoroutineIterator<COROUTINE_RETURN>*/ , RESUME_ARGUMENT> lastComplexStepState;

    private boolean finallyReturnRaised;

    /**
     * Reifier for type param {@link #COROUTINE_RETURN} to solve unchecked casts.
     */
    private final Class<? extends COROUTINE_RETURN> resultType;

    /**
     * GlobalVariables.
     */
    //public final HashMap<String, Object> vars = new HashMap<>();
    private final GlobalVariables globalVariables = new GlobalVariables();

    private final Map<String, Procedure<COROUTINE_RETURN , RESUME_ARGUMENT>> procedures = new HashMap<>();

    public final Map<String, Parameter> params;

    public final Arguments arguments;

    private RESUME_ARGUMENT resumeArgument;

    /**
     * Constructor.
     *
     * @param steps steps for coroutine processor
     */
    @SafeVarargs
    public Coroutine(
            final Class<? extends COROUTINE_RETURN> resultType ,
            final CoroIterStep<COROUTINE_RETURN /*, /*PARENT * / CoroutineIterator<COROUTINE_RETURN>*/>... steps )
    {
        this.resultType =
                Objects.requireNonNull(
                        resultType );

        this.complexStep =
                Block.convertStepsToComplexStep(
                        // creationStackOffset
                        5 ,
                        steps );

        this.complexStep.setResultType( resultType );

        this.params = Collections.emptyMap();

        this.arguments = Arguments.EMPTY;

        doMoreInitializations();
    }

    /**
     * Constructor.
     *
     * @param procedures can be <code>null</code>
     * @param initialVariableValues key value pairs to put initial in globalVariables {@link #vars}, can be <code>null</code>
     * @param steps steps for coroutine processor
     */
    @SafeVarargs
    public Coroutine(
            final Class<? extends COROUTINE_RETURN> resultType ,
            final Iterable<Procedure<COROUTINE_RETURN , RESUME_ARGUMENT>> procedures ,
            //final Map<String, ? extends Object> initialVariableValues ,
            final Parameter[] params ,
            final Argument<?>[] args ,
            final DeclareVariable<COROUTINE_RETURN, RESUME_ARGUMENT, ?>[] globalVariableDeclarations ,
            final CoroIterStep<COROUTINE_RETURN /*, /*PARENT * / CoroutineIterator<COROUTINE_RETURN>*/>... steps )
    {
        //this( steps );

        this.resultType =
                Objects.requireNonNull(
                        resultType );

        this.complexStep =
                Block.convertStepsToComplexStep(
                        // creationStackOffset
                        5 ,
                        steps );

        if ( procedures != null )
        {
            for ( final Procedure<COROUTINE_RETURN , RESUME_ARGUMENT> procedure : procedures )
            {
                if ( this.procedures.containsKey( procedure.name ) )
                {
                    throw new IllegalArgumentException(
                            "procedure name already in use: " +
                                    procedure.name );
                }

                this.procedures.put(
                        procedure.name ,
                        procedure );
            }
        }

        this.params =
                Procedure.initParams(
                        params );

        this.arguments =
                new Arguments(
                        // isInitializationCheck
                        false ,
                        // checkMandantoryValues
                        true ,
                        this.params ,
                        args ,
                        //parent
                        this );

        if ( globalVariableDeclarations != null )
        {
            for ( DeclareVariable<COROUTINE_RETURN, RESUME_ARGUMENT , ?> globalVariableDeclaration : globalVariableDeclarations )
            {
                globalVariableDeclaration.execute( this );
            }
        }

        this.complexStep.setResultType( resultType );

        doMoreInitializations();
    }

    private void doMoreInitializations()
    {
        if ( CoroutineDebugSwitches.initializationChecks )
        {
            checkForUnresolvedBreaksAndContinues();

            checkForUseGetProcedureArgumentOutsideOfProcedureException();

            this.complexStep.checkLabelAlreadyInUse(
                    // alreadyCheckedProcedureNames
                    new HashSet<>() ,
                    this ,
                    // labels
                    new HashSet<>() );

            this.complexStep.checkUseArguments(
                    // alreadyCheckedProcedureNames
                    new HashSet<>() ,
                    this );

            this.complexStep.checkUseVariables(
                    // alreadyCheckedProcedureNames
                    new HashSet<>() ,
                    // parent
                    this ,
                    // globalVariableTypes
                    this.globalVariables.getVariableTypes() ,
                    // localVariableTypes
                    new HashMap<>() );
        }
    }

    public COROUTINE_RETURN resume(
            final RESUME_ARGUMENT resumeArgument )
    {
        //throw new RuntimeException( "not implemented" );
        this.resumeArgument = resumeArgument;

        if ( finallyReturnRaised )
        {
            return null;
        }

        if ( this.nextComplexStepState == null )
            // no existing state from previous execute call
        {
            this.nextComplexStepState =
                    complexStep.newState(
                            this );
        }
        else if ( this.nextComplexStepState.isFinished() )
        {
            return null;
        }

        final CoroIterStepResult<COROUTINE_RETURN> executeResult =
                this.nextComplexStepState.execute(
                        //this
                        );

        COROUTINE_RETURN result;
        if ( executeResult == null ||
                executeResult instanceof CoroIterStepResult.ContinueCoroutine )
            // end of sub complex state without result
        {
            // Iterator ends
            result = null;
            // Keep for debug and for isFinished: this.nextComplexStepState = null;
        }
        else if ( executeResult instanceof CoroIterStepResult.YieldReturnWithResult )
        {
            final CoroIterStepResult.YieldReturnWithResult<COROUTINE_RETURN> yieldReturnWithResult =
                    (CoroIterStepResult.YieldReturnWithResult<COROUTINE_RETURN>) executeResult;

            result =
                    resultType.cast(
                            yieldReturnWithResult.result );
        }
        else if ( executeResult instanceof CoroIterStepResult.FinallyReturnWithResult )
        {
            final CoroIterStepResult.FinallyReturnWithResult<COROUTINE_RETURN> yieldReturnWithResult =
                    (CoroIterStepResult.FinallyReturnWithResult<COROUTINE_RETURN>) executeResult;

            result =
                    resultType.cast(
                            yieldReturnWithResult.result );

            finallyReturnRaised = true;
            // Keep for debug and for isFinished: this.nextComplexStepState = null;
        }
        else if ( executeResult instanceof CoroIterStepResult.FinallyReturnWithoutResult )
        {
            // Iterator ends
            result = null;
            // Keep for debug and for isFinished: this.nextComplexStepState = null;
        }
        else
        {
            throw new IllegalStateException( String.valueOf( executeResult ) );
        }

        return result;
    }

    public boolean isFinished()
    {
        //throw new RuntimeException( "not implemented" );
        return
                finallyReturnRaised ||
                ( this.nextComplexStepState != null &&
                this.nextComplexStepState.isFinished() );
    }

    @Override
    public Procedure<COROUTINE_RETURN , RESUME_ARGUMENT> getProcedure(
            final String procedureName )
    {
        return this.procedures.get( procedureName );
    }

    /**
     * Check for unresolved breaks and continues.
     */
    private void checkForUnresolvedBreaksAndContinues()
    {
        final List<BreakOrContinue<?, ?>> unresolvedBreaksOrContinues =
                complexStep.getUnresolvedBreaksOrContinues(
                        new HashSet<>() ,
                        this );

        if ( ! unresolvedBreaksOrContinues.isEmpty() )
        {
            throw new UnresolvedBreakOrContinueException(
                    "unresolved breaks or continues: " +
                            unresolvedBreaksOrContinues );
        }
    }

    /**
     * Check for {@link GetProcedureArgument} outside of procedure.
     */
    private void checkForUseGetProcedureArgumentOutsideOfProcedureException()
    {
        final List<GetProcedureArgument<?>> getProcedureArgumentsNotInProcedure =
                complexStep.getProcedureArgumentGetsNotInProcedure();

        if ( ! getProcedureArgumentsNotInProcedure.isEmpty() )
        {
            throw new UseGetProcedureArgumentOutsideOfProcedureException(
                    "ProcedureArguments not in procedure: " +
                            getProcedureArgumentsNotInProcedure );
        }
    }

    /**
     * @see CoroutineOrProcedureOrComplexstep#saveLastStepState()
     */
    @Override
    public void saveLastStepState()
    {
        if ( CoroutineDebugSwitches.saveToStringInfos )
        {
            this.lastComplexStepState = nextComplexStepState.createClone();
        }
    }

    /**
     * @see CoroutineOrProcedureOrComplexstep#localVars()
     */
    @Override
    //public Map<String, Object> localVars()
    public VariablesOrLocalVariables localVars()
    //public BlockLocalVariables localVars()
    {
        return this.globalVariables;
        //throw new IllegalStateException( this.getClass().getSimpleName() + " has no local variables" );
        //return BlockLocalVariables.EMPTY;
        //return null;
    }

    /**
     * @see CoroutineOrProcedureOrComplexstep#globalVars()
     */
    @Override
    //public Map<String, Object> globalVars()
    public VariablesOrLocalVariables globalVars()
    {
        return this.globalVariables;
    }

    /**
     * @see CoroutineOrProcedureOrComplexstep#procedureArgumentValues()
     */
    @Override
    public Arguments procedureArgumentValues()
    {
        // TODO code smell ausgeschlagenes Erbe Refused bequest
        //return null;
        throw new IllegalStateException( this.getClass().getSimpleName() + " has no procedure arguments" );
    }

    /**
     * @see HasArgumentsAndVariables#globalArgumentValues()
     */
    @Override
    public Arguments globalArgumentValues()
    {
        return this.arguments;
    }

    //@Override
    //public CoroutineIterator<COROUTINE_RETURN> getRootParent()
    //{
    //    return this;
    //}

    @Override
    public Map<String, Class<?>> procedureParameterTypes()
    {
        //throw new RuntimeException( "not implemented" );
        //return this.arguments.procedureParameterTypes();
        throw new IllegalStateException( "this is no procedure" );
    }

    @Override
    public Map<String, Class<?>> globalParameterTypes()
    {
        //throw new RuntimeException( "not implemented" );
        //return procedureParameterTypes();
        return arguments.procedureParameterTypes();
    }

    @Override
    public RESUME_ARGUMENT getResumeArgument()
    {
        return this.resumeArgument;
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return
                this.getClass().getSimpleName() + "\n" +
                "finallyReturnRaised=" + this.finallyReturnRaised + ",\n" +
                "globalVariables=" + this.globalVariables + "\n" +
                this.complexStep.toString(
                        //parent
                        this ,
                        //indent min length for last and next
                        "     " ,
                        this.lastComplexStepState ,
                        this.nextComplexStepState );
    }

}
