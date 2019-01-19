package de.heinerkuecker.coroutine;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import de.heinerkuecker.coroutine.step.simple.DeclareVariable;
import de.heinerkuecker.util.ArrayDeepToString;

/**
 * Variables {@link Map} with
 * type check at runtime.
 *
 * @author Heiner K&uuml;cker
 */
public class Variables
//implements Iterable<Entry<String, Object>>
implements VariablesOrLocalVariables
{
    private final HashMap<String, Object> values = new HashMap<>();

    private final HashMap<String, Class<?>> types = new HashMap<>();

    /**
     * Get variable value.
     *
     * @param variableName
     * @return variable value
     */
    @Override
    public Object get(
            final HasCreationStackTraceElement accessStepOrExpression ,
            final String variableName )
    {
        if ( ! this.types.containsKey( variableName ) )
        {
            // throw exception, when variableName is unknown
            throw new VariableNotDeclaredException(
                    accessStepOrExpression ,
                    variableName );
        }

        return this.values.get(
                Objects.requireNonNull(
                        variableName ) );
    }

    @Override
    public void declare(
            //final DeclareVariable<?, ?> declareLocalVar ,
            final HasCreationStackTraceElement declareStepOrExpression ,
            final String variableName ,
            final Class<?> type )
    {
        if ( types.containsKey( variableName ) )
        {
            throw new Variables.VariableAlreadyDeclaredException(
                    declareStepOrExpression ,
                    variableName );
        }

        this.types.put(
                Objects.requireNonNull(
                        variableName ) ,
                Objects.requireNonNull(
                        type ) );
    }

    /**
     * Convenience method.
     *
     * @param variableName
     * @param type
     * @param value
     */
    @Override
    public <T> void declare(
            //final DeclareVariable<?, ?> declareLocalVar ,
            final HasCreationStackTraceElement declareStepOrExpression ,
            final String variableName ,
            final Class<T> type ,
            final T value )
    {
        declare(
                declareStepOrExpression ,
                variableName ,
                type );

        set(
                Objects.requireNonNull(
                        variableName ) ,
                value );
    }

    @Override
    public void set(
            final String variableName ,
            final Object value )
    {
        if ( value == null )
        {
            this.values.remove(
                    Objects.requireNonNull(
                            variableName ) );
        }
        else
        {
            if ( types.containsKey( variableName ) )
            {
                if ( ! types.get( variableName ).isInstance( value ) )
                {
                    //throw new ClassCastException( value.getClass().toString() );
                    throw new Variables.WrongVariableClassException(
                            variableName ,
                            // expectedClass
                            types.get( variableName ) ,
                            // wrongValue
                            value );
                }
            }

            this.values.put(
                    Objects.requireNonNull(
                            variableName ) ,
                    // TODO types.get( variableName ).cast(
                            value
                            //)
            );
        }
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        //return "Variables [innerVars=" + this.innerVars + "]";
        //return String.valueOf( this.values );

        StringBuilder sb = new StringBuilder();
        for ( final Entry<String, Object> valuesEntry : this.values.entrySet() )
        {
            String key = valuesEntry.getKey();
            Object value = valuesEntry.getValue();

            if ( sb.length() != 0 )
            {
                sb.append(',').append(' ');
            }

            sb.append( types.get( key ) );
            sb.append( ' ' );
            sb.append( key );
            sb.append( '=' );
            sb.append(
                    value == this.values
                        ? "(this Map circular reference)"
                        : ArrayDeepToString.deepToString( value ) );
        }
        return "" + '{' + sb + '}';
    }

    /**
     * @see Iterable#iterator()
     */
    @Override
    public Iterator<Entry<String, Object>> iterator()
    {
        return this.values.entrySet().iterator();
    }

    /**
     * Exception
     */
    public static class VariableAlreadyDeclaredException
    extends RuntimeException
    {
        /**
         * Generated by Eclipse.
         */
        private static final long serialVersionUID = 462065284743881352L;

        /**
         * Constructor.
         */
        public VariableAlreadyDeclaredException(
                final HasCreationStackTraceElement declareStepOrExpression ,
                final String variableName )
        {
            //super( "variable already declared: " + variableName );
            super(
                    "variable already declared: " +
                    variableName +
                    " " +
                    declareStepOrExpression.getClass().getSimpleName() +
                    ( declareStepOrExpression.creationStackTraceElement != null
                        ? " " + declareStepOrExpression.creationStackTraceElement
                        : "" ) );
        }
    }

    /**
     * Exception
     */
    public static class WrongVariableClassException
    extends RuntimeException
    {
        /**
         * Generated by Eclipse.
         */
        private static final long serialVersionUID = -3843489649279228520L;

        /**
         * Constructor.
         *
         * @param message
         */
        public WrongVariableClassException(
                final String variableName ,
                final Class<?> expectedClass ,
                final Object wrongValue )
        {
            super(
                    "wrong class for variable: " +
                    variableName + ", " +
                    "expected class: " +
                    expectedClass + ", " +
                    "wrong value: " +
                    wrongValue + " " +
                    "wrong class: " +
                    wrongValue.getClass() );
        }
    }

    /**
     * Exception
     */
    public static class VariableNotDeclaredException
    extends RuntimeException
    {
        /**
         * Generated by Eclipse.
         */
        private static final long serialVersionUID = -1550792830722168281L;

        /**
         * Constructor.
         */
        public VariableNotDeclaredException(
                final HasCreationStackTraceElement wrongStepOrExpression ,
                final String variableName )
        {
            super(
                    "variable not declared: " +
                    variableName + " " +
                    wrongStepOrExpression );
        }
    }

}
