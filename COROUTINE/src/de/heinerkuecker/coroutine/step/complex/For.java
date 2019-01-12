package de.heinerkuecker.coroutine.step.complex;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import de.heinerkuecker.coroutine.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine.condition.ConditionOrBooleanExpression;
import de.heinerkuecker.coroutine.condition.IsTrue;
import de.heinerkuecker.coroutine.condition.True;
import de.heinerkuecker.coroutine.expression.CoroExpression;
import de.heinerkuecker.coroutine.expression.GetProcedureArgument;
import de.heinerkuecker.coroutine.step.CoroIterStep;
import de.heinerkuecker.coroutine.step.flow.BreakOrContinue;
import de.heinerkuecker.coroutine.step.flow.exc.LabelAlreadyInUseException;
import de.heinerkuecker.coroutine.step.simple.NoOperation;
import de.heinerkuecker.coroutine.step.simple.SimpleStep;

public class For<RESULT /*, PARENT extends CoroutineIterator<RESULT>*/>
extends ComplexStep<
    For<RESULT/*, PARENT*/>,
    ForState<RESULT/*, PARENT*/>,
    RESULT
    //PARENT
    >
{
    public final String label;

    // TODO nur SimpleStep oder ProcedureCall erlauben
    final CoroIterStep<RESULT/*, PARENT /*CoroutineIterator<RESULT>*/> initialStep;

    final ConditionOrBooleanExpression condition;

    // TODO nur SimpleStep oder ProcedureCall erlauben
    final CoroIterStep<RESULT/*, PARENT /*CoroutineIterator<RESULT>*/> updateStep;

    final ComplexStep<?, ?, RESULT/*, PARENT /*CoroutineIterator<RESULT>*/> bodyComplexStep;

    /**
     * Constructor.
     */
    @SafeVarargs
    public For(
            final CoroIterStep<RESULT/*, PARENT /*CoroutineIterator<RESULT>*/> initialStep ,
            final ConditionOrBooleanExpression condition ,
            final CoroIterStep<RESULT/*, PARENT /*CoroutineIterator<RESULT>*/> updateStep ,
            final CoroIterStep<RESULT/*, PARENT /*CoroutineIterator<RESULT>*/>... steps )
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
            this.condition = new True();
        }
        else
        {
            this.condition = condition;
        }

        if ( updateStep == null )
        {
            // C style default
            this.updateStep = new NoOperation<RESULT>();
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
                StepSequence.convertStepsToComplexStep(
                        // creationStackOffset
                        4 ,
                        steps );
    }

    /**
     * Constructor.
     */
    @SafeVarargs
    public For(
            final CoroIterStep<RESULT/*, PARENT /*CoroutineIterator<RESULT>*/> initialStep ,
            final CoroExpression<Boolean> condition ,
            final CoroIterStep<RESULT/*, PARENT /*CoroutineIterator<RESULT>*/> updateStep ,
            final CoroIterStep<RESULT/*, PARENT /*CoroutineIterator<RESULT>*/>... steps )
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
            this.condition = new True();
        }
        else
        {
            this.condition =
                    new IsTrue(
                            condition );
        }

        if ( updateStep == null )
        {
            // C style default
            this.updateStep = new NoOperation<RESULT>();
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
                StepSequence.convertStepsToComplexStep(
                        // creationStackOffset
                        4 ,
                        steps );
    }

    /**
     * Constructor.
     */
    @SafeVarargs
    public For(
            final String label ,
            final CoroIterStep<RESULT/*, PARENT /*CoroutineIterator<RESULT>*/> initialStep ,
            final ConditionOrBooleanExpression condition ,
            final CoroIterStep<RESULT/*, PARENT /*CoroutineIterator<RESULT>*/> updateStep ,
            final CoroIterStep<RESULT/*, PARENT /*CoroutineIterator<RESULT>*/>... steps )
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
            this.condition = new True();
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
                StepSequence.convertStepsToComplexStep(
                        // creationStackOffset
                        4 ,
                        steps );
    }

    /**
     * Constructor.
     */
    @SafeVarargs
    public For(
            final String label ,
            final CoroIterStep<RESULT/*, PARENT /*CoroutineIterator<RESULT>*/> initialStep ,
            final CoroExpression<Boolean> condition ,
            final CoroIterStep<RESULT/*, PARENT /*CoroutineIterator<RESULT>*/> updateStep ,
            final CoroIterStep<RESULT/*, PARENT /*CoroutineIterator<RESULT>*/>... steps )
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
            this.condition = new True();
        }
        else
        {
            this.condition =
                    new IsTrue(
                            condition );
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
                StepSequence.convertStepsToComplexStep(
                        // creationStackOffset
                        4 ,
                        steps );
    }

    /**
     * @see ComplexStep#newState
     */
    @Override
    public ForState<RESULT/*, PARENT*/> newState(
            final CoroIteratorOrProcedure<RESULT> parent )
    {
        return new ForState<>(
                this ,
                parent.getRootParent() );
    }

    /**
     * @see ComplexStep#getUnresolvedBreaksOrContinues()
     */
    @Override
    public List<BreakOrContinue<RESULT>> getUnresolvedBreaksOrContinues(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroIteratorOrProcedure<RESULT> parent )
    {
        if ( initialStep instanceof ComplexStep )
        {
            final List<BreakOrContinue<RESULT>> unresolvedBreaksOrContinues =
                    ( (ComplexStep<?, ?, RESULT/*, /*PARENT * / ? super CoroutineIterator<RESULT>*/>) initialStep ).getUnresolvedBreaksOrContinues(
                            alreadyCheckedProcedureNames ,
                            parent );

            if ( ! unresolvedBreaksOrContinues.isEmpty() )
            {
                throw new IllegalArgumentException(
                        "unpermitted breaks or continues in initial step" +
                        unresolvedBreaksOrContinues );
            }
        }

        if ( updateStep instanceof ComplexStep )
        {
            final List<BreakOrContinue<RESULT>> unresolvedBreaksOrContinues =
                    ( (ComplexStep<?, ?, RESULT/*, PARENT /*? super CoroutineIterator<RESULT>*/>) updateStep ).getUnresolvedBreaksOrContinues(
                            alreadyCheckedProcedureNames ,
                            parent );

            if ( ! unresolvedBreaksOrContinues.isEmpty() )
            {
                throw new IllegalArgumentException(
                        "unpermitted breaks or continues in update step" +
                        unresolvedBreaksOrContinues );
            }
        }

        final List<BreakOrContinue<RESULT>> result = new ArrayList<>();

        if ( initialStep instanceof ComplexStep )
        {
            result.addAll(
                    ( (ComplexStep<?, ?, RESULT>) initialStep ).getUnresolvedBreaksOrContinues(
                            alreadyCheckedProcedureNames ,
                            parent ) );
        }

        for ( BreakOrContinue<RESULT> unresolvedBreakOrContinue : bodyComplexStep.getUnresolvedBreaksOrContinues(
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

        if ( updateStep instanceof ComplexStep )
        {
            result.addAll(
                    ( (ComplexStep<?, ?, RESULT>) updateStep ).getUnresolvedBreaksOrContinues(
                            alreadyCheckedProcedureNames ,
                            parent ) );
        }

        return result;
    }

    /**
     * @see CoroIterStep#getProcedureArgumentGetsNotInProcedure()
     */
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

    /**
     * @see CoroIterStep#setResultType(Class)
     */
    @Override
    public void setResultType(
            final Class<? extends RESULT> resultType )
    {
        this.bodyComplexStep.setResultType( resultType );
    }

    /**
     * @see ComplexStep#checkLabelAlreadyInUse(Set)
     */
    @Override
    public void checkLabelAlreadyInUse(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroIteratorOrProcedure<RESULT> parent ,
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

    /**
     * @see ComplexStep#toString
     */
    @Override
    public String toString(
            final CoroIteratorOrProcedure<RESULT> parent ,
            final String indent ,
            ComplexStepState<?, ?, RESULT> lastStepExecuteState ,
            ComplexStepState<?, ?, RESULT> nextStepExecuteState )
    {
        @SuppressWarnings("unchecked")
        final ForState<RESULT/*, PARENT*/> lastForExecuteState =
                (ForState<RESULT/*, PARENT*/>) lastStepExecuteState;

        @SuppressWarnings("unchecked")
        final ForState<RESULT/*, PARENT*/> nextForExecuteState =
                (ForState<RESULT/*, PARENT*/>) nextStepExecuteState;

        final ComplexStepState<?, ?, RESULT/*, PARENT*/> lastBodyState;
        if ( lastForExecuteState != null )
        {
            lastBodyState = lastForExecuteState.bodyComplexState;
        }
        else
        {
            lastBodyState = null;
        }

        final ComplexStepState<?, ?, RESULT/*, PARENT*/> nextBodyState;
        if ( nextForExecuteState != null )
        {
            nextBodyState = nextForExecuteState.bodyComplexState;
        }
        else
        {
            nextBodyState = null;
        }

        final String initialStepStr;
        if ( initialStep instanceof ComplexStep )
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
        if ( updateStep instanceof ComplexStep )
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

        return
                indent +
                ( this.label != null ? this.label + " : " : "" ) +
                this.getClass().getSimpleName() + " (" +
                ( this.creationStackTraceElement != null ? " " + this.creationStackTraceElement : "" ) +
                "\n" +
                initialStepStr + " ;\n" +
                conditionStr + " ;\n" +
                updateStepStr + " )\n" +
                this.bodyComplexStep.toString(
                        parent ,
                        indent + " " ,
                        lastBodyState ,
                        nextBodyState );
    }

}
