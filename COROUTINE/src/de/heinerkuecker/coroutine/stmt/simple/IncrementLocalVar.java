package de.heinerkuecker.coroutine.stmt.simple;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineOrFunctioncallOrComplexstmt;
import de.heinerkuecker.coroutine.HasVariableName;
import de.heinerkuecker.coroutine.exprs.GetLocalVar;
import de.heinerkuecker.coroutine.stmt.CoroStmt;
import de.heinerkuecker.coroutine.stmt.CoroStmtResult;
import de.heinerkuecker.coroutine.stmt.simple.exc.WrongStmtVariableClassException;

/**
 * Stmt {@link CoroStmt} to
 * increment an {@link Number}
 * variable in variables
 * {@link CoroutineOrFunctioncallOrComplexstmt#localVars()}
 *
 * @param <COROUTINE_RETURN>
 * @author Heiner K&uuml;cker
 */
public final class IncrementLocalVar<FUNCTION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT>
//extends SimpleStmt<COROUTINE_RETURN/*, CoroutineIterator<COROUTINE_RETURN>*/>
extends SimpleStmtWithoutArguments<FUNCTION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT>
implements HasVariableName
{
    /**
     * Name of variable to increment in
     * {@link CoroutineOrFunctioncallOrComplexstmt#localVars()}
     */
    public final String localVarName;

    /**
     * Constructor.
     *
     * @param variable name
     */
    public IncrementLocalVar(
            final String localVarName )
    {
        this.localVarName =
                Objects.requireNonNull(
                        localVarName );
    }

    /**
     * Increment variable.
     *
     * @see SimpleStmt#execute
     */
    @Override
    public CoroStmtResult<FUNCTION_RETURN , COROUTINE_RETURN> execute(
            final CoroutineOrFunctioncallOrComplexstmt<FUNCTION_RETURN , COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        // TODO byte, short, char, long, float, double, BigInteger, BigDecimal
        final int var =
                (int) parent.localVars().get(
                        this ,
                        localVarName );

        parent.localVars().set(
                localVarName ,
                var + 1 );

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

        if ( ! Integer.class.equals( localVariableTypes.get( localVarName ) ) )
        {
            throw new WrongStmtVariableClassException(
                    //wrongStmt
                    this ,
                    //wrongClass
                    localVariableTypes.get( localVarName ) ,
                    //expectedClass
                    Integer.class );
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
        return localVarName + "++" +
                ( this.creationStackTraceElement != null
                    ? " " + this.creationStackTraceElement
                    : "" );
    }

}
