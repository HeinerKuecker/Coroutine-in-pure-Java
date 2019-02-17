package de.heinerkuecker.coroutine.stmt.complex;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineDebugSwitches;
import de.heinerkuecker.coroutine.CoroutineOrFunctioncallOrComplexstmt;
import de.heinerkuecker.coroutine.Function;
import de.heinerkuecker.coroutine.arg.Arguments;
import de.heinerkuecker.coroutine.exprs.complex.ComplexExpressionState;
import de.heinerkuecker.coroutine.stmt.CoroStmtResult;
import de.heinerkuecker.util.HCloneable;

class FunctionCallState<FUNCTION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT>
//extends ComplexStmtState<
extends ComplexExpressionState<
    FunctionCallState<FUNCTION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT>,
    FunctionCall<FUNCTION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT>,
    FUNCTION_RETURN ,
    COROUTINE_RETURN ,
    //FunctionCall<COROUTINE_RETURN>
    RESUME_ARGUMENT
    >
{
    private final FunctionCall<FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> functionCall;

    boolean runInFunction = true;

    // TODO getter
    ComplexStmtState<?, ?, FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> bodyComplexState;

    //private final CoroutineIterator<COROUTINE_RETURN> rootParent;
    private final CoroutineOrFunctioncallOrComplexstmt<FUNCTION_RETURN , COROUTINE_RETURN, RESUME_ARGUMENT> parent;

    //final Map<String, Object> functionArgumentValues;
    final Arguments arguments;

    /**
     * Constructor.
     *
     * @param functionCall
     */
    protected FunctionCallState(
            final boolean isInitializationCheck ,
            final FunctionCall<FUNCTION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT> functionCall ,
            final Class<? extends COROUTINE_RETURN> coroutineReturnType ,
            final Class<? extends RESUME_ARGUMENT> resumeArgumentType ,
            //final Map<String, Object> functionArgumentValues
            final Arguments arguments ,
            final CoroutineOrFunctioncallOrComplexstmt<FUNCTION_RETURN , COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        super( parent );
        this.functionCall =
                Objects.requireNonNull(
                        functionCall );

        //this.functionArgumentValues = Collections.unmodifiableMap( functionArgumentValues );
        this.arguments = arguments;

        //this.rootParent = Objects.requireNonNull( rootParent );

        this.parent =
                Objects.requireNonNull(
                        parent );

        final Function<? , COROUTINE_RETURN , RESUME_ARGUMENT> function =
                this.parent/*.getRootParent()*/.getFunction(
                        functionCall.functionName );

        if ( ! isInitializationCheck )
        {
            // for showing next stmt in toString
            //this.bodyComplexState = function.bodyComplexStmt.newState( this );
            this.bodyComplexState =
                    // if the function is used as statement the function return type is no matter
                    (ComplexStmtState<?, ?, FUNCTION_RETURN, COROUTINE_RETURN, RESUME_ARGUMENT>) function.bodyComplexStmt.newState(
                            //this.rootParent
                            (CoroutineOrFunctioncallOrComplexstmt/*<FUNCTION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT>*/) this );
        }

        if ( coroutineReturnType != null )
        {
            // TODO execute only once
            function.bodyComplexStmt.setStmtCoroutineReturnTypeAndResumeArgumentType(
                    // alreadyCheckedFunctionNames
                    new HashSet<>() ,
                    this ,
                    coroutineReturnType ,
                    resumeArgumentType );
        }
    }

    @Override
    public CoroStmtResult<FUNCTION_RETURN , COROUTINE_RETURN> execute(
            //final CoroutineOrFunctioncallOrComplexstmt<COROUTINE_RETURN, RESUME_ARGUMENT> parent
            )
    {
        if ( CoroutineDebugSwitches.logSimpleStatementsAndExpressions )
        {
            System.out.println( "execute " + this.functionCall );
        }

        if ( runInFunction )
        {
            final ComplexStmt<?, ?, ? , COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> bodyComplexStmt =
                    this.parent/*.getRootParent()*/.getFunction(
                            functionCall.functionName ).bodyComplexStmt;

            if ( this.bodyComplexState == null )
                // no existing state from previous execute call
            {
                this.bodyComplexState =
                        // if the function is used as statement the function return type is no matter
                        (ComplexStmtState<?, ?, FUNCTION_RETURN, COROUTINE_RETURN, RESUME_ARGUMENT>) bodyComplexStmt.newState(
                                //this.rootParent
                                (CoroutineOrFunctioncallOrComplexstmt/*<FUNCTION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT>*/) this );
            }

            // TODO only before executing simple stmt: parent.saveLastStmtState();

            final CoroStmtResult<FUNCTION_RETURN , COROUTINE_RETURN> bodyExecuteResult =
                    this.bodyComplexState.execute(
                            //parent
                            //this
                            );

            if ( this.bodyComplexState.isFinished() )
            {
                finish();
            }

            if ( ! ( bodyExecuteResult == null ||
                    bodyExecuteResult instanceof CoroStmtResult.ContinueCoroutine ) )
            {
                return bodyExecuteResult;
            }

            bodyComplexState = null;
            return bodyExecuteResult;
        }

        return CoroStmtResult.continueCoroutine();
    }

    private void finish()
    {
        this.runInFunction = false;
        bodyComplexState = null;
    }

    /**
     * @see ComplexStmtState#isFinished()
     */
    @Override
    public boolean isFinished()
    {
        return ! runInFunction;
    }

    /**
     * @see ComplexStmtState#getStmt()
     */
    @Override
    public FunctionCall<FUNCTION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT> getStmt()
    {
        return this.functionCall;
    }

    /**
     * @see HCloneable#createClone()
     */
    @Override
    public FunctionCallState<FUNCTION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT> createClone()
    {
        final FunctionCallState<FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> clone =
                new FunctionCallState<>(
                        // isInitializationCheck TODO wrong using of argument
                        true ,
                        this.functionCall ,
                        // coroutineReturnType null, because only for first call necessary
                        null ,
                        // resumeArgumentType null, because only for first call necessary
                        null ,
                        //this.functionArgumentValues
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
    //public void saveLastStmtState()
    //{
    //    this.parent.getRootParent().saveLastStmtState();
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
     * Overidden to access arguments of this function
     */
    @Override
    //public Map<String, Object> functionArgumentValues()
    public Arguments functionArgumentValues()
    {
        return
                //this.functionArgumentValues;
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
    //public Function<COROUTINE_RETURN> getFunction(
    //        final String functionName )
    //{
    //    return this.getRootParent().getFunction( functionName );
    //}

    /**
     * Overidden to access parameter of this function
     */
    @Override
    public Map<String, Class<?>> functionParameterTypes()
    {
        return this.arguments.functionParameterTypes();
    }

    //@Override
    //public Map<String, Class<?>> globalParameterTypes()
    //{
    //    //throw new RuntimeException( "not implemented" );
    //    return this.getRootParent().arguments.functionParameterTypes();
    //}

}
