package de.heinerkuecker.coroutine.expression.exc;

import de.heinerkuecker.coroutine.expression.CoroExpression;

/**
 * Exception like {@link ClassCastException},
 * but more informations.
 */
public class WrongClassException
extends RuntimeException
{
    /**
     * Generated by Eclipse.
     */
    private static final long serialVersionUID = 2180061788607918403L;

    /**
     * Constructor.
     */
    public WrongClassException(
            final CoroExpression<?> valueExpression ,
            final Class<?> expectedClass ,
            final Object wrongValue )
    {
        super(
                "wrong value class for expression: " +
                valueExpression + ",\n" +
                //"expected class: " +
                "expected: " +
                expectedClass + ", " +
                "wrong value: " +
                wrongValue + ", " +
                //"wrong class: " +
                "wrong: " +
                wrongValue.getClass() );
    }
}
