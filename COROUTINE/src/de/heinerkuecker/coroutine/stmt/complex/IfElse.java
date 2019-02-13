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

public class IfElse<
    FUNCTION_RETURN ,
    COROUTINE_RETURN/*, PARENT extends CoroutineIterator<COROUTINE_RETURN>*/ ,
    RESUME_ARGUMENT
    >
extends ComplexStmt<
    IfElse<FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> ,
    IfElseState<FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> ,
    FUNCTION_RETURN ,
    COROUTINE_RETURN ,
    //PARENT
    RESUME_ARGUMENT
    >
{
    final SimpleExpression<Boolean , COROUTINE_RETURN> condition;

    final ComplexStmt<?, ?, FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT/*CoroutineIterator<COROUTINE_RETURN>*/ , RESUME_ARGUMENT> thenBodyComplexStmt;
    final ComplexStmt<?, ?, FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT/*CoroutineIterator<COROUTINE_RETURN>*/ , RESUME_ARGUMENT> elseBodyComplexStmt;

    /**
     * Constructor.
     */
    public IfElse(
            final SimpleExpression<Boolean , COROUTINE_RETURN> condition ,
            final CoroStmt<FUNCTION_RETURN , COROUTINE_RETURN/*, CoroutineIterator<COROUTINE_RETURN>*/>[] thenStmts ,
            final CoroStmt<FUNCTION_RETURN , COROUTINE_RETURN/*, CoroutineIterator<COROUTINE_RETURN>*/>[] elseStmts )
    {
        super(
                //creationStackOffset
                3 );

        this.condition = condition;

        if ( thenStmts.length == 1 &&
                thenStmts[ 0 ] instanceof ComplexStmt )
        {
            this.thenBodyComplexStmt =
                    (ComplexStmt<?, ?, FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT/*? super CoroutineIterator<COROUTINE_RETURN>*/, RESUME_ARGUMENT>) thenStmts[ 0 ];
        }
        else
        {
            this.thenBodyComplexStmt =
                    new Block<>(
                            // creationStackOffset
                            3 ,
                            thenStmts );
        }

        if ( elseStmts.length == 1 &&
                elseStmts[ 0 ] instanceof ComplexStmt )
        {
            this.elseBodyComplexStmt =
                    (ComplexStmt<?, ?, FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT/*? super CoroutineIterator<COROUTINE_RETURN>*/ , RESUME_ARGUMENT>) elseStmts[ 0 ];
        }
        else
        {
            this.elseBodyComplexStmt =
                    new Block<>(
                            // creationStackOffset
                            3 ,
                            elseStmts );
        }

    }

    ///**
    // * Constructor.
    // */
    //public IfElse(
    //        final SimpleExpression<Boolean> condition ,
    //        final CoroIterStmt<COROUTINE_RETURN/*, CoroutineIterator<COROUTINE_RETURN>*/>[] thenStmts ,
    //        final CoroIterStmt<COROUTINE_RETURN/*, CoroutineIterator<COROUTINE_RETURN>*/>[] elseStmts )
    //{
    //    super(
    //            //creationStackOffset
    //            3 );
    //
    //    this.condition =
    //            new IsTrue(
    //                    condition );
    //
    //    if ( thenStmts.length == 1 &&
    //            thenStmts[ 0 ] instanceof ComplexStmt )
    //    {
    //        this.thenBodyComplexStmt =
    //                (ComplexStmt<?, ?, COROUTINE_RETURN/*, PARENT/*? super CoroutineIterator<COROUTINE_RETURN>*/ , RESUME_ARGUMENT>) thenStmts[ 0 ];
    //    }
    //    else
    //    {
    //        this.thenBodyComplexStmt =
    //                new Block<>(
    //                        // creationStackOffset
    //                        3 ,
    //                        thenStmts );
    //    }
    //
    //    if ( elseStmts.length == 1 &&
    //            elseStmts[ 0 ] instanceof ComplexStmt )
    //    {
    //        this.elseBodyComplexStmt =
    //                (ComplexStmt<?, ?, COROUTINE_RETURN/*, PARENT/*? super CoroutineIterator<COROUTINE_RETURN>*/ , RESUME_ARGUMENT>) elseStmts[ 0 ];
    //    }
    //    else
    //    {
    //        this.elseBodyComplexStmt =
    //                new Block<>(
    //                        // creationStackOffset
    //                        3 ,
    //                        elseStmts );
    //    }
    //
    //}

    /**
     * Constructor.
     */
    public IfElse(
            final boolean condition ,
            final CoroStmt<FUNCTION_RETURN , COROUTINE_RETURN/*, CoroutineIterator<COROUTINE_RETURN>*/>[] thenStmts ,
            final CoroStmt<FUNCTION_RETURN , COROUTINE_RETURN/*, CoroutineIterator<COROUTINE_RETURN>*/>[] elseStmts )
    {
        super(
                //creationStackOffset
                3 );

        this.condition =
                //new IsTrue(
                        Value.booleanValue(
                                condition );

        if ( thenStmts.length == 1 &&
                thenStmts[ 0 ] instanceof ComplexStmt )
        {
            this.thenBodyComplexStmt =
                    (ComplexStmt<?, ?, FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT/*? super CoroutineIterator<COROUTINE_RETURN>*/ , RESUME_ARGUMENT>) thenStmts[ 0 ];
        }
        else
        {
            this.thenBodyComplexStmt =
                    new Block<>(
                            // creationStackOffset
                            3 ,
                            thenStmts );
        }

        if ( elseStmts.length == 1 &&
                elseStmts[ 0 ] instanceof ComplexStmt )
        {
            this.elseBodyComplexStmt =
                    (ComplexStmt<?, ?, FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT/*? super CoroutineIterator<COROUTINE_RETURN>*/ , RESUME_ARGUMENT>) elseStmts[ 0 ];
        }
        else
        {
            this.elseBodyComplexStmt =
                    new Block<>(
                            // creationStackOffset
                            3 ,
                            elseStmts );
        }

    }

    /**
     * @see ComplexStmt#newState()
     */
    @Override
    public IfElseState<FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> newState(
            final CoroutineOrFunctioncallOrComplexstmt<FUNCTION_RETURN , COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        return new IfElseState<>(
                this ,
                parent );
    }

    @Override
    public List<BreakOrContinue<? , ? , ?>> getUnresolvedBreaksOrContinues(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<? , COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        final List<BreakOrContinue<? , ? , ?>> result = new ArrayList<>();

        result.addAll(
                thenBodyComplexStmt.getUnresolvedBreaksOrContinues(
                        alreadyCheckedFunctionNames ,
                        parent ) );

        result.addAll(
                elseBodyComplexStmt.getUnresolvedBreaksOrContinues(
                        alreadyCheckedFunctionNames ,
                        parent ) );

        return result;
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

        result.addAll(
                elseBodyComplexStmt.getFunctionArgumentGetsNotInFunction() );

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

        this.elseBodyComplexStmt.setStmtCoroutineReturnType(
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

        this.elseBodyComplexStmt.checkLabelAlreadyInUse(
                alreadyCheckedFunctionNames ,
                parent ,
                labels );
    }

    @Override
    public void checkUseVariables(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ? , ?> parent ,
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

        this.elseBodyComplexStmt.checkUseVariables(
                alreadyCheckedFunctionNames ,
                parent ,
                globalVariableTypes, localVariableTypes );
    }

    @Override
    public void checkUseArguments(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ? , ?> parent )
    {
        this.condition.checkUseArguments( alreadyCheckedFunctionNames , parent );
        this.thenBodyComplexStmt.checkUseArguments( alreadyCheckedFunctionNames , parent );
        this.elseBodyComplexStmt.checkUseArguments( alreadyCheckedFunctionNames , parent );
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
        final IfElseState<FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> lastIfElseExecuteState =
                (IfElseState<FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT>) lastStmtExecuteState;

        @SuppressWarnings("unchecked")
        final IfElseState<FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> nextIfElseExecuteState =
                (IfElseState<FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT>) nextStmtExecuteState;

        final ComplexStmtState<?, ?, FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> lastThenBodyState;
        if ( lastIfElseExecuteState != null )
        {
            lastThenBodyState = lastIfElseExecuteState.thenBodyComplexState;
        }
        else
        {
            lastThenBodyState = null;
        }

        final ComplexStmtState<?, ?, FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> nextThenBodyState;
        if ( nextIfElseExecuteState != null )
        {
            nextThenBodyState = nextIfElseExecuteState.thenBodyComplexState;
        }
        else
        {
            nextThenBodyState = null;
        }

        final ComplexStmtState<?, ?, FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> lastElseBodyState;
        if ( lastIfElseExecuteState != null )
        {
            lastElseBodyState = lastIfElseExecuteState.elseBodyComplexState;
        }
        else
        {
            lastElseBodyState = null;
        }

        final ComplexStmtState<?, ?, FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> nextElseBodyState;
        if ( nextIfElseExecuteState != null )
        {
            nextElseBodyState = nextIfElseExecuteState.elseBodyComplexState;
        }
        else
        {
            nextElseBodyState = null;
        }

        final String conditionStr;
        if ( lastIfElseExecuteState != null &&
                lastIfElseExecuteState.runInCondition )
        {
            conditionStr =
                    "last:" +
                    indentStrWithoutNextOrLastPart( indent ) +
                    "   " +
                    this.condition;
        }
        else if ( nextIfElseExecuteState != null &&
                nextIfElseExecuteState.runInCondition )
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
        if ( nextIfElseExecuteState == null )
        {
            variablesStr = "";
        }
        else
        {
            variablesStr =
                    nextIfElseExecuteState.getVariablesStr(
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
                        nextThenBodyState ) +
                indent + "else\n" +
                this.elseBodyComplexStmt.toString(
                        parent ,
                        indent + " " ,
                        lastElseBodyState ,
                        nextElseBodyState );
    }

}
