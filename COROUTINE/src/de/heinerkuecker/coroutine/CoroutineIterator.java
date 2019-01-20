package de.heinerkuecker.coroutine;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

import de.heinerkuecker.coroutine.arg.Argument;
import de.heinerkuecker.coroutine.arg.Arguments;
import de.heinerkuecker.coroutine.arg.Parameter;
import de.heinerkuecker.coroutine.expression.GetProcedureArgument;
import de.heinerkuecker.coroutine.expression.exc.UseGetProcedureArgumentOutsideOfProcedureException;
import de.heinerkuecker.coroutine.step.CoroIterStep;
import de.heinerkuecker.coroutine.step.CoroIterStepResult;
import de.heinerkuecker.coroutine.step.complex.Block;
import de.heinerkuecker.coroutine.step.complex.ComplexStep;
import de.heinerkuecker.coroutine.step.complex.ComplexStepState;
import de.heinerkuecker.coroutine.step.flow.BreakOrContinue;
import de.heinerkuecker.coroutine.step.flow.exc.UnresolvedBreakOrContinueException;
import de.heinerkuecker.coroutine.step.simple.DeclareVariable;
import de.heinerkuecker.util.ArrayDeepToString;

/**
 * Class to generate a sequence of values
 * with an emulated processor in the manner of
 * <a href="TODO">Wikipedia Coroutine</a>
 * without {@link Thread} or
 * memory consuming buffer or
 * bytecode manipulation.
 *
 * @param <RESULT> result type of this method {@link Iterator#next()}
 * @author Heiner K&uuml;cker
 */
