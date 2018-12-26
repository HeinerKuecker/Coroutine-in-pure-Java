package de.heinerkuecker.coroutine_iterator;

import java.util.Map;

import de.heinerkuecker.coroutine_iterator.step.complex.ComplexStep;
import de.heinerkuecker.coroutine_iterator.step.complex.ComplexStepState;
import de.heinerkuecker.coroutine_iterator.step.complex.Procedure;

/**
 * Common interface for
 * {@link CoroutineIterator} and
 * {@link Procedure}
 * to satisfy Java generics.
 *
 * @param <RESULT> result type of method {@link CoroutineIterator#next()}
 * @param <PARENT> type the {@link CoroutineIterator} instance
 * @author Heiner K&uuml;cker
 */
//public interface CoroIteratorOrProcedure<RESULT, /*PARENT*/ THIS extends CoroIteratorOrProcedure<RESULT, /*PARENT*/ THIS>>
public interface CoroIteratorOrProcedure<RESULT>
{
    // no implementation

    /**
     * Save last step state for {@link ComplexStep#toString(String, ComplexStepState, ComplexStepState)}
     */
    abstract public void saveLastStepState();

    /**
     * @return Map with variables
     */
    abstract public Map<String, Object> vars();
}
