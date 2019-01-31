package de.heinerkuecker.coroutine.stmt.flow;

import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstmt;
import de.heinerkuecker.coroutine.stmt.CoroIterStmt;
import de.heinerkuecker.coroutine.stmt.CoroIterStmtResult;
import de.heinerkuecker.coroutine.stmt.complex.DoWhile;
import de.heinerkuecker.coroutine.stmt.complex.For;
import de.heinerkuecker.coroutine.stmt.complex.ForEach;
import de.heinerkuecker.coroutine.stmt.complex.While;
import de.heinerkuecker.coroutine.stmt.simple.SimpleStmt;

/**
 * Stmt {@link CoroIterStmt}
 * to continue current loop like
 * {@link For},
 * {@link ForEach},
 * {@link While} or
 * {@link DoWhile}.
 *
 * @param <COROUTINE_RETURN> result type of method {@link CoroutineIterator#next()}
 * @author Heiner K&uuml;cker
 */
public class Continue<COROUTINE_RETURN , RESUME_ARGUMENT>
//implements SimpleStmt<COROUTINE_RETURN, CoroutineIterator<COROUTINE_RETURN>>
extends BreakOrContinue<COROUTINE_RETURN , RESUME_ARGUMENT>
{
    /**
     * Label of the loop
     * to continue.
     *
     * TODO move member to super class
     */
    public final String label;

    /**
     * Constructor without label.
     */
    public Continue()
    {
        this.label = null;
    }

    /**
     * Constructor.
     *
     * @param label label
     */
    public Continue(
            final String label )
    {
        this.label = label;
    }

    /**
     * Decrement variable.
     *
     * @see SimpleStmt#execute
     */
    @Override
    public CoroIterStmtResult<COROUTINE_RETURN> execute(
            final CoroutineOrProcedureOrComplexstmt<COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        return new CoroIterStmtResult.ContinueLoop<COROUTINE_RETURN>( this.label );
    }

    /**
     * @see BreakOrContinue#getLabel()
     */
    @Override
    public String getLabel()
    {
        return this.label;
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return
                this.getClass().getSimpleName() +
                ( this.label != null ? " " + this.label  : "" ) +
                ( this.creationStackTraceElement != null
                    ? " " + this.creationStackTraceElement
                    : "" );
    }
}
