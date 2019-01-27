package de.heinerkuecker.coroutine.step.simple;

import java.util.HashSet;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep;
import de.heinerkuecker.coroutine.HasVariableName;
import de.heinerkuecker.coroutine.expression.CoroExpression;
import de.heinerkuecker.coroutine.expression.GetLocalVar;
import de.heinerkuecker.coroutine.step.CoroIterStep;
import de.heinerkuecker.coroutine.step.CoroIterStepResult;
import de.heinerkuecker.coroutine.step.simple.exc.StmtVariableIsNullException;
import de.heinerkuecker.coroutine.step.simple.exc.WrongStmtVariableClassException;

public final class AddToCollectionLocalVar<RESULT , RESUME_ARGUMENT, ELEMENT_TO_ADD>
extends SimpleStepWithoutArguments<RESULT, RESUME_ARGUMENT>
implements HasVariableName
{
    /**
     * Name of {@link Collection} variable to add in
     * {@link CoroutineOrProcedureOrComplexstep#localVars()}
     */
    public final String collectionLocalVarName;

    /**
     * This is the expression whose result
     * is to add to the value of the
     * {@link Collection} variable.
     */
    public final CoroExpression<ELEMENT_TO_ADD> elementToAddExpression;

    /**
     * Constructor.
     */
    public AddToCollectionLocalVar(
            final String localVarName ,
            final CoroExpression<ELEMENT_TO_ADD> elementToAddExpression )
    {
        this.collectionLocalVarName =
                Objects.requireNonNull(
                        localVarName );

        this.elementToAddExpression =
                Objects.requireNonNull(
                        elementToAddExpression );
    }

    /**
     * Add element to {@link Collection} variable.
     *
     * @see SimpleStep#execute
     */
    @Override
    public CoroIterStepResult<RESULT> execute(
            final CoroutineOrProcedureOrComplexstep<RESULT, RESUME_ARGUMENT> parent )
    {
        final Object varValue =
                parent.localVars().get(
                        this ,
                        collectionLocalVarName );

        if ( varValue instanceof Collection )
        {
            final ELEMENT_TO_ADD elementToAdd =
                    elementToAddExpression.evaluate(
                            parent );

            ((Collection<ELEMENT_TO_ADD>) varValue).add(
                    elementToAdd );
        }
        else if ( varValue == null )
        {
            throw new StmtVariableIsNullException(
                    // wrongStmt
                    this ,
                    // expectedClass
                    Collection.class );
        }
        else
        {
            throw new WrongStmtVariableClassException(
                    //wrongStep
                    this ,
                    //wrongClass
                    varValue.getClass() ,
                    //expectedClass
                    Collection.class );
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
            final CoroutineOrProcedureOrComplexstep<?, ?> parent ,
            final Map<String, Class<?>> globalVariableTypes ,
            final Map<String, Class<?>> localVariableTypes )
    {
        if ( ! localVariableTypes.containsKey( this.collectionLocalVarName ) )
        {
            throw new GetLocalVar.LocalVariableNotDeclaredException( this );
        }

        if ( ! Collection.class.isAssignableFrom( localVariableTypes.get( collectionLocalVarName ) ) )
        {
            throw new WrongStmtVariableClassException(
                    //wrongStep
                    this ,
                    //wrongClass
                    localVariableTypes.get( collectionLocalVarName ) ,
                    //expectedClass
                    Collection.class );
        }
    }

    @Override
    public String getVariableName()
    {
        return this.collectionLocalVarName;
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return collectionLocalVarName + ".add( " + elementToAddExpression + " )" +
                ( this.creationStackTraceElement != null
                    ? " " + this.creationStackTraceElement
                    : "" );
    }

}