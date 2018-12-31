package de.heinerkuecker.coroutine.step.flow;

import java.util.Collections;
import java.util.List;

import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.expression.GetProcedureArgument;
import de.heinerkuecker.coroutine.step.CoroIterStep;
import de.heinerkuecker.coroutine.step.simple.SimpleStep;

/**
 * Common interface of
 * {@link Break} and
 * {@link Continue}.
 *
 * @param <RESULT> result type of method {@link CoroutineIterator#next()}
 * @author Heiner K&uuml;cker
 */
//public interface BreakOrContinue<RESULT>
abstract public class BreakOrContinue<RESULT>
extends SimpleStep<RESULT /*, CoroutineIterator<RESULT>*/>
{
    /**
     * Constructor.
     *
     * @param creationStackOffset
     */
    protected BreakOrContinue(
            //final int creationStackOffset
            )
    {
        super(
                //creationStackOffset
                4 );
    }

    abstract public String getLabel();

    /**
     * @see CoroIterStep#getProcedureArgumentsNotInProcedure()
     */
    @Override
    public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    {
        return Collections.emptyList();
    }

}
