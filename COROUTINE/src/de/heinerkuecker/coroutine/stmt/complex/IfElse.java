package de.heinerkuecker.coroutine.stmt.complex;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstmt;
import de.heinerkuecker.coroutine.exprs.CoroExpression;
import de.heinerkuecker.coroutine.exprs.GetProcedureArgument;
import de.heinerkuecker.coroutine.exprs.Value;
import de.heinerkuecker.coroutine.stmt.CoroIterStmt;
import de.heinerkuecker.coroutine.stmt.flow.BreakOrContinue;

public class IfElse<COROUTINE_RETURN/*, PARENT extends CoroutineIterator<COROUTINE_RETURN>*/ , RESUME_ARGUMENT>
extends ComplexStmt<
    IfElse<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT>,
    IfElseState<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT>,
    COROUTINE_RETURN ,
    //PARENT
    RESUME_ARGUMENT
    >
{
    //final ConditionOrBooleanExpression/*Condition*/ condition
    final CoroExpression<Boolean> condition;
    final ComplexStmt<?, ?, COROUTINE_RETURN/*, PARENT/*CoroutineIterator<COROUTINE_RETURN>*/ , RESUME_ARGUMENT> thenBodyComplexStmt;
    final ComplexStmt<?, ?, COROUTINE_RETURN/*, PARENT/*CoroutineIterator<COROUTINE_RETURN>*/ , RESUME_ARGUMENT> elseBodyComplexStmt;

    /**
     * Constructor.
     */
    public IfElse(
            //final ConditionOrBooleanExpression/*Condition*/ condition
            final CoroExpression<Boolean> condition ,
            final CoroIterStmt<COROUTINE_RETURN/*, CoroutineIterator<COROUTINE_RETURN>*/>[] thenStmts ,
            final CoroIterStmt<COROUTINE_RETURN/*, CoroutineIterator<COROUTINE_RETURN>*/>[] elseStmts )
    {
        super(
                //creationStackOffset
                3 );

        this.condition = condition;

        if ( thenStmts.length == 1 &&
                thenStmts[ 0 ] instanceof ComplexStmt )
        {
            this.thenBodyComplexStmt =
                    (ComplexStmt<?, ?, COROUTINE_RETURN/*, PARENT/*? super CoroutineIterator<COROUTINE_RETURN>*/, RESUME_ARGUMENT>) thenStmts[ 0 ];
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
                    (ComplexStmt<?, ?, COROUTINE_RETURN/*, PARENT/*? super CoroutineIterator<COROUTINE_RETURN>*/ , RESUME_ARGUMENT>) elseStmts[ 0 ];
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
    //        final CoroExpression<Boolean> condition ,
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
            final CoroIterStmt<COROUTINE_RETURN/*, CoroutineIterator<COROUTINE_RETURN>*/>[] thenStmts ,
            final CoroIterStmt<COROUTINE_RETURN/*, CoroutineIterator<COROUTINE_RETURN>*/>[] elseStmts )
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
                    (ComplexStmt<?, ?, COROUTINE_RETURN/*, PARENT/*? super CoroutineIterator<COROUTINE_RETURN>*/ , RESUME_ARGUMENT>) thenStmts[ 0 ];
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
                    (ComplexStmt<?, ?, COROUTINE_RETURN/*, PARENT/*? super CoroutineIterator<COROUTINE_RETURN>*/ , RESUME_ARGUMENT>) elseStmts[ 0 ];
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
    public IfElseState<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> newState(
            final CoroutineOrProcedureOrComplexstmt<COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        return new IfElseState<>(
                this ,
                parent );
    }

    /**
     * @see ComplexStmt#getUnresolvedBreaksOrContinues()
     */
    @Override
    public List<BreakOrContinue<? , ?>> getUnresolvedBreaksOrContinues(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstmt<COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        final List<BreakOrContinue<? , ?>> result = new ArrayList<>();

        result.addAll(
                thenBodyComplexStmt.getUnresolvedBreaksOrContinues(
                        alreadyCheckedProcedureNames ,
                        parent ) );

        result.addAll(
                elseBodyComplexStmt.getUnresolvedBreaksOrContinues(
                        alreadyCheckedProcedureNames ,
                        parent ) );

        return result;
    }

    /**
     * @see CoroIterStmt#getProcedureArgumentGetsNotInProcedure()
     */
    @Override
    public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    {
        final List<GetProcedureArgument<?>> result = new ArrayList<>();

        result.addAll(
                condition.getProcedureArgumentGetsNotInProcedure() );

        result.addAll(
                thenBodyComplexStmt.getProcedureArgumentGetsNotInProcedure() );

        result.addAll(
                elseBodyComplexStmt.getProcedureArgumentGetsNotInProcedure() );

        return result;
    }

    /**
     * @see CoroIterStmt#setResultType(Class)
     */
    @Override
    public void setResultType(
            final Class<? extends COROUTINE_RETURN> resultType )
    {
        this.thenBodyComplexStmt.setResultType( resultType );
        this.elseBodyComplexStmt.setResultType( resultType );
    }

    @Override
    public void checkLabelAlreadyInUse(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstmt<COROUTINE_RETURN, RESUME_ARGUMENT> parent ,
            final Set<String> labels )
    {
        this.thenBodyComplexStmt.checkLabelAlreadyInUse(
                alreadyCheckedProcedureNames ,
                parent ,
                labels );

        this.elseBodyComplexStmt.checkLabelAlreadyInUse(
                alreadyCheckedProcedureNames ,
                parent ,
                labels );
    }

    @Override
    public void checkUseVariables(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstmt<?, ?> parent ,
            final Map<String, Class<?>> globalVariableTypes ,
            final Map<String, Class<?>> localVariableTypes )
    {
        this.condition.checkUseVariables(
                alreadyCheckedProcedureNames ,
                parent ,
                globalVariableTypes, localVariableTypes );

        this.thenBodyComplexStmt.checkUseVariables(
                alreadyCheckedProcedureNames ,
                parent ,
                globalVariableTypes, localVariableTypes );

        this.elseBodyComplexStmt.checkUseVariables(
                alreadyCheckedProcedureNames ,
                parent ,
                globalVariableTypes, localVariableTypes );
    }

    @Override
    public void checkUseArguments(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstmt<?, ?> parent )
    {
        this.condition.checkUseArguments( alreadyCheckedProcedureNames , parent );
        this.thenBodyComplexStmt.checkUseArguments( alreadyCheckedProcedureNames , parent );
        this.elseBodyComplexStmt.checkUseArguments( alreadyCheckedProcedureNames , parent );
    }

    /**
     * @see ComplexStmt#toString
     */
    @Override
    public String toString(
            final CoroutineOrProcedureOrComplexstmt<COROUTINE_RETURN, RESUME_ARGUMENT> parent ,
            final String indent ,
            final ComplexStmtState<?, /*STMT*/?, COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> lastStmtExecuteState ,
            final ComplexStmtState<?, /*STMT*/?, COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> nextStmtExecuteState )
    {
        @SuppressWarnings("unchecked")
        final IfElseState<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> lastIfElseExecuteState =
                (IfElseState<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT>) lastStmtExecuteState;

        @SuppressWarnings("unchecked")
        final IfElseState<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> nextIfElseExecuteState =
                (IfElseState<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT>) nextStmtExecuteState;

        final ComplexStmtState<?, ?, COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> lastThenBodyState;
        if ( lastIfElseExecuteState != null )
        {
            lastThenBodyState = lastIfElseExecuteState.thenBodyComplexState;
        }
        else
        {
            lastThenBodyState = null;
        }

        final ComplexStmtState<?, ?, COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> nextThenBodyState;
        if ( nextIfElseExecuteState != null )
        {
            nextThenBodyState = nextIfElseExecuteState.thenBodyComplexState;
        }
        else
        {
            nextThenBodyState = null;
        }

        final ComplexStmtState<?, ?, COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> lastElseBodyState;
        if ( lastIfElseExecuteState != null )
        {
            lastElseBodyState = lastIfElseExecuteState.elseBodyComplexState;
        }
        else
        {
            lastElseBodyState = null;
        }

        final ComplexStmtState<?, ?, COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> nextElseBodyState;
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
