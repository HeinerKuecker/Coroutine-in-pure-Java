package de.heinerkuecker.coroutine;

import java.util.Map.Entry;

// TODO Klasse Variables und BlockLocalVariables zu einer Klasse zusammerfassen mit parentVariables == null bei Variables
public interface VariablesOrLocalVariables
extends Iterable<Entry<String, Object>>
{

    Object get(
            final HasCreationStackTraceElement accessStepOrExpression ,
            final String variableName );

    void declare(
            //final DeclareVariable<?, ?> declareLocalVar ,
            final HasCreationStackTraceElement declareStepOrExpression ,
            final String variableName ,
            final Class<?> type );

    <T> void declare(
            //final DeclareVariable<?, ?> declareLocalVar ,
            final HasCreationStackTraceElement declareStepOrExpression ,
            final String variableName ,
            final Class<? extends T> type ,
            final T value );

    void set(
            final String variableName ,
            final Object value );



}
