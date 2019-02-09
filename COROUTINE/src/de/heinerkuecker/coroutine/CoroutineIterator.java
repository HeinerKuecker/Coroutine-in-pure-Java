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
import de.heinerkuecker.coroutine.exprs.GetFunctionArgument;
import de.heinerkuecker.coroutine.exprs.exc.UseGetFunctionArgumentOutsideOfFunctionException;
import de.heinerkuecker.coroutine.stmt.CoroStmt;
import de.heinerkuecker.coroutine.stmt.CoroStmtResult;
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
    CoroutineOrFunctioncallOrComplexstmt<Void , COROUTINE_RETURN, Void> ,
    Iterator<COROUTINE_RETURN>
{
    /**
     * Es muss ein ComplexStmt sein,
     * weil dieser mit ComplexStmtState
     * einen State hat, welcher bei
     * einem SimpleStmt nicht vorhanden
     * ist und dessen State in dieser
     * Klasse verwaltet werden müsste.
     */
    private final ComplexStmt<?, ?, Void, COROUTINE_RETURN /*, /*PARENT* / CoroutineIterator<COROUTINE_RETURN>*/ , Void> complexStmt;
    private ComplexStmtState<?, ?, Void, COROUTINE_RETURN /*, CoroutineIterator<COROUTINE_RETURN>*/ , Void> nextComplexStmtState;

    // for debug
    private ComplexStmtState<?, ?, Void, COROUTINE_RETURN /*, CoroutineIterator<COROUTINE_RETURN>*/ , Void> lastComplexStmtState;

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
    private final Class<? extends COROUTINE_RETURN> coroutineReturnType;

    /**
     * GlobalVariables.
     */
    //public final HashMap<String, Object> vars = new HashMap<>();
    public final GlobalVariables globalVariables = new GlobalVariables();

    private final Map<String, Function<?, COROUTINE_RETURN , Void>> functions = new HashMap<>();

    public final Map<String, Parameter> params;

    public final Arguments arguments;

    /**
     * Constructor.
     *
     * @param functions can be <code>null</code>
     * @param initialVariableValues key value pairs to put initial in globalVariables {@link #vars}, can be <code>null</code>
     * @param stmts statements for coroutine processor
     */
    @SafeVarargs
    public CoroutineIterator(
            final Class<? extends COROUTINE_RETURN> coroutineReturnType ,
            final Iterable<Function<?, COROUTINE_RETURN , Void>> functions ,
            //final Map<String, ? extends Object> initialVariableValues ,
            final Parameter[] params ,
            final Argument<? , ?>[] args ,
            final DeclareVariable<Void, COROUTINE_RETURN, Void, ?>[] globalVariableDeclarations ,
            final CoroStmt<Void, COROUTINE_RETURN /*, /*PARENT * / CoroutineIterator<COROUTINE_RETURN>*/>... stmts )
    {
        //this( stmts );

        this.coroutineReturnType =
                Objects.requireNonNull(
                        coroutineReturnType );

        this.complexStmt =
                Block.convertStmtsToComplexStmt(
                        // creationStackOffset
                        5 ,
                        stmts );

        if ( functions != null )
        {
            for ( final Function<? , COROUTINE_RETURN , Void> function : functions )
            {
                if ( this.functions.containsKey( function.name ) )
                {
                    throw new IllegalArgumentException(
                            "function name already in use: " +
                                    function.name );
                }

                this.functions.put(
                        function.name ,
                        function );
            }
        }

        this.params =
                Function.initParams(
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
            for ( DeclareVariable<Void, COROUTINE_RETURN, Void, ?> globalVariableDeclaration : globalVariableDeclarations )
            {
                globalVariableDeclaration.execute( this );
            }
        }

        this.complexStmt.setStmtCoroutineReturnType(
                // alreadyCheckedFunctionNames
                new HashSet<>() ,
                this ,
                coroutineReturnType );

        doMoreInitializations();
    }

    /**
     * Constructor.
     *
     * @param stmts statements for coroutine processor
     */
    @SafeVarargs
    public CoroutineIterator(
            final Class<? extends COROUTINE_RETURN> coroutineReturnType ,
            final CoroStmt<Void, COROUTINE_RETURN /*, /*PARENT * / CoroutineIterator<COROUTINE_RETURN>*/>... stmts )
    {
        this.coroutineReturnType =
                Objects.requireNonNull(
                        coroutineReturnType );

        this.complexStmt =
                Block.convertStmtsToComplexStmt(
                        // creationStackOffset
                        5 ,
                        stmts );

        this.complexStmt.setStmtCoroutineReturnType(
                // alreadyCheckedFunctionNames
                new HashSet<>() ,
                this ,
                coroutineReturnType );

        this.params = Collections.emptyMap();

        this.arguments = Arguments.EMPTY;

        doMoreInitializations();
    }

    private void doMoreInitializations()
    {
        if ( CoroutineDebugSwitches.initializationChecks )
        {
            checkForUnresolvedBreaksAndContinues();

            checkForUseGetFunctionArgumentOutsideOfFunctionException();

            this.complexStmt.checkLabelAlreadyInUse(
                    // alreadyCheckedFunctionNames
                    new HashSet<>() ,
                    this ,
                    // labels
                    new HashSet<>() );

            this.complexStmt.checkUseArguments(
                    // alreadyCheckedFunctionNames
                    new HashSet<>() ,
                    this );

            this.complexStmt.checkUseVariables(
                    // alreadyCheckedFunctionNames
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
    public Function<? , COROUTINE_RETURN , Void> getFunction(
            final String functionName )
    {
        return this.functions.get( functionName );
    }

    /**
     * Check for unresolved breaks and continues.
     */
    private void checkForUnresolvedBreaksAndContinues()
    {
        final List<BreakOrContinue<?, ?, ?>> unresolvedBreaksOrContinues =
                complexStmt.getUnresolvedBreaksOrContinues(
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
     * Check for {@link GetFunctionArgument} outside of function.
     */
    private void checkForUseGetFunctionArgumentOutsideOfFunctionException()
    {
        final List<GetFunctionArgument<? , ?>> getFunctionArgumentsNotInFunction =
                complexStmt.getFunctionArgumentGetsNotInFunction();

        if ( ! getFunctionArgumentsNotInFunction.isEmpty() )
        {
            throw new UseGetFunctionArgumentOutsideOfFunctionException(
                    "FunctionArguments not in function: " +
                            getFunctionArgumentsNotInFunction );
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

        if ( this.nextComplexStmtState == null )
            // no existing state from previous execute call
        {
            this.nextComplexStmtState =
                    complexStmt.newState(
                            this );
        }
        else if ( this.nextComplexStmtState.isFinished() )
        {
            return false;
        }

        final CoroStmtResult<Void , COROUTINE_RETURN> executeResult =
                this.nextComplexStmtState.execute(
                        //this
                        );

        if ( executeResult == null ||
                executeResult instanceof CoroStmtResult.ContinueCoroutine )
            // end of sub complex state without result
        {
            // Iterator ends
            this.hasNext = false;
            this.nextComplexStmtState = null;
        }
        else if ( executeResult instanceof CoroStmtResult.YieldReturnWithResult )
        {
            final CoroStmtResult.YieldReturnWithResult<Void , COROUTINE_RETURN> yieldReturnWithResult =
                    (CoroStmtResult.YieldReturnWithResult<Void , COROUTINE_RETURN>) executeResult;

            this.next =
                    coroutineReturnType.cast(
                            yieldReturnWithResult.result );

            this.hasNext = true;
        }
        else if ( executeResult instanceof CoroStmtResult.FinallyReturnWithResult )
        {
            final CoroStmtResult.FinallyReturnWithResult<Void , COROUTINE_RETURN> yieldReturnWithResult =
                    (CoroStmtResult.FinallyReturnWithResult<Void , COROUTINE_RETURN>) executeResult;

            this.next =
                    coroutineReturnType.cast(
                            yieldReturnWithResult.result );

            this.hasNext = true;
            finallyReturnRaised = true;
            this.nextComplexStmtState = null;
        }
        else if ( executeResult instanceof CoroStmtResult.FinallyReturnWithoutResult )
        {
            // Iterator ends
            this.hasNext = false;
            this.nextComplexStmtState = null;
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
     * @see CoroutineOrFunctioncallOrComplexstmt#saveLastStmtState()
     */
    @Override
    public void saveLastStmtState()
    {
        if ( CoroutineDebugSwitches.saveToStringInfos )
        {
            this.lastComplexStmtState = nextComplexStmtState.createClone();
        }
    }

    /**
     * @see CoroutineOrFunctioncallOrComplexstmt#localVars()
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
     * @see CoroutineOrFunctioncallOrComplexstmt#globalVars()
     */
    @Override
    //public Map<String, Object> globalVars()
    public VariablesOrLocalVariables globalVars()
    {
        return this.globalVariables;
    }

    /**
     * @see CoroutineOrFunctioncallOrComplexstmt#functionArgumentValues()
     */
    @Override
    public Arguments functionArgumentValues()
    {
        // TODO code smell ausgeschlagenes Erbe Refused bequest
        //return null;
        throw new IllegalStateException( this.getClass().getSimpleName() + " has no function arguments" );
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
    public Map<String, Class<?>> functionParameterTypes()
    {
        //throw new RuntimeException( "not implemented" );
        //return this.arguments.functionParameterTypes();
        throw new IllegalStateException( "this is no function" );
    }

    @Override
    public Map<String, Class<?>> globalParameterTypes()
    {
        //throw new RuntimeException( "not implemented" );
        //return functionParameterTypes();
        return arguments.functionParameterTypes();
    }

    @Override
    public Void getResumeArgument()
    {
        // TODO code smell ausgeschlagenes Erbe Refused bequest
        //return null;
        throw new IllegalStateException( this.getClass().getSimpleName() + " has no function arguments" );
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
                this.complexStmt.toString(
                        //parent
                        this ,
                        //indent min length for last and next
                        "     " ,
                        this.lastComplexStmtState ,
                        this.nextComplexStmtState );
    }

}
