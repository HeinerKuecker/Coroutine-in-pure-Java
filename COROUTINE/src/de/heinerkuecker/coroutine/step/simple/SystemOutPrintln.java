package de.heinerkuecker.coroutine.step.simple;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.expression.CoroExpression;
import de.heinerkuecker.coroutine.expression.GetProcedureArgument;
import de.heinerkuecker.coroutine.step.CoroIterStep;
import de.heinerkuecker.coroutine.step.CoroIterStepResult;

/**
 * Step {@link CoroIterStep} to
 * print with newline to
 * {@link System#out}.
 *
 * Use this class as template
 * for your own log step.
 *
 * @param <RESULT> result type of method {@link CoroutineIterator#next()}
 * @author Heiner K&uuml;cker
 */
public final class SystemOutPrintln<RESULT>
extends SimpleStep<RESULT/*, CoroutineIterator<RESULT>*/>
{
    /**
     * Expression to deliver value to print.
     */
    public final CoroExpression<?> outputExpression;

    /**
     * Constructor.
     *
     * @param creationStackOffset
     * @param outputExpression
     */
    public SystemOutPrintln(
            final CoroExpression<?> outputExpression)
    {
        this.outputExpression =
                Objects.requireNonNull(
                        outputExpression );
    }

    /**
     * print with newline to
     * {@link System#out}.
     *
     * @see SimpleStep#execute(Object)
     */
    @Override
    public CoroIterStepResult<RESULT> execute(
            final CoroIteratorOrProcedure<RESULT> parent )
    {
        System.out.println( outputExpression.evaluate( parent ) );
        return CoroIterStepResult.continueCoroutine();
    }

    /**
     * @see CoroIterStep#getProcedureArgumentsNotInProcedure()
     */
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
            final Class<? extends RESULT> resultType )
    {
        // do nothing
    }

    @Override
    public void checkUseUndeclaredVariables(
            final CoroIteratorOrProcedure<?> parent ,
            final Map<String, Class<?>> globalVariableTypes ,
            final Map<String, Class<?>> localVariableTypes )
    {
        this.outputExpression.checkUseUndeclaredVariables(
                parent ,
                globalVariableTypes ,
                localVariableTypes );
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
                this.outputExpression +
                " " +
                ( this.creationStackTraceElement != null
                    ? " " + this.creationStackTraceElement
                    : "" );
    }

}
