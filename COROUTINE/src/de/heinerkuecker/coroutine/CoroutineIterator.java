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
import de.heinerkuecker.coroutine.exprs.GetProcedureArgument;
import de.heinerkuecker.coroutine.exprs.exc.UseGetProcedureArgumentOutsideOfProcedureException;
import de.heinerkuecker.coroutine.stmt.CoroIterStmt;
import de.heinerkuecker.coroutine.stmt.CoroIterStmtResult;
import de.heinerkuecker.coroutine.stmt.complex.Block;
import de.heinerkuecker.coroutine.stmt.complex.ComplexStmt;
import de.heinerkuecker.coroutine.stmt.complex.ComplexStmtState;
import de.heinerkuecker.coroutine.stmt.flow.BreakOrContinue;
import de.heinerkuecker.coroutine.stmt.flow.exc.UnresolvedBreakOrContinueException;
import de.heinerkuecker.coroutine.stmt.simple.DeclareVariable;
import de.heinerkuecker.util.ArrayDeepToString;

/**
 * Class to generate a sequence of values
 * with an emulated processor in the manner of
 * <a href="TODO">Wikipedia Coroutine</a>
 * without {@link Thread} or
 * memory consuming buffer or
 * bytecode manipulation.
 *
 * @param <COROUTINE_RETURN> result type of this method {@link Iterator#next()}
 * @author Heiner K&uuml;cker
 */
public class CoroutineIterator<COROUTINE_RETURN>
implements
    CoroutineOrProcedureOrComplexstmt<COROUTINE_RETURN, Void> ,
    Iterator<COROUTINE_RETURN>
{
    /**
     * Es muss ein ComplexStep sein,
     * weil dieser mit ComplexStepState
     * einen State hat, welcher bei
     * einem SimpleStep nicht vorhanden
     * ist und dessen State in dieser
     * Klasse verwaltet werden müsste.
     */
    private final ComplexStmt<?, ?, COROUTINE_RETURN /*, /*PARENT* / CoroutineIterator<COROUTINE_RETURN>*/ , Void> complexStep;
    private ComplexStmtState<?, ?, COROUTINE_RETURN /*, CoroutineIterator<COROUTINE_RETURN>*/ , Void> nextComplexStepState;

    // for debug
    private ComplexStmtState<?, ?, COROUTINE_RETURN /*, CoroutineIterator<COROUTINE_RETURN>*/ , Void> lastComplexStepState;

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
    private COROUTINE_RETURN next;

    /**
     * Reifier for type param {@link #COROUTINE_RETURN} to solve unchecked casts.
     */
    private final Class<? extends COROUTINE_RETURN> resultType;

    /**
     * GlobalVariables.
     */
    //public final HashMap<String, Object> vars = new HashMap<>();
    public final GlobalVariables globalVariables = new GlobalVariables();

    private final Map<String, Procedure<COROUTINE_RETURN , Void>> procedures = new HashMap<>();

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
            final Class<? extends COROUTINE_RETURN> resultType ,
            final Iterable<Procedure<COROUTINE_RETURN , Void>> procedures ,
            //final Map<String, ? extends Object> initialVariableValues ,
            final Parameter[] params ,
            final Argument<?>[] args ,
            final DeclareVariable<COROUTINE_RETURN, Void, ?>[] globalVariableDeclarations ,
            final CoroIterStmt<COROUTINE_RETURN /*, /*PARENT * / CoroutineIterator<COROUTINE_RETURN>*/>... steps )
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
            for ( final Procedure<COROUTINE_RETURN , Void> procedure : procedures )
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
            for ( DeclareVariable<COROUTINE_RETURN, Void, ?> globalVariableDeclaration : globalVariableDeclarations )
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
            final Class<? extends COROUTINE_RETURN> resultType ,
            final CoroIterStmt<COROUTINE_RETURN /*, /*PARENT * / CoroutineIterator<COROUTINE_RETURN>*/>... steps )
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
    public Procedure<COROUTINE_RETURN , Void> getProcedure(
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

        final CoroIterStmtResult<COROUTINE_RETURN> executeResult =
                this.nextComplexStepState.execute(
                        //this
                        );

        if ( executeResult == null ||
                executeResult instanceof CoroIterStmtResult.ContinueCoroutine )
            // end of sub complex state without result
        {
            // Iterator ends
            this.hasNext = false;
            this.nextComplexStepState = null;
        }
        else if ( executeResult instanceof CoroIterStmtResult.YieldReturnWithResult )
        {
            final CoroIterStmtResult.YieldReturnWithResult<COROUTINE_RETURN> yieldReturnWithResult =
                    (CoroIterStmtResult.YieldReturnWithResult<COROUTINE_RETURN>) executeResult;

            this.next =
                    resultType.cast(
                            yieldReturnWithResult.result );

            this.hasNext = true;
        }
        else if ( executeResult instanceof CoroIterStmtResult.FinallyReturnWithResult )
        {
            final CoroIterStmtResult.FinallyReturnWithResult<COROUTINE_RETURN> yieldReturnWithResult =
                    (CoroIterStmtResult.FinallyReturnWithResult<COROUTINE_RETURN>) executeResult;

            this.next =
                    resultType.cast(
                            yieldReturnWithResult.result );

            this.hasNext = true;
            finallyReturnRaised = true;
            this.nextComplexStepState = null;
        }
        else if ( executeResult instanceof CoroIterStmtResult.FinallyReturnWithoutResult )
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
    public COROUTINE_RETURN next()
    {
        if ( ! hasNext() )
        {
            throw new NoSuchElementException();
        }

        // enforce calling lookahead code in hasNext()
        this.hasNext = null;

        final COROUTINE_RETURN result = this.next;

        // free memory
        this.next = null;

        return result;
    }

    /**
     * @see CoroutineOrProcedureOrComplexstmt#saveLastStepState()
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
     * @see CoroutineOrProcedureOrComplexstmt#localVars()
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
     * @see CoroutineOrProcedureOrComplexstmt#globalVars()
     */
    @Override
    //public Map<String, Object> globalVars()
    public VariablesOrLocalVariables globalVars()
    {
        return this.globalVariables;
    }

    /**
     * @see CoroutineOrProcedureOrComplexstmt#procedureArgumentValues()
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
    public Void getResumeArgument()
    {
        // TODO code smell ausgeschlagenes Erbe Refused bequest
        //return null;
        throw new IllegalStateException( this.getClass().getSimpleName() + " has no procedure arguments" );
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
