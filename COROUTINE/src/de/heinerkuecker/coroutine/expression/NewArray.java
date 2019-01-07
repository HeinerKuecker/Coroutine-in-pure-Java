package de.heinerkuecker.coroutine.expression;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine.step.CoroIterStep;

public class NewArray<T>
implements CoroExpression<T[]>
{
    private final Class<? extends T> componentClass;

    private final CoroExpression<T>[] arrayElementExpressions;

    /**
     * Constructor.
     *
     * @param componentClass
     * @param arrayElementExpressions
     */
    @SafeVarargs
    public NewArray(
            final Class<? extends T> componentClass ,
            CoroExpression<T>... arrayElementExpressions )
    {
        this.componentClass =
                Objects.requireNonNull(
                        componentClass );

        this.arrayElementExpressions =
                Objects.requireNonNull(
                        arrayElementExpressions );
    }

    /**
     * @see CoroExpression#getValue(CoroIteratorOrProcedure)
     */
    @Override
    public T[] getValue(
            final CoroIteratorOrProcedure<?> parent )
    {
        @SuppressWarnings("unchecked")
        final T[] result = (T[]) Array.newInstance( componentClass ,  this.arrayElementExpressions.length );

        for ( int i = 0 ; i < this.arrayElementExpressions.length ; i++ )
        {
            result[ i ] = this.arrayElementExpressions[ i ].getValue( parent );
        }

        return result;
    }

    /**
     * @see CoroIterStep#getProcedureArgumentGetsNotInProcedure()
     */
    @Override
    public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    {
        final List<GetProcedureArgument<?>> result = new ArrayList<>();

        for ( final CoroExpression<T> arrayElementExpression : arrayElementExpressions )
        {
            result.addAll(
                    arrayElementExpression.getProcedureArgumentGetsNotInProcedure() );
        }

        return result;
    }

}
