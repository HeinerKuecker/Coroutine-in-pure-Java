package de.heinerkuecker.coroutine_iterator.step.retrn;

import de.heinerkuecker.coroutine_iterator.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine_iterator.CoroutineIterator;
import de.heinerkuecker.coroutine_iterator.step.CoroIterStep;
import de.heinerkuecker.coroutine_iterator.step.result.CoroIterStepResult;
import de.heinerkuecker.coroutine_iterator.step.simple.SimpleStep;

/**
 * Step {@link CoroIterStep} to
 * return a variable in variables
 * {@link CoroIteratorOrProcedure#localVars()}
 * and stop stepping.
 *
 * @param <RESULT> result type of method {@link CoroutineIterator#next()}
 * @author Heiner K&uuml;cker
 */
public class FinallyReturnLocalVar<RESULT>
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
    public FinallyReturnLocalVar(
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
     * @see CoroIterStep#execute(java.lang.Object)
     */
    @Override
    public CoroIterStepResult<RESULT> execute(
            final CoroIteratorOrProcedure<RESULT> parent )
    {
        @SuppressWarnings("unchecked")
        final RESULT varValue = (RESULT) parent.localVars().get( varName );
        return new CoroIterStepResult.FinallyReturnWithResult<RESULT>( varValue );
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return
                this.getClass().getSimpleName() + " " +
                varName +
                ( this.creationStackTraceElement != null
                    ? String.valueOf( this.creationStackTraceElement )
                    : "" );
    }
}
