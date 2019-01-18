package de.heinerkuecker.coroutine.step.complex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep;
import de.heinerkuecker.coroutine.expression.CoroExpression;
import de.heinerkuecker.coroutine.expression.GetProcedureArgument;
import de.heinerkuecker.coroutine.step.CoroIterStep;
import de.heinerkuecker.coroutine.step.flow.BreakOrContinue;
import de.heinerkuecker.coroutine.step.flow.exc.LabelAlreadyInUseException;

public class ForEach<RESULT /*, PARENT extends CoroutineIterator<RESULT>*/, ELEMENT>
extends ComplexStep<
    ForEach<RESULT/*, PARENT*/, ELEMENT>,
    ForEachState<RESULT/*, PARENT*/, ELEMENT>,
    RESULT
    //PARENT
    >
{
    public final String label;

    public final String variableName;

    public final Class<? extends ELEMENT> elementType;

    final CoroExpression<? extends Iterable<ELEMENT>> iterableExpression;

    final ComplexStep<?, ?, RESULT/*, PARENT /*CoroutineIterator<RESULT>*/> bodyComplexStep;

    /**
     * Constructor.
     */
    @SafeVarargs
    public ForEach(
            final String variableName ,
            final Class<? extends ELEMENT> elementType ,
            final CoroExpression<? extends Iterable<ELEMENT>> iterableExpression ,
            final CoroIterStep<RESULT/*, PARENT /*CoroutineIterator<RESULT>*/>... steps )
    {
        super(
                //creationStackOffset
                3 );

        this.label = null;

        this.variableName =
                Objects.requireNonNull(
                        variableName );

        this.elementType =
                Objects.requireNonNull(
                        elementType );

        this.iterableExpression =
                Objects.requireNonNull(
                        iterableExpression );

        this.bodyComplexStep =
                Block.convertStepsToComplexStep(
                        // creationStackOffset
                        4 ,
                        steps );
    }

    /**
     * Constructor.
     */
    @SafeVarargs
    public ForEach(
            final String label ,
            final String variableName ,
            final Class<? extends ELEMENT> elementType ,
            final CoroExpression<Iterable<ELEMENT>> iterableExpression ,
            final CoroIterStep<RESULT/*, PARENT /*CoroutineIterator<RESULT>*/>... steps )
    {
        super(
                //creationStackOffset
                3 );

        this.label = label;

        this.variableName =
                Objects.requireNonNull(
                        variableName );

        this.elementType =
                Objects.requireNonNull(
                        elementType );

        this.iterableExpression =
                Objects.requireNonNull(
                        iterableExpression );

        this.bodyComplexStep =
                Block.convertStepsToComplexStep(
                        // creationStackOffset
                        4 ,
                        steps );
    }

    /**
     * @see ComplexStep#newState
     */
    @Override
    public ForEachState<RESULT/*, PARENT*/, ELEMENT> newState(
            final CoroutineOrProcedureOrComplexstep<RESULT> parent )
    {
        return new ForEachState<>(
                this ,
                //parent.getRootParent()
                parent );
    }

    /**
     * @see ComplexStep#getUnresolvedBreaksOrContinues()
     */
    @Override
    public List<BreakOrContinue<RESULT>> getUnresolvedBreaksOrContinues(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstep<RESULT> parent )
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
                iterableExpression.getProcedureArgumentGetsNotInProcedure() );

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
            final CoroutineOrProcedureOrComplexstep<RESULT> parent ,
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
            final boolean isCoroutineRoot ,
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstep<?> parent ,
            final Map<String, Class<?>> globalVariableTypes ,
            final Map<String, Class<?>> localVariableTypes )
    {
        //throw new RuntimeException( "not implemented" );
        final Map<String, Class<?>> thisLocalVariableTypes = new HashMap<>();
        thisLocalVariableTypes.putAll( localVariableTypes );

        thisLocalVariableTypes.put(
                variableName ,
                elementType );

        this.bodyComplexStep.checkUseVariables(
                isCoroutineRoot ,
                alreadyCheckedProcedureNames ,
                parent ,
                globalVariableTypes ,
                thisLocalVariableTypes );
    }

    @Override
    public void checkUseArguments(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstep<?> parent )
    {
        this.iterableExpression.checkUseArguments( alreadyCheckedProcedureNames, parent );
    }

    /**
     * @see ComplexStep#toString
     */
    @Override
    public String toString(
            final CoroutineOrProcedureOrComplexstep<RESULT> parent ,
            final String indent ,
            ComplexStepState<?, ?, RESULT> lastStepExecuteState ,
            ComplexStepState<?, ?, RESULT> nextStepExecuteState )
    {
        @SuppressWarnings("unchecked")
        final ForEachState<RESULT/*, PARENT*/, ELEMENT> lastForExecuteState =
                (ForEachState<RESULT/*, PARENT*/, ELEMENT>) lastStepExecuteState;

        @SuppressWarnings("unchecked")
        final ForEachState<RESULT/*, PARENT*/, ELEMENT> nextForExecuteState =
                (ForEachState<RESULT/*, PARENT*/, ELEMENT>) nextStepExecuteState;

        final ComplexStepState<?, ?, RESULT/*, PARENT*/> lastBodyState;
        if ( lastForExecuteState != null )
        {
            lastBodyState = lastForExecuteState.bodyComplexState;
        }
        else
        {
            lastBodyState = null;
        }

        final ComplexStepState<?, ?, RESULT/*, PARENT*/> nextBodyState;
        if ( nextForExecuteState != null )
        {
            nextBodyState = nextForExecuteState.bodyComplexState;
        }
        else
        {
            nextBodyState = null;
        }

        final String variableNameStr;
        if ( lastForExecuteState != null &&
                lastForExecuteState.runInConditionAndUpdate )
        {
            variableNameStr =
                    "last:" +
                    indentStrWithoutNextOrLastPart( indent ) +
                    "   " +
                    this.variableName;
        }
        else if ( nextForExecuteState != null &&
                nextForExecuteState.runInConditionAndUpdate )
        {
            variableNameStr =
                    "next:" +
                    indentStrWithoutNextOrLastPart( indent ) +
                    "   " +
                    this.variableName;
        }
        else
        {
            variableNameStr =
                    indent +
                    "   " +
                    this.variableName;
        }

        final String iterableExpressionStr;
        if ( lastForExecuteState != null &&
                lastForExecuteState.runInInitializer )
        {
            iterableExpressionStr =
                    "last:" +
                    indentStrWithoutNextOrLastPart( indent ) +
                    "   " +
                    this.iterableExpression;
        }
        else if ( nextForExecuteState != null &&
                nextForExecuteState.runInInitializer )
        {
            iterableExpressionStr =
                    "next:" +
                    indentStrWithoutNextOrLastPart( indent ) +
                    "   " +
                    this.iterableExpression;
        }
        else
        {
            iterableExpressionStr =
                    indent +
                    "   " +
                    this.iterableExpression;
        }

        // TODO check whether output subsequent iterator (CoroutineIterator) is usefuel
        //final String iterableStr;
        //if ( nextForExecuteState != null &&
        //        nextForExecuteState.iterator != null )
        //{
        //    iterableStr =
        //            indent +
        //            "iterator: " +
        //            nextForExecuteState.iterator +
        //            "\n";
        //}
        //else
        //{
        //    iterableStr = "";
        //}

        return
                indent +
                ( this.label != null ? this.label + " : " : "" ) +
                this.getClass().getSimpleName() + " (" +
                ( this.creationStackTraceElement != null ? " " + this.creationStackTraceElement : "" ) +
                "\n" +
                variableNameStr + " :\n" +
                // TODO check whether output current (next) variable value is usefuel
                iterableExpressionStr + " )\n" +
                // TODO iterableStr +
                this.bodyComplexStep.toString(
                        parent ,
                        indent + " " ,
                        lastBodyState ,
                        nextBodyState );
    }

}
