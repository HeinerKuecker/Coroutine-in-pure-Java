package de.heinerkuecker.coroutine.arg;

import java.util.Objects;

import de.heinerkuecker.coroutine.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine.expression.CoroExpression;
import de.heinerkuecker.coroutine.expression.Value;

public class Argument<T>
{
    public final String name;

    public final CoroExpression<T> expression;

    /**
     * Constructor.
     *
     * @param name
     * @param expression
     */
    public Argument(
            final String name ,
            final CoroExpression<T> expression )
    {
        this.name = Objects.requireNonNull( name );
        this.expression = Objects.requireNonNull( expression );
    }

    /**
     * Convenience Constructor.
     *
     * @param name
     * @param expression
     */
    public Argument(
            final String name ,
            final T value )
    {
        this.name = Objects.requireNonNull( name );
        this.expression = new Value<T>( value );
    }

    public T getValue(
            final CoroIteratorOrProcedure<?> parent )
    {
        return this.expression.evaluate( parent );
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return this.getClass().getSimpleName() + "[name=" + this.name + ", expression=" + this.expression + "]";
    }

}
