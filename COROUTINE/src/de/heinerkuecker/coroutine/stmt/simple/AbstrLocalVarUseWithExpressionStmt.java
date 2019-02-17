package de.heinerkuecker.coroutine.stmt.simple;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineOrFunctioncallOrComplexstmt;
import de.heinerkuecker.coroutine.HasVariableName;
import de.heinerkuecker.coroutine.exprs.GetFunctionArgument;
import de.heinerkuecker.coroutine.exprs.SimpleExpression;
import de.heinerkuecker.coroutine.stmt.CoroStmtResult;

// TODO give expression instead local var name
abstract public class AbstrLocalVarUseWithExpressionStmt<FUNCTION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT , VARIABLE, EXPRESSION>
extends SimpleStmt<FUNCTION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT>
implements HasVariableName
{
    /**
     * Name of variable to use in
     * {@link CoroutineOrFunctioncallOrComplexstmt#localVars()}
     */
    public final String localVarName;

    /**
     * This is the expression whose result
     * is used to execute an operation
     * with the value of the variable.
     */
    public final SimpleExpression<EXPRESSION , COROUTINE_RETURN , RESUME_ARGUMENT> expression;

    /**
     * Method to implement the
     * operation with the variable.
     *
     * @param varvalue
     * @param expressionValue
     */
    abstract protected void execute(
            final CoroutineOrFunctioncallOrComplexstmt<FUNCTION_RETURN , COROUTINE_RETURN, RESUME_ARGUMENT> parent ,
            final VARIABLE varvalue ,
            final EXPRESSION expressionValue );

    /**
     * For using in {@link #toString()}.
     *
     * @return operation or field name
     */
    abstract protected String opString();

    /**
     * Constructor.
     */
    public AbstrLocalVarUseWithExpressionStmt(
            final String localVarName ,
            final SimpleExpression<EXPRESSION , COROUTINE_RETURN , RESUME_ARGUMENT> expression )
    {
        this.localVarName =
                Objects.requireNonNull(
                        localVarName );

        this.expression =
                Objects.requireNonNull(
                        expression );
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
        final VARIABLE varValue =
                (VARIABLE) parent.localVars().get(
                        this ,
                        localVarName );

        final EXPRESSION expressionValue =
                expression.evaluate(
                        parent );

        execute(
                parent ,
                varValue ,
                expressionValue );

        return CoroStmtResult.continueCoroutine();
    }

    @Override
    public void setStmtCoroutineReturnTypeAndResumeArgumentType(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ? , ?> parent ,
            //final Class<? /*extends COROUTINE_RETURN*/> coroutineReturnType ,
            final Class<? extends COROUTINE_RETURN> coroutineReturnType ,
            final Class<? extends RESUME_ARGUMENT> resumeArgumentType )
    {
        // do nothing
    }

    @Override
    final public List<GetFunctionArgument<? , ? , ?>> getFunctionArgumentGetsNotInFunction()
    {
        // nothing to do
        return Collections.emptyList();
    }

    @Override
    final public void checkUseArguments(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent )
    {
        // nothing to do
    }

    @Override
    public void checkUseVariables(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent ,
            final Map<String, Class<?>> globalVariableTypes ,
            final Map<String, Class<?>> localVariableTypes )
    {
        //if ( ! localVariableTypes.containsKey( this.localVarName ) )
        //{
        //    throw new GetLocalVar.LocalVariableNotDeclaredException( this );
        //}
        //
        //if ( ! List.class.equals( localVariableTypes.get( localVarName ) ) )
        //{
        //    throw new WrongStmtVariableClassException(
        //            //wrongStmt
        //            this ,
        //            //wrongClass
        //            localVariableTypes.get( localVarName ) ,
        //            //expectedClass
        //            List.class );
        //}

        // TODO check variable like GetLocalVar

        this.expression.checkUseVariables(
                alreadyCheckedFunctionNames ,
                parent ,
                globalVariableTypes ,
                localVariableTypes );
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
        return localVarName + "." + opString() +
                ( this.creationStackTraceElement != null
                    ? " " + this.creationStackTraceElement
                    : "" );
    }

}
