package de.heinerkuecker.coroutine.exprs;

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
//implements CoroExpression<T>
extends AbstrNoVarsNoArgsExpression<T>
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
            final HasArgumentsAndVariables<?>/*CoroutineOrFunctioncallOrComplexstmt<?, ?>*/ parent )
    {
        return null;
    }

    @Override
    public Class<? extends T>[] type()
    {
        // special class instance for null
        //return null;
        //return void.class;
        //return Void.class;
        // das array besteht aus und-verknuepften typen beim pruefen, beim aufzaehlen oder-verknuepft, ein leeres und wird zu true evaluiert, null ist also immer ein gueltiger typ
        return new Class[] {};
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
