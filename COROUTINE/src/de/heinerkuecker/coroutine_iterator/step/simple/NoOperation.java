package de.heinerkuecker.coroutine_iterator.step.simple;

import de.heinerkuecker.coroutine_iterator.CoroutineIterator;
import de.heinerkuecker.coroutine_iterator.step.CoroIterStep;
import de.heinerkuecker.coroutine_iterator.step.result.CoroIterStepResult;

/**
 * Step {@link CoroIterStep} to
 * do nothing.
 *
 * @param <RESULT> result type of method {@link CoroutineIterator#next()}
 * @author Heiner K&uuml;cker
 */
public final class NoOperation<RESULT>
extends SimpleStep<RESULT, CoroutineIterator<RESULT>>
{

    /**
     * Do nothing.
     *
     * @see CoroIterStep#execute(Object)
     */
    @Override
    public CoroIterStepResult<RESULT> execute(
            final CoroutineIterator<RESULT> parent )
    {
        return CoroIterStepResult.continueCoroutine();
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return this.getClass().getSimpleName() +
                ( this.creationStackTraceElement != null
                    ? " " + this.creationStackTraceElement
                    : "" );
    }
}
