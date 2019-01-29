package de.heinerkuecker.coroutine.stmt.simple;

import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep;
import de.heinerkuecker.coroutine.stmt.CoroIterStep;
import de.heinerkuecker.coroutine.stmt.CoroIterStepResult;

/**
 * Interface for one step
 * in {@link CoroutineIterator}.
 *
 * @param <COROUTINE_RETURN> result type of method {@link CoroutineIterator#next()}
 * @param <PARENT> type the {@link CoroutineIterator} instance
 * @author Heiner K&uuml;cker
 */
//public interface SimpleStep<COROUTINE_RETURN, PARENT>
abstract public class SimpleStep<COROUTINE_RETURN/*, PARENT*/, RESUME_ARGUMENT>
extends CoroIterStep<COROUTINE_RETURN /*,PARENT*/>
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
    abstract public CoroIterStepResult<COROUTINE_RETURN> execute(
            //final PARENT parent
            final CoroutineOrProcedureOrComplexstep<COROUTINE_RETURN, RESUME_ARGUMENT> parent );
}
