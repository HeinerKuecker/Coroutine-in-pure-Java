package de.heinerkuecker.coroutine.expression;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep;
import de.heinerkuecker.coroutine.HasArgumentsAndVariables;
import de.heinerkuecker.coroutine.step.CoroIterStep;

/**
 * Cast
 * result of the specified
 * expression {@link CoroExpression}.
 *
 * @author Heiner K&uuml;cker
 */
public class CastToInt<T extends Number>
implements CoroExpression<Integer>
{
    /**
     * Number expression to deliver object to cast.
     */
    public final CoroExpression<? extends T> numberExpression;

    /**
     * Constructor.
     */
    public CastToInt(
            final CoroExpression<? extends T> numberExpression )
    {
        this.numberExpression = Objects.requireNonNull( numberExpression );
    }

    /**
     * Add.
     *
     * @see CoroExpression#evaluate(CoroutineOrProcedureOrComplexstep)
     */
    @Override
    public Integer evaluate(
            final HasArgumentsAndVariables/*CoroutineOrProcedureOrComplexstep<?>*/ parent )
    {
        final Number numberExpressionResult = numberExpression.evaluate( parent );

        if ( numberExpressionResult == null )
        {
            throw new NullPointerException( "result of numberExpression: " + numberExpression );
        }

        return numberExpressionResult.intValue();
    }

    /**
     * @see CoroIterStep#getProcedureArgumentGetsNotInProcedure()
     */
    @Override
    public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    {
        return numberExpression.getProcedureArgumentGetsNotInProcedure();
    }

    @Override
    public void checkUseVariables(
            ////final boolean isCoroutineRoot ,
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstep<?> parent ,
            final Map<String, Class<?>> globalVariableTypes ,
            final Map<String, Class<?>> localVariableTypes )
    {
        this.numberExpression.checkUseVariables(
                //isCoroutineRoot ,
                alreadyCheckedProcedureNames ,
                parent ,
                globalVariableTypes, localVariableTypes );
    }

    @Override
    public void checkUseArguments(
            HashSet<String> alreadyCheckedProcedureNames, final CoroutineOrProcedureOrComplexstep<?> parent )
    {
        this.numberExpression.checkUseArguments( alreadyCheckedProcedureNames, parent );
    }

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
        return " ( (int) " + numberExpression + " )";
    }

}
