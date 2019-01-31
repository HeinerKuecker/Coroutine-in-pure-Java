package de.heinerkuecker.coroutine.stmt.simple;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstmt;
import de.heinerkuecker.coroutine.HasVariableName;
import de.heinerkuecker.coroutine.exprs.CoroExpression;
import de.heinerkuecker.coroutine.exprs.GetProcedureArgument;
import de.heinerkuecker.coroutine.stmt.CoroIterStmt;
import de.heinerkuecker.coroutine.stmt.CoroIterStmtResult;

// TODO give expression instead local var name
abstract public class AbstrLocalVarUseWithExpressionStmt<COROUTINE_RETURN , RESUME_ARGUMENT , VARIABLE, EXPRESSION>
extends SimpleStmt<COROUTINE_RETURN , RESUME_ARGUMENT>
implements HasVariableName
{
    /**
     * Name of variable to use in
     * {@link CoroutineOrProcedureOrComplexstmt#localVars()}
     */
    public final String localVarName;

    /**
     * This is the expression whose result
     * is used to execute an operation
     * with the value of the variable.
     */
    public final CoroExpression<EXPRESSION> expression;

    /**
     * Method to implement the
     * operation with the variable.
     *
     * @param varvalue
     * @param expressionValue
     */
    abstract protected void execute(
            final CoroutineOrProcedureOrComplexstmt<COROUTINE_RETURN, RESUME_ARGUMENT> parent ,
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
            final CoroExpression<EXPRESSION> expression )
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
    public CoroIterStmtResult<COROUTINE_RETURN> execute(
            final CoroutineOrProcedureOrComplexstmt<COROUTINE_RETURN, RESUME_ARGUMENT> parent )
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

        return CoroIterStmtResult.continueCoroutine();
    }

    /**
     * @see CoroIterStmt#setResultType(Class)
     */
    @Override
    public void setResultType(
            final Class<? extends COROUTINE_RETURN> resultType )
    {
        // do nothing
    }

    @Override
    final public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    {
        // nothing to do
        return Collections.emptyList();
    }

    @Override
    final public void checkUseArguments(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstmt<?, ?> parent )
    {
        // nothing to do
    }

    @Override
    public void checkUseVariables(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstmt<?, ?> parent ,
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
                alreadyCheckedProcedureNames ,
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
