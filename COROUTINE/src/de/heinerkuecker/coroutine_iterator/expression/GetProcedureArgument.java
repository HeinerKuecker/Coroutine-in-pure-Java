package de.heinerkuecker.coroutine_iterator.expression;

import java.util.Arrays;
import java.util.List;

import de.heinerkuecker.coroutine_iterator.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine_iterator.step.CoroIterStep;

public class GetProcedureArgument<T>
implements CoroExpression<T>
{
    /**
     * Name of procedure argument in
     * {@link CoroIteratorOrProcedure#procedureArgumentValues()}
     * to return.
     */
    public final String procedureArgumentName;

    /**
     * Constructor.
     * @param procedureArgumentName
     */
    public GetProcedureArgument(
            final String procedureArgumentName )
    {
        this.procedureArgumentName = procedureArgumentName;
    }

    /**
     * @see CoroExpression#getValue(CoroIteratorOrProcedure)
     */
    @SuppressWarnings("unchecked")
    @Override
    public T getValue(
            final CoroIteratorOrProcedure<?> parent )
    {
        return (T) parent.procedureArgumentValues().get( procedureArgumentName );
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return this.getClass().getSimpleName() + "[procedureArgumentName=" + this.procedureArgumentName + "]";
    }

    /**
     * @see CoroIterStep#getProcedureArgumentsNotInProcedure()
     */
    @Override
    public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    {
        return Arrays.asList( this );
    }

}
