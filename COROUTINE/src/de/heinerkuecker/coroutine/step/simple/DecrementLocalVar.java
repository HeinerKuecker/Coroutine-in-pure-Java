package de.heinerkuecker.coroutine.step.simple;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep;
import de.heinerkuecker.coroutine.HasVariableName;
import de.heinerkuecker.coroutine.expression.GetLocalVar;
import de.heinerkuecker.coroutine.step.CoroIterStep;
import de.heinerkuecker.coroutine.step.CoroIterStepResult;
import de.heinerkuecker.coroutine.step.simple.exc.WrongStmtVariableClassException;

/**
 * Step {@link CoroIterStep} to
 * decrement an {@link Number}
 * variable in variables
 * {@link CoroutineOrProcedureOrComplexstep#localVars()}
 *
 * @param <RESULT> result type of method {@link CoroutineIterator#next()}
 * @author Heiner K&uuml;cker
 */
public final class DecrementLocalVar<RESULT>
//extends SimpleStep<RESULT/*, CoroutineIterator<RESULT>*/>
extends SimpleStepWithoutArguments<RESULT>
implements HasVariableName
{
    /**
     * Name of variable to decrement in
     * {@link CoroutineOrProcedureOrComplexstep#localVars()}
     */
    public final String localVarName;

    /**
     * Constructor.
     *
     * @param variable name
     */
    public DecrementLocalVar(
            final String localVarName )
    {
        this.localVarName =
                Objects.requireNonNull(
                        localVarName );
    }

    /**
     * Decrement {@link Number} variable.
     */
    @Override
    public CoroIterStepResult<RESULT> execute(
            final CoroutineOrProcedureOrComplexstep<RESULT> parent )
    {
        // TODO byte, short, char, long, float, double, BigInteger, BigDecimal
        final int var =
                (int) parent.localVars().get(
                        this ,
                        localVarName );

        parent.localVars().set(
                localVarName ,
                var - 1 );

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
            final Class<? extends RESULT> resultType )
    {
        // do nothing
    }

    @Override
    public void checkUseVariables(
            //final boolean isCoroutineRoot ,
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstep<?> parent ,
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
    //        HashSet<String> alreadyCheckedProcedureNames, final CoroutineOrProcedureOrComplexstep<?> parent )
    //{
    //    // nothing to do
    //}

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return localVarName + "--" +
                ( this.creationStackTraceElement != null
                    ? " " + this.creationStackTraceElement
                    : "" );
    }

}
