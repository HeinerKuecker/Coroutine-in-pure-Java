package de.heinerkuecker.coroutine_iterator.step.complex;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine_iterator.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine_iterator.step.flow.BreakOrContinue;

public class ProcedureCall<RESULT/*, PARENT extends CoroIteratorOrProcedure<RESULT, PARENT>*/>
extends ComplexStep<
    ProcedureCall<RESULT/*, PARENT*/> ,
    ProcedureCallState<RESULT> ,
    RESULT
    //PARENT
    >
implements CoroIteratorOrProcedure<RESULT/*, CoroutineIterator<RESULT>*/>
{
    ///**
    // * Es muss ein ComplexStep sein,
    // * weil dieser mit ComplexStepState
    // * einen State hat, welcher bei
    // * einem SimpleStep nicht vorhanden
    // * ist und dessen State in dieser
    // * Klasse verwaltet werden müsste.
    // */
    //final ComplexStep<?, ?, RESULT /*, /*PARENT* / CoroutineIterator<RESULT>*/> bodyComplexStep;

    final Procedure<RESULT> procedure;

    private final Map<String, Object> procedureArguments;

    /**
     * Constructor.
     *
     * @param creationStackOffset
     */
    //@SafeVarargs
    public ProcedureCall(
            //final CoroIterStep<RESULT/*, ? super PARENT/*CoroutineIterator<RESULT>*/> ... bodySteps
            final Procedure<RESULT> procedure ,
            final ProcedureArgument... args )
    {
        super(
                //creationStackOffset
                3 );

        //if (bodySteps.length == 0 )
        //{
        //    throw new IllegalArgumentException( "procedure body is empty" );
        //}
        //
        //if ( bodySteps.length == 1 &&
        //        bodySteps[ 0 ] instanceof ComplexStep )
        //{
        //    this.bodyComplexStep =
        //            (ComplexStep<?, ?, RESULT/*, PARENT/*? super CoroutineIterator<RESULT>*/>) bodySteps[ 0 ];
        //}
        //else
        //{
        //    this.bodyComplexStep =
        //            new StepSequence(
        //                    // creationStackOffset
        //                    3 ,
        //                    bodySteps );
        //}

        this.procedure = Objects.requireNonNull( procedure );

        final LinkedHashMap<String, Object> argMap = new LinkedHashMap<>();

        if ( args != null )
        {
            for ( ProcedureArgument arg : args )
            {
                argMap.put(
                        Objects.requireNonNull( arg.getName() ) ,
                        arg.getValue() );
            }
        }

        this.procedureArguments = Collections.unmodifiableMap( argMap );
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

    /**
     * @see CoroIteratorOrProcedure#vars()
     */
    @Override
    public Map<String, Object> localVars()
    {
        // TODO Auto-generated method stub
        throw new RuntimeException( "not implemented" );
    }

    /**
     * @see CoroIteratorOrProcedure#globalVars()
     */
    @Override
    public Map<String, Object> globalVars()
    {
        // TODO Auto-generated method stub
        throw new RuntimeException( "not implemented" );
    }

    /**
     * @see CoroIteratorOrProcedure#procedureArguments()
     */
    @Override
    public Map<String, Object> procedureArguments()
    {
        return this.procedureArguments;
    }

    /* (non-Javadoc)
     * @see de.heinerkuecker.coroutine_iterator.step.complex.ComplexStep#newState()
     */
    @Override
    public ProcedureCallState<RESULT> newState()
    {
        return new ProcedureCallState<>( this );
    }

    /**
     * @see ComplexStep#getUnresolvedBreaksOrContinues()
     */
    @Override
    public List<BreakOrContinue<RESULT>> getUnresolvedBreaksOrContinues()
    {
        return this.procedure.bodyComplexStep.getUnresolvedBreaksOrContinues();
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
        final ProcedureCallState<RESULT /*, PARENT*/> lastProcExecuteState =
                (ProcedureCallState<RESULT /*, PARENT*/>) lastStepExecuteState;

        final ProcedureCallState<RESULT /*, PARENT*/> nextProcExecuteState =
                (ProcedureCallState<RESULT /*, PARENT*/>) nextStepExecuteState;

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
                " " +
                this.procedure.name +
                //" (" +
                ( this.creationStackTraceElement != null ? " " + this.creationStackTraceElement : "" ) +
                "\n" +
                //conditionStr +
                //" )\n" +
                this.procedure.bodyComplexStep.toString(
                        indent + " " ,
                        lastBodyState ,
                        nextBodyState );
    }

}
