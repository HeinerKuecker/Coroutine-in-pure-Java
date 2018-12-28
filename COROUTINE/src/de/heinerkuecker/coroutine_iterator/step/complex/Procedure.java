package de.heinerkuecker.coroutine_iterator.step.complex;

import java.util.List;
import java.util.Map;

import de.heinerkuecker.coroutine_iterator.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine_iterator.step.CoroIterStep;
import de.heinerkuecker.coroutine_iterator.step.flow.BreakOrContinue;

public class Procedure<RESULT/*, PARENT extends CoroIteratorOrProcedure<RESULT, PARENT>*/>
extends ComplexStep<
    Procedure<RESULT/*, PARENT*/> ,
    ProcedureState<RESULT> ,
    RESULT
    //PARENT
    >
implements CoroIteratorOrProcedure<RESULT/*, CoroutineIterator<RESULT>*/>
{
    /**
     * Es muss ein ComplexStep sein,
     * weil dieser mit ComplexStepState
     * einen State hat, welcher bei
     * einem SimpleStep nicht vorhanden
     * ist und dessen State in dieser
     * Klasse verwaltet werden müsste.
     */
    final ComplexStep<?, ?, RESULT /*, /*PARENT* / CoroutineIterator<RESULT>*/> bodyComplexStep;

    /**
     * Constructor.
     *
     * @param creationStackOffset
     */
    @SafeVarargs
    public Procedure(
            final CoroIterStep<RESULT/*, ? super PARENT/*CoroutineIterator<RESULT>*/> ... bodySteps )
    {
        super(
                //creationStackOffset
                3 );

        if (bodySteps.length == 0 )
        {
            throw new IllegalArgumentException( "procedure body is empty" );
        }

        if ( bodySteps.length == 1 &&
                bodySteps[ 0 ] instanceof ComplexStep )
        {
            this.bodyComplexStep =
                    (ComplexStep<?, ?, RESULT/*, PARENT/*? super CoroutineIterator<RESULT>*/>) bodySteps[ 0 ];
        }
        else
        {
            this.bodyComplexStep =
                    new StepSequence(
                            // creationStackOffset
                            3 ,
                            bodySteps );
        }
    }

    ///**
    // * @param creationStackOffset
    // */
    //protected Procedure(int creationStackOffset) {
    //    super(creationStackOffset);
    //    // TODO Auto-generated constructor stub
    //}

    /* (non-Javadoc)
     * @see de.heinerkuecker.coroutine_iterator.CoroIteratorOrProcedure#saveLastStepState()
     */
    @Override
    public void saveLastStepState() {
        // TODO Auto-generated method stub
        throw new RuntimeException( "not implemented" );
    }

    /* (non-Javadoc)
     * @see de.heinerkuecker.coroutine_iterator.CoroIteratorOrProcedure#vars()
     */
    @Override
    public Map<String, Object> vars() {
        // TODO Auto-generated method stub
        throw new RuntimeException( "not implemented" );
    }

    /* (non-Javadoc)
     * @see de.heinerkuecker.coroutine_iterator.step.complex.ComplexStep#newState()
     */
    @Override
    public ProcedureState<RESULT> newState()
    {
        return new ProcedureState<>( this );
    }

    /**
     * @see ComplexStep#getUnresolvedBreaksOrContinues()
     */
    @Override
    public List<BreakOrContinue<RESULT>> getUnresolvedBreaksOrContinues()
    {
        return this.bodyComplexStep.getUnresolvedBreaksOrContinues();
    }

    /**
     * @see ComplexStep#toString(String, ComplexStepState, ComplexStepState)
     */
    @Override
    public String toString(
            final String indent ,
            final ComplexStepState<?, ?, RESULT> lastStepExecuteState ,
            final ComplexStepState<?, ?, RESULT> nextStepExecuteState )
    {
        final ProcedureState<RESULT /*, PARENT*/> lastProcExecuteState =
                (ProcedureState<RESULT /*, PARENT*/>) lastStepExecuteState;

        final ProcedureState<RESULT /*, PARENT*/> nextProcExecuteState =
                (ProcedureState<RESULT /*, PARENT*/>) nextStepExecuteState;

        final ComplexStepState<?, ?, RESULT /*, PARENT*/> lastBodyState;
        if ( lastProcExecuteState != null )
        {
            lastBodyState = lastProcExecuteState.bodyComplexState;
        }
        else
        {
            lastBodyState = null;
        }

        final ComplexStepState<?, ?, RESULT /*, PARENT*/> nextBodyState;
        if ( nextProcExecuteState != null )
        {
            nextBodyState = nextProcExecuteState.bodyComplexState;
        }
        else
        {
            nextBodyState = null;
        }

        return
                indent +
                //( this.label != null ? this.label + " : " : "" ) +
                this.getClass().getSimpleName() +
                //" (" +
                ( this.creationStackTraceElement != null ? " " + this.creationStackTraceElement : "" ) +
                "\n" +
                //conditionStr +
                //" )\n" +
                this.bodyComplexStep.toString(
                        indent + " " ,
                        lastBodyState ,
                        nextBodyState );
    }

}
