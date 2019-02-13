package de.heinerkuecker.coroutine.stmt.complex;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.heinerkuecker.coroutine.CoroutineOrFunctioncallOrComplexstmt;
import de.heinerkuecker.coroutine.exprs.GetFunctionArgument;
import de.heinerkuecker.coroutine.exprs.SimpleExpression;
import de.heinerkuecker.coroutine.exprs.Value;
import de.heinerkuecker.coroutine.stmt.CoroStmt;
import de.heinerkuecker.coroutine.stmt.flow.BreakOrContinue;

public class If<FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT extends CoroutineIterator<COROUTINE_RETURN>*/ , RESUME_ARGUMENT>
extends ComplexStmt<
    If<FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT>,
    IfState<FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT>,
    FUNCTION_RETURN ,
    COROUTINE_RETURN ,
    //PARENT
    RESUME_ARGUMENT
    >
{
    final SimpleExpression<Boolean , COROUTINE_RETURN> condition;
    final ComplexStmt<?, ?, FUNCTION_RETURN , COROUTINE_RETURN /*, PARENT/*CoroutineIterator<COROUTINE_RETURN>*/ , RESUME_ARGUMENT> thenBodyComplexStmt;

    /**
     * Constructor.
     */
    @SafeVarargs
    public If(
            final SimpleExpression<Boolean , COROUTINE_RETURN> condition ,
            final CoroStmt<FUNCTION_RETURN , COROUTINE_RETURN/*, ? super PARENT/*CoroutineIterator<COROUTINE_RETURN>*/> ... stmts )
    {
        super(
                //creationStackOffset
                3 );

        this.condition = condition;

        this.thenBodyComplexStmt =
                Block.convertStmtsToComplexStmt(
                        // creationStackOffset
                        4 ,
                        stmts );
    }

    ///**
    // * Constructor.
    // */
    //@SafeVarargs
    //public If(
    //        final SimpleExpression<Boolean> condition ,
    //        final CoroIterStmt<COROUTINE_RETURN/*, ? super PARENT/*CoroutineIterator<COROUTINE_RETURN>*/> ... stmts )
    //{
    //    super(
    //            //creationStackOffset
    //            3 );
    //
    //    this.condition =
    //            new IsTrue(
    //                    condition );
    //
    //    this.thenBodyComplexStmt =
    //            Block.convertStmtsToComplexStmt(
    //                    // creationStackOffset
    //                    4 ,
    //                    stmts );
    //}

    /**
     * Constructor.
     */
    @SafeVarargs
    public If(
            final boolean condition ,
            final CoroStmt<FUNCTION_RETURN , COROUTINE_RETURN/*, ? super PARENT/*CoroutineIterator<COROUTINE_RETURN>*/> ... stmts )
    {
        super(
                //creationStackOffset
                3 );

        this.condition =
                //new IsTrue(
                        Value.booleanValue(
                                condition );

        this.thenBodyComplexStmt =
                Block.convertStmtsToComplexStmt(
                        // creationStackOffset
                        4 ,
                        stmts );
    }

    @Override
    public IfState<FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> newState(
            final CoroutineOrFunctioncallOrComplexstmt<FUNCTION_RETURN , COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        return new IfState<>(
                this ,
                parent );
    }

    @Override
    public List<BreakOrContinue<? , ?, ?>> getUnresolvedBreaksOrContinues(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<? , COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        return thenBodyComplexStmt.getUnresolvedBreaksOrContinues(
                alreadyCheckedFunctionNames ,
                parent );
    }

    /**
     * @see CoroStmt#getFunctionArgumentGetsNotInFunction()
     */
    @Override
    public List<GetFunctionArgument<? , ?>> getFunctionArgumentGetsNotInFunction()
    {
        final List<GetFunctionArgument<? , ?>> result = new ArrayList<>();

        result.addAll(
                condition.getFunctionArgumentGetsNotInFunction() );

        result.addAll(
                thenBodyComplexStmt.getFunctionArgumentGetsNotInFunction() );

        return result;
    }

    @Override
    public void setStmtCoroutineReturnType(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ? , ?> parent ,
            final Class<? extends COROUTINE_RETURN> coroutineReturnType )
    {
        this.thenBodyComplexStmt.setStmtCoroutineReturnType(
                alreadyCheckedFunctionNames ,
                parent ,
                coroutineReturnType );
    }

    @Override
    public void checkLabelAlreadyInUse(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<? , COROUTINE_RETURN, RESUME_ARGUMENT> parent ,
            final Set<String> labels )
    {
        this.thenBodyComplexStmt.checkLabelAlreadyInUse(
                alreadyCheckedFunctionNames ,
                parent ,
                labels );
    }

    @Override
    public void checkUseVariables(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent ,
            final Map<String, Class<?>> globalVariableTypes ,
            final Map<String, Class<?>> localVariableTypes )
    {
        this.condition.checkUseVariables(
                alreadyCheckedFunctionNames ,
                parent ,
                globalVariableTypes, localVariableTypes );

        this.thenBodyComplexStmt.checkUseVariables(
                alreadyCheckedFunctionNames ,
                parent ,
                globalVariableTypes, localVariableTypes );
    }

    @Override
    public void checkUseArguments(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent )
    {
        this.condition.checkUseArguments( alreadyCheckedFunctionNames, parent );
        this.thenBodyComplexStmt.checkUseArguments( alreadyCheckedFunctionNames, parent );
    }

    /**
     * @see ComplexStmt#toString
     */
    @Override
    public String toString(
            final CoroutineOrFunctioncallOrComplexstmt</*FUNCTION_RETURN*/? , COROUTINE_RETURN, RESUME_ARGUMENT> parent ,
            final String indent ,
            final ComplexStmtState<?, /*STMT*/?, /*FUNCTION_RETURN*/? , COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> lastStmtExecuteState ,
            final ComplexStmtState<?, /*STMT*/?, /*FUNCTION_RETURN*/? , COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> nextStmtExecuteState )
    {
        @SuppressWarnings("unchecked")
        final IfState<FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> lastIfExecuteState =
                (IfState<FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT>) lastStmtExecuteState;

        @SuppressWarnings("unchecked")
        final IfState<FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> nextIfExecuteState =
                (IfState<FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT>) nextStmtExecuteState;

        final ComplexStmtState<?, ?, FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> lastThenBodyState;
        if ( lastIfExecuteState != null )
        {
            lastThenBodyState = lastIfExecuteState.thenBodyComplexState;
        }
        else
        {
            lastThenBodyState = null;
        }

        final ComplexStmtState<?, ?, FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> nextThenBodyState;
        if ( nextIfExecuteState != null )
        {
            nextThenBodyState = nextIfExecuteState.thenBodyComplexState;
        }
        else
        {
            nextThenBodyState = null;
        }

        final String conditionStr;
        if ( lastIfExecuteState != null &&
                lastIfExecuteState.runInCondition )
        {
            conditionStr =
                    "last:" +
                    indentStrWithoutNextOrLastPart( indent ) +
                    "   " +
                    this.condition;
        }
        else if ( nextIfExecuteState != null &&
                nextIfExecuteState.runInCondition )
        {
            conditionStr =
                    "next:" +
                    indentStrWithoutNextOrLastPart( indent ) +
                    "   " +
                    this.condition;
        }
        else
        {
            conditionStr =
                    indent +
                    "   " +
                    this.condition;
        }

        final String variablesStr;
        if ( nextThenBodyState == null )
        {
            variablesStr = "";
        }
        else
        {
            variablesStr =
                    nextThenBodyState.getVariablesStr(
                            indent );
        }

        return
                indent +
                this.getClass().getSimpleName() + " (" +
                ( this.creationStackTraceElement != null ? " " + this.creationStackTraceElement : "" ) +
                "\n" +
                conditionStr + " )\n" +
                variablesStr +
                this.thenBodyComplexStmt.toString(
                        parent ,
                        indent + " " ,
                        lastThenBodyState ,
                        nextThenBodyState );
    }

}
