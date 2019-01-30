package de.heinerkuecker.coroutine;

import de.heinerkuecker.coroutine.stmt.complex.ComplexStep;
import de.heinerkuecker.coroutine.stmt.complex.ComplexStepState;
import de.heinerkuecker.coroutine.stmt.complex.ProcedureCall;

/**
 * Common interface for
 * {@link Coroutine},
 * {@link CoroutineIterator} and
 * {@link ProcedureCall}
 * to satisfy Java generics.
 *
 * @param <COROUTINE_RETURN> result type of method {@link CoroutineIterator#next()}
 * @param <PARENT> type the {@link CoroutineIterator} instance
 * @author Heiner K&uuml;cker
 */
//public interface CoroutineOrProcedureOrComplexstep<COROUTINE_RETURN, /*PARENT*/ THIS extends CoroutineOrProcedureOrComplexstep<COROUTINE_RETURN, /*PARENT*/ THIS>>
public interface CoroutineOrProcedureOrComplexstep<COROUTINE_RETURN, RESUME_ARGUMENT>
extends HasArgumentsAndVariables<RESUME_ARGUMENT>
{
    /**
     * Save last step state for {@link ComplexStep#toString(String, ComplexStepState, ComplexStepState)}.
     *
     * Call it before execute expressions or simple statements
     */
    abstract public void saveLastStepState();

    // TODO is this needed?
    //abstract CoroutineIterator<COROUTINE_RETURN> getRootParent();

    abstract public Procedure<COROUTINE_RETURN, RESUME_ARGUMENT> getProcedure(
            final String procedureName );
}
