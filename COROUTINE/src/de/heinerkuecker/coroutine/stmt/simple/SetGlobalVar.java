package de.heinerkuecker.coroutine.stmt.simple;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineOrFunctioncallOrComplexstmt;
import de.heinerkuecker.coroutine.HasArgumentsAndVariables;
import de.heinerkuecker.coroutine.HasVariableName;
import de.heinerkuecker.coroutine.exprs.GetFunctionArgument;
import de.heinerkuecker.coroutine.exprs.GetGlobalVar;
import de.heinerkuecker.coroutine.exprs.SimpleExpression;
import de.heinerkuecker.coroutine.exprs.Value;
import de.heinerkuecker.coroutine.stmt.CoroStmt;
import de.heinerkuecker.coroutine.stmt.CoroStmtResult;


/**
 * {@link SimpleStmt} to set
 * global variable with specified
 * value or result of specified
 * expression.
 *
 * @author Heiner K&uuml;cker
 * @param <COROUTINE_RETURN> result type of coroutine, here unused
 */
public final class SetGlobalVar<FUNCTION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT , VARIABLE>
extends SimpleStmt<FUNCTION_RETURN , COROUTINE_RETURN/*, CoroutineIterator<COROUTINE_RETURN>*/ , RESUME_ARGUMENT>
implements SimpleExpression<VARIABLE , COROUTINE_RETURN , RESUME_ARGUMENT> , HasVariableName
{
    /**
     * Name of variable to set in
     * {@link CoroutineOrFunctioncallOrComplexstmt#globalVars()}
     */
    public final String globalVarName;

    /**
     * This is the expression whose result
     * should be set as the value of the variable.
     */
    public final SimpleExpression<VARIABLE , COROUTINE_RETURN , RESUME_ARGUMENT> varValueExpression;

    /**
     * Constructor.
     */
    public SetGlobalVar(
            final String globalVarName ,
            final SimpleExpression<VARIABLE , COROUTINE_RETURN , RESUME_ARGUMENT> varValueExpression )
    {
        this.globalVarName =
                Objects.requireNonNull(
                        globalVarName );

        this.varValueExpression =
                Objects.requireNonNull(
                        varValueExpression );
    }

    /**
     * Convenience constructor.
     */
    public SetGlobalVar(
            final String globalVarName ,
            final VARIABLE varValue )
    {
        this.globalVarName =
                Objects.requireNonNull(
                        globalVarName );

        this.varValueExpression =
                new Value<>(
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

        parent.globalVars().set(
                globalVarName ,
                varValue );

        return CoroStmtResult.continueCoroutine();
    }

    @Override
    public VARIABLE evaluate(
            final HasArgumentsAndVariables<? extends RESUME_ARGUMENT> parent )
    // for using in expressions
    {
        execute(
                (CoroutineOrFunctioncallOrComplexstmt<FUNCTION_RETURN , COROUTINE_RETURN, RESUME_ARGUMENT>) parent );

        return (VARIABLE) parent.globalVars().get(
                this ,
                globalVarName );
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
    public List<GetFunctionArgument<? , ? , ?>> getFunctionArgumentGetsNotInFunction()
    {
        return this.varValueExpression.getFunctionArgumentGetsNotInFunction();
    }

    @Override
    public void setStmtCoroutineReturnTypeAndResumeArgumentType(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ? , ?> parent ,
            //final Class<? /*extends COROUTINE_RETURN*/> coroutineReturnType ,
            final Class<? extends COROUTINE_RETURN> coroutineReturnType ,
            final Class<? extends RESUME_ARGUMENT> resumeArgumentType )
    {
        this.varValueExpression.setExprCoroutineReturnTypeAndResumeArgumentType(
                alreadyCheckedFunctionNames ,
                parent ,
                coroutineReturnType ,
                resumeArgumentType );
    }

    @Override
    public void setExprCoroutineReturnTypeAndResumeArgumentType(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ? , ?> parent ,
            //final Class<? /*extends COROUTINE_RETURN*/> coroutineReturnType ,
            final Class<? extends COROUTINE_RETURN> coroutineReturnType ,
            final Class<? extends RESUME_ARGUMENT> resumeArgumentType )
    {
        this.varValueExpression.setExprCoroutineReturnTypeAndResumeArgumentType(
                alreadyCheckedFunctionNames ,
                parent ,
                coroutineReturnType ,
                resumeArgumentType );
    }

    @Override
    public void checkUseVariables(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent ,
            final Map<String, Class<?>> globalVariableTypes ,
            final Map<String, Class<?>> localVariableTypes )
    {
        if ( ! globalVariableTypes.containsKey( this.globalVarName ) )
        {
            throw new GetGlobalVar.GlobalVariableNotDeclaredException( this );
        }

        this.varValueExpression.checkUseVariables(
                alreadyCheckedFunctionNames ,
                parent ,
                globalVariableTypes, localVariableTypes );
    }

    @Override
    public void checkUseArguments(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent )
    {
        this.varValueExpression.checkUseArguments( alreadyCheckedFunctionNames, parent );
    }

    @Override
    public String getVariableName()
    {
        return this.globalVarName;
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return globalVarName + " = " + varValueExpression +
                ( this.creationStackTraceElement != null
                    ? " " + this.creationStackTraceElement
                    : "" );
    }

}
