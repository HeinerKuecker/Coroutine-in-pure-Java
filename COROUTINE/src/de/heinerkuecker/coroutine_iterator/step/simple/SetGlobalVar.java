package de.heinerkuecker.coroutine_iterator.step.simple;

import java.util.List;
import java.util.Objects;

import de.heinerkuecker.coroutine_iterator.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine_iterator.expression.CoroExpression;
import de.heinerkuecker.coroutine_iterator.expression.GetProcedureArgument;
import de.heinerkuecker.coroutine_iterator.step.CoroIterStep;
import de.heinerkuecker.coroutine_iterator.step.result.CoroIterStepResult;

public final class SetGlobalVar<RESULT>
extends SimpleStep<RESULT/*, CoroutineIterator<RESULT>*/>
{
    /**
     * Name of variable to set in
     * {@link CoroIteratorOrProcedure#globalVars()}
     */
    public final String varName;

    public final CoroExpression<?> varValueExpression;

    /**
     * Constructor.
     */
    public SetGlobalVar(
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

        parent.globalVars().put(
                varName ,
                varValue );

        return CoroIterStepResult.continueCoroutine();
    }

    /**
     * @see CoroIterStep#getProcedureArgumentGetsNotInProcedure()
     */
    @Override
    public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    {
        return this.varValueExpression.getProcedureArgumentGetsNotInProcedure();
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return varName + " = " + varValueExpression +
                ( this.creationStackTraceElement != null
                ? " " + this.creationStackTraceElement
                        : "" );
    }

}
