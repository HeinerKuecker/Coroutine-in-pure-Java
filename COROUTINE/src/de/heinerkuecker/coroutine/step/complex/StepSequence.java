package de.heinerkuecker.coroutine.step.complex;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import de.heinerkuecker.coroutine.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.expression.GetProcedureArgument;
import de.heinerkuecker.coroutine.step.CoroIterStep;
import de.heinerkuecker.coroutine.step.flow.BreakOrContinue;

/**
 * Sequence of {@link CoroIterStep} steps.
 *
 * @param <RESULT> result type of method {@link CoroutineIterator#next()}
 * @param <PARENT> type the {@link CoroutineIterator} instance
 * @author Heiner K&uuml;cker
 *
 * TODO rename to Block
 */
public class StepSequence<RESULT /*, PARENT extends CoroutineIterator<RESULT>*/>
extends ComplexStep<
    StepSequence<RESULT /*, PARENT*/> ,
    StepSequenceState<RESULT /*, PARENT*/> ,
    RESULT /*,
    PARENT*/
    >
{
    private final CoroIterStep<? extends RESULT/*, PARENT*/>[] steps;

    /**
     * Constructor.
     */
    @SafeVarargs
    public StepSequence(
            final int creationStackOffset ,
            final CoroIterStep<? extends RESULT /*, PARENT*/>... steps )
    {
        // TODO HasCreationStackTraceElement.creationStackTraceElement never used
        super( creationStackOffset );

        for ( final CoroIterStep<? extends RESULT /*, PARENT*/> step : steps )
        {
            Objects.requireNonNull( step );
        }
        this.steps = Objects.requireNonNull( steps );
    }

    /**
     * @return count of subsequent steps
     */
    int length()
    {
        return steps.length;
    }

    CoroIterStep<? extends RESULT /*, ? super PARENT*/> getStep(
            final int index )
    {
        return steps[ index ];
    }

    /**
     * @see ComplexStep#newState()
     */
    @Override
    public StepSequenceState<RESULT /*, PARENT*/> newState(
            final CoroIteratorOrProcedure<RESULT> parent )
    {
        return new StepSequenceState<>(
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

        for ( final CoroIterStep<? extends RESULT /*, PARENT*/> step : this.steps )
        {
            if ( step instanceof BreakOrContinue )
            {
                result.add(
                        (BreakOrContinue<RESULT>) step );
            }
            else if ( step instanceof ComplexStep )
            {
                result.addAll(
                        ((ComplexStep<?, ?, RESULT /*, CoroutineIterator<RESULT>*/>) step).getUnresolvedBreaksOrContinues(
                                alreadyCheckedProcedureNames ,
                                parent ) );
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

        for ( final CoroIterStep<? extends RESULT /*, PARENT*/> step : this.steps )
        {
            result.addAll(
                    step.getProcedureArgumentGetsNotInProcedure() );
        }

        return result;
    }

    /**
     * @see CoroIterStep#setResultType(Class)
     */
    @Override
    public void setResultType(
            final Class<? extends RESULT> resultType )
    {
        for ( final CoroIterStep<? extends RESULT /*, PARENT*/> step : this.steps )
        {
            ( (CoroIterStep<RESULT>) step ).setResultType( resultType );
        }
    }

    /**
     * @see ComplexStep#checkLabelAlreadyInUse
     */
    @Override
    public void checkLabelAlreadyInUse(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroIteratorOrProcedure<RESULT> parent ,
            final Set<String> labels )
    {
        for ( final CoroIterStep<? extends RESULT /*, PARENT*/> step : this.steps )
        {
            if ( step instanceof ComplexStep )
            {
                ((ComplexStep<?, ?, RESULT>) step).checkLabelAlreadyInUse(
                        alreadyCheckedProcedureNames ,
                        parent ,
                        labels );
            }
        }
    }

    @Override
    public void checkUseUndeclaredVariables(
            final CoroIteratorOrProcedure<?> parent ,
            final Map<String, Class<?>> globalVariableTypes ,
            final Map<String, Class<?>> localVariableTypes )
    {
        throw new RuntimeException( "not implemented" );
    }

    /**
     * @see ComplexStep#toString
     */
    @Override
    public String toString(
            final CoroIteratorOrProcedure<RESULT> parent ,
            final String indent ,
            final ComplexStepState<?, ?, RESULT/*, PARENT*/> lastStepExecuteState ,
            final ComplexStepState<?, ?, RESULT/*, PARENT*/> nextStepExecuteState )
    {
        @SuppressWarnings("unchecked")
        final StepSequenceState<RESULT /*, PARENT*/> lastSequenceExecuteState =
                (StepSequenceState<RESULT /*, PARENT*/>) lastStepExecuteState;

        @SuppressWarnings("unchecked")
        final StepSequenceState<RESULT /*, PARENT*/> nextSequenceExecuteState =
                (StepSequenceState<RESULT /*, PARENT*/>) nextStepExecuteState;

        final StringBuilder buff = new StringBuilder();

        for ( int i = 0 ; i < steps.length ; i++ )
        {
            final CoroIterStep<? extends RESULT /*, PARENT*/> step = this.steps[ i ];

            if ( step instanceof ComplexStep )
            {
                final ComplexStepState<?, ?, RESULT> lastSubStepExecuteState;
                if ( lastSequenceExecuteState != null &&
                        lastSequenceExecuteState.currentStepIndex == i )
                {
                    lastSubStepExecuteState = lastSequenceExecuteState.currentComplexState;
                }
                else
                {
                    lastSubStepExecuteState = null;
                }

                final ComplexStepState<?, ?, RESULT> nextSubStepExecuteState;
                if ( nextSequenceExecuteState != null &&
                        nextSequenceExecuteState.currentStepIndex == i )
                {
                    nextSubStepExecuteState = nextSequenceExecuteState.currentComplexState;
                }
                else
                {
                    nextSubStepExecuteState = null;
                }

                buff.append(
                        ( (ComplexStep<?, ?, RESULT>) step ).toString(
                                parent ,
                                //indent
                                indent /*+ " "*/ ,
                                lastSubStepExecuteState ,
                                nextSubStepExecuteState ) );
            }
            else
            {
                if ( indent.length() > "next".length() )
                {
                    if ( nextSequenceExecuteState != null &&
                            nextSequenceExecuteState.currentStepIndex == i )
                    {
                        buff.append( "next:" );
                    }
                    else if ( lastSequenceExecuteState != null &&
                            lastSequenceExecuteState.currentStepIndex == i )
                    {
                        buff.append( "last:" );
                    }
                    else
                    {
                        buff.append( nextOrLastSpaceStr() );
                    }

                    buff.append(
                            indentStrWithoutNextOrLastPart(
                                    indent ) );
                }

                buff.append( step );

                buff.append( ";\n" );
            }
        }

        return buff.toString();
    }

    @SuppressWarnings("unchecked")
    @SafeVarargs
    public static <RESULT> ComplexStep<?, ?, RESULT /*, /*PARENT* / CoroutineIterator<RESULT>*/> convertStepsToComplexStep(
            final int creationStackOffset ,
            final CoroIterStep<? extends RESULT /*, /*PARENT * / CoroutineIterator<RESULT>*/>... steps )
    {
        final ComplexStep<?, ?, RESULT /*, /*PARENT* / CoroutineIterator<RESULT>*/> complexStep;
        if ( steps.length == 1 &&
                steps[ 0 ] instanceof ComplexStep )
        {
            complexStep =
                    (ComplexStep<?, ?, RESULT /*, CoroutineIterator<RESULT>*/>) steps[ 0 ];
        }
        else
        {
            complexStep =
                    new StepSequence<>(
                            creationStackOffset ,
                            steps );
        }

        return complexStep;
    }

}
