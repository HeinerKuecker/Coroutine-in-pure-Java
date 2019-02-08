package de.heinerkuecker.coroutine.stmt.flow;

import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.CoroutineOrFunctioncallOrComplexstmt;
import de.heinerkuecker.coroutine.stmt.CoroStmt;
import de.heinerkuecker.coroutine.stmt.CoroStmtResult;
import de.heinerkuecker.coroutine.stmt.complex.DoWhile;
import de.heinerkuecker.coroutine.stmt.complex.For;
import de.heinerkuecker.coroutine.stmt.complex.ForEach;
import de.heinerkuecker.coroutine.stmt.complex.While;
import de.heinerkuecker.coroutine.stmt.simple.SimpleStmt;

/**
 * Stmt {@link CoroStmt}
 * to break current loop like
 * {@link For},
 * {@link ForEach},
 * {@link While} or
 * {@link DoWhile}.
 *
 * @param <COROUTINE_RETURN> result type of method {@link CoroutineIterator#next()}
 * @author Heiner K&uuml;cker
 */
public class Break<FUNCTION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT>
//implements SimpleStmt<COROUTINE_RETURN, CoroutineIterator<COROUTINE_RETURN>>
extends BreakOrContinue<FUNCTION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT>
{
    /**
     * Label of the loop
     * to break.
     *
     * TODO move member to super class
     */
    public final String label;

    /**
     * Constructor without label.
     */
    public Break()
    {
        this.label = null;
    }

    /**
     * Constructor.
     */
    public Break(
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
    public CoroStmtResult<FUNCTION_RETURN , COROUTINE_RETURN> execute(
            final CoroutineOrFunctioncallOrComplexstmt<FUNCTION_RETURN , COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        return new CoroStmtResult.Break<FUNCTION_RETURN , COROUTINE_RETURN>( this.label );
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
