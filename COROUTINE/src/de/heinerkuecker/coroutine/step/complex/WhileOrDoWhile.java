package de.heinerkuecker.coroutine.step.complex;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import de.heinerkuecker.coroutine.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine.condition.ConditionOrBooleanExpression;
import de.heinerkuecker.coroutine.expression.GetProcedureArgument;
import de.heinerkuecker.coroutine.step.CoroIterStep;
import de.heinerkuecker.coroutine.step.flow.BreakOrContinue;
import de.heinerkuecker.coroutine.step.flow.exc.LabelAlreadyInUseException;

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
    final ConditionOrBooleanExpression condition;
    final ComplexStep<?, ?, RESULT /*, PARENT*/> bodyComplexStep;

    /**
     * Constructor.
     */
    @SafeVarargs
    WhileOrDoWhile(
            final String label ,
            final ConditionOrBooleanExpression condition ,
            final CoroIterStep<? extends RESULT /*, PARENT/*CoroutineIterator<RESULT>*/> ... steps )
    {
        super(
                //creationStackOffset
                4 );

        this.label = label;

        this.condition = Objects.requireNonNull( condition );

        this.bodyComplexStep =
                StepSequence.convertStepsToComplexStep(
                        // creationStackOffset
                        5 ,
                        steps );
    }

    /**
     * @see ComplexStep#getUnresolvedBreaksOrContinues()
     */
    @Override
    final public List<BreakOrContinue<RESULT>> getUnresolvedBreaksOrContinues(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroIteratorOrProcedure<RESULT> parent )
    {
        final List<BreakOrContinue<RESULT>> result = new ArrayList<>();

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

        this.bodyComplexStep.checkUseUndeclaredVariables(
                parent ,
                globalVariableTypes ,
                localVariableTypes );
    }

    /**
     * @see ComplexStep#toString
     */
    @Override
    final public String toString(
            final CoroIteratorOrProcedure<RESULT> parent ,
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
                        parent ,
                        indent + " " ,
                        lastBodyState ,
                        nextBodyState );
    }

}
