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

/**
 * Stmt {@link CoroIterStmt} to
 * increment an {@link Number}
 * variable in globalVariables
 * {@link CoroutineOrProcedureOrComplexstmt#globalVars()}
 *
 * @param <COROUTINE_RETURN>
 * @author Heiner K&uuml;cker
 */
public final class IncrementGlobalVar<COROUTINE_RETURN , RESUME_ARGUMENT>
//extends SimpleStmt<COROUTINE_RETURN/*, CoroutineIterator<COROUTINE_RETURN>*/>
extends SimpleStmtWithoutArguments<COROUTINE_RETURN , RESUME_ARGUMENT>
implements HasVariableName
{
    /**
     * Name of variable to increment in
     * {@link CoroutineOrProcedureOrComplexstmt#globalVars()}
     */
    public final String globalVarName;

    /**
     * Constructor.
     *
     * @param variable name
     */
    public IncrementGlobalVar(
            final String globalVarName )
    {
        this.globalVarName =
                Objects.requireNonNull(
                        globalVarName );
    }

    /**
     * Increment variable.
     *
     * @see SimpleStmt#execute
     */
    @Override
    public CoroIterStmtResult<COROUTINE_RETURN> execute(
            final CoroutineOrProcedureOrComplexstmt<COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        // TODO byte, short, char, long, float, double, BigInteger, BigDecimal
        final int var =
                (int) parent.globalVars().get(
                        this ,
                        globalVarName );

        parent.globalVars().set(
                globalVarName ,
                var + 1 );

        return CoroIterStmtResult.continueCoroutine();
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

        if ( ! Integer.class.equals( globalVariableTypes.get( globalVarName ) ) )
        {
            throw new WrongStmtVariableClassException(
                    //wrongStmt
                    this ,
                    //wrongClass
                    globalVariableTypes.get( globalVarName ) ,
                    //expectedClass
                    Integer.class );
        }
    }

    @Override
    public String getVariableName()
    {
        return this.globalVarName;
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
        return globalVarName + "++" +
                ( this.creationStackTraceElement != null
                    ? " " + this.creationStackTraceElement
                    : "" );
    }

}
