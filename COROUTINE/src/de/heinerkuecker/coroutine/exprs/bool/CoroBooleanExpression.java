package de.heinerkuecker.coroutine.exprs.bool;

import de.heinerkuecker.coroutine.exprs.CoroExpression;

//public interface CoroBooleanExpression
abstract public class CoroBooleanExpression
//extends
implements
    CoroExpression<Boolean>
    //ConditionOrBooleanExpression
{
    ///**
    // * Execute the condition and return the result.
    // *
    // * @param parent the {@link CoroutineOrProcedureOrComplexstmt} instance
    // * @return condition result
    // */
    //@Override
    //boolean execute(
    //        final HasArgumentsAndVariables<?>/*CoroutineOrProcedureOrComplexstmt<?, ?>*/ parent );

    @SuppressWarnings("unchecked")
    @Override
    public Class<? extends Boolean>[] type()
    {
        return new Class[]{ Boolean.class };
    }

}
