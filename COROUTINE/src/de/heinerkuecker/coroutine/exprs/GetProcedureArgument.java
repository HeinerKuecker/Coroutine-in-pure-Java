package de.heinerkuecker.coroutine.exprs;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroCheckable;
import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstmt;
import de.heinerkuecker.coroutine.HasArgumentName;
import de.heinerkuecker.coroutine.HasArgumentsAndVariables;
import de.heinerkuecker.coroutine.HasCreationStackTraceElement;
import de.heinerkuecker.coroutine.exprs.exc.WrongExpressionClassException;
import de.heinerkuecker.util.ArrayTypeName;

public class GetProcedureArgument<T>
extends HasCreationStackTraceElement
implements CoroExpression<T> , HasArgumentName
{
    /**
     * Name of procedure argument in
     * {@link CoroutineOrProcedureOrComplexstmt#procedureArgumentValues()}
     * to return.
     */
    public final String procedureArgumentName;

    /**
     * For type check.
     *
     * Solve unchecked cast.
     */
    public final Class<T> type;

    /**
     * Constructor.
     * @param procedureArgumentName
     */
    public GetProcedureArgument(
            final String procedureArgumentName ,
            final Class<T> type )
    {
        super(
                //creationStackOffset
                2 );

        this.procedureArgumentName =
                Objects.requireNonNull(
                        procedureArgumentName );

        this.type =
                Objects.requireNonNull(
                        type );
    }

    @Override
    public T evaluate(
            final HasArgumentsAndVariables<?>/*CoroutineOrProcedureOrComplexstmt<?, ?>*/ parent )
    {
        final Object procArgValue = parent.procedureArgumentValues().get( procedureArgumentName );

        if ( procArgValue != null &&
                ! type.isInstance( procArgValue ) )
        {
            //throw new ClassCastException(
            //        procArgValue.getClass().toString() +
            //        ( this.creationStackTraceElement != null
            //            ? " " + this.creationStackTraceElement
            //            : "" ) );
            throw new WrongExpressionClassException(
                    //valueExpression
                    this ,
                    //expectedClass
                    type ,
                    //wrongValue
                    procArgValue );
        }

        return type.cast( procArgValue );
    }

    @Override
    public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    {
        return Arrays.asList( this );
    }

    @Override
    public void checkUseVariables(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstmt<?, ?> parent ,
            final Map<String, Class<?>> globalVariableTypes ,
            final Map<String, Class<?>> localVariableTypes )
    {
        // nothing to do
    }

    /**
     * @see CoroCheckable#checkUseArguments(HashSet, CoroutineOrProcedureOrComplexstmt)
     */
    @Override
    public void checkUseArguments(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstmt<?, ?> parent )
    {
        if ( ! parent.procedureParameterTypes().containsKey( this.procedureArgumentName ) )
        {
            throw new ProcedureArgumentNotDeclaredException( this );
        }

        final Class<?> argumentType = parent.procedureParameterTypes().get( this.procedureArgumentName );

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
        return this.procedureArgumentName;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<? extends T>[] type()
    {
        //return type;
        return new Class[] { type };
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
                //"procedureArgumentName=" +
                this.procedureArgumentName +
                //"]"
                ( this.creationStackTraceElement != null
                    ? " " + this.creationStackTraceElement
                    : "" );
    }

    /**
     * Exception
     */
    public static class ProcedureArgumentNotDeclaredException
    extends RuntimeException
    {
        /**
         * Generated by Eclipse.
         */
        private static final long serialVersionUID = 3148874855690178787L;

        /**
         * Constructor.
         *
         * @param stmtOrExpression statement or expression with access to not declared procedure argument
         */
        public <T extends HasCreationStackTraceElement & HasArgumentName> ProcedureArgumentNotDeclaredException(
                final T stmtOrExpression )
        {
            super(
                    "procedure argument not declared: " +
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
