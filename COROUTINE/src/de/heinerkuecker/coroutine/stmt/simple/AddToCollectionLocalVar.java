package de.heinerkuecker.coroutine.stmt.simple;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineOrFunctioncallOrComplexstmt;
import de.heinerkuecker.coroutine.HasVariableName;
import de.heinerkuecker.coroutine.exprs.GetFunctionArgument;
import de.heinerkuecker.coroutine.exprs.GetLocalVar;
import de.heinerkuecker.coroutine.exprs.SimpleExpression;
import de.heinerkuecker.coroutine.stmt.CoroStmtResult;
import de.heinerkuecker.coroutine.stmt.simple.exc.StmtVariableIsNullException;
import de.heinerkuecker.coroutine.stmt.simple.exc.WrongStmtVariableClassException;

public final class AddToCollectionLocalVar<FUNCTION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT, ELEMENT_TO_ADD>
extends SimpleStmt<FUNCTION_RETURN , COROUTINE_RETURN, RESUME_ARGUMENT>
implements HasVariableName
{
    /**
     * Name of {@link Collection} variable to add in
     * {@link CoroutineOrFunctioncallOrComplexstmt#localVars()}
     */
    public final String collectionLocalVarName;

    /**
     * This is the expression whose result
     * is to add to the value of the
     * {@link Collection} variable.
     */
    public final SimpleExpression<ELEMENT_TO_ADD , COROUTINE_RETURN> elementToAddExpression;

    /**
     * Constructor.
     */
    public AddToCollectionLocalVar(
            final String localVarName ,
            final SimpleExpression<ELEMENT_TO_ADD , COROUTINE_RETURN> elementToAddExpression )
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
     * @see SimpleStmt#execute
     */
    @Override
    public CoroStmtResult<FUNCTION_RETURN , COROUTINE_RETURN> execute(
            final CoroutineOrFunctioncallOrComplexstmt<FUNCTION_RETURN , COROUTINE_RETURN, RESUME_ARGUMENT> parent )
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
                    //wrongStmt
                    this ,
                    //wrongClass
                    varValue.getClass() ,
                    //expectedClass
                    Collection.class );
        }
        return CoroStmtResult.continueCoroutine();
    }

    @Override
    public void setStmtCoroutineReturnType(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ? , ?> parent ,
            final Class<? extends COROUTINE_RETURN> coroutineReturnType )
    {
        this.elementToAddExpression.setExprCoroutineReturnType(
                alreadyCheckedFunctionNames ,
                parent ,
                coroutineReturnType );
    }

    @Override
    public List<GetFunctionArgument<? , ?>> getFunctionArgumentGetsNotInFunction()
    {
        return this.elementToAddExpression.getFunctionArgumentGetsNotInFunction();
    }

    @Override
    public void checkUseArguments(
            final HashSet<String> alreadyCheckedFunctionNames,
            final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent)
    {
        this.elementToAddExpression.checkUseArguments( alreadyCheckedFunctionNames, parent );
    }

    @Override
    public void checkUseVariables(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent ,
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
                    //wrongStmt
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
