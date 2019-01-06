package de.heinerkuecker.coroutine.expression;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine.HasCreationStackTraceElement;
import de.heinerkuecker.coroutine.step.CoroIterStep;

public class GetLocalVar<T>
extends HasCreationStackTraceElement
implements CoroExpression<T>
{
    public final String localVarName;

    /**
     * For type check.
     *
     * Solve unchecked cast.
     */
    public final Class<T> type;

    /**
     * Constructor.
     * @param localVarName
     */
    public GetLocalVar(
            final String localVarName ,
            final Class<T> type )
    {
        super(
                //creationStackOffset
                2 );

        this.localVarName =
                Objects.requireNonNull(
                        localVarName );

        this.type =
                Objects.requireNonNull(
                        type );
    }

    /**
     * @see CoroExpression#getValue(CoroIteratorOrProcedure)
     */
    @SuppressWarnings("unchecked")
    @Override
    public T getValue(
            final CoroIteratorOrProcedure<?> parent )
    {
        final Object localVarValue = parent.localVars().get( localVarName );

        if ( localVarValue != null &&
                ! type.isInstance( localVarValue ) )
        {
            throw new ClassCastException(
                    localVarValue.getClass().toString() +
                    ( this.creationStackTraceElement != null
                        ? " " + this.creationStackTraceElement
                        : "" )
                    );
        }

        return (T) localVarValue;
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return
                this.getClass().getSimpleName() +
                "[" +
                //"localVarName=" +
                this.localVarName +
                "]";
    }

    /**
     * @see CoroIterStep#getProcedureArgumentsNotInProcedure()
     */
    @Override
    public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    {
        return Collections.emptyList();
    }

}
