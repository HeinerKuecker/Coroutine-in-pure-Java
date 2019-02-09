package de.heinerkuecker.coroutine.stmt.ret;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.CoroutineOrFunctioncallOrComplexstmt;
import de.heinerkuecker.coroutine.exprs.GetFunctionArgument;
import de.heinerkuecker.coroutine.stmt.CoroStmt;
import de.heinerkuecker.coroutine.stmt.CoroStmtResult;
import de.heinerkuecker.coroutine.stmt.simple.SimpleStmt;

/**
 * Stmt {@link CoroStmt}
 * to stop run without
 * return a value.
 *
 * @param <COROUTINE_RETURN> result type of method {@link CoroutineIterator#next()}
 * @author Heiner K&uuml;cker
 */
public class FinallyReturnWithoutResult<FUNCTION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT>
extends SimpleStmt<FUNCTION_RETURN , COROUTINE_RETURN/*, CoroutineIterator<COROUTINE_RETURN>*/ , RESUME_ARGUMENT>
{
    /**
     * Constructor.
     */
    public FinallyReturnWithoutResult()
    {
        super(
                //creationStackOffset
                //2
                );
    }

    /**
     * Decrement variable.
     */
    @Override
    public CoroStmtResult<FUNCTION_RETURN , COROUTINE_RETURN> execute(
            final CoroutineOrFunctioncallOrComplexstmt<FUNCTION_RETURN , COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        return new CoroStmtResult.FinallyReturnWithoutResult<>();
    }

    @Override
    public List<GetFunctionArgument<? , ?>> getFunctionArgumentGetsNotInFunction()
    {
        return Collections.emptyList();
    }

    @Override
    public void setStmtCoroutineReturnType(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ? , ?> parent ,
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
        // nothing to do
    }

    @Override
    public void checkUseArguments(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent )
    {
        // nothing to do
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return
                this.getClass().getSimpleName() +
                 ( this.creationStackTraceElement != null
                     ? " " + this.creationStackTraceElement
                     : "" );
    }

}
