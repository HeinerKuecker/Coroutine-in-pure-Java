package de.heinerkuecker.coroutine.stmt.simple;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstmt;
import de.heinerkuecker.coroutine.HasVariableName;
import de.heinerkuecker.coroutine.exprs.GetGlobalVar;
import de.heinerkuecker.coroutine.stmt.CoroIterStmt;
import de.heinerkuecker.coroutine.stmt.CoroIterStmtResult;
import de.heinerkuecker.coroutine.stmt.simple.exc.WrongStmtVariableClassException;

public final class NegateGlobalVar<COROUTINE_RETURN , RESUME_ARGUMENT>
//extends SimpleStmt<COROUTINE_RETURN/*, CoroutineIterator<COROUTINE_RETURN>*/>
extends SimpleStmtWithoutArguments<COROUTINE_RETURN , RESUME_ARGUMENT>
implements HasVariableName
{
    /**
     * Name of variable to negate in
     * {@link CoroutineOrProcedureOrComplexstmt#globalVars()}
     */
    public final String globalVarName;

    /**
     * Constructor.
     */
    public NegateGlobalVar(
            final String globalVarName )
    {
        this.globalVarName =
                Objects.requireNonNull(
                        globalVarName );
    }

    public static <COROUTINE_RETURN , RESUME_ARGUMENT> NegateGlobalVar<COROUTINE_RETURN , RESUME_ARGUMENT> negate(
            final String globalVarName )
    {
        return new NegateGlobalVar<>(
                globalVarName );
    }

    /**
     * Set variable.
     *
     * @see SimpleStmt#execute
     */
    @Override
    public CoroIterStmtResult<COROUTINE_RETURN> execute(
            final CoroutineOrProcedureOrComplexstmt<COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        final Object varValue =
                parent.globalVars().get(
                        this ,
                        globalVarName );

        if ( varValue instanceof Boolean )
        {
            parent.globalVars().set(
                    globalVarName ,
                    ! (boolean) varValue );
        }
        else
            // null or not boolean is handled as false
        {
            parent.globalVars().set(
                    globalVarName ,
                    true );
        }
        return CoroIterStmtResult.continueCoroutine();
    }

    @Override
    public String getVariableName()
    {
        return this.globalVarName;
    }

    //@Override
    //public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    //{
    //    return Collections.emptyList();
    //}

    /**
     * @see CoroIterStmt#setResultType(Class)
     */
    @Override
    public void setResultType(
            final Class<? extends COROUTINE_RETURN> resultType )
    {
        // do nothing
    }

    @Override
    public void checkUseVariables(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstmt<?, ?> parent ,
            final Map<String, Class<?>> globalVariableTypes ,
            final Map<String, Class<?>> localVariableTypes )
    {
        if ( ! globalVariableTypes.containsKey( this.globalVarName ) )
        {
            throw new GetGlobalVar.GlobalVariableNotDeclaredException( this );
        }

        if ( ! Boolean.class.equals( globalVariableTypes.get( globalVarName ) ) )
        {
            throw new WrongStmtVariableClassException(
                    //wrongStmt
                    this ,
                    //wrongClass
                    globalVariableTypes.get( globalVarName ) ,
                    //expectedClass
                    Boolean.class );
        }
    }

    //@Override
    //public void checkUseArguments(
    //        HashSet<String> alreadyCheckedProcedureNames, final CoroutineOrProcedureOrComplexstmt<?, ?> parent )
    //{
    //    // nothing to do
    //}

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return globalVarName + " = ! " + globalVarName +
                ( this.creationStackTraceElement != null
                    ? " " + this.creationStackTraceElement
                    : "" );
    }

}
