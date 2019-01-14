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
 * @param <RESULT> result type of method {@link CoroutineIterator#next()}
 * @param <PARENT> type the {@link CoroutineIterator} instance
 * @author Heiner K&uuml;cker
 */
//public interface CoroIteratorOrProcedure<RESULT, /*PARENT*/ THIS extends CoroIteratorOrProcedure<RESULT, /*PARENT*/ THIS>>
public interface CoroIteratorOrProcedure<RESULT>
extends HasArgumentsAndVariables
{
    /**
     * Save last step state for {@link ComplexStep#toString(String, ComplexStepState, ComplexStepState)}
     */
    abstract public void saveLastStepState();

    abstract CoroutineIterator<RESULT> getRootParent();

    abstract boolean isCoroutineRoot();

    abstract public Procedure<RESULT> getProcedure(
            final String procedureName );
}
