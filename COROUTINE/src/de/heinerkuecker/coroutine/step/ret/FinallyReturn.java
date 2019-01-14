package de.heinerkuecker.coroutine.step.ret;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.expression.CoroExpression;
import de.heinerkuecker.coroutine.expression.GetProcedureArgument;
import de.heinerkuecker.coroutine.expression.Value;
import de.heinerkuecker.coroutine.expression.exc.WrongClassException;
import de.heinerkuecker.coroutine.step.CoroIterStep;
import de.heinerkuecker.coroutine.step.CoroIterStepResult;
import de.heinerkuecker.coroutine.step.simple.SimpleStep;

/**
 * Step {@link CoroIterStep} to
 * return a specified value
 * and stop stepping.
 *
 * @param <RESULT> result type of method {@link CoroutineIterator#next()}
 * @author Heiner K&uuml;cker
 */
public class FinallyReturn<RESULT>
extends SimpleStep<RESULT/*, CoroutineIterator<RESULT>*/>
{
    public final CoroExpression<? extends RESULT> expression;

    /**
     * Reifier for type param {@link #RESULT} to solve unchecked casts.
     */
    private Class<? extends RESULT> resultType;

    /**
     * Constructor.
     */
    public FinallyReturn(
            final CoroExpression<? extends RESULT> expression )
    {
        super(
                //creationStackOffset
                //2
                );

        this.expression =
                Objects.requireNonNull(
                        expression );
    }

    /**
     * Constructor.
     */
    public FinallyReturn(
            final RESULT value )
    {
        super(
                //creationStackOffset
                //2
                );

        this.expression =
                new Value<>( value );
    }

    /**
     * Compute result value and wrap it in finally return.
     *
     * @see CoroIterStep#execute(Object)
     */
    @Override
    public CoroIterStepResult<RESULT> execute(
            final CoroIteratorOrProcedure<RESULT> parent )
    {
        final RESULT resultValue =
                expression.evaluate(
                        parent );

        if ( resultValue != null &&
                ! resultType.isInstance( resultValue ) )
        {
            throw new WrongClassException(
                    //valueExpression
                    expression ,
                    //expectedClass
                    resultType ,
                    //wrongValue
                    resultValue );
        }

        return new CoroIterStepResult.FinallyReturnWithResult<RESULT>(
                resultType.cast(
                        resultValue ) );
    }

    /**
     * @see CoroIterStep#getProcedureArgumentGetsNotInProcedure()
     */
    @Override
    public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    {
        return expression.getProcedureArgumentGetsNotInProcedure();
    }

    /**
     * @see CoroIterStep#setResultType(Class)
     */
    @Override
    public void setResultType(
            final Class<? extends RESULT> resultType )
    {
        this.resultType = resultType;
    }

    @Override
    public void checkUseUndeclaredVariables(
            final CoroIteratorOrProcedure<?> parent ,
            final Map<String, Class<?>> globalVariableTypes ,
            final Map<String, Class<?>> localVariableTypes )
    {
        this.expression.checkUseUndeclaredVariables(
                parent ,
                globalVariableTypes ,
                localVariableTypes );
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
                expression +
                "]" +
                ( this.creationStackTraceElement != null
                    ? " " + this.creationStackTraceElement
                    : "" );
    }

}
