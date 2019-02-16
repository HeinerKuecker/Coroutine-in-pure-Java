package de.heinerkuecker.coroutine.exprs.bool;

import de.heinerkuecker.coroutine.exprs.SimpleExpression;
import de.heinerkuecker.coroutine.exprs.Value;

abstract public class CmpblLhsRhsBoolExpression<OPERAND extends Comparable<OPERAND> , COROUTINE_RETURN , RESUME_ARGUMENT>
extends LhsRhsBoolExpression<OPERAND , COROUTINE_RETURN , RESUME_ARGUMENT>
{
    /**
     * Constructor.
     *
     * @param lhs
     * @param rhs
     */
    protected CmpblLhsRhsBoolExpression(
            final SimpleExpression<? extends OPERAND, COROUTINE_RETURN , RESUME_ARGUMENT> lhs ,
            final SimpleExpression<? extends OPERAND, COROUTINE_RETURN , RESUME_ARGUMENT> rhs )
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
            final SimpleExpression<? extends OPERAND , COROUTINE_RETURN , RESUME_ARGUMENT> rhs )
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
            final SimpleExpression<? extends OPERAND , COROUTINE_RETURN , RESUME_ARGUMENT> lhs ,
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
