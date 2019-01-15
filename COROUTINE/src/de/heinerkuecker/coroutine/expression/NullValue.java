package de.heinerkuecker.coroutine.expression;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import de.heinerkuecker.coroutine.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine.HasArgumentsAndVariables;

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

    @Override
    public T evaluate(
            final HasArgumentsAndVariables/*CoroIteratorOrProcedure<?>*/ parent )
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

    @Override
    public void checkUseVariables(
            HashSet<String> alreadyCheckedProcedureNames ,
            final CoroIteratorOrProcedure<?> parent ,
            final Map<String, Class<?>> globalVariableTypes, final Map<String, Class<?>> localVariableTypes )
    {
        // nothing to do
    }

    @Override
    public void checkUseArguments(
            HashSet<String> alreadyCheckedProcedureNames, final CoroIteratorOrProcedure<?> parent )
    {
        // nothing to do
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
