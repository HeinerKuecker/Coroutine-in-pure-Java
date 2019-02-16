package de.heinerkuecker.coroutine.stmt.complex;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import de.heinerkuecker.coroutine.CoroutineOrFunctioncallOrComplexstmt;
import de.heinerkuecker.coroutine.Function;
import de.heinerkuecker.coroutine.arg.Argument;
import de.heinerkuecker.coroutine.arg.Arguments;
import de.heinerkuecker.coroutine.exprs.CoroExpression;
import de.heinerkuecker.coroutine.exprs.GetFunctionArgument;
import de.heinerkuecker.coroutine.exprs.complex.ComplexExpression;
import de.heinerkuecker.coroutine.stmt.flow.BreakOrContinue;
import de.heinerkuecker.util.ArrayDeepToString;

public class FunctionCall<
    FUNCTION_RETURN ,
    COROUTINE_RETURN/*, PARENT extends CoroutineOrFunctioncallOrComplexstmt<COROUTINE_RETURN, PARENT>*/ ,
    RESUME_ARGUMENT
    >
extends ComplexExpression<
    FunctionCall<FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> ,
    FunctionCallState<FUNCTION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT> ,
    FUNCTION_RETURN ,
    COROUTINE_RETURN ,
    //PARENT
    RESUME_ARGUMENT
    >
