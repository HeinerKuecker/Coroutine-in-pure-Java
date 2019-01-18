package de.heinerkuecker.coroutine.step.simple;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep;
import de.heinerkuecker.coroutine.HasVariableName;
import de.heinerkuecker.coroutine.expression.GetGlobalVar;
import de.heinerkuecker.coroutine.expression.GetProcedureArgument;
import de.heinerkuecker.coroutine.step.CoroIterStep;
import de.heinerkuecker.coroutine.step.CoroIterStepResult;
import de.heinerkuecker.coroutine.step.simple.NegateLocalVar.WrongVariableClassException;

/**
 * Step {@link CoroIterStep} to
 * decrement an {@link Number}
 * variable in variables
 * {@link CoroutineOrProcedureOrComplexstep#globalVars()}
 *
 * @param <RESULT>
 * @author Heiner K&uuml;cker
 */
public final class DecrementGlobalVar<RESULT>
extends SimpleStep<RESULT/*, CoroutineIterator<RESULT>*/>
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
    public CoroIterStepResult<RESULT> execute(
            final CoroutineOrProcedureOrComplexstep<RESULT> parent )
    {
        // TODO byte, short, char, long, float, double, BigInteger, BigDecimal
        final int var = (int) parent.globalVars().get( globalVarName );

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

    @Override
    public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    {
        return Collections.emptyList();
    }

    /**
     * @see CoroIterStep#setResultType(Class)
     */
    @Override
    public void setResultType(
            final Class<? extends RESULT> resultType )
    {
        // do nothing
    }

    @Override
    public void checkUseVariables(
            final boolean isCoroutineRoot ,
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstep<?> parent ,
            final Map<String, Class<?>> globalVariableTypes ,
            final Map<String, Class<?>> localVariableTypes )
    {
        if ( ! globalVariableTypes.containsKey( this.globalVarName ) )
        {
            throw new GetGlobalVar.GlobalVariableNotDeclaredException( this );
        }

        if ( ! Integer.class.equals( globalVariableTypes.get( globalVarName ) ) )
        {
            throw new WrongVariableClassException(
                    //wrongStep
                    this ,
                    //wrongClass
                    globalVariableTypes.get( globalVarName ) ,
                    //expectedClass
                    Integer.class );
        }
    }

    @Override
    public void checkUseArguments(
            HashSet<String> alreadyCheckedProcedureNames, final CoroutineOrProcedureOrComplexstep<?> parent )
    {
        // nothing to do
    }

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
