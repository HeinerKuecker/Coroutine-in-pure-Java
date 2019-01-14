package de.heinerkuecker.coroutine.expression;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import de.heinerkuecker.coroutine.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine.HasArgumentsAndVariables;
import de.heinerkuecker.util.ArrayDeepToString;

public class Value<T>
implements CoroExpression<T>
{
    public final T value;

    /**
     * Constructor.
     *
     * @param value
     */
    public Value(
            final T value )
    {
        this.value = value;
    }

    /**
     * @see CoroExpression#evaluate(CoroIteratorOrProcedure)
     */
    @Override
    public T evaluate(
            final HasArgumentsAndVariables/*CoroIteratorOrProcedure<?>*/ parent )
    {
        return this.value;
    }

    /**
     * @see CoroExpression#getProcedureArgumentGetsNotInProcedure()
     */
    @Override
    public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    {
        return Collections.emptyList();
    }

    @Override
    public void checkUseUndeclaredVariables(
            final CoroIteratorOrProcedure<?> parent ,
            final Map<String, Class<?>> globalVariableTypes ,
            final Map<String, Class<?>> localVariableTypes )
    {
        // nothing to do
    }

    @Override
    public void checkUseUndeclaredParameters(
            final CoroIteratorOrProcedure<?> parent )
    {
        // nothing to do
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return
                //this.getClass().getSimpleName() +
                //"[" +
                //"value=" +
                //String.valueOf( this.value )
                ArrayDeepToString.deepToString( this.value );
                //"]";
    }

}
