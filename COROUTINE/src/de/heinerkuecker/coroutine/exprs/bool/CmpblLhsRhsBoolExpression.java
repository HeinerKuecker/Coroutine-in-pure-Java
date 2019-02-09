package de.heinerkuecker.coroutine.exprs.bool;

import de.heinerkuecker.coroutine.exprs.CoroExpression;
import de.heinerkuecker.coroutine.exprs.Value;

abstract public class CmpblLhsRhsBoolExpression<OPERAND extends Comparable<OPERAND> , COROUTINE_RETURN>
extends LhsRhsBoolExpression<OPERAND , COROUTINE_RETURN>
{
    /**
     * Constructor.
     *
     * @param lhs
     * @param rhs
     */
    protected CmpblLhsRhsBoolExpression(
            final CoroExpression<? extends OPERAND, COROUTINE_RETURN> lhs ,
            final CoroExpression<? extends OPERAND, COROUTINE_RETURN> rhs )
    {
        super(
                lhs ,
                rhs );
    }

    ///**
    // * Constructor.
    // *
    // * @param lhs
    // * @param rhs
    // */
    //protected CmpblLhsRhsBoolExpression(
    //        final CoroExpression<? extends OPERAND , COROUTINE_RETURN> lhs ,
    //        final CoroExpression<? extends OPERAND , COROUTINE_RETURN> rhs )
    //{
    //    super(
    //            lhs ,
    //            rhs );
    //}

    /**
     * Constructor.
     *
     * @param lhs
     * @param rhs
     */
    protected CmpblLhsRhsBoolExpression(
            final OPERAND lhsValue ,
            final CoroExpression<? extends OPERAND , COROUTINE_RETURN> rhs )
    {
        super(
                new Value<>(
                        (Class<? extends OPERAND>) Comparable.class ,
                        lhsValue ) ,
                rhs );
    }

    /**
     * Constructor.
     *
     * @param lhs
     * @param rhs
     */
    protected CmpblLhsRhsBoolExpression(
            final CoroExpression<? extends OPERAND , COROUTINE_RETURN> lhs ,
            final OPERAND rhsValue )
    {
        super(
                lhs ,
                new Value<>(
                        (Class<? extends OPERAND>) Comparable.class ,
                        rhsValue ) );
    }

    /**
     * Constructor.
     *
     * @param lhs
     * @param rhs
     */
    protected CmpblLhsRhsBoolExpression(
            final OPERAND lhsValue ,
            final OPERAND rhsValue )
    {
        super(
                new Value<>(
                        (Class<? extends OPERAND>) Comparable.class ,
                        lhsValue ) ,
                new Value<>(
                        (Class<? extends OPERAND>) Comparable.class ,
                        rhsValue ) );
    }

}
