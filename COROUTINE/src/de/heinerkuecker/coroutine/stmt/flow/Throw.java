package de.heinerkuecker.coroutine.stmt.flow;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineOrFunctioncallOrComplexstmt;
import de.heinerkuecker.coroutine.exprs.CoroExpression;
import de.heinerkuecker.coroutine.exprs.GetFunctionArgument;
import de.heinerkuecker.coroutine.exprs.Value;
import de.heinerkuecker.coroutine.stmt.CoroStmt;
import de.heinerkuecker.coroutine.stmt.CoroStmtResult;
import de.heinerkuecker.coroutine.stmt.simple.SimpleStmt;
import de.heinerkuecker.util.ExceptionUnchecker;

public class Throw<FUNCTION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT>
extends SimpleStmt<FUNCTION_RETURN , COROUTINE_RETURN/*, CoroutineIterator<COROUTINE_RETURN>*/ , RESUME_ARGUMENT>
{
    private final CoroExpression<? extends Throwable> exceptionExpression;

    /**
     * Constructor.
     *
     * @param exceptionExpression
     */
    public Throw(
            //final Exception exception
            final CoroExpression<? extends Throwable> exceptionExpression )
    {
        this.exceptionExpression =
                Objects.requireNonNull(
                        exceptionExpression );
    }

    /**
     * Constructor.
     *
     * @param exception
     */
    public Throw(
            final Throwable exception )
    {
        this.exceptionExpression =
                new Value<Throwable>(
                        Objects.requireNonNull(
                                exception ) );
    }

    /**
     * @see SimpleStmt#execute
     */
    @Override
    public CoroStmtResult<FUNCTION_RETURN , COROUTINE_RETURN> execute(
            final CoroutineOrFunctioncallOrComplexstmt<FUNCTION_RETURN , COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        final Throwable exception = exceptionExpression.evaluate( parent );

        return ExceptionUnchecker.returnRethrow( exception );
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return
                this.getClass().getSimpleName() +
                " " +
                this.exceptionExpression +
                ( this.creationStackTraceElement != null
                    ? " " + this.creationStackTraceElement
                    : "" );
    }

    @Override
    public List<GetFunctionArgument<?>> getFunctionArgumentGetsNotInFunction()
    {
        return Collections.emptyList();
    }

    /**
     * @see CoroStmt#setCoroutineReturnType(Class)
     */
    @Override
    public void setCoroutineReturnType(
            final Class<? extends COROUTINE_RETURN> coroutineReturnType )
    {
        // do nothing
    }

    @Override
    public void checkUseVariables(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent ,
            final Map<String, Class<?>> globalVariableTypes ,
            final Map<String, Class<?>> localVariableTypes )
    {
        this.exceptionExpression.checkUseVariables(alreadyCheckedFunctionNames, parent, globalVariableTypes, localVariableTypes);
    }

    @Override
    public void checkUseArguments(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent )
    {
        this.exceptionExpression.checkUseArguments(alreadyCheckedFunctionNames, parent);
    }
}
