package de.heinerkuecker.coroutine_iterator.step.simple;

import de.heinerkuecker.coroutine_iterator.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine_iterator.CoroutineIterator;
import de.heinerkuecker.coroutine_iterator.step.CoroIterStep;
import de.heinerkuecker.coroutine_iterator.step.result.CoroIterStepResult;

/**
 * Interface for one step
 * in {@link CoroutineIterator}.
 *
 * @param <RESULT> result type of method {@link CoroutineIterator#next()}
 * @param <PARENT> type the {@link CoroutineIterator} instance
 * @author Heiner K&uuml;cker
 */
//public interface SimpleStep<RESULT, PARENT>
abstract public class SimpleStep<RESULT/*, PARENT*/>
extends CoroIterStep<RESULT /*,PARENT*/>
{
    /**
     * Constructor.
     *
     * @param creationStackOffset
     */
    protected SimpleStep(
            final int creationStackOffset )
    {
        super( creationStackOffset );
    }

    /**
     * Constructor.
     */
    protected SimpleStep()
    {
        super(
                //creationStackOffset
                3 );
    }

    /**
     * Execute one step.
     *
     * @param parent
     * @return object to return a value and to control the flow
     */
    abstract public CoroIterStepResult<RESULT> execute(
            //final PARENT parent
            final CoroIteratorOrProcedure<RESULT> parent );
}
