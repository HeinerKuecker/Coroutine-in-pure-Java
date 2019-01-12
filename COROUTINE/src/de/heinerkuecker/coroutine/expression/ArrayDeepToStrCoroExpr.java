package de.heinerkuecker.coroutine.expression;

import java.util.List;

import de.heinerkuecker.coroutine.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine.HasArgumentsAndVariables;
import de.heinerkuecker.util.ArrayDeepToString;

public class ArrayDeepToStrCoroExpr
implements CoroExpression<String>
{
    public final CoroExpression<? extends Object[]> arrayExpression;

    /**
     * Constructor.
     */
    public ArrayDeepToStrCoroExpr(
            final CoroExpression<? extends Object[]> arrayExpression )
    {
        this.arrayExpression = arrayExpression;
    }

    /**
     * @see CoroExpression#evaluate(CoroIteratorOrProcedure)
     */
    @Override
    public String evaluate(
            final HasArgumentsAndVariables/*CoroIteratorOrProcedure<?>*/ parent )
    {
        final Object[] array = arrayExpression.evaluate( parent );

        return
                ArrayDeepToString.deepToString(
                        array );
    }

    /**
     * @see CoroExpression#getProcedureArgumentGetsNotInProcedure()
     */
    @Override
    public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    {
        return this.arrayExpression.getProcedureArgumentGetsNotInProcedure();
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return
                this.getClass().getSimpleName() +
                "[" +
                //"value=" +
                String.valueOf( arrayExpression ) +
                "]";
    }

}
