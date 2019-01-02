package de.heinerkuecker.coroutine.expression;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine.step.CoroIterStep;

/**
 * Add
 * result of the right
 * expression {@link CoroExpression}
 * to the result of the left
 * expression {@link CoroExpression}.
 *
 * @author Heiner K&uuml;cker
 */
public class Add<T extends Number>
implements CoroExpression<T>
{
    /**
     * Left hand side expression to add.
     */
    public final CoroExpression<? extends T> lhs;

    /**
     * Right hand side expression to add.
     */
    public final CoroExpression<? extends T> rhs;

    /**
     * Constructor.
     *
     * @param lhs
     * @param rhs
     */
    public Add(
            CoroExpression<? extends T> lhs ,
            CoroExpression<? extends T> rhs )
    {
        this.lhs = Objects.requireNonNull( lhs );
        this.rhs = Objects.requireNonNull( rhs );
    }

    /**
     * Add.
     *
     * @see CoroExpression#getValue(CoroIteratorOrProcedure)
     */
    @Override
    public T getValue(
            final CoroIteratorOrProcedure<?> parent)
    {
        final T lhsResult = lhs.getValue( parent );
        final T rhsResult = rhs.getValue( parent );


        if ( lhsResult == null || rhsResult == null )
        {
            throw new NullPointerException();
        }

        if ( lhsResult instanceof Integer )
        {
            if ( rhsResult instanceof Integer )
            {
                return (T) ( (Integer) ( ( (int) lhsResult ) + ( (int) rhsResult ) ) );
            }
        }

        throw new RuntimeException( "not implemented" );
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
