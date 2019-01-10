package de.heinerkuecker.coroutine.expression;

import java.util.Collections;
import java.util.List;

import de.heinerkuecker.coroutine.CoroIteratorOrProcedure;

/**
 * Short cut for
 * <code>new {@link Value}( null )</code>.
 *
 * The specification of this class or of
 * <code>new {@link Value}( null )</code>.
 * is necessary for the convenience constructors
 * to declare that it is an expression
 * that returns null and
 * is not an expression of zero.
 *
 * @author Heiner K&uuml;cker
 */
public class NullValue<T>
implements CoroExpression<T>
{
    /**
     * Singleton instance.
     */
    public static final NullValue<?> INSTANCE = new NullValue<>();

    /**
     * Factory method.
     *
     * @return {@link #INSTANCE}
     */
    @SuppressWarnings("unchecked")
    public static final <T> NullValue<? extends T> nullValue()
    {
        return (NullValue<T>) INSTANCE;
    }

    /**
     * Singleton Constructor.
     */
    private NullValue()
    {
        super();
    }

    /**
     * @see CoroExpression#evaluate(CoroIteratorOrProcedure)
     */
    @Override
    public T evaluate(
            final CoroIteratorOrProcedure<?> parent )
    {
        return null;
    }

    /**
     * @see CoroExpression#getProcedureArgumentGetsNotInProcedure()
     */
    @Override
    public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    {
        return Collections.emptyList();
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return this.getClass().getSimpleName();
    }

}
