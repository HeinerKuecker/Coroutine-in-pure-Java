package de.heinerkuecker.coroutine.exprs;

import de.heinerkuecker.coroutine.HasArgumentsAndVariables;

/**
 * Cast result of the specified
 * expression {@link CoroExpression}
 * to {@link Long}.
 *
 * @param <T> number type to cast
 * @author Heiner K&uuml;cker
 */
public class CastToLong<T extends Number>
extends AbstrOneExprExpression<Long , Number>
//implements CoroExpression<Long>
{
    /**
     * Number expression to deliver number object to cast.
     */
    //public final CoroExpression<? extends T> numberExpression;

    /**
     * Constructor.
     *
     * @param numberExpression expression to deliver number object to cast
     */
    public CastToLong(
            final CoroExpression<? extends T> numberExpression )
    {
        //this.numberExpression = Objects.requireNonNull( numberExpression );
        super( numberExpression );
    }

    /**
     * Add.
     */
    @Override
    public Long evaluate(
            final HasArgumentsAndVariables<?>/*CoroutineOrProcedureOrComplexstep<?, ?>*/ parent )
    {
        final Number numberExpressionResult = /*numberExpression*/expr.evaluate( parent );

        if ( numberExpressionResult == null )
        {
            //throw new NullPointerException( "result of numberExpression: " + /*numberExpression*/expr );
            return null;
        }

        return numberExpressionResult.longValue();
    }

    //@Override
    //public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    //{
    //    return numberExpression.getProcedureArgumentGetsNotInProcedure();
    //}

    //@Override
    //public void checkUseVariables(
    //        ////final boolean isCoroutineRoot ,
    //        final HashSet<String> alreadyCheckedProcedureNames ,
    //        final CoroutineOrProcedureOrComplexstep<?, ?> parent ,
    //        final Map<String, Class<?>> globalVariableTypes ,
    //        final Map<String, Class<?>> localVariableTypes )
    //{
    //    this.numberExpression.checkUseVariables(
    //            //isCoroutineRoot ,
    //            alreadyCheckedProcedureNames ,
    //            parent ,
    //            globalVariableTypes, localVariableTypes );
    //}

    //@Override
    //public void checkUseArguments(
    //        HashSet<String> alreadyCheckedProcedureNames, final CoroutineOrProcedureOrComplexstep<?, ?> parent )
    //{
    //    this.numberExpression.checkUseArguments( alreadyCheckedProcedureNames, parent );
    //}

    @SuppressWarnings("unchecked")
    @Override
    public Class<? extends Long>[] type()
    {
        //return Long.class;
        return new Class[] { Long.class };
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return " ( (long) " + /*numberExpression*/expr + " )";
    }

}
