package de.heinerkuecker.coroutine.expression;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine.HasCreationStackTraceElement;
import de.heinerkuecker.coroutine.step.CoroIterStep;

public class GetGlobalVar<T>
extends HasCreationStackTraceElement
implements CoroExpression<T>
{
    public final String globalVarName;

    /**
     * For type check.
     *
     * Solve unchecked cast.
     */
    public final Class<T> type;

    /**
     * Constructor.
     * @param globalVarName
     */
    public GetGlobalVar(
            final String globalVarName ,
            final Class<T> type )
    {
        super(
                //creationStackOffset
                2 );

        this.globalVarName =
                Objects.requireNonNull(
                        globalVarName );

        this.type =
                Objects.requireNonNull(
                        type );
    }

    /**
     * @see CoroExpression#evaluate(CoroIteratorOrProcedure)
     */
    @SuppressWarnings("unchecked")
    @Override
    public T evaluate(
            final CoroIteratorOrProcedure<?> parent )
    {
        final Object globalVarValue = parent.globalVars().get( globalVarName );

        if ( globalVarValue != null &&
                ! type.isInstance( globalVarValue ) )
        {
            throw new ClassCastException(
                    globalVarValue.getClass().toString() +
                    ( this.creationStackTraceElement != null
                        ? " " + this.creationStackTraceElement
                        : "" )
                    );
        }

        return type.cast( globalVarValue );
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
                //"globalVarName=" +
                this.globalVarName +
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
