package de.heinerkuecker.coroutine.expression;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine.HasArgumentsAndVariables;
import de.heinerkuecker.coroutine.step.CoroIterStep;

public class StrConcat
implements CoroExpression<String>
{
    /**
     * Left hand side expression.
     */
    public final CoroExpression<?> lhs;

    /**
     * Right hand side expression to add.
     */
    public final CoroExpression<?> rhs;

    /**
     * Constructor.
     *
     * @param lhs
     * @param rhs
     */
    public StrConcat(
            final CoroExpression<?> lhs ,
            final CoroExpression<?> rhs )
    {
        this.lhs = Objects.requireNonNull( lhs );
        this.rhs = Objects.requireNonNull( rhs );
    }


    /**
     * Add.
     *
     * @see CoroExpression#evaluate(CoroIteratorOrProcedure)
     */
    @Override
    public String evaluate(
            final HasArgumentsAndVariables/*CoroIteratorOrProcedure<?>*/ parent )
    {
        final Object lhsResult = lhs.evaluate( parent );
        final Object rhsResult = rhs.evaluate( parent );


        return lhsResult + "" + rhsResult;
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

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return lhs + " + " + rhs;
    }

}
