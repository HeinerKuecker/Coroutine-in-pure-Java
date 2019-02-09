package de.heinerkuecker.coroutine.stmt.simple;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineOrFunctioncallOrComplexstmt;
import de.heinerkuecker.coroutine.HasVariableName;
import de.heinerkuecker.coroutine.exprs.GetLocalVar;
import de.heinerkuecker.coroutine.stmt.CoroStmtResult;
import de.heinerkuecker.coroutine.stmt.simple.exc.WrongStmtVariableClassException;

public final class NegateLocalVar<FUNCTION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT>
//extends SimpleStmt<COROUTINE_RETURN/*, CoroutineIterator<COROUTINE_RETURN>*/>
extends SimpleStmtWithoutArguments<FUNCTION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT>
implements HasVariableName
{
    /**
     * Name of variable to negate in
     * {@link CoroutineOrFunctioncallOrComplexstmt#localVars()}
     */
    public final String localVarName;

    /**
     * Constructor.
     */
    public NegateLocalVar(
            final String localVarName )
    {
        this.localVarName =
                Objects.requireNonNull(
                        localVarName );
    }

    public static <FUNCTION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT> NegateLocalVar<FUNCTION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT> negate(
            final String varName )
    {
        return new NegateLocalVar<>(
                varName );
    }

    /**
     * Set variable.
     *
     * @see SimpleStmt#execute
     */
    @Override
    public CoroStmtResult<FUNCTION_RETURN , COROUTINE_RETURN> execute(
            final CoroutineOrFunctioncallOrComplexstmt<FUNCTION_RETURN , COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        final Object varValue =
                parent.localVars().get(
                        this ,
                        localVarName );

        if ( varValue instanceof Boolean )
        {
            parent.localVars().set(
                    localVarName ,
                    ! (boolean) varValue );
        }
        else
            // null or not boolean is handled as false
        {
            parent.localVars().set(
                    localVarName ,
                    true );
        }
        return CoroStmtResult.continueCoroutine();
    }

    //@Override
    //public List<GetFunctionArgument<? , ?>> getFunctionArgumentGetsNotInFunction()
    //{
    //    return Collections.emptyList();
    //}

    @Override
    public void setStmtCoroutineReturnType(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ? , ?> parent ,
            final Class<? extends COROUTINE_RETURN> coroutineReturnType )
    {
        // do nothing
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
            throw new GetLocalVar.LocalVariableNotDeclaredException( this );
        }

        if ( ! Boolean.class.equals( localVariableTypes.get( localVarName ) ) )
        {
            throw new WrongStmtVariableClassException(
                    //wrongStmt
                    this ,
                    //wrongClass
                    localVariableTypes.get( localVarName ) ,
                    //expectedClass
                    Boolean.class );
        }
    }

    @Override
    public String getVariableName()
    {
        return this.localVarName;
    }

    //@Override
    //public void checkUseArguments(
    //        HashSet<String> alreadyCheckedFunctionNames, final CoroutineOrFunctioncallOrComplexstmt<?, ?> parent )
    //{
    //    // nothing to do
    //}

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return localVarName + " = ! " + localVarName +
                ( this.creationStackTraceElement != null
                    ? " " + this.creationStackTraceElement
                    : "" );
    }

}
