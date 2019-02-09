package de.heinerkuecker.coroutine.stmt.simple;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.CoroutineOrFunctioncallOrComplexstmt;
import de.heinerkuecker.coroutine.exprs.CoroExpression;
import de.heinerkuecker.coroutine.exprs.GetFunctionArgument;
import de.heinerkuecker.coroutine.stmt.CoroStmt;
import de.heinerkuecker.coroutine.stmt.CoroStmtResult;
import de.heinerkuecker.util.ArrayDeepToString;

/**
 * Statement {@link CoroStmt} to
 * print with newline to
 * {@link System#out}.
 *
 * Use this class as template
 * for your own log statement.
 *
 * @param <COROUTINE_RETURN> result type of method {@link CoroutineIterator#next()}
 * @author Heiner K&uuml;cker
 */
public final class SystemOutPrintln<FUNCTION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT>
extends SimpleStmt<FUNCTION_RETURN , COROUTINE_RETURN/*, CoroutineIterator<COROUTINE_RETURN>*/ , RESUME_ARGUMENT>
{
    /**
     * Expression to deliver value to print.
     */
    public final CoroExpression<? , COROUTINE_RETURN> outputExpression;

    /**
     * Constructor.
     *
     * @param creationStackOffset
     * @param outputExpression
     */
    public SystemOutPrintln(
            final CoroExpression<? , COROUTINE_RETURN> outputExpression )
    {
        this.outputExpression =
                Objects.requireNonNull(
                        outputExpression );
    }

    /**
     * print with newline to
     * {@link System#out}.
     */
    @Override
    public CoroStmtResult<FUNCTION_RETURN , COROUTINE_RETURN> execute(
            final CoroutineOrFunctioncallOrComplexstmt<FUNCTION_RETURN , COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        System.out.println( ArrayDeepToString.deepToString( outputExpression.evaluate( parent ) ) );
        return CoroStmtResult.continueCoroutine();
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
        this.outputExpression.setExprCoroutineReturnType(
                alreadyCheckedFunctionNames ,
                parent ,
                coroutineReturnType );
    }

    @Override
    public void checkUseVariables(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent ,
            final Map<String, Class<?>> globalVariableTypes ,
            final Map<String, Class<?>> localVariableTypes )
    {
        this.outputExpression.checkUseVariables(
                alreadyCheckedFunctionNames ,
                parent ,
                globalVariableTypes, localVariableTypes );
    }

    @Override
    public void checkUseArguments(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent )
    {
        this.outputExpression.checkUseArguments( alreadyCheckedFunctionNames, parent );
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
                this.outputExpression +
                " " +
                ( this.creationStackTraceElement != null
                    ? " " + this.creationStackTraceElement
                    : "" );
    }

}
