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

public final class NegateGlobalVar<RESULT>
extends SimpleStep<RESULT/*, CoroutineIterator<RESULT>*/>
implements HasVariableName
{
    /**
     * Name of variable to negate in
     * {@link CoroutineOrProcedureOrComplexstep#globalVars()}
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

    public static <RESULT> NegateGlobalVar<RESULT> negate(
            final String globalVarName )
    {
        return new NegateGlobalVar<>(
                globalVarName );
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
        return CoroIterStepResult.continueCoroutine();
    }

    @Override
    public String getVariableName()
    {
        return this.globalVarName;
    }

    /**
     * @see CoroIterStep#getProcedureArgumentsNotInProcedure()
     */
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

        if ( ! Boolean.class.equals( globalVariableTypes.get( globalVarName ) ) )
        {
            throw new WrongVariableClassException(
                    //wrongStep
                    this ,
                    //wrongClass
                    globalVariableTypes.get( globalVarName ) ,
                    //expectedClass
                    Boolean.class );
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
        return globalVarName + " = ! " + globalVarName +
                ( this.creationStackTraceElement != null
                    ? " " + this.creationStackTraceElement
                    : "" );
    }

}
