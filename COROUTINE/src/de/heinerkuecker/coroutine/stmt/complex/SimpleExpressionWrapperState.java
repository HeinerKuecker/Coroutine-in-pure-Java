package de.heinerkuecker.coroutine.stmt.complex;

import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineOrFunctioncallOrComplexstmt;
import de.heinerkuecker.coroutine.exprs.complex.ComplexExpressionState;
import de.heinerkuecker.coroutine.stmt.CoroStmtResult;
import de.heinerkuecker.util.HCloneable;

class SimpleExpressionWrapperState<FUNCTION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT>
//extends ComplexStmtState<
extends ComplexExpressionState<
    SimpleExpressionWrapperState<FUNCTION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT>,
    SimpleExpressionWrapper<FUNCTION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT>,
    FUNCTION_RETURN ,
    COROUTINE_RETURN ,
    //FunctionCall<COROUTINE_RETURN>
    RESUME_ARGUMENT
    >
{
    private final SimpleExpressionWrapper<FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> simpleExpressionWrapper;

    boolean runInSimpleExpression = true;

    private final CoroutineOrFunctioncallOrComplexstmt<FUNCTION_RETURN , COROUTINE_RETURN, RESUME_ARGUMENT> parent;

    /**
     * Constructor.
     */
    protected SimpleExpressionWrapperState(
            final boolean isInitializationCheck ,
            final SimpleExpressionWrapper<FUNCTION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT> simpleExpressionWrapper ,
            //final Class<? extends COROUTINE_RETURN> coroutineReturnType ,
            final CoroutineOrFunctioncallOrComplexstmt<FUNCTION_RETURN , COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        super( parent );
        this.simpleExpressionWrapper =
                Objects.requireNonNull(
                        simpleExpressionWrapper );

        this.parent =
                Objects.requireNonNull(
                        parent );

        if ( ! isInitializationCheck )
        {
            // for showing next stmt in toString
            //this.bodyComplexState =
            //        // if the function is used as statement the function return type is no matter
            //        (ComplexStmtState<?, ?, FUNCTION_RETURN, COROUTINE_RETURN, RESUME_ARGUMENT>) function.bodyComplexStmt.newState(
            //                //this.rootParent
            //                (CoroutineOrFunctioncallOrComplexstmt/*<FUNCTION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT>*/) this );
        }

        //if ( coroutineReturnType != null )
        //{
        //    // TODO execute only once
        //    //function.bodyComplexStmt.setStmtCoroutineReturnType(
        //    //        // alreadyCheckedFunctionNames
        //    //        new HashSet<>() ,
        //    //        this ,
        //    //        coroutineReturnType );
        //}
    }

    @Override
    public CoroStmtResult<FUNCTION_RETURN , COROUTINE_RETURN> execute(
            //final CoroutineOrFunctioncallOrComplexstmt<COROUTINE_RETURN, RESUME_ARGUMENT> parent
            )
    {
        if ( runInSimpleExpression )
        {
            // TODO only before executing simple stmt:
            parent.saveLastStmtState();

            FUNCTION_RETURN expressionResult =
                    this.simpleExpressionWrapper.simpleExpression.evaluate(
                            this);

            //bodyComplexState = null;
            finish();

            return new CoroStmtResult.FunctionReturnWithResult<FUNCTION_RETURN, COROUTINE_RETURN>(
                    expressionResult );
        }

        return CoroStmtResult.continueCoroutine();
    }

    private void finish()
    {
        this.runInSimpleExpression = false;
        //bodyComplexState = null;
    }

    /**
     * @see ComplexStmtState#isFinished()
     */
    @Override
    public boolean isFinished()
    {
        return ! runInSimpleExpression;
    }

    /**
     * @see ComplexStmtState#getStmt()
     */
    @Override
    public SimpleExpressionWrapper<FUNCTION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT> getStmt()
    {
        return this.simpleExpressionWrapper;
    }

    /**
     * @see HCloneable#createClone()
     */
    @Override
    public SimpleExpressionWrapperState<FUNCTION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT> createClone()
    {
        final SimpleExpressionWrapperState<FUNCTION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT> clone =
                new SimpleExpressionWrapperState<FUNCTION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT>(
                        // isInitializationCheck TODO wrong using of argument
                        true ,
                        this.simpleExpressionWrapper ,
                        // coroutineReturnType null, because only for first call necessary
                        //null ,
                        this.parent );

        clone.runInSimpleExpression = this.runInSimpleExpression;

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

    ///**
    // * Overidden to access arguments of this function
    // */
    //@Override
    ////public Map<String, Object> functionArgumentValues()
    //public Arguments functionArgumentValues()
    //{
    //    return this.parent.functionArgumentValues();
    //}

    ///**
    // * Overidden to access parameter of this function
    // */
    //@Override
    //public Map<String, Class<?>> functionParameterTypes()
    //{
    //    return this.parent.functionParameterTypes();
    //}

    //@Override
    //public Map<String, Class<?>> globalParameterTypes()
    //{
    //    //throw new RuntimeException( "not implemented" );
    //    return this.getRootParent().arguments.functionParameterTypes();
    //}

}
