package de.heinerkuecker.coroutine.exprs.bool;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep;
import de.heinerkuecker.coroutine.HasArgumentsAndVariables;
import de.heinerkuecker.coroutine.exprs.CoroExpression;
import de.heinerkuecker.coroutine.exprs.GetProcedureArgument;

/**
 * Is empty {@link Condition}
 * to check emptiness of the result
 * of the specified
 * expression {@link CoroExpression}.
 *
 * Checks is
 * <code>null</code>,
 * empty array,
 * empty {@link String},
 * empty {@link Map} or
 * empty {@link Collection}.
 *
 * @author Heiner K&uuml;cker
 */
public class IsEmpty
//implements ConditionOrBooleanExpression/*Condition/*<CoroutineIterator<?>>*/
extends CoroBooleanExpression
{
    /**
     * Expression to check.
     */
    public final CoroExpression<?> expression;

    /**
     * Constructor.
     */
    public IsEmpty(
            final CoroExpression<?> expression )
    {
        this.expression = Objects.requireNonNull( expression );
    }

    ///**
    // * Checks variable is
    // * <code>null</code>,
    // * empty array,
    // * empty {@link String},
    // * empty {@link Map} or
    // * empty {@link Collection}.
    // */
    //@Override
    //public boolean execute(
    //        final HasArgumentsAndVariables<?>/*CoroutineOrProcedureOrComplexstep<?, ?>*/ parent )
    //{
    //    //final Object varValue = parent.localVars().get( varName );
    //    final Object varValue = expression.evaluate( parent );
    //
    //    if ( varValue == null )
    //    {
    //        return true;
    //    }
    //
    //    if ( varValue instanceof String )
    //    {
    //        return ( (String) varValue ).isEmpty();
    //    }
    //
    //    if ( varValue instanceof Collection )
    //    {
    //        return ( (Collection<?>) varValue ).isEmpty();
    //    }
    //
    //    if ( varValue instanceof Map )
    //    {
    //        return ( (Map<?, ?>) varValue ).isEmpty();
    //    }
    //
    //    if ( varValue instanceof Object[] )
    //    {
    //        return ( (Object[]) varValue ).length == 0;
    //    }
    //
    //    if ( varValue instanceof boolean[] )
    //    {
    //        return ( (boolean[]) varValue ).length == 0;
    //    }
    //
    //    if ( varValue instanceof byte[] )
    //    {
    //        return ( (byte[]) varValue ).length == 0;
    //    }
    //
    //    if ( varValue instanceof short[] )
    //    {
    //        return ( (short[]) varValue ).length == 0;
    //    }
    //
    //    if ( varValue instanceof char[] )
    //    {
    //        return ( (char[]) varValue ).length == 0;
    //    }
    //
    //    if ( varValue instanceof int[] )
    //    {
    //        return ( (int[]) varValue ).length == 0;
    //    }
    //
    //    if ( varValue instanceof long[] )
    //    {
    //        return ( (long[]) varValue ).length == 0;
    //    }
    //
    //    if ( varValue instanceof float[] )
    //    {
    //        return ( (float[]) varValue ).length == 0;
    //    }
    //
    //    if ( varValue instanceof double[] )
    //    {
    //        return ( (double[]) varValue ).length == 0;
    //    }
    //
    //    return false;
    //}

    /**
     * Checks variable is
     * <code>null</code>,
     * empty array,
     * empty {@link String},
     * empty {@link Map} or
     * empty {@link Collection}.
     */
    @Override
    public Boolean evaluate(
            final HasArgumentsAndVariables<?> parent )
    {
        final Object varValue = expression.evaluate( parent );

        if ( varValue == null )
        {
            return true;
        }

        if ( varValue instanceof String )
        {
            return ( (String) varValue ).isEmpty();
        }

        if ( varValue instanceof Collection )
        {
            return ( (Collection<?>) varValue ).isEmpty();
        }

        if ( varValue instanceof Map )
        {
            return ( (Map<?, ?>) varValue ).isEmpty();
        }

        if ( varValue instanceof Object[] )
        {
            return ( (Object[]) varValue ).length == 0;
        }

        if ( varValue instanceof boolean[] )
        {
            return ( (boolean[]) varValue ).length == 0;
        }

        if ( varValue instanceof byte[] )
        {
            return ( (byte[]) varValue ).length == 0;
        }

        if ( varValue instanceof short[] )
        {
            return ( (short[]) varValue ).length == 0;
        }

        if ( varValue instanceof char[] )
        {
            return ( (char[]) varValue ).length == 0;
        }

        if ( varValue instanceof int[] )
        {
            return ( (int[]) varValue ).length == 0;
        }

        if ( varValue instanceof long[] )
        {
            return ( (long[]) varValue ).length == 0;
        }

        if ( varValue instanceof float[] )
        {
            return ( (float[]) varValue ).length == 0;
        }

        if ( varValue instanceof double[] )
        {
            return ( (double[]) varValue ).length == 0;
        }

        return false;
    }

    @Override
    public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    {
        return this.expression.getProcedureArgumentGetsNotInProcedure();
    }

    @Override
    public void checkUseVariables(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstep<?, ?> parent ,
            final Map<String, Class<?>> globalVariableTypes ,
            final Map<String, Class<?>> localVariableTypes )
    {
        this.expression.checkUseVariables(
                alreadyCheckedProcedureNames ,
                parent ,
                globalVariableTypes, localVariableTypes );
    }

    @Override
    public void checkUseArguments(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstep<?, ?> parent )
    {
        this.expression.checkUseArguments( alreadyCheckedProcedureNames , parent );
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return expression + " is empty";
    }

}
