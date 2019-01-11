package de.heinerkuecker.coroutine.step.simple;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine.expression.CoroExpression;
import de.heinerkuecker.coroutine.expression.GetProcedureArgument;
import de.heinerkuecker.coroutine.step.CoroIterStep;
import de.heinerkuecker.coroutine.step.CoroIterStepResult;
import de.heinerkuecker.util.ArrayTypeName;

public final class DeclareLocalVar<RESULT, T>
extends SimpleStep<RESULT/*, CoroutineIterator<RESULT>*/>
{
    /**
     * Name of variable to set in
     * {@link CoroIteratorOrProcedure#localVars()}
     */
    public final String varName;

    public final Class<T> type;

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

        this.initialVarValueExpression = null;
    }

    /**
     * Set variable.
     *
     * @see SimpleStep#execute
     */
    @Override
    public CoroIterStepResult<RESULT> execute(
            final CoroIteratorOrProcedure<RESULT> parent )
    {
        if ( this.initialVarValueExpression == null )
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

}
