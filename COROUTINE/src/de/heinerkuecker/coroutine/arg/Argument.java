package de.heinerkuecker.coroutine.arg;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroCheckable;
import de.heinerkuecker.coroutine.CoroutineOrFunctioncallOrComplexstmt;
import de.heinerkuecker.coroutine.exprs.CoroExpression;
import de.heinerkuecker.coroutine.exprs.GetFunctionArgument;
import de.heinerkuecker.coroutine.exprs.SimpleExpression;
import de.heinerkuecker.coroutine.exprs.Value;

/**
 * Argument for function or
 * the entire coroutine.
 *
 * @param <ARGUMENT> argument/parameter type
 * @author Heiner K&uuml;cker
 */
public class Argument<ARGUMENT , COROUTINE_RETURN , RESUME_ARGUMENT>
implements CoroCheckable
{
    public final String name;

    public final SimpleExpression<ARGUMENT , COROUTINE_RETURN , RESUME_ARGUMENT> expression;

    /**
     * Constructor.
     *
     * @param name
     * @param expression
     */
    public Argument(
            final String name ,
            final SimpleExpression<ARGUMENT , COROUTINE_RETURN , RESUME_ARGUMENT> expression )
    {
        this.name = Objects.requireNonNull( name );
        this.expression = Objects.requireNonNull( expression );
    }

    /**
     * Convenience Constructor.
     *
     * @param name
     * @param expression
     */
    public Argument(
            final String name ,
            final ARGUMENT value )
    {
        this.name = Objects.requireNonNull( name );

        this.expression =
                new Value<>(
                        (Class<? extends ARGUMENT>) value.getClass() ,
                        value );
    }

    public ARGUMENT getValue(
            final CoroutineOrFunctioncallOrComplexstmt<?, ?, RESUME_ARGUMENT> parent )
    {
        @SuppressWarnings("unchecked")
        final Class<ARGUMENT> parameterType = (Class<ARGUMENT>) parent.functionParameterTypes().get( name );

        return
                parameterType.cast(
                        this.expression.evaluate( parent ) );
    }

    @Override
    public List<GetFunctionArgument<? , ? , ?>> getFunctionArgumentGetsNotInFunction()
    {
        return this.expression.getFunctionArgumentGetsNotInFunction();
    }

    @Override
    public void checkUseVariables(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent ,
            final Map<String, Class<?>> globalVariableTypes ,
            final Map<String, Class<?>> localVariableTypes )
    {
        this.expression.checkUseVariables(
                alreadyCheckedFunctionNames ,
                parent ,
                globalVariableTypes, localVariableTypes );
    }

    @Override
    public void checkUseArguments(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent )
    {
        final Class<?> parameterType = parent.functionParameterTypes().get( name );

        if ( parameterType == null )
        {
            throw new ArgumentNotDeclaredException( this );
        }

        final Class<? extends ARGUMENT>[] typeArr = this.expression.type();

        for ( final Class<? extends ARGUMENT> type : typeArr )
        {
            if ( ! parameterType.isAssignableFrom( type ) )
            {
                throw new WrongArgumentClassException(
                        //wrongExpression
                        this.expression ,
                        //wrongClass
                        type ,
                        //expectedClass
                        parameterType );
            }
        }

        this.expression.checkUseArguments(
                alreadyCheckedFunctionNames ,
                parent );
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return this.getClass().getSimpleName() + "[name=" + this.name + ", expression=" + this.expression + "]";
    }

    /**
     * Exception
     */
    public static class ArgumentNotDeclaredException
    extends RuntimeException
    {
        /**
         * Generated by Eclipse.
         */
        private static final long serialVersionUID = -2823336930271744702L;

        /**
         * Constructor.
         *
         * @param stmtOrExpression statement or expression with access to not declared global variable
         */
        //public <T extends HasCreationStackTraceElement & HasVariableName> ArgumentNotDeclaredException(
        //        final T stmtOrExpression )
        public ArgumentNotDeclaredException(
                final Argument<? , ? , ?> argument )
        {
            super(
                    "argument not declared: " +
                    argument );
        }

    }

    /**
     * Exception
     */
    public static class WrongArgumentClassException
    extends RuntimeException
    {
        /**
         * Generated by Eclipse.
         */

        /**
         * Constructor.
         */
        public WrongArgumentClassException(
                //final HasVariableName wrongExpression ,
                final CoroExpression<? , ? , ?> wrongExpression ,
                final Class<?> wrongClass ,
                final Class<?> expectedClass )
        {
            super(
                    "wrong variable class for expression: " +
                    wrongExpression + ",\n" +
                    "expected class: " +
                    expectedClass + ", " +
                    "wrong class: " +
                    wrongClass );
        }
    }

}
