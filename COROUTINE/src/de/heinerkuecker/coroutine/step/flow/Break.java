package de.heinerkuecker.coroutine.step.flow;

import de.heinerkuecker.coroutine.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.step.CoroIterStep;
import de.heinerkuecker.coroutine.step.CoroIterStepResult;
import de.heinerkuecker.coroutine.step.complex.DoWhile;
import de.heinerkuecker.coroutine.step.complex.For;
import de.heinerkuecker.coroutine.step.complex.While;
import de.heinerkuecker.coroutine.step.simple.SimpleStep;

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
     * @see SimpleStep#execute
     */
    @Override
    public CoroIterStepResult<RESULT> execute(
            final CoroIteratorOrProcedure<RESULT> parent )
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
                ( this.label != null ? " " + this.label  : "" ) +
                ( this.creationStackTraceElement != null
                    ? " " + this.creationStackTraceElement
                    : "" );
    }

}
