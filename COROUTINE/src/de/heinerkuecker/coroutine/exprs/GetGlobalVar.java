package de.heinerkuecker.coroutine.exprs;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineOrFunctioncallOrComplexstmt;
import de.heinerkuecker.coroutine.HasArgumentsAndVariables;
import de.heinerkuecker.coroutine.HasCreationStackTraceElement;
import de.heinerkuecker.coroutine.HasVariableName;
import de.heinerkuecker.coroutine.exprs.exc.WrongExpressionResultValueClassException;
import de.heinerkuecker.coroutine.exprs.exc.WrongExpressionVariableClassException;
import de.heinerkuecker.util.ArrayTypeName;

public class GetGlobalVar<GLOBAL_VAR , COROUTINE_RETURN>
extends HasCreationStackTraceElement
implements SimpleExpression<GLOBAL_VAR , COROUTINE_RETURN> , HasVariableName
{
    public final String globalVarName;

    /**
     * For type check.
     *
     * Solve unchecked cast.
     */
    public final Class<GLOBAL_VAR> type;

    /**
     * Constructor.
     * @param globalVarName
     */
    public GetGlobalVar(
            final String globalVarName ,
            final Class<GLOBAL_VAR> type )
    {
        super(
                //creationStackOffset
                2 );

        this.globalVarName =
                Objects.requireNonNull(
                        globalVarName );

        this.type =
                Objects.requireNonNull(
                        type );
    }

    @Override
    public GLOBAL_VAR evaluate(
            final HasArgumentsAndVariables<?>/*CoroutineOrFunctioncallOrComplexstmt<?, ?>*/ parent )
    {
        final Object globalVarValue =
                parent.globalVars().get(
                        this ,
                        globalVarName );

        if ( globalVarValue != null &&
                ! type.isInstance( globalVarValue ) )
        {
            //throw new ClassCastException(
            //        globalVarValue.getClass().toString() +
            //        ( this.creationStackTraceElement != null
            //            ? " " + this.creationStackTraceElement
            //            : "" ) );
            throw new WrongExpressionResultValueClassException(
                    //valueExpression
                    this ,
                    //expectedClass
                    type ,
                    //wrongValue
                    globalVarValue );
        }

        return type.cast( globalVarValue );
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
        if ( ! globalVariableTypes.containsKey( this.globalVarName ) )
        {
            throw new GlobalVariableNotDeclaredException( this );
        }

        final Class<?> variableType = globalVariableTypes.get( this.globalVarName );

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
        return this.globalVarName;
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
    public Class<? extends GLOBAL_VAR>[] type()
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
                //"globalVarName=" +
                this.globalVarName +
                //"]"
                ( this.creationStackTraceElement != null
                    ? " " + this.creationStackTraceElement
                    : "" );
    }

    /**
     * Exception
     */
    public static class GlobalVariableNotDeclaredException
    extends RuntimeException
    {
        /**
         * Generated by Eclipse.
         */
        private static final long serialVersionUID = 3720688760091360980L;

        /**
         * Constructor.
         *
         * @param stmtOrExpression statement or expression with access to not declared global variable
         */
        public <T extends HasCreationStackTraceElement & HasVariableName> GlobalVariableNotDeclaredException(
                final T stmtOrExpression )
        {
            super(
                    "global variable not declared: " +
                    stmtOrExpression );
        }

    }

}
