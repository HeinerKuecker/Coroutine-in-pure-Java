package de.heinerkuecker.coroutine.expression;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep;
import de.heinerkuecker.coroutine.HasArgumentsAndVariables;
import de.heinerkuecker.coroutine.HasCreationStackTraceElement;
import de.heinerkuecker.coroutine.HasVariableName;
import de.heinerkuecker.coroutine.expression.exc.WrongExpressionClassException;
import de.heinerkuecker.coroutine.expression.exc.WrongExpressionVariableClassException;
import de.heinerkuecker.util.ArrayTypeName;

public class GetGlobalVar<T>
extends HasCreationStackTraceElement
implements CoroExpression<T> , HasVariableName
{
    public final String globalVarName;

    /**
     * For type check.
     *
     * Solve unchecked cast.
     */
    public final Class<T> type;

    /**
     * Constructor.
     * @param globalVarName
     */
    public GetGlobalVar(
            final String globalVarName ,
            final Class<T> type )
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
    public T evaluate(
            final HasArgumentsAndVariables/*CoroutineOrProcedureOrComplexstep<?>*/ parent )
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
            throw new WrongExpressionClassException(
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
    public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    {
        return Collections.emptyList();
    }

    @Override
    public void checkUseVariables(
            //final boolean isCoroutineRoot ,
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstep<?> parent ,
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
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstep<?> parent )
    {
        // nothing to do
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
         * @param stepOrExpression step or expression with access to not declared global variable
         */
        public <T extends HasCreationStackTraceElement & HasVariableName> GlobalVariableNotDeclaredException(
                final T stepOrExpression )
        {
            super(
                    "global variable not declared: " +
                    stepOrExpression );
        }

    }

}
