package de.heinerkuecker.coroutine.stmt.simple;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineOrFunctioncallOrComplexstmt;
import de.heinerkuecker.coroutine.HasArgumentsAndVariables;
import de.heinerkuecker.coroutine.exprs.GetFunctionArgument;
import de.heinerkuecker.coroutine.exprs.NullValue;
import de.heinerkuecker.coroutine.exprs.SimpleExpression;
import de.heinerkuecker.coroutine.exprs.Value;
import de.heinerkuecker.coroutine.stmt.CoroStmt;
import de.heinerkuecker.coroutine.stmt.CoroStmtResult;
import de.heinerkuecker.util.ArrayTypeName;

/**
 * {@link SimpleStmt} to declare
 * variable with specified
 * type (class).
 *
 * The value of the variable can
 * optional initialized with an
 * specified value or result of
 * specified expression.
 *
 * Variables can be global or
 * local in the scoped block or
 * statement.
 *
 * @param <COROUTINE_RETURN> result type of coroutine, here unused
 * @param <T> variable type
 * @author Heiner K&uuml;cker
 */
public final class DeclareVariable<FUNCTION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT, T>
extends SimpleStmt<FUNCTION_RETURN , COROUTINE_RETURN/*, CoroutineIterator<COROUTINE_RETURN>*/, RESUME_ARGUMENT>
implements SimpleExpression<T , COROUTINE_RETURN>
{
    /**
     * Name of variable.
     */
    public final String varName;

    /**
     * Type (class) of the variable.
     */
    public final Class<? extends T> type;

    /**
     * This is the expression whose result
     * should be set as the initial value of
     * the variable.
     */
    public final SimpleExpression<? extends T , COROUTINE_RETURN> initialVarValueExpression;

    /**
     * Constructor.
     */
    public DeclareVariable(
            final String varName ,
            final Class<? extends T> type ,
            final SimpleExpression<? extends T , COROUTINE_RETURN> initialVarValueExpression )
    {
        this.varName =
                Objects.requireNonNull(
                        varName );

        this.type =
                Objects.requireNonNull(
                        type );

        this.initialVarValueExpression =
                Objects.requireNonNull(
                        initialVarValueExpression );
    }

    /**
     * Convenience constructor.
     */
    public DeclareVariable(
            final String varName ,
            final Class<? extends T> type ,
            final T initialVarValue )
    {
        this.varName =
                Objects.requireNonNull(
                        varName );

        this.type =
                Objects.requireNonNull(
                        type );

        this.initialVarValueExpression =
                new Value<>(
                        type ,
                        initialVarValue );
    }

    /**
     * Convenience constructor.
     */
    public DeclareVariable(
            final String varName ,
            // null is forbidden
            final T initialVarValue )
    {
        this.varName =
                Objects.requireNonNull(
                        varName );

        this.type =
                (Class<? extends T>) initialVarValue.getClass();

        this.initialVarValueExpression =
                new Value<>(
                        type ,
                        initialVarValue );
    }

    /**
     * Constructor.
     */
    public DeclareVariable(
            final String varName ,
            final Class<? extends T> type )
    {
        this.varName =
                Objects.requireNonNull(
                        varName );

        this.type =
                Objects.requireNonNull(
                        type );

        this.initialVarValueExpression =
                //null
                NullValue.nullValue();
    }

    /**
     * Set variable.
     */
    @Override
    public CoroStmtResult<FUNCTION_RETURN , COROUTINE_RETURN> execute(
            final CoroutineOrFunctioncallOrComplexstmt<FUNCTION_RETURN , COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        System.out.println( "execute " + this );

        //if ( this.initialVarValueExpression == null )
        //    // TODO no more needed
        //{
        //    parent.localVars().declare(
        //            this ,
        //            varName ,
        //            type );
        //}
        //else
        {
            final T varValue = initialVarValueExpression.evaluate( parent );

            parent.localVars().declare(
                    this ,
                    varName ,
                    type ,
                    varValue );
        }

        return CoroStmtResult.continueCoroutine();
    }

    @Override
    public T evaluate(
            final HasArgumentsAndVariables<?> parent )
    // for using in expressions
    {
        execute(
                (CoroutineOrFunctioncallOrComplexstmt<FUNCTION_RETURN , COROUTINE_RETURN, RESUME_ARGUMENT>) parent );

        return (T) parent.localVars().get(
                this ,
                varName );
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<? extends T>[] type()
    {
        return new Class[] { type };
    }

    /**
     * @see CoroStmt#getFunctionArgumentGetsNotInFunction()
     */
    @Override
    public List<GetFunctionArgument<? , ?>> getFunctionArgumentGetsNotInFunction()
    {
        if ( this.initialVarValueExpression == null )
        {
            return Collections.emptyList();
        }
        return this.initialVarValueExpression.getFunctionArgumentGetsNotInFunction();
    }

    @Override
    public void setStmtCoroutineReturnType(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ? , ?> parent ,
            final Class<? extends COROUTINE_RETURN> coroutineReturnType )
    {
        this.initialVarValueExpression.setExprCoroutineReturnType(
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
        this.initialVarValueExpression.setExprCoroutineReturnType(
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
        if ( localVariableTypes.containsKey( this.varName ) )
        {
            throw new VariableAlreadyDeclaredException( this );
        }

        localVariableTypes.put(
                this.varName ,
                this.type );

        if ( initialVarValueExpression != null )
        {
            this.initialVarValueExpression.checkUseVariables(
                    alreadyCheckedFunctionNames ,
                    parent ,
                    globalVariableTypes, localVariableTypes );
        }
    }

    @Override
    public void checkUseArguments(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent )
    {
        if ( initialVarValueExpression != null )
        {
            this.initialVarValueExpression.checkUseArguments(
                    alreadyCheckedFunctionNames ,
                    parent );
        }
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        final String initializationStr;
        if ( this.initialVarValueExpression == null )
        {
            initializationStr = "";
        }
        else
        {
            initializationStr =
                    " = " + initialVarValueExpression;
        }

        return
                this.getClass().getSimpleName() + " " +
                //type.getName() +
                ArrayTypeName.toStr( this.type ) +
                " " +
                varName +
                initializationStr +
                ( this.creationStackTraceElement != null
                    ? " " + this.creationStackTraceElement
                    : "" );
    }

    /**
     * Exception
     */
    public static class VariableAlreadyDeclaredException
    extends RuntimeException
    {
        /**
         * Generated by Eclipse.
         */
        private static final long serialVersionUID = 1720716354727789269L;

        /**
         * Constructor.
         *
         * @param declareLocalVar
         */
        public VariableAlreadyDeclaredException(
                final DeclareVariable<?, ?, ?, ?> declareLocalVar )
        {
            super(
                    "variable already declared: " +
                    declareLocalVar );
        }

    }

}
