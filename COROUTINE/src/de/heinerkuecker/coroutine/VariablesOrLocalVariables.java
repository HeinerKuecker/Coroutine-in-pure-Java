package de.heinerkuecker.coroutine;

import java.util.Map.Entry;

public interface VariablesOrLocalVariables
extends Iterable<Entry<String, Object>>
{

    Object get(
            final String variableName );

    void declare(
            final String variableName ,
            final Class<?> type );

    <T> void declare(
            final String variableName ,
            final Class<T> type ,
            final T value );

    void set(
            final String variableName ,
            final Object value );



}
