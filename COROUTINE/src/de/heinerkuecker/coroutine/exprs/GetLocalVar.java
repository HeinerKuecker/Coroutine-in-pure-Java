package de.heinerkuecker.coroutine.exprs;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineDebugSwitches;
import de.heinerkuecker.coroutine.CoroutineOrFunctioncallOrComplexstmt;
import de.heinerkuecker.coroutine.HasArgumentsAndVariables;
import de.heinerkuecker.coroutine.HasCreationStackTraceElement;
import de.heinerkuecker.coroutine.HasVariableName;
import de.heinerkuecker.coroutine.exprs.exc.WrongExpressionResultValueClassException;
import de.heinerkuecker.coroutine.exprs.exc.WrongExpressionVariableClassException;
import de.heinerkuecker.util.ArrayTypeName;

public class GetLocalVar<VARIABLE , COROUTINE_RETURN>
extends HasCreationStackTraceElement
implements SimpleExpression<VARIABLE , COROUTINE_RETURN> , HasVariableName
{
    public final String localVarName;

    /**
     * Reifier for type param VARIABLE to solve unchecked casts.
     *
     * For type check.
     * Solve unchecked cast.
     */
    public final Class<? extends VARIABLE> type;

    /**
     * Constructor.
     * @param localVarName
     */
    public GetLocalVar(
            final String localVarName ,
            final Class<? extends VARIABLE> type )
    {
        super(
                //creationStackOffset
                2 );

        this.localVarName =
                Objects.requireNonNull(
                        localVarName );

        this.type =
                Objects.requireNonNull(
                        type );
    }

    @Override
    public VARIABLE evaluate(
            final HasArgumentsAndVariables<?>/*CoroutineOrFunctioncallOrComplexstmt<?, ?>*/ parent )
    {
        if ( CoroutineDebugSwitches.logSimpleStatementsAndExpressions )
        {
            System.out.println( "evaluate " + this );
        }

        final Object localVarValue =
                parent.localVars().get(
                        this ,
                        localVarName );

        if ( localVarValue != null &&
                ! type.isInstance( localVarValue ) )
        {
            //throw new ClassCastException(
            //        localVarValue.getClass().toString() +
            //        ( this.creationStackTraceElement != null
            //            ? " " + this.creationStackTraceElement
            //            : "" ) );
            throw new WrongExpressionResultValueClassException(
                    //valueExpression
                    this ,
                    //expectedClass
                    type ,
                    //wrongValue
                    localVarValue );
        }

        return type.cast( localVarValue );
    }

    @Override
    public List<GetFunctionArgument<? , ?>> getFunctionArgumentGetsNotInFunction()
    {
        return Collections.emptyList();
    }

    @Override
    public void checkUseVariables(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent ,
            final Map<String, Class<?>> globalVariableTypes ,
            final Map<String, Class<?>> localVariableTypes )
    {
        if ( ! localVariableTypes.containsKey( this.localVarName ) )
        {
            throw new LocalVariableNotDeclaredException( this );
        }

        final Class<?> variableType = localVariableTypes.get( this.localVarName );

        if ( ! variableType.isAssignableFrom( this.type ) )
        {
            throw new WrongExpressionVariableClassException(
                    //wrongNamedExpression
                    this ,
                    //wrongClass
                    this.type ,
                    //expectedClass
                    variableType );
        }
    }

    @Override
    public String getVariableName()
    {
        return this.localVarName;
    }

    @Override
    public void checkUseArguments(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent )
    {
        // nothing to do
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<? extends VARIABLE>[] type()
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
                //"localVarName=" +
                this.localVarName +
                //"]"
                ( this.creationStackTraceElement != null
                    ? " " + this.creationStackTraceElement
                    : "" );
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
         * @param stmtOrExpression statement or expression with access to not declared local variable
         */
        public <T extends HasCreationStackTraceElement & HasVariableName> LocalVariableNotDeclaredException(
                final T stmtOrExpression )
        {
            super(
                    "local variable not declared: " +
                    stmtOrExpression );
        }

    }

}
