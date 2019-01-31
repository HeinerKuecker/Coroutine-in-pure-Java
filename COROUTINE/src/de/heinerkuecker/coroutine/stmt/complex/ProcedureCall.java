package de.heinerkuecker.coroutine.stmt.complex;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstmt;
import de.heinerkuecker.coroutine.arg.Argument;
import de.heinerkuecker.coroutine.arg.Arguments;
import de.heinerkuecker.coroutine.exprs.GetProcedureArgument;
import de.heinerkuecker.coroutine.stmt.CoroIterStmt;
import de.heinerkuecker.coroutine.stmt.flow.BreakOrContinue;
import de.heinerkuecker.util.ArrayDeepToString;

public class ProcedureCall<COROUTINE_RETURN/*, PARENT extends CoroutineOrProcedureOrComplexstmt<COROUTINE_RETURN, PARENT>*/ , RESUME_ARGUMENT>
extends ComplexStmt<
    ProcedureCall<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> ,
    ProcedureCallState<COROUTINE_RETURN , RESUME_ARGUMENT> ,
    COROUTINE_RETURN ,
    //PARENT
    RESUME_ARGUMENT
    >
//implements CoroutineOrProcedureOrComplexstmt<COROUTINE_RETURN/*, CoroutineIterator<COROUTINE_RETURN>*/>
{
    ///**
    // * Es muss ein ComplexStmt sein,
    // * weil dieser mit ComplexStmtState
    // * einen State hat, welcher bei
    // * einem SimpleStmt nicht vorhanden
    // * ist und dessen State in dieser
    // * Klasse verwaltet werden müsste.
    // */
    //final ComplexStmt<?, ?, COROUTINE_RETURN /*, /*PARENT* / CoroutineIterator<COROUTINE_RETURN>*/> bodyComplexStmt;

    //final Procedure<COROUTINE_RETURN> procedure;
    final String procedureName;

    // TODO getter
    //final Map<String, ProcedureArgument<?>> procedureArguments;
    final Argument<?>[] procedureArguments;

    /**
     * Reifier for type param {@link #COROUTINE_RETURN} to solve unchecked casts.
     */
    private Class<? extends COROUTINE_RETURN> resultType;

    /**
     * Constructor.
     *
     * @param creationStackOffset
     */
    //@SafeVarargs
    public ProcedureCall(
            //final CoroIterStmt<COROUTINE_RETURN/*, ? super PARENT/*CoroutineIterator<COROUTINE_RETURN>*/> ... bodyStmts
            //final Procedure<COROUTINE_RETURN> procedure
            final String procedureName ,
            final Argument<?>... args )
    {
        super(
                //creationStackOffset
                3 );

        //if (bodyStmts.length == 0 )
        //{
        //    throw new IllegalArgumentException( "procedure body is empty" );
        //}
        //
        //if ( bodyStmts.length == 1 &&
        //        bodyStmts[ 0 ] instanceof ComplexStmt )
        //{
        //    this.bodyComplexStmt =
        //            (ComplexStmt<?, ?, COROUTINE_RETURN/*, PARENT/*? super CoroutineIterator<COROUTINE_RETURN>*/>) bodyStmts[ 0 ];
        //}
        //else
        //{
        //    this.bodyComplexStmt =
        //            new Block(
        //                    // creationStackOffset
        //                    3 ,
        //                    bodyStmts );
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
     * @see ComplexStmt#newState
     */
    @Override
    public ProcedureCallState<COROUTINE_RETURN , RESUME_ARGUMENT> newState(
            final CoroutineOrProcedureOrComplexstmt<COROUTINE_RETURN, RESUME_ARGUMENT> parent )
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
                        // params
                        parent.getProcedure( this.procedureName ).params ,
                        // args
                        procedureArguments ,
                        parent );

        final ProcedureCallState<COROUTINE_RETURN , RESUME_ARGUMENT> procedureCallState =
                new ProcedureCallState<>(
                        // isInitializationCheck
                        false ,
                        this ,
                        resultType ,
                        // procedureArgumentValues
                        arguments ,
                        parent );

        return procedureCallState;
    }

    public ProcedureCallState<COROUTINE_RETURN , RESUME_ARGUMENT> newStateForCheck(
            final CoroutineOrProcedureOrComplexstmt<COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        final Arguments arguments =
                new Arguments(
                        // isInitializationCheck
                        true ,
                        // checkMandantoryValues
                        false ,
                        // params
                        parent.getProcedure( this.procedureName ).params ,
                        // args
                        null ,
                        parent );

        final ProcedureCallState<COROUTINE_RETURN , RESUME_ARGUMENT> procedureCallState =
                new ProcedureCallState<>(
                        // isInitializationCheck
                        true ,
                        this ,
                        resultType ,
                        // procedureArgumentValues
                        arguments ,
                        parent );

        return procedureCallState;
    }

    @Override
    public List<BreakOrContinue<? , ?>> getUnresolvedBreaksOrContinues(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstmt<COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        if ( alreadyCheckedProcedureNames.contains( this.procedureName ) )
        {
            return Collections.emptyList();
        }

        alreadyCheckedProcedureNames.add( procedureName );

        //return this.procedure.bodyComplexStmt.getUnresolvedBreaksOrContinues( parent );
        return parent.getProcedure( this.procedureName ).bodyComplexStmt.getUnresolvedBreaksOrContinues(
                alreadyCheckedProcedureNames ,
                parent );
    }

    @Override
    public void checkLabelAlreadyInUse(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstmt<COROUTINE_RETURN, RESUME_ARGUMENT> parent ,
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
     * @see CoroIterStmt#setResultType(Class)
     */
    @Override
    public void setResultType(
            final Class<? extends COROUTINE_RETURN> resultType )
    {
        this.resultType = resultType;
    }

    @Override
    public void checkUseVariables(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstmt<?, ?> parent ,
            final Map<String, Class<?>> globalVariableTypes ,
            final Map<String, Class<?>> localVariableTypes )
    {
        if ( alreadyCheckedProcedureNames.contains( procedureName ) )
        {
            return;
        }

        alreadyCheckedProcedureNames.add( procedureName );

        parent.getProcedure( this.procedureName ).bodyComplexStmt.checkUseVariables(
                alreadyCheckedProcedureNames ,
                parent ,
                globalVariableTypes ,
                // localVariableTypes
                new HashMap<>() );
    }

    @Override
    public void checkUseArguments(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstmt<?, ?> parent )
    {
        for ( final Argument<?> procArg : this.procedureArguments )
        {
            procArg.checkUseArguments(
                    alreadyCheckedProcedureNames,
                    // parent
                    this.newStateForCheck(
                            (CoroutineOrProcedureOrComplexstmt<COROUTINE_RETURN, RESUME_ARGUMENT>) parent ) );
        }

        if ( alreadyCheckedProcedureNames.contains( procedureName ) )
        {
            return;
        }

        alreadyCheckedProcedureNames.add( procedureName );

        parent.getProcedure( this.procedureName ).bodyComplexStmt.checkUseArguments(
                alreadyCheckedProcedureNames ,
                //parent
                this.newStateForCheck(
                        (CoroutineOrProcedureOrComplexstmt<COROUTINE_RETURN, RESUME_ARGUMENT>) parent ) );
    }

    @Override
    public String toString(
            final CoroutineOrProcedureOrComplexstmt<COROUTINE_RETURN, RESUME_ARGUMENT> parent ,
            final String indent ,
            final ComplexStmtState<?, ?, COROUTINE_RETURN , RESUME_ARGUMENT> lastStmtExecuteState ,
            final ComplexStmtState<?, ?, COROUTINE_RETURN , RESUME_ARGUMENT> nextStmtExecuteState )
    {
        @SuppressWarnings("unchecked")
        final ProcedureCallState<COROUTINE_RETURN /*, PARENT*/ , RESUME_ARGUMENT> lastProcExecuteState =
                (ProcedureCallState<COROUTINE_RETURN /*, PARENT*/ , RESUME_ARGUMENT>) lastStmtExecuteState;

        @SuppressWarnings("unchecked")
        final ProcedureCallState<COROUTINE_RETURN /*, PARENT*/ , RESUME_ARGUMENT> nextProcExecuteState =
                (ProcedureCallState<COROUTINE_RETURN /*, PARENT*/ , RESUME_ARGUMENT>) nextStmtExecuteState;

        final ComplexStmtState<?, ?, COROUTINE_RETURN /*, PARENT*/ , RESUME_ARGUMENT> lastBodyState;
        if ( lastProcExecuteState != null )
        {
            lastBodyState = lastProcExecuteState.bodyComplexState;
        }
        else
        {
            lastBodyState = null;
        }

        final ComplexStmtState<?, ?, COROUTINE_RETURN /*, PARENT*/ , RESUME_ARGUMENT> nextBodyState;
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

        final String procedureBodyComplexStmtStr;
        if ( lastBodyState == null &&
                nextBodyState == null )
        {
            procedureBodyComplexStmtStr = "";
        }
        else
            // print procedure body only when next or last source position is in procedure
        {
            procedureBodyComplexStmtStr =
                    //this.procedure.bodyComplexStmt.toString(
                    parent.getProcedure( this.procedureName ).bodyComplexStmt.toString(
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
                procedureBodyComplexStmtStr;
    }

}
