package de.heinerkuecker.coroutine_iterator.step.complex;

import java.util.List;

import de.heinerkuecker.coroutine_iterator.CoroutineIterator;
import de.heinerkuecker.coroutine_iterator.condition.Condition;
import de.heinerkuecker.coroutine_iterator.step.CoroIterStep;
import de.heinerkuecker.coroutine_iterator.step.flow.BreakOrContinue;

public class If<RESULT, PARENT extends CoroutineIterator<RESULT>>
extends ComplexStep<If<RESULT, PARENT>, IfState<RESULT, PARENT>, RESULT, PARENT>
{
    final Condition<? super PARENT/*? super CoroutineIterator<RESULT>*/> condition;
    final ComplexStep<?, ?, RESULT, PARENT/*CoroutineIterator<RESULT>*/> thenBodyComplexStep;

    /**
     * Constructor.
     */
    @SafeVarargs
    public If(
            final Condition<? super PARENT/*? super CoroutineIterator<RESULT>*/> condition ,
            final CoroIterStep<RESULT, ? super PARENT/*CoroutineIterator<RESULT>*/> ... steps )
    {
        super(
                //creationStackOffset
                3 );

        this.condition = condition;

        if ( steps.length == 1 &&
                steps[ 0 ] instanceof ComplexStep )
        {
            this.thenBodyComplexStep = (ComplexStep<?, ?, RESULT, PARENT/*? super CoroutineIterator<RESULT>*/>) steps[ 0 ];
        }
        else
        {
            this.thenBodyComplexStep =
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
    public IfState<RESULT, PARENT> newState()
    {
        return new IfState<>( this );
    }

    /**
     * @see ComplexStep#getUnresolvedBreaksOrContinues()
     */
    @Override
    public List<BreakOrContinue<RESULT>> getUnresolvedBreaksOrContinues()
    {
        return thenBodyComplexStep.getUnresolvedBreaksOrContinues();
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
        final IfState<RESULT, PARENT> lastIfExecuteState =
                (IfState<RESULT, PARENT>) lastStepExecuteState;

        final IfState<RESULT, PARENT> nextIfExecuteState =
                (IfState<RESULT, PARENT>) nextStepExecuteState;

        final ComplexStepState<?, ?, RESULT, PARENT> lastThenBodyState;
        if ( lastIfExecuteState != null )
        {
            lastThenBodyState = lastIfExecuteState.thenBodyComplexState;
        }
        else
        {
            lastThenBodyState = null;
        }

        final ComplexStepState<?, ?, RESULT, PARENT> nextThenBodyState;
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

        return
                indent +
                this.getClass().getSimpleName() + " (" +
                ( this.creationStackTraceElement != null ? " " + this.creationStackTraceElement : "" ) +
                "\n" +
                conditionStr + " )\n" +
                this.thenBodyComplexStep.toString(
                        indent + " " ,
                        lastThenBodyState ,
                        nextThenBodyState );
    }

}
