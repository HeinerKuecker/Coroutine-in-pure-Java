package de.heinerkuecker.coroutine_iterator.step.flow;

import de.heinerkuecker.coroutine_iterator.CoroutineIterator;
import de.heinerkuecker.coroutine_iterator.step.CoroIterStep;
import de.heinerkuecker.coroutine_iterator.step.complex.For;
import de.heinerkuecker.coroutine_iterator.step.complex.While;
import de.heinerkuecker.coroutine_iterator.step.result.CoroIterStepResult;

/**
 * Step {@link CoroIterStep}
 * to break current loop like
 * {@link For},
 * {@link While} or
 * {@link DoWhile}.
 *
 * @param <RESULT> result type of method {@link CoroutineIterator#next()}
 * @author Heiner K&uuml;cker
 */
public class Break<RESULT>
//implements SimpleStep<RESULT, CoroutineIterator<RESULT>>
extends BreakOrContinue<RESULT>
{
    /**
     * Label of the loop
     * to break.
     */
    public final String label;

    /**
     * Constructor without label.
     */
    public Break()
    {
        this.label = null;
    }

    /**
     * Constructor.
     */
    public Break(
            final String label )
    {
        this.label = label;
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
        return new CoroIterStepResult.Break<RESULT>( this.label );
    }

    /**
     * @see BreakOrContinue#getLabel()
     */
    @Override
    public String getLabel()
    {
        return this.label;
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
}
