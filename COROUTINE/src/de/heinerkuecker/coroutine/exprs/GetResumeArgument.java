package de.heinerkuecker.coroutine.exprs;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import de.heinerkuecker.coroutine.CoroutineOrFunctioncallOrComplexstmt;
import de.heinerkuecker.coroutine.HasArgumentsAndVariables;
import de.heinerkuecker.coroutine.HasCreationStackTraceElement;
import de.heinerkuecker.coroutine.exprs.exc.WrongExpressionResultValueClassException;

public class GetResumeArgument<RESUME_ARGUMENT , COROUTINE_RETURN>
extends HasCreationStackTraceElement
implements SimpleExpression</*EXPRESSION_RETURN*/RESUME_ARGUMENT , COROUTINE_RETURN , RESUME_ARGUMENT>
//extends AbstrHasSrcPosNoVarsNoArgsExpression<RESUME_ARGUMENT , COROUTINE_RETURN , RESUME_ARGUMENT>
{
    /**
     * For type check.
     *
     * Solve unchecked cast.
     */
    public Class<? extends RESUME_ARGUMENT> resumeArgumentType;

    /**
     * Constructor.
     */
    public GetResumeArgument()
    {
        super(
                // creationStackOffset
                2 );
    }

    @Override
    public RESUME_ARGUMENT evaluate(
            final HasArgumentsAndVariables<? extends RESUME_ARGUMENT>/*CoroutineOrFunctioncallOrComplexstmt<?, ?>*/ parent )
    {
        if ( this.resumeArgumentType == null )
        {
            throw new IllegalStateException(
                    "this.resumeArgumentType == null " +  this );
        }

        final RESUME_ARGUMENT resumeArgument = parent.getResumeArgument();

        if ( resumeArgument != null &&
                ! resumeArgumentType.isInstance( resumeArgument ) )
        {
            throw new WrongExpressionResultValueClassException(
                    //valueExpression
                    this ,
                    //expectedClass
                    resumeArgumentType ,
                    //wrongValue
                    resumeArgument );
        }

        return resumeArgumentType.cast( resumeArgument );
        //return resumeArgument;
    }

    @Override
    public final List<GetFunctionArgument<? , ? , ?>> getFunctionArgumentGetsNotInFunction()
    {
        // nothing to do
        return Collections.emptyList();
    }

    @Override
    public final void checkUseVariables(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent ,
            final Map<String, Class<?>> globalVariableTypes ,
            final Map<String, Class<?>> localVariableTypes )
    {
        // nothing to do
    }

    @Override
    public final void checkUseArguments(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent )
    {
        // nothing to do
    }

    @Override
    public void setExprCoroutineReturnTypeAndResumeArgumentType(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ? , ?> parent ,
            //final Class<? /*extends COROUTINE_RETURN*/> coroutineReturnType ,
            final Class<? extends COROUTINE_RETURN> coroutineReturnType ,
            final Class<? extends RESUME_ARGUMENT> resumeArgumentType )
    {
        this.resumeArgumentType = resumeArgumentType;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<? extends RESUME_ARGUMENT>[] type()
    {
        return new Class[] { resumeArgumentType };
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return
                this.getClass().getSimpleName() +
                ( this.creationStackTraceElement != null
                    ? " " + this.creationStackTraceElement
                    : "" );
    }

}
