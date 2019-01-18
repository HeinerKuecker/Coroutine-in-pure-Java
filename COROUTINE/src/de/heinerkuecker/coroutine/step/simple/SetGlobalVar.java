package de.heinerkuecker.coroutine.step.simple;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep;
import de.heinerkuecker.coroutine.HasVariableName;
import de.heinerkuecker.coroutine.expression.CoroExpression;
import de.heinerkuecker.coroutine.expression.GetGlobalVar;
import de.heinerkuecker.coroutine.expression.GetProcedureArgument;
import de.heinerkuecker.coroutine.expression.Value;
import de.heinerkuecker.coroutine.step.CoroIterStep;
import de.heinerkuecker.coroutine.step.CoroIterStepResult;


/**
 * {@link SimpleStep} to set
 * global variable with specified
 * value or result of specified
 * expression.
 *
 * @author Heiner K&uuml;cker
 * @param <RESULT> result type of coroutine, here unused
 */
public final class SetGlobalVar<RESULT>
extends SimpleStep<RESULT/*, CoroutineIterator<RESULT>*/>
implements HasVariableName
{
    /**
     * Name of variable to set in
     * {@link CoroutineOrProcedureOrComplexstep#globalVars()}
     */
    public final String globalVarName;

    /**
     * This is the expression whose result
     * should be set as the value of the variable.
     */
    public final CoroExpression<?> varValueExpression;

    /**
     * Constructor.
     */
    public SetGlobalVar(
            final String globalVarName ,
            final CoroExpression<?> varValueExpression )
    {
        this.globalVarName =
                Objects.requireNonNull(
                        globalVarName );

        this.varValueExpression =
                Objects.requireNonNull(
                        varValueExpression );
    }

    /**
     * Convenience constructor.
     */
    public SetGlobalVar(
            final String globalVarName ,
            final Object varValue )
    {
        this.globalVarName =
                Objects.requireNonNull(
                        globalVarName );

        this.varValueExpression =
                new Value<>(
                        varValue.getClass() ,
                        varValue );
    }

    /**
     * Set variable.
     *
     * @see SimpleStep#execute
     */
    @Override
    public CoroIterStepResult<RESULT> execute(
            final CoroutineOrProcedureOrComplexstep<RESULT> parent )
    {
        final Object varValue = varValueExpression.evaluate( parent );

        parent.globalVars().set(
                globalVarName ,
                varValue );

        return CoroIterStepResult.continueCoroutine();
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

        this.varValueExpression.checkUseVariables(
                isCoroutineRoot ,
                alreadyCheckedProcedureNames ,
                parent ,
                globalVariableTypes, localVariableTypes );
    }

    @Override
    public void checkUseArguments(
            HashSet<String> alreadyCheckedProcedureNames, final CoroutineOrProcedureOrComplexstep<?> parent )
    {
        this.varValueExpression.checkUseArguments( alreadyCheckedProcedureNames, parent );
    }

    @Override
    public String getVariableName()
    {
        return this.globalVarName;
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return globalVarName + " = " + varValueExpression +
                ( this.creationStackTraceElement != null
                    ? " " + this.creationStackTraceElement
                    : "" );
    }

}
