package de.heinerkuecker.coroutine;

import java.util.Map;
import java.util.Map.Entry;

// TODO Klasse GlobalVariables und BlockLocalVariables zu einer Klasse zusammerfassen mit parentVariables == null bei GlobalVariables
public interface VariablesOrLocalVariables
extends Iterable<Entry<String, Object>>
{

    Object get(
            final HasCreationStackTraceElement accessStmtOrExpression ,
            final String variableName );

    void declare(
            //final DeclareVariable<?, ?> declareLocalVar ,
            final HasCreationStackTraceElement declareStmtOrExpression ,
            final String variableName ,
            final Class<?> type );

    <T> void declare(
            //final DeclareVariable<?, ?> declareLocalVar ,
            final HasCreationStackTraceElement declareStmtOrExpression ,
            final String variableName ,
            final Class<? extends T> type ,
            final T value );

    void set(
            final String variableName ,
            final Object value );

    Map<String, Class<?>> getVariableTypes();

    boolean isEmpty();

}
