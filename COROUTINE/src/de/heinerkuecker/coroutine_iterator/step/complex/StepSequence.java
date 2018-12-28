package de.heinerkuecker.coroutine_iterator.step.complex;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.heinerkuecker.coroutine_iterator.CoroutineIterator;
import de.heinerkuecker.coroutine_iterator.step.CoroIterStep;
import de.heinerkuecker.coroutine_iterator.step.flow.BreakOrContinue;

/**
 * Sequence of {@link CoroIterStep} steps.
 *
 * @param <RESULT> result type of method {@link CoroutineIterator#next()}
 * @param <PARENT> type the {@link CoroutineIterator} instance
 * @author Heiner K&uuml;cker
 */
public class StepSequence<RESULT /*, PARENT extends CoroutineIterator<RESULT>*/>
extends ComplexStep<
    StepSequence<RESULT /*, PARENT*/> ,
    StepSequenceState<RESULT /*, PARENT*/> ,
    RESULT /*,
    PARENT*/
    >
{
    private final CoroIterStep<RESULT/*, PARENT*/>[] steps;

    /**
     * Constructor.
     */
    @SafeVarargs
    public StepSequence(
            final int creationStackOffset ,
            final CoroIterStep<RESULT /*, PARENT*/>... steps )
    {
        // TODO HasCreationStackTraceElement.creationStackTraceElement never used
        super( creationStackOffset );

        for ( final CoroIterStep<RESULT /*, PARENT*/> step : steps )
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

    CoroIterStep<RESULT /*, ? super PARENT*/> getStep(
            final int index )
    {
        return steps[ index ];
    }

    /**
     * @see ComplexStep#newState()
     */
    @Override
    public StepSequenceState<RESULT /*, PARENT*/> newState()
    {
        return new StepSequenceState<>( this );
    }

    /**
     * @see ComplexStep#getUnresolvedBreaksOrContinues()
     */
    @Override
    public List<BreakOrContinue<RESULT>> getUnresolvedBreaksOrContinues()
    {
        final List<BreakOrContinue<RESULT>> result = new ArrayList<>();

        for ( final CoroIterStep<RESULT /*, PARENT*/> step : this.steps )
        {
            if ( step instanceof BreakOrContinue )
            {
                result.add(
                        (BreakOrContinue<RESULT>) step );
            }
            else if ( step instanceof ComplexStep )
            {
                result.addAll(
                        ((ComplexStep<?, ?, RESULT /*, CoroutineIterator<RESULT>*/>) step).getUnresolvedBreaksOrContinues() );
            }
        }

        return result;
    }

    /**
     * @see ComplexStep#toString
     */
    @Override
    public String toString(
            final String indent ,
            final ComplexStepState<?, ?, RESULT/*, PARENT*/> lastStepExecuteState ,
            final ComplexStepState<?, ?, RESULT/*, PARENT*/> nextStepExecuteState )
    {
        final StepSequenceState<RESULT /*, PARENT*/> lastSequenceExecuteState =
                (StepSequenceState<RESULT /*, PARENT*/>) lastStepExecuteState;

        final StepSequenceState<RESULT /*, PARENT*/> nextSequenceExecuteState =
                (StepSequenceState<RESULT /*, PARENT*/>) nextStepExecuteState;

        final StringBuilder buff = new StringBuilder();

        for ( int i = 0 ; i < steps.length ; i++ )
        {
            final CoroIterStep<RESULT /*, PARENT*/> step = this.steps[ i ];

            if ( step instanceof ComplexStep )
            {
                final ComplexStepState lastSubStepExecuteState;
                if ( lastSequenceExecuteState != null &&
                        lastSequenceExecuteState.currentStepIndex == i )
                {
                    lastSubStepExecuteState = lastSequenceExecuteState.currentComplexState;
                }
                else
                {
                    lastSubStepExecuteState = null;
                }

                final ComplexStepState nextSubStepExecuteState;
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
                        ( (ComplexStep<?, ?, ? /*, ?*/>) step ).toString(
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

}
