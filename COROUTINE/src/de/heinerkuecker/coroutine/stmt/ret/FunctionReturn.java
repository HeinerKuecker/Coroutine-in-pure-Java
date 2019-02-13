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
import de.heinerkuecker.coroutine.stmt.complex.FunctionCall;
import de.heinerkuecker.coroutine.stmt.simple.SimpleStmt;

/**
 * Stmt {@link CoroStmt} to
 * return a specified value
 * from a {@link FunctionCall}
 * and continue run.
 *
 * @param <COROUTINE_RETURN> result type of method {@link CoroutineIterator#next()}
 * @author Heiner K&uuml;cker
 */
public class FunctionReturn<FUNCTION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT>
extends SimpleStmt<FUNCTION_RETURN , COROUTINE_RETURN/*, CoroutineIterator<COROUTINE_RETURN>*/ , RESUME_ARGUMENT>
{
    /**
     * Reifier for type param {@link #FUNCTION_RETURN} to solve unchecked casts.
     */
    private final Class<? extends FUNCTION_RETURN> functionReturnType;

    public final SimpleExpression<? extends FUNCTION_RETURN , COROUTINE_RETURN> expression;

    /**
     * Constructor.
     */
    public FunctionReturn(
            final Class<? extends FUNCTION_RETURN> functionReturnType ,
            final SimpleExpression<? extends FUNCTION_RETURN , COROUTINE_RETURN> expression )
    {
        super(
                //creationStackOffset
                //2
                );

        this.functionReturnType =
                Objects.requireNonNull(
                        functionReturnType );

        this.expression =
                Objects.requireNonNull(
                        expression );
    }

    /**
     * Constructor.
     */
    public FunctionReturn(
            final Class<? extends FUNCTION_RETURN> functionReturnType ,
            final FUNCTION_RETURN value )
    {
        super(
                //creationStackOffset
                //2
                );

        this.functionReturnType =
                Objects.requireNonNull(
                        functionReturnType );

        this.expression =
                new Value<FUNCTION_RETURN , COROUTINE_RETURN>(
                        (Class<? extends FUNCTION_RETURN>) value.getClass() ,
                        value );
    }

    /**
     * Compute result value and wrap it in yield return.
     */
    @Override
    public CoroStmtResult<FUNCTION_RETURN , COROUTINE_RETURN> execute(
            final CoroutineOrFunctioncallOrComplexstmt<FUNCTION_RETURN , COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        final FUNCTION_RETURN resultValue =
                expression.evaluate(
                        parent );

        if ( resultValue != null &&
                ! functionReturnType.isInstance( resultValue ) )
        {
            throw new WrongExpressionResultValueClassException(
                    //valueExpression
                    expression ,
                    //expectedClass
                    functionReturnType ,
                    //wrongValue
                    resultValue );
        }

        System.out.println( "execute " + this );

        return new CoroStmtResult.FunctionReturnWithResult<FUNCTION_RETURN , COROUTINE_RETURN>(
                functionReturnType.cast(
                        resultValue ) );
    }

    /**
     * @see CoroStmt#getFunctionArgumentGetsNotInFunction()
     */
    @Override
    public List<GetFunctionArgument<? , ?>> getFunctionArgumentGetsNotInFunction()
    {
        return expression.getFunctionArgumentGetsNotInFunction();
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
