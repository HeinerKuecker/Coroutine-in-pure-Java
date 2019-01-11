package de.heinerkuecker.coroutine.expression;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine.HasCreationStackTraceElement;
import de.heinerkuecker.coroutine.expression.exc.WrongClassException;
import de.heinerkuecker.coroutine.step.CoroIterStep;
import de.heinerkuecker.util.ArrayTypeName;

public class GetLocalVar<T>
extends HasCreationStackTraceElement
implements CoroExpression<T>
{
    public final String localVarName;

    /**
     * Reifier for type param {@link #RESULT} to solve unchecked casts.
     *
     * For type check.
     * Solve unchecked cast.
     */
    public final Class<? extends T> type;

    /**
     * Constructor.
     * @param localVarName
     */
    public GetLocalVar(
            final String localVarName ,
            final Class<? extends T> type )
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
     * @see CoroExpression#evaluate(CoroIteratorOrProcedure)
     */
    //@SuppressWarnings("unchecked")
    @Override
    public T evaluate(
            final CoroIteratorOrProcedure<?> parent )
    {
        final Object localVarValue = parent.localVars().get( localVarName );

        if ( localVarValue != null &&
                ! type.isInstance( localVarValue ) )
        {
            //throw new ClassCastException(
            //        localVarValue.getClass().toString() +
            //        ( this.creationStackTraceElement != null
            //            ? " " + this.creationStackTraceElement
            //            : "" ) );
            throw new WrongClassException(
                    //valueExpression
                    this ,
                    //expectedClass
                    type ,
                    //wrongValue
                    localVarValue );
        }

        return type.cast( localVarValue );
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
                ":" +
                //this.type.getName() +
                ArrayTypeName.toStr( this.type ) +
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
