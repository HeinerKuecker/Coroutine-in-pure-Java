package de.heinerkuecker.coroutine.step.simple;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep;
import de.heinerkuecker.coroutine.expression.CoroExpression;
import de.heinerkuecker.coroutine.expression.GetProcedureArgument;
import de.heinerkuecker.coroutine.expression.NullValue;
import de.heinerkuecker.coroutine.expression.Value;
import de.heinerkuecker.coroutine.step.CoroIterStep;
import de.heinerkuecker.coroutine.step.CoroIterStepResult;
import de.heinerkuecker.util.ArrayTypeName;

/**
 * {@link SimpleStep} to declare
 * local variable with specified
 * type (class).
 *
 * The value of the variable can
 * optional initialized with
 * specified value or result of
 * specified expression.
 *
 * There is no DeclareGlobalVar step,
 * because global variables are
 * local for the root of coroutine.
 * Declare global variables as local
 * variables in the root of the
 * coroutine.
 *
 * @param <RESULT> result type of coroutine, here unused
 * @param <T> variable type
 * @author Heiner K&uuml;cker
 */
public final class DeclareLocalVar<RESULT, T>
extends SimpleStep<RESULT/*, CoroutineIterator<RESULT>*/>
{
    /**
     * Name of variable to set in
     * {@link CoroutineOrProcedureOrComplexstep#localVars()}
     */
    public final String varName;

    /**
     * Type (class) of the variable.
     */
    public final Class<T> type;

    /**
     * This is the expression whose result
     * should be set as the initial value of
     * the variable.
     */
    public final CoroExpression<? extends T> initialVarValueExpression;

    /**
     * Constructor.
     */
    public DeclareLocalVar(
            final String varName ,
            final Class<T> type ,
            final CoroExpression<? extends T> initialVarValueExpression )
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
    public DeclareLocalVar(
            final String varName ,
            final Class<T> type ,
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
    public DeclareLocalVar(
            final String varName ,
            // null is forbidden
            final T initialVarValue )
    {
        this.varName =
                Objects.requireNonNull(
                        varName );

        this.type =
                (Class<T>) initialVarValue.getClass();

        this.initialVarValueExpression =
                new Value<>(
                        type ,
                        initialVarValue );
    }

    /**
     * Constructor.
     */
    public DeclareLocalVar(
            final String varName ,
            final Class<T> type )
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
     *
     * @see SimpleStep#execute
     */
    @Override
    public CoroIterStepResult<RESULT> execute(
            final CoroutineOrProcedureOrComplexstep<RESULT> parent )
    {
        if ( this.initialVarValueExpression == null )
            // TODO no more needed
        {
            parent.localVars().declare(
                    varName ,
                    type );
        }
        else
        {
            final T varValue = initialVarValueExpression.evaluate( parent );

            parent.localVars().declare(
                    varName ,
                    type ,
                    varValue );
        }

        return CoroIterStepResult.continueCoroutine();
    }

    /**
     * @see CoroIterStep#getProcedureArgumentGetsNotInProcedure()
     */
    @Override
    public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    {
        if ( this.initialVarValueExpression == null )
        {
            return Collections.emptyList();
        }
        return this.initialVarValueExpression.getProcedureArgumentGetsNotInProcedure();
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
            final boolean isCoroutineRoot ,
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstep<?> parent ,
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

        if ( parent.isCoroutineRoot() )
        {
            globalVariableTypes.put(
                    this.varName ,
                    this.type );
        }

        if ( initialVarValueExpression != null )
        {
            this.initialVarValueExpression.checkUseVariables(
                    isCoroutineRoot ,
                    alreadyCheckedProcedureNames ,
                    parent ,
                    globalVariableTypes, localVariableTypes );
        }
    }

    @Override
    public void checkUseArguments(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstep<?> parent )
    {
        if ( initialVarValueExpression != null )
        {
            this.initialVarValueExpression.checkUseArguments(
                    alreadyCheckedProcedureNames ,
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
                final DeclareLocalVar<?, ?> declareLocalVar )
        {
            super(
                    "variable already declared: " +
                    declareLocalVar );
        }

    }

}
