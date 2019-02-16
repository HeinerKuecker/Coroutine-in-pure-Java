package de.heinerkuecker.coroutine.exprs.bool;

import de.heinerkuecker.coroutine.HasArgumentsAndVariables;
import de.heinerkuecker.coroutine.exprs.CoroExpression;
import de.heinerkuecker.coroutine.exprs.SimpleExpression;

/**
 * Compare condition
 * to check greaterness or equality of result
 * of the left
 * expression {@link CoroExpression}
 * to the result of the right
 * expression {@link CoroExpression}.
 *
 * @author Heiner K&uuml;cker
 */
public class GreaterOrEqual<TO_COMPARE extends Comparable<TO_COMPARE> , COROUTINE_RETURN , RESUME_ARGUMENT>
extends CmpblLhsRhsBoolExpression<TO_COMPARE , COROUTINE_RETURN , RESUME_ARGUMENT>
{
    /**
     * Constructor.
     *
     * @param lhs
     * @param rhs
     */
    public GreaterOrEqual(
            final SimpleExpression<? extends TO_COMPARE , COROUTINE_RETURN , RESUME_ARGUMENT> lhs ,
            final SimpleExpression<? extends TO_COMPARE , COROUTINE_RETURN , RESUME_ARGUMENT> rhs )
    {
        super(
                lhs ,
                rhs );
    }

    /**
     * Constructor.
     *
     * @param lhs
     * @param rhs
     */
    public GreaterOrEqual(
            final TO_COMPARE lhsValue ,
            final SimpleExpression<? extends TO_COMPARE , COROUTINE_RETURN , RESUME_ARGUMENT> rhs )
    {
        super(
                lhsValue ,
                rhs );
    }

    /**
     * Constructor.
     *
     * @param lhs
     * @param rhs
     */
    public GreaterOrEqual(
            final SimpleExpression<? extends TO_COMPARE , COROUTINE_RETURN , RESUME_ARGUMENT> lhs ,
            final TO_COMPARE rhsValue )
    {
        super(
                lhs ,
                rhsValue );
    }

    /**
     * Constructor.
     *
     * @param lhs
     * @param rhs
     */
    public GreaterOrEqual(
            final TO_COMPARE lhsValue ,
            final TO_COMPARE rhsValue )
    {
        super(
                lhsValue ,
                rhsValue );
    }

    //@Override
    //public boolean execute(
    //        final HasArgumentsAndVariables<? extends RESUME_ARGUMENT>/*CoroutineOrFunctioncallOrComplexstmt<?, ?>*/ parent )
    //{
    //    final T lhsResult = lhs.evaluate( parent );
    //    final T rhsResult = rhs.evaluate( parent );
    //
    //    if ( lhsResult == null && rhsResult == null )
    //    {
    //        return true;
    //    }
    //
    //    if ( lhsResult == null )
    //        // null is lesser
    //    {
    //        return false;
    //    }
    //
    //    if ( rhsResult == null )
    //        // null is lesser
    //    {
    //        return true;
    //    }
    //
    //    return lhsResult.compareTo( rhsResult ) >= 0;
    //}

    @Override
    public Boolean evaluate(
            final HasArgumentsAndVariables<? extends RESUME_ARGUMENT> parent )
    {
        final TO_COMPARE lhsResult = lhs.evaluate( parent );
        final TO_COMPARE rhsResult = rhs.evaluate( parent );

        if ( lhsResult == null && rhsResult == null )
        {
            return true;
        }

        if ( lhsResult == null )
            // null is lesser
        {
            return false;
        }

        if ( rhsResult == null )
            // null is lesser
        {
            return true;
        }

        return lhsResult.compareTo( rhsResult ) >= 0;
    }

    //@Override
    //public List<GetFunctionArgument<? , ? , ?>> getFunctionArgumentGetsNotInFunction()
    //{
    //    final List<GetFunctionArgument<? , ? , ?>> result = new ArrayList<>();
    //
    //    result.addAll(
    //            lhs.getFunctionArgumentGetsNotInFunction() );
    //
    //    result.addAll(
    //            rhs.getFunctionArgumentGetsNotInFunction() );
    //
    //    return result;
    //}

    //@Override
    //public void checkUseVariables(
    //        final HashSet<String> alreadyCheckedFunctionNames ,
    //        final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent ,
    //        final Map<String, Class<?>> globalVariableTypes ,
    //        final Map<String, Class<?>> localVariableTypes )
    //{
    //    this.lhs.checkUseVariables(
    //            alreadyCheckedFunctionNames ,
    //            parent ,
    //            globalVariableTypes, localVariableTypes );
    //
    //    this.rhs.checkUseVariables(
    //            alreadyCheckedFunctionNames ,
    //            parent ,
    //            globalVariableTypes, localVariableTypes );
    //}

    //@Override
    //public void checkUseArguments(
    //        final HashSet<String> alreadyCheckedFunctionNames ,
    //        final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent )
    //{
    //    this.lhs.checkUseArguments( alreadyCheckedFunctionNames , parent );
    //    this.rhs.checkUseArguments( alreadyCheckedFunctionNames , parent );
    //}

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return lhs + " >= " + rhs;
    }

}
