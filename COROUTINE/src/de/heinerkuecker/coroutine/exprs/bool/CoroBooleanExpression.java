package de.heinerkuecker.coroutine.exprs.bool;

import de.heinerkuecker.coroutine.exprs.CoroExpression;

//public interface CoroBooleanExpression
abstract public class CoroBooleanExpression<COROUTINE_RETURN>
//extends
implements
    CoroExpression<Boolean , COROUTINE_RETURN>
    //ConditionOrBooleanExpression
{
    ///**
    // * Execute the condition and return the result.
    // *
    // * @param parent the {@link CoroutineOrFunctioncallOrComplexstmt} instance
    // * @return condition result
    // */
    //@Override
    //boolean execute(
    //        final HasArgumentsAndVariables<?>/*CoroutineOrFunctioncallOrComplexstmt<?, ?>*/ parent );

    @SuppressWarnings("unchecked")
    @Override
    public Class<? extends Boolean>[] type()
    {
        return new Class[]{ Boolean.class };
    }

}
