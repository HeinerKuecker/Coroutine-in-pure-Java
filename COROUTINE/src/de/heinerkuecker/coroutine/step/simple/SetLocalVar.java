package de.heinerkuecker.coroutine.step.simple;

import java.util.List;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine.expression.CoroExpression;
import de.heinerkuecker.coroutine.expression.GetProcedureArgument;
import de.heinerkuecker.coroutine.step.CoroIterStep;
import de.heinerkuecker.coroutine.step.result.CoroIterStepResult;

public final class SetLocalVar<RESULT>
extends SimpleStep<RESULT/*, CoroutineIterator<RESULT>*/>
{
    /**
     * Name of variable to set in
     * {@link CoroIteratorOrProcedure#localVars()}
     */
    public final String varName;

    public final CoroExpression<?> varValueExpression;

    /**
     * Constructor.
     */
    public SetLocalVar(
            final String varName ,
            final CoroExpression<?> varValueExpression )
    {
        this.varName =
                Objects.requireNonNull(
                        varName );

        this.varValueExpression =
                Objects.requireNonNull(
                        varValueExpression );
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
        final Object varValue = varValueExpression.getValue( parent );

        parent.localVars().put(
                varName ,
                varValue );

        return CoroIterStepResult.continueCoroutine();
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return varName + " = " + varValueExpression +
                ( this.creationStackTraceElement != null
                ? " " + this.creationStackTraceElement
                        : "" );
    }

    /**
     * @see CoroIterStep#getProcedureArgumentGetsNotInProcedure()
     */
    @Override
    public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    {
        return this.varValueExpression.getProcedureArgumentGetsNotInProcedure();
    }

}
