package de.heinerkuecker.coroutine.stmt.complex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import de.heinerkuecker.coroutine.CoroutineOrFunctioncallOrComplexstmt;
import de.heinerkuecker.coroutine.exprs.GetFunctionArgument;
import de.heinerkuecker.coroutine.exprs.SimpleExpression;
import de.heinerkuecker.coroutine.exprs.Value;
import de.heinerkuecker.coroutine.stmt.CoroStmt;
import de.heinerkuecker.coroutine.stmt.flow.Break;
import de.heinerkuecker.coroutine.stmt.flow.BreakOrContinue;
import de.heinerkuecker.coroutine.stmt.flow.Continue;
import de.heinerkuecker.coroutine.stmt.flow.exc.LabelAlreadyInUseException;
import de.heinerkuecker.coroutine.stmt.simple.NoOperation;
import de.heinerkuecker.coroutine.stmt.simple.SimpleStmt;

public class For<
    FUNCTION_RETURN ,
    COROUTINE_RETURN /*, PARENT extends CoroutineIterator<COROUTINE_RETURN>*/,
    RESUME_ARGUMENT
    >
extends ComplexStmt<
    For<FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT*/, RESUME_ARGUMENT> ,
    ForState<FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT*/, RESUME_ARGUMENT> ,
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

    // TODO nur SimpleStmt oder FunctionCall erlauben
    final CoroStmt<FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT /*CoroutineIterator<COROUTINE_RETURN>*/> initialStmt;

    final SimpleExpression<Boolean , COROUTINE_RETURN> condition;

    // TODO nur SimpleStmt oder FunctionCall erlauben
    final CoroStmt<FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT /*CoroutineIterator<COROUTINE_RETURN>*/> updateStmt;

    final ComplexStmt<?, ?, FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT /*CoroutineIterator<COROUTINE_RETURN>*/, RESUME_ARGUMENT> bodyComplexStmt;

    /**
     * Constructor.
     */
    @SafeVarargs
    public For(
            final CoroStmt<FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT /*CoroutineIterator<COROUTINE_RETURN>*/> initialStmt ,
            final SimpleExpression<Boolean , COROUTINE_RETURN> condition ,
            final CoroStmt<FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT /*CoroutineIterator<COROUTINE_RETURN>*/> updateStmt ,
            final CoroStmt<FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT /*CoroutineIterator<COROUTINE_RETURN>*/>... stmts )
    {
        super(
                //creationStackOffset
                3 );

        this.label = null;

        if ( initialStmt == null )
        {
            // C style default
            this.initialStmt = new NoOperation<>();
        }
        else if ( initialStmt instanceof BreakOrContinue )
        {
            throw new IllegalArgumentException(
                    "break or continue in initial stmt: " +
                    initialStmt );
        }
        else
        {
            this.initialStmt =
                    Objects.requireNonNull(
                            initialStmt );
        }

        if ( condition == null )
        {
            // C style default
            this.condition =
                    //new True()
                    new Value<Boolean , COROUTINE_RETURN>( true );
        }
        else
        {
            this.condition = condition;
        }

        if ( updateStmt == null )
        {
            // C style default
            this.updateStmt = new NoOperation<>();
        }
        else if ( updateStmt instanceof BreakOrContinue )
        {
            throw new IllegalArgumentException(
                    "break or continue in update stmt: " +
                            updateStmt );
        }
        else
        {
            this.updateStmt =
                    Objects.requireNonNull(
                            updateStmt );
        }

        this.bodyComplexStmt =
                Block.convertStmtsToComplexStmt(
                        // creationStackOffset
                        4 ,
                        stmts );
    }

    ///**
    // * Constructor.
    // */
    //@SafeVarargs
    //public For(
    //        final CoroIterStmt<COROUTINE_RETURN/*, PARENT /*CoroutineIterator<COROUTINE_RETURN>*/> initialStmt ,
    //        final CoroExpression<Boolean> condition ,
    //        final CoroIterStmt<COROUTINE_RETURN/*, PARENT /*CoroutineIterator<COROUTINE_RETURN>*/> updateStmt ,
    //        final CoroIterStmt<COROUTINE_RETURN/*, PARENT /*CoroutineIterator<COROUTINE_RETURN>*/>... stmts )
    //{
    //    super(
    //            //creationStackOffset
    //            3 );
    //
    //    this.label = null;
    //
    //    if ( initialStmt == null )
    //    {
    //        // C style default
    //        this.initialStmt = new NoOperation<>();
    //    }
    //    else if ( initialStmt instanceof BreakOrContinue )
    //    {
    //        throw new IllegalArgumentException(
    //                "break or continue in initial stmt: " +
    //                initialStmt );
    //    }
    //    else
    //    {
    //        this.initialStmt =
    //                Objects.requireNonNull(
    //                        initialStmt );
    //    }
    //
    //    if ( condition == null )
    //    {
    //        // C style default
    //        this.condition = new True();
    //    }
    //    else
    //    {
    //        this.condition =
    //                new IsTrue(
    //                        condition );
    //    }
    //
    //    if ( updateStmt == null )
    //    {
    //        // C style default
    //        this.updateStmt = new NoOperation<>();
    //    }
    //    else if ( updateStmt instanceof BreakOrContinue )
    //    {
    //        throw new IllegalArgumentException(
    //                "break or continue in update stmt: " +
    //                        updateStmt );
    //    }
    //    else
    //    {
    //        this.updateStmt =
    //                Objects.requireNonNull(
    //                        updateStmt );
    //    }
    //
    //    this.bodyComplexStmt =
    //            Block.convertStmtsToComplexStmt(
    //                    // creationStackOffset
    //                    4 ,
    //                    stmts );
    //}

    /**
     * Constructor.
     */
    @SafeVarargs
    public For(
            final String label ,
            final CoroStmt<FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT /*CoroutineIterator<COROUTINE_RETURN>*/> initialStmt ,
            final SimpleExpression<Boolean , COROUTINE_RETURN> condition ,
            final CoroStmt<FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT /*CoroutineIterator<COROUTINE_RETURN>*/> updateStmt ,
            final CoroStmt<FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT /*CoroutineIterator<COROUTINE_RETURN>*/>... stmts )
    {
        super(
                //creationStackOffset
                3 );

        this.label = label;

        if ( initialStmt == null )
        {
            // C style default
            this.initialStmt = new NoOperation<>();
        }
        else
        {
            this.initialStmt =
                    Objects.requireNonNull(
                            initialStmt );
        }

        if ( condition == null )
        {
            // C style default
            this.condition =
                    //new True()
                    new Value<Boolean , COROUTINE_RETURN>( true );
        }
        else
        {
            this.condition = condition;
        }

        if ( updateStmt == null )
        {
            // C style default
            this.updateStmt = new NoOperation<>();
        }
        else
        {
            this.updateStmt =
                    Objects.requireNonNull(
                            updateStmt );
        }

        this.bodyComplexStmt =
                Block.convertStmtsToComplexStmt(
                        // creationStackOffset
                        4 ,
                        stmts );
    }

    ///**
    // * Constructor.
    // */
    //@SafeVarargs
    //public For(
    //        final String label ,
    //        final CoroIterStmt<COROUTINE_RETURN/*, PARENT /*CoroutineIterator<COROUTINE_RETURN>*/> initialStmt ,
    //        final CoroExpression<Boolean> condition ,
    //        final CoroIterStmt<COROUTINE_RETURN/*, PARENT /*CoroutineIterator<COROUTINE_RETURN>*/> updateStmt ,
    //        final CoroIterStmt<COROUTINE_RETURN/*, PARENT /*CoroutineIterator<COROUTINE_RETURN>*/>... stmts )
    //{
    //    super(
    //            //creationStackOffset
    //            3 );
    //
    //    this.label = label;
    //
    //    if ( initialStmt == null )
    //    {
    //        // C style default
    //        this.initialStmt = new NoOperation<>();
    //    }
    //    else
    //    {
    //        this.initialStmt =
    //                Objects.requireNonNull(
    //                        initialStmt );
    //    }
    //
    //    if ( condition == null )
    //    {
    //        // C style default
    //        this.condition = new True();
    //    }
    //    else
    //    {
    //        this.condition =
    //                new IsTrue(
    //                        condition );
    //    }
    //
    //    if ( updateStmt == null )
    //    {
    //        // C style default
    //        this.updateStmt = new NoOperation<>();
    //    }
    //    else
    //    {
    //        this.updateStmt =
    //                Objects.requireNonNull(
    //                        updateStmt );
    //    }
    //
    //    this.bodyComplexStmt =
    //            Block.convertStmtsToComplexStmt(
    //                    // creationStackOffset
    //                    4 ,
    //                    stmts );
    //}

    @Override
    public ForState<FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> newState(
            final CoroutineOrFunctioncallOrComplexstmt<FUNCTION_RETURN , COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        return new ForState<FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT>(
                this ,
                parent );
    }

    @Override
    public List<BreakOrContinue<?, ?, ?>> getUnresolvedBreaksOrContinues(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<? , COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        if ( initialStmt instanceof ComplexStmt )
        {
            final List<BreakOrContinue<?, ?, ?>> unresolvedBreaksOrContinues =
                    ( (ComplexStmt<?, ?, FUNCTION_RETURN , COROUTINE_RETURN/*, /*PARENT * / ? super CoroutineIterator<COROUTINE_RETURN>*/ , RESUME_ARGUMENT>) initialStmt ).getUnresolvedBreaksOrContinues(
                            alreadyCheckedFunctionNames ,
                            parent );

            if ( ! unresolvedBreaksOrContinues.isEmpty() )
            {
                throw new IllegalArgumentException(
                        "unpermitted breaks or continues in initial stmt" +
                        unresolvedBreaksOrContinues );
            }
        }

        if ( updateStmt instanceof ComplexStmt )
        {
            final List<BreakOrContinue<?, ?, ?>> unresolvedBreaksOrContinues =
                    ( (ComplexStmt<?, ?, FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT /*? super CoroutineIterator<COROUTINE_RETURN>*/ , RESUME_ARGUMENT>) updateStmt ).getUnresolvedBreaksOrContinues(
                            alreadyCheckedFunctionNames ,
                            parent );

            if ( ! unresolvedBreaksOrContinues.isEmpty() )
            {
                throw new IllegalArgumentException(
                        "unpermitted breaks or continues in update stmt" +
                        unresolvedBreaksOrContinues );
            }
        }

        final List<BreakOrContinue<?, ?, ?>> result = new ArrayList<>();

        if ( initialStmt instanceof ComplexStmt )
        {
            result.addAll(
                    ( (ComplexStmt<?, ?, FUNCTION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT>) initialStmt ).getUnresolvedBreaksOrContinues(
                            alreadyCheckedFunctionNames ,
                            parent ) );
        }

        for ( final BreakOrContinue<?, ?, ?> unresolvedBreakOrContinue : bodyComplexStmt.getUnresolvedBreaksOrContinues(
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

        if ( updateStmt instanceof ComplexStmt )
        {
            result.addAll(
                    ( (ComplexStmt<?, ?, FUNCTION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT>) updateStmt ).getUnresolvedBreaksOrContinues(
                            alreadyCheckedFunctionNames ,
                            parent ) );
        }

        return result;
    }

    @Override
    public List<GetFunctionArgument<? , ?>> getFunctionArgumentGetsNotInFunction()
    {
        final List<GetFunctionArgument<? , ?>> result = new ArrayList<>();

        result.addAll(
                initialStmt.getFunctionArgumentGetsNotInFunction() );

        result.addAll(
                condition.getFunctionArgumentGetsNotInFunction() );

        result.addAll(
                updateStmt.getFunctionArgumentGetsNotInFunction() );

        result.addAll(
                bodyComplexStmt.getFunctionArgumentGetsNotInFunction() );

        return result;
    }

    @Override
    public void setStmtCoroutineReturnType(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ? , ?> parent ,
            final Class<? extends COROUTINE_RETURN> coroutineReturnType )
    {
        this.bodyComplexStmt.setStmtCoroutineReturnType(
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
        final Map<String, Class<?>> thisLocalVariableTypes = new HashMap<>( localVariableTypes );

        this.initialStmt.checkUseVariables(
                alreadyCheckedFunctionNames ,
                parent ,
                globalVariableTypes ,
                thisLocalVariableTypes );

        this.condition.checkUseVariables(
                alreadyCheckedFunctionNames ,
                parent ,
                globalVariableTypes ,
                thisLocalVariableTypes );

        this.bodyComplexStmt.checkUseVariables(
                alreadyCheckedFunctionNames ,
                parent ,
                globalVariableTypes ,
                thisLocalVariableTypes );

        this.updateStmt.checkUseVariables(
                alreadyCheckedFunctionNames ,
                parent ,
                globalVariableTypes ,
                thisLocalVariableTypes );
    }

    @Override
    public void checkUseArguments(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent )
    {
        this.initialStmt.checkUseArguments( alreadyCheckedFunctionNames, parent );
        this.condition.checkUseArguments( alreadyCheckedFunctionNames, parent );
        this.updateStmt.checkUseArguments( alreadyCheckedFunctionNames, parent );
        this.bodyComplexStmt.checkUseArguments( alreadyCheckedFunctionNames, parent );
    }

    /**
     * @see ComplexStmt#toString
     */
    @Override
    public String toString(
            final CoroutineOrFunctioncallOrComplexstmt</*FUNCTION_RETURN*/? , COROUTINE_RETURN, RESUME_ARGUMENT> parent ,
            final String indent ,
            ComplexStmtState<?, ?, /*FUNCTION_RETURN*/? , COROUTINE_RETURN , RESUME_ARGUMENT> lastStmtExecuteState ,
            ComplexStmtState<?, ?, /*FUNCTION_RETURN*/? , COROUTINE_RETURN , RESUME_ARGUMENT> nextStmtExecuteState )
    {
        @SuppressWarnings("unchecked")
        final ForState<FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> lastForExecuteState =
                (ForState<FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT>) lastStmtExecuteState;

        @SuppressWarnings("unchecked")
        final ForState<FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> nextForExecuteState =
                (ForState<FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT>) nextStmtExecuteState;

        final ComplexStmtState<?, ?, FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> lastBodyState;
        if ( lastForExecuteState != null )
        {
            lastBodyState = lastForExecuteState.bodyComplexState;
        }
        else
        {
            lastBodyState = null;
        }

        final ComplexStmtState<?, ?, FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> nextBodyState;
        if ( nextForExecuteState != null )
        {
            nextBodyState = nextForExecuteState.bodyComplexState;
        }
        else
        {
            nextBodyState = null;
        }

        final String initialStmtStr;
        if ( initialStmt instanceof ComplexStmt )
        {
            // TODO
            initialStmtStr = "???";
        }
        else if ( initialStmt instanceof SimpleStmt )
        {
                final String tmpIndent;
                if ( lastForExecuteState != null &&
                        lastForExecuteState.runInInitializer )
                {
                    tmpIndent = "last:" + indentStrWithoutNextOrLastPart( indent );
                }
                else if ( nextForExecuteState != null &&
                        nextForExecuteState.runInInitializer )
                {
                    tmpIndent = "next:" + indentStrWithoutNextOrLastPart( indent );
                }
                else
                {
                    tmpIndent = indent;
                }
                initialStmtStr = tmpIndent + "   " + this.initialStmt;
        }
        else
        {
            throw new IllegalStateException( String.valueOf( initialStmt ) );
        }

        final String conditionStr;
        if ( lastForExecuteState != null &&
                lastForExecuteState.runInCondition )
        {
            conditionStr =
                    "last:" +
                    indentStrWithoutNextOrLastPart( indent ) +
                    "   " +
                    this.condition;
        }
        else if ( nextForExecuteState != null &&
                nextForExecuteState.runInCondition )
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

        final String updateStmtStr;
        if ( updateStmt instanceof ComplexStmt )
        {
            // TODO
            updateStmtStr = "???";
        }
        else if ( updateStmt instanceof SimpleStmt )
        {
                final String tmpIndent;
                if ( lastForExecuteState != null &&
                        lastForExecuteState.runInUpdate )
                {
                    tmpIndent = "last:" + indentStrWithoutNextOrLastPart( indent );
                }
                else if ( nextForExecuteState != null &&
                        nextForExecuteState.runInUpdate )
                {
                    tmpIndent = "next:" + indentStrWithoutNextOrLastPart( indent );
                }
                else
                {
                    tmpIndent = indent;
                }
                updateStmtStr = tmpIndent + "   " + this.updateStmt;
        }
        else
        {
            throw new IllegalStateException( String.valueOf( updateStmt ) );
        }

        final String variablesStr;
        if ( nextForExecuteState == null )
        {
            variablesStr = "";
        }
        else
        {
            variablesStr =
                    nextForExecuteState.getVariablesStr(
                            indent );
        }

        return
                indent +
                ( this.label != null ? this.label + " : " : "" ) +
                this.getClass().getSimpleName() + " (" +
                ( this.creationStackTraceElement != null ? " " + this.creationStackTraceElement : "" ) +
                "\n" +
                initialStmtStr + " ;\n" +
                conditionStr + " ;\n" +
                updateStmtStr + " )\n" +
                variablesStr +
                this.bodyComplexStmt.toString(
                        parent ,
                        indent + " " ,
                        lastBodyState ,
                        nextBodyState );
    }

}
