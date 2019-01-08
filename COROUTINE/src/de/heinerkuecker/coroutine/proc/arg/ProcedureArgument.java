package de.heinerkuecker.coroutine.proc.arg;

import java.util.Objects;

import de.heinerkuecker.coroutine.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine.expression.CoroExpression;

public /*interface*/class ProcedureArgument<T>
{
    //String getName();
    //Object getValue( final CoroIteratorOrProcedure<?> parent );

    public final String name;

    public final CoroExpression<T> expression;

    /**
     * Constructor.
     *
     * @param name
     * @param expression
     */
    public ProcedureArgument(
            final String name ,
            final CoroExpression<T> expression )
    {
        this.name = Objects.requireNonNull( name );
        this.expression = Objects.requireNonNull( expression );
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
