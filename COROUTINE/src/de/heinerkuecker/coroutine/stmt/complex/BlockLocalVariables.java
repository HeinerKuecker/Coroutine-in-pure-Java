package de.heinerkuecker.coroutine.stmt.complex;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import de.heinerkuecker.coroutine.HasCreationStackTraceElement;
import de.heinerkuecker.coroutine.VariablesOrLocalVariables;
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
            final HasCreationStackTraceElement accessStmtOrExpression ,
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
                accessStmtOrExpression ,
                variableName );
    }

    @Override
    public void declare(
            //final DeclareVariable<?, ?> declareLocalVar ,
            final HasCreationStackTraceElement declareStmtOrExpression ,
            final String variableName ,
            final Class<?> type )
    {
        if ( types.containsKey( variableName ) )
        {
            throw new BlockLocalVariables.VariableAlreadyDeclaredException(
                    declareStmtOrExpression ,
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
            final HasCreationStackTraceElement declareStmtOrExpression ,
            final String variableName ,
            final Class<? extends T> type ,
            final T value )
    {
        declare(
                declareStmtOrExpression ,
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
     * @see Iterable#iterator()
     */
    @Override
    public Iterator<Entry<String, Object>> iterator()
    {
        return this.values.entrySet().iterator();
    }

    @Override
    public Map<String, Class<?>> getVariableTypes()
    {
        return Collections.unmodifiableMap( this.types );
    }

    @Override
    public boolean isEmpty()
    {
        return types.isEmpty();
    }

    void reset()
    {
        this.types.clear();
        this.values.clear();
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
                //final DeclareVariable<?, ?> declareLocalVar
                final HasCreationStackTraceElement declareStmtOrExpression ,
                final String variableName )
        {
            //super( "variable already declared: " + variableName );
            super(
                    "variable already declared: " +
                    variableName +
                    " " +
                    declareStmtOrExpression.getClass().getSimpleName() +
                    ( declareStmtOrExpression.creationStackTraceElement != null
                        ? " " + declareStmtOrExpression.creationStackTraceElement
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
