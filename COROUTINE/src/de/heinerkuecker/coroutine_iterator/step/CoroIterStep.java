package de.heinerkuecker.coroutine_iterator.step;

import de.heinerkuecker.coroutine_iterator.CoroutineIterator;

/**
 * Interface for one step
 * in {@link CoroutineIterator}.
 *
 * @param <RESULT> result type of method {@link CoroutineIterator#next()}
 * @param <PARENT> type the {@link CoroutineIterator} instance
 * @author Heiner K&uuml;cker
 */
abstract public class CoroIterStep<RESULT, PARENT>
{
    ///**
    // * Execute one step.
    // *
    // * @param parent
    // * @return object to return a value and to control the flow
    // */
    //CoroIterStepResult<RESULT> execute(
    //        final PARENT parent );

    // TODO rename to creation source position
    public final StackTraceElement creationStackTraceElement;

    /**
     * Constructor with safe creation line number optional.
     */
    protected CoroIterStep(
            final int creationStackOffset )
    {
        if ( CoroutineIterator.safeCreationSourcePosition )
        {
            creationStackTraceElement = new Exception().getStackTrace()[ creationStackOffset ];
        }
        else
        {
            creationStackTraceElement = null;
        }
    }
}
