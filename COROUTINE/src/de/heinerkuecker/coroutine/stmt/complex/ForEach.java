package de.heinerkuecker.coroutine.stmt.complex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstmt;
import de.heinerkuecker.coroutine.exprs.CoroExpression;
import de.heinerkuecker.coroutine.exprs.GetProcedureArgument;
import de.heinerkuecker.coroutine.stmt.CoroIterStmt;
import de.heinerkuecker.coroutine.stmt.flow.BreakOrContinue;
import de.heinerkuecker.coroutine.stmt.flow.Continue;
import de.heinerkuecker.coroutine.stmt.flow.exc.LabelAlreadyInUseException;

public class ForEach<COROUTINE_RETURN /*, PARENT extends CoroutineIterator<COROUTINE_RETURN>*/ , RESUME_ARGUMENT , ELEMENT>
extends ComplexStmt<
    ForEach<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT , ELEMENT>,
    ForEachState<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT , ELEMENT>,
    COROUTINE_RETURN ,
    //PARENT
    RESUME_ARGUMENT
    >
{
    /**
     * Label to use with {@link Break} or {@link Continue}, optional.
     */
    public final String label;

    public final String variableName;

    public final Class<? extends ELEMENT> elementType;

    final CoroExpression<? extends Iterable<ELEMENT>> iterableExpression;

    final ComplexStmt<?, ?, COROUTINE_RETURN/*, PARENT /*CoroutineIterator<COROUTINE_RETURN>*/ , RESUME_ARGUMENT> bodyComplexStmt;

    /**
     * Constructor.
     */
    @SafeVarargs
    public ForEach(
            final String variableName ,
            final Class<? extends ELEMENT> elementType ,
            final CoroExpression<? extends Iterable<ELEMENT>> iterableExpression ,
            final CoroIterStmt<COROUTINE_RETURN/*, PARENT /*CoroutineIterator<COROUTINE_RETURN>*/>... stmts )
    {
        super(
                //creationStackOffset
                3 );

        this.label = null;

        this.variableName =
                Objects.requireNonNull(
                        variableName );

        this.elementType =
                Objects.requireNonNull(
                        elementType );

        this.iterableExpression =
                Objects.requireNonNull(
                        iterableExpression );

        this.bodyComplexStmt =
                Block.convertStmtsToComplexStmt(
                        // creationStackOffset
                        4 ,
                        stmts );
    }

    /**
     * Constructor.
     */
    @SafeVarargs
    public ForEach(
            final String label ,
            final String variableName ,
            final Class<? extends ELEMENT> elementType ,
            final CoroExpression<Iterable<ELEMENT>> iterableExpression ,
            final CoroIterStmt<COROUTINE_RETURN/*, PARENT /*CoroutineIterator<COROUTINE_RETURN>*/>... stmts )
    {
        super(
                //creationStackOffset
                3 );

        this.label = label;

        this.variableName =
                Objects.requireNonNull(
                        variableName );

        this.elementType =
                Objects.requireNonNull(
                        elementType );

        this.iterableExpression =
                Objects.requireNonNull(
                        iterableExpression );

        this.bodyComplexStmt =
                Block.convertStmtsToComplexStmt(
                        // creationStackOffset
                        4 ,
                        stmts );
    }

    /**
     * @see ComplexStmt#newState
     */
    @Override
    public ForEachState<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT , ELEMENT> newState(
            final CoroutineOrProcedureOrComplexstmt<COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        return new ForEachState<>(
                this ,
                //parent.getRootParent()
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

        for ( BreakOrContinue<? , ?> unresolvedBreakOrContinue : bodyComplexStmt.getUnresolvedBreaksOrContinues(
                alreadyCheckedProcedureNames ,
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
     * @see CoroIterStmt#getProcedureArgumentGetsNotInProcedure()
     */
    @Override
    public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    {
        final List<GetProcedureArgument<?>> result = new ArrayList<>();

        result.addAll(
                iterableExpression.getProcedureArgumentGetsNotInProcedure() );

        result.addAll(
                bodyComplexStmt.getProcedureArgumentGetsNotInProcedure() );

        return result;
    }

    /**
     * @see CoroIterStmt#setResultType(Class)
     */
    @Override
    public void setResultType(
            final Class<? extends COROUTINE_RETURN> resultType )
    {
        this.bodyComplexStmt.setResultType( resultType );
    }

    @Override
    public void checkLabelAlreadyInUse(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstmt<COROUTINE_RETURN, RESUME_ARGUMENT> parent ,
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
        //throw new RuntimeException( "not implemented" );
        final Map<String, Class<?>> thisLocalVariableTypes = new HashMap<>();
        thisLocalVariableTypes.putAll( localVariableTypes );

        thisLocalVariableTypes.put(
                variableName ,
                elementType );

        this.bodyComplexStmt.checkUseVariables(
                alreadyCheckedProcedureNames ,
                parent ,
                globalVariableTypes ,
                thisLocalVariableTypes );
    }

    @Override
    public void checkUseArguments(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstmt<?, ?> parent )
    {
        this.iterableExpression.checkUseArguments( alreadyCheckedProcedureNames, parent );
    }

    /**
     * @see ComplexStmt#toString
     */
    @Override
    public String toString(
            final CoroutineOrProcedureOrComplexstmt<COROUTINE_RETURN, RESUME_ARGUMENT> parent ,
            final String indent ,
            ComplexStmtState<?, ?, COROUTINE_RETURN , RESUME_ARGUMENT> lastStmtExecuteState ,
            ComplexStmtState<?, ?, COROUTINE_RETURN , RESUME_ARGUMENT> nextStmtExecuteState )
    {
        @SuppressWarnings("unchecked")
        final ForEachState<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT , ELEMENT> lastForeachExecuteState =
                (ForEachState<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT , ELEMENT>) lastStmtExecuteState;

        @SuppressWarnings("unchecked")
        final ForEachState<COROUTINE_RETURN/*, PARENT*/, RESUME_ARGUMENT , ELEMENT> nextForeachExecuteState =
                (ForEachState<COROUTINE_RETURN/*, PARENT*/, RESUME_ARGUMENT , ELEMENT>) nextStmtExecuteState;

        final ComplexStmtState<?, ?, COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> lastBodyState;
        if ( lastForeachExecuteState != null )
        {
            lastBodyState = lastForeachExecuteState.bodyComplexState;
        }
        else
        {
            lastBodyState = null;
        }

        final ComplexStmtState<?, ?, COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> nextBodyState;
        if ( nextForeachExecuteState != null )
        {
            nextBodyState = nextForeachExecuteState.bodyComplexState;
        }
        else
        {
            nextBodyState = null;
        }

        final String variableNameStr;
        if ( lastForeachExecuteState != null &&
                lastForeachExecuteState.runInConditionAndUpdate )
        {
            variableNameStr =
                    "last:" +
                    indentStrWithoutNextOrLastPart( indent ) +
                    "   " +
                    this.variableName;
        }
        else if ( nextForeachExecuteState != null &&
                nextForeachExecuteState.runInConditionAndUpdate )
        {
            variableNameStr =
                    "next:" +
                    indentStrWithoutNextOrLastPart( indent ) +
                    "   " +
                    this.variableName;
        }
        else
        {
            variableNameStr =
                    indent +
                    "   " +
                    this.variableName;
        }

        final String iterableExpressionStr;
        if ( lastForeachExecuteState != null &&
                lastForeachExecuteState.runInInitializer )
        {
            iterableExpressionStr =
                    "last:" +
                    indentStrWithoutNextOrLastPart( indent ) +
                    "   " +
                    this.iterableExpression;
        }
        else if ( nextForeachExecuteState != null &&
                nextForeachExecuteState.runInInitializer )
        {
            iterableExpressionStr =
                    "next:" +
                    indentStrWithoutNextOrLastPart( indent ) +
                    "   " +
                    this.iterableExpression;
        }
        else
        {
            iterableExpressionStr =
                    indent +
                    "   " +
                    this.iterableExpression;
        }

        // TODO check whether output subsequent iterator (CoroutineIterator) is usefuel
        //final String iterableStr;
        //if ( nextForeachExecuteState != null &&
        //        nextForeachExecuteState.iterator != null )
        //{
        //    iterableStr =
        //            indent +
        //            "iterator: " +
        //            nextForeachExecuteState.iterator +
        //            "\n";
        //}
        //else
        //{
        //    iterableStr = "";
        //}

        final String variablesStr;
        if ( nextForeachExecuteState == null )
        {
            variablesStr = "";
        }
        else
        {
            variablesStr =
                    nextForeachExecuteState.getVariablesStr(
                            indent );
        }

        return
                indent +
                ( this.label != null ? this.label + " : " : "" ) +
                this.getClass().getSimpleName() + " (" +
                ( this.creationStackTraceElement != null ? " " + this.creationStackTraceElement : "" ) +
                "\n" +
                variableNameStr + " :\n" +
                // TODO check whether output current (next) variable value is usefuel
                iterableExpressionStr + " )\n" +
                // TODO iterableStr +
                variablesStr +
                this.bodyComplexStmt.toString(
                        parent ,
                        indent + " " ,
                        lastBodyState ,
                        nextBodyState );
    }

}
