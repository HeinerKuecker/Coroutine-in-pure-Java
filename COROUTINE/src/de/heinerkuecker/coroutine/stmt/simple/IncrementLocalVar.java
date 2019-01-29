package de.heinerkuecker.coroutine.stmt.simple;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep;
import de.heinerkuecker.coroutine.HasVariableName;
import de.heinerkuecker.coroutine.exprs.GetLocalVar;
import de.heinerkuecker.coroutine.stmt.CoroIterStep;
import de.heinerkuecker.coroutine.stmt.CoroIterStepResult;
import de.heinerkuecker.coroutine.stmt.simple.exc.WrongStmtVariableClassException;

/**
 * Step {@link CoroIterStep} to
 * increment an {@link Number}
 * variable in variables
 * {@link CoroutineOrProcedureOrComplexstep#localVars()}
 *
 * @param <COROUTINE_RETURN>
 * @author Heiner K&uuml;cker
 */
public final class IncrementLocalVar<COROUTINE_RETURN , RESUME_ARGUMENT>
//extends SimpleStep<COROUTINE_RETURN/*, CoroutineIterator<COROUTINE_RETURN>*/>
extends SimpleStepWithoutArguments<COROUTINE_RETURN , RESUME_ARGUMENT>
implements HasVariableName
{
    /**
     * Name of variable to increment in
     * {@link CoroutineOrProcedureOrComplexstep#localVars()}
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
     * @see SimpleStep#execute
     */
    @Override
    public CoroIterStepResult<COROUTINE_RETURN> execute(
            final CoroutineOrProcedureOrComplexstep<COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        // TODO byte, short, char, long, float, double, BigInteger, BigDecimal
        final int var =
                (int) parent.localVars().get(
                        this ,
                        localVarName );

        parent.localVars().set(
                localVarName ,
                var + 1 );

        return CoroIterStepResult.continueCoroutine();
    }

    //@Override
    //public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    //{
    //    return Collections.emptyList();
    //}

    /**
     * @see CoroIterStep#setResultType(Class)
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
            final CoroutineOrProcedureOrComplexstep<?, ?> parent ,
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
                    //wrongStep
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
    //        HashSet<String> alreadyCheckedProcedureNames, final CoroutineOrProcedureOrComplexstep<?, ?> parent )
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
