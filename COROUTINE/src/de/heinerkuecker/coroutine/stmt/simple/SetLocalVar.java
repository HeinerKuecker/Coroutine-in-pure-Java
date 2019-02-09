package de.heinerkuecker.coroutine.stmt.simple;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineOrFunctioncallOrComplexstmt;
import de.heinerkuecker.coroutine.HasArgumentsAndVariables;
import de.heinerkuecker.coroutine.HasVariableName;
import de.heinerkuecker.coroutine.exprs.CoroExpression;
import de.heinerkuecker.coroutine.exprs.GetFunctionArgument;
import de.heinerkuecker.coroutine.exprs.GetLocalVar;
import de.heinerkuecker.coroutine.exprs.Value;
import de.heinerkuecker.coroutine.stmt.CoroStmt;
import de.heinerkuecker.coroutine.stmt.CoroStmtResult;

/**
 * {@link SimpleStmt} to set
 * local variable with specified
 * value or result of specified
 * expression.
 *
 * @author Heiner K&uuml;cker
 * @param <COROUTINE_RETURN> result type of coroutine, here unused
 */
public /*final*/ class SetLocalVar<FUNCTION_RETURN , COROUTINE_RETURN, RESUME_ARGUMENT , VARIABLE>
extends SimpleStmt<FUNCTION_RETURN , COROUTINE_RETURN/*, CoroutineIterator<COROUTINE_RETURN>*/ , RESUME_ARGUMENT>
implements CoroExpression<VARIABLE , COROUTINE_RETURN> , HasVariableName
{
    /**
     * Name of variable to set in
     * {@link CoroutineOrFunctioncallOrComplexstmt#localVars()}
     */
    public final String localVarName;

    /**
     * This is the expression whose result
     * should be set as the value of the variable.
     */
    public final CoroExpression<VARIABLE , COROUTINE_RETURN> varValueExpression;

    /**
     * Constructor.
     */
    public SetLocalVar(
            final String localVarName ,
            final CoroExpression<VARIABLE , COROUTINE_RETURN> varValueExpression )
    {
        this.localVarName =
                Objects.requireNonNull(
                        localVarName );

        this.varValueExpression =
                Objects.requireNonNull(
                        varValueExpression );
    }

    /**
     * Convenience constructor.
     */
    public SetLocalVar(
            final String localVarName ,
            final VARIABLE varValue )
    {
        this.localVarName =
                Objects.requireNonNull(
                        localVarName );

        this.varValueExpression =
                new Value<VARIABLE , COROUTINE_RETURN>(
                        (Class<? extends VARIABLE>) varValue.getClass() ,
                        varValue );
    }

    /**
     * Set variable.
     *
     * @see SimpleStmt#execute
     */
    @Override
    public CoroStmtResult<FUNCTION_RETURN , COROUTINE_RETURN> execute(
            final CoroutineOrFunctioncallOrComplexstmt<FUNCTION_RETURN , COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        final Object varValue = varValueExpression.evaluate( parent );

        parent.localVars().set(
                localVarName ,
                varValue );

        return CoroStmtResult.continueCoroutine();
    }

    @Override
    public VARIABLE evaluate(
            final HasArgumentsAndVariables<?> parent )
    // for using in expressions
    {
        execute(
                (CoroutineOrFunctioncallOrComplexstmt<FUNCTION_RETURN , COROUTINE_RETURN, RESUME_ARGUMENT>) parent );

        return (VARIABLE) parent.localVars().get(
                this ,
                localVarName );
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<? extends VARIABLE>[] type()
    {
        //return new Class[] { type };
        return varValueExpression.type();
    }

    /**
     * @see CoroStmt#getFunctionArgumentGetsNotInFunction()
     */
    @Override
    public List<GetFunctionArgument<? , ?>> getFunctionArgumentGetsNotInFunction()
    {
        return this.varValueExpression.getFunctionArgumentGetsNotInFunction();
    }

    @Override
    public void checkUseArguments(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent )
    {
        this.varValueExpression.checkUseArguments(
                alreadyCheckedFunctionNames ,
                parent );
    }

    @Override
    public void setStmtCoroutineReturnType(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ? , ?> parent ,
            final Class<? extends COROUTINE_RETURN> coroutineReturnType )
    {
        this.varValueExpression.setExprCoroutineReturnType(
                alreadyCheckedFunctionNames ,
                parent ,
                coroutineReturnType );
    }

    @Override
    public void setExprCoroutineReturnType(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent ,
            final Class<?> coroutineReturnType )
    {
        this.varValueExpression.setExprCoroutineReturnType(
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
        if ( ! localVariableTypes.containsKey( this.localVarName ) )
        {
            throw new GetLocalVar.LocalVariableNotDeclaredException( this );
        }

        this.varValueExpression.checkUseVariables(
                alreadyCheckedFunctionNames ,
                parent ,
                globalVariableTypes, localVariableTypes );
    }

    @Override
    public String getVariableName()
    {
        return this.localVarName;
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return
                localVarName +
                " = " +
                varValueExpression +
                ( this.creationStackTraceElement != null
                    ? " " + this.creationStackTraceElement
                    : "" );
    }

}
