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

public class IfElse<COROUTINE_RETURN/*, PARENT extends CoroutineIterator<COROUTINE_RETURN>*/ , RESUME_ARGUMENT>
extends ComplexStep<
    IfElse<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT>,
    IfElseState<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT>,
    COROUTINE_RETURN ,
    //PARENT
    RESUME_ARGUMENT
    >
{
    //final ConditionOrBooleanExpression/*Condition*/ condition
    final CoroExpression<Boolean> condition;
    final ComplexStep<?, ?, COROUTINE_RETURN/*, PARENT/*CoroutineIterator<COROUTINE_RETURN>*/ , RESUME_ARGUMENT> thenBodyComplexStep;
    final ComplexStep<?, ?, COROUTINE_RETURN/*, PARENT/*CoroutineIterator<COROUTINE_RETURN>*/ , RESUME_ARGUMENT> elseBodyComplexStep;

    /**
     * Constructor.
     */
    public IfElse(
            //final ConditionOrBooleanExpression/*Condition*/ condition
            final CoroExpression<Boolean> condition ,
            final CoroIterStep<COROUTINE_RETURN/*, CoroutineIterator<COROUTINE_RETURN>*/>[] thenSteps ,
            final CoroIterStep<COROUTINE_RETURN/*, CoroutineIterator<COROUTINE_RETURN>*/>[] elseSteps )
    {
        super(
                //creationStackOffset
                3 );

        this.condition = condition;

        if ( thenSteps.length == 1 &&
                thenSteps[ 0 ] instanceof ComplexStep )
        {
            this.thenBodyComplexStep =
                    (ComplexStep<?, ?, COROUTINE_RETURN/*, PARENT/*? super CoroutineIterator<COROUTINE_RETURN>*/, RESUME_ARGUMENT>) thenSteps[ 0 ];
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
                    (ComplexStep<?, ?, COROUTINE_RETURN/*, PARENT/*? super CoroutineIterator<COROUTINE_RETURN>*/ , RESUME_ARGUMENT>) elseSteps[ 0 ];
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

    ///**
    // * Constructor.
    // */
    //public IfElse(
    //        final CoroExpression<Boolean> condition ,
    //        final CoroIterStep<COROUTINE_RETURN/*, CoroutineIterator<COROUTINE_RETURN>*/>[] thenSteps ,
    //        final CoroIterStep<COROUTINE_RETURN/*, CoroutineIterator<COROUTINE_RETURN>*/>[] elseSteps )
    //{
    //    super(
    //            //creationStackOffset
    //            3 );
    //
    //    this.condition =
    //            new IsTrue(
    //                    condition );
    //
    //    if ( thenSteps.length == 1 &&
    //            thenSteps[ 0 ] instanceof ComplexStep )
    //    {
    //        this.thenBodyComplexStep =
    //                (ComplexStep<?, ?, COROUTINE_RETURN/*, PARENT/*? super CoroutineIterator<COROUTINE_RETURN>*/ , RESUME_ARGUMENT>) thenSteps[ 0 ];
    //    }
    //    else
    //    {
    //        this.thenBodyComplexStep =
    //                new Block<>(
    //                        // creationStackOffset
    //                        3 ,
    //                        thenSteps );
    //    }
    //
    //    if ( elseSteps.length == 1 &&
    //            elseSteps[ 0 ] instanceof ComplexStep )
    //    {
    //        this.elseBodyComplexStep =
    //                (ComplexStep<?, ?, COROUTINE_RETURN/*, PARENT/*? super CoroutineIterator<COROUTINE_RETURN>*/ , RESUME_ARGUMENT>) elseSteps[ 0 ];
    //    }
    //    else
    //    {
    //        this.elseBodyComplexStep =
    //                new Block<>(
    //                        // creationStackOffset
    //                        3 ,
    //                        elseSteps );
    //    }
    //
    //}

    /**
     * Constructor.
     */
    public IfElse(
            final boolean condition ,
            final CoroIterStep<COROUTINE_RETURN/*, CoroutineIterator<COROUTINE_RETURN>*/>[] thenSteps ,
            final CoroIterStep<COROUTINE_RETURN/*, CoroutineIterator<COROUTINE_RETURN>*/>[] elseSteps )
    {
        super(
                //creationStackOffset
                3 );

        this.condition =
                //new IsTrue(
                        Value.booleanValue(
                                condition );

        if ( thenSteps.length == 1 &&
                thenSteps[ 0 ] instanceof ComplexStep )
        {
            this.thenBodyComplexStep =
                    (ComplexStep<?, ?, COROUTINE_RETURN/*, PARENT/*? super CoroutineIterator<COROUTINE_RETURN>*/ , RESUME_ARGUMENT>) thenSteps[ 0 ];
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
                    (ComplexStep<?, ?, COROUTINE_RETURN/*, PARENT/*? super CoroutineIterator<COROUTINE_RETURN>*/ , RESUME_ARGUMENT>) elseSteps[ 0 ];
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
    public IfElseState<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> newState(
            final CoroutineOrProcedureOrComplexstep<COROUTINE_RETURN, RESUME_ARGUMENT> parent )
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
            final CoroutineOrProcedureOrComplexstep<COROUTINE_RETURN, RESUME_ARGUMENT> parent )
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
            final Class<? extends COROUTINE_RETURN> resultType )
    {
        this.thenBodyComplexStep.setResultType( resultType );
        this.elseBodyComplexStep.setResultType( resultType );
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

        this.elseBodyComplexStep.checkLabelAlreadyInUse(
                alreadyCheckedProcedureNames ,
                parent ,
                labels );
    }

    @Override
    public void checkUseVariables(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstep<?, ?> parent ,
            final Map<String, Class<?>> globalVariableTypes ,
            final Map<String, Class<?>> localVariableTypes )
    {
        this.condition.checkUseVariables(
                alreadyCheckedProcedureNames ,
                parent ,
                globalVariableTypes, localVariableTypes );

        this.thenBodyComplexStep.checkUseVariables(
                alreadyCheckedProcedureNames ,
                parent ,
                globalVariableTypes, localVariableTypes );

        this.elseBodyComplexStep.checkUseVariables(
                alreadyCheckedProcedureNames ,
                parent ,
                globalVariableTypes, localVariableTypes );
    }

    @Override
    public void checkUseArguments(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstep<?, ?> parent )
    {
        this.condition.checkUseArguments( alreadyCheckedProcedureNames , parent );
        this.thenBodyComplexStep.checkUseArguments( alreadyCheckedProcedureNames , parent );
        this.elseBodyComplexStep.checkUseArguments( alreadyCheckedProcedureNames , parent );
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
        final IfElseState<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> lastIfElseExecuteState =
                (IfElseState<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT>) lastStepExecuteState;

        @SuppressWarnings("unchecked")
        final IfElseState<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> nextIfElseExecuteState =
                (IfElseState<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT>) nextStepExecuteState;

        final ComplexStepState<?, ?, COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> lastThenBodyState;
        if ( lastIfElseExecuteState != null )
        {
            lastThenBodyState = lastIfElseExecuteState.thenBodyComplexState;
        }
        else
        {
            lastThenBodyState = null;
        }

        final ComplexStepState<?, ?, COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> nextThenBodyState;
        if ( nextIfElseExecuteState != null )
        {
            nextThenBodyState = nextIfElseExecuteState.thenBodyComplexState;
        }
        else
        {
            nextThenBodyState = null;
        }

        final ComplexStepState<?, ?, COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> lastElseBodyState;
        if ( lastIfElseExecuteState != null )
        {
            lastElseBodyState = lastIfElseExecuteState.elseBodyComplexState;
        }
        else
        {
            lastElseBodyState = null;
        }

        final ComplexStepState<?, ?, COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> nextElseBodyState;
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
