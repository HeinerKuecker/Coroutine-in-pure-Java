package de.heinerkuecker.coroutine.expression;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine.HasArgumentsAndVariables;
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
     * @see CoroExpression#evaluate(CoroIteratorOrProcedure)
     */
    @Override
    public Integer evaluate(
            final HasArgumentsAndVariables/*CoroIteratorOrProcedure<?>*/ parent )
    {
        final Number numberExpressionResult = numberExpression.evaluate( parent );

        if ( numberExpressionResult == null )
        {
            throw new NullPointerException( "result of numberExpression: " + numberExpression );
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

    @Override
    public void checkUseUndeclaredVariables(
            final CoroIteratorOrProcedure<?> parent ,
            final Map<String, Class<?>> globalVariableTypes ,
            final Map<String, Class<?>> localVariableTypes )
    {
        this.numberExpression.checkUseUndeclaredVariables(
                parent ,
                globalVariableTypes ,
                localVariableTypes );
    }

    @Override
    public void checkUseUndeclaredParameters(
            final CoroIteratorOrProcedure<?> parent )
    {
        this.numberExpression.checkUseUndeclaredParameters( parent );
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
