package de.heinerkuecker.coroutine.step.simple;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine.expression.GetProcedureArgument;
import de.heinerkuecker.coroutine.step.CoroIterStep;
import de.heinerkuecker.coroutine.step.CoroIterStepResult;

/**
 * Step {@link CoroIterStep} to
 * increment an {@link Number}
 * variable in variables
 * {@link CoroIteratorOrProcedure#globalVars()}
 *
 * @param <RESULT>
 * @author Heiner K&uuml;cker
 */
public final class IncrementGlobalVar<RESULT>
extends SimpleStep<RESULT/*, CoroutineIterator<RESULT>*/>
{
    /**
     * Name of variable to increment in
     * {@link CoroIteratorOrProcedure#globalVars()}
     */
    public final String varName;

    /**
     * Constructor.
     *
     * @param variable name
     */
    public IncrementGlobalVar(
            final String varName )
    {
        this.varName =
                Objects.requireNonNull(
                        varName );
    }

    /**
     * Increment variable.
     *
     * @see SimpleStep#execute
     */
    @Override
    public CoroIterStepResult<RESULT> execute(
            final CoroIteratorOrProcedure<RESULT> parent )
    {
        // TODO byte, short, char, long, float, double, BigInteger, BigDecimal
        final int var = (int) parent.globalVars().get( varName );
        parent.globalVars().set(
                varName ,
                var + 1 );
        return CoroIterStepResult.continueCoroutine();
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return varName + "++" +
                ( this.creationStackTraceElement != null
                    ? " " + this.creationStackTraceElement
                    : "" );
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

}
