package de.heinerkuecker.coroutine;

import de.heinerkuecker.coroutine.stmt.complex.ComplexStmt;
import de.heinerkuecker.coroutine.stmt.complex.ComplexStmtState;
import de.heinerkuecker.coroutine.stmt.complex.FunctionCall;

/**
 * Common interface for
 * {@link Coroutine},
 * {@link CoroutineIterator} and
 * {@link FunctionCall}
 * to satisfy Java generics.
 *
 * @param <COROUTINE_RETURN> result type of method {@link CoroutineIterator#next()}
 * @param <PARENT> type the {@link CoroutineIterator} instance
 * @author Heiner K&uuml;cker
 */
//public interface CoroutineOrFunctioncallOrComplexstmt<COROUTINE_RETURN, /*PARENT*/ THIS extends CoroutineOrFunctioncallOrComplexstmt<COROUTINE_RETURN, /*PARENT*/ THIS>>
public interface CoroutineOrFunctioncallOrComplexstmt<FUNCTION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT>
extends HasArgumentsAndVariables<RESUME_ARGUMENT>
{
    /**
     * Save last statement state for {@link ComplexStmt#toString}.
     *
     * Call it before execute expressions or simple statements
     */
    abstract public void saveLastStmtState();

    // TODO is this needed?
    //abstract CoroutineIterator<COROUTINE_RETURN> getRootParent();

    abstract public Function</*FUNCTION_RETURN*/ ? , COROUTINE_RETURN, RESUME_ARGUMENT> getFunction(
            final String functionName );
}
