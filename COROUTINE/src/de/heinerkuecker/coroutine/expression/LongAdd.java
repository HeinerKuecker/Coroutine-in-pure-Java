package de.heinerkuecker.coroutine.expression;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep;
import de.heinerkuecker.coroutine.HasArgumentsAndVariables;
import de.heinerkuecker.coroutine.step.CoroIterStep;

/**
 * {@link Long} add
 * result of the right
 * expression {@link CoroExpression}
 * to the result of the left
 * expression {@link CoroExpression}.
 *
 * @author Heiner K&uuml;cker
 */
public class LongAdd
implements CoroExpression<Long>
{
    /**
     * Left hand side expression.
     */
    public final CoroExpression<Long> lhs;

    /**
     * Right hand side expression to add.
     */
    public final CoroExpression<Long> rhs;

    /**
     * Constructor.
     *
     * @param lhs
     * @param rhs
     */
    public LongAdd(
            final CoroExpression<Long> lhs ,
            final CoroExpression<Long> rhs )
    {
        this.lhs = Objects.requireNonNull( lhs );
        this.rhs = Objects.requireNonNull( rhs );
    }

    /**
     * Add.
     */
    @Override
    public Long evaluate(
            final HasArgumentsAndVariables/*CoroutineOrProcedureOrComplexstep<?>*/ parent )
    {
        final Long lhsResult = lhs.evaluate( parent );
        final Long rhsResult = rhs.evaluate( parent );

        if ( lhsResult == null )
        {
            throw new NullPointerException( "lhs: " + lhs );
        }

        if ( rhsResult == null )
        {
            throw new NullPointerException( "rhs: " + rhs );
        }

        return lhsResult + rhsResult;
    }

    /**
     * @see CoroIterStep#getProcedureArgumentGetsNotInProcedure()
     */
    @Override
    public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    {
        final List<GetProcedureArgument<?>> result = new ArrayList<>();

        result.addAll(
                lhs.getProcedureArgumentGetsNotInProcedure() );

        result.addAll(
                rhs.getProcedureArgumentGetsNotInProcedure() );

        return result;
    }

    @Override
    public void checkUseVariables(
            final boolean isCoroutineRoot ,
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstep<?> parent,
            final Map<String, Class<?>> globalVariableTypes ,
            final Map<String, Class<?>> localVariableTypes)
    {
        this.lhs.checkUseVariables(
                isCoroutineRoot ,
                alreadyCheckedProcedureNames ,
                parent ,
                globalVariableTypes, localVariableTypes );

        this.rhs.checkUseVariables(
                isCoroutineRoot ,
                alreadyCheckedProcedureNames ,
                parent ,
                globalVariableTypes, localVariableTypes );
    }

    @Override
    public void checkUseArguments(
            HashSet<String> alreadyCheckedProcedureNames, final CoroutineOrProcedureOrComplexstep<?> parent )
    {
        this.lhs.checkUseArguments( alreadyCheckedProcedureNames, parent );
        this.rhs.checkUseArguments( alreadyCheckedProcedureNames, parent );
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<Long>[] type()
    {
        //return (Class<? extends T>) Number.class;
        return new Class[] { Long.class };
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return lhs + " + " + rhs;
    }

}