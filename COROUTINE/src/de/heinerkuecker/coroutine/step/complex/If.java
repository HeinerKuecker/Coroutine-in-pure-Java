package de.heinerkuecker.coroutine.step.complex;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import de.heinerkuecker.coroutine.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine.condition.ConditionOrBooleanExpression;
import de.heinerkuecker.coroutine.expression.GetProcedureArgument;
import de.heinerkuecker.coroutine.step.CoroIterStep;
import de.heinerkuecker.coroutine.step.flow.BreakOrContinue;

public class If<RESULT/*, PARENT extends CoroutineIterator<RESULT>*/>
extends ComplexStep<
    If<RESULT/*, PARENT*/>,
    IfState<RESULT/*, PARENT*/>,
    RESULT
    //PARENT
    >
{
    final ConditionOrBooleanExpression condition;
    final ComplexStep<?, ?, RESULT /*, PARENT/*CoroutineIterator<RESULT>*/> thenBodyComplexStep;

    /**
     * Constructor.
     */
    @SafeVarargs
    public If(
            final ConditionOrBooleanExpression condition ,
            final CoroIterStep<RESULT/*, ? super PARENT/*CoroutineIterator<RESULT>*/> ... steps )
    {
        super(
                //creationStackOffset
                3 );

        this.condition = condition;

        if ( steps.length == 1 &&
                steps[ 0 ] instanceof ComplexStep )
        {
            this.thenBodyComplexStep = (ComplexStep<?, ?, RESULT/*, PARENT/*? super CoroutineIterator<RESULT>*/>) steps[ 0 ];
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
    public IfState<RESULT/*, PARENT*/> newState(
            final CoroIteratorOrProcedure<RESULT> parent )
    {
        return new IfState<>(
                this ,
                parent.getRootParent() );
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
     * @see CoroIterStep#getProcedureArgumentGetsNotInProcedure()
     */
    @Override
    public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    {
        final List<GetProcedureArgument<?>> result = new ArrayList<>();

        result.addAll(
                condition.getProcedureArgumentGetsNotInProcedure() );

        result.addAll(
                thenBodyComplexStep.getProcedureArgumentGetsNotInProcedure() );

        return result;
    }

    /**
     * @see ComplexStep#checkLabelAlreadyInUse(Set)
     */
    @Override
    public void checkLabelAlreadyInUse(
            final Set<String> labels )
    {
        this.thenBodyComplexStep.checkLabelAlreadyInUse( labels );
    }

    ///**
    // * @see ComplexStep#setRootParent
    // */
    //@Override
    //public void setRootParent(
    //        final CoroutineIterator<RESULT> rootParent )
    //{
    //    this.thenBodyComplexStep.setRootParent( rootParent );
    //}

    /**
     * @see ComplexStep#toString
     */
    @Override
    public String toString(
            final String indent ,
            final ComplexStepState<?, /*STEP*/?, RESULT/*, PARENT*/> lastStepExecuteState ,
            final ComplexStepState<?, /*STEP*/?, RESULT/*, PARENT*/> nextStepExecuteState )
    {
        final IfState<RESULT/*, PARENT*/> lastIfExecuteState =
                (IfState<RESULT/*, PARENT*/>) lastStepExecuteState;

        final IfState<RESULT/*, PARENT*/> nextIfExecuteState =
                (IfState<RESULT/*, PARENT*/>) nextStepExecuteState;

        final ComplexStepState<?, ?, RESULT/*, PARENT*/> lastThenBodyState;
        if ( lastIfExecuteState != null )
        {
            lastThenBodyState = lastIfExecuteState.thenBodyComplexState;
        }
        else
        {
            lastThenBodyState = null;
        }

        final ComplexStepState<?, ?, RESULT/*, PARENT*/> nextThenBodyState;
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
