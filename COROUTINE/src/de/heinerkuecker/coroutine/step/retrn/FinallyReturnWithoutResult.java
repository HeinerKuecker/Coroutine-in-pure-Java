package de.heinerkuecker.coroutine.step.retrn;

import java.util.Collections;
import java.util.List;

import de.heinerkuecker.coroutine.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.expression.GetProcedureArgument;
import de.heinerkuecker.coroutine.step.CoroIterStep;
import de.heinerkuecker.coroutine.step.result.CoroIterStepResult;
import de.heinerkuecker.coroutine.step.simple.SimpleStep;

/**
 * Step {@link CoroIterStep}
 * to stop stepping without
 * return a value.
 *
 * @param <RESULT> result type of method {@link CoroutineIterator#next()}
 * @author Heiner K&uuml;cker
 */
public class FinallyReturnWithoutResult<RESULT>
extends SimpleStep<RESULT/*, CoroutineIterator<RESULT>*/>
{
    /**
     * Constructor.
     */
    public FinallyReturnWithoutResult()
    {
        super(
                //creationStackOffset
                //2
                );
    }

    /**
     * Decrement variable.
     *
     * @see CoroIterStep#execute(Object)
     */
    @Override
    public CoroIterStepResult<RESULT> execute(
            final CoroIteratorOrProcedure<RESULT> parent )
    {
        return new CoroIterStepResult.FinallyReturnWithoutResult<>();
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return
                this.getClass().getSimpleName() +
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

}
