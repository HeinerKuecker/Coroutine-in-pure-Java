package de.heinerkuecker.coroutine.step.simple;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep;
import de.heinerkuecker.coroutine.HasVariableName;
import de.heinerkuecker.coroutine.expression.GetLocalVar;
import de.heinerkuecker.coroutine.step.CoroIterStep;
import de.heinerkuecker.coroutine.step.CoroIterStepResult;

public final class ResetLocalVar<RESULT>
//extends SimpleStep<RESULT/*, CoroutineIterator<RESULT>*/>
extends SimpleStepWithoutExpression<RESULT>
implements HasVariableName
{
    /**
     * Name of variable to reset in
     * {@link CoroutineOrProcedureOrComplexstep#localVars()}
     */
    public final String localVarName;

    /**
     * Constructor.
     */
    public ResetLocalVar(
            final String localVarName )
    {
        this.localVarName =
                Objects.requireNonNull(
                        localVarName );
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
        //parent.localVars().remove( varName );
        parent.localVars().set( localVarName , null );
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
            final Map<String, Class<?>> globalVariableTypes, final Map<String, Class<?>> localVariableTypes )
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
        return localVarName + " = null" +
                ( this.creationStackTraceElement != null
                    ? " " + this.creationStackTraceElement
                    : "" );
    }

}
