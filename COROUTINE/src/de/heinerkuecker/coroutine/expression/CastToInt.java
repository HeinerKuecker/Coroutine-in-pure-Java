package de.heinerkuecker.coroutine.expression;

import java.util.List;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine.step.CoroIterStep;

/**
 * Cast
 * result of the specified
 * expression {@link CoroExpression}.
 *
 * @author Heiner K&uuml;cker
 */
public class CastToInt<T extends Number>
implements CoroExpression<Integer>
{
    /**
     * Number expression to deliver object to cast.
     */
    public final CoroExpression<? extends T> numberExpression;

    /**
     * Constructor.
     */
    public CastToInt(
            final CoroExpression<? extends T> numberExpression )
    {
        this.numberExpression = Objects.requireNonNull( numberExpression );
    }

    /**
     * Add.
     *
     * @see CoroExpression#getValue(CoroIteratorOrProcedure)
     */
    @Override
    public Integer getValue(
            final CoroIteratorOrProcedure<?> parent )
    {
        final Number numberExpressionResult = numberExpression.getValue( parent );

        if ( numberExpressionResult == null )
        {
            throw new NullPointerException( "numberExpression: " + numberExpression );
        }

        return numberExpressionResult.intValue();
    }

    /**
     * @see CoroIterStep#getProcedureArgumentGetsNotInProcedure()
     */
    @Override
    public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    {
        return numberExpression.getProcedureArgumentGetsNotInProcedure();
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return " ( (int) " + numberExpression + " )";
    }

}