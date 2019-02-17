package de.heinerkuecker.coroutine.stmt.complex;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import de.heinerkuecker.coroutine.CoroutineOrFunctioncallOrComplexstmt;
import de.heinerkuecker.coroutine.exprs.GetFunctionArgument;
import de.heinerkuecker.coroutine.exprs.SimpleExpression;
import de.heinerkuecker.coroutine.exprs.complex.ComplexExpression;
import de.heinerkuecker.coroutine.stmt.CoroStmt;
import de.heinerkuecker.coroutine.stmt.flow.Break;
import de.heinerkuecker.coroutine.stmt.flow.BreakOrContinue;
import de.heinerkuecker.coroutine.stmt.flow.Continue;
import de.heinerkuecker.coroutine.stmt.flow.exc.LabelAlreadyInUseException;

abstract class WhileOrDoWhile<
    WHILE_OR_DO_WHILE extends WhileOrDoWhile<WHILE_OR_DO_WHILE, WHILE_OR_DO_WHILE_STATE, FUNCTION_RETURN , COROUTINE_RETURN /*, PARENT*/, RESUME_ARGUMENT> ,
    WHILE_OR_DO_WHILE_STATE extends WhileOrDoWhileState<WHILE_OR_DO_WHILE, WHILE_OR_DO_WHILE_STATE, FUNCTION_RETURN , COROUTINE_RETURN /*, PARENT*/, RESUME_ARGUMENT> ,
    FUNCTION_RETURN ,
    COROUTINE_RETURN ,
    //PARENT extends CoroutineIterator<COROUTINE_RETURN>
    RESUME_ARGUMENT
    >
