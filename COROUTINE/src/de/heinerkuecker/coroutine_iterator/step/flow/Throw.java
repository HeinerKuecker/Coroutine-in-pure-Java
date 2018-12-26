package de.heinerkuecker.coroutine_iterator.step.flow;

import java.util.Objects;

import de.heinerkuecker.coroutine_iterator.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine_iterator.step.result.CoroIterStepResult;
import de.heinerkuecker.coroutine_iterator.step.simple.SimpleStep;
import de.heinerkuecker.util.ExceptionUnchecker;

public class Throw<RESULT>
extends SimpleStep<RESULT/*, CoroutineIterator<RESULT>*/>
{
    private final Exception exception;

    /**
     * @param creationStackOffset
     * @param exception
     */
    public Throw(
            final Exception exception )
    {
        this.exception =
                Objects.requireNonNull(
                        exception );
    }

    /**
     * @see SimpleStep#execute
     */
    @Override
    public CoroIterStepResult<RESULT> execute(
            final CoroIteratorOrProcedure<RESULT> parent )
    {
        return ExceptionUnchecker.returnRethrow( exception );
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
                this.exception +
                ( this.creationStackTraceElement != null
                    ? " " + this.creationStackTraceElement
                    : "" );
    }
}
