package de.heinerkuecker.coroutine.step.simple;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep;
import de.heinerkuecker.coroutine.HasArgumentsAndVariables;
import de.heinerkuecker.coroutine.HasVariableName;
import de.heinerkuecker.coroutine.expression.CoroExpression;
import de.heinerkuecker.coroutine.expression.GetLocalVar;
import de.heinerkuecker.coroutine.expression.GetProcedureArgument;
import de.heinerkuecker.coroutine.expression.Value;
import de.heinerkuecker.coroutine.step.CoroIterStep;
import de.heinerkuecker.coroutine.step.CoroIterStepResult;

/**
 * {@link SimpleStep} to set
 * local variable with specified
 * value or result of specified
 * expression.
 *
 * @author Heiner K&uuml;cker
 * @param <RESULT> result type of coroutine, here unused
 */
public /*final*/ class SetLocalVar<RESULT, RESUME_ARGUMENT , VARIABLE>
extends SimpleStep<RESULT/*, CoroutineIterator<RESULT>*/ , RESUME_ARGUMENT>
implements CoroExpression<VARIABLE> , HasVariableName
{
    /**
     * Name of variable to set in
     * {@link CoroutineOrProcedureOrComplexstep#localVars()}
     */
    public final String localVarName;

    /**
     * This is the expression whose result
     * should be set as the value of the variable.
     */
    public final CoroExpression<VARIABLE> varValueExpression;

    /**
     * Constructor.
     */
    public SetLocalVar(
            final String localVarName ,
            final CoroExpression<VARIABLE> varValueExpression )
    {
        this.localVarName =
                Objects.requireNonNull(
                        localVarName );

        this.varValueExpression =
                Objects.requireNonNull(
                        varValueExpression );
    }

    /**
     * Convenience constructor.
     */
    public SetLocalVar(
            final String localVarName ,
            final VARIABLE varValue )
    {
        this.localVarName =
                Objects.requireNonNull(
                        localVarName );

        this.varValueExpression =
                new Value<VARIABLE>(
                        (Class<? extends VARIABLE>) varValue.getClass() ,
                        varValue );
    }

    /**
     * Set variable.
     *
     * @see SimpleStep#execute
     */
    @Override
    public CoroIterStepResult<RESULT> execute(
            final CoroutineOrProcedureOrComplexstep<RESULT, RESUME_ARGUMENT> parent )
    {
        final Object varValue = varValueExpression.evaluate( parent );

        parent.localVars().set(
                localVarName ,
                varValue );

        return CoroIterStepResult.continueCoroutine();
    }

    @Override
    public VARIABLE evaluate(
            final HasArgumentsAndVariables parent )
    // for using in expressions
    {
        execute( (CoroutineOrProcedureOrComplexstep<RESULT, RESUME_ARGUMENT>) parent );

        return (VARIABLE) parent.localVars().get(
                this ,
                localVarName );
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<? extends VARIABLE>[] type()
    {
        //return new Class[] { type };
        return varValueExpression.type();
    }

    /**
     * @see CoroIterStep#getProcedureArgumentGetsNotInProcedure()
     */
    @Override
    public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    {
        return this.varValueExpression.getProcedureArgumentGetsNotInProcedure();
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
            //final boolean isCoroutineRoot ,
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstep<?, ?> parent ,
            final Map<String, Class<?>> globalVariableTypes ,
            final Map<String, Class<?>> localVariableTypes )
    {
        if ( ! localVariableTypes.containsKey( this.localVarName ) )
        {
            throw new GetLocalVar.LocalVariableNotDeclaredException( this );
        }

        this.varValueExpression.checkUseVariables(
                //isCoroutineRoot ,
                alreadyCheckedProcedureNames ,
                parent ,
                globalVariableTypes, localVariableTypes );
    }

    @Override
    public void checkUseArguments(
            HashSet<String> alreadyCheckedProcedureNames, final CoroutineOrProcedureOrComplexstep<?, ?> parent )
    {
        this.varValueExpression.checkUseArguments( alreadyCheckedProcedureNames, parent );
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
        return
                localVarName +
                " = " +
                varValueExpression +
                ( this.creationStackTraceElement != null
                    ? " " + this.creationStackTraceElement
                    : "" );
    }

}
