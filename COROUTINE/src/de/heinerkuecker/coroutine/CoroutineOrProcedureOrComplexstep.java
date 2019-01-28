package de.heinerkuecker.coroutine;

import de.heinerkuecker.coroutine.step.complex.ComplexStep;
import de.heinerkuecker.coroutine.step.complex.ComplexStepState;
import de.heinerkuecker.coroutine.step.complex.ProcedureCall;

/**
 * Common interface for
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

    // TODO wird dies noch gebraucht
    //abstract CoroutineIterator<COROUTINE_RETURN> getRootParent();

    // TODO remove, was intented for glabal variables
    //abstract boolean isCoroutineRoot();

    abstract public Procedure<COROUTINE_RETURN, RESUME_ARGUMENT> getProcedure(
            final String procedureName );
}
