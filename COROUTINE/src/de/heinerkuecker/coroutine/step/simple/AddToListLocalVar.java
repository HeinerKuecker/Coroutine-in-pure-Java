package de.heinerkuecker.coroutine.step.simple;

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
import de.heinerkuecker.coroutine.step.simple.exc.StmtVariableIsNullException;
import de.heinerkuecker.coroutine.step.simple.exc.WrongStmtVariableClassException;

public final class AddToListLocalVar<RESULT, ELEMENT_TO_ADD>
extends SimpleStepWithoutArguments<RESULT>
implements HasVariableName
{
    /**
     * Name of {@link List} variable to add in
     * {@link CoroutineOrProcedureOrComplexstep#localVars()}
     */
    public final String listLocalVarName;

    /**
     * This is the expression whose result
     * is to add to the value of the
     * {@link List} variable.
     */
    public final CoroExpression<ELEMENT_TO_ADD> elementToAddExpression;

    /**
     * Constructor.
     */
    public AddToListLocalVar(
            final String localVarName ,
            final CoroExpression<ELEMENT_TO_ADD> elementToAddExpression )
    {
        this.listLocalVarName =
                Objects.requireNonNull(
                        localVarName );

        this.elementToAddExpression =
                Objects.requireNonNull(
                        elementToAddExpression );
    }

    /**
     * Add element to {@link List} variable.
     *
     * @see SimpleStep#execute
     */
    @Override
    public CoroIterStepResult<RESULT> execute(
            final CoroutineOrProcedureOrComplexstep<RESULT> parent )
    {
        final Object varValue =
                parent.localVars().get(
                        this ,
                        listLocalVarName );

        if ( varValue instanceof List )
        {
            final ELEMENT_TO_ADD elementToAdd =
                    elementToAddExpression.evaluate(
                            parent );

            ((List<ELEMENT_TO_ADD>) varValue).add(
                    elementToAdd );
        }
        else if ( varValue == null )
        {
            throw new StmtVariableIsNullException(
                    // wrongStmt
                    this ,
                    // expectedClass
                    List.class );
        }
        else
        {
            throw new WrongStmtVariableClassException(
                    //wrongStep
                    this ,
                    //wrongClass
                    varValue.getClass() ,
                    //expectedClass
                    List.class );
        }
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
    public void checkUseVariables(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstep<?> parent ,
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
