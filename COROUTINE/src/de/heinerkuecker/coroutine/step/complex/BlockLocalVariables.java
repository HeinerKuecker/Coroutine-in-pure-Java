package de.heinerkuecker.coroutine.step.complex;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Objects;

import de.heinerkuecker.coroutine.HasCreationStackTraceElement;
import de.heinerkuecker.coroutine.VariablesOrLocalVariables;
import de.heinerkuecker.coroutine.step.simple.DeclareLocalVar;
import de.heinerkuecker.util.ArrayDeepToString;

/**
 * Block local variables with
 * type check at runtime.
 *
 * @author Heiner K&uuml;cker
 */
public class BlockLocalVariables
//implements Iterable<Entry<String, Object>>
implements VariablesOrLocalVariables
{
    private final VariablesOrLocalVariables parentVariables;

    private final HashMap<String, Object> values = new HashMap<>();

    private final HashMap<String, Class<?>> types = new HashMap<>();

    /**
     * Constructor.
     *
     * @param parentVariables
     */
    protected BlockLocalVariables(
            final VariablesOrLocalVariables parentVariables )
    {
        this.parentVariables = parentVariables;
    }

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
        // TODO throw exception, when variableName is unknown
        if ( this.types.containsKey( variableName ) )
        {
            return this.values.get(
                    Objects.requireNonNull(
                            variableName ) );
        }
        return parentVariables.get(
                accessStepOrExpression ,
                variableName );
    }

    @Override
    public void declare(
            //final DeclareLocalVar<?, ?> declareLocalVar ,
            final HasCreationStackTraceElement declareStepOrExpression ,
            final String variableName ,
            final Class<?> type )
    {
        if ( types.containsKey( variableName ) )
        {
            throw new BlockLocalVariables.VariableAlreadyDeclaredException(
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
            //final DeclareLocalVar<?, ?> declareLocalVar ,
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

    public void set(
            final String variableName ,
            final Object value )
    {
        if ( types.containsKey(variableName))
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
                        throw new BlockLocalVariables.WrongVariableClassException(
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
        else
        {
            parentVariables.set(
                    variableName ,
                    value );
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

            sb.append( key );
            sb.append('=');
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
                //final DeclareLocalVar<?, ?> declareLocalVar
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

}
