package de.heinerkuecker.coroutine.arg;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep;
import de.heinerkuecker.util.ArrayDeepToString;

/**
 * Argument values {@link Map}
 * for {@link Parameter}
 * with type check at runtime.
 *
 * Parameters are unmodifiable.
 *
 * @author Heiner K&uuml;cker
 */
public class Arguments
implements Iterable<Entry<String, Object>>
{
    private final HashMap<String, Object> values = new HashMap<>();

    private final Map<String, Parameter> params;

    /**
     * Constructor.
     *
     * @param params
     * @param args
     * @param parent
     */
    public Arguments(
            final boolean checkMandantoryValues ,
            final Map<String, Parameter> params ,
            final Argument<?>[] args ,
            final CoroutineOrProcedureOrComplexstep<?> parent )
    {
        Set<String> missedMandantoryParamNames = null;

        if ( checkMandantoryValues )
        {
            missedMandantoryParamNames = new HashSet<>();
        }

        if ( params != null )
        {
            if ( checkMandantoryValues )
            {
                for ( Parameter param : params.values() )
                {
                    if ( param.isMandantory )
                    {
                        missedMandantoryParamNames.add(
                                param.name );
                    }
                }
            }

            this.params = params;
        }
        else
        {
            this.params = Collections.emptyMap();
        }

        if ( args != null )
        {
            for ( final Argument<?> argument : args )
            {
                if ( params == null ||
                        ! params.containsKey( argument.name ) )
                {
                    throw new IllegalArgumentException(
                            "undefined argument name: " +
                            argument.name );
                }

                if ( checkMandantoryValues )
                {
                    missedMandantoryParamNames.remove( argument.name );
                }

                final Object argumentValue =
                        argument.expression.evaluate(
                                parent );

                if ( argumentValue != null )
                {
                    final Parameter param =
                            params.get(
                                    argument.name );

                    if ( ! param.type.isInstance( argumentValue ) )
                    {
                        throw new WrongArgumentValueClassException(
                                argument.name ,
                                //expectedClass
                                param.type ,
                                //wrongValue
                                argumentValue );
                    }

                    values.put(
                            argument.name ,
                            argumentValue );
                }
            }
        }

        if ( checkMandantoryValues &&
                ! missedMandantoryParamNames.isEmpty() )
        {
            throw new MissedArgumentException(
                    missedMandantoryParamNames.toString() );
        }
    }

    /**
     * Empty instance.
     */
    public static Arguments EMPTY =
            new Arguments(
                    // checkMandantoryValues
                    false ,
                    //params
                    null ,
                    //args
                    null ,
                    //parent
                    null );

    /**
     * Get argument value.
     *
     * @param argumentName
     * @return argument value
     */
    public Object get(
            final String argumentName )
    {
        // TODO throw exception, when variableName is unknown
        return this.values.get(
                Objects.requireNonNull(
                        argumentName ) );
    }

    @SuppressWarnings("javadoc")
    public Map<String, Class<?>> procedureParameterTypes()
    {
        final Map<String, Class<?>> paramTypes = new HashMap<>();

        for ( final Parameter param : this.params.values() )
        {
            paramTypes.put(
                    param.name ,
                    param.type );
        }

        return paramTypes;
    }

    /**
     * @see java.lang.Iterable#iterator()
     */
    @Override
    public Iterator<Entry<String, Object>> iterator()
    {
        return this.values.entrySet().iterator();
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
    public static class WrongArgumentValueClassException
    extends RuntimeException
    {
        /**
         * Generated by Eclipse.
         */
        private static final long serialVersionUID = -3843489649279228520L;

        /**
         * Constructor.
         */
        public WrongArgumentValueClassException(
                final String argumentName ,
                final Class<?> expectedClass ,
                final Object wrongValue )
        {
            super(
                    "wrong class for argument: " +
                    argumentName + ", " +
                    "expected class: " +
                    expectedClass + ", " +
                    "wrong value: " +
                    wrongValue + ", " +
                    "wrong class: " +
                    wrongValue.getClass() );
        }
    }

    /**
     * Exception
     */
    public static class MissedArgumentException
    extends RuntimeException
    {
        /**
         * Generated by Eclipse.
         */
        private static final long serialVersionUID = 1944167892402238600L;

        /**
         * Constructor.
         */
        public MissedArgumentException(
                final String argumentNames )
        {
            super( argumentNames );
        }
    }

}
