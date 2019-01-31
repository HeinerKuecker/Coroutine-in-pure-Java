package de.heinerkuecker.coroutine.stmt.complex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstmt;
import de.heinerkuecker.coroutine.exprs.GetProcedureArgument;
import de.heinerkuecker.coroutine.stmt.CoroIterStmt;
import de.heinerkuecker.coroutine.stmt.flow.BreakOrContinue;

public class TryCatch<COROUTINE_RETURN/*, PARENT extends CoroutineIterator<COROUTINE_RETURN>*/ , RESUME_ARGUMENT>
extends ComplexStmt<
    TryCatch<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT>,
    TryCatchState<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT>,
    COROUTINE_RETURN ,
    //PARENT
    RESUME_ARGUMENT
    >
{
    // TODO variable to enable access to exception

    final ComplexStmt<?, ?, COROUTINE_RETURN/*, PARENT/*CoroutineIterator<COROUTINE_RETURN>*/ , RESUME_ARGUMENT> tryBodyComplexStmt;
    final Class<? extends Throwable> catchExceptionClass;
    final String catchedExceptionVariableName;
    final ComplexStmt<?, ?, COROUTINE_RETURN/*, PARENT/*CoroutineIterator<COROUTINE_RETURN>*/ , RESUME_ARGUMENT> catchBodyComplexStmt;

    /**
     * Constructor.
     */
    @SafeVarargs
    public TryCatch(
            final CoroIterStmt<COROUTINE_RETURN/*, PARENT /*CoroutineIterator<COROUTINE_RETURN>*/> tryStmt ,
            final Class<? extends Throwable> catchExceptionClass ,
            final String catchedExceptionVariableName ,
            final CoroIterStmt<COROUTINE_RETURN/*, ? super PARENT/*CoroutineIterator<COROUTINE_RETURN>*/> ... catchBodyStmts )
    {
        super(
                //creationStackOffset
                3 );

        this.tryBodyComplexStmt =
                Block.convertStmtsToComplexStmt(
                        // creationStackOffset
                        4 ,
                        tryStmt );

        this.catchExceptionClass =
                Objects.requireNonNull(
                        catchExceptionClass );

        this.catchedExceptionVariableName =
                Objects.requireNonNull(
                        catchedExceptionVariableName );

        this.catchBodyComplexStmt =
                Block.convertStmtsToComplexStmt(
                        // creationStackOffset
                        4 ,
                        catchBodyStmts );
    }

    /**
     * Factroy method.
     */
    @SafeVarargs
    public static <COROUTINE_RETURN/*, PARENT extends CoroutineIterator<COROUTINE_RETURN>*/ , RESUME_ARGUMENT> TryCatch<COROUTINE_RETURN/*, PARENT*/, RESUME_ARGUMENT> newTryCatch(
            final CoroIterStmt<COROUTINE_RETURN/*, PARENT /*CoroutineIterator<COROUTINE_RETURN>*/> tryStmt ,
            final Class<? extends Throwable> catchExceptionClass ,
            final String catchedExceptionVariableName ,
            final CoroIterStmt<COROUTINE_RETURN/*, ? super PARENT/*CoroutineIterator<COROUTINE_RETURN>*/> ... catchBodyStmts )
    {
        return new TryCatch<>(
                tryStmt ,
                catchExceptionClass ,
                catchedExceptionVariableName ,
                catchBodyStmts);
    }

    @Override
    public TryCatchState<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> newState(
            final CoroutineOrProcedureOrComplexstmt<COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        return new TryCatchState<>(
                this ,
                parent );
    }

    @Override
    public List<BreakOrContinue<? , ?>> getUnresolvedBreaksOrContinues(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstmt<COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        final List<BreakOrContinue<? , ?>> result = new ArrayList<>();

        if ( tryBodyComplexStmt instanceof ComplexStmt )
        {
            result.addAll( ((ComplexStmt<?, ?, COROUTINE_RETURN , RESUME_ARGUMENT>) tryBodyComplexStmt).getUnresolvedBreaksOrContinues(
                    alreadyCheckedProcedureNames ,
                    parent ) );
        }

        if ( catchBodyComplexStmt instanceof ComplexStmt )
        {
            result.addAll( ((ComplexStmt<?, ?, COROUTINE_RETURN , RESUME_ARGUMENT>) catchBodyComplexStmt).getUnresolvedBreaksOrContinues(
                    alreadyCheckedProcedureNames ,
                    parent ) );
        }

        return result;
    }

    /**
     * @see CoroIterStmt#getProcedureArgumentGetsNotInProcedure()
     */
    @Override
    public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    {
        final List<GetProcedureArgument<?>> result = new ArrayList<>();

        result.addAll(
                tryBodyComplexStmt.getProcedureArgumentGetsNotInProcedure() );

        result.addAll(
                catchBodyComplexStmt.getProcedureArgumentGetsNotInProcedure() );

        return result;
    }

    /**
     * @see CoroIterStmt#setResultType(Class)
     */
    @Override
    public void setResultType(
            final Class<? extends COROUTINE_RETURN> resultType )
    {
        this.tryBodyComplexStmt.setResultType( resultType );
        this.catchBodyComplexStmt.setResultType( resultType );
    }

    @Override
    public void checkLabelAlreadyInUse(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstmt<COROUTINE_RETURN, RESUME_ARGUMENT> parent ,
            final Set<String> labels )
    {
        this.tryBodyComplexStmt.checkLabelAlreadyInUse(
                alreadyCheckedProcedureNames ,
                parent ,
                labels );

        this.catchBodyComplexStmt.checkLabelAlreadyInUse(
                alreadyCheckedProcedureNames ,
                parent ,
                labels );
    }

    @Override
    public void checkUseVariables(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstmt<?, ?> parent ,
            final Map<String, Class<?>> globalVariableTypes ,
            final Map<String, Class<?>> localVariableTypes )
    {
        this.tryBodyComplexStmt.checkUseVariables(
                alreadyCheckedProcedureNames ,
                parent ,
                globalVariableTypes ,
                localVariableTypes );

        final Map<String, Class<?>> catchLocalVariableTypes =
                new HashMap<String, Class<?>>(
                        localVariableTypes );

        catchLocalVariableTypes.put(
                catchedExceptionVariableName ,
                catchExceptionClass );

        this.catchBodyComplexStmt.checkUseVariables(
                alreadyCheckedProcedureNames ,
                parent ,
                globalVariableTypes ,
                catchLocalVariableTypes );
    }

    @Override
    public void checkUseArguments(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstmt<?, ?> parent )
    {
        this.tryBodyComplexStmt.checkUseArguments( alreadyCheckedProcedureNames, parent );
        this.catchBodyComplexStmt.checkUseArguments( alreadyCheckedProcedureNames, parent );
    }

    @Override
    public String toString(
            final CoroutineOrProcedureOrComplexstmt<COROUTINE_RETURN, RESUME_ARGUMENT> parent ,
            String indent ,
            ComplexStmtState<?, ?, COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> lastStmtExecuteState ,
            ComplexStmtState<?, ?, COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> nextStmtExecuteState )
    {
        @SuppressWarnings("unchecked")
        final TryCatchState<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> lastTryCatchExecuteState =
                (TryCatchState<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT>) lastStmtExecuteState;

        @SuppressWarnings("unchecked")
        final TryCatchState<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> nextTryCatchExecuteState =
                (TryCatchState<COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT>) nextStmtExecuteState;

        final ComplexStmtState<?, ?, COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> lastTryBodyState;
        if ( lastTryCatchExecuteState != null )
        {
            lastTryBodyState = lastTryCatchExecuteState.tryBodyComplexState;
        }
        else
        {
            lastTryBodyState = null;
        }

        final ComplexStmtState<?, ?, COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> nextTryBodyState;
        if ( nextTryCatchExecuteState != null )
        {
            nextTryBodyState = nextTryCatchExecuteState.tryBodyComplexState;
        }
        else
        {
            nextTryBodyState = null;
        }

        final ComplexStmtState<?, ?, COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> lastCatchBodyState;
        if ( lastTryCatchExecuteState != null )
        {
            lastCatchBodyState = lastTryCatchExecuteState.catchBodyComplexState;
        }
        else
        {
            lastCatchBodyState = null;
        }

        final ComplexStmtState<?, ?, COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> nextCatchBodyState;
        if ( nextTryCatchExecuteState != null )
        {
            nextCatchBodyState = nextTryCatchExecuteState.catchBodyComplexState;
        }
        else
        {
            nextCatchBodyState = null;
        }

        return
                indent +
                this.getClass().getSimpleName() +
                ( this.creationStackTraceElement != null ? " " + this.creationStackTraceElement : "" ) +
                "\n" +
                this.tryBodyComplexStmt.toString(
                        parent ,
                        indent + " " ,
                        lastTryBodyState ,
                        nextTryBodyState ) +
                indent + "catch ( " + this.catchExceptionClass.getName() + " )" + "\n" +
                this.catchBodyComplexStmt.toString(
                        parent ,
                        indent + " " ,
                        lastCatchBodyState ,
                        nextCatchBodyState );
    }

}
