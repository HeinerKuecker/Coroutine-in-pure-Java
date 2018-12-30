package de.heinerkuecker.coroutine_iterator.step.complex;

import java.util.ArrayList;
import java.util.List;

import de.heinerkuecker.coroutine_iterator.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine_iterator.CoroutineIterator;
import de.heinerkuecker.coroutine_iterator.condition.Condition;
import de.heinerkuecker.coroutine_iterator.condition.ConditionOrBooleanExpression;
import de.heinerkuecker.coroutine_iterator.condition.True;
import de.heinerkuecker.coroutine_iterator.step.CoroIterStep;
import de.heinerkuecker.coroutine_iterator.step.flow.BreakOrContinue;
import de.heinerkuecker.coroutine_iterator.step.simple.NoOperation;
import de.heinerkuecker.coroutine_iterator.step.simple.SimpleStep;

public class For<RESULT /*, PARENT extends CoroutineIterator<RESULT>*/>
extends ComplexStep<
    For<RESULT/*, PARENT*/>,
    ForState<RESULT/*, PARENT*/>,
    RESULT
    //PARENT
    >
{
    public final String label;
    final CoroIterStep<RESULT/*, PARENT /*CoroutineIterator<RESULT>*/> initialStep;
    final ConditionOrBooleanExpression condition;
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
            this.initialStep = initialStep;

            if ( CoroutineIterator.initializationChecks &&
                    initialStep instanceof ComplexStep )
            {
                final List<BreakOrContinue<RESULT>> unresolvedBreaksOrContinues =
                        ( (ComplexStep<?, ?, RESULT/*, /*PARENT * / ? super CoroutineIterator<RESULT>*/>) initialStep ).getUnresolvedBreaksOrContinues();

                if ( ! unresolvedBreaksOrContinues.isEmpty() )
                {
                    throw new IllegalArgumentException(
                            "unpermitted breaks or continues in initial step" +
                            unresolvedBreaksOrContinues );
                }
            }
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
            this.updateStep = new NoOperation();
        }
        else if ( updateStep instanceof BreakOrContinue )
        {
            throw new IllegalArgumentException(
                    "break or continue in update step: " +
                            updateStep );
        }
        else
        {
            this.updateStep = updateStep;

            if ( CoroutineIterator.initializationChecks &&
                    updateStep instanceof ComplexStep )
            {
                final List<BreakOrContinue<RESULT>> unresolvedBreaksOrContinues =
                        ( (ComplexStep<?, ?, RESULT/*, PARENT /*? super CoroutineIterator<RESULT>*/>) updateStep ).getUnresolvedBreaksOrContinues();

                if ( ! unresolvedBreaksOrContinues.isEmpty() )
                {
                    throw new IllegalArgumentException(
                            "unpermitted breaks or continues in update step" +
                            unresolvedBreaksOrContinues );
                }
            }
        }

        if ( steps.length == 1 &&
                steps[ 0 ] instanceof ComplexStep )
        {
            this.bodyComplexStep =
                    (ComplexStep<?, ?, RESULT/*, PARENT /*CoroutineIterator<RESULT>*/>) steps[ 0 ];
        }
        else
        {
            this.bodyComplexStep =
                    //new StepSequence<RESULT, PARENT /*CoroutineIterator<RESULT>*/>(
                    new StepSequence(
                            // creationStackOffset
                            3 ,
                            steps );
        }
    }

    /**
     * Constructor.
     */
    @SafeVarargs
    public For(
            final String label ,
            final CoroIterStep<RESULT/*, PARENT /*CoroutineIterator<RESULT>*/> initialStep ,
            final Condition condition ,
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
            this.initialStep = initialStep;

            if ( CoroutineIterator.initializationChecks &&
                    initialStep instanceof ComplexStep )
            {
                final List<BreakOrContinue<RESULT>> unresolvedBreaksOrContinues =
                        ( (ComplexStep<?, ?, RESULT/*, /*PARENT * / ? super CoroutineIterator<RESULT>*/>) initialStep ).getUnresolvedBreaksOrContinues();

                if ( ! unresolvedBreaksOrContinues.isEmpty() )
                {
                    throw new IllegalArgumentException(
                            "unpermitted breaks or continues in initial step" +
                            unresolvedBreaksOrContinues );
                }
            }
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
            this.updateStep = updateStep;

            if ( CoroutineIterator.initializationChecks &&
                    updateStep instanceof ComplexStep )
            {
                final List<BreakOrContinue<RESULT>> unresolvedBreaksOrContinues =
                        ( (ComplexStep<?, ?, RESULT/*, PARENT /*? super CoroutineIterator<RESULT>*/>) updateStep ).getUnresolvedBreaksOrContinues();

                if ( ! unresolvedBreaksOrContinues.isEmpty() )
                {
                    throw new IllegalArgumentException(
                            "unpermitted breaks or continues in update step" +
                            unresolvedBreaksOrContinues );
                }
            }
        }

        if ( steps.length == 1 &&
                steps[ 0 ] instanceof ComplexStep )
        {
            this.bodyComplexStep =
                    (ComplexStep<?, ?, RESULT/*, PARENT /*CoroutineIterator<RESULT>*/>) steps[ 0 ];
        }
        else
        {
            this.bodyComplexStep =
                    //new StepSequence<RESULT, PARENT /*CoroutineIterator<RESULT>*/>(
                    new StepSequence(
                            // creationStackOffset
                            3 ,
                            steps );
        }
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
    public List<BreakOrContinue<RESULT>> getUnresolvedBreaksOrContinues()
    {
        final List<BreakOrContinue<RESULT>> result = new ArrayList<>();

        if ( initialStep instanceof ComplexStep )
        {
            result.addAll( ((ComplexStep) initialStep).getUnresolvedBreaksOrContinues() );
        }

        for ( BreakOrContinue<RESULT> unresolvedBreakOrContinue : bodyComplexStep.getUnresolvedBreaksOrContinues() )
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
            result.addAll( ((ComplexStep) updateStep).getUnresolvedBreaksOrContinues() );
        }

        return result;
    }

    ///**
    // * @see ComplexStep#setRootParent
    // */
    //@Override
    //public void setRootParent(
    //        final CoroutineIterator<RESULT> rootParent )
    //{
    //    this.bodyComplexStep.setRootParent( rootParent );
    //
    //    if ( this.initialStep instanceof ComplexStep )
    //    {
    //        ((ComplexStep) this.initialStep).setRootParent(rootParent);
    //    }
    //
    //    if ( this.updateStep instanceof ComplexStep )
    //    {
    //        ((ComplexStep) this.updateStep).setRootParent(rootParent);
    //    }
    //}

    /**
     * @see ComplexStep#toString
     */
    @Override
    public String toString(
            final String indent ,
            final ComplexStepState<?, ?, RESULT/*, PARENT*/> lastStepExecuteState ,
            final ComplexStepState<?, ?, RESULT/*, PARENT*/> nextStepExecuteState )
    {
        final ForState<RESULT/*, PARENT*/> lastForExecuteState =
                (ForState<RESULT/*, PARENT*/>) lastStepExecuteState;

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
                        indent + " " ,
                        lastBodyState ,
                        nextBodyState );
    }

}
