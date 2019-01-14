package de.heinerkuecker.coroutine.step.complex;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.heinerkuecker.coroutine.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine.condition.ConditionOrBooleanExpression;
import de.heinerkuecker.coroutine.condition.IsTrue;
import de.heinerkuecker.coroutine.expression.CoroExpression;
import de.heinerkuecker.coroutine.expression.GetProcedureArgument;
import de.heinerkuecker.coroutine.expression.Value;
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

        this.thenBodyComplexStep =
                StepSequence.convertStepsToComplexStep(
                        // creationStackOffset
                        4 ,
                        steps );
    }

    /**
     * Constructor.
     */
    @SafeVarargs
    public If(
            final CoroExpression<Boolean> condition ,
            final CoroIterStep<RESULT/*, ? super PARENT/*CoroutineIterator<RESULT>*/> ... steps )
    {
        super(
                //creationStackOffset
                3 );

        this.condition =
                new IsTrue(
                        condition );

        this.thenBodyComplexStep =
                StepSequence.convertStepsToComplexStep(
                        // creationStackOffset
                        4 ,
                        steps );
    }

    /**
     * Constructor.
     */
    @SafeVarargs
    public If(
            final Boolean condition ,
            final CoroIterStep<RESULT/*, ? super PARENT/*CoroutineIterator<RESULT>*/> ... steps )
    {
        super(
                //creationStackOffset
                3 );

        this.condition =
                new IsTrue(
                        new Value<Boolean>(
                                condition ) );

        this.thenBodyComplexStep =
                StepSequence.convertStepsToComplexStep(
                        // creationStackOffset
                        4 ,
                        steps );
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
        return thenBodyComplexStep.getUnresolvedBreaksOrContinues(
                alreadyCheckedProcedureNames ,
                parent );
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
     * @see CoroIterStep#setResultType(Class)
     */
    @Override
    public void setResultType(
            final Class<? extends RESULT> resultType )
    {
        this.thenBodyComplexStep.setResultType( resultType );
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
    }

    @Override
    public void checkUseUndeclaredVariables(
            final CoroIteratorOrProcedure<?> parent ,
            final Map<String, Class<?>> globalVariableTypes ,
            final Map<String, Class<?>> localVariableTypes )
    {
        this.condition.checkUseUndeclaredVariables(
                parent ,
                globalVariableTypes ,
                localVariableTypes );

        this.thenBodyComplexStep.checkUseUndeclaredVariables(
                parent ,
                globalVariableTypes ,
                localVariableTypes );
    }

    @Override
    public void checkUseUndeclaredParameters(
            final CoroIteratorOrProcedure<?> parent )
    {
        this.condition.checkUseUndeclaredParameters( parent );
        this.thenBodyComplexStep.checkUseUndeclaredParameters( parent );
    }

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
        @SuppressWarnings("unchecked")
        final IfState<RESULT/*, PARENT*/> lastIfExecuteState =
                (IfState<RESULT/*, PARENT*/>) lastStepExecuteState;

        @SuppressWarnings("unchecked")
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
                        parent ,
                        indent + " " ,
                        lastThenBodyState ,
                        nextThenBodyState );
    }

}
