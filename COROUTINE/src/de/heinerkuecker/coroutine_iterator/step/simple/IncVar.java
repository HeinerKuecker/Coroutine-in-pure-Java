package de.heinerkuecker.coroutine_iterator.step.simple;

import java.util.Objects;

import de.heinerkuecker.coroutine_iterator.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine_iterator.CoroutineIterator;
import de.heinerkuecker.coroutine_iterator.step.CoroIterStep;
import de.heinerkuecker.coroutine_iterator.step.result.CoroIterStepResult;

/**
 * Step {@link CoroIterStep} to
 * increment an {@link Number}
 * variable in variables
 * {@link CoroutineIterator#vars}.
 *
 * @param <RESULT>
 * @author Heiner K&uuml;cker
 */
public final class IncVar<RESULT>
extends SimpleStep<RESULT/*, CoroutineIterator<RESULT>*/>
{
    /**
     * Name of variable to increment in
     * {@link CoroutineIterator#vars}.
     */
    public final String varName;

    /**
     * Constructor.
     *
     * @param variable name
     */
    public IncVar(
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
        final int var = (int) parent.vars().get( varName );
        parent.vars().put( varName , var + 1 );
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
}
