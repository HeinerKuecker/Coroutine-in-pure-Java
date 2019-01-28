package de.heinerkuecker.coroutine.step.flow;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep;
import de.heinerkuecker.coroutine.expression.CoroExpression;
import de.heinerkuecker.coroutine.expression.GetProcedureArgument;
import de.heinerkuecker.coroutine.expression.Value;
import de.heinerkuecker.coroutine.step.CoroIterStep;
import de.heinerkuecker.coroutine.step.CoroIterStepResult;
import de.heinerkuecker.coroutine.step.simple.SimpleStep;
import de.heinerkuecker.util.ExceptionUnchecker;

public class Throw<COROUTINE_RETURN , RESUME_ARGUMENT>
extends SimpleStep<COROUTINE_RETURN/*, CoroutineIterator<COROUTINE_RETURN>*/ , RESUME_ARGUMENT>
{
    private final CoroExpression<? extends Throwable> exceptionExpression;

    /**
     * Constructor.
     *
     * @param exceptionExpression
     */
    public Throw(
            //final Exception exception
            final CoroExpression<? extends Throwable> exceptionExpression )
    {
        this.exceptionExpression =
                Objects.requireNonNull(
                        exceptionExpression );
    }

    /**
     * Constructor.
     *
     * @param exception
     */
    public Throw(
            final Throwable exception )
    {
        this.exceptionExpression =
                new Value<Throwable>(
                        Objects.requireNonNull(
                                exception ) );
    }

    /**
     * @see SimpleStep#execute
     */
    @Override
    public CoroIterStepResult<COROUTINE_RETURN> execute(
            final CoroutineOrProcedureOrComplexstep<COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        final Throwable exception = exceptionExpression.evaluate( parent );

        return ExceptionUnchecker.returnRethrow( exception );
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return
                this.getClass().getSimpleName() +
                " " +
                this.exceptionExpression +
                ( this.creationStackTraceElement != null
                    ? " " + this.creationStackTraceElement
                    : "" );
    }

    @Override
    public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    {
        return Collections.emptyList();
    }

    /**
     * @see CoroIterStep#setResultType(Class)
     */
    @Override
    public void setResultType(
            final Class<? extends COROUTINE_RETURN> resultType )
    {
        // do nothing
    }

    @Override
    public void checkUseVariables(
            //final boolean isCoroutineRoot ,
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstep<?, ?> parent ,
            final Map<String, Class<?>> globalVariableTypes ,
            final Map<String, Class<?>> localVariableTypes )
    {
        // nothing to do
        // TODO change it, when expression for exception is used
    }

    @Override
    public void checkUseArguments(
            HashSet<String> alreadyCheckedProcedureNames, final CoroutineOrProcedureOrComplexstep<?, ?> parent )
    {
        // nothing to do
        // TODO change it, when expression for exception is used
        //this.expression.checkUseUndeclaredParameters( parent );
    }
}
