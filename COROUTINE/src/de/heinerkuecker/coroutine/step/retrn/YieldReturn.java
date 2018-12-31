package de.heinerkuecker.coroutine.step.retrn;

import java.util.List;

import de.heinerkuecker.coroutine.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.expression.CoroExpression;
import de.heinerkuecker.coroutine.expression.GetProcedureArgument;
import de.heinerkuecker.coroutine.step.CoroIterStep;
import de.heinerkuecker.coroutine.step.result.CoroIterStepResult;
import de.heinerkuecker.coroutine.step.simple.SimpleStep;

/**
 * Step {@link CoroIterStep} to
 * return a specified value
 * and suspend stepping.
 *
 * @param <RESULT> result type of method {@link CoroutineIterator#next()}
 * @author Heiner K&uuml;cker
 */
public class YieldReturn<RESULT>
extends SimpleStep<RESULT/*, CoroutineIterator<RESULT>*/>
{
    public final CoroExpression<RESULT> expression;

    /**
     * Constructor.
     */
    public YieldReturn(
            final CoroExpression<RESULT> expression )
    {
        super(
                //creationStackOffset
                //2
                );

        this.expression = expression;
    }

    /**
     * Compute result value and wrap it in yield return.
     *
     * @see CoroIterStep#execute(Object)
     */
    @Override
    public CoroIterStepResult<RESULT> execute(
            final CoroIteratorOrProcedure<RESULT> parent )
    {
        return new CoroIterStepResult.YieldReturnWithResult<RESULT>(
                expression.getValue(
                        parent ) );
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return
                this.getClass().getSimpleName() + " " +
                expression +
                ( this.creationStackTraceElement != null
                    ? " " + this.creationStackTraceElement
                    : "" );
    }

    /**
     * @see CoroIterStep#getProcedureArgumentGetsNotInProcedure()
     */
    @Override
    public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    {
        return expression.getProcedureArgumentGetsNotInProcedure();
    }

}
