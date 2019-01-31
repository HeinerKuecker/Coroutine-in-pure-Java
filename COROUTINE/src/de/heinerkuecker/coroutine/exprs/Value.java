package de.heinerkuecker.coroutine.exprs;

import java.util.Objects;

import de.heinerkuecker.coroutine.HasArgumentsAndVariables;
import de.heinerkuecker.util.ArrayDeepToString;

/**
 * Expression to return the specified value.
 *
 * @param <T> value type
 * @author Heiner K&uuml;cker
 */
public class Value<T>
//implements CoroExpression<T>
extends AbstrNoVarsNoArgsExpression<T>
{
    /**
     * For type check.
     *
     * Solve unchecked cast.
     */
    public final Class<? extends T> type;

    /**
     * The value they return the expression.
     */
    public final T value;

    /**
     * Constructor.
     *
     * @param type value type
     * @param value value to return
     */
    public Value(
            final Class<? extends T> type ,
            final T value )
    {
        this.type =
                Objects.requireNonNull(
                        type );

        this.value = value;
    }

    /**
     * Constructor.
     *
     * @param value
     */
    public Value(
            final T value )
    {
        @SuppressWarnings("unchecked")
        final Class<? extends T> valueType = (Class<? extends T>) value.getClass();
        this.type = valueType;

        this.value = value;
    }

    @Override
    public T evaluate(
            final HasArgumentsAndVariables<?>/*CoroutineOrProcedureOrComplexstmt<?, ?>*/ parent )
    {
        return this.value;
    }

    //@Override
    //public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    //{
    //    return Collections.emptyList();
    //}

    //@Override
    //public void checkUseVariables(
    //        final HashSet<String> alreadyCheckedProcedureNames ,
    //        final CoroutineOrProcedureOrComplexstmt<?, ?> parent ,
    //        final Map<String, Class<?>> globalVariableTypes ,
    //        final Map<String, Class<?>> localVariableTypes )
    //{
    //    // nothing to do
    //}

    //@Override
    //public void checkUseArguments(
    //        HashSet<String> alreadyCheckedProcedureNames, final CoroutineOrProcedureOrComplexstmt<?, ?> parent )
    //{
    //    // nothing to do
    //}

    @SuppressWarnings("unchecked")
    @Override
    public Class<? extends T>[] type()
    {
        return new Class[] { type };
    }

    /**
     * Factory method.
     *
     * @param value the value to return
     * @return value expression
     */
    public static Value<Boolean> booleanValue(
            final boolean value )
    {
        return
                new Value<Boolean>(
                        Boolean.class ,
                        value );
    }

    /**
     * Factory method.
     *
     * @return value expression
     */
    public static Value<Boolean> falseValue()
    {
        return
                new Value<Boolean>(
                        Boolean.class ,
                        false );
    }

    /**
     * Factory method.
     *
     * @return value expression
     */
    public static Value<Boolean> trueValue()
    {
        return
                new Value<Boolean>(
                        Boolean.class ,
                        true );
    }

    /**
     * Factory method.
     *
     * @param value the value to return
     * @return value expression
     */
    public static Value<Integer> intValue(
            final int value )
    {
        return
                new Value<Integer>(
                        Integer.class ,
                        value );
    }

    /**
     * Factory method.
     *
     * @param value the value to return
     * @return value expression
     */
    public static Value<Long> longValue(
            final long value )
    {
        return
                new Value<Long>(
                        Long.class ,
                        value );
    }

    /**
     * Factory method.
     *
     * @param value the value to return
     * @return value expression
     */
    public static Value<String> strValue(
            final String value )
    {
        return
                new Value<String>(
                        String.class ,
                        value );
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return
                //this.getClass().getSimpleName() +
                //"[" +
                //"value=" +
                //String.valueOf( this.value )
                ArrayDeepToString.deepToString( this.value );
        //"]";
    }

}
