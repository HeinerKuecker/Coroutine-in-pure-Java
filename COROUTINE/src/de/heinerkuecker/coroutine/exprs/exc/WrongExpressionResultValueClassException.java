package de.heinerkuecker.coroutine.exprs.exc;

import de.heinerkuecker.coroutine.exprs.CoroExpression;
import de.heinerkuecker.coroutine.exprs.complex.ComplexExpression;

/**
 * Exception like {@link ClassCastException},
 * but more informations.
 */
public class WrongExpressionResultValueClassException
extends RuntimeException
{
    /**
     * Generated by Eclipse.
     */
    private static final long serialVersionUID = 2180061788607918403L;

    /**
     * Constructor.
     */
    public WrongExpressionResultValueClassException(
            final CoroExpression<? , ?> valueExpression ,
            final Class<?> expectedClass ,
            final Object wrongValue )
    {
        super(
                "wrong result value class for expression: " +
                valueExpression + ",\n" +
                "expected class: " +
                //"expected: " +
                expectedClass + ", " +
                "wrong value: " +
                wrongValue + ", " +
                "wrong class: " +
                //"wrong: " +
                wrongValue.getClass() );
    }

    /**
     * Constructor.
     */
    public WrongExpressionResultValueClassException(
            final ComplexExpression<? , ? , ? , ? , ?> valueExpression ,
            final Class<?> expectedClass ,
            final Object wrongValue )
    {
        super(
                "wrong result value class for expression: " +
                valueExpression + ",\n" +
                "expected class: " +
                //"expected: " +
                expectedClass + ", " +
                "wrong value: " +
                wrongValue + ", " +
                "wrong class: " +
                //"wrong: " +
                wrongValue.getClass() );
    }
}
