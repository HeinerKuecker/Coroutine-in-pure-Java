package de.heinerkuecker.coroutine.exprs.bool;

import de.heinerkuecker.coroutine.exprs.SimpleExpression;

abstract public class CoroBooleanExpression<COROUTINE_RETURN , RESUME_ARGUMENT>
implements SimpleExpression<Boolean , COROUTINE_RETURN , RESUME_ARGUMENT>
{
    ///**
    // * Execute the condition and return the result.
    // *
    // * @param parent the {@link CoroutineOrFunctioncallOrComplexstmt} instance
    // * @return condition result
    // */
    //@Override
    //boolean execute(
    //        final HasArgumentsAndVariables<? extends RESUME_ARGUMENT>/*CoroutineOrFunctioncallOrComplexstmt<?, ?>*/ parent );

    @SuppressWarnings("unchecked")
    @Override
    public Class<? extends Boolean>[] type()
    {
        return new Class[]{ Boolean.class };
    }

}
