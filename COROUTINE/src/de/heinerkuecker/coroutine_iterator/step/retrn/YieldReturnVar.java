package de.heinerkuecker.coroutine_iterator.step.retrn;

import de.heinerkuecker.coroutine_iterator.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine_iterator.CoroutineIterator;
import de.heinerkuecker.coroutine_iterator.step.CoroIterStep;
import de.heinerkuecker.coroutine_iterator.step.result.CoroIterStepResult;
import de.heinerkuecker.coroutine_iterator.step.simple.SimpleStep;

/**
 * Step {@link CoroIterStep} to
 * return a variable in variables
 * {@link CoroutineIterator#vars}
 * and suspend stepping.
 *
 * @param <RESULT> result type of method {@link CoroutineIterator#next()}
 * @author Heiner K&uuml;cker
 */
public class YieldReturnVar<RESULT>
extends SimpleStep<RESULT/*, CoroutineIterator<RESULT>*/>
{
    /**
     * Name of variable to return in
     * {@link CoroutineIterator#vars}.
     */
    public final String varName;

    /**
     * Constructor.
     *
     * @param variable name
     */
    public YieldReturnVar(
            final String varName )
    {
        super(
                //creationStackOffset
                //2
                );

        this.varName = varName;
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
        @SuppressWarnings("unchecked")
        final RESULT varValue = (RESULT) parent.vars().get( varName );
        return new CoroIterStepResult.YieldReturnWithResult<RESULT>( varValue );
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return
                this.getClass().getSimpleName() + " " +
                varName +
                ( this.creationStackTraceElement != null
                    ? " " + this.creationStackTraceElement
                    : "" );
    }
}
