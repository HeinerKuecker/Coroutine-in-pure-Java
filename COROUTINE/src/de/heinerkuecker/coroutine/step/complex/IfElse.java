package de.heinerkuecker.coroutine.step.complex;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep;
import de.heinerkuecker.coroutine.condition.ConditionOrBooleanExpression;
import de.heinerkuecker.coroutine.condition.IsTrue;
import de.heinerkuecker.coroutine.expression.CoroExpression;
import de.heinerkuecker.coroutine.expression.GetProcedureArgument;
import de.heinerkuecker.coroutine.expression.Value;
import de.heinerkuecker.coroutine.step.CoroIterStep;
import de.heinerkuecker.coroutine.step.flow.BreakOrContinue;

public class IfElse<RESULT/*, PARENT extends CoroutineIterator<RESULT>*/ , RESUME_ARGUMENT>
extends ComplexStep<
    IfElse<RESULT/*, PARENT*/ , RESUME_ARGUMENT>,
    IfElseState<RESULT/*, PARENT*/ , RESUME_ARGUMENT>,
    RESULT ,
    //PARENT
    RESUME_ARGUMENT
    >
{
    final ConditionOrBooleanExpression condition;
    final ComplexStep<?, ?, RESULT/*, PARENT/*CoroutineIterator<RESULT>*/ , RESUME_ARGUMENT> thenBodyComplexStep;
    final ComplexStep<?, ?, RESULT/*, PARENT/*CoroutineIterator<RESULT>*/ , RESUME_ARGUMENT> elseBodyComplexStep;

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
            this.thenBodyComplexStep =
                    (ComplexStep<?, ?, RESULT/*, PARENT/*? super CoroutineIterator<RESULT>*/, RESUME_ARGUMENT>) thenSteps[ 0 ];
        }
        else
        {
            this.thenBodyComplexStep =
                    new Block<>(
                            // creationStackOffset
                            3 ,
                            thenSteps );
        }

        if ( elseSteps.length == 1 &&
                elseSteps[ 0 ] instanceof ComplexStep )
        {
            this.elseBodyComplexStep =
                    (ComplexStep<?, ?, RESULT/*, PARENT/*? super CoroutineIterator<RESULT>*/ , RESUME_ARGUMENT>) elseSteps[ 0 ];
        }
        else
        {
            this.elseBodyComplexStep =
                    new Block<>(
                            // creationStackOffset
                            3 ,
                            elseSteps );
        }

    }

    /**
     * Constructor.
     */
    public IfElse(
            final CoroExpression<Boolean> condition ,
            final CoroIterStep<RESULT/*, CoroutineIterator<RESULT>*/>[] thenSteps ,
            final CoroIterStep<RESULT/*, CoroutineIterator<RESULT>*/>[] elseSteps )
    {
        super(
                //creationStackOffset
                3 );

        this.condition =
                new IsTrue(
                        condition );

        if ( thenSteps.length == 1 &&
                thenSteps[ 0 ] instanceof ComplexStep )
        {
            this.thenBodyComplexStep =
                    (ComplexStep<?, ?, RESULT/*, PARENT/*? super CoroutineIterator<RESULT>*/ , RESUME_ARGUMENT>) thenSteps[ 0 ];
        }
        else
        {
            this.thenBodyComplexStep =
                    new Block<>(
                            // creationStackOffset
                            3 ,
                            thenSteps );
        }

        if ( elseSteps.length == 1 &&
                elseSteps[ 0 ] instanceof ComplexStep )
        {
            this.elseBodyComplexStep =
                    (ComplexStep<?, ?, RESULT/*, PARENT/*? super CoroutineIterator<RESULT>*/ , RESUME_ARGUMENT>) elseSteps[ 0 ];
        }
        else
        {
            this.elseBodyComplexStep =
                    new Block<>(
                            // creationStackOffset
                            3 ,
                            elseSteps );
        }

    }

    /**
     * Constructor.
     */
    public IfElse(
            final boolean condition ,
            final CoroIterStep<RESULT/*, CoroutineIterator<RESULT>*/>[] thenSteps ,
            final CoroIterStep<RESULT/*, CoroutineIterator<RESULT>*/>[] elseSteps )
    {
        super(
                //creationStackOffset
                3 );

        this.condition =
                new IsTrue(
                        Value.booleanValue(
                                condition ) );

        if ( thenSteps.length == 1 &&
                thenSteps[ 0 ] instanceof ComplexStep )
        {
            this.thenBodyComplexStep =
                    (ComplexStep<?, ?, RESULT/*, PARENT/*? super CoroutineIterator<RESULT>*/ , RESUME_ARGUMENT>) thenSteps[ 0 ];
        }
        else
        {
            this.thenBodyComplexStep =
                    new Block<>(
                            // creationStackOffset
                            3 ,
                            thenSteps );
        }

        if ( elseSteps.length == 1 &&
                elseSteps[ 0 ] instanceof ComplexStep )
        {
            this.elseBodyComplexStep =
                    (ComplexStep<?, ?, RESULT/*, PARENT/*? super CoroutineIterator<RESULT>*/ , RESUME_ARGUMENT>) elseSteps[ 0 ];
        }
        else
        {
            this.elseBodyComplexStep =
                    new Block<>(
                            // creationStackOffset
                            3 ,
                            elseSteps );
        }

    }

    /**
     * @see ComplexStep#newState()
     */
    @Override
    public IfElseState<RESULT/*, PARENT*/ , RESUME_ARGUMENT> newState(
            final CoroutineOrProcedureOrComplexstep<RESULT, RESUME_ARGUMENT> parent )
    {
        return new IfElseState<>(
                this ,
                parent );
    }

    /**
     * @see ComplexStep#getUnresolvedBreaksOrContinues()
     */
    @Override
    public List<BreakOrContinue<? , ?>> getUnresolvedBreaksOrContinues(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstep<RESULT, RESUME_ARGUMENT> parent )
    {
        final List<BreakOrContinue<? , ?>> result = new ArrayList<>();

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
     * @see CoroIterStep#setResultType(Class)
     */
    @Override
    public void setResultType(
            final Class<? extends RESULT> resultType )
    {
        this.thenBodyComplexStep.setResultType( resultType );
        this.elseBodyComplexStep.setResultType( resultType );
    }

    @Override
    public void checkLabelAlreadyInUse(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstep<RESULT, RESUME_ARGUMENT> parent ,
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

        this.thenBodyComplexStep.checkUseVariables(
                //isCoroutineRoot ,
                alreadyCheckedProcedureNames ,
                parent ,
                globalVariableTypes, localVariableTypes );

        this.elseBodyComplexStep.checkUseVariables(
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
        this.thenBodyComplexStep.checkUseArguments( alreadyCheckedProcedureNames, parent );
        this.elseBodyComplexStep.checkUseArguments( alreadyCheckedProcedureNames, parent );
    }

    /**
     * @see ComplexStep#toString
     */
    @Override
    public String toString(
            final CoroutineOrProcedureOrComplexstep<RESULT, RESUME_ARGUMENT> parent ,
            final String indent ,
            final ComplexStepState<?, /*STEP*/?, RESULT/*, PARENT*/ , RESUME_ARGUMENT> lastStepExecuteState ,
            final ComplexStepState<?, /*STEP*/?, RESULT/*, PARENT*/ , RESUME_ARGUMENT> nextStepExecuteState )
    {
        @SuppressWarnings("unchecked")
        final IfElseState<RESULT/*, PARENT*/ , RESUME_ARGUMENT> lastIfElseExecuteState =
                (IfElseState<RESULT/*, PARENT*/ , RESUME_ARGUMENT>) lastStepExecuteState;

        @SuppressWarnings("unchecked")
        final IfElseState<RESULT/*, PARENT*/ , RESUME_ARGUMENT> nextIfElseExecuteState =
                (IfElseState<RESULT/*, PARENT*/ , RESUME_ARGUMENT>) nextStepExecuteState;

        final ComplexStepState<?, ?, RESULT/*, PARENT*/ , RESUME_ARGUMENT> lastThenBodyState;
        if ( lastIfElseExecuteState != null )
        {
            lastThenBodyState = lastIfElseExecuteState.thenBodyComplexState;
        }
        else
        {
            lastThenBodyState = null;
        }

        final ComplexStepState<?, ?, RESULT/*, PARENT*/ , RESUME_ARGUMENT> nextThenBodyState;
        if ( nextIfElseExecuteState != null )
        {
            nextThenBodyState = nextIfElseExecuteState.thenBodyComplexState;
        }
        else
        {
            nextThenBodyState = null;
        }

        final ComplexStepState<?, ?, RESULT/*, PARENT*/ , RESUME_ARGUMENT> lastElseBodyState;
        if ( lastIfElseExecuteState != null )
        {
            lastElseBodyState = lastIfElseExecuteState.elseBodyComplexState;
        }
        else
        {
            lastElseBodyState = null;
        }

        final ComplexStepState<?, ?, RESULT/*, PARENT*/ , RESUME_ARGUMENT> nextElseBodyState;
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

        final String variablesStr;
        if ( nextIfElseExecuteState == null )
        {
            variablesStr = "";
        }
        else
        {
            variablesStr =
                    nextIfElseExecuteState.getVariablesStr(
                            indent );
        }

        return
                indent +
                this.getClass().getSimpleName() + " (" +
                ( this.creationStackTraceElement != null ? " " + this.creationStackTraceElement : "" ) +
                "\n" +
                conditionStr + " )\n" +
                variablesStr +
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
