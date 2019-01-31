package de.heinerkuecker.coroutine.stmt.complex;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstmt;
import de.heinerkuecker.coroutine.stmt.CoroIterStmt;
import de.heinerkuecker.coroutine.stmt.flow.BreakOrContinue;

/**
 * Interface for {@link CoroIterStmt}
 * to complex statements like
 * {@link While}.
 *
 * @param <STMT>       type of the complex statement, resursivly defined
 * @param <STMT_STATE> type of the complex statement execute state, resursivly defined
 * @param <COROUTINE_RETURN>     result type of method {@link CoroutineIterator#next()}
 * @param <PARENT>     type the {@link CoroutineIterator} instance
 * @author Heiner K&uuml;cker
 */
abstract public class ComplexStmt<
    STMT extends ComplexStmt<STMT, STMT_STATE, COROUTINE_RETURN/*, PARENT*/, RESUME_ARGUMENT>,
    STMT_STATE extends ComplexStmtState<STMT_STATE, STMT, COROUTINE_RETURN/*, PARENT*/, RESUME_ARGUMENT>,
    COROUTINE_RETURN ,
    //PARENT extends CoroutineOrProcedureOrComplexstmt<COROUTINE_RETURN/*, PARENT*/>
    RESUME_ARGUMENT
    >
extends CoroIterStmt<COROUTINE_RETURN/*, PARENT*/>
{
    /**
     * Constructor.
     *
     * @param creationStackOffset
     */
    protected ComplexStmt(
            final int creationStackOffset )
    {
        super( creationStackOffset );
    }

    // TODO rename to newExecuteState
    //abstract public ComplexStmtState<ComplexStmtState<?, STMT, COROUTINE_RETURN, PARENT>, STMT, COROUTINE_RETURN, PARENT> newState();
    abstract public STMT_STATE newState(
            final CoroutineOrProcedureOrComplexstmt<COROUTINE_RETURN, RESUME_ARGUMENT> parent );

    abstract public List<BreakOrContinue<?, ?>> getUnresolvedBreaksOrContinues(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstmt<COROUTINE_RETURN, RESUME_ARGUMENT> parent );

    abstract public void checkLabelAlreadyInUse(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstmt<COROUTINE_RETURN, RESUME_ARGUMENT> parent ,
            final Set<String> labels );

    /**
     * Returns formatted {@link String}
     * for debug purposes.
     *
     * @param indent indentation {@link String}
     * @param lastStmtExecuteState stack and instruction pointer when this stmt is current executed
     * @param nextStmtExecuteState stack and instruction pointer when this stmt is current executed
     * @return formatted {@link String}
     */
    abstract public String toString(
            final CoroutineOrProcedureOrComplexstmt<COROUTINE_RETURN, RESUME_ARGUMENT> parent ,
            final String indent ,
            final ComplexStmtState<?, ?, COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> lastStmtExecuteState ,
            //final STMT_STATE lastStmtExecuteState ,
            final ComplexStmtState<?, ?, COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> nextStmtExecuteState
            //final STMT_STATE nextStmtExecuteState
            );

    /**
     * @see Object#toString()
     */
    @Override
    public final String toString()
    {
        return toString(
                //parent
                null ,
                //indent
                "" ,
                //lastStmtExecuteState
                null ,
                //nextStmtExecuteState
                null );
    }

    protected String indentStrWithoutNextOrLastPart(
            final String indent )
    {
        return indent.substring(
                //beginIndex
                0 ,
                //endIndex
                indent.length() - "next:".length() );
    }

    protected String nextOrLastSpaceStr()
    {
        return "     ";
    }
}
