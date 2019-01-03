package de.heinerkuecker.coroutine.step.complex;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.heinerkuecker.coroutine.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine.condition.ConditionOrBooleanExpression;
import de.heinerkuecker.coroutine.expression.GetProcedureArgument;
import de.heinerkuecker.coroutine.step.CoroIterStep;
import de.heinerkuecker.coroutine.step.flow.BreakOrContinue;

public class IfElse<RESULT/*, PARENT extends CoroutineIterator<RESULT>*/>
extends ComplexStep<
    IfElse<RESULT/*, PARENT*/>,
    IfElseState<RESULT/*, PARENT*/>,
    RESULT
    //PARENT
    >
{
    final ConditionOrBooleanExpression condition;
    final ComplexStep<?, ?, RESULT/*, PARENT/*CoroutineIterator<RESULT>*/> thenBodyComplexStep;
    final ComplexStep<?, ?, RESULT/*, PARENT/*CoroutineIterator<RESULT>*/> elseBodyComplexStep;

    /**
     * Constructor.
     */
    public IfElse(
            final ConditionOrBooleanExpression condition ,
            final CoroIterStep<RESULT/*, CoroutineIterator<RESULT>*/>[] thenSteps ,
            final CoroIterStep<RESULT/*, CoroutineIterator<RESULT>*/>[] elseSteps )
    {
        super(
                //creationStackOffset
                3 );

        this.condition = condition;

        if ( thenSteps.length == 1 &&
                thenSteps[ 0 ] instanceof ComplexStep )
        {
            this.thenBodyComplexStep = (ComplexStep<?, ?, RESULT/*, PARENT/*? super CoroutineIterator<RESULT>*/>) thenSteps[ 0 ];
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
            this.elseBodyComplexStep = (ComplexStep<?, ?, RESULT/*, PARENT/*? super CoroutineIterator<RESULT>*/>) elseSteps[ 0 ];
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
    public IfElseState<RESULT/*, PARENT*/> newState(
            final CoroIteratorOrProcedure<RESULT> parent )
    {
        return new IfElseState<>(
                this ,
                parent );
    }

    /**
     * @see ComplexStep#getUnresolvedBreaksOrContinues()
     */
    @Override
    public List<BreakOrContinue<RESULT>> getUnresolvedBreaksOrContinues(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroIteratorOrProcedure<RESULT> parent )
    {
        final List<BreakOrContinue<RESULT>> result = new ArrayList<>();

        result.addAll(
                thenBodyComplexStep.getUnresolvedBreaksOrContinues(
                        alreadyCheckedProcedureNames ,
                        parent ) );

        result.addAll(
                elseBodyComplexStep.getUnresolvedBreaksOrContinues(
                        alreadyCheckedProcedureNames ,
                        parent ) );

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
                condition.getProcedureArgumentGetsNotInProcedure() );

        result.addAll(
                thenBodyComplexStep.getProcedureArgumentGetsNotInProcedure() );

        result.addAll(
                elseBodyComplexStep.getProcedureArgumentGetsNotInProcedure() );

        return result;
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
        this.thenBodyComplexStep.checkLabelAlreadyInUse(
                alreadyCheckedProcedureNames ,
                parent ,
                labels );

        this.elseBodyComplexStep.checkLabelAlreadyInUse(
                alreadyCheckedProcedureNames ,
                parent ,
                labels );
    }

    ///**
    // * @see ComplexStep#setRootParent
    // */
    //@Override
    //public void setRootParent(
    //        final CoroutineIterator<RESULT> rootParent )
    //{
    //    this.thenBodyComplexStep.setRootParent( rootParent );
    //    this.elseBodyComplexStep.setRootParent( rootParent );
    //}

    /**
     * @see ComplexStep#toString
     */
    @Override
    public String toString(
            final CoroIteratorOrProcedure<RESULT> parent ,
            final String indent ,
            final ComplexStepState<?, /*STEP*/?, RESULT/*, PARENT*/> lastStepExecuteState ,
            final ComplexStepState<?, /*STEP*/?, RESULT/*, PARENT*/> nextStepExecuteState )
    {
        final IfElseState<RESULT/*, PARENT*/> lastIfElseExecuteState =
                (IfElseState<RESULT/*, PARENT*/>) lastStepExecuteState;

        final IfElseState<RESULT/*, PARENT*/> nextIfElseExecuteState =
                (IfElseState<RESULT/*, PARENT*/>) nextStepExecuteState;

        final ComplexStepState<?, ?, RESULT/*, PARENT*/> lastThenBodyState;
        if ( lastIfElseExecuteState != null )
        {
            lastThenBodyState = lastIfElseExecuteState.thenBodyComplexState;
        }
        else
        {
            lastThenBodyState = null;
        }

        final ComplexStepState<?, ?, RESULT/*, PARENT*/> nextThenBodyState;
        if ( nextIfElseExecuteState != null )
        {
            nextThenBodyState = nextIfElseExecuteState.thenBodyComplexState;
        }
        else
        {
            nextThenBodyState = null;
        }

        final ComplexStepState<?, ?, RESULT/*, PARENT*/> lastElseBodyState;
        if ( lastIfElseExecuteState != null )
        {
            lastElseBodyState = lastIfElseExecuteState.elseBodyComplexState;
        }
        else
        {
            lastElseBodyState = null;
        }

        final ComplexStepState<?, ?, RESULT/*, PARENT*/> nextElseBodyState;
        if ( nextIfElseExecuteState != null )
        {
            nextElseBodyState = nextIfElseExecuteState.elseBodyComplexState;
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
                        parent ,
                        indent + " " ,
                        lastThenBodyState ,
                        nextThenBodyState ) +
                indent + "else\n" +
                this.elseBodyComplexStep.toString(
                        parent ,
                        indent + " " ,
                        lastElseBodyState ,
                        nextElseBodyState );
    }

}