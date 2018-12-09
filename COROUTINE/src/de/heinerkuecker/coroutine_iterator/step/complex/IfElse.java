package de.heinerkuecker.coroutine_iterator.step.complex;

import java.util.ArrayList;
import java.util.List;

import de.heinerkuecker.coroutine_iterator.CoroutineIterator;
import de.heinerkuecker.coroutine_iterator.condition.Condition;
import de.heinerkuecker.coroutine_iterator.step.CoroIterStep;
import de.heinerkuecker.coroutine_iterator.step.flow.BreakOrContinue;

public class IfElse<RESULT, PARENT extends CoroutineIterator<RESULT>>
//extends ComplexStep<IfElse<RESULT, PARENT>, RESULT, PARENT>
extends ComplexStep<IfElse<RESULT, PARENT>, IfElseState<RESULT, PARENT>, RESULT, PARENT>
{
    final Condition<? super PARENT/*? super CoroutineIterator<RESULT>*/> condition;
    final ComplexStep<?, ?, RESULT, PARENT/*CoroutineIterator<RESULT>*/> thenBodyComplexStep;
    final ComplexStep<?, ?, RESULT, PARENT/*CoroutineIterator<RESULT>*/> elseBodyComplexStep;

    /**
     * Constructor.
     */
    public IfElse(
            final Condition<? super CoroutineIterator<RESULT>> condition ,
            final CoroIterStep<RESULT, CoroutineIterator<RESULT>>[] thenSteps ,
            final CoroIterStep<RESULT, CoroutineIterator<RESULT>>[] elseSteps )
    {
        super(
                //creationStackOffset
                3 );

        this.condition = condition;

        if ( thenSteps.length == 1 &&
                thenSteps[ 0 ] instanceof ComplexStep )
        {
            this.thenBodyComplexStep = (ComplexStep<?, ?, RESULT, PARENT/*? super CoroutineIterator<RESULT>*/>) thenSteps[ 0 ];
        }
        else
        {
            this.thenBodyComplexStep =
                    new StepSequence(
                            // creationStackOffset
                            3 ,
                            thenSteps );
        }

        if ( elseSteps.length == 1 &&
                elseSteps[ 0 ] instanceof ComplexStep )
        {
            this.elseBodyComplexStep = (ComplexStep<?, ?, RESULT, PARENT/*? super CoroutineIterator<RESULT>*/>) elseSteps[ 0 ];
        }
        else
        {
            this.elseBodyComplexStep =
                    new StepSequence(
                            // creationStackOffset
                            3 ,
                            elseSteps );
        }

    }

    /**
     * @see ComplexStep#newState()
     */
    @Override
    public IfElseState<RESULT, PARENT> newState()
    {
        return new IfElseState<>( this );
    }

    /**
     * @see ComplexStep#getUnresolvedBreaksOrContinues()
     */
    @Override
    public List<BreakOrContinue<RESULT>> getUnresolvedBreaksOrContinues()
    {
        final List<BreakOrContinue<RESULT>> result = new ArrayList<>();

        result.addAll(
                thenBodyComplexStep.getUnresolvedBreaksOrContinues() );

        result.addAll(
                elseBodyComplexStep.getUnresolvedBreaksOrContinues() );

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
        //return
        //        indent + this.getClass().getSimpleName() + " ( " + this.condition + " ) \n" +
        //        this.thenBodySequence.toString(
        //                indent + " " ,
        //                //isThenCurrentExecuted &&
        //                runInThenBody ) +
        //        indent + "else\n" +
        //        this.elseBodySequence.toString(
        //                indent + " " ,
        //                //isElseCurrentExecuted &&
        //                runInElseBody );
        final IfElseState<RESULT, PARENT> lastIfElseExecuteState =
                (IfElseState<RESULT, PARENT>) lastStepExecuteState;

        final IfElseState<RESULT, PARENT> nextIfElseExecuteState =
                (IfElseState<RESULT, PARENT>) nextStepExecuteState;

        final ComplexStepState<?, ?, RESULT, PARENT> lastThenBodyState;
        if ( lastIfElseExecuteState != null )
        {
            lastThenBodyState = lastIfElseExecuteState.thenBodyComplexState;
        }
        else
        {
            lastThenBodyState = null;
        }

        final ComplexStepState<?, ?, RESULT, PARENT> nextThenBodyState;
        if ( nextIfElseExecuteState != null )
        {
            nextThenBodyState = nextIfElseExecuteState.thenBodyComplexState;
        }
        else
        {
            nextThenBodyState = null;
        }

        final ComplexStepState<?, ?, RESULT, PARENT> lastElseBodyState;
        if ( lastIfElseExecuteState != null )
        {
            lastElseBodyState = lastIfElseExecuteState.thenBodyComplexState;
        }
        else
        {
            lastElseBodyState = null;
        }

        final ComplexStepState<?, ?, RESULT, PARENT> nextElseBodyState;
        if ( nextIfElseExecuteState != null )
        {
            nextElseBodyState = nextIfElseExecuteState.thenBodyComplexState;
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

        return
                indent +
                this.getClass().getSimpleName() + " (" +
                ( this.creationStackTraceElement != null ? " " + this.creationStackTraceElement : "" ) +
                "\n" +
                conditionStr + " )\n" +
                this.thenBodyComplexStep.toString(
                        indent + " " ,
                        lastThenBodyState ,
                        nextThenBodyState ) +
                indent + "else\n" +
                this.elseBodyComplexStep.toString(
                        indent + " " ,
                        lastElseBodyState ,
                        nextElseBodyState );
    }

}
