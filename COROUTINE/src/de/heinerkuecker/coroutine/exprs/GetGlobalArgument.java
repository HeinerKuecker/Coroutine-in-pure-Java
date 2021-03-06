package de.heinerkuecker.coroutine.exprs;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineOrFunctioncallOrComplexstmt;
import de.heinerkuecker.coroutine.HasArgumentName;
import de.heinerkuecker.coroutine.HasArgumentsAndVariables;
import de.heinerkuecker.coroutine.HasCreationStackTraceElement;
import de.heinerkuecker.coroutine.exprs.exc.WrongExpressionResultValueClassException;

public class GetGlobalArgument<GLOBAL_ARGUMENT , COROUTINE_RETURN , RESUME_ARGUMENT>
extends HasCreationStackTraceElement
implements SimpleExpression<GLOBAL_ARGUMENT , COROUTINE_RETURN , RESUME_ARGUMENT> , HasArgumentName
{
    /**
     * Name of function argument in
     * {@link CoroutineOrFunctioncallOrComplexstmt#globalArgumentValues()}
     * to return.
     */
    public final String globalArgumentName;

    /**
     * For type check.
     *
     * Solve unchecked cast.
     */
    public final Class<GLOBAL_ARGUMENT> type;

    /**
     * Constructor.
     * @param globalArgumentName
     */
    public GetGlobalArgument(
            final String globalArgumentName ,
            final Class<GLOBAL_ARGUMENT> type )
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
    public GLOBAL_ARGUMENT evaluate(
            final HasArgumentsAndVariables<? extends RESUME_ARGUMENT>/*CoroutineOrFunctioncallOrComplexstmt<?, ?>*/ parent )
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
            throw new WrongExpressionResultValueClassException(
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
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent ,
            final Map<String, Class<?>> globalVariableTypes, final Map<String, Class<?>> localVariableTypes )
    {
        // nothing to do
    }

    @Override
    public void checkUseArguments(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent )
    {
        if ( ! parent.globalParameterTypes().containsKey( this.globalArgumentName ) )
        {
            throw new LocalVariableNotDeclaredException( this );
        }

        final Class<?> argumentType = parent.globalParameterTypes().get( this.globalArgumentName );

        if ( ! argumentType.isAssignableFrom( this.type ) )
        {
            throw new GetFunctionArgument.WrongArgumentClassException(
                    //wrongNamedExpression
                    this ,
                    //wrongClass
                    this.type ,
                    //expectedClass
                    argumentType );
        }

    }

    @Override
    public List<GetFunctionArgument<? , ? , ?>> getFunctionArgumentGetsNotInFunction()
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
    public Class<? extends GLOBAL_ARGUMENT>[] type()
    {
        //return type;
        return new Class[] { type };
    }

    @Override
    public void setExprCoroutineReturnTypeAndResumeArgumentType(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ? , ?> parent ,
            //final Class<? /*extends COROUTINE_RETURN*/> coroutineReturnType ,
            final Class<? extends COROUTINE_RETURN> coroutineReturnType ,
            final Class<? extends RESUME_ARGUMENT> resumeArgumentType )
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
