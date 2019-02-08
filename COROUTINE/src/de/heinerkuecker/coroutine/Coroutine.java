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

public class Coroutine<COROUTINE_RETURN, RESUME_ARGUMENT>
implements CoroutineOrFunctioncallOrComplexstmt<Void , COROUTINE_RETURN, RESUME_ARGUMENT>
{
    /**
     * Es muss ein ComplexStmt sein,
     * weil dieser mit ComplexStmtState
     * einen State hat, welcher bei
     * einem SimpleStmt nicht vorhanden
     * ist und dessen State in dieser
     * Klasse verwaltet werden müsste.
     */
    private final ComplexStmt<?, ?, Void , COROUTINE_RETURN /*, /*PARENT* / CoroutineIterator<COROUTINE_RETURN>*/ , RESUME_ARGUMENT> complexStmt;
    private ComplexStmtState<?, ?, Void , COROUTINE_RETURN /*, CoroutineIterator<COROUTINE_RETURN>*/ , RESUME_ARGUMENT> nextComplexStmtState;

    // for debug
    private ComplexStmtState<?, ?, Void , COROUTINE_RETURN /*, CoroutineIterator<COROUTINE_RETURN>*/ , RESUME_ARGUMENT> lastComplexStmtState;

    private boolean finallyReturnRaised;

    /**
     * Reifier for type param {@link #COROUTINE_RETURN} to solve unchecked casts.
     */
    private final Class<? extends COROUTINE_RETURN> coroutineReturnType;

    /**
     * GlobalVariables.
     */
    //public final HashMap<String, Object> vars = new HashMap<>();
    private final GlobalVariables globalVariables = new GlobalVariables();

    private final Map<String, Function<?, COROUTINE_RETURN , RESUME_ARGUMENT>> functions = new HashMap<>();

    public final Map<String, Parameter> params;

    public final Arguments arguments;

    private RESUME_ARGUMENT resumeArgument;

    /**
     * Constructor.
     *
     * @param stmts statements for coroutine processor
     */
    @SafeVarargs
    public Coroutine(
            final Class<? extends COROUTINE_RETURN> coroutineReturnType ,
            final CoroStmt<Void , COROUTINE_RETURN /*, /*PARENT * / CoroutineIterator<COROUTINE_RETURN>*/>... stmts )
    {
        this.coroutineReturnType =
                Objects.requireNonNull(
                        coroutineReturnType );

        this.complexStmt =
                Block.convertStmtsToComplexStmt(
                        // creationStackOffset
                        5 ,
                        stmts );

        this.complexStmt.setCoroutineReturnType( coroutineReturnType );

        this.params = Collections.emptyMap();

        this.arguments = Arguments.EMPTY;

        doMoreInitializations();
    }

    /**
     * Constructor.
     *
     * @param functions can be <code>null</code>
     * @param initialVariableValues key value pairs to put initial in globalVariables {@link #vars}, can be <code>null</code>
     * @param stmts statements for coroutine processor
     */
    @SafeVarargs
    public Coroutine(
            final Class<? extends COROUTINE_RETURN> coroutineReturnType ,
            final Iterable<Function<? , COROUTINE_RETURN , RESUME_ARGUMENT>> functions ,
            //final Map<String, ? extends Object> initialVariableValues ,
            final Parameter[] params ,
            final Argument<?>[] args ,
            final DeclareVariable<Void , COROUTINE_RETURN, RESUME_ARGUMENT, ?>[] globalVariableDeclarations ,
            final CoroStmt<Void , COROUTINE_RETURN /*, /*PARENT * / CoroutineIterator<COROUTINE_RETURN>*/>... stmts )
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
            for ( final Function<? , COROUTINE_RETURN , RESUME_ARGUMENT> function : functions )
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
            for ( DeclareVariable<Void , COROUTINE_RETURN, RESUME_ARGUMENT , ?> globalVariableDeclaration : globalVariableDeclarations )
            {
                globalVariableDeclaration.execute( this );
            }
        }

        this.complexStmt.setCoroutineReturnType( coroutineReturnType );

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

    public COROUTINE_RETURN resume(
            final RESUME_ARGUMENT resumeArgument )
    {
        //throw new RuntimeException( "not implemented" );
        this.resumeArgument = resumeArgument;

        if ( finallyReturnRaised )
        {
            return null;
        }

        if ( this.nextComplexStmtState == null )
            // no existing state from previous execute call
        {
            this.nextComplexStmtState =
                    complexStmt.newState(
                            this );
        }
        else if ( this.nextComplexStmtState.isFinished() )
        {
            return null;
        }

        final CoroStmtResult<Void , COROUTINE_RETURN> executeResult =
                this.nextComplexStmtState.execute(
                        //this
                        );

        COROUTINE_RETURN result;
        if ( executeResult == null ||
                executeResult instanceof CoroStmtResult.ContinueCoroutine )
            // end of sub complex state without result
        {
            // Iterator ends
            result = null;
            // Keep for debug and for isFinished: this.nextComplexStmtState = null;
        }
        else if ( executeResult instanceof CoroStmtResult.YieldReturnWithResult )
        {
            final CoroStmtResult.YieldReturnWithResult<Void , COROUTINE_RETURN> yieldReturnWithResult =
                    (CoroStmtResult.YieldReturnWithResult<Void , COROUTINE_RETURN>) executeResult;

            result =
                    coroutineReturnType.cast(
                            yieldReturnWithResult.result );
        }
        else if ( executeResult instanceof CoroStmtResult.FinallyReturnWithResult )
        {
            final CoroStmtResult.FinallyReturnWithResult<Void , COROUTINE_RETURN> yieldReturnWithResult =
                    (CoroStmtResult.FinallyReturnWithResult<Void , COROUTINE_RETURN>) executeResult;

            result =
                    coroutineReturnType.cast(
                            yieldReturnWithResult.result );

            finallyReturnRaised = true;
            // Keep for debug and for isFinished: this.nextComplexStmtState = null;
        }
        else if ( executeResult instanceof CoroStmtResult.FinallyReturnWithoutResult )
        {
            // Iterator ends
            result = null;
            // Keep for debug and for isFinished: this.nextComplexStmtState = null;
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
                ( this.nextComplexStmtState != null &&
                this.nextComplexStmtState.isFinished() );
    }

    @Override
    public Function<? , COROUTINE_RETURN , RESUME_ARGUMENT> getFunction(
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
        final List<GetFunctionArgument<?>> getFunctionArgumentsNotInFunction =
                complexStmt.getFunctionArgumentGetsNotInFunction();

        if ( ! getFunctionArgumentsNotInFunction.isEmpty() )
        {
            throw new UseGetFunctionArgumentOutsideOfFunctionException(
                    "FunctionArguments not in function: " +
                            getFunctionArgumentsNotInFunction );
        }
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
                this.complexStmt.toString(
                        //parent
                        this ,
                        //indent min length for last and next
                        "     " ,
                        this.lastComplexStmtState ,
                        this.nextComplexStmtState );
    }

}