extends ComplexStmt<
    /*WhileOrDoWhile<COROUTINE_RETURN, PARENT>*/WHILE_OR_DO_WHILE ,
    /*WhileOrDoWhileState<WhileOrDoWhile<COROUTINE_RETURN, PARENT>, COROUTINE_RETURN, PARENT>*/WHILE_OR_DO_WHILE_STATE ,
    FUNCTION_RETURN ,
    COROUTINE_RETURN ,
    //PARENT
    RESUME_ARGUMENT
    >
{
    /**
     * Label to use with {@link Break} or {@link Continue}, optional.
     */
    public final String label;

    //final SimpleExpression<Boolean , COROUTINE_RETURN> condition;
    final ComplexExpression<
    /*ComplexExpression<COROUTINE_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT>*/ ? ,
    /*ComplexExpressionState<COROUTINE_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT>*/ ? ,
    Boolean ,
    COROUTINE_RETURN ,
    RESUME_ARGUMENT
    > condition;

    final ComplexStmt<?, ?, FUNCTION_RETURN , COROUTINE_RETURN /*, PARENT*/, RESUME_ARGUMENT> bodyComplexStmt;

    /**
     * Constructor.
     */
    @SafeVarargs
    protected WhileOrDoWhile(
            final String label ,
            final ComplexExpression<? , ? , Boolean , COROUTINE_RETURN , RESUME_ARGUMENT> condition ,
            final CoroStmt<FUNCTION_RETURN , ? extends COROUTINE_RETURN /*, PARENT/*CoroutineIterator<COROUTINE_RETURN>*/ , RESUME_ARGUMENT> ... stmts )
    {
        super(
                //creationStackOffset
                4 );

        this.label = label;

        this.condition = Objects.requireNonNull( condition );

        this.bodyComplexStmt =
                Block.convertStmtsToComplexStmt(
                        // creationStackOffset
                        5 ,
                        stmts );
    }

    /**
     * Constructor.
     */
    @SafeVarargs
    protected WhileOrDoWhile(
            final String label ,
            final SimpleExpression<Boolean , COROUTINE_RETURN , RESUME_ARGUMENT> condition ,
            final CoroStmt<FUNCTION_RETURN , ? extends COROUTINE_RETURN /*, PARENT/*CoroutineIterator<COROUTINE_RETURN>*/ , RESUME_ARGUMENT> ... stmts )
    {
        super(
                //creationStackOffset
                4 );

        this.label = label;

        this.condition =
                Objects.requireNonNull(
                        new SimpleExpressionWrapper<>(
                                condition ) );

        this.bodyComplexStmt =
                Block.convertStmtsToComplexStmt(
                        // creationStackOffset
                        5 ,
                        stmts );
    }

    @Override
    final public List<BreakOrContinue<?, ?, ?>> getUnresolvedBreaksOrContinues(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<? , COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        final List<BreakOrContinue<? , ? , ?>> result = new ArrayList<>();

        for ( BreakOrContinue<? , ? , ?> unresolvedBreakOrContinue : bodyComplexStmt.getUnresolvedBreaksOrContinues(
                alreadyCheckedFunctionNames ,
                parent ) )
        {
            final String label = unresolvedBreakOrContinue.getLabel();

            if ( label != null &&
                    ! label.equals( this.label ) )
                // no label of this loop
            {
                result.add( unresolvedBreakOrContinue );
            }
        }

        return result;
    }

    /**
     * @see CoroStmt#getFunctionArgumentGetsNotInFunction()
     */
    @Override
    public List<GetFunctionArgument<? , ? , ?>> getFunctionArgumentGetsNotInFunction()
    {
        final List<GetFunctionArgument<? , ? , ?>> result = new ArrayList<>();

        result.addAll(
                condition.getFunctionArgumentGetsNotInFunction() );

        result.addAll(
                bodyComplexStmt.getFunctionArgumentGetsNotInFunction() );

        return result;
    }

    @Override
    public void setStmtCoroutineReturnTypeAndResumeArgumentType(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ? , ?> parent ,
            //final Class<? /*extends COROUTINE_RETURN*/> coroutineReturnType ,
            final Class<? extends COROUTINE_RETURN> coroutineReturnType ,
            final Class<? extends RESUME_ARGUMENT> resumeArgumentType )
    {
        this.condition.setStmtCoroutineReturnTypeAndResumeArgumentType(
                alreadyCheckedFunctionNames ,
                parent ,
                coroutineReturnType ,
                resumeArgumentType );

        this.bodyComplexStmt.setStmtCoroutineReturnTypeAndResumeArgumentType(
                alreadyCheckedFunctionNames ,
                parent ,
                coroutineReturnType ,
                resumeArgumentType );
    }

    @Override
    public void checkLabelAlreadyInUse(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<? , COROUTINE_RETURN, RESUME_ARGUMENT> parent ,
            final Set<String> labels )
    {
        if ( label != null )
        {
            if ( labels.contains( label ) )
            {
                throw new LabelAlreadyInUseException(
                        label );
            }
            labels.add( label );
        }

        this.bodyComplexStmt.checkLabelAlreadyInUse(
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

        this.bodyComplexStmt.checkUseVariables(
                alreadyCheckedFunctionNames ,
                parent ,
                globalVariableTypes, localVariableTypes );
    }

    @Override
    public void checkUseArguments(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent )
    {
        this.condition.checkUseArguments(
                alreadyCheckedFunctionNames ,
                parent );

        this.bodyComplexStmt.checkUseArguments(
                alreadyCheckedFunctionNames ,
                parent );
    }

    /**
     * @see ComplexStmt#toString
     */
    @Override
    final public String toString(
            final CoroutineOrFunctioncallOrComplexstmt</*FUNCTION_RETURN*/? , COROUTINE_RETURN, RESUME_ARGUMENT> parent ,
            final String indent ,
            final ComplexStmtState<?, ?, /*FUNCTION_RETURN*/? , COROUTINE_RETURN /*, PARENT*/ , RESUME_ARGUMENT> lastStmtExecuteState ,
            final ComplexStmtState<?, ?, /*FUNCTION_RETURN*/? , COROUTINE_RETURN /*, PARENT*/ , RESUME_ARGUMENT> nextStmtExecuteState )
    {
        final WhileOrDoWhileState<?, ?, FUNCTION_RETURN , COROUTINE_RETURN /*, PARENT*/ , RESUME_ARGUMENT> lastWhileExecuteState =
                (WhileOrDoWhileState<?, ?, FUNCTION_RETURN , COROUTINE_RETURN /*, PARENT*/ , RESUME_ARGUMENT>) lastStmtExecuteState;

        final WhileOrDoWhileState<?, ?, FUNCTION_RETURN , COROUTINE_RETURN /*, PARENT*/ , RESUME_ARGUMENT> nextWhileExecuteState =
                (WhileOrDoWhileState<?, ?, FUNCTION_RETURN , COROUTINE_RETURN /*, PARENT*/ , RESUME_ARGUMENT>) nextStmtExecuteState;

        final ComplexStmtState<?, ?, FUNCTION_RETURN , COROUTINE_RETURN /*, PARENT*/ , RESUME_ARGUMENT> lastBodyState;
        if ( lastWhileExecuteState != null )
        {
            lastBodyState = lastWhileExecuteState.bodyComplexState;
        }
        else
        {
            lastBodyState = null;
        }

        final ComplexStmtState<?, ?, FUNCTION_RETURN , COROUTINE_RETURN /*, PARENT*/ , RESUME_ARGUMENT> nextBodyState;
        if ( nextWhileExecuteState != null )
        {
            nextBodyState = nextWhileExecuteState.bodyComplexState;
        }
        else
        {
            nextBodyState = null;
        }

        final String conditionStr;
        if ( lastWhileExecuteState != null &&
                lastWhileExecuteState.runInCondition )
        {
            conditionStr =
                    "last:" +
                    indentStrWithoutNextOrLastPart( indent ) +
                    "   " +
                    this.condition;
        }
        else if ( nextWhileExecuteState != null &&
                nextWhileExecuteState.runInCondition )
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
        if ( nextWhileExecuteState == null )
        {
            variablesStr = "";
        }
        else
        {
            variablesStr =
                    nextWhileExecuteState.getVariablesStr(
                            indent );
        }

        return
                indent +
                ( this.label != null ? this.label + " : " : "" ) +
                this.getClass().getSimpleName() + " (" +
                ( this.creationStackTraceElement != null ? " " + this.creationStackTraceElement : "" ) +
                "\n" +
                conditionStr + " )\n" +
                variablesStr +
                this.bodyComplexStmt.toString(
                        parent ,
                        indent + " " ,
                        lastBodyState ,
                        nextBodyState );
    }

}
