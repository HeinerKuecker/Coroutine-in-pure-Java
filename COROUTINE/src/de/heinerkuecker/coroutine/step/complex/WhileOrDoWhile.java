package de.heinerkuecker.coroutine.step.complex;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep;
import de.heinerkuecker.coroutine.condition.ConditionOrBooleanExpression;
import de.heinerkuecker.coroutine.expression.GetProcedureArgument;
import de.heinerkuecker.coroutine.step.CoroIterStep;
import de.heinerkuecker.coroutine.step.flow.BreakOrContinue;
import de.heinerkuecker.coroutine.step.flow.exc.LabelAlreadyInUseException;

abstract public class WhileOrDoWhile<
    WHILE_OR_DO_WHILE extends WhileOrDoWhile<WHILE_OR_DO_WHILE, WHILE_OR_DO_WHILE_STATE, COROUTINE_RETURN /*, PARENT*/, RESUME_ARGUMENT>,
    WHILE_OR_DO_WHILE_STATE extends WhileOrDoWhileState<WHILE_OR_DO_WHILE, WHILE_OR_DO_WHILE_STATE, COROUTINE_RETURN /*, PARENT*/, RESUME_ARGUMENT>,
    COROUTINE_RETURN ,
    //PARENT extends CoroutineIterator<COROUTINE_RETURN>
    RESUME_ARGUMENT
    >
extends ComplexStep<
    /*WhileOrDoWhile<COROUTINE_RETURN, PARENT>*/WHILE_OR_DO_WHILE,
    /*WhileOrDoWhileState<WhileOrDoWhile<COROUTINE_RETURN, PARENT>, COROUTINE_RETURN, PARENT>*/WHILE_OR_DO_WHILE_STATE,
    COROUTINE_RETURN ,
    //PARENT
    RESUME_ARGUMENT
    >
{
    public final String label;
    final ConditionOrBooleanExpression condition;
    final ComplexStep<?, ?, COROUTINE_RETURN /*, PARENT*/, RESUME_ARGUMENT> bodyComplexStep;

    /**
     * Constructor.
     */
    @SafeVarargs
    WhileOrDoWhile(
            final String label ,
            final ConditionOrBooleanExpression condition ,
            final CoroIterStep<? extends COROUTINE_RETURN /*, PARENT/*CoroutineIterator<COROUTINE_RETURN>*/> ... steps )
    {
        super(
                //creationStackOffset
                4 );

        this.label = label;

        this.condition = Objects.requireNonNull( condition );

        this.bodyComplexStep =
                Block.convertStepsToComplexStep(
                        // creationStackOffset
                        5 ,
                        steps );
    }

    /**
     * @see ComplexStep#getUnresolvedBreaksOrContinues()
     */
    @Override
    final public List<BreakOrContinue<?, ?>> getUnresolvedBreaksOrContinues(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstep<COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        final List<BreakOrContinue<? , ?>> result = new ArrayList<>();

        for ( BreakOrContinue<? , ?> unresolvedBreakOrContinue : bodyComplexStep.getUnresolvedBreaksOrContinues(
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
            final Class<? extends COROUTINE_RETURN> resultType )
    {
        this.bodyComplexStep.setResultType( resultType );
    }

    /**
     * @see ComplexStep#checkLabelAlreadyInUse(Set)
     */
    @Override
    public void checkLabelAlreadyInUse(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstep<COROUTINE_RETURN, RESUME_ARGUMENT> parent ,
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
    public void checkUseVariables(
            //final boolean isCoroutineRoot ,
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstep<?, ?> parent ,
            final Map<String, Class<?>> globalVariableTypes, final Map<String, Class<?>> localVariableTypes )
    {
        this.condition.checkUseVariables(
                //isCoroutineRoot ,
                alreadyCheckedProcedureNames ,
                parent ,
                globalVariableTypes, localVariableTypes );

        this.bodyComplexStep.checkUseVariables(
                //isCoroutineRoot ,
                alreadyCheckedProcedureNames ,
                parent ,
                globalVariableTypes, localVariableTypes );
    }

    @Override
    public void checkUseArguments(
            HashSet<String> alreadyCheckedProcedureNames, final CoroutineOrProcedureOrComplexstep<?, ?> parent )
    {
        this.condition.checkUseArguments( alreadyCheckedProcedureNames, parent );
        this.bodyComplexStep.checkUseArguments( alreadyCheckedProcedureNames, parent );
    }

    /**
     * @see ComplexStep#toString
     */
    @Override
    final public String toString(
            final CoroutineOrProcedureOrComplexstep<COROUTINE_RETURN, RESUME_ARGUMENT> parent ,
            final String indent ,
            final ComplexStepState<?, ?, COROUTINE_RETURN /*, PARENT*/ , RESUME_ARGUMENT> lastStepExecuteState ,
            final ComplexStepState<?, ?, COROUTINE_RETURN /*, PARENT*/ , RESUME_ARGUMENT> nextStepExecuteState )
    {
        final WhileOrDoWhileState<?, ?, COROUTINE_RETURN /*, PARENT*/ , RESUME_ARGUMENT> lastWhileExecuteState =
                (WhileOrDoWhileState<?, ?, COROUTINE_RETURN /*, PARENT*/ , RESUME_ARGUMENT>) lastStepExecuteState;

        final WhileOrDoWhileState<?, ?, COROUTINE_RETURN /*, PARENT*/ , RESUME_ARGUMENT> nextWhileExecuteState =
                (WhileOrDoWhileState<?, ?, COROUTINE_RETURN /*, PARENT*/ , RESUME_ARGUMENT>) nextStepExecuteState;

        final ComplexStepState<?, ?, COROUTINE_RETURN /*, PARENT*/ , RESUME_ARGUMENT> lastBodyState;
        if ( lastWhileExecuteState != null )
        {
            lastBodyState = lastWhileExecuteState.bodyComplexState;
        }
        else
        {
            lastBodyState = null;
        }

        final ComplexStepState<?, ?, COROUTINE_RETURN /*, PARENT*/ , RESUME_ARGUMENT> nextBodyState;
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

        final String variablesStr;
        if ( nextWhileExecuteState == null )
        {
            variablesStr = "";
        }
        else
        {
            variablesStr =
                    nextWhileExecuteState.getVariablesStr(
                            indent );
        }

        return
                indent +
                ( this.label != null ? this.label + " : " : "" ) +
                this.getClass().getSimpleName() + " (" +
                ( this.creationStackTraceElement != null ? " " + this.creationStackTraceElement : "" ) +
                "\n" +
                conditionStr + " )\n" +
                variablesStr +
                this.bodyComplexStep.toString(
                        parent ,
                        indent + " " ,
                        lastBodyState ,
                        nextBodyState );
    }

}
