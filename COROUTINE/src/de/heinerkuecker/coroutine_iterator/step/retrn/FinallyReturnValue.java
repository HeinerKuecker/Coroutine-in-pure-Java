package de.heinerkuecker.coroutine_iterator.step.retrn;

import de.heinerkuecker.coroutine_iterator.CoroutineIterator;
import de.heinerkuecker.coroutine_iterator.step.CoroIterStep;
import de.heinerkuecker.coroutine_iterator.step.result.CoroIterStepResult;
import de.heinerkuecker.coroutine_iterator.step.simple.SimpleStep;

/**
 * Step {@link CoroIterStep} to
 * return a specified value
 * and stop stepping.
 *
 * @param <RESULT> result type of method {@link CoroutineIterator#next()}
 * @author Heiner K&uuml;cker
 */
public class FinallyReturnValue<RESULT>
extends SimpleStep<RESULT, CoroutineIterator<RESULT>>
{
    public final RESULT resultValue;

    /**
     * Constructor.
     */
    public FinallyReturnValue(
            final RESULT resultValue )
    {
        super(
                //creationStackOffset
                //2
                );

        this.resultValue = resultValue;
    }

    /**
     * Decrement variable.
     *
     * @see CoroIterStep#execute(Object)
     */
    @Override
    public CoroIterStepResult<RESULT> execute(
            final CoroutineIterator<RESULT> parent )
    {
        return new CoroIterStepResult.FinallyReturnWithResult<RESULT>( resultValue );
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return
                this.getClass().getSimpleName() + " " +
                resultValue +
                ( this.creationStackTraceElement != null
                    ? " " + this.creationStackTraceElement
                    : "" );
    }
}
