package de.heinerkuecker.coroutine.stmt.simple;

import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.CoroutineOrFunctioncallOrComplexstmt;
import de.heinerkuecker.coroutine.stmt.CoroStmt;
import de.heinerkuecker.coroutine.stmt.CoroStmtResult;
import de.heinerkuecker.coroutine.stmt.CoroStmtResult.YieldReturnWithResult;
import de.heinerkuecker.coroutine.stmt.ret.YieldReturn;

/**
 * Interface for one simple statement
 * in {@link CoroutineIterator}.
 *
 * A simple statement has no own state
 * and is not interruptable by
 * {@link YieldReturn} or
 * {@link YieldReturnWithResult}.
 *
 * @param <COROUTINE_RETURN> result type of method {@link CoroutineIterator#next()}
 * @param <PARENT> type the {@link CoroutineIterator} instance
 * @author Heiner K&uuml;cker
 */
abstract public class SimpleStmt<FUNCTION_RETURN , COROUTINE_RETURN/*, PARENT*/, RESUME_ARGUMENT>
extends CoroStmt<FUNCTION_RETURN , COROUTINE_RETURN /*,PARENT*/>
{
    /**
     * Constructor.
     *
     * @param creationStackOffset
     */
    protected SimpleStmt(
            final int creationStackOffset )
    {
        super( creationStackOffset );
    }

    /**
     * Constructor.
     */
    protected SimpleStmt()
    {
        super(
                //creationStackOffset
                3 );
    }

    /**
     * Execute one statement.
     *
     * @param parent
     * @return object to return a value and to control the flow
     */
    abstract public CoroStmtResult<FUNCTION_RETURN , COROUTINE_RETURN> execute(
            //final PARENT parent
            final CoroutineOrFunctioncallOrComplexstmt<FUNCTION_RETURN , COROUTINE_RETURN, RESUME_ARGUMENT> parent );
}
