package de.heinerkuecker.coroutine.step.simple;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep;
import de.heinerkuecker.coroutine.HasVariableName;
import de.heinerkuecker.coroutine.expression.CoroExpression;
import de.heinerkuecker.coroutine.expression.GetLocalVar;
import de.heinerkuecker.coroutine.expression.GetProcedureArgument;
import de.heinerkuecker.coroutine.step.CoroIterStep;
import de.heinerkuecker.coroutine.step.CoroIterStepResult;
import de.heinerkuecker.coroutine.step.simple.exc.WrongStmtVariableClassException;

abstract public class AbstrLocalVarUseWithExpressionStmt<RESULT , RESUME_ARGUMENT , VARIABLE, EXPRESSION>
extends SimpleStep<RESULT , RESUME_ARGUMENT>
implements HasVariableName
{
    /**
     * Name of {@link List} variable to use in
     * {@link CoroutineOrProcedureOrComplexstep#localVars()}
     */
    public final String listLocalVarName;

    /**
     * This is the expression whose result
     * is used to execute an operation
     * with the value of the variable.
     */
    public final CoroExpression<EXPRESSION> expression;

    /**
     * Method to implement the
     * peration with the variable.
     *
     * @param varvalue
     * @param expressionValue
     */
    abstract protected void execute(
            final VARIABLE varvalue ,
            final EXPRESSION expressionValue );

    /**
     * Constructor.
     */
    public AbstrLocalVarUseWithExpressionStmt(
            final String localVarName ,
            final CoroExpression<EXPRESSION> expression )
    {
        this.listLocalVarName =
                Objects.requireNonNull(
                        localVarName );

        this.expression =
                Objects.requireNonNull(
                        expression );
    }

    /**
     * Set variable.
     *
     * @see SimpleStep#execute
     */
    @Override
    public CoroIterStepResult<RESULT> execute(
            final CoroutineOrProcedureOrComplexstep<RESULT, RESUME_ARGUMENT> parent )
    {
        final VARIABLE varValue =
                (VARIABLE) parent.localVars().get(
                        this ,
                        listLocalVarName );

        final EXPRESSION expressionValue =
                expression.evaluate(
                        parent );

        execute(
                varValue ,
                expressionValue );

        return CoroIterStepResult.continueCoroutine();
    }

    /**
     * @see CoroIterStep#setResultType(Class)
     */
    @Override
    public void setResultType(
            final Class<? extends RESULT> resultType )
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
            final CoroutineOrProcedureOrComplexstep<?, ?> parent )
    {
        // nothing to do
    }

    @Override
    public void checkUseVariables(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstep<?, ?> parent ,
            final Map<String, Class<?>> globalVariableTypes ,
            final Map<String, Class<?>> localVariableTypes )
    {
        if ( ! localVariableTypes.containsKey( this.listLocalVarName ) )
        {
            throw new GetLocalVar.LocalVariableNotDeclaredException( this );
        }

        if ( ! List.class.equals( localVariableTypes.get( listLocalVarName ) ) )
        {
            throw new WrongStmtVariableClassException(
                    //wrongStep
                    this ,
                    //wrongClass
                    localVariableTypes.get( listLocalVarName ) ,
                    //expectedClass
                    List.class );
        }
    }

    @Override
    public String getVariableName()
    {
        return this.listLocalVarName;
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return listLocalVarName + " = ! " + listLocalVarName +
                ( this.creationStackTraceElement != null
                    ? " " + this.creationStackTraceElement
                    : "" );
    }

}
