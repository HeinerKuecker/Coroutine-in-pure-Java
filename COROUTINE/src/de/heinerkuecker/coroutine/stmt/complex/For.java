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
import de.heinerkuecker.coroutine.exprs.Value;
import de.heinerkuecker.coroutine.stmt.CoroIterStmt;
import de.heinerkuecker.coroutine.stmt.flow.BreakOrContinue;
import de.heinerkuecker.coroutine.stmt.flow.exc.LabelAlreadyInUseException;
import de.heinerkuecker.coroutine.stmt.simple.NoOperation;
import de.heinerkuecker.coroutine.stmt.simple.SimpleStep;

public class For<COROUTINE_RETURN /*, PARENT extends CoroutineIterator<COROUTINE_RETURN>*/, RESUME_ARGUMENT>
extends ComplexStmt<
    For<COROUTINE_RETURN/*, PARENT*/, RESUME_ARGUMENT> ,
    ForState<COROUTINE_RETURN/*, PARENT*/, RESUME_ARGUMENT> ,
    COROUTINE_RETURN ,
    //PARENT
    RESUME_ARGUMENT
    >
{
    public final String label;

    // TODO nur SimpleStep oder ProcedureCall erlauben
    final CoroIterStmt<COROUTINE_RETURN/*, PARENT /*CoroutineIterator<COROUTINE_RETURN>*/> initialStep;

    //final ConditionOrBooleanExpression condition;
    final CoroExpression<Boolean> condition;

    // TODO nur SimpleStep oder ProcedureCall erlauben
    final CoroIterStmt<COROUTINE_RETURN/*, PARENT /*CoroutineIterator<COROUTINE_RETURN>*/> updateStep;

    final ComplexStmt<?, ?, COROUTINE_RETURN/*, PARENT /*CoroutineIterator<COROUTINE_RETURN>*/, RESUME_ARGUMENT> bodyComplexStep;

    /**
     * Constructor.
     */
    @SafeVarargs
    public For(
            final CoroIterStmt<COROUTINE_RETURN/*, PARENT /*CoroutineIterator<COROUTINE_RETURN>*/> initialStep ,
            //final ConditionOrBooleanExpression/*Condition*/ condition
            final CoroExpression<Boolean> condition ,
            final CoroIterStmt<COROUTINE_RETURN/*, PARENT /*CoroutineIterator<COROUTINE_RETURN>*/> updateStep ,
            final CoroIterStmt<COROUTINE_RETURN/*, PARENT /*CoroutineIterator<COROUTINE_RETURN>*/>... steps )
    {
        super(
                //creationStackOffset
                3 );

        this.label = null;

        if ( initialStep == null )
        {
            // C style default
            this.initialStep = new NoOperation<>();
        }
        else if ( initialStep instanceof BreakOrContinue )
        {
            throw new IllegalArgumentException(
                    "break or continue in initial step: " +
                    initialStep );
        }
        else
        {
            this.initialStep =
                    Objects.requireNonNull(
                            initialStep );
        }

        if ( condition == null )
        {
            // C style default
            this.condition =
                    //new True()
                    new Value<Boolean>( true );
        }
        else
        {
            this.condition = condition;
        }

        if ( updateStep == null )
        {
            // C style default
            this.updateStep = new NoOperation<>();
        }
        else if ( updateStep instanceof BreakOrContinue )
        {
            throw new IllegalArgumentException(
                    "break or continue in update step: " +
                            updateStep );
        }
        else
        {
            this.updateStep =
                    Objects.requireNonNull(
                            updateStep );
        }

        this.bodyComplexStep =
                Block.convertStepsToComplexStep(
                        // creationStackOffset
                        4 ,
                        steps );
    }

    ///**
    // * Constructor.
    // */
    //@SafeVarargs
    //public For(
    //        final CoroIterStep<COROUTINE_RETURN/*, PARENT /*CoroutineIterator<COROUTINE_RETURN>*/> initialStep ,
    //        final CoroExpression<Boolean> condition ,
    //        final CoroIterStep<COROUTINE_RETURN/*, PARENT /*CoroutineIterator<COROUTINE_RETURN>*/> updateStep ,
    //        final CoroIterStep<COROUTINE_RETURN/*, PARENT /*CoroutineIterator<COROUTINE_RETURN>*/>... steps )
    //{
    //    super(
    //            //creationStackOffset
    //            3 );
    //
    //    this.label = null;
    //
    //    if ( initialStep == null )
    //    {
    //        // C style default
    //        this.initialStep = new NoOperation<>();
    //    }
    //    else if ( initialStep instanceof BreakOrContinue )
    //    {
    //        throw new IllegalArgumentException(
    //                "break or continue in initial step: " +
    //                initialStep );
    //    }
    //    else
    //    {
    //        this.initialStep =
    //                Objects.requireNonNull(
    //                        initialStep );
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
    //    if ( updateStep == null )
    //    {
    //        // C style default
    //        this.updateStep = new NoOperation<>();
    //    }
    //    else if ( updateStep instanceof BreakOrContinue )
    //    {
    //        throw new IllegalArgumentException(
    //                "break or continue in update step: " +
    //                        updateStep );
    //    }
    //    else
    //    {
    //        this.updateStep =
    //                Objects.requireNonNull(
    //                        updateStep );
    //    }
    //
    //    this.bodyComplexStep =
    //            Block.convertStepsToComplexStep(
    //                    // creationStackOffset
    //                    4 ,
    //                    steps );
    //}

    /**
     * Constructor.
     */
    @SafeVarargs
    public For(
            final String label ,
            final CoroIterStmt<COROUTINE_RETURN/*, PARENT /*CoroutineIterator<COROUTINE_RETURN>*/> initialStep ,
            //final ConditionOrBooleanExpression/*Condition*/ condition
            final CoroExpression<Boolean> condition ,
            final CoroIterStmt<COROUTINE_RETURN/*, PARENT /*CoroutineIterator<COROUTINE_RETURN>*/> updateStep ,
            final CoroIterStmt<COROUTINE_RETURN/*, PARENT /*CoroutineIterator<COROUTINE_RETURN>*/>... steps )
    {
        super(
                //creationStackOffset
                3 );

        this.label = label;

        if ( initialStep == null )
        {
            // C style default
            this.initialStep = new NoOperation<>();
        }
        else
        {
            this.initialStep =
                    Objects.requireNonNull(
                            initialStep );
        }

        if ( condition == null )
        {
            // C style default
            this.condition =
                    //new True()
                    new Value<Boolean>( true );
        }
        else
        {
            this.condition = condition;
        }

        if ( updateStep == null )
        {
            // C style default
            this.updateStep = new NoOperation<>();
        }
        else
        {
            this.updateStep =
                    Objects.requireNonNull(
                            updateStep );
        }

        this.bodyComplexStep =
                Block.convertStepsToComplexStep(
                        // creationStackOffset
                        4 ,
                        steps );
    }

    ///**
    // * Constructor.
    // */
    //@SafeVarargs
    //public For(
    //        final String label ,
    //        final CoroIterStep<COROUTINE_RETURN/*, PARENT /*CoroutineIterator<COROUTINE_RETURN>*/> initialStep ,
    //        final CoroExpression<Boolean> condition ,
    //        final CoroIterStep<COROUTINE_RETURN/*, PARENT /*CoroutineIterator<COROUTINE_RETURN>*/> updateStep ,
    //        final CoroIterStep<COROUTINE_RETURN/*, PARENT /*CoroutineIterator<COROUTINE_RETURN>*/>... steps )
    //{
    //    super(
    //            //creationStackOffset
    //            3 );
    //
    //    this.label = label;
    //
    //    if ( initialStep == null )
    //    {
    //        // C style default
    //        this.initialStep = new NoOperation<>();
    //    }
    //    else
    //    {
    //        this.initialStep =
    //                Objects.requireNonNull(
    //                        initialStep );
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
    //    if ( updateStep == null )
    //    {
    //        // C style default
    //        this.updateStep = new NoOperation<>();
    //    }
    //    else
    //    {
    //        this.updateStep =
    //                Objects.requireNonNull(
    //                        updateStep );
    //    }
    //
    //    this.bodyComplexStep =
    //            Block.convertStepsToComplexStep(
    //                    // creationStackOffset
    //                    4 ,
    //                    steps );
    //}

    @Override
    public ForState<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> newState(
            final CoroutineOrProcedureOrComplexstmt<COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        return new ForState<>(
                this ,
                parent );
    }

    @Override
    public List<BreakOrContinue<?, ?>> getUnresolvedBreaksOrContinues(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstmt<COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        if ( initialStep instanceof ComplexStmt )
        {
            final List<BreakOrContinue<?, ?>> unresolvedBreaksOrContinues =
                    ( (ComplexStmt<?, ?, COROUTINE_RETURN/*, /*PARENT * / ? super CoroutineIterator<COROUTINE_RETURN>*/ , RESUME_ARGUMENT>) initialStep ).getUnresolvedBreaksOrContinues(
                            alreadyCheckedProcedureNames ,
                            parent );

            if ( ! unresolvedBreaksOrContinues.isEmpty() )
            {
                throw new IllegalArgumentException(
                        "unpermitted breaks or continues in initial step" +
                        unresolvedBreaksOrContinues );
            }
        }

        if ( updateStep instanceof ComplexStmt )
        {
            final List<BreakOrContinue<?, ?>> unresolvedBreaksOrContinues =
                    ( (ComplexStmt<?, ?, COROUTINE_RETURN/*, PARENT /*? super CoroutineIterator<COROUTINE_RETURN>*/ , RESUME_ARGUMENT>) updateStep ).getUnresolvedBreaksOrContinues(
                            alreadyCheckedProcedureNames ,
                            parent );

            if ( ! unresolvedBreaksOrContinues.isEmpty() )
            {
                throw new IllegalArgumentException(
                        "unpermitted breaks or continues in update step" +
                        unresolvedBreaksOrContinues );
            }
        }

        final List<BreakOrContinue<?, ?>> result = new ArrayList<>();

        if ( initialStep instanceof ComplexStmt )
        {
            result.addAll(
                    ( (ComplexStmt<?, ?, COROUTINE_RETURN , RESUME_ARGUMENT>) initialStep ).getUnresolvedBreaksOrContinues(
                            alreadyCheckedProcedureNames ,
                            parent ) );
        }

        for ( final BreakOrContinue<?, ?> unresolvedBreakOrContinue : bodyComplexStep.getUnresolvedBreaksOrContinues(
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

        if ( updateStep instanceof ComplexStmt )
        {
            result.addAll(
                    ( (ComplexStmt<?, ?, COROUTINE_RETURN , RESUME_ARGUMENT>) updateStep ).getUnresolvedBreaksOrContinues(
                            alreadyCheckedProcedureNames ,
                            parent ) );
        }

        return result;
    }

    @Override
    public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    {
        final List<GetProcedureArgument<?>> result = new ArrayList<>();

        result.addAll(
                initialStep.getProcedureArgumentGetsNotInProcedure() );

        result.addAll(
                condition.getProcedureArgumentGetsNotInProcedure() );

        result.addAll(
                updateStep.getProcedureArgumentGetsNotInProcedure() );

        result.addAll(
                bodyComplexStep.getProcedureArgumentGetsNotInProcedure() );

        return result;
    }

    @Override
    public void setResultType(
            final Class<? extends COROUTINE_RETURN> resultType )
    {
        this.bodyComplexStep.setResultType( resultType );
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
        this.bodyComplexStep.checkLabelAlreadyInUse(
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
        final Map<String, Class<?>> thisLocalVariableTypes = new HashMap<>( localVariableTypes );

        this.initialStep.checkUseVariables(
                alreadyCheckedProcedureNames ,
                parent ,
                globalVariableTypes ,
                thisLocalVariableTypes );

        this.condition.checkUseVariables(
                alreadyCheckedProcedureNames ,
                parent ,
                globalVariableTypes ,
                thisLocalVariableTypes );

        this.bodyComplexStep.checkUseVariables(
                alreadyCheckedProcedureNames ,
                parent ,
                globalVariableTypes ,
                thisLocalVariableTypes );

        this.updateStep.checkUseVariables(
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
        this.initialStep.checkUseArguments( alreadyCheckedProcedureNames, parent );
        this.condition.checkUseArguments( alreadyCheckedProcedureNames, parent );
        this.updateStep.checkUseArguments( alreadyCheckedProcedureNames, parent );
        this.bodyComplexStep.checkUseArguments( alreadyCheckedProcedureNames, parent );
    }

    /**
     * @see ComplexStmt#toString
     */
    @Override
    public String toString(
            final CoroutineOrProcedureOrComplexstmt<COROUTINE_RETURN, RESUME_ARGUMENT> parent ,
            final String indent ,
            ComplexStmtState<?, ?, COROUTINE_RETURN , RESUME_ARGUMENT> lastStepExecuteState ,
            ComplexStmtState<?, ?, COROUTINE_RETURN , RESUME_ARGUMENT> nextStepExecuteState )
    {
        @SuppressWarnings("unchecked")
        final ForState<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> lastForExecuteState =
                (ForState<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT>) lastStepExecuteState;

        @SuppressWarnings("unchecked")
        final ForState<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> nextForExecuteState =
                (ForState<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT>) nextStepExecuteState;

        final ComplexStmtState<?, ?, COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> lastBodyState;
        if ( lastForExecuteState != null )
        {
            lastBodyState = lastForExecuteState.bodyComplexState;
        }
        else
        {
            lastBodyState = null;
        }

        final ComplexStmtState<?, ?, COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> nextBodyState;
        if ( nextForExecuteState != null )
        {
            nextBodyState = nextForExecuteState.bodyComplexState;
        }
        else
        {
            nextBodyState = null;
        }

        final String initialStepStr;
        if ( initialStep instanceof ComplexStmt )
        {
            // TODO
            initialStepStr = "???";
        }
        else if ( initialStep instanceof SimpleStep )
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
                initialStepStr = tmpIndent + "   " + this.initialStep;
        }
        else
        {
            throw new IllegalStateException( String.valueOf( initialStep ) );
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

        final String updateStepStr;
        if ( updateStep instanceof ComplexStmt )
        {
            // TODO
            updateStepStr = "???";
        }
        else if ( updateStep instanceof SimpleStep )
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
                updateStepStr = tmpIndent + "   " + this.updateStep;
        }
        else
        {
            throw new IllegalStateException( String.valueOf( updateStep ) );
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
                initialStepStr + " ;\n" +
                conditionStr + " ;\n" +
                updateStepStr + " )\n" +
                variablesStr +
                this.bodyComplexStep.toString(
                        parent ,
                        indent + " " ,
                        lastBodyState ,
                        nextBodyState );
    }

}
