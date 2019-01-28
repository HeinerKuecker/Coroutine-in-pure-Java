package de.heinerkuecker.coroutine.step.simple;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep;
import de.heinerkuecker.coroutine.HasVariableName;
import de.heinerkuecker.coroutine.expression.GetGlobalVar;
import de.heinerkuecker.coroutine.step.CoroIterStep;
import de.heinerkuecker.coroutine.step.CoroIterStepResult;
import de.heinerkuecker.coroutine.step.simple.exc.WrongStmtVariableClassException;

/**
 * Step {@link CoroIterStep} to
 * decrement an {@link Number}
 * variable in globalVariables
 * {@link CoroutineOrProcedureOrComplexstep#globalVars()}
 *
 * @param <COROUTINE_RETURN>
 * @author Heiner K&uuml;cker
 */
public final class DecrementGlobalVar<COROUTINE_RETURN , RESUME_ARGUMENT>
//extends SimpleStep<COROUTINE_RETURN/*, CoroutineIterator<COROUTINE_RETURN>*/>
extends SimpleStepWithoutArguments<COROUTINE_RETURN , RESUME_ARGUMENT>
implements HasVariableName
{
    /**
     * Name of variable to decrement in
     * {@link CoroutineOrProcedureOrComplexstep#globalVars()}
     */
    public final String globalVarName;

    /**
     * Constructor.
     *
     * @param variable name
     */
    public DecrementGlobalVar(
            final String globalVarName )
    {
        this.globalVarName =
                Objects.requireNonNull(
                        globalVarName );
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
                (int) parent.globalVars().get(
                        this ,
                        globalVarName );

        parent.globalVars().set(
                globalVarName ,
                var - 1 );

        return CoroIterStepResult.continueCoroutine();
    }

    @Override
    public String getVariableName()
    {
        return this.globalVarName;
    }

    //@Override
    //public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    //{
    //    // nothing to do
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
            ////final boolean isCoroutineRoot ,
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstep<?, ?> parent ,
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
                    //wrongStep
                    this ,
                    //wrongClass
                    globalVariableTypes.get( globalVarName ) ,
                    //expectedClass
                    Integer.class );
        }
    }

    //@Override
    //public void checkUseArguments(
    //        final HashSet<String> alreadyCheckedProcedureNames ,
    //        final CoroutineOrProcedureOrComplexstep<?, ?> parent )
    //{
    //    // nothing to do
    //}

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return globalVarName + "--" +
                ( this.creationStackTraceElement != null
                    ? " " + this.creationStackTraceElement
                    : "" );
    }

}
