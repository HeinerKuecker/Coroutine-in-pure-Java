package de.heinerkuecker.coroutine.arg;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroCheckable;
import de.heinerkuecker.coroutine.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine.expression.CoroExpression;
import de.heinerkuecker.coroutine.expression.GetProcedureArgument;
import de.heinerkuecker.coroutine.expression.Value;

public class Argument<T>
implements CoroCheckable
{
    public final String name;

    public final CoroExpression<T> expression;

    /**
     * Constructor.
     *
     * @param name
     * @param expression
     */
    public Argument(
            final String name ,
            final CoroExpression<T> expression )
    {
        this.name = Objects.requireNonNull( name );
        this.expression = Objects.requireNonNull( expression );
    }

    /**
     * Convenience Constructor.
     *
     * @param name
     * @param expression
     */
    public Argument(
            final String name ,
            final T value )
    {
        this.name = Objects.requireNonNull( name );
        this.expression = new Value<T>( value );
    }

    public T getValue(
            final CoroIteratorOrProcedure<?> parent )
    {
        return this.expression.evaluate( parent );
    }

    /* (non-Javadoc)
     * @see de.heinerkuecker.coroutine.CoroCheckable#getProcedureArgumentGetsNotInProcedure()
     */
    @Override
    public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    {
        return this.expression.getProcedureArgumentGetsNotInProcedure();
    }

    @Override
    public void checkUseVariables(
            HashSet<String> alreadyCheckedProcedureNames ,
            CoroIteratorOrProcedure<?> parent ,
            Map<String, Class<?>> globalVariableTypes ,
            Map<String, Class<?>> localVariableTypes )
    {
        this.expression.checkUseVariables(
                alreadyCheckedProcedureNames ,
                parent ,
                globalVariableTypes, localVariableTypes );
    }

    @Override
    public void checkUseArguments(
            HashSet<String> alreadyCheckedProcedureNames ,
            CoroIteratorOrProcedure<?> parent )
    {
        this.expression.checkUseArguments(
                alreadyCheckedProcedureNames ,
                parent );
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return this.getClass().getSimpleName() + "[name=" + this.name + ", expression=" + this.expression + "]";
    }

}
