package de.heinerkuecker.coroutine.expression;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import de.heinerkuecker.coroutine.HasArgumentsAndVariables;

/**
 * Subtract
 * result of the right
 * expression {@link CoroExpression}
 * from the result of the left
 * expression {@link CoroExpression}.
 *
 * @author Heiner K&uuml;cker
 */
public class Subtract<T extends Number>
//implements CoroExpression<T>
extends LhsRhsExpression<T>
{
    /**
     * Left hand side expression.
     */
    //public final CoroExpression<? extends T> lhs;

    /**
     * Right hand side expression to subtract.
     */
    //public final CoroExpression<? extends T> rhs;

    /**
     * Constructor.
     *
     * @param lhs
     * @param rhs
     */
    public Subtract(
            final CoroExpression<? extends T> lhs ,
            final CoroExpression<? extends T> rhs )
    {
        //this.lhs = Objects.requireNonNull( lhs );
        //this.rhs = Objects.requireNonNull( rhs );
        super(
                lhs ,
                rhs );
    }

    /**
     * Add.
     */
    @Override
    public T evaluate(
            final HasArgumentsAndVariables/*CoroutineOrProcedureOrComplexstep<?, ?>*/ parent )
    {
        final T lhsResult = lhs.evaluate( parent );
        final T rhsResult = rhs.evaluate( parent );

        if ( lhsResult == null )
        {
            throw new NullPointerException( "lhs: " + lhs );
        }

        if ( rhsResult == null )
        {
            throw new NullPointerException( "rhs: " + rhs );
        }

        if ( lhsResult instanceof Byte ||
                lhsResult instanceof Short ||
                lhsResult instanceof Integer ||
                lhsResult instanceof AtomicInteger ||
                lhsResult instanceof Long )
        {
            final long lhsLong = lhsResult.longValue();

            if ( rhsResult instanceof Byte ||
                    rhsResult instanceof Short ||
                    rhsResult instanceof Integer ||
                    rhsResult instanceof AtomicInteger ||
                    rhsResult instanceof Long ||
                    rhsResult instanceof AtomicLong )
            {
                return (T) (Long) ( ( (long) lhsLong ) - rhsResult.longValue() );
            }
            else if ( rhsResult instanceof Float ||
                    rhsResult instanceof Double )
            {
                return (T) (Double) ( ( (double) lhsLong ) - rhsResult.doubleValue() );
            }
            else if ( rhsResult instanceof BigInteger )
            {
                return (T) BigInteger.valueOf( lhsLong ).subtract( ( (BigInteger) rhsResult ) );
            }
            else if ( rhsResult instanceof BigDecimal )
            {
                return (T) BigDecimal.valueOf( lhsLong ).subtract( ( (BigDecimal) rhsResult ) );
            }
        }

        return (T) (Double) ( lhsResult.doubleValue() - rhsResult.doubleValue() );
    }

    //@Override
    //public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    //{
    //    final List<GetProcedureArgument<?>> result = new ArrayList<>();
    //
    //    result.addAll(
    //            lhs.getProcedureArgumentGetsNotInProcedure() );
    //
    //    result.addAll(
    //            rhs.getProcedureArgumentGetsNotInProcedure() );
    //
    //    return result;
    //}

    //@Override
    //public void checkUseVariables(
    //        //final boolean isCoroutineRoot ,
    //        final HashSet<String> alreadyCheckedProcedureNames ,
    //        final CoroutineOrProcedureOrComplexstep<?, ?> parent ,
    //        final Map<String, Class<?>> globalVariableTypes ,
    //        final Map<String, Class<?>> localVariableTypes )
    //{
    //    this.lhs.checkUseVariables(
    //            //isCoroutineRoot ,
    //            alreadyCheckedProcedureNames ,
    //            parent ,
    //            globalVariableTypes, localVariableTypes );
    //
    //    this.rhs.checkUseVariables(
    //            //isCoroutineRoot ,
    //            alreadyCheckedProcedureNames ,
    //            parent ,
    //            globalVariableTypes, localVariableTypes );
    //}

    //@Override
    //public void checkUseArguments(
    //        HashSet<String> alreadyCheckedProcedureNames, final CoroutineOrProcedureOrComplexstep<?, ?> parent )
    //{
    //    this.lhs.checkUseArguments( alreadyCheckedProcedureNames, parent );
    //    this.rhs.checkUseArguments( alreadyCheckedProcedureNames, parent );
    //}

    @SuppressWarnings("unchecked")
    @Override
    public Class<? extends T>[] type()
    {
        //return (Class<? extends T>) Number.class;
        return new Class[] { Number.class };
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return lhs + " - " + rhs;
    }

}
