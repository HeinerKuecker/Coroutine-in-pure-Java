package de.heinerkuecker.coroutine.expression;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep;
import de.heinerkuecker.coroutine.HasArgumentsAndVariables;
import de.heinerkuecker.util.ArrayDeepToString;

public class Value<T>
implements CoroExpression<T>
{
    /**
     * For type check.
     *
     * Solve unchecked cast.
     */
    public final Class<? extends T> type;

    public final T value;

    /**
     * Constructor.
     *
     * @param value
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
        this.type = (Class<? extends T>) value.getClass();

        this.value = value;
    }

    @Override
    public T evaluate(
            final HasArgumentsAndVariables/*CoroutineOrProcedureOrComplexstep<?>*/ parent )
    {
        return this.value;
    }

    @Override
    public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    {
        return Collections.emptyList();
    }

    @Override
    public void checkUseVariables(
            //final boolean isCoroutineRoot ,
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstep<?> parent ,
            final Map<String, Class<?>> globalVariableTypes ,
            final Map<String, Class<?>> localVariableTypes )
    {
        // nothing to do
    }

    @Override
    public void checkUseArguments(
            HashSet<String> alreadyCheckedProcedureNames, final CoroutineOrProcedureOrComplexstep<?> parent )
    {
        // nothing to do
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<? extends T>[] type()
    {
        return new Class[] { type };
    }

    public static Value<Boolean> booleanValue(
            final boolean value )
    {
        return
                new Value<Boolean>(
                        Boolean.class ,
                        value );
    }

    public static Value<Integer> intValue(
            final int value )
    {
        return
                new Value<Integer>(
                        Integer.class ,
                        value );
    }

    public static Value<Long> longValue(
            final long value )
    {
        return
                new Value<Long>(
                        Long.class ,
                        value );
    }

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
