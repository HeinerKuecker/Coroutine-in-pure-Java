package de.heinerkuecker.coroutine.expression;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine.HasArgumentsAndVariables;
import de.heinerkuecker.coroutine.HasCreationStackTraceElement;
import de.heinerkuecker.coroutine.expression.exc.WrongClassException;

public class GetGlobalArgument<T>
extends HasCreationStackTraceElement
implements CoroExpression<T>
{
    /**
     * Name of procedure argument in
     * {@link CoroIteratorOrProcedure#globalArgumentValues()}
     * to return.
     */
    public final String globalArgumentName;

    /**
     * For type check.
     *
     * Solve unchecked cast.
     */
    public final Class<T> type;

    /**
     * Constructor.
     * @param globalArgumentName
     */
    public GetGlobalArgument(
            final String globalArgumentName ,
            final Class<T> type )
    {
        super(
                //creationStackOffset
                2 );

        this.globalArgumentName =
                Objects.requireNonNull(
                        globalArgumentName );

        this.type =
                Objects.requireNonNull(
                        type );
    }

    @Override
    public T evaluate(
            final HasArgumentsAndVariables/*CoroIteratorOrProcedure<?>*/ parent )
    {
        final Object procArgValue = parent.globalArgumentValues().get( globalArgumentName );

        if ( procArgValue != null &&
                ! type.isInstance( procArgValue ) )
        {
            //throw new ClassCastException(
            //        procArgValue.getClass().toString() +
            //        ( this.creationStackTraceElement != null
            //            ? " " + this.creationStackTraceElement
            //            : "" ) );
            throw new WrongClassException(
                    //valueExpression
                    this ,
                    //expectedClass
                    type ,
                    //wrongValue
                    procArgValue );
        }

        return type.cast( procArgValue );
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
                //"globalArgumentName=" +
                this.globalArgumentName +
                "]";
    }

    @Override
    public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    {
        return Collections.emptyList();
    }

}
