package de.heinerkuecker.coroutine.step.complex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep;
import de.heinerkuecker.coroutine.expression.GetProcedureArgument;
import de.heinerkuecker.coroutine.step.CoroIterStep;
import de.heinerkuecker.coroutine.step.flow.BreakOrContinue;
import de.heinerkuecker.coroutine.step.simple.DeclareVariable;

/**
 * Sequence of {@link CoroIterStep} steps.
 *
 * @param <RESULT> result type of method {@link CoroutineIterator#next()}
 * @param <PARENT> type the {@link CoroutineIterator} instance
 * @author Heiner K&uuml;cker
 */
public class Block<RESULT /*, PARENT extends CoroutineIterator<RESULT>*/, RESUME_ARGUMENT>
extends ComplexStep<
    Block<RESULT /*, PARENT*/, RESUME_ARGUMENT> ,
    BlockState<RESULT /*, PARENT*/, RESUME_ARGUMENT> ,
    RESULT ,
    /*,PARENT*/
    RESUME_ARGUMENT
    >
{
    private final CoroIterStep<? extends RESULT/*, PARENT*/>[] steps;

    /**
     * Constructor.
     */
    @SafeVarargs
    public Block(
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

    @Override
    public BlockState<RESULT /*, PARENT*/ , RESUME_ARGUMENT> newState(
            final CoroutineOrProcedureOrComplexstep<RESULT, RESUME_ARGUMENT> parent )
    {
        return new BlockState<>(
                this ,
                parent );
    }

    @Override
    public List<BreakOrContinue<?, ?>> getUnresolvedBreaksOrContinues(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstep<RESULT, RESUME_ARGUMENT> parent )
    {
        final List<BreakOrContinue<?, ?>> result = new ArrayList<>();

        for ( final CoroIterStep<? extends RESULT /*, PARENT*/> step : this.steps )
        {
            if ( step instanceof BreakOrContinue )
            {
                result.add(
                        (BreakOrContinue<RESULT , RESUME_ARGUMENT>) step );
            }
            else if ( step instanceof ComplexStep )
            {
                result.addAll(
                        ((ComplexStep<?, ?, RESULT /*, CoroutineIterator<RESULT>*/ , RESUME_ARGUMENT>) step).getUnresolvedBreaksOrContinues(
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
            final CoroutineOrProcedureOrComplexstep<RESULT, RESUME_ARGUMENT> parent ,
            final Set<String> labels )
    {
        for ( final CoroIterStep<? extends RESULT /*, PARENT*/> step : this.steps )
        {
            if ( step instanceof ComplexStep )
            {
                ((ComplexStep<?, ?, RESULT , RESUME_ARGUMENT>) step).checkLabelAlreadyInUse(
                        alreadyCheckedProcedureNames ,
                        parent ,
                        labels );
            }
        }
    }

    @Override
    public void checkUseVariables(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstep<?, ?> parent ,
            final Map<String, Class<?>> globalVariableTypes ,
            final Map<String, Class<?>> localVariableTypes )
    {
        final Map<String, Class<?>> thisLocalVariableTypes =
                new HashMap<>(
                        localVariableTypes );

        for ( final CoroIterStep<? extends RESULT /*, PARENT*/> step : this.steps )
        {
            if ( step instanceof DeclareVariable )
            {
                final DeclareVariable<?, ?, ?> declareLocalVar = (DeclareVariable<?, ?, ?>) step;

                if ( thisLocalVariableTypes.containsKey( declareLocalVar.varName ) )
                {
                    throw new DeclareVariable.VariableAlreadyDeclaredException(
                            declareLocalVar );
                }

                thisLocalVariableTypes.put(
                        declareLocalVar.varName ,
                        declareLocalVar.type );
            }
            else
            {
                step.checkUseVariables(
                        //isCoroutineRoot ,
                        alreadyCheckedProcedureNames ,
                        parent ,
                        //thisGlobalVariableTypes
                        globalVariableTypes ,
                        thisLocalVariableTypes );
            }
        }
    }

    @Override
    public void checkUseArguments(
            HashSet<String> alreadyCheckedProcedureNames, final CoroutineOrProcedureOrComplexstep<?, ?> parent )
    {
        for ( final CoroIterStep<? extends RESULT /*, PARENT*/> step : this.steps )
        {
            step.checkUseArguments(
                    alreadyCheckedProcedureNames ,
                    parent );
        }
    }

    /**
     * @see ComplexStep#toString
     */
    @Override
    public String toString(
            final CoroutineOrProcedureOrComplexstep<RESULT, RESUME_ARGUMENT> parent ,
            final String indent ,
            final ComplexStepState<?, ?, RESULT/*, PARENT*/ , RESUME_ARGUMENT> lastStepExecuteState ,
            final ComplexStepState<?, ?, RESULT/*, PARENT*/ , RESUME_ARGUMENT> nextStepExecuteState )
    {
        @SuppressWarnings("unchecked")
        final BlockState<RESULT /*, PARENT*/, RESUME_ARGUMENT> lastSequenceExecuteState =
                (BlockState<RESULT /*, PARENT*/ , RESUME_ARGUMENT>) lastStepExecuteState;

        @SuppressWarnings("unchecked")
        final BlockState<RESULT /*, PARENT*/ , RESUME_ARGUMENT> nextSequenceExecuteState =
                (BlockState<RESULT /*, PARENT*/ , RESUME_ARGUMENT>) nextStepExecuteState;

        final StringBuilder buff = new StringBuilder();

        if ( nextSequenceExecuteState != null )
        {
            buff.append( nextSequenceExecuteState.getVariablesStr( indent ) );
        }

        for ( int i = 0 ; i < steps.length ; i++ )
        {
            final CoroIterStep<? extends RESULT /*, PARENT*/> step = this.steps[ i ];

            if ( step instanceof ComplexStep )
            {
                final ComplexStepState<?, ?, RESULT , RESUME_ARGUMENT> lastSubStepExecuteState;
                if ( lastSequenceExecuteState != null &&
                        lastSequenceExecuteState.currentStepIndex == i )
                {
                    lastSubStepExecuteState = lastSequenceExecuteState.currentComplexState;
                }
                else
                {
                    lastSubStepExecuteState = null;
                }

                final ComplexStepState<?, ?, RESULT , RESUME_ARGUMENT> nextSubStepExecuteState;
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
                        ( (ComplexStep<?, ?, RESULT , RESUME_ARGUMENT>) step ).toString(
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
    public static <RESULT , RESUME_ARGUMENT> ComplexStep<?, ?, RESULT /*, /*PARENT* / CoroutineIterator<RESULT>*/, RESUME_ARGUMENT> convertStepsToComplexStep(
            final int creationStackOffset ,
            final CoroIterStep<? extends RESULT /*, /*PARENT * / CoroutineIterator<RESULT>*/>... steps )
    {
        final ComplexStep<?, ?, RESULT /*, /*PARENT* / CoroutineIterator<RESULT>*/, RESUME_ARGUMENT> complexStep;
        if ( steps.length == 1 &&
                steps[ 0 ] instanceof ComplexStep )
        {
            complexStep =
                    (ComplexStep<?, ?, RESULT /*, CoroutineIterator<RESULT>*/ , RESUME_ARGUMENT>) steps[ 0 ];
        }
        else
        {
            complexStep =
                    new Block<>(
                            creationStackOffset ,
                            steps );
        }

        return complexStep;
    }

}
