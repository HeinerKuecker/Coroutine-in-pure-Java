package de.heinerkuecker.coroutine_iterator.step.complex;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.heinerkuecker.coroutine_iterator.CoroutineIterator;
import de.heinerkuecker.coroutine_iterator.condition.Condition;
import de.heinerkuecker.coroutine_iterator.step.CoroIterStep;
import de.heinerkuecker.coroutine_iterator.step.flow.BreakOrContinue;

public class While<RESULT, PARENT extends CoroutineIterator<RESULT>>
//extends ComplexStep<While<RESULT, PARENT>, RESULT, PARENT>
extends ComplexStep<While<RESULT, PARENT>, WhileState<RESULT, PARENT>, RESULT, PARENT>
{
    public final String label;
    final Condition<? super PARENT/*? super CoroutineIterator<RESULT>*/> condition;
    final ComplexStep<?, ?, RESULT, PARENT/*CoroutineIterator<RESULT>*/> bodyComplexStep;

    /**
     * Constructor.
     */
    @SafeVarargs
    public While(
            final Condition<? super PARENT/*? super CoroutineIterator<RESULT>*/> condition ,
            final CoroIterStep<? super RESULT, PARENT/*CoroutineIterator<RESULT>*/> ... steps )
    {
        super(
                //creationStackOffset
                3 );

        this.label = null;

        this.condition = Objects.requireNonNull( condition );

        if ( steps.length == 1 &&
                steps[ 0 ] instanceof ComplexStep )
        {
            this.bodyComplexStep = (ComplexStep<?, ?, RESULT, PARENT/*? super CoroutineIterator<RESULT>*/>) steps[ 0 ];
        }
        else
        {
            this.bodyComplexStep =
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
    public While(
            final String label ,
            final Condition<? super PARENT/*? super CoroutineIterator<RESULT>*/> condition ,
            final CoroIterStep<? super RESULT, PARENT/*CoroutineIterator<RESULT>*/> ... steps )
    {
        super(
                //creationStackOffset
                3 );

        this.label = label;

        this.condition = Objects.requireNonNull( condition );

        if ( steps.length == 1 &&
                steps[ 0 ] instanceof ComplexStep )
        {
            this.bodyComplexStep = (ComplexStep<?, ?, RESULT, PARENT/*? super CoroutineIterator<RESULT>*/>) steps[ 0 ];
        }
        else
        {
            this.bodyComplexStep =
                    new StepSequence(
                            // creationStackOffset
                            3 ,
                            steps );
        }
    }

    /**
     * @see ComplexStep#newState()
     */
    @Override
    public WhileState<RESULT, PARENT> newState()
    {
        return new WhileState<>( this );
    }

    /**
     * @see ComplexStep#getUnresolvedBreaksOrContinues()
     */
    @Override
    public List<BreakOrContinue<RESULT>> getUnresolvedBreaksOrContinues()
    {
        final List<BreakOrContinue<RESULT>> result = new ArrayList<>();

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

        return result;
    }

    /**
     * @see ComplexStep#toString
     */
    @Override
    public String toString(
            final String indent ,
            final ComplexStepState<?, /*STEP*/?, RESULT, PARENT> lastStepExecuteState ,
            final ComplexStepState<?, /*STEP*/?, RESULT, PARENT> nextStepExecuteState )
    {
        final WhileState<RESULT, PARENT> lastWhileExecuteState =
                (WhileState<RESULT, PARENT>) lastStepExecuteState;

        final WhileState<RESULT, PARENT> nextWhileExecuteState =
                (WhileState<RESULT, PARENT>) nextStepExecuteState;

        final ComplexStepState<?, ?, RESULT, PARENT> lastBodyState;
        if ( lastWhileExecuteState != null )
        {
            lastBodyState = lastWhileExecuteState.bodyComplexState;
        }
        else
        {
            lastBodyState = null;
        }

        final ComplexStepState<?, ?, RESULT, PARENT> nextBodyState;
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

        return
                indent +
                ( this.label != null ? this.label + " : " : "" ) +
                this.getClass().getSimpleName() + " (" +
                ( this.creationStackTraceElement != null ? " " + this.creationStackTraceElement : "" ) +
                "\n" +
                conditionStr + " )\n" +
                this.bodyComplexStep.toString(
                        indent + " " ,
                        lastBodyState ,
                        nextBodyState );
    }

}
