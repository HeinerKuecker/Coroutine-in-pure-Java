package de.heinerkuecker.coroutine.exprs;

import java.util.Objects;

import de.heinerkuecker.coroutine.HasArgumentsAndVariables;
import de.heinerkuecker.coroutine.exprs.exc.WrongExpressionClassException;

public class GetResumeArgument<T>
//implements CoroExpression<T>
extends AbstrHasSrcPosNoVarsNoArgsExpression<T>
{
    /**
     * For type check.
     *
     * Solve unchecked cast.
     */
    public final Class<? extends T> type;

    /**
     * Constructor.
     *
     * @param value
     */
    public GetResumeArgument(
            final Class<? extends T> type )
    {
        super(
                // creationStackOffset
                3 );

        this.type =
                Objects.requireNonNull(
                        type );
    }

    @Override
    public T evaluate(
            final HasArgumentsAndVariables<?>/*CoroutineOrProcedureOrComplexstep<?, ?>*/ parent )
    {
        final Object resumeArgument = parent.getResumeArgument();

        if ( resumeArgument != null &&
                ! type.isInstance( resumeArgument ) )
        {
            throw new WrongExpressionClassException(
                    //valueExpression
                    this ,
                    //expectedClass
                    type ,
                    //wrongValue
                    resumeArgument );
        }

        return type.cast( resumeArgument );
    }

    //@Override
    //public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    //{
    //    return Collections.emptyList();
    //}

    //@Override
    //public void checkUseVariables(
    //        //final boolean isCoroutineRoot ,
    //        final HashSet<String> alreadyCheckedProcedureNames ,
    //        final CoroutineOrProcedureOrComplexstep<?, ?> parent ,
    //        final Map<String, Class<?>> globalVariableTypes ,
    //        final Map<String, Class<?>> localVariableTypes )
    //{
    //    // nothing to do
    //}

    //@Override
    //public void checkUseArguments(
    //        HashSet<String> alreadyCheckedProcedureNames, final CoroutineOrProcedureOrComplexstep<?, ?> parent )
    //{
    //    // nothing to do
    //}

    @SuppressWarnings("unchecked")
    @Override
    public Class<? extends T>[] type()
    {
        return new Class[] { type };
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