//implements CoroutineOrFunctioncallOrComplexstmt<COROUTINE_RETURN/*, CoroutineIterator<COROUTINE_RETURN>*/>
//implements SimpleExpression<FUNCTION_RETURN , COROUTINE_RETURN>
implements CoroExpression<FUNCTION_RETURN , COROUTINE_RETURN>
{
    ///**
    // * Es muss ein ComplexStmt sein,
    // * weil dieser mit ComplexStmtState
    // * einen State hat, welcher bei
    // * einem SimpleStmt nicht vorhanden
    // * ist und dessen State in dieser
    // * Klasse verwaltet werden müsste.
    // */
    //final ComplexStmt<?, ?, COROUTINE_RETURN /*, /*PARENT* / CoroutineIterator<COROUTINE_RETURN>*/> bodyComplexStmt;

    //final Function<COROUTINE_RETURN> function;
    final String functionName;

    // TODO getter
    //final Map<String, FunctionArgument<?>> functionArguments;
    final Argument<? , COROUTINE_RETURN>[] functionArguments;

    /**
     * Reifier for type param {@link #COROUTINE_RETURN} to solve unchecked casts.
     */
    private Class<? extends COROUTINE_RETURN> coroutineReturnType;

    /**
     * Reifier for type param {@link #FUNCTION_RETURN} to solve unchecked casts.
     */
    private final Class<? extends FUNCTION_RETURN> functionReturnType;

    /**
     * Constructor.
     */
    @SafeVarargs
    public FunctionCall(
            //final CoroIterStmt<COROUTINE_RETURN/*, ? super PARENT/*CoroutineIterator<COROUTINE_RETURN>*/> ... bodyStmts
            //final Function<COROUTINE_RETURN> function
            final String functionName ,
            final Class<? extends FUNCTION_RETURN> functionReturnType ,
            final Argument<? , COROUTINE_RETURN>... args )
    {
        super(
                //creationStackOffset
                3 );

        this.functionName = Objects.requireNonNull( functionName );

        this.functionReturnType = Objects.requireNonNull( functionReturnType );

        //final LinkedHashMap<String, FunctionArgument<?>> argMap = new LinkedHashMap<>();
        //
        //if ( args != null )
        //{
        //    for ( FunctionArgument<?> arg : args )
        //    {
        //        if ( argMap.containsKey( Objects.requireNonNull( arg.name ) ) )
        //        {
        //            throw new IllegalArgumentException( "argument name " + arg.name + " already in use" );
        //        }
        //
        //        argMap.put(
        //                Objects.requireNonNull( arg.name ) ,
        //                Objects.requireNonNull( arg ) );
        //    }
        //}
        //
        //this.functionArguments = Collections.unmodifiableMap( argMap );
        this.functionArguments = args;
    }

    ///**
    // * @param creationStackOffset
    // */
    //protected Function(int creationStackOffset) {
    //    super(creationStackOffset);
    //    // TODO Auto-generated constructor stub
    //}

    /**
     * @see ComplexStmt#newState
     */
    @Override
    public FunctionCallState<FUNCTION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT> newState(
            final CoroutineOrFunctioncallOrComplexstmt<FUNCTION_RETURN , COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        //final Map<String, Object> functionArgumentValues = new LinkedHashMap<>();
        //for ( FunctionArgument<?> arg : functionArguments.values() )
        //{
        //    functionArgumentValues.put(
        //            arg.name ,
        //            arg.getValue(
        //                    parent ) );
        //}

        final Arguments arguments =
                new Arguments(
                        // isInitializationCheck
                        false ,
                        // checkMandantoryValues
                        true ,
                        // params
                        parent.getFunction( this.functionName ).params ,
                        // args
                        functionArguments ,
                        parent );

        final FunctionCallState<FUNCTION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT> functionCallState =
                new FunctionCallState<FUNCTION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT>(
                        // isInitializationCheck
                        false ,
                        this ,
                        coroutineReturnType ,
                        // functionArgumentValues
                        arguments ,
                        parent );

        return functionCallState;
    }

    public FunctionCallState<FUNCTION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT> newStateForCheck(
            final CoroutineOrFunctioncallOrComplexstmt<FUNCTION_RETURN , COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        final Arguments arguments =
                new Arguments(
                        // isInitializationCheck
                        true ,
                        // checkMandantoryValues
                        false ,
                        // params
                        parent.getFunction( this.functionName ).params ,
                        // args
                        null ,
                        parent );

        final FunctionCallState<FUNCTION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT> functionCallState =
                new FunctionCallState<>(
                        // isInitializationCheck
                        true ,
                        this ,
                        coroutineReturnType ,
                        // functionArgumentValues
                        arguments ,
                        parent );

        return functionCallState;
    }

    //@Override
    //public FUNCTION_RETURN evaluate(
    //        final HasArgumentsAndVariables<?> parent )
    //{
    //    final FunctionCallState<FUNCTION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT> functionCallState =
    //            newState( (CoroutineOrFunctioncallOrComplexstmt<FUNCTION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT>) /*this*/parent );
    //
    //    CoroStmtResult<FUNCTION_RETURN, COROUTINE_RETURN> functionCallResult =
    //            functionCallState.execute();
    //
    //    if ( functionCallResult instanceof FunctionReturnWithResult )
    //    {
    //        FunctionReturnWithResult<FUNCTION_RETURN, COROUTINE_RETURN> functionReturnWithResult =
    //                (FunctionReturnWithResult<FUNCTION_RETURN , COROUTINE_RETURN>) functionCallResult;
    //
    //
    //        return functionReturnWithResult.result;
    //    }
    //
    //    throw new RuntimeException( "not implemented" );
    //}

    @SuppressWarnings("unchecked")
    @Override
    public Class<? extends FUNCTION_RETURN>[] type()
    {
        return new Class[] { this.functionReturnType };
    }

    @Override
    public List<BreakOrContinue<? , ? , ?>> getUnresolvedBreaksOrContinues(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<? , COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        if ( alreadyCheckedFunctionNames.contains( this.functionName ) )
        {
            return Collections.emptyList();
        }

        alreadyCheckedFunctionNames.add( functionName );

        //return this.function.bodyComplexStmt.getUnresolvedBreaksOrContinues( parent );
        return parent.getFunction( this.functionName ).bodyComplexStmt.getUnresolvedBreaksOrContinues(
                alreadyCheckedFunctionNames ,
                parent );
    }

    @Override
    public void checkLabelAlreadyInUse(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<? , COROUTINE_RETURN, RESUME_ARGUMENT> parent ,
            final Set<String> labels )
    {
        if ( alreadyCheckedFunctionNames.contains( functionName ) )
        {
            return;
        }

        alreadyCheckedFunctionNames.add( functionName );

        //this.function.checkLabelAlreadyInUse();
        parent.getFunction( this.functionName ).checkLabelAlreadyInUse(
                alreadyCheckedFunctionNames ,
                parent );
    }

    @Override
    public List<GetFunctionArgument<? , ?>> getFunctionArgumentGetsNotInFunction()
    {
        // all subsequent GetFunctionArgument are in function
        return Collections.emptyList();
    }

    @Override
    public void setStmtCoroutineReturnType(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ? , ?> parent ,
            final Class<? extends COROUTINE_RETURN> coroutineReturnType )
    {
        setExprCoroutineReturnType(
                alreadyCheckedFunctionNames ,
                parent ,
                coroutineReturnType );
    }

    @Override
    public void setExprCoroutineReturnType(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent ,
            final Class<?> coroutineReturnType )
    {
        this.coroutineReturnType = (Class<? extends COROUTINE_RETURN>) coroutineReturnType;

        if ( alreadyCheckedFunctionNames.contains( functionName ) )
        {
            return;
        }

        alreadyCheckedFunctionNames.add( functionName );

        parent.getFunction( this.functionName ).bodyComplexStmt.setStmtCoroutineReturnType(
                alreadyCheckedFunctionNames ,
                parent ,
                (Class) coroutineReturnType );
    }

    @Override
    public void checkUseVariables(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ? , ?> parent ,
            final Map<String, Class<?>> globalVariableTypes ,
            final Map<String, Class<?>> localVariableTypes )
    {
        if ( alreadyCheckedFunctionNames.contains( functionName ) )
        {
            return;
        }

        alreadyCheckedFunctionNames.add( functionName );

        parent.getFunction( this.functionName ).bodyComplexStmt.checkUseVariables(
                alreadyCheckedFunctionNames ,
                parent ,
                globalVariableTypes ,
                // localVariableTypes
                new HashMap<>() );
    }

    @Override
    public void checkUseArguments(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ? , ?> parent )
    {
        for ( final Argument<? , ?> procArg : this.functionArguments )
        {
            procArg.checkUseArguments(
                    alreadyCheckedFunctionNames,
                    // parent
                    this.newStateForCheck(
                            (CoroutineOrFunctioncallOrComplexstmt<FUNCTION_RETURN , COROUTINE_RETURN, RESUME_ARGUMENT>) parent ) );
        }

        if ( alreadyCheckedFunctionNames.contains( functionName ) )
        {
            return;
        }

        alreadyCheckedFunctionNames.add( functionName );

        final Function<?, ?, ?> function = parent.getFunction( this.functionName );

        if ( function == null )
        {
            throw new IllegalStateException( "unknown function: " + this.functionName );
        }

        function.bodyComplexStmt.checkUseArguments(
                alreadyCheckedFunctionNames ,
                //parent
                this.newStateForCheck(
                        (CoroutineOrFunctioncallOrComplexstmt<FUNCTION_RETURN , COROUTINE_RETURN, RESUME_ARGUMENT>) parent ) );
    }

    @Override
    public String toString(
            final CoroutineOrFunctioncallOrComplexstmt</*FUNCTION_RETURN*/? , COROUTINE_RETURN, RESUME_ARGUMENT> parent ,
            final String indent ,
            final ComplexStmtState<?, ?, /*FUNCTION_RETURN*/? , COROUTINE_RETURN , RESUME_ARGUMENT> lastStmtExecuteState ,
            final ComplexStmtState<?, ?, /*FUNCTION_RETURN*/? , COROUTINE_RETURN , RESUME_ARGUMENT> nextStmtExecuteState )
    {
        @SuppressWarnings("unchecked")
        final FunctionCallState<FUNCTION_RETURN , COROUTINE_RETURN /*, PARENT*/ , RESUME_ARGUMENT> lastProcExecuteState =
                (FunctionCallState<FUNCTION_RETURN , COROUTINE_RETURN /*, PARENT*/ , RESUME_ARGUMENT>) lastStmtExecuteState;

        @SuppressWarnings("unchecked")
        final FunctionCallState<FUNCTION_RETURN , COROUTINE_RETURN /*, PARENT*/ , RESUME_ARGUMENT> nextProcExecuteState =
                (FunctionCallState<FUNCTION_RETURN , COROUTINE_RETURN /*, PARENT*/ , RESUME_ARGUMENT>) nextStmtExecuteState;

        final ComplexStmtState<?, ?, FUNCTION_RETURN , COROUTINE_RETURN /*, PARENT*/ , RESUME_ARGUMENT> lastBodyState;
        if ( lastProcExecuteState != null )
        {
            lastBodyState = lastProcExecuteState.bodyComplexState;
        }
        else
        {
            lastBodyState = null;
        }

        final ComplexStmtState<?, ?, FUNCTION_RETURN , COROUTINE_RETURN /*, PARENT*/ , RESUME_ARGUMENT> nextBodyState;
        if ( nextProcExecuteState != null )
        {
            nextBodyState = nextProcExecuteState.bodyComplexState;
        }
        else
        {
            nextBodyState = null;
        }

        final String functionArgumentsStr;
        if ( nextProcExecuteState == null )
        //if ( lastBodyState == null &&
        //        nextBodyState == null )
        {
            functionArgumentsStr = "";
        }
        else
        {
            functionArgumentsStr =
                    indent + " " +
                            //"function arguments: " +
                            "function argument values: " +
                            nextProcExecuteState.arguments +
                            "\n";
        }

        //final String functionVariablesStr;
        //if ( nextProcExecuteState == null ||
        //        ( lastBodyState == null &&
        //        nextBodyState == null ) )
        //{
        //    functionVariablesStr = "";
        //}
        //else
        //{
        //    functionVariablesStr =
        //            indent + " " +
        //                    "function variables: " +
        //                    nextProcExecuteState.variables +
        //                    "\n";
        //}

        final String functionBodyComplexStmtStr;
        if ( lastBodyState == null &&
                nextBodyState == null )
        {
            functionBodyComplexStmtStr = "";
        }
        else
            // print function body only when next or last source position is in function
        {
            functionBodyComplexStmtStr =
                    //this.function.bodyComplexStmt.toString(
                    parent.getFunction( this.functionName ).bodyComplexStmt.toString(
                            parent ,
                            indent + " " ,
                            lastBodyState ,
                            nextBodyState );
        }

        final String functionArgumentExpressionsStr;
        if ( this.functionArguments == null || this.functionArguments.length == 0 )
        {
            functionArgumentExpressionsStr = "";
        }
        else
        {
            functionArgumentExpressionsStr =
                    indent + " " +
                    "function argument expressions: " +
                    ArrayDeepToString.deepToString( this.functionArguments ) +
                    "\n";
        }
        return
                indent +
                //( this.label != null ? this.label + " : " : "" ) +
                this.getClass().getSimpleName() +
                " " +
                this.functionName +
                //" (" +
                ( this.creationStackTraceElement != null ? " " + this.creationStackTraceElement : "" ) +
                "\n" +
                functionArgumentExpressionsStr +
                functionArgumentsStr +
                //functionVariablesStr +
                functionBodyComplexStmtStr;
    }

}
