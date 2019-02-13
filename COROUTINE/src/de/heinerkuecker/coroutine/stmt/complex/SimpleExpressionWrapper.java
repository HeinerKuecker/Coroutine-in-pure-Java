package de.heinerkuecker.coroutine.stmt.complex;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import de.heinerkuecker.coroutine.CoroutineOrFunctioncallOrComplexstmt;
import de.heinerkuecker.coroutine.exprs.CoroExpression;
import de.heinerkuecker.coroutine.exprs.GetFunctionArgument;
import de.heinerkuecker.coroutine.exprs.SimpleExpression;
import de.heinerkuecker.coroutine.exprs.complex.ComplexExpression;
import de.heinerkuecker.coroutine.stmt.flow.BreakOrContinue;

public class SimpleExpressionWrapper<
    EXPRESSION_RETURN ,
    COROUTINE_RETURN/*, PARENT extends CoroutineOrFunctioncallOrComplexstmt<COROUTINE_RETURN, PARENT>*/ ,
    RESUME_ARGUMENT
    >
extends ComplexExpression<
    SimpleExpressionWrapper<EXPRESSION_RETURN , COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> ,
    SimpleExpressionWrapperState<EXPRESSION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT> ,
    EXPRESSION_RETURN ,
    COROUTINE_RETURN ,
    //PARENT
    RESUME_ARGUMENT
    >
//implements CoroutineOrFunctioncallOrComplexstmt<COROUTINE_RETURN/*, CoroutineIterator<COROUTINE_RETURN>*/>
//implements SimpleExpression<FUNCTION_RETURN , COROUTINE_RETURN>
implements CoroExpression<EXPRESSION_RETURN , COROUTINE_RETURN>
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

    final SimpleExpression<EXPRESSION_RETURN, COROUTINE_RETURN> simpleExpression;

    /**
     * Reifier for type param {@link #COROUTINE_RETURN} to solve unchecked casts.
     */
    private Class<? extends COROUTINE_RETURN> coroutineReturnType;

    /**
     * Reifier for type param {@link #FUNCTION_RETURN} to solve unchecked casts.
     */
    //private final Class<? extends EXPRESSION_RETURN> functionReturnType;

    /**
     * Constructor.
     */
    public SimpleExpressionWrapper(
            final SimpleExpression<EXPRESSION_RETURN, COROUTINE_RETURN> simpleExpression
            //final Class<? extends EXPRESSION_RETURN> functionReturnType
            )
    {
        super(
                //creationStackOffset
                3 );

        this.simpleExpression =
                Objects.requireNonNull(
                        simpleExpression );

        //this.functionReturnType = Objects.requireNonNull( functionReturnType );
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
    public SimpleExpressionWrapperState<EXPRESSION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT> newState(
            final CoroutineOrFunctioncallOrComplexstmt<EXPRESSION_RETURN , COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        final SimpleExpressionWrapperState<EXPRESSION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT> simpleExpressionWrapperState =
                new SimpleExpressionWrapperState<EXPRESSION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT>(
                        // isInitializationCheck
                        false ,
                        this ,
                        //coroutineReturnType ,
                        parent );

        return simpleExpressionWrapperState;
    }

    public SimpleExpressionWrapperState<EXPRESSION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT> newStateForCheck(
            final CoroutineOrFunctioncallOrComplexstmt<EXPRESSION_RETURN , COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        final SimpleExpressionWrapperState<EXPRESSION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT> simpleExpressionWrapperState =
                new SimpleExpressionWrapperState<EXPRESSION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT>(
                        // isInitializationCheck
                        true ,
                        this ,
                        //coroutineReturnType ,
                        parent );

        return simpleExpressionWrapperState;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<? extends EXPRESSION_RETURN>[] type()
    {
        //return new Class[] { this.si };
        return this.simpleExpression.type();
    }

    @Override
    public List<BreakOrContinue<? , ? , ?>> getUnresolvedBreaksOrContinues(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<? , COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        return Collections.emptyList();
    }

    @Override
    public void checkLabelAlreadyInUse(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<? , COROUTINE_RETURN, RESUME_ARGUMENT> parent ,
            final Set<String> labels )
    {
        // do nothing
    }

    @Override
    public List<GetFunctionArgument<? , ?>> getFunctionArgumentGetsNotInFunction()
    {
        return this.simpleExpression.getFunctionArgumentGetsNotInFunction();
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

        this.simpleExpression.setExprCoroutineReturnType(
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
        this.simpleExpression.checkUseVariables(
                alreadyCheckedFunctionNames ,
                parent ,
                globalVariableTypes ,
                localVariableTypes );
    }

    @Override
    public void checkUseArguments(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ? , ?> parent )
    {
        this.simpleExpression.checkUseArguments(
                alreadyCheckedFunctionNames ,
                //parent
                this.newStateForCheck(
                        (CoroutineOrFunctioncallOrComplexstmt<EXPRESSION_RETURN , COROUTINE_RETURN, RESUME_ARGUMENT>) parent ) );
    }

    @Override
    public String toString(
            final CoroutineOrFunctioncallOrComplexstmt</*FUNCTION_RETURN*/? , COROUTINE_RETURN, RESUME_ARGUMENT> parent ,
            final String indent ,
            final ComplexStmtState<?, ?, /*FUNCTION_RETURN*/? , COROUTINE_RETURN , RESUME_ARGUMENT> lastStmtExecuteState ,
            final ComplexStmtState<?, ?, /*FUNCTION_RETURN*/? , COROUTINE_RETURN , RESUME_ARGUMENT> nextStmtExecuteState )
    {
        @SuppressWarnings("unchecked")
        final FunctionCallState<EXPRESSION_RETURN , COROUTINE_RETURN /*, PARENT*/ , RESUME_ARGUMENT> lastProcExecuteState =
                (FunctionCallState<EXPRESSION_RETURN , COROUTINE_RETURN /*, PARENT*/ , RESUME_ARGUMENT>) lastStmtExecuteState;

        @SuppressWarnings("unchecked")
        final FunctionCallState<EXPRESSION_RETURN , COROUTINE_RETURN /*, PARENT*/ , RESUME_ARGUMENT> nextProcExecuteState =
                (FunctionCallState<EXPRESSION_RETURN , COROUTINE_RETURN /*, PARENT*/ , RESUME_ARGUMENT>) nextStmtExecuteState;

        final ComplexStmtState<?, ?, EXPRESSION_RETURN , COROUTINE_RETURN /*, PARENT*/ , RESUME_ARGUMENT> lastBodyState;
        if ( lastProcExecuteState != null )
        {
            lastBodyState = lastProcExecuteState.bodyComplexState;
        }
        else
        {
            lastBodyState = null;
        }

        final ComplexStmtState<?, ?, EXPRESSION_RETURN , COROUTINE_RETURN /*, PARENT*/ , RESUME_ARGUMENT> nextBodyState;
        if ( nextProcExecuteState != null )
        {
            nextBodyState = nextProcExecuteState.bodyComplexState;
        }
        else
        {
            nextBodyState = null;
        }

        return
                indent +
                // TODO next and last
                this.simpleExpression;
    }

}
