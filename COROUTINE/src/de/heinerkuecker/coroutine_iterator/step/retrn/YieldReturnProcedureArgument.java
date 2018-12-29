package de.heinerkuecker.coroutine_iterator.step.retrn;

import de.heinerkuecker.coroutine_iterator.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine_iterator.CoroutineIterator;
import de.heinerkuecker.coroutine_iterator.step.CoroIterStep;
import de.heinerkuecker.coroutine_iterator.step.result.CoroIterStepResult;
import de.heinerkuecker.coroutine_iterator.step.simple.SimpleStep;

/**
 * Step {@link CoroIterStep} to
 * return a procedure argument
 * {@link CoroIteratorOrProcedure#procedureArguments()}
 * and suspend stepping.
 *
 * @param <RESULT> result type of method {@link CoroutineIterator#next()}
 * @author Heiner K&uuml;cker
 */
public class YieldReturnProcedureArgument<RESULT>
extends SimpleStep<RESULT/*, CoroutineIterator<RESULT>*/>
{
    /**
     * Name of procedure argument in
     * {@link CoroIteratorOrProcedure#procedureArguments()}
     * to return.
     */
    public final String procedureArgumentName;

    /**
     * Constructor.
     *
     * @param procedureArgumentName
     */
    public YieldReturnProcedureArgument(
            final String procedureArgumentName )
    {
        super(
                //creationStackOffset
                //2
                );

        this.procedureArgumentName = procedureArgumentName;
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
        final RESULT varValue = (RESULT) parent.procedureArguments().get( procedureArgumentName );
        return new CoroIterStepResult.YieldReturnWithResult<RESULT>( varValue );
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return
                this.getClass().getSimpleName() + " " +
                procedureArgumentName +
                ( this.creationStackTraceElement != null
                    ? " " + this.creationStackTraceElement
                    : "" );
    }
}
