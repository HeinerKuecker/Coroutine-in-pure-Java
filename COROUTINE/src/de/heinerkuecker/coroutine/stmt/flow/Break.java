package de.heinerkuecker.coroutine.stmt.flow;

import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep;
import de.heinerkuecker.coroutine.stmt.CoroIterStep;
import de.heinerkuecker.coroutine.stmt.CoroIterStepResult;
import de.heinerkuecker.coroutine.stmt.complex.DoWhile;
import de.heinerkuecker.coroutine.stmt.complex.For;
import de.heinerkuecker.coroutine.stmt.complex.ForEach;
import de.heinerkuecker.coroutine.stmt.complex.While;
import de.heinerkuecker.coroutine.stmt.simple.SimpleStep;

/**
 * Step {@link CoroIterStep}
 * to break current loop like
 * {@link For},
 * {@link ForEach},
 * {@link While} or
 * {@link DoWhile}.
 *
 * @param <COROUTINE_RETURN> result type of method {@link CoroutineIterator#next()}
 * @author Heiner K&uuml;cker
 */
public class Break<COROUTINE_RETURN , RESUME_ARGUMENT>
//implements SimpleStep<COROUTINE_RETURN, CoroutineIterator<COROUTINE_RETURN>>
extends BreakOrContinue<COROUTINE_RETURN , RESUME_ARGUMENT>
{
    /**
     * Label of the loop
     * to break.
     *
     * TODO move member to super class
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
    public CoroIterStepResult<COROUTINE_RETURN> execute(
            final CoroutineOrProcedureOrComplexstep<COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        return new CoroIterStepResult.Break<COROUTINE_RETURN>( this.label );
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
