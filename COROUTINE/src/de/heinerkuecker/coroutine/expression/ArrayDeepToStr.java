package de.heinerkuecker.coroutine.expression;

import java.util.Collections;
import java.util.List;

import de.heinerkuecker.coroutine.CoroIteratorOrProcedure;
import de.heinerkuecker.util.ArrayDeepToString;

public class ArrayDeepToStr
implements CoroExpression<String>
{
    public final CoroExpression<? extends Object[]> arrayExpression;

    /**
     * Constructor.
     */
    public ArrayDeepToStr(
            final CoroExpression<? extends Object[]> arrayExpression )
    {
        this.arrayExpression = arrayExpression;
    }

    /**
     * @see CoroExpression#getValue(CoroIteratorOrProcedure)
     */
    @Override
    public String getValue(
            final CoroIteratorOrProcedure<?> parent )
    {
        final Object[] array = arrayExpression.getValue( parent );

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
