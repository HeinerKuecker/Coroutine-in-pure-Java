package de.heinerkuecker.coroutine.exprs;

import java.util.Objects;

import de.heinerkuecker.coroutine.HasArgumentsAndVariables;

public class GetResumeArgument<RESUME_ARGUMENT , COROUTINE_RETURN>
//implements CoroExpression<RESUME_ARGUMENT>
extends AbstrHasSrcPosNoVarsNoArgsExpression<RESUME_ARGUMENT , COROUTINE_RETURN , RESUME_ARGUMENT>
{
    /**
     * For type check.
     *
     * Solve unchecked cast.
     */
    public final Class<? extends RESUME_ARGUMENT> resumeArgumentType;

    /**
     * Constructor.
     */
    public GetResumeArgument(
            final Class<? extends RESUME_ARGUMENT> resumeArgumentType )
    {
        super(
                // creationStackOffset
                3 );

        this.resumeArgumentType =
                Objects.requireNonNull(
                        resumeArgumentType );
    }

    @Override
    public RESUME_ARGUMENT evaluate(
            final HasArgumentsAndVariables<? extends RESUME_ARGUMENT>/*CoroutineOrFunctioncallOrComplexstmt<?, ?>*/ parent )
    {
        final RESUME_ARGUMENT resumeArgument = parent.getResumeArgument();

        //if ( resumeArgument != null &&
        //        ! resumeArgumentType.isInstance( resumeArgument ) )
        //{
        //    throw new WrongExpressionResultValueClassException(
        //            //valueExpression
        //            this ,
        //            //expectedClass
        //            resumeArgumentType ,
        //            //wrongValue
        //            resumeArgument );
        //}
        //
        //return resumeArgumentType.cast( resumeArgument );

        return resumeArgument;
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