public class CoroutineIterator<RESULT>
implements AbstrCoroIterator<RESULT/*, CoroutineIterator<RESULT>*/>
{
    /**
     * Es muss ein ComplexStep sein,
     * weil dieser mit ComplexStepState
     * einen State hat, welcher bei
     * einem SimpleStep nicht vorhanden
     * ist und dessen State in dieser
     * Klasse verwaltet werden müsste.
     */
    private final ComplexStep<?, ?, RESULT /*, /*PARENT* / CoroutineIterator<RESULT>*/> complexStep;
    private ComplexStepState<?, ?, RESULT /*, CoroutineIterator<RESULT>*/> nextComplexStepState;

    // for debug
    private ComplexStepState<?, ?, RESULT /*, CoroutineIterator<RESULT>*/> lastComplexStepState;


    private boolean finallyReturnRaised;

    /**
     * Tri state boolean for result
     * of method {@link #hasNext()}.
     *
     * <code>null</code> enforces calling lookahead code in {@link #hasNext()}.
     * Non <code>null</code> is the computed and cached result.
     */
    private Boolean hasNext;

    /**
     * Lookahead return value
     * for method {@link #next()},
     */
    private RESULT next;

    /**
     * Reifier for type param {@link #RESULT} to solve unchecked casts.
     */
    private final Class<? extends RESULT> resultType;

    /**
     * GlobalVariables.
     */
    //public final HashMap<String, Object> vars = new HashMap<>();
    public final GlobalVariables globalVariables = new GlobalVariables();

    private final Map<String, Procedure<RESULT>> procedures = new HashMap<>();

    public final Map<String, Parameter> params;

    public final Arguments arguments;

    /**
     * Constructor.
     *
     * @param procedures can be <code>null</code>
     * @param initialVariableValues key value pairs to put initial in globalVariables {@link #vars}, can be <code>null</code>
     * @param steps steps for coroutine processor
     */
    @SafeVarargs
    public CoroutineIterator(
            final Class<? extends RESULT> resultType ,
            final Iterable<Procedure<RESULT>> procedures ,
            //final Map<String, ? extends Object> initialVariableValues ,
            final Parameter[] params ,
            final Argument<?>[] args ,
            final DeclareVariable<RESULT, ?>[] globalVariableDeclarations ,
            final CoroIterStep<RESULT /*, /*PARENT * / CoroutineIterator<RESULT>*/>... steps )
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
            for ( final Procedure<RESULT> procedure : procedures )
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

        //if ( initialVariableValues != null )
        //{
        //    //this.vars.putAll( initialVariableValues );
        //    for ( Entry<String, ? extends Object> initialVariableEntry : initialVariableValues.entrySet() )
        //    {
        //        this.variables.set(
        //                initialVariableEntry.getKey() ,
        //                initialVariableEntry.getValue() );
        //    }
        //}

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
            for ( DeclareVariable<RESULT, ?> globalVariableDeclaration : globalVariableDeclarations )
            {
                globalVariableDeclaration.execute( this );
            }
        }

        this.complexStep.setResultType( resultType );

        doMoreInitializations();
    }

    /**
     * Constructor.
     *
     * @param steps steps for coroutine processor
     */
    @SafeVarargs
    public CoroutineIterator(
            final Class<? extends RESULT> resultType ,
            final CoroIterStep<RESULT /*, /*PARENT * / CoroutineIterator<RESULT>*/>... steps )
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
                    //isCoroutineRoot
                    //true ,
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

    @Override
    public Procedure<RESULT> getProcedure(
            final String procedureName )
    {
        return this.procedures.get( procedureName );
    }

    /**
     * Check for unresolved breaks and continues.
     */
    private void checkForUnresolvedBreaksAndContinues()
    {
        final List<BreakOrContinue<RESULT>> unresolvedBreaksOrContinues =
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
     * @see Iterator#hasNext()
     */
    @Override
    public boolean hasNext()
    {
        if ( this.hasNext != null )
        {
            return this.hasNext;
        }

        if ( finallyReturnRaised )
        {
            return false;
        }

        // --- lookahead ---

        if ( this.nextComplexStepState == null )
            // no existing state from previous execute call
        {
            this.nextComplexStepState =
                    complexStep.newState(
                            this );
        }
        else if ( this.nextComplexStepState.isFinished() )
        {
            return false;
        }

        final CoroIterStepResult<RESULT> executeResult =
                this.nextComplexStepState.execute(
                        //this
                        );

        if ( executeResult == null ||
                executeResult instanceof CoroIterStepResult.ContinueCoroutine )
            // end of sub complex state without result
        {
            // Iterator ends
            this.hasNext = false;
            this.nextComplexStepState = null;
        }
        else if ( executeResult instanceof CoroIterStepResult.YieldReturnWithResult )
        {
            final CoroIterStepResult.YieldReturnWithResult<RESULT> yieldReturnWithResult =
                    (CoroIterStepResult.YieldReturnWithResult<RESULT>) executeResult;

            this.next =
                    resultType.cast(
                            yieldReturnWithResult.result );

            this.hasNext = true;
        }
        else if ( executeResult instanceof CoroIterStepResult.FinallyReturnWithResult )
        {
            final CoroIterStepResult.FinallyReturnWithResult<RESULT> yieldReturnWithResult =
                    (CoroIterStepResult.FinallyReturnWithResult<RESULT>) executeResult;


            this.next =
                    resultType.cast(
                            yieldReturnWithResult.result );

            this.hasNext = true;
            finallyReturnRaised = true;
            this.nextComplexStepState = null;
        }
        else if ( executeResult instanceof CoroIterStepResult.FinallyReturnWithoutResult )
        {
            // Iterator ends
            this.hasNext = false;
            this.nextComplexStepState = null;
        }
        else
        {
            throw new IllegalStateException( String.valueOf( executeResult ) );
        }

        return this.hasNext;
    }

    /**
     * @see Iterator#next()
     */
    @Override
    public RESULT next()
    {
        if ( ! hasNext() )
        {
            throw new NoSuchElementException();
        }

        // enforce calling lookahead code in hasNext()
        this.hasNext = null;

        final RESULT result = this.next;

        // free memory
        this.next = null;

        return result;
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
    {
        return this.globalVariables;
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

    /**
     * @see CoroutineOrProcedureOrComplexstep#getRootParent()
     */
    @Override
    public CoroutineIterator<RESULT> getRootParent()
    {
        return this;
    }

    //@Override
    //public boolean isCoroutineRoot()
    //{
    //    return true;
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
        return globalVariables.getVariableTypes();
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return
                this.getClass().getSimpleName() + "\n" +
                "finallyReturnRaised=" + this.finallyReturnRaised + ", " +
                "hasNext=" + this.hasNext + ", " +
                "next=" + ArrayDeepToString.deepToString( this.next ) + ", " +
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
