package de.heinerkuecker.coroutine_iterator.step.complex;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.heinerkuecker.coroutine_iterator.condition.Condition;
import de.heinerkuecker.coroutine_iterator.step.CoroIterStep;
import de.heinerkuecker.coroutine_iterator.step.flow.BreakOrContinue;

abstract public class WhileOrDoWhile<
    WHILE_OR_DO_WHILE extends WhileOrDoWhile<WHILE_OR_DO_WHILE, WHILE_OR_DO_WHILE_STATE, RESULT /*, PARENT*/>,
    WHILE_OR_DO_WHILE_STATE extends WhileOrDoWhileState<WHILE_OR_DO_WHILE, WHILE_OR_DO_WHILE_STATE, RESULT /*, PARENT*/>,
    RESULT
    //PARENT extends CoroutineIterator<RESULT>
    >
extends ComplexStep<
    /*WhileOrDoWhile<RESULT, PARENT>*/WHILE_OR_DO_WHILE,
    /*WhileOrDoWhileState<WhileOrDoWhile<RESULT, PARENT>, RESULT, PARENT>*/WHILE_OR_DO_WHILE_STATE,
    RESULT
    //PARENT
    >
{
    public final String label;
    final Condition condition;
    final ComplexStep<?, ?, RESULT /*, PARENT*/> bodyComplexStep;

    ///**
    // * Constructor.
    // */
    //@SafeVarargs
    //public WhileOrDoWhile(
    //        final Condition<? super PARENT> condition ,
    //        final CoroIterStep<? super RESULT, PARENT> ... steps )
    //{
    //    super(
    //            //creationStackOffset
    //            3 );
    //
    //    this.label = null;
    //
    //    this.condition = Objects.requireNonNull( condition );
    //
    //    if ( steps.length == 1 &&
    //            steps[ 0 ] instanceof ComplexStep )
    //    {
    //        this.bodyComplexStep =
    //                (ComplexStep<?, ?, RESULT, PARENT>) steps[ 0 ];
    //    }
    //    else
    //    {
    //        this.bodyComplexStep =
    //                new StepSequence(
    //                        // creationStackOffset
    //                        3 ,
    //                        steps );
    //    }
    //}

    /**
     * Constructor.
     */
    @SafeVarargs
    public WhileOrDoWhile(
            final String label ,
            final Condition condition ,
            final CoroIterStep<? extends RESULT /*, PARENT/*CoroutineIterator<RESULT>*/> ... steps )
    {
        super(
                //creationStackOffset
                4 );

        this.label = label;

        this.condition = Objects.requireNonNull( condition );

        if ( steps.length == 1 &&
                steps[ 0 ] instanceof ComplexStep )
        {
            this.bodyComplexStep = (ComplexStep<?, ?, RESULT /*, PARENT/*? super CoroutineIterator<RESULT>*/>) steps[ 0 ];
        }
        else
        {
            this.bodyComplexStep =
                    new StepSequence(
                            // creationStackOffset
                            4 ,
                            steps );
        }
    }

    ///**
    // * @see ComplexStep#newState()
    // */
    //@Override
    //public WhileState<RESULT, PARENT> newState()
    //{
    //    return new WhileState<>( this );
    //}

    /**
     * @see ComplexStep#getUnresolvedBreaksOrContinues()
     */
    @Override
    final public List<BreakOrContinue<RESULT>> getUnresolvedBreaksOrContinues()
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

    ///**
    // * @see ComplexStep#setRootParent
    // */
    //@Override
    //public void setRootParent(
    //        final CoroutineIterator<RESULT> rootParent )
    //{
    //    this.bodyComplexStep.setRootParent( rootParent );
    //}

    /**
     * @see ComplexStep#toString
     */
    @Override
    final public String toString(
            final String indent ,
            final ComplexStepState<?, ?, RESULT /*, PARENT*/> lastStepExecuteState ,
            final ComplexStepState<?, ?, RESULT /*, PARENT*/> nextStepExecuteState )
    {
        final WhileOrDoWhileState<?, ?, RESULT /*, PARENT*/> lastWhileExecuteState =
                (WhileOrDoWhileState<?, ?, RESULT /*, PARENT*/>) lastStepExecuteState;

        final WhileOrDoWhileState<?, ?, RESULT /*, PARENT*/> nextWhileExecuteState =
                (WhileOrDoWhileState<?, ?, RESULT /*, PARENT*/>) nextStepExecuteState;

        final ComplexStepState<?, ?, RESULT /*, PARENT*/> lastBodyState;
        if ( lastWhileExecuteState != null )
        {
            lastBodyState = lastWhileExecuteState.bodyComplexState;
        }
        else
        {
            lastBodyState = null;
        }

        final ComplexStepState<?, ?, RESULT /*, PARENT*/> nextBodyState;
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
