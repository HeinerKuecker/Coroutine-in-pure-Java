package de.heinerkuecker.coroutine.step.ret;

import java.util.HashSet;
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
 * and suspend stepping.
 *
 * @param <RESULT> result type of method {@link CoroutineIterator#next()}
 * @author Heiner K&uuml;cker
 */
public class YieldReturn<RESULT>
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
    public YieldReturn(
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
    public YieldReturn(
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
     * Compute result value and wrap it in yield return.
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

        return new CoroIterStepResult.YieldReturnWithResult<RESULT>(
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
    public void checkUseVariables(
            HashSet<String> alreadyCheckedProcedureNames ,
            final CoroIteratorOrProcedure<?> parent ,
            final Map<String, Class<?>> globalVariableTypes, final Map<String, Class<?>> localVariableTypes )
    {
        this.expression.checkUseVariables(
                alreadyCheckedProcedureNames ,
                parent ,
                globalVariableTypes, localVariableTypes );
    }

    @Override
    public void checkUseArguments(
            HashSet<String> alreadyCheckedProcedureNames, final CoroIteratorOrProcedure<?> parent )
    {
        this.expression.checkUseArguments( alreadyCheckedProcedureNames, parent );
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
