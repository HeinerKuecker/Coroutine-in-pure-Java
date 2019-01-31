package de.heinerkuecker.coroutine.stmt.simple;

import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstmt;
import de.heinerkuecker.coroutine.stmt.CoroIterStmt;
import de.heinerkuecker.coroutine.stmt.CoroIterStmtResult;

/**
 * Interface for one statement
 * in {@link CoroutineIterator}.
 *
 * @param <COROUTINE_RETURN> result type of method {@link CoroutineIterator#next()}
 * @param <PARENT> type the {@link CoroutineIterator} instance
 * @author Heiner K&uuml;cker
 */
abstract public class SimpleStmt<COROUTINE_RETURN/*, PARENT*/, RESUME_ARGUMENT>
extends CoroIterStmt<COROUTINE_RETURN /*,PARENT*/>
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
    abstract public CoroIterStmtResult<COROUTINE_RETURN> execute(
            //final PARENT parent
            final CoroutineOrProcedureOrComplexstmt<COROUTINE_RETURN, RESUME_ARGUMENT> parent );
}
