package de.heinerkuecker.coroutine.stmt.ret;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.CoroutineOrFunctioncallOrComplexstmt;
import de.heinerkuecker.coroutine.exprs.GetFunctionArgument;
import de.heinerkuecker.coroutine.exprs.SimpleExpression;
import de.heinerkuecker.coroutine.exprs.Value;
import de.heinerkuecker.coroutine.exprs.exc.WrongExpressionResultValueClassException;
import de.heinerkuecker.coroutine.stmt.CoroStmt;
import de.heinerkuecker.coroutine.stmt.CoroStmtResult;
import de.heinerkuecker.coroutine.stmt.simple.SimpleStmt;

/**
 * Statement {@link CoroStmt} to
 * return a specified value
 * and stop run.
 *
 * @param <COROUTINE_RETURN> result type of method {@link CoroutineIterator#next()}
 * @author Heiner K&uuml;cker
 */
public class FinallyReturn<FUNCTION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT>
extends SimpleStmt<FUNCTION_RETURN , COROUTINE_RETURN/*, CoroutineIterator<COROUTINE_RETURN>*/ , RESUME_ARGUMENT>
{
    public final SimpleExpression<? extends COROUTINE_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT> expression;

    /**
     * Reifier for type param {@link #COROUTINE_RETURN} to solve unchecked casts.
     */
    private Class<? extends COROUTINE_RETURN> coroutineReturnType;

    /**
     * Constructor.
     */
    public FinallyReturn(
            final SimpleExpression<? extends COROUTINE_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT> expression )
    {
        super(
                //creationStackOffset
                //2
                );

        this.expression =
                Objects.requireNonNull(
                        expression );
    }

    /**
     * Constructor.
     */
    public FinallyReturn(
            final COROUTINE_RETURN value )
    {
        super(
                //creationStackOffset
                //2
                );

        this.expression =
                new Value<COROUTINE_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT>(
                        (Class<? extends COROUTINE_RETURN>) value.getClass() ,
                        value );
    }

    /**
     * Compute result value and wrap it in finally return.
     */
    @Override
    public CoroStmtResult<FUNCTION_RETURN , COROUTINE_RETURN> execute(
            final CoroutineOrFunctioncallOrComplexstmt<FUNCTION_RETURN , COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        final COROUTINE_RETURN resultValue =
                expression.evaluate(
                        parent );

        if ( resultValue != null &&
                ! coroutineReturnType.isInstance( resultValue ) )
        {
            throw new WrongExpressionResultValueClassException(
                    //valueExpression
                    expression ,
                    //expectedClass
                    coroutineReturnType ,
                    //wrongValue
                    resultValue );
        }

        return new CoroStmtResult.FinallyReturnWithResult<FUNCTION_RETURN , COROUTINE_RETURN>(
                coroutineReturnType.cast(
                        resultValue ) );
    }

    /**
     * @see CoroStmt#getFunctionArgumentGetsNotInFunction()
     */
    @Override
    public List<GetFunctionArgument<? , ? , ?>> getFunctionArgumentGetsNotInFunction()
    {
        return expression.getFunctionArgumentGetsNotInFunction();
    }

    @Override
    public void setStmtCoroutineReturnTypeAndResumeArgumentType(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ? , ?> parent ,
            //final Class<? /*extends COROUTINE_RETURN*/> coroutineReturnType ,
            final Class<? extends COROUTINE_RETURN> coroutineReturnType ,
            final Class<? extends RESUME_ARGUMENT> resumeArgumentType )
    {
        this.coroutineReturnType = coroutineReturnType;
    }

    @Override
    public void checkUseVariables(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent ,
            final Map<String, Class<?>> globalVariableTypes ,
            final Map<String, Class<?>> localVariableTypes )
    {
        this.expression.checkUseVariables(
                alreadyCheckedFunctionNames ,
                parent ,
                globalVariableTypes, localVariableTypes );
    }

    @Override
    public void checkUseArguments(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent )
    {
        this.expression.checkUseArguments(
                alreadyCheckedFunctionNames ,
                parent );
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return
                this.getClass().getSimpleName() +
                "[" +
                expression +
                "]" +
                ( this.creationStackTraceElement != null
                    ? " " + this.creationStackTraceElement
                    : "" );
    }

}
