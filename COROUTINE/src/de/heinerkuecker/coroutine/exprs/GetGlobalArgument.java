package de.heinerkuecker.coroutine.exprs;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstmt;
import de.heinerkuecker.coroutine.HasArgumentName;
import de.heinerkuecker.coroutine.HasArgumentsAndVariables;
import de.heinerkuecker.coroutine.HasCreationStackTraceElement;
import de.heinerkuecker.coroutine.exprs.exc.WrongExpressionClassException;

public class GetGlobalArgument<T>
extends HasCreationStackTraceElement
implements CoroExpression<T> , HasArgumentName
{
    /**
     * Name of procedure argument in
     * {@link CoroutineOrProcedureOrComplexstmt#globalArgumentValues()}
     * to return.
     */
    public final String globalArgumentName;

    /**
     * For type check.
     *
     * Solve unchecked cast.
     */
    public final Class<T> type;

    /**
     * Constructor.
     * @param globalArgumentName
     */
    public GetGlobalArgument(
            final String globalArgumentName ,
            final Class<T> type )
    {
        super(
                //creationStackOffset
                2 );

        this.globalArgumentName =
                Objects.requireNonNull(
                        globalArgumentName );

        this.type =
                Objects.requireNonNull(
                        type );
    }

    @Override
    public T evaluate(
            final HasArgumentsAndVariables<?>/*CoroutineOrProcedureOrComplexstep<?, ?>*/ parent )
    {
        final Object procArgValue = parent.globalArgumentValues().get( globalArgumentName );

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
    public void checkUseVariables(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstmt<?, ?> parent ,
            final Map<String, Class<?>> globalVariableTypes, final Map<String, Class<?>> localVariableTypes )
    {
        // nothing to do
    }

    @Override
    public void checkUseArguments(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstmt<?, ?> parent )
    {
        if ( ! parent.globalParameterTypes().containsKey( this.globalArgumentName ) )
        {
            throw new LocalVariableNotDeclaredException( this );
        }

        final Class<?> argumentType = parent.globalParameterTypes().get( this.globalArgumentName );

        if ( ! argumentType.isAssignableFrom( this.type ) )
        {
            throw new GetProcedureArgument.WrongArgumentClassException(
                    //wrongNamedExpression
                    this ,
                    //wrongClass
                    this.type ,
                    //expectedClass
                    argumentType );
        }

    }

    @Override
    public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    {
        return Collections.emptyList();
    }

    @Override
    public String getArgumentName()
    {
        return this.globalArgumentName;
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
                "[" +
                //"globalArgumentName=" +
                this.globalArgumentName +
                "]";
    }

    /**
     * Exception
     */
    public static class LocalVariableNotDeclaredException
    extends RuntimeException
    {
        /**
         * Generated by Eclipse.
         */
        private static final long serialVersionUID = 2715294591972011082L;

        /**
         * Constructor.
         *
         * @param stmtOrExpression statement or expression with access to not declared global argument (argument of coroutine)
         */
        public <T extends HasCreationStackTraceElement & HasArgumentName> LocalVariableNotDeclaredException(
                final T stmtOrExpression )
        {
            super(
                    "global argument not declared: " +
                    stmtOrExpression );
        }

    }

}
