package de.heinerkuecker.coroutine;

import java.util.Map;

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
{
    /**
     * Save last step state for {@link ComplexStep#toString(String, ComplexStepState, ComplexStepState)}
     */
    abstract public void saveLastStepState();

    /**
     * @return Map with local variables
     */
    abstract public Map<String, Object> localVars();

    /**
     * @return Map with global variables
     */
    abstract public Map<String, Object> globalVars();

    /**
     * @return Map with procedure arguments
     */
    abstract public Map<String, Object> procedureArgumentValues();

    abstract CoroutineIterator<RESULT> getRootParent();

    abstract public Procedure<RESULT> getProcedure(
            final String procedureName );
}
