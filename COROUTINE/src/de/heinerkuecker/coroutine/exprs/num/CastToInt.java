package de.heinerkuecker.coroutine.exprs.num;

import de.heinerkuecker.coroutine.HasArgumentsAndVariables;
import de.heinerkuecker.coroutine.exprs.AbstrOneExprExpression;
import de.heinerkuecker.coroutine.exprs.CoroExpression;

/**
 * Cast result of the specified
 * expression {@link CoroExpression}
 * to {@link Integer}.
 *
 * @param <T> number type to cast
 * @author Heiner K&uuml;cker
 */
public class CastToInt<T extends Number>
extends AbstrOneExprExpression<Integer , Number>
//implements CoroExpression<Integer>
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
    public CastToInt(
            final CoroExpression<? extends T> numberExpression )
    {
        //this.numberExpression = Objects.requireNonNull( numberExpression );
        super( numberExpression );
    }

    /**
     * Add.
     */
    @Override
    public Integer evaluate(
            final HasArgumentsAndVariables<?>/*CoroutineOrProcedureOrComplexstmt<?, ?>*/ parent )
    {
        final Number numberExpressionResult = /*numberExpression*/expr.evaluate( parent );

        if ( numberExpressionResult == null )
        {
            //throw new NullPointerException( "result of numberExpression: " + /*numberExpression*/expr );
            return null;
        }

        return numberExpressionResult.intValue();
    }

    //@Override
    //public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    //{
    //    return numberExpression.getProcedureArgumentGetsNotInProcedure();
    //}

    //@Override
    //public void checkUseVariables(
    //        final HashSet<String> alreadyCheckedProcedureNames ,
    //        final CoroutineOrProcedureOrComplexstmt<?, ?> parent ,
    //        final Map<String, Class<?>> globalVariableTypes ,
    //        final Map<String, Class<?>> localVariableTypes )
    //{
    //    this.numberExpression.checkUseVariables(
    //            alreadyCheckedProcedureNames ,
    //            parent ,
    //            globalVariableTypes, localVariableTypes );
    //}

    //@Override
    //public void checkUseArguments(
    //        HashSet<String> alreadyCheckedProcedureNames, final CoroutineOrProcedureOrComplexstmt<?, ?> parent )
    //{
    //    this.numberExpression.checkUseArguments( alreadyCheckedProcedureNames, parent );
    //}

    @SuppressWarnings("unchecked")
    @Override
    public Class<? extends Integer>[] type()
    {
        //return Integer.class;
        return new Class[] { Integer.class };
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return " ( (int) " + /*numberExpression*/expr + " )";
    }

}
