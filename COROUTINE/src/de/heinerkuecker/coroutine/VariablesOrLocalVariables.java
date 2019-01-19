package de.heinerkuecker.coroutine;

import java.util.Map.Entry;

import de.heinerkuecker.coroutine.step.simple.DeclareLocalVar;

// TODO Klasse Variables und BlockLocalVariables zu einer Klasse zusammerfassen mit parentVariables == null bei Variables
public interface VariablesOrLocalVariables
extends Iterable<Entry<String, Object>>
{

    Object get(
            final HasCreationStackTraceElement accessStepOrExpression ,
            final String variableName );

    void declare(
            //final DeclareLocalVar<?, ?> declareLocalVar ,
            final HasCreationStackTraceElement declareStepOrExpression ,
            final String variableName ,
            final Class<?> type );

    <T> void declare(
            //final DeclareLocalVar<?, ?> declareLocalVar ,
            final HasCreationStackTraceElement declareStepOrExpression ,
            final String variableName ,
            final Class<T> type ,
            final T value );

    void set(
            final String variableName ,
            final Object value );



}
