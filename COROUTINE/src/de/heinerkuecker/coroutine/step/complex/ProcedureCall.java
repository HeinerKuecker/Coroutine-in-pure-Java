package de.heinerkuecker.coroutine.step.complex;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep;
import de.heinerkuecker.coroutine.arg.Argument;
import de.heinerkuecker.coroutine.arg.Arguments;
import de.heinerkuecker.coroutine.expression.GetProcedureArgument;
import de.heinerkuecker.coroutine.step.CoroIterStep;
import de.heinerkuecker.coroutine.step.flow.BreakOrContinue;
import de.heinerkuecker.util.ArrayDeepToString;

public class ProcedureCall<RESULT/*, PARENT extends CoroutineOrProcedureOrComplexstep<RESULT, PARENT>*/ , RESUME_ARGUMENT>
extends ComplexStep<
    ProcedureCall<RESULT/*, PARENT*/ , RESUME_ARGUMENT> ,
    ProcedureCallState<RESULT , RESUME_ARGUMENT> ,
    RESULT ,
    //PARENT
    RESUME_ARGUMENT
    >
//implements CoroutineOrProcedureOrComplexstep<RESULT/*, CoroutineIterator<RESULT>*/>
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

    //final Procedure<RESULT> procedure;
    final String procedureName;

    // TODO getter
    //final Map<String, ProcedureArgument<?>> procedureArguments;
    final Argument<?>[] procedureArguments;

    /**
     * Reifier for type param {@link #RESULT} to solve unchecked casts.
     */
    private Class<? extends RESULT> resultType;

    /**
     * Constructor.
     *
     * @param creationStackOffset
     */
    //@SafeVarargs
    public ProcedureCall(
            //final CoroIterStep<RESULT/*, ? super PARENT/*CoroutineIterator<RESULT>*/> ... bodySteps
            //final Procedure<RESULT> procedure
            final String procedureName ,
            final Argument<?>... args )
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
        //            new Block(
        //                    // creationStackOffset
        //                    3 ,
        //                    bodySteps );
        //}

        //this.procedure = Objects.requireNonNull( procedure );
        this.procedureName = Objects.requireNonNull( procedureName );

        //final LinkedHashMap<String, ProcedureArgument<?>> argMap = new LinkedHashMap<>();
        //
        //if ( args != null )
        //{
        //    for ( ProcedureArgument<?> arg : args )
        //    {
        //        if ( argMap.containsKey( Objects.requireNonNull( arg.name ) ) )
        //        {
        //            throw new IllegalArgumentException( "argument name " + arg.name + " already in use" );
        //        }
        //
        //        argMap.put(
        //                Objects.requireNonNull( arg.name ) ,
        //                Objects.requireNonNull( arg ) );
        //    }
        //}
        //
        //this.procedureArguments = Collections.unmodifiableMap( argMap );
        this.procedureArguments = args;
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
    public ProcedureCallState<RESULT , RESUME_ARGUMENT> newState(
            final CoroutineOrProcedureOrComplexstep<RESULT, RESUME_ARGUMENT> parent )
    {
        //final Map<String, Object> procedureArgumentValues = new LinkedHashMap<>();
        //for ( ProcedureArgument<?> arg : procedureArguments.values() )
        //{
        //    procedureArgumentValues.put(
        //            arg.name ,
        //            arg.getValue(
        //                    parent ) );
        //}

        final Arguments arguments =
                new Arguments(
                        // isInitializationCheck
                        false ,
                        // checkMandantoryValues
                        true ,
                        //params
                        parent.getProcedure( this.procedureName ).params ,
                        //args
                        procedureArguments ,
                        parent );

        final ProcedureCallState<RESULT , RESUME_ARGUMENT> procedureCallState =
                new ProcedureCallState<>(
                        // isInitializationCheck
                        false ,
                        this ,
                        resultType ,
                        //procedureArgumentValues
                        arguments ,
                        parent );

        return procedureCallState;
    }

    public ProcedureCallState<RESULT , RESUME_ARGUMENT> newStateForCheck(
            final CoroutineOrProcedureOrComplexstep<RESULT, RESUME_ARGUMENT> parent )
    {
        final Arguments arguments =
                new Arguments(
                        // isInitializationCheck
                        true ,
                        // checkMandantoryValues
                        false ,
                        //params
                        parent.getProcedure( this.procedureName ).params ,
                        //args
                        null ,
                        parent );

        final ProcedureCallState<RESULT , RESUME_ARGUMENT> procedureCallState =
                new ProcedureCallState<>(
                        // isInitializationCheck
                        true ,
                        this ,
                        resultType ,
                        //procedureArgumentValues
                        arguments ,
                        parent );

        return procedureCallState;
    }

    @Override
    public List<BreakOrContinue<? , ?>> getUnresolvedBreaksOrContinues(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstep<RESULT, RESUME_ARGUMENT> parent )
    {
        if ( alreadyCheckedProcedureNames.contains( this.procedureName ) )
        {
            return Collections.emptyList();
        }

        alreadyCheckedProcedureNames.add( procedureName );

        //return this.procedure.bodyComplexStep.getUnresolvedBreaksOrContinues( parent );
        return parent.getProcedure( this.procedureName ).bodyComplexStep.getUnresolvedBreaksOrContinues(
                alreadyCheckedProcedureNames ,
                parent );
    }

    @Override
    public void checkLabelAlreadyInUse(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstep<RESULT, RESUME_ARGUMENT> parent ,
            final Set<String> labels )
    {
        if ( alreadyCheckedProcedureNames.contains( procedureName ) )
        {
            return;
        }

        alreadyCheckedProcedureNames.add( procedureName );

        //this.procedure.checkLabelAlreadyInUse();
        parent.getProcedure( this.procedureName ).checkLabelAlreadyInUse(
                alreadyCheckedProcedureNames ,
                parent );
    }

    @Override
    public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    {
        // all subsequent GetProcedureArgument are in procedure
        return Collections.emptyList();
    }

    /**
     * @see CoroIterStep#setResultType(Class)
     */
    @Override
    public void setResultType(
            final Class<? extends RESULT> resultType )
    {
        this.resultType = resultType;
    }

    @Override
    public void checkUseVariables(
            //final boolean isCoroutineRoot ,
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstep<?, ?> parent ,
            final Map<String, Class<?>> globalVariableTypes ,
            final Map<String, Class<?>> localVariableTypes )
    {
        if ( alreadyCheckedProcedureNames.contains( procedureName ) )
        {
            return;
        }

        alreadyCheckedProcedureNames.add( procedureName );

        parent.getProcedure( this.procedureName ).bodyComplexStep.checkUseVariables(
                //isCoroutineRoot
                //false ,
                alreadyCheckedProcedureNames ,
                parent ,
                globalVariableTypes ,
                //localVariableTypes
                new HashMap<>() );
    }

    @Override
    public void checkUseArguments(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstep<?, ?> parent )
    {
        for ( final Argument<?> procArg : this.procedureArguments )
        {
            procArg.checkUseArguments(
                    alreadyCheckedProcedureNames,
                    //parent
                    this.newStateForCheck(
                            (CoroutineOrProcedureOrComplexstep<RESULT, RESUME_ARGUMENT>) parent ) );
        }

        if ( alreadyCheckedProcedureNames.contains( procedureName ) )
        {
            return;
        }

        alreadyCheckedProcedureNames.add( procedureName );

        parent.getProcedure( this.procedureName ).bodyComplexStep.checkUseArguments(
                alreadyCheckedProcedureNames ,
                //parent
                this.newStateForCheck(
                        (CoroutineOrProcedureOrComplexstep<RESULT, RESUME_ARGUMENT>) parent ) );
    }

    @Override
    public String toString(
            final CoroutineOrProcedureOrComplexstep<RESULT, RESUME_ARGUMENT> parent ,
            final String indent ,
            final ComplexStepState<?, ?, RESULT , RESUME_ARGUMENT> lastStepExecuteState ,
            final ComplexStepState<?, ?, RESULT , RESUME_ARGUMENT> nextStepExecuteState )
    {
        @SuppressWarnings("unchecked")
        final ProcedureCallState<RESULT /*, PARENT*/ , RESUME_ARGUMENT> lastProcExecuteState =
                (ProcedureCallState<RESULT /*, PARENT*/ , RESUME_ARGUMENT>) lastStepExecuteState;

        @SuppressWarnings("unchecked")
        final ProcedureCallState<RESULT /*, PARENT*/ , RESUME_ARGUMENT> nextProcExecuteState =
                (ProcedureCallState<RESULT /*, PARENT*/ , RESUME_ARGUMENT>) nextStepExecuteState;

        final ComplexStepState<?, ?, RESULT /*, PARENT*/ , RESUME_ARGUMENT> lastBodyState;
        if ( lastProcExecuteState != null )
        {
            lastBodyState = lastProcExecuteState.bodyComplexState;
        }
        else
        {
            lastBodyState = null;
        }

        final ComplexStepState<?, ?, RESULT /*, PARENT*/ , RESUME_ARGUMENT> nextBodyState;
        if ( nextProcExecuteState != null )
        {
            nextBodyState = nextProcExecuteState.bodyComplexState;
        }
        else
        {
            nextBodyState = null;
        }

        final String procedureArgumentsStr;
        if ( nextProcExecuteState == null )
        //if ( lastBodyState == null &&
        //        nextBodyState == null )
        {
            procedureArgumentsStr = "";
        }
        else
        {
            procedureArgumentsStr =
                    indent + " " +
                            //"procedure arguments: " +
                            "procedure argument values: " +
                            nextProcExecuteState.arguments +
                            "\n";
        }

        //final String procedureVariablesStr;
        //if ( nextProcExecuteState == null ||
        //        ( lastBodyState == null &&
        //        nextBodyState == null ) )
        //{
        //    procedureVariablesStr = "";
        //}
        //else
        //{
        //    procedureVariablesStr =
        //            indent + " " +
        //                    "procedure variables: " +
        //                    nextProcExecuteState.variables +
        //                    "\n";
        //}

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
                    //this.procedure.bodyComplexStep.toString(
                    parent.getProcedure( this.procedureName ).bodyComplexStep.toString(
                            parent ,
                            indent + " " ,
                            lastBodyState ,
                            nextBodyState );
        }

        final String procedureArgumentExpressionsStr =
                indent + " " +
                "procedure argument expressions: " +
                ArrayDeepToString.deepToString( this.procedureArguments ) +
                "\n";


        return
                indent +
                //( this.label != null ? this.label + " : " : "" ) +
                this.getClass().getSimpleName() +
                " " +
                this.procedureName +
                //" (" +
                ( this.creationStackTraceElement != null ? " " + this.creationStackTraceElement : "" ) +
                "\n" +
                procedureArgumentExpressionsStr +
                procedureArgumentsStr +
                //procedureVariablesStr +
                procedureBodyComplexStepStr;
    }

}
