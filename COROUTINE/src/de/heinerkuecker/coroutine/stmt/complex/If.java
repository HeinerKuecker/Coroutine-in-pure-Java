package de.heinerkuecker.coroutine.stmt.complex;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep;
import de.heinerkuecker.coroutine.exprs.CoroExpression;
import de.heinerkuecker.coroutine.exprs.GetProcedureArgument;
import de.heinerkuecker.coroutine.exprs.Value;
import de.heinerkuecker.coroutine.stmt.CoroIterStep;
import de.heinerkuecker.coroutine.stmt.flow.BreakOrContinue;

public class If<COROUTINE_RETURN/*, PARENT extends CoroutineIterator<COROUTINE_RETURN>*/ , RESUME_ARGUMENT>
extends ComplexStep<
    If<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT>,
    IfState<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT>,
    COROUTINE_RETURN ,
    //PARENT
    RESUME_ARGUMENT
    >
{
    //final ConditionOrBooleanExpression/*Condition*/ condition
    final CoroExpression<Boolean> condition;
    final ComplexStep<?, ?, COROUTINE_RETURN /*, PARENT/*CoroutineIterator<COROUTINE_RETURN>*/ , RESUME_ARGUMENT> thenBodyComplexStep;

    /**
     * Constructor.
     */
    @SafeVarargs
    public If(
            //final ConditionOrBooleanExpression/*Condition*/ condition
            final CoroExpression<Boolean> condition ,
            final CoroIterStep<COROUTINE_RETURN/*, ? super PARENT/*CoroutineIterator<COROUTINE_RETURN>*/> ... steps )
    {
        super(
                //creationStackOffset
                3 );

        this.condition = condition;

        this.thenBodyComplexStep =
                Block.convertStepsToComplexStep(
                        // creationStackOffset
                        4 ,
                        steps );
    }

    ///**
    // * Constructor.
    // */
    //@SafeVarargs
    //public If(
    //        final CoroExpression<Boolean> condition ,
    //        final CoroIterStep<COROUTINE_RETURN/*, ? super PARENT/*CoroutineIterator<COROUTINE_RETURN>*/> ... steps )
    //{
    //    super(
    //            //creationStackOffset
    //            3 );
    //
    //    this.condition =
    //            new IsTrue(
    //                    condition );
    //
    //    this.thenBodyComplexStep =
    //            Block.convertStepsToComplexStep(
    //                    // creationStackOffset
    //                    4 ,
    //                    steps );
    //}

    /**
     * Constructor.
     */
    @SafeVarargs
    public If(
            final boolean condition ,
            final CoroIterStep<COROUTINE_RETURN/*, ? super PARENT/*CoroutineIterator<COROUTINE_RETURN>*/> ... steps )
    {
        super(
                //creationStackOffset
                3 );

        this.condition =
                //new IsTrue(
                        Value.booleanValue(
                                condition );

        this.thenBodyComplexStep =
                Block.convertStepsToComplexStep(
                        // creationStackOffset
                        4 ,
                        steps );
    }

    @Override
    public IfState<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> newState(
            final CoroutineOrProcedureOrComplexstep<COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        return new IfState<>(
                this ,
                parent );
    }

    @Override
    public List<BreakOrContinue<? , ?>> getUnresolvedBreaksOrContinues(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstep<COROUTINE_RETURN, RESUME_ARGUMENT> parent )
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
            final Class<? extends COROUTINE_RETURN> resultType )
    {
        this.thenBodyComplexStep.setResultType( resultType );
    }

    @Override
    public void checkLabelAlreadyInUse(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstep<COROUTINE_RETURN, RESUME_ARGUMENT> parent ,
            final Set<String> labels )
    {
        this.thenBodyComplexStep.checkLabelAlreadyInUse(
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
    }

    @Override
    public void checkUseArguments(
            HashSet<String> alreadyCheckedProcedureNames, final CoroutineOrProcedureOrComplexstep<?, ?> parent )
    {
        this.condition.checkUseArguments( alreadyCheckedProcedureNames, parent );
        this.thenBodyComplexStep.checkUseArguments( alreadyCheckedProcedureNames, parent );
    }

    /**
     * @see ComplexStep#toString
     */
    @Override
    public String toString(
            final CoroutineOrProcedureOrComplexstep<COROUTINE_RETURN, RESUME_ARGUMENT> parent ,
            final String indent ,
            final ComplexStepState<?, /*STEP*/?, COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> lastStepExecuteState ,
            final ComplexStepState<?, /*STEP*/?, COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> nextStepExecuteState )
    {
        @SuppressWarnings("unchecked")
        final IfState<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> lastIfExecuteState =
                (IfState<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT>) lastStepExecuteState;

        @SuppressWarnings("unchecked")
        final IfState<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> nextIfExecuteState =
                (IfState<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT>) nextStepExecuteState;

        final ComplexStepState<?, ?, COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> lastThenBodyState;
        if ( lastIfExecuteState != null )
        {
            lastThenBodyState = lastIfExecuteState.thenBodyComplexState;
        }
        else
        {
            lastThenBodyState = null;
        }

        final ComplexStepState<?, ?, COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> nextThenBodyState;
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

        final String variablesStr;
        if ( nextThenBodyState == null )
        {
            variablesStr = "";
        }
        else
        {
            variablesStr =
                    nextThenBodyState.getVariablesStr(
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
                        nextThenBodyState );
    }

}
