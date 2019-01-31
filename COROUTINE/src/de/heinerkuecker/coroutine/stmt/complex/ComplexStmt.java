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
 * to non simple statements like
 * {@link While}.
 *
 * @param <STEP>       type of the complex stmt, resursivly defined
 * @param <STEP_STATE> type of the complex stmt execute state, resursivly defined
 * @param <COROUTINE_RETURN>     result type of method {@link CoroutineIterator#next()}
 * @param <PARENT>     type the {@link CoroutineIterator} instance
 * @author Heiner K&uuml;cker
 */
//public interface ComplexStep<STEP extends ComplexStep<STEP, COROUTINE_RETURN, PARENT>, COROUTINE_RETURN, PARENT extends CoroutineIterator<COROUTINE_RETURN>>
abstract public class ComplexStmt<
    STEP extends ComplexStmt<STEP, STEP_STATE, COROUTINE_RETURN/*, PARENT*/, RESUME_ARGUMENT>,
    STEP_STATE extends ComplexStmtState<STEP_STATE, STEP, COROUTINE_RETURN/*, PARENT*/, RESUME_ARGUMENT>,
    COROUTINE_RETURN ,
    //PARENT extends CoroutineOrProcedureOrComplexstmt<COROUTINE_RETURN/*, PARENT*/>
    RESUME_ARGUMENT
    >
extends CoroIterStmt<COROUTINE_RETURN/*, PARENT*/>
{
    /**
     * @param creationStackOffset
     */
    protected ComplexStmt(
            final int creationStackOffset )
    {
        super( creationStackOffset );
    }

    // TODO rename to newExecuteState
    //abstract public ComplexStepState<ComplexStepState<?, STEP, COROUTINE_RETURN, PARENT>, STEP, COROUTINE_RETURN, PARENT> newState();
    abstract public STEP_STATE newState(
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
     * @param lastStepExecuteState stack and instruction pointer when this stmt is current executed
     * @param nextStepExecuteState stack and instruction pointer when this stmt is current executed
     * @return formatted {@link String}
     */
    abstract public String toString(
            final CoroutineOrProcedureOrComplexstmt<COROUTINE_RETURN, RESUME_ARGUMENT> parent ,
            final String indent ,
            final ComplexStmtState<?, ?, COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> lastStepExecuteState ,
            //final STEP_STATE lastStepExecuteState ,
            final ComplexStmtState<?, ?, COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> nextStepExecuteState
            //final STEP_STATE nextStepExecuteState
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
                //lastStepExecuteState
                null ,
                //nextStepExecuteState
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
