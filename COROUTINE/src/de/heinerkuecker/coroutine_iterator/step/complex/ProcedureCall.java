package de.heinerkuecker.coroutine_iterator.step.complex;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine_iterator.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine_iterator.Procedure;
import de.heinerkuecker.coroutine_iterator.expression.GetProcedureArgument;
import de.heinerkuecker.coroutine_iterator.proc.arg.ProcedureArgument;
import de.heinerkuecker.coroutine_iterator.step.CoroIterStep;
import de.heinerkuecker.coroutine_iterator.step.flow.BreakOrContinue;

public class ProcedureCall<RESULT/*, PARENT extends CoroIteratorOrProcedure<RESULT, PARENT>*/>
extends ComplexStep<
    ProcedureCall<RESULT/*, PARENT*/> ,
    ProcedureCallState<RESULT> ,
    RESULT
    //PARENT
    >
//implements CoroIteratorOrProcedure<RESULT/*, CoroutineIterator<RESULT>*/>
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

    // TODO getter
    final Map<String, ProcedureArgument<?>> procedureArguments;

    /**
     * Constructor.
     *
     * @param creationStackOffset
     */
    //@SafeVarargs
    public ProcedureCall(
            //final CoroIterStep<RESULT/*, ? super PARENT/*CoroutineIterator<RESULT>*/> ... bodySteps
            final Procedure<RESULT> procedure ,
            final ProcedureArgument<?>... args )
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

        final LinkedHashMap<String, ProcedureArgument<?>> argMap = new LinkedHashMap<>();

        if ( args != null )
        {
            for ( ProcedureArgument<?> arg : args )
            {
                if ( argMap.containsKey( Objects.requireNonNull( arg.name ) ) )
                {
                    throw new IllegalArgumentException( "argument name " + arg.name + " already in use" );
                }

                argMap.put(
                        Objects.requireNonNull( arg.name ) ,
                        Objects.requireNonNull( arg ) );
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

    /**
     * @see ComplexStep#newState
     */
    @Override
    public ProcedureCallState<RESULT> newState(
            final CoroIteratorOrProcedure<RESULT> parent )
    {
        final Map<String, Object> procedureArgumentValues = new LinkedHashMap<>();

        for ( ProcedureArgument<?> arg : procedureArguments.values() )
        {
            procedureArgumentValues.put(
                    arg.name ,
                    arg.getValue( parent ) );
        }

        final ProcedureCallState<RESULT> procedureCallState =
                new ProcedureCallState<>(
                        this ,
                        parent.getRootParent() ,
                        procedureArgumentValues );

        return procedureCallState;
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

        final String procedureBodyComplexStepStr;
        if ( lastBodyState == null &&
                nextBodyState == null )
        {
            procedureBodyComplexStepStr = "";
        }
        else
            // print procedure body only when next or last source position is in procedure
        {
            procedureBodyComplexStepStr =
                    this.procedure.bodyComplexStep.toString(
                            indent + " " ,
                            lastBodyState ,
                            nextBodyState );
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
                indent +
                "procedure arguments: " + this.procedureArguments +
                "\n" +
                procedureBodyComplexStepStr;
    }

    /**
     * @see CoroIterStep#getProcedureArgumentsNotInProcedure()
     */
    @Override
    public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    {
        return Collections.emptyList();
    }

}
