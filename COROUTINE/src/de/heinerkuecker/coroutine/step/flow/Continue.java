package de.heinerkuecker.coroutine.step.flow;

import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep;
import de.heinerkuecker.coroutine.step.CoroIterStep;
import de.heinerkuecker.coroutine.step.CoroIterStepResult;
import de.heinerkuecker.coroutine.step.complex.DoWhile;
import de.heinerkuecker.coroutine.step.complex.For;
import de.heinerkuecker.coroutine.step.complex.ForEach;
import de.heinerkuecker.coroutine.step.complex.While;
import de.heinerkuecker.coroutine.step.simple.SimpleStep;

/**
 * Step {@link CoroIterStep}
 * to continue current loop like
 * {@link For},
 * {@link ForEach},
 * {@link While} or
 * {@link DoWhile}.
 *
 * @param <RESULT> result type of method {@link CoroutineIterator#next()}
 * @author Heiner K&uuml;cker
 */
public class Continue<RESULT , RESUME_ARGUMENT>
//implements SimpleStep<RESULT, CoroutineIterator<RESULT>>
extends BreakOrContinue<RESULT , RESUME_ARGUMENT>
{
    /**
     * Label of the loop
     * to continue.
     *
     * TODO move member to super class
     */
    public final String label;

    /**
     * Constructor without label.
     */
    public Continue()
    {
        this.label = null;
    }

    /**
     * Constructor.
     *
     * @param label label
     */
    public Continue(
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
            final CoroutineOrProcedureOrComplexstep<RESULT, RESUME_ARGUMENT> parent )
    {
        return new CoroIterStepResult.ContinueLoop<RESULT>( this.label );
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
