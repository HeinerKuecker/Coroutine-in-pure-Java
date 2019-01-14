package de.heinerkuecker.coroutine.step.simple;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.HasVariableName;
import de.heinerkuecker.coroutine.expression.GetLocalVar;
import de.heinerkuecker.coroutine.expression.GetProcedureArgument;
import de.heinerkuecker.coroutine.step.CoroIterStep;
import de.heinerkuecker.coroutine.step.CoroIterStepResult;

/**
 * Step {@link CoroIterStep} to
 * decrement an {@link Number}
 * variable in variables
 * {@link CoroIteratorOrProcedure#localVars()}
 *
 * @param <RESULT> result type of method {@link CoroutineIterator#next()}
 * @author Heiner K&uuml;cker
 */
public final class DecrementLocalVar<RESULT>
extends SimpleStep<RESULT/*, CoroutineIterator<RESULT>*/>
implements HasVariableName
{
    /**
     * Name of variable to decrement in
     * {@link CoroIteratorOrProcedure#localVars()}
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
     * Decrement {@link Number}variable.
     *
     * @see CoroIterStep#execute(Object)
     */
    @Override
    public CoroIterStepResult<RESULT> execute(
            final CoroIteratorOrProcedure<RESULT> parent )
    {
        // TODO byte, short, char, long, float, double, BigInteger, BigDecimal
        final int var = (int) parent.localVars().get( localVarName );
        parent.localVars().set(
                localVarName ,
                var - 1 );
        return CoroIterStepResult.continueCoroutine();
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
    public void checkUseUndeclaredVariables(
            final CoroIteratorOrProcedure<?> parent ,
            final Map<String, Class<?>> globalVariableTypes ,
            final Map<String, Class<?>> localVariableTypes )
    {
        if ( ! localVariableTypes.containsKey( this.localVarName ) )
        {
            throw new GetLocalVar.LocalVariableNotDeclaredException( this );
        }
    }

    @Override
    public String getVariableName()
    {
        return this.localVarName;
    }

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
