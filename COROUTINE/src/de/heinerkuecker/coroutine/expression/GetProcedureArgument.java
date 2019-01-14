package de.heinerkuecker.coroutine.expression;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine.HasArgumentsAndVariables;
import de.heinerkuecker.coroutine.HasCreationStackTraceElement;
import de.heinerkuecker.coroutine.expression.exc.WrongClassException;
import de.heinerkuecker.coroutine.step.CoroIterStep;

public class GetProcedureArgument<T>
extends HasCreationStackTraceElement
implements CoroExpression<T>
{
    /**
     * Name of procedure argument in
     * {@link CoroIteratorOrProcedure#procedureArgumentValues()}
     * to return.
     */
    public final String procedureArgumentName;

    /**
     * For type check.
     *
     * Solve unchecked cast.
     */
    public final Class<T> type;

    /**
     * Constructor.
     * @param procedureArgumentName
     */
    public GetProcedureArgument(
            final String procedureArgumentName ,
            final Class<T> type )
    {
        super(
                //creationStackOffset
                2 );

        this.procedureArgumentName =
                Objects.requireNonNull(
                        procedureArgumentName );

        this.type =
                Objects.requireNonNull(
                        type );
    }

    /**
     * @see CoroExpression#evaluate(CoroIteratorOrProcedure)
     */
    @Override
    public T evaluate(
            final HasArgumentsAndVariables/*CoroIteratorOrProcedure<?>*/ parent )
    {
        final Object procArgValue = parent.procedureArgumentValues().get( procedureArgumentName );

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
                //"procedureArgumentName=" +
                this.procedureArgumentName +
                "]";
    }

    @Override
    public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    {
        return Arrays.asList( this );
    }

    @Override
    public void checkUseUndeclaredVariables(
            final CoroIteratorOrProcedure<?> parent ,
            final Map<String, Class<?>> globalVariableTypes ,
            final Map<String, Class<?>> localVariableTypes )
    {
        // nothing to do
    }

}
