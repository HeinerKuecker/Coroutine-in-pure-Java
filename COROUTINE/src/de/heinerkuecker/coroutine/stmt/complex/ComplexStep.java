package de.heinerkuecker.coroutine.stmt.complex;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep;
import de.heinerkuecker.coroutine.stmt.CoroIterStep;
import de.heinerkuecker.coroutine.stmt.flow.BreakOrContinue;

/**
 * Interface for {@link CoroIterStep}
 * to non simple steps like
 * {@link While}.
 *
 * @param <STEP>       type of the complex step, resursivly defined
 * @param <STEP_STATE> type of the complex step execute state, resursivly defined
 * @param <COROUTINE_RETURN>     result type of method {@link CoroutineIterator#next()}
 * @param <PARENT>     type the {@link CoroutineIterator} instance
 * @author Heiner K&uuml;cker
 */
//public interface ComplexStep<STEP extends ComplexStep<STEP, COROUTINE_RETURN, PARENT>, COROUTINE_RETURN, PARENT extends CoroutineIterator<COROUTINE_RETURN>>
abstract public class ComplexStep<
    STEP extends ComplexStep<STEP, STEP_STATE, COROUTINE_RETURN/*, PARENT*/, RESUME_ARGUMENT>,
    STEP_STATE extends ComplexStepState<STEP_STATE, STEP, COROUTINE_RETURN/*, PARENT*/, RESUME_ARGUMENT>,
    COROUTINE_RETURN ,
    //PARENT extends CoroutineOrProcedureOrComplexstep<COROUTINE_RETURN/*, PARENT*/>
    RESUME_ARGUMENT
    >
extends CoroIterStep<COROUTINE_RETURN/*, PARENT*/>
{
    /**
     * @param creationStackOffset
     */
    protected ComplexStep(
            final int creationStackOffset )
    {
        super( creationStackOffset );
    }

    // TODO rename to newExecuteState
    //abstract public ComplexStepState<ComplexStepState<?, STEP, COROUTINE_RETURN, PARENT>, STEP, COROUTINE_RETURN, PARENT> newState();
    abstract public STEP_STATE newState(
            final CoroutineOrProcedureOrComplexstep<COROUTINE_RETURN, RESUME_ARGUMENT> parent );

    abstract public List<BreakOrContinue<?, ?>> getUnresolvedBreaksOrContinues(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstep<COROUTINE_RETURN, RESUME_ARGUMENT> parent );

    abstract public void checkLabelAlreadyInUse(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstep<COROUTINE_RETURN, RESUME_ARGUMENT> parent ,
            final Set<String> labels );

    /**
     * Returns formatted {@link String}
     * for debug purposes.
     *
     * @param indent indentation {@link String}
     * @param lastStepExecuteState stack and instruction pointer when this step is current executed
     * @param nextStepExecuteState stack and instruction pointer when this step is current executed
     * @return formatted {@link String}
     */
    abstract public String toString(
            final CoroutineOrProcedureOrComplexstep<COROUTINE_RETURN, RESUME_ARGUMENT> parent ,
            final String indent ,
            final ComplexStepState<?, ?, COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> lastStepExecuteState ,
            //final STEP_STATE lastStepExecuteState ,
            final ComplexStepState<?, ?, COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> nextStepExecuteState
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
