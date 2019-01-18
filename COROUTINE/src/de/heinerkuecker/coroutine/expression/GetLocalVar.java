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
import de.heinerkuecker.coroutine.expression.exc.WrongClassException;
import de.heinerkuecker.util.ArrayTypeName;

public class GetLocalVar<T>
extends HasCreationStackTraceElement
implements CoroExpression<T> , HasVariableName
{
    public final String localVarName;

    /**
     * Reifier for type param {@link #RESULT} to solve unchecked casts.
     *
     * For type check.
     * Solve unchecked cast.
     */
    public final Class<? extends T> type;

    /**
     * Constructor.
     * @param localVarName
     */
    public GetLocalVar(
            final String localVarName ,
            final Class<? extends T> type )
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
    public T evaluate(
            final HasArgumentsAndVariables/*CoroutineOrProcedureOrComplexstep<?>*/ parent )
    {
        final Object localVarValue = parent.localVars().get( localVarName );

        if ( localVarValue != null &&
                ! type.isInstance( localVarValue ) )
        {
            //throw new ClassCastException(
            //        localVarValue.getClass().toString() +
            //        ( this.creationStackTraceElement != null
            //            ? " " + this.creationStackTraceElement
            //            : "" ) );
            throw new WrongClassException(
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
    public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    {
        return Collections.emptyList();
    }

    @Override
    public void checkUseVariables(
            final boolean isCoroutineRoot ,
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstep<?> parent ,
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
            throw new WrongVariableClassException(
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
            HashSet<String> alreadyCheckedProcedureNames, final CoroutineOrProcedureOrComplexstep<?> parent )
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
                "[" +
                //"localVarName=" +
                this.localVarName +
                ":" +
                //this.type.getName() +
                ArrayTypeName.toStr( this.type ) +
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
         * @param stepOrExpression step or expression with access to not declared local variable
         */
        public <T extends HasCreationStackTraceElement & HasVariableName> LocalVariableNotDeclaredException(
                final T stepOrExpression )
        {
            super(
                    "local variable not declared: " +
                    stepOrExpression );
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
        private static final long serialVersionUID = 4050350736374761539L;

        /**
         * Constructor.
         */
        public WrongVariableClassException(
                final HasVariableName wrongExpression ,
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
