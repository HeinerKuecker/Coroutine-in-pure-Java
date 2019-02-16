package de.heinerkuecker.coroutine.exprs;

import java.util.Objects;

import de.heinerkuecker.coroutine.HasArgumentsAndVariables;
import de.heinerkuecker.util.ArrayDeepToString;

/**
 * Expression to return the specified value.
 *
 * @param <VALUE> value type
 * @author Heiner K&uuml;cker
 */
public class Value<VALUE , COROUTINE_RETURN>
//implements CoroExpression<T>
extends AbstrNoVarsNoArgsExpression<VALUE , COROUTINE_RETURN>
{
    /**
     * For type check.
     *
     * Solve unchecked cast.
     */
    public final Class<? extends VALUE> type;

    /**
     * The value they return the expression.
     */
    public final VALUE value;

    /**
     * Constructor.
     *
     * @param type value type
     * @param value value to return
     */
    public Value(
            final Class<? extends VALUE> type ,
            final VALUE value )
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
            final VALUE value )
    {
        @SuppressWarnings("unchecked")
        final Class<? extends VALUE> valueType = (Class<? extends VALUE>) value.getClass();
        this.type = valueType;

        this.value = value;
    }

    @Override
    public VALUE evaluate(
            final HasArgumentsAndVariables<?>/*CoroutineOrFunctioncallOrComplexstmt<?, ?>*/ parent )
    {
        return this.value;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<? extends VALUE>[] type()
    {
        return new Class[] { type };
    }

    //@Override
    //public void setExprCoroutineReturnType(
    //        HashSet<String> alreadyCheckedFunctionNames ,
    //        CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent ,
    //        Class<?> coroutineReturnType )
    //{
    //    // do nothing
    //}

    /**
     * Factory method.
     *
     * @param value the value to return
     * @return value expression
     */
    public static <COROUTINE_RETURN> Value<Boolean , COROUTINE_RETURN> booleanValue(
            final boolean value )
    {
        return
                new Value<Boolean , COROUTINE_RETURN>(
                        Boolean.class ,
                        value );
    }

    /**
     * Factory method.
     *
     * @return value expression
     */
    public static <COROUTINE_RETURN> Value<Boolean , COROUTINE_RETURN> falseValue()
    {
        return
                new Value<Boolean , COROUTINE_RETURN>(
                        Boolean.class ,
                        false );
    }

    /**
     * Factory method.
     *
     * @return value expression
     */
    public static <COROUTINE_RETURN> Value<Boolean , COROUTINE_RETURN> trueValue()
    {
        return
                new Value<Boolean , COROUTINE_RETURN>(
                        Boolean.class ,
                        true );
    }

    /**
     * Factory method.
     *
     * @param value the value to return
     * @return value expression
     */
    public static <COROUTINE_RETURN> Value<Integer , COROUTINE_RETURN> intValue(
            final int value )
    {
        return
                new Value<Integer , COROUTINE_RETURN>(
                        Integer.class ,
                        value );
    }

    /**
     * Factory method.
     *
     * @param value the value to return
     * @return value expression
     */
    public static <COROUTINE_RETURN> Value<Long , COROUTINE_RETURN> longValue(
            final long value )
    {
        return
                new Value<Long , COROUTINE_RETURN>(
                        Long.class ,
                        value );
    }

    /**
     * Factory method.
     *
     * @param value the value to return
     * @return value expression
     */
    public static <COROUTINE_RETURN> Value<String , COROUTINE_RETURN> strValue(
            final String value )
    {
        return
                new Value<String , COROUTINE_RETURN>(
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
