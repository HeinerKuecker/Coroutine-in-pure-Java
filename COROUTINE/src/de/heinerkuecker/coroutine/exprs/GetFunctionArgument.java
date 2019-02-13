package de.heinerkuecker.coroutine.exprs;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroCheckable;
import de.heinerkuecker.coroutine.CoroutineOrFunctioncallOrComplexstmt;
import de.heinerkuecker.coroutine.HasArgumentName;
import de.heinerkuecker.coroutine.HasArgumentsAndVariables;
import de.heinerkuecker.coroutine.HasCreationStackTraceElement;
import de.heinerkuecker.coroutine.exprs.exc.WrongExpressionResultValueClassException;
import de.heinerkuecker.util.ArrayTypeName;

public class GetFunctionArgument<FUNCTION_ARGUMENT , COROUTINE_RETURN>
extends HasCreationStackTraceElement
implements SimpleExpression<FUNCTION_ARGUMENT , COROUTINE_RETURN> , HasArgumentName
{
    /**
     * Name of function argument in
     * {@link CoroutineOrFunctioncallOrComplexstmt#functionArgumentValues()}
     * to return.
     */
    public final String functionArgumentName;

    /**
     * For type check.
     *
     * Solve unchecked cast.
     */
    public final Class<FUNCTION_ARGUMENT> type;

    /**
     * Constructor.
     * @param functionArgumentName
     */
    public GetFunctionArgument(
            final String functionArgumentName ,
            final Class<FUNCTION_ARGUMENT> type )
    {
        super(
                //creationStackOffset
                2 );

        this.functionArgumentName =
                Objects.requireNonNull(
                        functionArgumentName );

        this.type =
                Objects.requireNonNull(
                        type );
    }

    @Override
    public FUNCTION_ARGUMENT evaluate(
            final HasArgumentsAndVariables<?>/*CoroutineOrFunctioncallOrComplexstmt<?, ?>*/ parent )
    {
        final Object funcArgValue = parent.functionArgumentValues().get( functionArgumentName );

        if ( funcArgValue != null &&
                ! type.isInstance( funcArgValue ) )
        {
            //throw new ClassCastException(
            //        procArgValue.getClass().toString() +
            //        ( this.creationStackTraceElement != null
            //            ? " " + this.creationStackTraceElement
            //            : "" ) );
            throw new WrongExpressionResultValueClassException(
                    //valueExpression
                    this ,
                    //expectedClass
                    type ,
                    //wrongValue
                    funcArgValue );
        }

        return type.cast( funcArgValue );
    }

    @Override
    public List<GetFunctionArgument<? , ?>> getFunctionArgumentGetsNotInFunction()
    {
        return Arrays.asList( this );
    }

    @Override
    public void checkUseVariables(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent ,
            final Map<String, Class<?>> globalVariableTypes ,
            final Map<String, Class<?>> localVariableTypes )
    {
        // nothing to do
    }

    /**
     * @see CoroCheckable#checkUseArguments(HashSet, CoroutineOrFunctioncallOrComplexstmt)
     */
    @Override
    public void checkUseArguments(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent )
    {
        if ( ! parent.functionParameterTypes().containsKey( this.functionArgumentName ) )
        {
            throw new FunctionArgumentNotDeclaredException( this );
        }

        final Class<?> argumentType = parent.functionParameterTypes().get( this.functionArgumentName );

        if ( ! argumentType.isAssignableFrom( this.type ) )
        {
            throw new WrongArgumentClassException(
                    //wrongNamedExpression
                    this ,
                    //wrongClass
                    this.type ,
                    //expectedClass
                    argumentType );
        }
    }

    @Override
    public String getArgumentName()
    {
        return this.functionArgumentName;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<? extends FUNCTION_ARGUMENT>[] type()
    {
        //return type;
        return new Class[] { type };
    }

    @Override
    public void setExprCoroutineReturnType(
            HashSet<String> alreadyCheckedFunctionNames ,
            CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent ,
            Class<?> coroutineReturnType )
    {
        // do nothing
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return
                this.getClass().getSimpleName() +
                //"[" +
                " " +
                //this.type.getName() +
                ArrayTypeName.toStr( this.type ) +
                " " +
                //"functionArgumentName=" +
                this.functionArgumentName +
                //"]"
                ( this.creationStackTraceElement != null
                    ? " " + this.creationStackTraceElement
                    : "" );
    }

    /**
     * Exception
     */
    public static class FunctionArgumentNotDeclaredException
    extends RuntimeException
    {
        /**
         * Generated by Eclipse.
         */
        private static final long serialVersionUID = 3148874855690178787L;

        /**
         * Constructor.
         *
         * @param stmtOrExpression statement or expression with access to not declared function argument
         */
        public <T extends HasCreationStackTraceElement & HasArgumentName> FunctionArgumentNotDeclaredException(
                final T stmtOrExpression )
        {
            super(
                    "function argument not declared: " +
                    stmtOrExpression );
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
        private static final long serialVersionUID = 5452841405188807894L;

        /**
         * Constructor.
         */
        public WrongArgumentClassException(
                final HasArgumentName wrongNamedExpression ,
                final Class<?> wrongClass ,
                final Class<?> expectedClass )
        {
            super(
                    "wrong argument class for expression: " +
                    wrongNamedExpression + ",\n" +
                    "expected class: " +
                    expectedClass + ", " +
                    "wrong class: " +
                    wrongClass );
        }
    }

}
