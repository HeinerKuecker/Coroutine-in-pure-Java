package de.heinerkuecker.coroutine_iterator.step.simple;

import java.util.Objects;

import de.heinerkuecker.coroutine_iterator.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine_iterator.CoroutineIterator;
import de.heinerkuecker.coroutine_iterator.step.result.CoroIterStepResult;

// TODO rename to Negatevar
public final class Negate<RESULT>
extends SimpleStep<RESULT/*, CoroutineIterator<RESULT>*/>
{
    /**
     * Name of variable to negate in
     * {@link CoroutineIterator#vars}.
     */
    public final String varName;

    /**
     * Constructor.
     */
    public Negate(
            final String varName )
    {
        this.varName =
                Objects.requireNonNull(
                        varName );
    }

    public static <RESULT> Negate<RESULT> negate(
            final String varName )
    {
        return new Negate<>(
                varName );
    }

    /**
     * Set variable.
     *
     * @see SimpleStep#execute
     */
    @Override
    public CoroIterStepResult<RESULT> execute(
            final CoroIteratorOrProcedure<RESULT> parent )
    {
        final Object varValue = parent.vars().get( varName );

        if ( varValue instanceof Boolean )
        {
            parent.vars().put( varName , ! (boolean) varValue );
        }
        else
            // null or not boolean is handled as false
        {
            parent.vars().put( varName , true );
        }
        return CoroIterStepResult.continueCoroutine();
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return varName + " = ! " + varName +
                ( this.creationStackTraceElement != null
                    ? " " + this.creationStackTraceElement
                    : "" );
    }

}
